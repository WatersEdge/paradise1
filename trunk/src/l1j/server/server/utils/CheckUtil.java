package l1j.server.server.utils;

import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_PARALYZED;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_SKIN;
import static l1j.server.server.model.skill.L1SkillId.THUNDER_GRAB;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_COCKATRICE;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_BASILISK;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FREEZE;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 对象检查器
 * 
 * @author jrwz
 */
public class CheckUtil {

	private CheckUtil() {
	}

	/**
	 * 检查能否使用指定道具
	 * 
	 * @param pc
	 *            检查的对象
	 * @return 如果可使用道具、true
	 */
	public static boolean getUseItem(final L1PcInstance pc) {

		// 法师魔法 (药水霜化术)
		if (pc.hasSkillEffect(DECAY_POTION)) {
			pc.sendPackets(new S_ServerMessage(698)); // 喉咙灼热，无法喝东西。
			return false;
		}

		// 龙骑士魔法 (夺命之雷)
		if (pc.hasSkillEffect(THUNDER_GRAB)) { // 2秒束缚效果
			return false;
		}

		// 束缚术
		if (pc.hasSkillEffect(STATUS_FREEZE)) {
			return false;
		}
		return true;
	}

	/**
	 * 检查能否使用全部道具
	 * 
	 * @param pc
	 *            检查的对象
	 * @return 如果可使用道具、true
	 */
	public static boolean getUseItemAll(final L1PcInstance pc) {

		// 麻痹毒(木乃伊诅咒)后
		if (pc.hasSkillEffect(STATUS_CURSE_PARALYZED)) {
			return false;
		}

		// 法师魔法 (冰矛围篱)
		if (pc.hasSkillEffect(ICE_LANCE)) {
			return false;
		}

		// 亚力安冰矛围篱
		if (pc.hasSkillEffect(ICE_LANCE_COCKATRICE)) {
			return false;
		}

		// 邪恶蜥蜴冰矛围篱
		if (pc.hasSkillEffect(ICE_LANCE_BASILISK)) {
			return false;
		}

		// 法师魔法 (沉睡之雾)
		if (pc.hasSkillEffect(FOG_OF_SLEEPING)) {
			return false;
		}

		// 骑士魔法 (冲击之晕)
		if (pc.hasSkillEffect(SHOCK_STUN)) {
			return false;
		}

		// 妖精魔法 (大地屏障)
		if (pc.hasSkillEffect(EARTH_BIND)) {
			return false;
		}

		// 龙骑士魔法 (冲击之肤)
		if (pc.hasSkillEffect(SHOCK_SKIN)) { // 冲晕效果
			return false;
		}

		// 龙骑士魔法 (寒冰喷吐)
		if (pc.hasSkillEffect(FREEZING_BREATH)) {
			return false;
		}
		return true;
	}

}
