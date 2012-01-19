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

import static l1j.server.server.model.Instance.L1PcInstance.REGENSTATE_ATTACK;
import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.MEDITATION;
import l1j.server.Config;
import l1j.server.server.ClientThread;
import l1j.server.server.model.AcceleratorChecker;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

/**
 * 处理客戶端传来攻击的封包
 */
public class C_Attack extends ClientBasePacket {

	public C_Attack(byte[] decrypt, ClientThread client) {
		super(decrypt);
		int targetId = readD();
		int x = readH();
		int y = readH();

		L1PcInstance pc = client.getActiveChar();

		if (pc.isGhost() || pc.isDead() || pc.isTeleport()|| pc.isParalyzed() || pc.isSleeped()) {
			return;
		}

		L1Object target = L1World.getInstance().findObject(targetId);

		// 确认是否可以攻击
		// 是否超重
		if (pc.getInventory().getWeight242() >= 197) {
			pc.sendPackets(new S_ServerMessage(110)); // \f1当负重过重的时候，无法战斗。
			return;
		}

		// 是否隐形
		if (pc.isInvisble()) {
			return;
		}

		// 是否在隐形解除的延迟中
		if (pc.isInvisDelay()) {
			return;
		}

		if (target instanceof L1Character) {
			// 如果目标距离玩家太远(外挂)
			if ((target.getMapId() != pc.getMapId())
					|| (pc.getLocation().getLineDistance(target.getLocation()) > 20D)) {
				return;
			}
		}

		if (target instanceof L1NpcInstance) {
			int hiddenStatus = ((L1NpcInstance) target).getHiddenStatus();
			// 如果目标躲到土里面，或是飞起來了
			if ((hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK)
					|| (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_FLY)) {
				return;
			}
		}

		// 是否要检查攻击的间隔
		if (Config.CHECK_ATTACK_INTERVAL) {
			int result;
			result = pc.getAcceleratorChecker().checkInterval(
					AcceleratorChecker.ACT_TYPE.ATTACK);
			if (result == AcceleratorChecker.R_DISPOSED) {
				return;
			}
		}

		if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) { // 取消绝对屏障
			pc.removeSkillEffect(ABSOLUTE_BARRIER);
		}
		if (pc.hasSkillEffect(MEDITATION)) { // 取消冥想效果
			pc.removeSkillEffect(MEDITATION);
		}

		pc.delInvis(); // 解除隐形状态

		pc.setRegenState(REGENSTATE_ATTACK);

		if ((target != null)
				&& !((L1Character) target).isDead()) {
			target.onAction(pc);
		} else { // 目标为空或死亡
			L1Character cha = new L1Character();
			cha.setId(targetId);
			cha.setX(x);
			cha.setY(y);
			L1Attack atk = new L1Attack(pc, cha);
			atk.actionPc();
		}
	}
}