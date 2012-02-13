/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package l1j.server.server.clientpackets;

import static l1j.server.server.model.skill.L1SkillId.AREA_OF_SILENCE;
import static l1j.server.server.model.skill.L1SkillId.SILENCE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_POISON_SILENCE;
import l1j.server.Config;
import l1j.server.server.ClientThread;
import l1j.server.server.GMCommands;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.ChatLogTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

/**
 * 处理收到由客户端传来的聊天封包
 */
public class C_Chat extends ClientBasePacket {

	private static final String C_CHAT = "[C] C_Chat";

	public C_Chat(final byte abyte0[], final ClientThread clientthread) {
		super(abyte0);

		final L1PcInstance pc = clientthread.getActiveChar();
		final int chatType = this.readC();
		final String chatText = this.readS();
		if (pc.hasSkillEffect(SILENCE) || pc.hasSkillEffect(AREA_OF_SILENCE) || pc.hasSkillEffect(STATUS_POISON_SILENCE)) {
			return;
		}
		if (pc.hasSkillEffect(1005)) { // 被魔封
			pc.sendPackets(new S_ServerMessage(242)); // 你从现在被禁止闲谈。
			return;
		}

		// 一般聊天
		if (chatType == 0) {
			if (pc.isGhost() && !(pc.isGm() || pc.isMonitor())) {
				return;
			}
			// GM指令
			if (chatText.startsWith(".") && (pc.isGm() || pc.isMonitor())) {
				final String cmd = chatText.substring(1);
				GMCommands.getInstance().handleCommands(pc, cmd);
				return;
			}

			// 交易频道
			// 本来はchatType==12になるはずだが、行头の$が送信されない
			if (chatText.startsWith("$")) {
				final String text = chatText.substring(1);
				this.chatWorld(pc, text, 12);
				if (!pc.isGm()) {
					pc.checkChatInterval();
				}
				return;
			}

			ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
			final S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_NORMALCHAT, 0);
			if (!pc.getExcludingList().contains(pc.getName())) {
				pc.sendPackets(s_chatpacket);
			}
			for (final L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
				if ((listner.getMapId() < 16384) || (listner.getMapId() > 25088) || (listner.getInnKeyId() == pc.getInnKeyId())) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(s_chatpacket);
					}
				}
			}
			// 怪物模仿
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName()) && !mob.isDead()) {
						mob.broadcastPacket(new S_NpcChatPacket(mob, chatText, 0));
					}
				}
			}
		}

		// 喊叫
		else if (chatType == 2) {

			if (pc.isGhost()) {
				return;
			}
			ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
			final S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_NORMALCHAT, 2);
			if (!pc.getExcludingList().contains(pc.getName())) {
				pc.sendPackets(s_chatpacket);
			}
			for (final L1PcInstance listner : L1World.getInstance().getVisiblePlayer(pc, 50)) {
				if ((listner.getMapId() < 16384) || (listner.getMapId() > 25088) || (listner.getInnKeyId() == pc.getInnKeyId())) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(s_chatpacket);
					}
				}
			}

			// 怪物模仿
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName()) && !mob.isDead()) {
						for (final L1PcInstance listner : L1World.getInstance().getVisiblePlayer(mob, 50)) {
							listner.sendPackets(new S_NpcChatPacket(mob, chatText, 2));
						}
					}
				}
			}
		}

		// 全体聊天
		else if (chatType == 3) {
			this.chatWorld(pc, chatText, chatType);
		}

		// 血盟聊天
		else if (chatType == 4) {
			if (pc.getClanid() != 0) { // 所属血盟
				final L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
				final int rank = pc.getClanRank();
				if ((clan != null) && ((rank == L1Clan.CLAN_RANK_PUBLIC) || (rank == L1Clan.CLAN_RANK_GUARDIAN) || (rank == L1Clan.CLAN_RANK_PRINCE))) {
					ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
					final S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_GLOBALCHAT, 4);
					final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
					for (final L1PcInstance listner : clanMembers) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							if (listner.isShowClanChat() && (chatType == 4)) {
								listner.sendPackets(s_chatpacket);
							}
						}
					}
				}
			}
		}

		// 组队聊天
		else if (chatType == 11) {
			if (pc.isInParty()) { // 组队中
				ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
				final S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_GLOBALCHAT, 11);
				final L1PcInstance[] partyMembers = pc.getParty().getMembers();
				for (final L1PcInstance listner : partyMembers) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						if (listner.isShowPartyChat() && (chatType == 11)) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
			}
		}

		// 交易聊天
		else if (chatType == 12) {
			this.chatWorld(pc, chatText, chatType);
		}

		// 联合血盟
		else if (chatType == 13) {
			if (pc.getClanid() != 0) { // 在血盟中
				final L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
				final int rank = pc.getClanRank();
				if ((clan != null) && ((rank == L1Clan.CLAN_RANK_GUARDIAN) || (rank == L1Clan.CLAN_RANK_PRINCE))) {
					ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
					final S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_GLOBALCHAT, 13);
					final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
					for (final L1PcInstance listner : clanMembers) {
						final int listnerRank = listner.getClanRank();
						if (!listner.getExcludingList().contains(pc.getName()) && ((listnerRank == L1Clan.CLAN_RANK_GUARDIAN) || (listnerRank == L1Clan.CLAN_RANK_PRINCE))) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
			}
		}

		// 聊天组队
		else if (chatType == 14) {
			if (pc.isInChatParty()) { // 聊天组队
				ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
				final S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_NORMALCHAT, 14);
				final L1PcInstance[] partyMembers = pc.getChatParty().getMembers();
				for (final L1PcInstance listner : partyMembers) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(s_chatpacket);
					}
				}
			}
		}
		if (!pc.isGm()) {
			pc.checkChatInterval();
		}
	}

	@Override
	public String getType() {
		return C_CHAT;
	}

	/** 世界聊天 */
	private void chatWorld(final L1PcInstance pc, final String chatText, final int chatType) {
		if (pc.isGm()) {
			ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
			L1World.getInstance().broadcastPacketToAll(new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_GLOBALCHAT, chatType));
		}
		else if (pc.getLevel() >= Config.GLOBAL_CHAT_LEVEL) {
			if (L1World.getInstance().isWorldChatElabled()) {
				if (pc.get_food() >= 6) {
					pc.set_food(pc.get_food() - 5);
					ChatLogTable.getInstance().storeChat(pc, null, chatText, chatType);
					pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, pc.get_food()));
					for (final L1PcInstance listner : L1World.getInstance().getAllPlayers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							if (listner.isShowTradeChat() && (chatType == 12)) {
								listner.sendPackets(new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_GLOBALCHAT, chatType));
							}
							else if (listner.isShowWorldChat() && (chatType == 3)) {
								listner.sendPackets(new S_ChatPacket(pc, chatText, Opcodes.S_OPCODE_GLOBALCHAT, chatType));
							}
						}
					}
				}
				else {
					pc.sendPackets(new S_ServerMessage(462)); // 你太过于饥饿以致于无法谈话。
				}
			}
			else {
				pc.sendPackets(new S_ServerMessage(510)); // 管理者有非常重要的事项公告，请见谅。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(195, String.valueOf(Config.GLOBAL_CHAT_LEVEL))); // 等级 %0 以下的角色无法使用公频或买卖频道。
		}
	}
}
