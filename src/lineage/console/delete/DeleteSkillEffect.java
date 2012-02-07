package lineage.console.delete;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.BURNING_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_BLESS_OF_MAZU;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_ENCHANTING_BATTLE;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_AHTHARTS;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_BIRTH;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_FIGURE;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_LIFE;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_LINDVIOR;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_VALAKAS;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_BATTLE;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_150;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_175;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_200;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_225;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_250;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_STRENGTHENING_HP;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_STRENGTHENING_MP;
import static l1j.server.server.model.skill.L1SkillId.FIRE_BLESS;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.STORM_EYE;
import static l1j.server.server.model.skill.L1SkillId.STORM_SHOT;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHOT;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillHaste;

/**
 * 删除技能效果
 * 
 * @author jrwz
 */
public class DeleteSkillEffect {

	/**
	 * 删除绝对屏障效果
	 * 
	 * @param pc
	 *            对象
	 */
	public final static void DeleteEffectOfAbsoluteBarrier(final L1PcInstance pc) {
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
	public final static void DeleteEffectOfRepeat(final L1PcInstance pc, final int skill_id) {
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
	public final static void DeleteEffectOfGreenPotion(final L1PcInstance pc, final int skill_id) {
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
	public final static void DeleteEffectOfSlow(final L1PcInstance pc, final int skill_id) {
		if (pc.hasSkillEffect(skill_id)) {
			pc.killSkillEffectTimer(skill_id);
			pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 0, 0));
		}
	}

	/**
	 * 设定不可重复的魔法状态 (经验药水、魔眼等等)
	 * 
	 * @param pc
	 *            检查对象
	 * @param skillId
	 *            技能ID
	 */
	public static void deleteRepeatedSkills(L1PcInstance pc, int skillId) {
		final int[][] repeatedSkills = {
				// 经验加成状态
				{ EFFECT_POTION_OF_EXP_150, EFFECT_POTION_OF_EXP_175, EFFECT_POTION_OF_EXP_200, EFFECT_POTION_OF_EXP_225, EFFECT_POTION_OF_EXP_250, EFFECT_POTION_OF_BATTLE },
				// 体力增强卷轴、魔力增强卷轴、强化战斗卷
				{ EFFECT_STRENGTHENING_HP, EFFECT_STRENGTHENING_MP, EFFECT_ENCHANTING_BATTLE },
				// 火焰武器、风之神射、烈炎气息、暴风之眼、烈炎武器、暴风神射、妈祖的祝福
				{ FIRE_WEAPON, WIND_SHOT, FIRE_BLESS, STORM_EYE, BURNING_WEAPON, STORM_SHOT, EFFECT_BLESS_OF_MAZU },
				// 1 ~ 9 阶附魔石(近战)(远攻)(恢复)(防御)
				{ EFFECT_MAGIC_STONE_A_1, EFFECT_MAGIC_STONE_A_2, EFFECT_MAGIC_STONE_A_3, EFFECT_MAGIC_STONE_A_4, EFFECT_MAGIC_STONE_A_5, EFFECT_MAGIC_STONE_A_6, EFFECT_MAGIC_STONE_A_7, EFFECT_MAGIC_STONE_A_8, EFFECT_MAGIC_STONE_A_9, EFFECT_MAGIC_STONE_B_1,
						EFFECT_MAGIC_STONE_B_2, EFFECT_MAGIC_STONE_B_3, EFFECT_MAGIC_STONE_B_4, EFFECT_MAGIC_STONE_B_5, EFFECT_MAGIC_STONE_B_6, EFFECT_MAGIC_STONE_B_7, EFFECT_MAGIC_STONE_B_8, EFFECT_MAGIC_STONE_B_9, EFFECT_MAGIC_STONE_C_1, EFFECT_MAGIC_STONE_C_2,
						EFFECT_MAGIC_STONE_C_3, EFFECT_MAGIC_STONE_C_4, EFFECT_MAGIC_STONE_C_5, EFFECT_MAGIC_STONE_C_6, EFFECT_MAGIC_STONE_C_7, EFFECT_MAGIC_STONE_C_8, EFFECT_MAGIC_STONE_C_9, EFFECT_MAGIC_STONE_D_1, EFFECT_MAGIC_STONE_D_2, EFFECT_MAGIC_STONE_D_3,
						EFFECT_MAGIC_STONE_D_4, EFFECT_MAGIC_STONE_D_5, EFFECT_MAGIC_STONE_D_6, EFFECT_MAGIC_STONE_D_7, EFFECT_MAGIC_STONE_D_8, EFFECT_MAGIC_STONE_D_9 },
				// 魔眼
				{ EFFECT_MAGIC_EYE_OF_AHTHARTS, EFFECT_MAGIC_EYE_OF_FAFURION, EFFECT_MAGIC_EYE_OF_LINDVIOR, EFFECT_MAGIC_EYE_OF_VALAKAS, EFFECT_MAGIC_EYE_OF_BIRTH, EFFECT_MAGIC_EYE_OF_FIGURE, EFFECT_MAGIC_EYE_OF_LIFE } };

		for (int[] skills : repeatedSkills) {
			for (int id : skills) {
				if (id == skillId) {
					stopSkillList(pc, skillId, skills);
				}
			}
		}
	}

	/**
	 * 将重复的状态删除 (经验药水、魔眼等等)
	 * 
	 * @param pc
	 * @param _skillId
	 * @param repeat_skill
	 */
	private static void stopSkillList(L1PcInstance pc, int _skillId, int[] repeat_skill) {
		for (int skillId : repeat_skill) {
			if (skillId != _skillId) {
				pc.removeSkillEffect(skillId);
			}
		}
	}
}
