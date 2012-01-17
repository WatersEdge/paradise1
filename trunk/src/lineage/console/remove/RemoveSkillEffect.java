package lineage.console.remove;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 删除技能效果
 * 
 * @author jrwz
 */
public class RemoveSkillEffect {

	/**
	 * 删除绝对屏障效果
	 * 
	 * @param pc
	 *            对象
	 */
	public static void removeAbsoluteBarrierEffect(L1PcInstance pc) {
		if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
			pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
			pc.startHpRegeneration(); // 开始角色回血
			pc.startMpRegeneration(); // 开始角色回魔
			pc.startHpRegenerationByDoll(); // 开始魔法娃娃对角色的回血
			pc.startMpRegenerationByDoll(); // 开始魔法娃娃对角色的回魔
		}
	}

	/**
	 * 删除蓝色药水效果
	 * 
	 * @param pc
	 *            对象
	 */
	public static void removeStatusBluePotion(L1PcInstance pc) {
		if (pc.hasSkillEffect(STATUS_BLUE_POTION)) {
			pc.killSkillEffectTimer(STATUS_BLUE_POTION);
		}
	}

}
