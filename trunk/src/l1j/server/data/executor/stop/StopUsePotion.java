package l1j.server.data.executor.stop;

import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 判断能否使用药水
 * 
 * @author jrwz
 */
public class StopUsePotion {

	/**
	 * 判断能否使用药水
	 * 
	 * @param pc
	 *            角色
	 * @return 可以、true 不能、false
	 */
	public static boolean stopUsePotion(final L1PcInstance pc) {

		// 药水霜化术状态
		if (pc.hasSkillEffect(DECAY_POTION)) {
			pc.sendPackets(new S_ServerMessage(698)); // 喉咙灼热，无法喝东西。
			return false;
		}
		return true;
	}

}
