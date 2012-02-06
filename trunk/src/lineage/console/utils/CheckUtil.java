package lineage.console.utils;

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BLIZZARD;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_BASILISK;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_COCKATRICE;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_SKIN;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.SOLID_CARRIAGE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_PARALYZED;
import l1j.server.server.ClientThread;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_SPMR;
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
	 * @param client
	 *            连线端对象
	 * @return 如果可用、true
	 */
	public static boolean checkUseItem(final ClientThread client) {

		// 取得使用者
		L1PcInstance pc = client.getActiveChar();

		// 角色为空
		if (pc == null) {
			return false;
		}

		// 幽灵状态
		if (pc.isGhost()) {
			return false;
		}

		// 死亡状态
		if (pc.isDead()) {
			return false;
		}

		// 传送状态
		if (pc.isTeleport()) {
			return false;
		}

		// 开个人商店中
		if (pc.isPrivateShop()) {
			return false;
		}

		// 不能使用道具的地图
		if (!pc.getMap().isUsableItem()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1你无法在这个地方使用。
			return false;
		}

		// 什么都不能做的状态
		if (!CheckUtil.check(client)) {
			return false;
		}

		return true;
	}

	/**
	 * 检查能否使用药水
	 * 
	 * @param client
	 *            连线端对象
	 * @return 如果可用、true
	 */
	public static boolean checkUsePotion(final ClientThread client) {

		// 取得使用者
		L1PcInstance pc = client.getActiveChar();

		// 药水霜化术状态
		if (pc.hasSkillEffect(DECAY_POTION)) {
			pc.sendPackets(new S_ServerMessage(698)); // 喉咙灼热，无法喝东西。
			return false;
		}

		return true;
	}

	/**
	 * 检查能否变身
	 * 
	 * @param pc
	 *            对象
	 * @return 如果可用、true
	 */
	public static boolean checkPoly(final L1PcInstance pc) {

		// 取回觉醒技能ID
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身。
			return false;
		}

		return true;
	}

	/**
	 * 什么都不能做的状态 (无法回城、无法移动、无法使用道具等)
	 * 
	 * @param client
	 *            连线端对象
	 * @return 如果可用、true
	 */
	public static boolean check(final ClientThread client) {

		// 取得使用者
		L1PcInstance pc = client.getActiveChar();

		// 麻痹毒(木乃伊诅咒)后
		if (pc.hasSkillEffect(STATUS_CURSE_PARALYZED)) {
			return false;
		}

		// 法师魔法 (冰矛围篱)
		if (pc.hasSkillEffect(ICE_LANCE)) {
			return false;
		}

		// 法师魔法 (冰雪飓风)
		if (pc.hasSkillEffect(FREEZING_BLIZZARD)) {
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

	/**
	 * 检查武器与防具该职业能否使用
	 * 
	 * @param pc
	 *            使用的对象
	 * @param useItem
	 *            使用的装备
	 * @return 如果该职业可用、true
	 */
	public static boolean checkEquipped(final L1PcInstance pc, final L1ItemInstance useItem) {

		// 是否能装备
		boolean isEquipped = false;

		// 王族
		if (pc.isCrown()) {
			if (useItem.getItem().isUseRoyal()) {
				isEquipped = true;
			}
		}

		// 骑士
		else if (pc.isKnight()) {
			if (useItem.getItem().isUseKnight()) {
				isEquipped = true;
			}
		}

		// 精灵
		else if (pc.isElf()) {
			if (useItem.getItem().isUseElf()) {
				isEquipped = true;
			}
		}

		// 法师
		else if (pc.isWizard()) {
			if (useItem.getItem().isUseMage()) {
				isEquipped = true;
			}
		}

		// 黑暗精灵
		else if (pc.isDarkelf()) {
			if (useItem.getItem().isUseDarkelf()) {
				isEquipped = true;
			}
		}

		// 龙骑士
		else if (pc.isDragonKnight()) {
			if (useItem.getItem().isUseDragonknight()) {
				isEquipped = true;
			}
		}

		// 幻术师
		else if (pc.isIllusionist()) {
			if (useItem.getItem().isUseIllusionist()) {
				isEquipped = true;
			}
		}

		// 该职业不可用
		if (!isEquipped) {
			pc.sendPackets(new S_ServerMessage(264)); // \f1你的职业无法使用此装备。
		}

		return isEquipped;
	}

	/**
	 * 设置武器的装备
	 * 
	 * @param pc
	 *            装备的对象
	 * @param weapon
	 *            装备的武器
	 */
	public static void useWeapon(final L1PcInstance pc, final L1ItemInstance weapon) {

		// 取回角色背包道具
		final L1PcInventory pcInventory = pc.getInventory();

		// 没有使用武器或使用武器与所选武器不同
		if ((pc.getWeapon() == null) || !pc.getWeapon().equals(weapon)) {

			// 取回武器类型
			final int weapon_type = weapon.getItem().getType();

			// 取回变身编号
			final int polyid = pc.getTempCharGfx();

			// 此变身状态不能使用的武器
			if (!L1PolyMorph.isEquipableWeapon(polyid, weapon_type)) {
				return;
			}

			// 装备盾牌时不可再使用双手武器
			if (weapon.getItem().isTwohandedWeapon() && (pcInventory.getTypeEquipped(2, 7) >= 1)) {
				pc.sendPackets(new S_ServerMessage(128));
				return;
			}
		}

		// 已有装备的状态
		if (pc.getWeapon() != null) {

			// 被诅咒的装备
			if (pc.getWeapon().getItem().getBless() == 2) {
				pc.sendPackets(new S_ServerMessage(150)); // \f1你无法这样做。这个物品已经被诅咒了。
				return;
			}

			// 解除装备
			if (pc.getWeapon().equals(weapon)) {
				pcInventory.setEquipped(pc.getWeapon(), false, false, false);
				return;
			}
			else { // 武器交换
				pcInventory.setEquipped(pc.getWeapon(), false, false, true);
			}
		}

		// 被诅咒的装备
		if (weapon.getItem().getBless() == 2) {
			pc.sendPackets(new S_ServerMessage(149, weapon.getLogName())); // \f1%0%s 主动固定在你的手上！
		}
		pcInventory.setEquipped(weapon, true, false, false);
	}

	/**
	 * 设置防具的装备
	 * 
	 * @param pc
	 *            装备的对象
	 * @param armor
	 *            装备的防具
	 */
	public static void useArmor(final L1PcInstance pc, final L1ItemInstance armor) {

		// 取回防具类型
		final int type = armor.getItem().getType();

		// 取回角色背包道具
		final L1PcInventory pcInventory = pc.getInventory();

		// 装备栏是否有空位
		boolean equipeSpace;

		// 戒指可戴2个,其他都只能戴1个
		if (type == 9) {
			equipeSpace = pcInventory.getTypeEquipped(2, 9) <= 1;
		}
		else {
			equipeSpace = pcInventory.getTypeEquipped(2, type) <= 0;
		}

		// 防具穿戴
		if (equipeSpace && !armor.isEquipped()) { // 要安装的装备栏尚未安装物品

			// 取回变身编号
			final int polyid = pc.getTempCharGfx();

			// 在此变身状态不能装备的装备
			if (!L1PolyMorph.isEquipableArmor(polyid, type)) {
				return;
			}

			// 已经装备其他东西。
			if (((type == 13) && (pcInventory.getTypeEquipped(2, 7) >= 1)) || ((type == 7) && (pcInventory.getTypeEquipped(2, 13) >= 1))) {
				pc.sendPackets(new S_ServerMessage(124)); // 已经装备其他东西。
				return;
			}

			// 使用双手武器时无法装备盾牌
			if ((type == 7) && (pc.getWeapon() != null)) {
				if (pc.getWeapon().getItem().isTwohandedWeapon()) { // 双手武器
					pc.sendPackets(new S_ServerMessage(129)); // \f1当你使用双手武器时，无法装备盾牌。
					return;
				}
			}

			// 穿着斗篷时不可穿内衣
			if ((type == 3) && (pcInventory.getTypeEquipped(2, 4) >= 1)) {
				pc.sendPackets(new S_ServerMessage(126, "$224", "$225")); // \f1穿着%1 无法装备 %0%o 。
				return;
			}

			// 穿着盔甲时不可穿内衣
			else if ((type == 3) && (pcInventory.getTypeEquipped(2, 2) >= 1)) {
				pc.sendPackets(new S_ServerMessage(126, "$224", "$226")); // \f1穿着%1 无法装备 %0%o 。
				return;
			}

			// 穿着斗篷时不可穿盔甲
			else if ((type == 2) && (pcInventory.getTypeEquipped(2, 4) >= 1)) {
				pc.sendPackets(new S_ServerMessage(126, "$226", "$225")); // \f1穿着%1 无法装备 %0%o 。
				return;
			}
			pcInventory.setEquipped(armor, true);
		}

		// 防具脱除
		else if (armor.isEquipped()) { // 所选防具穿戴在身上
			if (armor.getItem().getBless() == 2) { // 被诅咒的装备
				pc.sendPackets(new S_ServerMessage(150)); // \f1你无法这样做。这个物品已经被诅咒了。
				return;

			}
			else {

				// 穿着盔甲时不能脱下内衣
				if ((type == 3) && (pcInventory.getTypeEquipped(2, 2) >= 1)) {
					pc.sendPackets(new S_ServerMessage(127)); // \f1你不能够脱掉那个。
					return;

				}

				// 穿着斗篷时不能脱下内衣
				else if (((type == 2) || (type == 3)) && (pcInventory.getTypeEquipped(2, 4) >= 1)) {
					pc.sendPackets(new S_ServerMessage(127)); // \f1你不能够脱掉那个。
					return;
				}

				// 解除坚固防御
				if (type == 7) {
					if (pc.hasSkillEffect(SOLID_CARRIAGE)) {
						pc.removeSkillEffect(SOLID_CARRIAGE);
					}
				}
				pcInventory.setEquipped(armor, false);
			}
		}
		else {
			if (armor.getItem().getUseType() == 23) {
				pc.sendPackets(new S_ServerMessage(144)); // \f1你已经戴着二个戒指。
				return;
			}
			else {
				pc.sendPackets(new S_ServerMessage(124)); // \f1已经装备其他东西。
				return;
			}
		}

		pc.setCurrentHp(pc.getCurrentHp()); // 更新角色HP
		pc.setCurrentMp(pc.getCurrentMp()); // 更新角色MP
		pc.sendPackets(new S_OwnCharAttrDef(pc)); // 更新角色物理防御与四属性防御
		pc.sendPackets(new S_OwnCharStatus(pc)); // 更新角色属性与能力值
		pc.sendPackets(new S_SPMR(pc)); // 更新角色魔法攻击与魔法防御
	}

}
