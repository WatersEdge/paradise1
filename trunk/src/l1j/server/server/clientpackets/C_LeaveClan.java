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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.ClientThread;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

/**
 * 处理收到由客户端传来离开血盟的封包
 */
public class C_LeaveClan extends ClientBasePacket {

	private static final String C_LEAVE_CLAN = "[C] C_LeaveClan";
	private static Logger _log = Logger.getLogger(C_LeaveClan.class.getName());

	public C_LeaveClan(byte abyte0[], ClientThread clientthread) throws Exception {
		super(abyte0);

		L1PcInstance player = clientthread.getActiveChar();
		String player_name = player.getName();
		String clan_name = player.getClanname();
		int clan_id = player.getClanid();
		if (clan_id == 0) { // 还没加入血盟
			return;
		}

		L1Clan clan = L1World.getInstance().getClan(clan_name);
		if (clan != null) {
			String clan_member_name[] = clan.getAllMembers();
			int i;
			if (player.isCrown() && player.getId() == clan.getLeaderId()) { // 是王族而且是联盟王
				int castleId = clan.getCastleId();
				int houseId = clan.getHouseId();
				if (castleId != 0 || houseId != 0) {
					player.sendPackets(new S_ServerMessage(665)); // \f1拥有城堡与血盟小屋的状态下无法解散血盟。
					return;
				}
				for (L1War war : L1World.getInstance().getWarList()) {
					if (war.CheckClanInWar(clan_name)) {
						player.sendPackets(new S_ServerMessage(302)); // \f1无法解散。
						return;
					}
				}

				for (i = 0; i < clan_member_name.length; i++) { // 取得所有血盟成员
					L1PcInstance online_pc = L1World.getInstance().getPlayer(clan_member_name[i]);
					if (online_pc != null) { // 在线上的血盟成员
						online_pc.setClanid(0);
						online_pc.setClanname("");
						online_pc.setClanRank(0);
						online_pc.setTitle("");
						online_pc.sendPackets(new S_CharTitle(online_pc.getId(), ""));
						online_pc.broadcastPacket(new S_CharTitle(online_pc.getId(), ""));
						online_pc.save(); // 储存玩家资料到资料库中
						online_pc.sendPackets(new S_ServerMessage(269, player_name, clan_name)); // %1血盟的盟主%0%s解散了血盟。
					}
					else { // 非线上的血盟成员
						try {
							L1PcInstance offline_pc = CharacterTable.getInstance().restoreCharacter(clan_member_name[i]);
							offline_pc.setClanid(0);
							offline_pc.setClanname("");
							offline_pc.setClanRank(0);
							offline_pc.setTitle("");
							offline_pc.save(); // 储存玩家资料到资料库中
						}
						catch (Exception e) {
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
						}
					}
				}
				String emblem_file = String.valueOf(clan_id);
				File file = new File("emblem/" + emblem_file);
				file.delete();
				ClanTable.getInstance().deleteClan(clan_name);
			}
			else { // 除了联盟主之外
				L1PcInstance clanMember[] = clan.getOnlineClanMember();
				for (i = 0; i < clanMember.length; i++) {
					clanMember[i].sendPackets(new S_ServerMessage(178, player_name, clan_name)); // \f1%0%s退出 %1 血盟了。
				}
				if (clan.getWarehouseUsingChar() // 血盟成员使用血盟仓库中
				== player.getId()) {
					clan.setWarehouseUsingChar(0); // 移除使用血盟仓库的成员
				}
				player.setClanid(0);
				player.setClanname("");
				player.setClanRank(0);
				player.setTitle("");
				player.sendPackets(new S_CharTitle(player.getId(), ""));
				player.broadcastPacket(new S_CharTitle(player.getId(), ""));
				player.save(); // 储存玩家资料到资料库中
				clan.delMemberName(player_name);
			}
		}
		else {
			player.setClanid(0);
			player.setClanname("");
			player.setClanRank(0);
			player.setTitle("");
			player.sendPackets(new S_CharTitle(player.getId(), ""));
			player.broadcastPacket(new S_CharTitle(player.getId(), ""));
			player.save(); // 储存玩家资料到资料库中
			player.sendPackets(new S_ServerMessage(178, player_name, clan_name)); // \f1%0%s退出 %1 血盟了。
		}
	}

	@Override
	public String getType() {
		return C_LEAVE_CLAN;
	}

}
