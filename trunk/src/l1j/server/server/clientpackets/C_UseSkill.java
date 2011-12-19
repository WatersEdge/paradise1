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

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.CALL_CLAN;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WALL;
import static l1j.server.server.model.skill.L1SkillId.LIFE_STREAM;
import static l1j.server.server.model.skill.L1SkillId.MASS_TELEPORT;
import static l1j.server.server.model.skill.L1SkillId.MEDITATION;
import static l1j.server.server.model.skill.L1SkillId.RUN_CLAN;
import static l1j.server.server.model.skill.L1SkillId.TELEPORT;
import static l1j.server.server.model.skill.L1SkillId.TRUE_TARGET;
import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.ClientThread;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.AcceleratorChecker;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

/**
 * 处理收到由客户端传来使用魔法的封包
 */
public class C_UseSkill extends ClientBasePacket {

	public C_UseSkill(byte abyte0[], ClientThread client) throws Exception {
		super(abyte0);
		int row = readC();
		int column = readC();
		int skillId = (row * 8) + column + 1;
		String charName = null;
		String message = null;
		int targetId = 0;
		int targetX = 0;
		int targetY = 0;
		L1PcInstance pc = client.getActiveChar();

		// 传送中或死亡
		if (pc.isTeleport() || pc.isDead()) {
			return;
		}
		// 无法使用的地图
		if (!pc.getMap().isUsableSkill()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1你无法在这个地方使用。
			return;
		}
		// 没有该技能
		if (!pc.isSkillMastery(skillId)) {
			return;
		}

		// 检查使用魔法的间隔
		if (Config.CHECK_SPELL_INTERVAL) {
			int result;
			// FIXME 判断有向及无向的魔法
			if (SkillsTable.getInstance().getTemplate(skillId).getActionId() == ActionCodes.ACTION_SkillAttack) {
				result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.SPELL_DIR);
			}
			else {
				result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.SPELL_NODIR);
			}
			if (result == AcceleratorChecker.R_DISPOSED) {
				return;
			}
		}

		if (abyte0.length > 4) {
			try {
				if ((skillId == CALL_CLAN) || (skillId == RUN_CLAN)) { // 呼唤盟友、援护盟友
					charName = readS();
				}
				else if (skillId == TRUE_TARGET) { // 精准目标
					targetId = readD();
					targetX = readH();
					targetY = readH();
					message = readS();
				}
				else if ((skillId == TELEPORT) || (skillId == MASS_TELEPORT)) { // 指定传送、集体传送
					readH(); // MapID
					targetId = readD(); // Bookmark ID
				}
				else if ((skillId == FIRE_WALL) || (skillId == LIFE_STREAM)) { // 火牢、治愈能量风暴
					targetX = readH();
					targetY = readH();
				}
				else {
					targetId = readD();
					targetX = readH();
					targetY = readH();
				}
			}
			catch (Exception e) {
				// _log.log(Level.SEVERE, "", e);
			}
		}

		if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) { // 取消绝对屏障
			pc.removeSkillEffect(ABSOLUTE_BARRIER);
		}
		if (pc.hasSkillEffect(MEDITATION)) { // 取消冥想效果
			pc.removeSkillEffect(MEDITATION);
		}

		try {
			if ((skillId == CALL_CLAN) || (skillId == RUN_CLAN)) { // 呼唤盟友、援护盟友
				if (charName.isEmpty()) {
					// 角色名称为空
					return;
				}

				L1PcInstance target = L1World.getInstance().getPlayer(charName);

				if (target == null) {
					// メッセージが正确であるか未调查
					pc.sendPackets(new S_ServerMessage(73, charName)); // \f1%0%d 不在线上。
					return;
				}
				if (pc.getClanid() != target.getClanid()) {
					pc.sendPackets(new S_ServerMessage(414)); // 您只能邀请您血盟中的成员。
					return;
				}
				targetId = target.getId();
				if (skillId == CALL_CLAN) { // 呼唤盟友
					// 移动せずに连续して同じクラン员にコールクランした场合、向きは前回の向きになる
					int callClanId = pc.getCallClanId();
					if ((callClanId == 0) || (callClanId != targetId)) {
						pc.setCallClanId(targetId);
						pc.setCallClanHeading(pc.getHeading());
					}
				}
			}
			L1SkillUse l1skilluse = new L1SkillUse();
			l1skilluse.handleCommands(pc, skillId, targetId, targetX, targetY, message, 0, L1SkillUse.TYPE_NORMAL);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
