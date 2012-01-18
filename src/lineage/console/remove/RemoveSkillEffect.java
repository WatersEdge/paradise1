package lineage.console.remove;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.BLOODLUST;
import static l1j.server.server.model.skill.L1SkillId.ENTANGLE;
import static l1j.server.server.model.skill.L1SkillId.GREATER_HASTE;
import static l1j.server.server.model.skill.L1SkillId.HASTE;
import static l1j.server.server.model.skill.L1SkillId.HOLY_WALK;
import static l1j.server.server.model.skill.L1SkillId.MASS_SLOW;
import static l1j.server.server.model.skill.L1SkillId.MOVING_ACCELERATION;
import static l1j.server.server.model.skill.L1SkillId.SLOW;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_THIRD_SPEED;
import static l1j.server.server.model.skill.L1SkillId.WIND_WALK;
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

	/**
	 * 删除重复的一段加速效果 (绿色药水)
	 * 
	 * @param pc
	 *            对象
	 */
	public static void removeStatusGreenPotion(L1PcInstance pc) {

		// 一段加速
		if (pc.hasSkillEffect(STATUS_HASTE)) {
			pc.killSkillEffectTimer(STATUS_HASTE);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
			pc.setMoveSpeed(0);
		}

		// 加速术
		if (pc.hasSkillEffect(HASTE)) {
			pc.killSkillEffectTimer(HASTE);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
			pc.setMoveSpeed(0);
		}

		// 强力加速术
		if (pc.hasSkillEffect(GREATER_HASTE)) {
			pc.killSkillEffectTimer(GREATER_HASTE);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
			pc.setMoveSpeed(0);
		}
	}

	/**
	 * 删除重复的二段加速效果
	 * 
	 * @param pc
	 *            对象
	 */
	public static void removeStatusBravePotion(L1PcInstance pc) {

		// 勇敢药水类 1.33倍
		if (pc.hasSkillEffect(STATUS_BRAVE)) {
			pc.killSkillEffectTimer(STATUS_BRAVE);
		}

		// 精灵饼干 1.15倍
		if (pc.hasSkillEffect(STATUS_ELFBRAVE)) {
			pc.killSkillEffectTimer(STATUS_ELFBRAVE);
		}

		// 神圣疾走 移速1.33倍
		if (pc.hasSkillEffect(HOLY_WALK)) {
			pc.killSkillEffectTimer(HOLY_WALK);
		}

		// 行走加速 移速1.33倍
		if (pc.hasSkillEffect(MOVING_ACCELERATION)) {
			pc.killSkillEffectTimer(MOVING_ACCELERATION);
		}

		// 风之疾走 移速1.33倍
		if (pc.hasSkillEffect(WIND_WALK)) {
			pc.killSkillEffectTimer(WIND_WALK);
		}

		// 血之渴望 攻速1.33倍
		if (pc.hasSkillEffect(BLOODLUST)) {
			pc.killSkillEffectTimer(BLOODLUST);
		}

		// 超级加速 2.66倍
		if (pc.hasSkillEffect(STATUS_BRAVE2)) {
			pc.killSkillEffectTimer(STATUS_BRAVE2);
		}
	}

	/**
	 * 删除重复的三段加速效果
	 * 
	 * @param pc
	 *            对象
	 */
	public static void removeThirdSpeed(L1PcInstance pc) {

		// 三段加速
		if (pc.hasSkillEffect(STATUS_THIRD_SPEED)) {
			pc.killSkillEffectTimer(STATUS_THIRD_SPEED);
		}
	}

	/**
	 * 删除重复的缓速效果
	 * 
	 * @param pc
	 *            对象
	 */
	public static void removeStatusSlow(L1PcInstance pc) {

		// 缓速术
		if (pc.hasSkillEffect(SLOW)) {
			pc.killSkillEffectTimer(SLOW);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
		}

		// 集体缓速术
		if (pc.hasSkillEffect(MASS_SLOW)) {
			pc.killSkillEffectTimer(MASS_SLOW);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
		}

		// 地面障碍
		if (pc.hasSkillEffect(ENTANGLE)) {
			pc.killSkillEffectTimer(ENTANGLE);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
		}
	}
}
