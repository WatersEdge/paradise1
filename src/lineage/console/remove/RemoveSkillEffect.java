package lineage.console.remove;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillHaste;

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
	public final static void removeAbsoluteBarrierEffect(final L1PcInstance pc) {
		if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
			pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
			pc.startHpRegeneration(); // 开始角色回血
			pc.startMpRegeneration(); // 开始角色回魔
			pc.startHpRegenerationByDoll(); // 开始魔法娃娃对角色的回血
			pc.startMpRegenerationByDoll(); // 开始魔法娃娃对角色的回魔
		}
	}

	/**
	 * 删除重复的技能效果
	 * 
	 * @param pc
	 *            对象
	 * @param skill_id
	 *            技能ID
	 */
	public final static void removeRepeat(final L1PcInstance pc, final int skill_id) {
		if (pc.hasSkillEffect(skill_id)) {
			pc.killSkillEffectTimer(skill_id);
		}
	}

	/**
	 * 删除重复的一段加速效果 (绿色药水)
	 * 
	 * @param pc
	 *            对象
	 * @param skill_id
	 *            技能ID
	 */
	public final static void removeStatusGreenPotion(final L1PcInstance pc, final int skill_id) {
		if (pc.hasSkillEffect(skill_id)) {
			pc.killSkillEffectTimer(skill_id);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
			pc.setMoveSpeed(0);
		}
	}

	/**
	 * 删除重复的缓速效果
	 * 
	 * @param pc
	 *            对象
	 * @param skill_id
	 *            技能ID
	 */
	public final static void removeStatusSlow(final L1PcInstance pc, final int skill_id) {
		if (pc.hasSkillEffect(skill_id)) {
			pc.killSkillEffectTimer(skill_id);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
		}
	}
}
