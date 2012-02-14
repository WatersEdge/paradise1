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
package l1j.server.server.model;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.AREA_OF_SILENCE;
import static l1j.server.server.model.skill.L1SkillId.CANCELLATION;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_7_S;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MIRROR;
import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.DARK_BLIND;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.DISEASE;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FALL_DOWN;
import static l1j.server.server.model.skill.L1SkillId.ENTANGLE;
import static l1j.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.FINAL_BURN;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WALL;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BLIZZARD;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.GUARD_BRAKE;
import static l1j.server.server.model.skill.L1SkillId.HORROR_OF_DEATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_BASILISK;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_COCKATRICE;
import static l1j.server.server.model.skill.L1SkillId.ILLUSION_AVATAR;
import static l1j.server.server.model.skill.L1SkillId.IMMUNE_TO_HARM;
import static l1j.server.server.model.skill.L1SkillId.JOY_OF_PAIN;
import static l1j.server.server.model.skill.L1SkillId.MANA_DRAIN;
import static l1j.server.server.model.skill.L1SkillId.MASS_SLOW;
import static l1j.server.server.model.skill.L1SkillId.MIND_BREAK;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.RESIST_FEAR;
import static l1j.server.server.model.skill.L1SkillId.RETURN_TO_NATURE;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.SLOW;
import static l1j.server.server.model.skill.L1SkillId.STRIKER_GALE;
import static l1j.server.server.model.skill.L1SkillId.TAMING_MONSTER;
import static l1j.server.server.model.skill.L1SkillId.WEAKNESS;
import static l1j.server.server.model.skill.L1SkillId.WEAPON_BREAK;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHACKLE;
import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.WarTimeController;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1MagicDoll;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.Random;

/**
 * 魔法
 */
public class L1Magic {

	/** 判断类型 */
	private int _calcType;
	/** PC对PC */
	private final int PC_PC = 1;
	/** PC对NPC */
	private final int PC_NPC = 2;
	/** NPC对PC */
	private final int NPC_PC = 3;
	/** NPC对NPC */
	private final int NPC_NPC = 4;
	/** 目标 */
	private L1Character _target = null;
	/** 自身 */
	private L1PcInstance _pc = null;
	/** 目标PC */
	private L1PcInstance _targetPc = null;
	/** 怪物 */
	private L1NpcInstance _npc = null;
	/** 目标NPC */
	private L1NpcInstance _targetNpc = null;

	/**  */
	private int _leverage = 10; // 1/10倍で表现する。

	/** 拥有这些状态的, 不会受到伤害(无敌) */
	private static final int[] INVINCIBLE = { ABSOLUTE_BARRIER, ICE_LANCE, FREEZING_BLIZZARD, FREEZING_BREATH, EARTH_BIND, ICE_LANCE_COCKATRICE, ICE_LANCE_BASILISK };

	/**  */
	public L1Magic(final L1Character attacker, final L1Character target) {
		this._target = target;

		if (attacker instanceof L1PcInstance) {
			if (target instanceof L1PcInstance) {
				this._calcType = this.PC_PC;
				this._pc = (L1PcInstance) attacker;
				this._targetPc = (L1PcInstance) target;
			}
			else {
				this._calcType = this.PC_NPC;
				this._pc = (L1PcInstance) attacker;
				this._targetNpc = (L1NpcInstance) target;
			}
		}
		else {
			if (target instanceof L1PcInstance) {
				this._calcType = this.NPC_PC;
				this._npc = (L1NpcInstance) attacker;
				this._targetPc = (L1PcInstance) target;
			}
			else {
				this._calcType = this.NPC_NPC;
				this._npc = (L1NpcInstance) attacker;
				this._targetNpc = (L1NpcInstance) target;
			}
		}
	}

	/** ●●●● 治愈魔法回复量（对亡灵无伤害）算出 ●●●● */
	public int calcHealing(final int skillId) {
		final L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		final int dice = l1skills.getDamageDice();
		final int value = l1skills.getDamageValue();
		int magicDamage = 0;

		int magicBonus = this.getMagicBonus();
		if (magicBonus > 10) {
			magicBonus = 10;
		}

		final int diceCount = value + magicBonus;
		for (int i = 0; i < diceCount; i++) {
			magicDamage += (Random.nextInt(dice) + 1);
		}

		double alignmentRevision = 1.0;
		if (this.getLawful() > 0) {
			alignmentRevision += (this.getLawful() / 32768.0);
		}

		magicDamage *= alignmentRevision;

		magicDamage = (magicDamage * this.getLeverage()) / 10;

		return magicDamage;
	}

	/** ■■■■■■■■■■■■■■ 魔法伤害算出 ■■■■■■■■■■■■■■ */
	public int calcMagicDamage(final int skillId) {
		int damage = 0;

		// 检查无敌状态
		for (final int skillid : INVINCIBLE) {
			if (this._target.hasSkillEffect(skillid)) {
				return damage;
			}
		}

		if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
			damage = this.calcPcMagicDamage(skillId);
		}
		else if ((this._calcType == this.PC_NPC) || (this._calcType == this.NPC_NPC)) {
			damage = this.calcNpcMagicDamage(skillId);
		}

		if (skillId != JOY_OF_PAIN) { // 疼痛的欢愉无视魔免
			damage = this.calcMrDefense(damage);
		}

		return damage;
	}

	/** ●●●● ＮＰＣ 的火牢魔法伤害算出 ●●●● */
	public int calcNpcFireWallDamage() {
		int dmg = 0;
		final double attrDeffence = this.calcAttrResistance(L1Skills.ATTR_FIRE);
		final L1Skills l1skills = SkillsTable.getInstance().getTemplate(FIRE_WALL);
		dmg = (int) ((1.0 - attrDeffence) * l1skills.getDamageValue());

		if (this._targetNpc.hasSkillEffect(ICE_LANCE)) {
			dmg = 0;
		}
		if (this._targetNpc.hasSkillEffect(FREEZING_BLIZZARD)) {
			dmg = 0;
		}
		if (this._targetNpc.hasSkillEffect(FREEZING_BREATH)) {
			dmg = 0;
		}
		if (this._targetNpc.hasSkillEffect(EARTH_BIND)) {
			dmg = 0;
		}
		if (this._targetNpc.hasSkillEffect(ICE_LANCE_COCKATRICE)) {
			dmg = 0;
		}
		if (this._targetNpc.hasSkillEffect(ICE_LANCE_BASILISK)) {
			dmg = 0;
		}

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	/** ●●●● ＰＣ 的火牢魔法伤害算出 ●●●● */
	public int calcPcFireWallDamage() {
		int dmg = 0;
		final double attrDeffence = this.calcAttrResistance(L1Skills.ATTR_FIRE);
		final L1Skills l1skills = SkillsTable.getInstance().getTemplate(FIRE_WALL);
		dmg = (int) ((1.0 - attrDeffence) * l1skills.getDamageValue());

		if (this._targetPc.hasSkillEffect(ABSOLUTE_BARRIER)) {
			dmg = 0;
		}
		if (this._targetPc.hasSkillEffect(ICE_LANCE)) {
			dmg = 0;
		}
		if (this._targetPc.hasSkillEffect(FREEZING_BLIZZARD)) {
			dmg = 0;
		}
		if (this._targetPc.hasSkillEffect(FREEZING_BREATH)) {
			dmg = 0;
		}
		if (this._targetPc.hasSkillEffect(EARTH_BIND)) {
			dmg = 0;
		}
		if (this._targetPc.hasSkillEffect(ICE_LANCE_COCKATRICE)) {
			dmg = 0;
		}
		if (this._targetPc.hasSkillEffect(ICE_LANCE_BASILISK)) {
			dmg = 0;
		}

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	/* ■■■■■■■■■■■■■■ 成功判定 ■■■■■■■■■■■■■ */
	// ●●●● 确率系魔法の成功判定 ●●●●
	// 计算方法
	// 攻击方：LV + ((MagicBonus * 3) * 魔法固有系数)
	// 防御方：((LV / 2) + (MR * 3)) / 2
	// 攻击成功率：攻击方 - 防御方
	public boolean calcProbabilityMagic(final int skillId) {
		int probability = 0;
		boolean isSuccess = false;

		// 攻击者为GM 100%成功
		if ((this._pc != null) && this._pc.isGm()) {
			return true;
		}

		// 判断特定状态下才可攻击 NPC
		if ((this._calcType == this.PC_NPC) && (this._targetNpc != null)) {
			if (this._pc.isAttackMiss(this._pc, this._targetNpc.getNpcTemplate().get_npcId())) {
				return false;
			}
		}

		// 不能使用技能的区域
		if (!this.checkZone(skillId)) {
			return false;
		}

		// 魔法相消术
		if (skillId == CANCELLATION) {
			if ((this._calcType == this.PC_PC) && (this._pc != null) && (this._targetPc != null)) {
				// 对自身100%成功
				if (this._pc.getId() == this._targetPc.getId()) {
					return true;
				}
				// 同血盟100%成功
				if ((this._pc.getClanid() > 0) && (this._pc.getClanid() == this._targetPc.getClanid())) {
					return true;
				}
				// 同队伍100%成功
				if (this._pc.isInParty()) {
					if (this._pc.getParty().isMember(this._targetPc)) {
						return true;
					}
				}
				// 以外的场合、安全区内无效
				if ((this._pc.getZoneType() == 1) || (this._targetPc.getZoneType() == 1)) {
					return false;
				}
			}
			// 目标为NPC、使用者为NPC的场合100%成功
			if ((this._calcType == this.PC_NPC) || (this._calcType == this.NPC_PC) || (this._calcType == this.NPC_NPC)) {
				return true;
			}
		}

		// 大地屏障状态中坏物术、相消术 以外的魔法无效
		if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
			if (this._targetPc.hasSkillEffect(EARTH_BIND)) {
				if ((skillId != WEAPON_BREAK) && (skillId != CANCELLATION)) {
					return false;
				}
			}
		}
		else {
			if (this._targetNpc.hasSkillEffect(EARTH_BIND)) {
				if ((skillId != WEAPON_BREAK) && (skillId != CANCELLATION)) {
					return false;
				}
			}
		}

		probability = this.calcProbability(skillId);

		final int rnd = Random.nextInt(100) + 1;
		if (probability > 90) {
			probability = 90; // 最高90%的成功率。
		}

		if (probability >= rnd) {
			isSuccess = true;
		}
		else {
			isSuccess = false;
		}

		// 确率系魔法信息
		if (!Config.ALT_ATKMSG) {
			return isSuccess;
		}
		if (Config.ALT_ATKMSG) {
			if (((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) && !this._pc.isGm()) {
				return isSuccess;
			}
			if (((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) && !this._targetPc.isGm()) {
				return isSuccess;
			}
		}

		String msg0 = "";
		final String msg1 = " 施放魔法 ";
		String msg2 = "";
		String msg3 = "";
		String msg4 = "";

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) { // 攻击者为ＰＣ的场合
			msg0 = this._pc.getName() + " 对";
		}
		else if (this._calcType == this.NPC_PC) { // 攻击者为ＮＰＣ的场合
			msg0 = this._npc.getName();
		}

		msg2 = "，机率：" + probability + "%";
		if ((this._calcType == this.NPC_PC) || (this._calcType == this.PC_PC)) { // 目标为ＰＣ的场合
			msg4 = this._targetPc.getName();
		}
		else if (this._calcType == this.PC_NPC) { // 目标为ＮＰＣ的场合
			msg4 = this._targetNpc.getName();
		}
		if (isSuccess == true) {
			msg3 = "成功";
		}
		else {
			msg3 = "失败";
		}

		// 0 4 1 3 2 攻击者 对 目标 施放魔法 成功/失败，机率：X%。
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			this._pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4));
		}
		// 攻击者 施放魔法 成功/失败，机率：X%。
		else if ((this._calcType == this.NPC_PC)) {
			this._targetPc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3, null));
		}

		return isSuccess;
	}

	/** ■■■■■■■■■■■■■■■ 计算结果反映 ■■■■■■■■■■■■■■■ */

	public void commit(final int damage, final int drainMana) {
		if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
			this.commitPc(damage, drainMana);
		}
		else if ((this._calcType == this.PC_NPC) || (this._calcType == this.NPC_NPC)) {
			this.commitNpc(damage, drainMana);
		}

		// 伤害值及命中率确认
		if (!Config.ALT_ATKMSG) {
			return;
		}
		if (Config.ALT_ATKMSG) {
			if (((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) && !this._pc.isGm()) {
				return;
			}
			if ((this._calcType == this.NPC_PC) && !this._targetPc.isGm()) {
				return;
			}
		}

		String msg0 = "";
		final String msg1 = " 造成 ";
		String msg2 = "";
		String msg3 = "";
		String msg4 = "";

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {// 攻击者为ＰＣ的场合
			msg0 = "魔攻 对";
		}
		else if (this._calcType == this.NPC_PC) { // 攻击者为ＮＰＣ的场合
			msg0 = this._npc.getName() + "(魔攻)：";
		}

		if ((this._calcType == this.NPC_PC) || (this._calcType == this.PC_PC)) { // 目标为ＰＣ的场合
			msg4 = this._targetPc.getName();
			msg2 = "，剩余 " + this._targetPc.getCurrentHp();
		}
		else if (this._calcType == this.PC_NPC) { // 目标为ＮＰＣ的场合
			msg4 = this._targetNpc.getName();
			msg2 = "，剩余 " + this._targetNpc.getCurrentHp();
		}

		msg3 = damage + " 伤害";

		// 魔攻 对 目标 造成 X 伤害，剩余 Y。
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) { // 攻击者为ＰＣ的场合
			this._pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4)); // \f1%0%s %4%1%3 %2。
		}
		// 攻击者(魔攻)： X伤害，剩余 Y。
		else if ((this._calcType == this.NPC_PC)) { // 目标为ＰＣ的场合
			this._targetPc.sendPackets(new S_ServerMessage(166, msg0, null, msg2, msg3, null)); // \f1%0%s %4%1%3 %2。
		}
	}

	/**  */
	public void setLeverage(final int i) {
		this._leverage = i;
	}

	/**
	 * ●●●● 属性伤害减免 ●●●● <br>
	 * <br>
	 * attr:0.无属性魔法,1.地魔法,2.火魔法,4.水魔法,8.风魔法,16.光魔法
	 */
	private double calcAttrResistance(final int attr) {
		int resist = 0;
		if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
			if (attr == L1Skills.ATTR_EARTH) {
				resist = this._targetPc.getEarth();
			}
			else if (attr == L1Skills.ATTR_FIRE) {
				resist = this._targetPc.getFire();
			}
			else if (attr == L1Skills.ATTR_WATER) {
				resist = this._targetPc.getWater();
			}
			else if (attr == L1Skills.ATTR_WIND) {
				resist = this._targetPc.getWind();
			}
		}
		else if ((this._calcType == this.PC_NPC) || (this._calcType == this.NPC_NPC)) {
		}

		int resistFloor = (int) (0.32 * Math.abs(resist));
		if (resist >= 0) {
			resistFloor *= 1;
		}
		else {
			resistFloor *= -1;
		}

		final double attrDeffence = resistFloor / 32.0;

		return attrDeffence;
	}

	/** ●●●● damage_dice、damage_dice_count、damage_value、SP魔法伤害计算 ●●●● */
	private int calcMagicDiceDamage(final int skillId) {
		final L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		final int dice = l1skills.getDamageDice();
		final int diceCount = l1skills.getDamageDiceCount();
		final int value = l1skills.getDamageValue();
		int magicDamage = 0;
		int charaIntelligence = 0;

		for (int i = 0; i < diceCount; i++) {
			magicDamage += (Random.nextInt(dice) + 1);
		}
		magicDamage += value;

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			int weaponAddDmg = 0; // 武器追加伤害
			final L1ItemInstance weapon = this._pc.getWeapon();
			if (weapon != null) {
				weaponAddDmg = weapon.getItem().getMagicDmgModifier();
			}
			magicDamage += weaponAddDmg;
		}

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			final int spByItem = this._pc.getSp() - this._pc.getTrueSp(); // 于道具的SP变动
			charaIntelligence = this._pc.getInt() + spByItem - 12;
		}
		else if ((this._calcType == this.NPC_PC) || (this._calcType == this.NPC_NPC)) {
			final int spByItem = this._npc.getSp() - this._npc.getTrueSp(); // 于道具的SP变动
			charaIntelligence = this._npc.getInt() + spByItem - 12;
		}
		if (charaIntelligence < 1) {
			charaIntelligence = 1;
		}

		final double attrDeffence = this.calcAttrResistance(l1skills.getAttr());

		double coefficient = (1.0 - attrDeffence + charaIntelligence * 3.0 / 32.0);
		if (coefficient < 0) {
			coefficient = 0;
		}

		magicDamage *= coefficient;

		final double criticalCoefficient = 1.5; // 魔法クリティカル
		final int rnd = Random.nextInt(100) + 1;
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			if (l1skills.getSkillLevel() <= 6) {
				if (rnd <= (10 + this._pc.getOriginalMagicCritical())) {
					magicDamage *= criticalCoefficient;
				}
			}
		}

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) { // 原始INT的魔法伤害
			magicDamage += this._pc.getOriginalMagicDamage();
		}
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) { // 幻觉:化身 追加的伤害
			if (this._pc.hasSkillEffect(ILLUSION_AVATAR)) {
				magicDamage += 10;
			}
		}

		return magicDamage;
	}

	/** ●●●● ＭＲ伤害减免 ●●●● */
	private int calcMrDefense(int dmg) {

		final int mr = this.getTargetMr();
		double mrFloor = 0;

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			if (mr <= 100) {
				mrFloor = Math.floor((mr - this._pc.getOriginalMagicHit()) / 2);
			}
			else if (mr >= 100) {
				mrFloor = Math.floor((mr - this._pc.getOriginalMagicHit()) / 10);
			}
			double mrCoefficient = 0;
			if (mr <= 100) {
				mrCoefficient = 1 - 0.01 * mrFloor;
			}
			else if (mr >= 100) {
				mrCoefficient = 0.6 - 0.01 * mrFloor;
			}
			dmg *= mrCoefficient;
		}
		else if ((this._calcType == this.NPC_PC) || (this._calcType == this.NPC_NPC)) {
			final int rnd = Random.nextInt(100) + 1;
			if (mr >= rnd) {
				dmg /= 2;
			}
		}

		return dmg;
	}

	/** ●●●● 角色・ＮＰＣ对 ＮＰＣ 的伤害算出 ●●●● */
	private int calcNpcMagicDamage(final int skillId) {
		int dmg = 0;
		if (skillId == FINAL_BURN) {
			if (this._calcType == this.PC_NPC) {
				dmg = this._pc.getCurrentMp();
			}
			else {
				dmg = this._npc.getCurrentMp();
			}
		}
		else {
			dmg = this.calcMagicDiceDamage(skillId);
			dmg = (dmg * this.getLeverage()) / 10;
		}

		// 心灵破坏消耗目标5点MP造成5倍精神伤害
		if (skillId == MIND_BREAK) {
			if (this._targetNpc.getCurrentMp() >= 5) {
				this._targetNpc.setCurrentMp(this._targetNpc.getCurrentMp() - 5);
				if (this._calcType == this.PC_NPC) {
					dmg += this._pc.getWis() * 5;
				}
				else if (this._calcType == this.NPC_NPC) {
					dmg += this._npc.getWis() * 5;
				}
			}
		}

		// 疼痛的欢愉伤害：(最大血量 - 目前血量 /5)
		if (skillId == JOY_OF_PAIN) {
			int nowDamage = 0;
			if (this._calcType == this.PC_NPC) {
				nowDamage = this._pc.getMaxHp() - this._pc.getCurrentHp();
				if (nowDamage > 0) {
					dmg = nowDamage / 5;
				}
			}
			else if (this._calcType == this.NPC_NPC) {
				nowDamage = this._npc.getMaxHp() - this._npc.getCurrentHp();
				if (nowDamage > 0) {
					dmg = nowDamage / 5;
				}
			}
		}

		if (this._calcType == this.PC_NPC) { // プレイヤーからペット、サモンに攻击
			boolean isNowWar = false;
			final int castleId = L1CastleLocation.getCastleIdByArea(this._targetNpc);
			if (castleId > 0) {
				isNowWar = WarTimeController.getInstance().isNowWar(castleId);
			}
			if (!isNowWar) {
				if (this._targetNpc instanceof L1PetInstance) {
					dmg /= 8;
				}
				if (this._targetNpc instanceof L1SummonInstance) {
					final L1SummonInstance summon = (L1SummonInstance) this._targetNpc;
					if (summon.isExsistMaster()) {
						dmg /= 8;
					}
				}
			}
		}

		if (this._targetNpc.hasSkillEffect(ICE_LANCE)) {
			dmg = 0;
		}
		else if (this._targetNpc.hasSkillEffect(FREEZING_BLIZZARD)) {
			dmg = 0;
		}
		else if (this._targetNpc.hasSkillEffect(FREEZING_BREATH)) {
			dmg = 0;
		}
		else if (this._targetNpc.hasSkillEffect(EARTH_BIND)) {
			dmg = 0;
		}

		// 判断特定状态下才可攻击 NPC
		if ((this._calcType == this.PC_NPC) && (this._targetNpc != null)) {
			if (this._pc.isAttackMiss(this._pc, this._targetNpc.getNpcTemplate().get_npcId())) {
				dmg = 0;
			}
			// 吉尔塔斯反击屏障伤害判断 (PC_NPC)
			if (this._targetNpc.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_COUNTERATTACK_BARRIER) {
				this._pc.broadcastPacket(new S_DoActionGFX(this._pc.getId(), ActionCodes.ACTION_Damage));
				this._pc.receiveDamage(this._targetNpc, (dmg * 2), true);
				dmg = 0;
			}
		}
		if (this._calcType == this.NPC_NPC) {
			if (((this._npc != null) || (this._npc instanceof L1PetInstance) || (this._npc instanceof L1SummonInstance)) && ((this._targetNpc != null) || (this._targetNpc instanceof L1PetInstance) || (this._targetNpc instanceof L1SummonInstance))) {
				// 目标在安区、攻击者在安区
				if ((this._targetNpc.getZoneType() == 1) || (this._npc.getZoneType() == 1)) {
					dmg = 0;
				}
				// 吉尔塔斯反击屏障伤害判断 (NPC_NPC)
				if (this._targetNpc.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_COUNTERATTACK_BARRIER) {
					this._npc.broadcastPacket(new S_DoActionGFX(this._npc.getId(), ActionCodes.ACTION_Damage));
					this._npc.receiveDamage(this._targetNpc, (dmg * 2));
					dmg = 0;
				}
			}
		}

		return dmg;
	}

	/** ●●●● 角色・ＮＰＣ 从角色的魔法伤害算出 ●●●● */
	private int calcPcMagicDamage(final int skillId) {
		int dmg = 0;
		if (skillId == FINAL_BURN) {
			if (this._calcType == this.PC_PC) {
				dmg = this._pc.getCurrentMp();
			}
			else {
				dmg = this._npc.getCurrentMp();
			}
		}
		else {
			dmg = this.calcMagicDiceDamage(skillId);
			dmg = (dmg * this.getLeverage()) / 10;
		}

		// 心灵破坏消耗目标5点MP造成5倍精神伤害
		if (skillId == MIND_BREAK) {
			if (this._targetPc.getCurrentMp() >= 5) {
				this._targetPc.setCurrentMp(this._targetPc.getCurrentMp() - 5);
				if (this._calcType == this.PC_PC) {
					dmg += this._pc.getWis() * 5;
				}
				else if (this._calcType == this.NPC_PC) {
					dmg += this._npc.getWis() * 5;
				}
			}
		}

		dmg -= this._targetPc.getDamageReductionByArmor(); // 防具的伤害减免

		// 魔法娃娃效果 - 伤害减免
		dmg -= L1MagicDoll.getDamageReductionByDoll(this._targetPc);

		// 料理伤害减免
		if (this._targetPc.hasSkillEffect(COOKING_1_0_S) || this._targetPc.hasSkillEffect(COOKING_1_1_S) || this._targetPc.hasSkillEffect(COOKING_1_2_S) || this._targetPc.hasSkillEffect(COOKING_1_3_S) || this._targetPc.hasSkillEffect(COOKING_1_4_S)
				|| this._targetPc.hasSkillEffect(COOKING_1_5_S) || this._targetPc.hasSkillEffect(COOKING_1_6_S) || this._targetPc.hasSkillEffect(COOKING_2_0_S) || this._targetPc.hasSkillEffect(COOKING_2_1_S) || this._targetPc.hasSkillEffect(COOKING_2_2_S)
				|| this._targetPc.hasSkillEffect(COOKING_2_3_S) || this._targetPc.hasSkillEffect(COOKING_2_4_S) || this._targetPc.hasSkillEffect(COOKING_2_5_S) || this._targetPc.hasSkillEffect(COOKING_2_6_S) || this._targetPc.hasSkillEffect(COOKING_3_0_S)
				|| this._targetPc.hasSkillEffect(COOKING_3_1_S) || this._targetPc.hasSkillEffect(COOKING_3_2_S) || this._targetPc.hasSkillEffect(COOKING_3_3_S) || this._targetPc.hasSkillEffect(COOKING_3_4_S) || this._targetPc.hasSkillEffect(COOKING_3_5_S)
				|| this._targetPc.hasSkillEffect(COOKING_3_6_S)) {
			dmg -= 5;
		}

		// 特别的料理伤害减免
		if (this._targetPc.hasSkillEffect(COOKING_1_7_S) || this._targetPc.hasSkillEffect(COOKING_2_7_S) || this._targetPc.hasSkillEffect(COOKING_3_7_S)) {
			dmg -= 5;
		}

		if (this._targetPc.hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = this._targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}
		if (this._targetPc.hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 2;
		}

		if (this._targetPc.hasSkillEffect(PATIENCE)) {
			dmg -= 2;
		}

		if (this._calcType == this.NPC_PC) { // ペット、サモンからプレイヤーに攻击
			boolean isNowWar = false;
			final int castleId = L1CastleLocation.getCastleIdByArea(this._targetPc);
			if (castleId > 0) {
				isNowWar = WarTimeController.getInstance().isNowWar(castleId);
			}
			if (!isNowWar) {
				if (this._npc instanceof L1PetInstance) {
					dmg /= 8;
				}
				if (this._npc instanceof L1SummonInstance) {
					final L1SummonInstance summon = (L1SummonInstance) this._npc;
					if (summon.isExsistMaster()) {
						dmg /= 8;
					}
				}
			}
		}

		if (this._targetPc.hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg /= 2;
		}
		// 疼痛的欢愉伤害：(最大血量 - 目前血量 /5)
		if (skillId == JOY_OF_PAIN) {
			int nowDamage = 0;
			if (this._calcType == this.PC_PC) {
				nowDamage = this._pc.getMaxHp() - this._pc.getCurrentHp();
				if (nowDamage > 0) {
					dmg = nowDamage / 5;
				}
			}
			else if (this._calcType == this.NPC_PC) {
				nowDamage = this._npc.getMaxHp() - this._npc.getCurrentHp();
				if (nowDamage > 0) {
					dmg = nowDamage / 5;
				}
			}
		}

		if (this._targetPc.hasSkillEffect(ABSOLUTE_BARRIER)) {
			dmg = 0;
		}
		else if (this._targetPc.hasSkillEffect(ICE_LANCE)) {
			dmg = 0;
		}
		else if (this._targetPc.hasSkillEffect(FREEZING_BLIZZARD)) {
			dmg = 0;
		}
		else if (this._targetPc.hasSkillEffect(FREEZING_BREATH)) {
			dmg = 0;
		}
		else if (this._targetPc.hasSkillEffect(EARTH_BIND)) {
			dmg = 0;
		}

		if (this._calcType == this.NPC_PC) {
			if ((this._npc instanceof L1PetInstance) || (this._npc instanceof L1SummonInstance)) {
				// 目标在安区、攻击者在安区、NOPVP
				if ((this._targetPc.getZoneType() == 1) || (this._npc.getZoneType() == 1) || (this._targetPc.checkNonPvP(this._targetPc, this._npc))) {
					dmg = 0;
				}
			}
		}

		if (this._targetPc.hasSkillEffect(COUNTER_MIRROR)) {
			if (this._calcType == this.PC_PC) {
				if (this._targetPc.getWis() >= Random.nextInt(100)) {
					this._pc.sendPackets(new S_DoActionGFX(this._pc.getId(), ActionCodes.ACTION_Damage));
					this._pc.broadcastPacket(new S_DoActionGFX(this._pc.getId(), ActionCodes.ACTION_Damage));
					this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 4395));
					this._targetPc.broadcastPacket(new S_SkillSound(this._targetPc.getId(), 4395));
					this._pc.receiveDamage(this._targetPc, dmg, false);
					dmg = 0;
					this._targetPc.killSkillEffectTimer(COUNTER_MIRROR);
				}
			}
			else if (this._calcType == this.NPC_PC) {
				final int npcId = this._npc.getNpcTemplate().get_npcId();
				if ((npcId == 45681) || (npcId == 45682) || (npcId == 45683) || (npcId == 45684)) {
				}
				else if (!this._npc.getNpcTemplate().get_IsErase()) {
				}
				else {
					if (this._targetPc.getWis() >= Random.nextInt(100)) {
						this._npc.broadcastPacket(new S_DoActionGFX(this._npc.getId(), ActionCodes.ACTION_Damage));
						this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 4395));
						this._targetPc.broadcastPacket(new S_SkillSound(this._targetPc.getId(), 4395));
						this._npc.receiveDamage(this._targetPc, dmg);
						dmg = 0;
						this._targetPc.killSkillEffectTimer(COUNTER_MIRROR);
					}
				}
			}
		}

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	/** 检查概率 */
	private int calcProbability(final int skillId) {
		final L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int attackLevel = 0;
		int defenseLevel = 0;
		int probability = 0;

		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			attackLevel = this._pc.getLevel();
		}
		else {
			attackLevel = this._npc.getLevel();
		}

		if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
			defenseLevel = this._targetPc.getLevel();
		}
		else {
			defenseLevel = this._targetNpc.getLevel();
			if (skillId == RETURN_TO_NATURE) {
				if (this._targetNpc instanceof L1SummonInstance) {
					final L1SummonInstance summon = (L1SummonInstance) this._targetNpc;
					defenseLevel = summon.getMaster().getLevel();
				}
			}
		}

		if ((skillId == ELEMENTAL_FALL_DOWN) || (skillId == RETURN_TO_NATURE) || (skillId == ENTANGLE) || (skillId == ERASE_MAGIC) || (skillId == AREA_OF_SILENCE) || (skillId == WIND_SHACKLE) || (skillId == STRIKER_GALE) || (skillId == POLLUTE_WATER) || (skillId == EARTH_BIND)) {
			// 成功率是 魔法固有系数 × LV差异 + 基本概率
			probability = (int) (((l1skills.getProbabilityDice()) / 10D) * (attackLevel - defenseLevel)) + l1skills.getProbabilityValue();

			// 原始INT的魔法命中
			if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
				probability += 2 * this._pc.getOriginalMagicHit();
			}
		}
		else if (skillId == SHOCK_STUN) {
			// 成功率是 基本概率 + LV每差一级+-2%
			probability = l1skills.getProbabilityValue() + (attackLevel - defenseLevel) * 2;

			// 原始INT的魔法命中
			if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
				probability += 2 * this._pc.getOriginalMagicHit();
			}
		}
		else if (skillId == COUNTER_BARRIER) {
			// 成功率是 基本概率 + LV每差一级+-1%
			probability = l1skills.getProbabilityValue() + attackLevel - defenseLevel;

			// 原始INT的魔法命中
			if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
				probability += 2 * this._pc.getOriginalMagicHit();
			}
		}
		else if ((skillId == GUARD_BRAKE) || (skillId == RESIST_FEAR) || (skillId == HORROR_OF_DEATH)) {
			final int dice = l1skills.getProbabilityDice();
			final int value = l1skills.getProbabilityValue();
			int diceCount = 0;
			diceCount = this.getMagicBonus() + this.getMagicLevel();

			if (diceCount < 1) {
				diceCount = 1;
			}

			for (int i = 0; i < diceCount; i++) {
				probability += (Random.nextInt(dice) + 1 + value);
			}

			probability = probability * this.getLeverage() / 10;

			// 原始INT的魔法命中
			if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
				probability += 2 * this._pc.getOriginalMagicHit();
			}

			if (probability >= this.getTargetMr()) {
				probability = 100;
			}
			else {
				probability = 0;
			}
		}
		else {
			final int dice = l1skills.getProbabilityDice();
			int diceCount = 0;
			if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
				if (this._pc.isWizard()) {
					diceCount = this.getMagicBonus() + this.getMagicLevel() + 1;
				}
				else if (this._pc.isElf()) {
					diceCount = this.getMagicBonus() + this.getMagicLevel() - 1;
				}
				else {
					diceCount = this.getMagicBonus() + this.getMagicLevel() - 1;
				}
			}
			else {
				diceCount = this.getMagicBonus() + this.getMagicLevel();
			}
			if (diceCount < 1) {
				diceCount = 1;
			}

			for (int i = 0; i < diceCount; i++) {
				probability += (Random.nextInt(dice) + 1);
			}
			probability = probability * this.getLeverage() / 10;

			// 原始INT的魔法命中
			if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
				probability += 2 * this._pc.getOriginalMagicHit();
			}

			probability -= this.getTargetMr();

			if (skillId == TAMING_MONSTER) {
				double probabilityRevision = 1;
				if ((this._targetNpc.getMaxHp() * 1 / 4) > this._targetNpc.getCurrentHp()) {
					probabilityRevision = 1.3;
				}
				else if ((this._targetNpc.getMaxHp() * 2 / 4) > this._targetNpc.getCurrentHp()) {
					probabilityRevision = 1.2;
				}
				else if ((this._targetNpc.getMaxHp() * 3 / 4) > this._targetNpc.getCurrentHp()) {
					probabilityRevision = 1.1;
				}
				probability *= probabilityRevision;
			}
		}

		// 对异常状态的耐性
		if (skillId == EARTH_BIND) {
			if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
				probability -= this._targetPc.getRegistSustain();
			}
		}
		else if (skillId == SHOCK_STUN) {
			if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
				probability -= 2 * this._targetPc.getRegistStun();
			}
		}
		else if (skillId == CURSE_PARALYZE) {
			if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
				probability -= this._targetPc.getRegistStone();
			}
		}
		else if (skillId == FOG_OF_SLEEPING) {
			if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
				probability -= this._targetPc.getRegistSleep();
			}
		}
		else if ((skillId == ICE_LANCE) || (skillId == FREEZING_BLIZZARD) || (skillId == FREEZING_BREATH) || (skillId == ICE_LANCE_COCKATRICE) || (skillId == ICE_LANCE_BASILISK)) {
			if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
				probability -= this._targetPc.getRegistFreeze();
				// 检查无敌状态
				for (final int skillid : INVINCIBLE) {
					if (this._targetPc.hasSkillEffect(skillid)) {
						probability = 0;
						break;
					}
				}
			}
		}
		else if ((skillId == CURSE_BLIND) || (skillId == DARKNESS) || (skillId == DARK_BLIND)) {
			if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
				probability -= this._targetPc.getRegistBlind();
			}
		}

		return probability;
	}

	/** 检查区域 */
	private boolean checkZone(final int skillId) {
		if ((this._pc != null) && (this._targetPc != null)) {

			// 安全区域无效果的魔法
			if ((this._pc.getZoneType() == 1) || (this._targetPc.getZoneType() == 1)) { // 安全区
				if ((skillId == WEAPON_BREAK) || (skillId == SLOW) || (skillId == CURSE_PARALYZE) || (skillId == MANA_DRAIN) || (skillId == DARKNESS) || (skillId == WEAKNESS) || (skillId == DISEASE) || (skillId == DECAY_POTION) || (skillId == MASS_SLOW) || (skillId == ENTANGLE)
						|| (skillId == ERASE_MAGIC) || (skillId == EARTH_BIND) || (skillId == AREA_OF_SILENCE) || (skillId == WIND_SHACKLE) || (skillId == STRIKER_GALE) || (skillId == SHOCK_STUN) || (skillId == FOG_OF_SLEEPING) || (skillId == ICE_LANCE)
						|| (skillId == FREEZING_BLIZZARD) || (skillId == FREEZING_BREATH) || (skillId == POLLUTE_WATER) || (skillId == ELEMENTAL_FALL_DOWN) || (skillId == RETURN_TO_NATURE) || (skillId == ICE_LANCE_COCKATRICE) || (skillId == ICE_LANCE_BASILISK)) {
					return false;
				}
			}
		}
		return true;
	}

	/** ●●●● ＮＰＣ计算结果反映 ●●●● */
	private void commitNpc(final int damage, final int drainMana) {
		if (this._calcType == this.PC_NPC) {
			if (drainMana > 0) {
				final int drainValue = this._targetNpc.drainMana(drainMana);
				final int newMp = this._pc.getCurrentMp() + drainValue;
				this._pc.setCurrentMp(newMp);
			}
			this._targetNpc.ReceiveManaDamage(this._pc, drainMana);
			this._targetNpc.receiveDamage(this._pc, damage);
		}
		else if (this._calcType == this.NPC_NPC) {
			this._targetNpc.receiveDamage(this._npc, damage);
		}
	}

	/** ●●●● 玩家计算结果反映 ●●●● */
	private void commitPc(final int damage, int drainMana) {
		if (this._calcType == this.PC_PC) {
			if ((drainMana > 0) && (this._targetPc.getCurrentMp() > 0)) {
				if (drainMana > this._targetPc.getCurrentMp()) {
					drainMana = this._targetPc.getCurrentMp();
				}
				final int newMp = this._pc.getCurrentMp() + drainMana;
				this._pc.setCurrentMp(newMp);
			}
			this._targetPc.receiveManaDamage(this._pc, drainMana);
			this._targetPc.receiveDamage(this._pc, damage, true);
		}
		else if (this._calcType == this.NPC_PC) {
			this._targetPc.receiveDamage(this._npc, damage, true);
		}
	}

	/** 取得正义值 */
	private int getLawful() {
		int lawful = 0;
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			lawful = this._pc.getLawful();
		}
		else if ((this._calcType == this.NPC_PC) || (this._calcType == this.NPC_NPC)) {
			lawful = this._npc.getLawful();
		}
		return lawful;
	}

	/**  */
	private int getLeverage() {
		return this._leverage;
	}

	/** 取得魔法奖励 */
	private int getMagicBonus() {
		int magicBonus = 0;
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			magicBonus = this._pc.getMagicBonus();
		}
		else if ((this._calcType == this.NPC_PC) || (this._calcType == this.NPC_NPC)) {
			magicBonus = this._npc.getMagicBonus();
		}
		return magicBonus;
	}

	/** 取得魔法等级 */
	private int getMagicLevel() {
		int magicLevel = 0;
		if ((this._calcType == this.PC_PC) || (this._calcType == this.PC_NPC)) {
			magicLevel = this._pc.getMagicLevel();
		}
		else if ((this._calcType == this.NPC_PC) || (this._calcType == this.NPC_NPC)) {
			magicLevel = this._npc.getMagicLevel();
		}
		return magicLevel;
	}

	/** 取得目标的魔防 */
	private int getTargetMr() {
		int mr = 0;
		if ((this._calcType == this.PC_PC) || (this._calcType == this.NPC_PC)) {
			mr = this._targetPc.getMr();
		}
		else {
			mr = this._targetNpc.getMr();
		}
		return mr;
	}
}
