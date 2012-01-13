package l1j.server.data.executor.remove;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 解除技能效果
 * 
 * @author jrwz
 */
public class RemoveSkillEffect {

	/**
	 * 解除绝对屏障效果
	 * 
	 * @param pc
	 *            角色
	 */
	public static void RemoveAbsoluteBarrierEffect(L1PcInstance pc) {
		if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
			pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
			pc.startHpRegeneration(); // 开始角色回血
			pc.startMpRegeneration(); // 开始角色回魔
			pc.startHpRegenerationByDoll(); // 开始魔法娃娃对角色的回血
			pc.startMpRegenerationByDoll(); // 开始魔法娃娃对角色的回魔
		}
	}

}
