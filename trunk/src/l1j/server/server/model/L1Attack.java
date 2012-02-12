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
import static l1j.server.server.model.skill.L1SkillId.BERSERKERS;
import static l1j.server.server.model.skill.L1SkillId.BONE_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BONE_BREAK_START;
import static l1j.server.server.model.skill.L1SkillId.BOUNCE_ATTACK;
import static l1j.server.server.model.skill.L1SkillId.BURNING_SLASH;
import static l1j.server.server.model.skill.L1SkillId.BURNING_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BURNING_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_0_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_3_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_0_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_2_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_7_S;
import static l1j.server.server.model.skill.L1SkillId.DOUBLE_BRAKE;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FIRE;
import static l1j.server.server.model.skill.L1SkillId.ENCHANT_VENOM;
import static l1j.server.server.model.skill.L1SkillId.FIRE_BLESS;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BLIZZARD;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_BASILISK;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE_COCKATRICE;
import static l1j.server.server.model.skill.L1SkillId.IMMUNE_TO_HARM;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.SMASH;
import static l1j.server.server.model.skill.L1SkillId.SOUL_OF_FLAME;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_EFFECT_WEAKNESS_LV1;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_EFFECT_WEAKNESS_LV2;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_EFFECT_WEAKNESS_LV3;
import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.WarTimeController;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.gametime.L1GameTimeClock;
import l1j.server.server.model.npc.action.L1NpcDefaultAction;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.poison.L1ParalysisPoison;
import l1j.server.server.model.poison.L1SilencePoison;
import l1j.server.server.serverpackets.S_AttackMissPacket;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_UseArrowSkill;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1MagicDoll;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.types.Point;
import l1j.server.server.utils.Random;

/**
 * 攻击
 */
public class L1Attack {
	/** 自身 */
	private L1PcInstance _pc = null;
	/** 目标 */
	private L1Character _target = null;
	/** 目标为 PC */
	private L1PcInstance _targetPc = null;
	/** 怪物 */
	private L1NpcInstance _npc = null;
	/** 目标为 NPC */
	private L1NpcInstance _targetNpc = null;
	/** 目标 ID */
	private final int _targetId;
	/** 目标的 X 坐标 */
	private final int _targetX;
	/** 目标的 Y 坐标 */
	private final int _targetY;
	/** 伤害状况 */
	private int _statusDamage = 0;
	/** 命中率 */
	private int _hitRate = 0;
	/** 状态 (谁攻击谁) */
	private int _calcType;
	/** PC 对 PC */
	private static final int PC_PC = 1;
	/** PC 对 NPC */
	private static final int PC_NPC = 2;
	/** NPC 对 PC */
	private static final int NPC_PC = 3;
	/** NPC 对 NPC */
	private static final int NPC_NPC = 4;
	/** 命中 */
	private boolean _isHit = false;
	/** 伤害 */
	private int _damage = 0;
	/** 吸取魔力 */
	private int _drainMana = 0;
	/** 吸取体力 */
	private int _drainHp = 0;
	/** 效果 ID */
	private byte _effectId = 0;
	/** 攻击图像 ID */
	private int _attckGrfxId = 0;
	/** 攻击动作 ID */
	private int _attckActId = 0;

	/** 攻击者がプレイヤーの场合の武器情报 */
	private L1ItemInstance weapon = null;
	/** 武器编号 */
	private int _weaponId = 0;
	/** 武器种类 */
	private int _weaponType = 0;
	/** 武器种类2 */
	private int _weaponType2 = 0;
	/** 武器的额外命中 */
	private int _weaponAddHit = 0;
	/** 武器的额外伤害 */
	private int _weaponAddDmg = 0;
	/** 武器对小怪的伤害 */
	private int _weaponSmall = 0;
	/** 武器对大怪的伤害 */
	private int _weaponLarge = 0;
	/** 武器攻击范围 */
	private int _weaponRange = 1;
	/** 武器的状态 (祝福、普通、诅咒) */
	private int _weaponBless = 1;
	/** 武器加成 */
	private int _weaponEnchant = 0;
	/** 武器的材质 (23种) */
	private int _weaponMaterial = 0;
	/** 双手武器伤害几率 */
	private int _weaponDoubleDmgChance = 0;
	/** 武器的属性种类 */
	private int _weaponAttrEnchantKind = 0;
	/** 武器的属性等级 */
	private int _weaponAttrEnchantLevel = 0;
	/** 箭 */
	private L1ItemInstance _arrow = null;
	/** 飞刀 */
	private L1ItemInstance _sting = null;

	/** 技能 ID */
	private final int _skillId;

	@SuppressWarnings("unused")
	private double _skillDamage = 0;

	private int _leverage = 10; // 1/10倍で表现する。

	public void setLeverage(int i) {
		_leverage = i;
	}

	private int getLeverage() {
		return _leverage;
	}

	/** STR命中补正 */
	private static final int[] strHit = { -2, -2, -2, -2, -2, -2, -2, // 1～7まで
			-2, -1, -1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, // 8～26まで
			7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 12, // 27～44まで
			13, 13, 13, 14, 14, 14, 15, 15, 15, 16, 16, 16, 17, 17, 17 }; // 45～59まで

	/** DEX命中补正 */
	private static final int[] dexHit = { -2, -2, -2, -2, -2, -2, -1, -1, 0, 0, // 1～10まで
			1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, // 11～30まで
			17, 18, 19, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 22, 23, // 31～45まで
			23, 23, 24, 24, 24, 25, 25, 25, 26, 26, 26, 27, 27, 27, 28 }; // 46～60まで

	/** STR伤害补正 */
	private static final int[] strDmg = new int[128];

	static {
		// STR伤害补正
		int dmg = -6;
		for (int str = 0; str <= 22; str++) { // 0～22每两点+1伤害
			if (str % 2 == 1) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
		for (int str = 23; str <= 28; str++) { // 23～28每三点+1伤害
			if (str % 3 == 2) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
		for (int str = 29; str <= 32; str++) { // 29～32每两点+1伤害
			if (str % 2 == 1) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
		for (int str = 33; str <= 34; str++) { // 33～34每一点+1伤害
			dmg++;
			strDmg[str] = dmg;
		}
		for (int str = 35; str <= 127; str++) { // 35～127每四点+1伤害
			if (str % 4 == 1) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
	}

	/** DEX伤害补正 */
	private static final int[] dexDmg = new int[128];

	static {
		// DEX伤害补正
		for (int dex = 0; dex <= 14; dex++) {
			// 0～14点 伤害0
			dexDmg[dex] = 0;
		}
		dexDmg[15] = 1;
		dexDmg[16] = 2;
		dexDmg[17] = 3;
		dexDmg[18] = 4;
		dexDmg[19] = 4;
		dexDmg[20] = 4;
		dexDmg[21] = 5;
		dexDmg[22] = 5;
		dexDmg[23] = 5;
		int dmg = 5;
		for (int dex = 24; dex <= 35; dex++) { // 24～35每三点+1伤害
			if (dex % 3 == 1) {
				dmg++;
			}
			dexDmg[dex] = dmg;
		}
		for (int dex = 36; dex <= 127; dex++) { // 36～127每四点+1伤害
			if (dex % 4 == 1) {
				dmg++;
			}
			dexDmg[dex] = dmg;
		}
	}

	/** 设定动作ID */
	public void setActId(int actId) {
		_attckActId = actId;
	}

	/** 设定动画ID */
	public void setGfxId(int gfxId) {
		_attckGrfxId = gfxId;
	}

	/** 取得动作ID */
	public int getActId() {
		return _attckActId;
	}

	/** 取得动画ID */
	public int getGfxId() {
		return _attckGrfxId;
	}

	public L1Attack(L1Character attacker, L1Character target) {
		this(attacker, target, 0);
	}

	public L1Attack(L1Character attacker, L1Character target, int skillId) {
		_skillId = skillId;
		if (_skillId != 0) {
			L1Skills skills = SkillsTable.getInstance().getTemplate(_skillId);
			_skillDamage = skills.getDamageValue();
		}
		if (attacker instanceof L1PcInstance) {
			_pc = (L1PcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = PC_PC;
			}
			else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = PC_NPC;
			}
			// 武器情报の取得
			weapon = _pc.getWeapon();
			if (weapon != null) {
				_weaponId = weapon.getItem().getItemId();
				_weaponType = weapon.getItem().getType1();
				_weaponType2 = weapon.getItem().getType();
				_weaponAddHit = weapon.getItem().getHitModifier() + weapon.getHitByMagic();
				_weaponAddDmg = weapon.getItem().getDmgModifier() + weapon.getDmgByMagic();
				_weaponSmall = weapon.getItem().getDmgSmall();
				_weaponLarge = weapon.getItem().getDmgLarge();
				_weaponRange = weapon.getItem().getRange();
				_weaponBless = weapon.getItem().getBless();
				_weaponEnchant = weapon.getEnchantLevel();
				_weaponMaterial = weapon.getItem().getMaterial();
				_statusDamage = dexDmg[_pc.getDex()]; // 伤害预设用敏捷补正

				if (_weaponType == 20) { // 弓箭
					_arrow = _pc.getInventory().getArrow();
					if (_arrow != null) { // 有箭
						_weaponBless = _arrow.getItem().getBless();
						_weaponMaterial = _arrow.getItem().getMaterial();
					}
				}
				else if (_weaponType == 62) { // 铁手甲
					_sting = _pc.getInventory().getSting();
					if (_sting != null) { // 有飞刀
						_weaponBless = _sting.getItem().getBless();
						_weaponMaterial = _sting.getItem().getMaterial();
					}
				}
				else { // 近战类武器
					_weaponEnchant = weapon.getEnchantLevel() - weapon.get_durability(); // 计算武器损伤
					_statusDamage = strDmg[_pc.getStr()]; // 伤害用力量补正
				}
				_weaponDoubleDmgChance = weapon.getItem().getDoubleDmgChance();
				_weaponAttrEnchantKind = weapon.getAttrEnchantKind();
				_weaponAttrEnchantLevel = weapon.getAttrEnchantLevel();
			}
		}
		else if (attacker instanceof L1NpcInstance) {
			_npc = (L1NpcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = NPC_PC;
			}
			else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = NPC_NPC;
			}
		}
		_target = target;
		_targetId = target.getId();
		_targetX = target.getX();
		_targetY = target.getY();
	}

	/* ■■■■■■■■■■■■■■■■ 命中判定 ■■■■■■■■■■■■■■■■ */

	/** 拥有这些状态的, 不会受到伤害(无敌) */
	private static final int[] INVINCIBLE = { ABSOLUTE_BARRIER, // 绝对屏障
			ICE_LANCE, // 冰矛围篱
			FREEZING_BLIZZARD, // 冰雪飓风
			FREEZING_BREATH, // 寒冰喷吐
			EARTH_BIND, // 大地屏障
			ICE_LANCE_COCKATRICE,// 亚力安冰矛围篱
			ICE_LANCE_BASILISK // 邪恶蜥蜴冰矛围篱
	};

	/** 检查命中 */
	public boolean calcHit() {
		// 检查无敌状态
		for (int skillId : INVINCIBLE) {
			if (_target.hasSkillEffect(skillId)) {
				_isHit = false;
				return _isHit;
			}
		}

		if ((_calcType == PC_PC) || (_calcType == PC_NPC)) {
			if (_weaponRange != -1) {
				if (_pc.getLocation().getTileLineDistance(_target.getLocation()) > _weaponRange + 1) { // BIGのモンスターに对应するため射程范围+1
					_isHit = false; // 射程范围外
					return _isHit;
				}
			}
			else {
				if (!_pc.getLocation().isInScreen(_target.getLocation())) {
					_isHit = false; // 射程范围外
					return _isHit;
				}
			}
			if ((_weaponType == 20) && (_weaponId != 190) && (_arrow == null)) {
				_isHit = false; // 没有箭
			}
			else if ((_weaponType == 62) && (_sting == null)) {
				_isHit = false; // 没有飞刀
			}
			else if (_weaponRange != 1 && !_pc.glanceCheck(_targetX, _targetY)) {
				_isHit = false; // 两格以上武器 直线距离上有障碍物
			}
			else if ((_weaponId == 247) || (_weaponId == 248) || (_weaponId == 249)) {
				_isHit = false; // 试练之剑B～C 攻击无效
			}
			else if (_calcType == PC_PC) {
				_isHit = calcPcPcHit();
			}
			else if (_calcType == PC_NPC) {
				_isHit = calcPcNpcHit();
			}
		}
		else if (_calcType == NPC_PC) {
			_isHit = calcNpcPcHit();
		}
		else if (_calcType == NPC_NPC) {
			_isHit = calcNpcNpcHit();
		}
		return _isHit;
	}

	/** 近距离命中率 */
	private int calShortRageHit(int hitRate) {
		int shortHit = hitRate + _pc.getHitup() + _pc.getOriginalHitup();
		// 防具增加命中
		shortHit += _pc.getHitModifierByArmor();

		if (_pc.hasSkillEffect(COOKING_2_0_N) // 料理追加命中
				|| _pc.hasSkillEffect(COOKING_2_0_S))
			shortHit += 1;
		if (_pc.hasSkillEffect(COOKING_3_2_N) // 料理追加命中
				|| _pc.hasSkillEffect(COOKING_3_2_S))
			shortHit += 2;
		return shortHit;
	}

	/** 远距离命中率 */
	private int calLongRageHit(int hitRate) {
		int longHit = hitRate + _pc.getBowHitup() + _pc.getOriginalBowHitup();
		// 防具增加命中
		longHit += _pc.getBowHitModifierByArmor();

		if (_pc.hasSkillEffect(COOKING_2_3_N) // 料理追加命中
				|| _pc.hasSkillEffect(COOKING_2_3_S) || _pc.hasSkillEffect(COOKING_3_0_N) || _pc.hasSkillEffect(COOKING_3_0_S))
			longHit += 1;
		return longHit;
	}

	/**
	 * ●●●● PC对PC的命中判定 ●●●● <br>
	 * <br>
	 * ＰＣへの命中率 ＝（PCのLv＋クラス补正＋STR补正＋DEX补正＋武器补正＋DAIの枚数/2＋魔法补正）×0.68－10<br>
	 * これで算出された数值は自分が最大命中(95%)を与える事のできる相手侧PCのAC そこから相手侧PCのACが1良くなる每に自命中率から1引いていく<br>
	 * 最小命中率5% 最大命中率95%
	 */
	private boolean calcPcPcHit() {
		_hitRate = _pc.getLevel();

		if (_pc.getStr() > 59) {
			_hitRate += strHit[58];
		}
		else {
			_hitRate += strHit[_pc.getStr() - 1];
		}

		if (_pc.getDex() > 60) {
			_hitRate += dexHit[59];
		}
		else {
			_hitRate += dexHit[_pc.getDex() - 1];
		}

		// 命中计算 与魔法、食物buff
		_hitRate += _weaponAddHit + (_weaponEnchant / 2);
		if (_weaponType == 20 || _weaponType == 62)
			_hitRate = calLongRageHit(_hitRate);
		else
			_hitRate = calShortRageHit(_hitRate);

		// 负重命中补正
		if ((80 < _pc.getInventory().getWeight242()) && (121 >= _pc.getInventory().getWeight242())) {
			_hitRate -= 1;
		}
		else if ((122 <= _pc.getInventory().getWeight242()) && (160 >= _pc.getInventory().getWeight242())) {
			_hitRate -= 3;
		}
		else if ((161 <= _pc.getInventory().getWeight242()) && (200 >= _pc.getInventory().getWeight242())) {
			_hitRate -= 5;
		}

		int attackerDice = Random.nextInt(20) + 1 + _hitRate - 10;

		// 闪避率
		attackerDice -= _targetPc.getDodge();
		attackerDice += _targetPc.getNdodge();

		int defenderDice = 0;

		int defenderValue = (int) (_targetPc.getAc() * 1.5) * -1;

		if (_targetPc.getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAc();
		}
		else if (_targetPc.getAc() < 0) {
			defenderDice = 10 + Random.nextInt(defenderValue) + 1;
		}

		int fumble = _hitRate - 9;
		int critical = _hitRate + 10;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		}
		else if (attackerDice >= critical) {
			_hitRate = 100;
		}
		else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			}
			else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}

		// 奇古兽命中率100%
		if (_weaponType2 == 17 || _weaponType2 == 19) {
			_hitRate = 100;
		}

		// TODO 魔法娃娃效果 - 伤害回避
		else if (L1MagicDoll.getDamageEvasionByDoll(_targetPc) > 0) {
			_hitRate = 0;
		}

		int rnd = Random.nextInt(100) + 1;
		if ((_weaponType == 20) && (_hitRate > rnd)) { // 弓の场合、ヒットした场合でもERでの回避を再度行う。
			return calcErEvasion();
		}

		return _hitRate >= rnd;

	}

	/** ●●●● PC 对 NPC 的命中判定 ●●●● */
	private boolean calcPcNpcHit() {
		// ＮＰＣ的命中率
		// ＝（PC的Lv＋クラス补正＋STR补正＋DEX补正＋武器补正＋DAIの枚数/2＋魔法补正）×5－{NPCのAC×（-5）}
		_hitRate = _pc.getLevel();

		if (_pc.getStr() > 59) {
			_hitRate += strHit[58];
		}
		else {
			_hitRate += strHit[_pc.getStr() - 1];
		}

		if (_pc.getDex() > 60) {
			_hitRate += dexHit[59];
		}
		else {
			_hitRate += dexHit[_pc.getDex() - 1];
		}

		// 命中计算 与魔法、食物buff
		_hitRate += _weaponAddHit + (_weaponEnchant / 2);
		if (_weaponType == 20 || _weaponType == 62)
			_hitRate = calLongRageHit(_hitRate);
		else
			_hitRate = calShortRageHit(_hitRate);

		// 负重命中补正
		if ((80 < _pc.getInventory().getWeight242()) && (121 >= _pc.getInventory().getWeight242())) {
			_hitRate -= 1;
		}
		else if ((122 <= _pc.getInventory().getWeight242()) && (160 >= _pc.getInventory().getWeight242())) {
			_hitRate -= 3;
		}
		else if ((161 <= _pc.getInventory().getWeight242()) && (200 >= _pc.getInventory().getWeight242())) {
			_hitRate -= 5;
		}

		int attackerDice = Random.nextInt(20) + 1 + _hitRate - 10;

		// 闪避率
		attackerDice -= _targetNpc.getDodge();
		attackerDice += _targetNpc.getNdodge();

		int defenderDice = 10 - _targetNpc.getAc();

		int fumble = _hitRate - 9;
		int critical = _hitRate + 10;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		}
		else if (attackerDice >= critical) {
			_hitRate = 100;
		}
		else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			}
			else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}

		// 奇古兽 命中率 100%
		if (_weaponType2 == 17 || _weaponType2 == 19) {
			_hitRate = 100;
		}

		// 特定状态下才可攻击 NPC
		if (_pc.isAttackMiss(_pc, _targetNpc.getNpcTemplate().get_npcId())) {
			_hitRate = 0;
		}

		int rnd = Random.nextInt(100) + 1;

		return _hitRate >= rnd;
	}

	/** ●●●● NPC 对 PC 的命中判定 ●●●● */
	private boolean calcNpcPcHit() {

		_hitRate += _npc.getLevel();

		if (_npc instanceof L1PetInstance) { // 宠物的武器追加命中
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		_hitRate += _npc.getHitup();

		int attackerDice = Random.nextInt(20) + 1 + _hitRate - 1;

		// 闪避率
		attackerDice -= _targetPc.getDodge();
		attackerDice += _targetPc.getNdodge();

		int defenderDice = 0;

		int defenderValue = (_targetPc.getAc()) * -1;

		if (_targetPc.getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAc();
		}
		else if (_targetPc.getAc() < 0) {
			defenderDice = 10 + Random.nextInt(defenderValue) + 1;
		}

		int fumble = _hitRate;
		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		}
		else if (attackerDice >= critical) {
			_hitRate = 100;
		}
		else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			}
			else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}

		if ((_npc instanceof L1PetInstance) || (_npc instanceof L1SummonInstance)) {
			// 目标在安区、攻击者在安区、NOPVP
			if ((_targetPc.getZoneType() == 1) || (_npc.getZoneType() == 1) || (_targetPc.checkNonPvP(_targetPc, _npc))) {
				_hitRate = 0;
			}
		}
		// TODO 魔法娃娃效果 - 伤害回避
		else if (L1MagicDoll.getDamageEvasionByDoll(_targetPc) > 0) {
			_hitRate = 0;
		}

		int rnd = Random.nextInt(100) + 1;

		// NPC的攻击范围10以上的场合、2以上距离的场合视为弓攻击
		if ((_npc.getAtkRanged() >= 10) && (_hitRate > rnd) && (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) >= 2)) {
			return calcErEvasion();
		}
		return _hitRate >= rnd;
	}

	/** ●●●● NPC 对 NPC 的命中判定 ●●●● */
	private boolean calcNpcNpcHit() {

		_hitRate += _npc.getLevel();

		if (_npc instanceof L1PetInstance) { // 宠物的武器追加命中
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		_hitRate += _npc.getHitup();

		int attackerDice = Random.nextInt(20) + 1 + _hitRate - 1;

		// 闪避率
		attackerDice -= _targetNpc.getDodge();
		attackerDice += _targetNpc.getNdodge();

		int defenderDice = 0;

		int defenderValue = (_targetNpc.getAc()) * -1;

		if (_targetNpc.getAc() >= 0) {
			defenderDice = 10 - _targetNpc.getAc();
		}
		else if (_targetNpc.getAc() < 0) {
			defenderDice = 10 + Random.nextInt(defenderValue) + 1;
		}

		int fumble = _hitRate;
		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		}
		else if (attackerDice >= critical) {
			_hitRate = 100;
		}
		else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			}
			else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}
		if (((_npc instanceof L1PetInstance) || (_npc instanceof L1SummonInstance)) && ((_targetNpc instanceof L1PetInstance) || (_targetNpc instanceof L1SummonInstance))) {
			// 目标在安区、攻击者在安区、NOPVP
			if ((_targetNpc.getZoneType() == 1) || (_npc.getZoneType() == 1)) {
				_hitRate = 0;
			}
		}

		int rnd = Random.nextInt(100) + 1;
		return _hitRate >= rnd;
	}

	/** ●●●● ＥＲ决定回避率 ●●●● */
	private boolean calcErEvasion() {
		int er = _targetPc.getEr();

		int rnd = Random.nextInt(100) + 1;
		return er < rnd;
	}

	/* ■■■■■■■■■■■■■■■ 伤害算出 ■■■■■■■■■■■■■■■ */

	/** 检查伤害 */
	public int calcDamage() {
		if (_calcType == PC_PC) {
			_damage = calcPcPcDamage();
		}
		else if (_calcType == PC_NPC) {
			_damage = calcPcNpcDamage();
		}
		else if (_calcType == NPC_PC) {
			_damage = calcNpcPcDamage();
		}
		else if (_calcType == NPC_NPC) {
			_damage = calcNpcNpcDamage();
		}
		return _damage;
	}

	/** 检查武器伤害 */
	private int calcWeponDamage(int weaponMaxDamage) {
		int weaponDamage = Random.nextInt(weaponMaxDamage) + 1;
		// 判断魔法辅助
		if (_pc.hasSkillEffect(SOUL_OF_FLAME))
			weaponDamage = weaponMaxDamage;

		// 判断武器类型
		boolean darkElfWeapon = false;
		if (_pc.isDarkelf() && (_weaponType == 58)) { // 钢爪 (追加判断持有者为黑妖，避免与幻术师奇谷兽相冲)
			darkElfWeapon = true;
			if ((Random.nextInt(100) + 1) <= _weaponDoubleDmgChance) { // 出现最大值的机率
				weaponDamage = weaponMaxDamage;
			}
			if (weaponDamage == weaponMaxDamage) { // 出现最大值时 - 爪痕
				_effectId = 2;
			}
		}
		else if (_weaponType == 20 || _weaponType == 62) {// 弓、铁手甲 不算武器伤害
			weaponDamage = 0;
		}

		weaponDamage += _weaponAddDmg + _weaponEnchant; // 加上武器(额外点数+祝福魔法武器)跟武卷数

		if (_calcType == PC_NPC)
			weaponDamage += calcMaterialBlessDmg(); // 银祝福武器加伤害
		if (_weaponType == 54) {
			darkElfWeapon = true;
			if ((Random.nextInt(100) + 1) <= _weaponDoubleDmgChance) { // 双刀双击
				weaponDamage *= 2;
				_effectId = 4;
			}
		}
		weaponDamage += calcAttrEnchantDmg(); // 属性强化伤害

		if (darkElfWeapon && _pc.hasSkillEffect(DOUBLE_BRAKE))
			if ((Random.nextInt(100) + 1) <= 33)
				weaponDamage *= 2;

		return weaponDamage;
	}

	/** 远距离伤害 */
	private double calLongRageDamage(double dmg) {
		double longdmg = dmg + _pc.getBowDmgup() + _pc.getOriginalBowDmgup();

		int add_dmg = 1;
		if (_weaponType == 20) { // 弓
			if (_arrow != null) {
				add_dmg = _arrow.getItem().getDmgSmall();
				if (_calcType == PC_NPC) {
					if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large"))
						add_dmg = _arrow.getItem().getDmgLarge();
					if (_targetNpc.getNpcTemplate().is_hard())
						add_dmg /= 2;
				}
			}
			else if (_weaponId == 190) // 沙哈之弓
				add_dmg = 15;
		}
		else if (_weaponType == 62) { // 铁手甲
			add_dmg = _sting.getItem().getDmgSmall();
			if (_calcType == PC_NPC)
				if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large"))
					add_dmg = _sting.getItem().getDmgLarge();
		}

		if (add_dmg > 0)
			longdmg += Random.nextInt(add_dmg) + 1;

		// 防具增伤
		longdmg += _pc.getDmgModifierByArmor();

		if (_pc.hasSkillEffect(COOKING_2_3_N) // 料理
				|| _pc.hasSkillEffect(COOKING_2_3_S) || _pc.hasSkillEffect(COOKING_3_0_N) || _pc.hasSkillEffect(COOKING_3_0_S))
			longdmg += 1;

		return longdmg;
	}

	/** 近距离伤害 */
	private double calShortRageDamage(double dmg) {
		double shortdmg = dmg + _pc.getDmgup() + _pc.getOriginalDmgup();
		// 弱点曝光发动判断
		WeaknessExposure();
		// 近战魔法增伤
		shortdmg = calcBuffDamage(shortdmg);
		// 防具增伤
		shortdmg += _pc.getBowDmgModifierByArmor();

		if (_weaponType == 0) // 空手
			shortdmg = (Random.nextInt(5) + 4) / 4;
		else if (_weaponType2 == 17 || _weaponType2 == 19) // 奇古兽
			shortdmg = L1WeaponSkill.getKiringkuDamage(_pc, _target);

		if (_pc.hasSkillEffect(COOKING_2_0_N) // 料理
				|| _pc.hasSkillEffect(COOKING_2_0_S) || _pc.hasSkillEffect(COOKING_3_2_N) || _pc.hasSkillEffect(COOKING_3_2_S))
			shortdmg += 1;

		return shortdmg;
	}

	/** ●●●● PC 对 PC 的伤害算出 ●●●● */
	public int calcPcPcDamage() {
		// 计算武器总伤害
		int weaponTotalDamage = calcWeponDamage(_weaponSmall);

		if ((_weaponId == 262) && (Random.nextInt(100) + 1 <= 75)) { // 装备毁灭巨剑的成功确率(暂定)75%
			weaponTotalDamage += calcDestruction(weaponTotalDamage);
		}

		// 计算 远程 或 近战武器 伤害 与魔法、食物buff
		double dmg = weaponTotalDamage + _statusDamage;
		if (_weaponType == 20 || _weaponType == 62)
			dmg = calLongRageDamage(dmg);
		else
			dmg = calShortRageDamage(dmg);

		// 巴风特与耀武类武器 (地裂魔法)
		if (_weaponId == 124 || _weaponId == 289 || _weaponId == 290 || _weaponId == 291 || _weaponId == 292 || _weaponId == 293 || _weaponId == 294 || _weaponId == 295 || _weaponId == 296 || _weaponId == 297 || _weaponId == 298 || _weaponId == 299 || _weaponId == 300
				|| _weaponId == 301 || _weaponId == 302 || _weaponId == 303) {
			dmg += L1WeaponSkill.getBaphometStaffDamage(_pc, _target);
		}

		// 骰子匕首
		else if (_weaponId == 2 || _weaponId == 200002) {
			dmg += L1WeaponSkill.getDiceDaggerDamage(_pc, _targetPc, weapon);
		}

		// 深红之弩 (束缚术)
		else if (_weaponId == 204 || _weaponId == 100204) {
			L1WeaponSkill.giveFettersEffect(_pc, _targetPc);
		}

		// 雷雨之剑,天雷剑 (极道落雷)
		else if (_weaponId == 264 || _weaponId == 288) {
			dmg += L1WeaponSkill.getLightningEdgeDamage(_pc, _target);
		}

		// 暴风之斧,酷寒之矛,玄冰弓
		else if (_weaponId == 260 || _weaponId == 263 || _weaponId == 287) {
			dmg += L1WeaponSkill.getAreaSkillWeaponDamage(_pc, _target, _weaponId);
		}

		// 大法师之杖 (疾病术)
		else if (_weaponId == 261) {
			L1WeaponSkill.giveArkMageDiseaseEffect(_pc, _target);
		}
		else {
			dmg += L1WeaponSkill.getWeaponSkillDamage(_pc, _target, _weaponId);
		}

		dmg -= _targetPc.getDamageReductionByArmor(); // 防具的伤害减免

		// 魔法娃娃效果 - 伤害减免
		dmg -= L1MagicDoll.getDamageReductionByDoll(_targetPc);

		// 特别的料理伤害减免
		if (_targetPc.hasSkillEffect(COOKING_1_0_S) || _targetPc.hasSkillEffect(COOKING_1_1_S) || _targetPc.hasSkillEffect(COOKING_1_2_S) || _targetPc.hasSkillEffect(COOKING_1_3_S) || _targetPc.hasSkillEffect(COOKING_1_4_S) || _targetPc.hasSkillEffect(COOKING_1_5_S)
				|| _targetPc.hasSkillEffect(COOKING_1_6_S) || _targetPc.hasSkillEffect(COOKING_2_0_S) || _targetPc.hasSkillEffect(COOKING_2_1_S) || _targetPc.hasSkillEffect(COOKING_2_2_S) || _targetPc.hasSkillEffect(COOKING_2_3_S) || _targetPc.hasSkillEffect(COOKING_2_4_S)
				|| _targetPc.hasSkillEffect(COOKING_2_5_S) || _targetPc.hasSkillEffect(COOKING_2_6_S) || _targetPc.hasSkillEffect(COOKING_3_0_S) || _targetPc.hasSkillEffect(COOKING_3_1_S) || _targetPc.hasSkillEffect(COOKING_3_2_S) || _targetPc.hasSkillEffect(COOKING_3_3_S)
				|| _targetPc.hasSkillEffect(COOKING_3_4_S) || _targetPc.hasSkillEffect(COOKING_3_5_S) || _targetPc.hasSkillEffect(COOKING_3_6_S)) {
			dmg -= 5;
		}

		// 特别的料理伤害减免
		if (_targetPc.hasSkillEffect(COOKING_1_7_S) || _targetPc.hasSkillEffect(COOKING_2_7_S) || _targetPc.hasSkillEffect(COOKING_3_7_S)) {
			dmg -= 5;
		}

		// 增幅防御伤害减免
		if (_targetPc.hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		// 龙之护铠伤害减免
		if (_targetPc.hasSkillEffect(DRAGON_SKIN) || _targetPc.hasSkillEffect(PATIENCE)) {
			dmg -= 2;
		}

		// 圣结界伤害减半
		if (_targetPc.hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg /= 2;
		}

		// 使用暴击增加15点伤害，而奇古兽固定15点伤害
		if (_skillId == SMASH) {
			dmg += 15;
			if (_weaponType2 == 17 || _weaponType2 == 19) {
				dmg = 15;
			}
		}

		// 使用骷髅毁坏增加10点伤害，而奇古兽固定10点伤害
		else if (_skillId == BONE_BREAK) {
			dmg += 10;
			if (_weaponType2 == 17 || _weaponType2 == 19) {
				dmg = 10;
			}
			// 再次发动判断
			if (!_targetPc.hasSkillEffect(BONE_BREAK)) {
				int change = Random.nextInt(100) + 1;
				if (change < (30 + Random.nextInt(11))) { // 30 ~ 40%
					L1EffectSpawn.getInstance().spawnEffect(93001, 1700, _targetPc.getX(), _targetPc.getY(), _targetPc.getMapId());
					_targetPc.setSkillEffect(BONE_BREAK, 2 * 1000); // 发动后再次发动间隔
																	// 2秒
					_targetPc.setSkillEffect(BONE_BREAK_START, 700);
				}
			}
		}
		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0; // 没有伤害 不能吸取体力
		}

		return (int) dmg;
	}

	/** ●●●● PC 对 NPC 的伤害 ●●●● */
	private int calcPcNpcDamage() {
		int weaponMaxDamage = 0;
		if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("small") && (_weaponSmall > 0)) {
			weaponMaxDamage = _weaponSmall;
		}
		else if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("large") && (_weaponLarge > 0)) {
			weaponMaxDamage = _weaponLarge;
		}

		// 计算武器总伤害
		int weaponTotalDamage = calcWeponDamage(weaponMaxDamage);

		if ((_weaponId == 262) && (Random.nextInt(100) + 1 <= 75)) { // 装备毁灭巨剑的成功确率(暂定)75%
			weaponTotalDamage += calcDestruction(weaponTotalDamage);
		}

		// 计算伤害 远程 或 近战武器 及buff
		double dmg = weaponTotalDamage + _statusDamage;
		if (_weaponType == 20 || _weaponType == 62)
			dmg = calLongRageDamage(dmg);
		else
			dmg = calShortRageDamage(dmg);

		// 巴风特与耀武类武器 (地裂魔法)
		if (_weaponId == 124 || _weaponId == 289 || _weaponId == 290 || _weaponId == 291 || _weaponId == 292 || _weaponId == 293 || _weaponId == 294 || _weaponId == 295 || _weaponId == 296 || _weaponId == 297 || _weaponId == 298 || _weaponId == 299 || _weaponId == 300
				|| _weaponId == 301 || _weaponId == 302 || _weaponId == 303) {
			dmg += L1WeaponSkill.getBaphometStaffDamage(_pc, _target);
		}

		// 骰子匕首
		else if ((_weaponId == 2) || (_weaponId == 200002)) {
			dmg += L1WeaponSkill.getDiceDaggerDamage(_pc, _targetNpc, weapon);
		}

		// 深红之弩 (束缚术)
		else if ((_weaponId == 204) || (_weaponId == 100204)) {
			L1WeaponSkill.giveFettersEffect(_pc, _targetNpc);
		}

		// 雷雨之剑,天雷剑 (极道落雷)
		else if (_weaponId == 264 || _weaponId == 288) {
			dmg += L1WeaponSkill.getLightningEdgeDamage(_pc, _target);
		}

		// 暴风之斧,酷寒之矛,玄冰弓
		else if ((_weaponId == 260) || (_weaponId == 263 || _weaponId == 287)) {
			dmg += L1WeaponSkill.getAreaSkillWeaponDamage(_pc, _target, _weaponId);
		}

		// 大法师之杖 (疾病术)
		else if (_weaponId == 261) {
			L1WeaponSkill.giveArkMageDiseaseEffect(_pc, _target);
		}
		else {
			dmg += L1WeaponSkill.getWeaponSkillDamage(_pc, _target, _weaponId);
		}

		dmg -= calcNpcDamageReduction();

		// 使用暴击增加15点伤害，而奇古兽固定15点伤害
		if (_skillId == SMASH) {
			dmg += 15;
			if (_weaponType2 == 17 || _weaponType2 == 19) {
				dmg = 15;
			}
		}

		// 使用骷髅毁坏增加10点伤害，而奇古兽固定10点伤害
		else if (_skillId == BONE_BREAK) {
			dmg += 10;
			if (_weaponType2 == 17 || _weaponType2 == 19) {
				dmg = 10;
			}
			// 再次发动判断
			if (!_targetNpc.hasSkillEffect(BONE_BREAK)) {
				int change = Random.nextInt(100) + 1;
				if (change < (30 + Random.nextInt(11))) { // 30 ~ 40%
					L1EffectSpawn.getInstance().spawnEffect(93001, 1700, _targetNpc.getX(), _targetNpc.getY(), _targetNpc.getMapId());
					_targetNpc.setSkillEffect(BONE_BREAK, 2 * 1000); // 发动后再次发动间隔
																		// 2秒
					_targetNpc.setSkillEffect(BONE_BREAK_START, 700);
				}
			}
		}

		// 非攻城区域对宠物、召唤兽伤害减少
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetNpc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_targetNpc instanceof L1PetInstance)
				dmg /= 8;
			else if (_targetNpc instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) _targetNpc;
				if (summon.isExsistMaster())
					dmg /= 8;
			}
		}

		// 吉尔塔斯反击屏障伤害判断 (PC_NPC)
		if (_targetNpc.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_COUNTERATTACK_BARRIER) {
			_pc.broadcastPacket(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			_pc.receiveDamage(_targetNpc, (int) (dmg * 2), true);
			dmg = 0;
		}

		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0; // 没有伤害 不能吸取体力
		}

		return (int) dmg;
	}

	/** ●●●● NPC 对 PC 的伤害 ●●●● */
	private int calcNpcPcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 0D;
		if (lvl < 10) {
			dmg = Random.nextInt(lvl) + 10D + _npc.getStr() / 2 + 1;
		}
		else {
			dmg = Random.nextInt(lvl) + _npc.getStr() / 2 + 1;
		}

		if (_npc instanceof L1PetInstance) {
			dmg += (lvl / 16); // 宠物每LV16追加打击
			dmg += ((L1PetInstance) _npc).getDamageByWeapon();
		}

		dmg += _npc.getDmgup();

		if (isUndeadDamage()) {
			dmg *= 1.1;
		}

		dmg = dmg * getLeverage() / 10;

		dmg -= calcPcDefense();

		if (_npc.isWeaponBreaked()) { // ＮＰＣ的武器损坏中。
			dmg /= 2;
		}

		dmg -= _targetPc.getDamageReductionByArmor(); // 防具伤害减免

		// 魔法娃娃效果 - 伤害减免
		dmg -= L1MagicDoll.getDamageReductionByDoll(_targetPc);

		// 特别的料理伤害减免
		if (_targetPc.hasSkillEffect(COOKING_1_0_S) || _targetPc.hasSkillEffect(COOKING_1_1_S) || _targetPc.hasSkillEffect(COOKING_1_2_S) || _targetPc.hasSkillEffect(COOKING_1_3_S) || _targetPc.hasSkillEffect(COOKING_1_4_S) || _targetPc.hasSkillEffect(COOKING_1_5_S)
				|| _targetPc.hasSkillEffect(COOKING_1_6_S) || _targetPc.hasSkillEffect(COOKING_2_0_S) || _targetPc.hasSkillEffect(COOKING_2_1_S) || _targetPc.hasSkillEffect(COOKING_2_2_S) || _targetPc.hasSkillEffect(COOKING_2_3_S) || _targetPc.hasSkillEffect(COOKING_2_4_S)
				|| _targetPc.hasSkillEffect(COOKING_2_5_S) || _targetPc.hasSkillEffect(COOKING_2_6_S) || _targetPc.hasSkillEffect(COOKING_3_0_S) || _targetPc.hasSkillEffect(COOKING_3_1_S) || _targetPc.hasSkillEffect(COOKING_3_2_S) || _targetPc.hasSkillEffect(COOKING_3_3_S)
				|| _targetPc.hasSkillEffect(COOKING_3_4_S) || _targetPc.hasSkillEffect(COOKING_3_5_S) || _targetPc.hasSkillEffect(COOKING_3_6_S)) {
			dmg -= 5;
		}

		// 特别的料理伤害减免
		if (_targetPc.hasSkillEffect(COOKING_1_7_S) || _targetPc.hasSkillEffect(COOKING_2_7_S) || _targetPc.hasSkillEffect(COOKING_3_7_S)) {
			dmg -= 5;
		}

		// 增幅防御伤害减免
		if (_targetPc.hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		// 龙之护铠伤害减免
		if (_targetPc.hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 2;
		}

		// 耐力伤害减免
		if (_targetPc.hasSkillEffect(PATIENCE)) {
			dmg -= 2;
		}

		// 圣结界伤害减半
		if (_targetPc.hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg /= 2;
		}

		// 非攻城区域宠物、召唤兽对玩家伤害减少
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetPc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_npc instanceof L1PetInstance) {
				dmg /= 8;
			}
			else if (_npc instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) _npc;
				if (summon.isExsistMaster()) {
					dmg /= 8;
				}
			}
		}

		if (dmg <= 0) {
			_isHit = false;
		}

		addNpcPoisonAttack(_npc, _targetPc);

		return (int) dmg;
	}

	/** ●●●● NPC 对 NPC 的伤害 ●●●● */
	private int calcNpcNpcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 0;

		if (_npc instanceof L1PetInstance) {
			dmg = Random.nextInt(_npc.getNpcTemplate().get_level()) + _npc.getStr() / 2 + 1;
			dmg += (lvl / 16); // 宠物每LV16追加打击
			dmg += ((L1PetInstance) _npc).getDamageByWeapon();
		}
		else {
			dmg = Random.nextInt(lvl) + _npc.getStr() / 2 + 1;
		}

		if (isUndeadDamage()) {
			dmg *= 1.1;
		}

		dmg = dmg * getLeverage() / 10;

		dmg -= calcNpcDamageReduction();

		if (_npc.isWeaponBreaked()) { // ＮＰＣ武器损坏中。
			dmg /= 2;
		}

		addNpcPoisonAttack(_npc, _targetNpc);

		// 吉尔塔斯反击屏障伤害判断 (NPC_NPC)
		if (_targetNpc.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_COUNTERATTACK_BARRIER) {
			_npc.broadcastPacket(new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage));
			_npc.receiveDamage(_targetNpc, (int) (dmg * 2));
			dmg = 0;
		}

		if (dmg <= 0) {
			_isHit = false;
		}

		return (int) dmg;
	}

	/** ●●●● 强化魔法近战用 ●●●● */
	private double calcBuffDamage(double dmg) {
		// 属性之火、燃烧斗志1.5倍伤害
		if (_pc.hasSkillEffect(BURNING_SPIRIT) // 燃烧斗志
				|| _pc.hasSkillEffect(ELEMENTAL_FIRE)) { // 属性之火
			if ((Random.nextInt(100) + 1) <= 33) {
				double tempDmg = dmg;
				if (_pc.hasSkillEffect(FIRE_WEAPON)) { // 火焰武器
					tempDmg -= 4;
				}
				else if (_pc.hasSkillEffect(FIRE_BLESS)) { // 烈炎气息
					tempDmg -= 5;
				}
				else if (_pc.hasSkillEffect(BURNING_WEAPON)) { // 烈炎武器
					tempDmg -= 6;
				}
				if (_pc.hasSkillEffect(BERSERKERS)) { // 狂暴术
					tempDmg -= 5;
				}
				double diffDmg = dmg - tempDmg;
				dmg = tempDmg * 1.5 + diffDmg;
			}
		}

		// 锁链剑
		if (_weaponType2 == 18) {
			// 弱点曝光 - 伤害加成
			if (_pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV3)) {
				dmg += 9;
			}
			else if (_pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV2)) {
				dmg += 6;
			}
			else if (_pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV1)) {
				dmg += 3;
			}
		}

		// 屠宰者 & 弱点曝光LV3 - 伤害 *1.3
		if (_pc.isFoeSlayer() && _pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV3)) {
			dmg *= 1.3;
		}
		if (_pc.hasSkillEffect(BURNING_SLASH)) { // 燃烧击砍
			dmg += 10;
			_pc.sendPackets(new S_EffectLocation(_targetX, _targetY, 6591));
			_pc.broadcastPacket(new S_EffectLocation(_targetX, _targetY, 6591));
			_pc.killSkillEffectTimer(BURNING_SLASH);
		}

		return dmg;
	}

	/** ●●●● 角色防御的伤害减免 ●●●● */
	private int calcPcDefense() {
		int ac = Math.max(0, 10 - _targetPc.getAc());
		int acDefMax = _targetPc.getClassFeature().getAcDefenseMax(ac);
		return Random.nextInt(acDefMax + 1);
	}

	/** ●●●● NPC 的伤害减免 ●●●● */
	private int calcNpcDamageReduction() {
		return _targetNpc.getNpcTemplate().get_damagereduction();
	}

	/** ●●●● 武器的材质(祝福等)追加的额外伤害 ●●●● */
	private int calcMaterialBlessDmg() {
		int damage = 0;
		int undead = _targetNpc.getNpcTemplate().get_undead();

		// 银质,米索莉,黑色米索莉 武器对不死系怪物的随机伤害 (1-20)
		if (((_weaponMaterial == 14) || (_weaponMaterial == 17) || (_weaponMaterial == 22)) && ((undead == 1) || (undead == 3) || (undead == 5))) {
			damage += Random.nextInt(20) + 1;
		}

		// 黑色米索莉・オリハルコン、かつ、恶魔系
		else if (((_weaponMaterial == 17) || (_weaponMaterial == 22)) && (undead == 2)) {
			damage += Random.nextInt(3) + 1;
		}

		// 祝福武器、かつ、アンデッド系・恶魔系・ア不死系
		if ((_weaponBless == 0) && ((undead == 1) || (undead == 2) || (undead == 3))) {
			damage += Random.nextInt(4) + 1;
		}
		if ((_pc.getWeapon() != null) && (_weaponType != 20) && (_weaponType != 62) && (weapon.getHolyDmgByMagic() != 0) && ((undead == 1) || (undead == 3))) {
			damage += weapon.getHolyDmgByMagic();
		}
		return damage;
	}

	/** ●●●● 属性强化卷追加伤害 ●●●● */
	private int calcAttrEnchantDmg() {

		int damage = 0;
		if (_weaponAttrEnchantLevel == 1) { // 一段
			damage = 1;
		}
		else if (_weaponAttrEnchantLevel == 2) { // 二段
			damage = 3;
		}
		else if (_weaponAttrEnchantLevel == 3) { // 三段
			damage = 5;
		}

		// XXX 耐性处理は本来、耐性合计值ではなく、各值を个别に处理して总和する。
		int resist = 0;
		if (_calcType == PC_PC) {
			if (_weaponAttrEnchantKind == 1) { // 地
				resist = _targetPc.getEarth();
			}
			else if (_weaponAttrEnchantKind == 2) { // 火
				resist = _targetPc.getFire();
			}
			else if (_weaponAttrEnchantKind == 4) { // 水
				resist = _targetPc.getWater();
			}
			else if (_weaponAttrEnchantKind == 8) { // 风
				resist = _targetPc.getWind();
			}
		}
		else if (_calcType == PC_NPC) {
			int weakAttr = _targetNpc.getNpcTemplate().get_weakAttr();
			if (((_weaponAttrEnchantKind == 1) && (weakAttr == 1)) // 地
					|| ((_weaponAttrEnchantKind == 2) && (weakAttr == 2)) // 火
					|| ((_weaponAttrEnchantKind == 4) && (weakAttr == 4)) // 水
					|| ((_weaponAttrEnchantKind == 8) && (weakAttr == 8))) { // 风
				resist = -50;
			}
		}

		int resistFloor = (int) (0.32 * Math.abs(resist));
		if (resist >= 0) {
			resistFloor *= 1;
		}
		else {
			resistFloor *= -1;
		}

		double attrDeffence = resistFloor / 32.0;
		double attrCoefficient = 1 - attrDeffence;

		damage *= attrCoefficient;

		return damage;
	}

	/** ●●●● 亡灵系列ＮＰＣ在夜间的攻击力变化 ●●●● */
	private boolean isUndeadDamage() {
		boolean flag = false;
		int undead = _npc.getNpcTemplate().get_undead();
		boolean isNight = L1GameTimeClock.getInstance().currentTime().isNight();
		if (isNight && ((undead == 1) || (undead == 3) || (undead == 4))) { // 18～6时、かつ、亡灵系列・不死系列・不死系的弱点无效
			flag = true;
		}
		return flag;
	}

	/** ●●●● 增加ＮＰＣ的附毒攻击 ●●●● */
	private void addNpcPoisonAttack(L1Character attacker, L1Character target) {
		if (_npc.getNpcTemplate().get_poisonatk() != 0) { // 附毒攻击
			if (15 >= Random.nextInt(100) + 1) { // 15%的几率附毒攻击
				if (_npc.getNpcTemplate().get_poisonatk() == 1) { // 通常毒
					// 3秒周期、伤害HP-5
					L1DamagePoison.doInfection(attacker, target, 3000, 5);
				}
				else if (_npc.getNpcTemplate().get_poisonatk() == 2) { // 沉默毒
					L1SilencePoison.doInfection(target);
				}
				else if (_npc.getNpcTemplate().get_poisonatk() == 4) { // 麻痹毒
					// 20秒后至45秒间麻痹
					L1ParalysisPoison.doInfection(target, 20000, 45000);
				}
			}
		}
		else if (_npc.getNpcTemplate().get_paralysisatk() != 0) { // 随着麻痹攻击
		}
	}

	/** ■■■■ 玛那魔杖、钢铁玛那魔杖、魔力短剑的MP吸收量 ■■■■ */
	public void calcStaffOfMana() {
		if ((_weaponId == 126) || (_weaponId == 127)) { // 玛纳、钢铁玛纳
			int som_lvl = _weaponEnchant + 3; // 设定最大MP吸收量
			if (som_lvl < 0) {
				som_lvl = 0;
			}
			// 获取随机的MP吸收量
			_drainMana = Random.nextInt(som_lvl) + 1;
			// 吸收MP上限的最高限额为9
			if (_drainMana > Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK) {
				_drainMana = Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK;
			}
		}
		else if (_weaponId == 259) { // 魔力短剑
			if (_calcType == PC_PC) {
				if (_targetPc.getMr() <= Random.nextInt(100) + 1) { // 确率取决于目标的MR
					_drainMana = 1; // 吸收量固定为1
				}
			}
			else if (_calcType == PC_NPC) {
				if (_targetNpc.getMr() <= Random.nextInt(100) + 1) { // 确率取决于目标的MR
					_drainMana = 1; // 吸收量固定为1
				}
			}
		}
	}

	/** ■■■■ 毁灭巨剑的HP吸收量 ■■■■ */
	private int calcDestruction(int dmg) {
		_drainHp = (dmg / 8) + 1;
		return _drainHp > 0 ? _drainHp : 1;
	}

	/** ■■■■ 增加ＰＣ的附毒攻击 ■■■■ */
	public void addPcPoisonAttack(L1Character attacker, L1Character target) {
		int chance = Random.nextInt(100) + 1;
		if (((_weaponId == 13 // 死亡之指
				)
				|| (_weaponId == 44 // 古代黑暗妖精之剑
				) || ((_weaponId != 0) && _pc.hasSkillEffect(ENCHANT_VENOM))) // 附加剧毒
				&& (chance <= 10)) {
			// 通常毒、3秒周期、伤害HP-5
			L1DamagePoison.doInfection(attacker, target, 3000, 5);
		}
		else {
			// 魔法娃娃效果 - 中毒
			if (L1MagicDoll.getEffectByDoll(attacker, (byte) 1) == 1) {
				L1DamagePoison.doInfection(attacker, target, 3000, 5);
			}
		}
	}

	/** ■■■■ 增加底比斯武器的付加攻击 ■■■■ */
	public void addChaserAttack() {
		if (5 > Random.nextInt(100) + 1) {
			if (_weaponId == 265 || _weaponId == 266 || _weaponId == 267 || _weaponId == 268 || _weaponId == 280 || _weaponId == 281) {
				L1Chaser chaser = new L1Chaser(_pc, _target, L1Skills.ATTR_EARTH, 7025);
				chaser.begin();
			}
			else if (_weaponId == 276 || _weaponId == 277) {
				L1Chaser chaser = new L1Chaser(_pc, _target, L1Skills.ATTR_WATER, 7179);
				chaser.begin();
			}
			else if (_weaponId == 304 || _weaponId == 307 || _weaponId == 308) {
				L1Chaser chaser = new L1Chaser(_pc, _target, L1Skills.ATTR_WATER, 8150);
				chaser.begin();
			}
			else if (_weaponId == 305 || _weaponId == 306 || _weaponId == 309) {
				L1Chaser chaser = new L1Chaser(_pc, _target, L1Skills.ATTR_WATER, 8152);
				chaser.begin();
			}
		}
	}

	/** ■■■■■■■■■■■■■■ 攻击动作送信 ■■■■■■■■■■■■■■ */
	public void action() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			actionPc();
		}
		else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			actionNpc();
		}
	}

	/** ●●●● ＰＣ攻击动作 ●●●● */
	public void actionPc() {
		_attckActId = 1;
		boolean isFly = false;
		_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // 改变面向

		if (_weaponType == 20 && (_arrow != null || _weaponId == 190)) { // 弓 有箭或沙哈之弓
			if (_arrow != null) { // 弓 - 有箭
				_pc.getInventory().removeItem(_arrow, 1); // 移除一支箭矢
				_attckGrfxId = 66; // 箭
			}
			else if (_weaponId == 190) // 沙哈 - 无箭
				_attckGrfxId = 2349; // 魔法箭

			if (_pc.getTempCharGfx() == 8719) // 柑橘
				_attckGrfxId = 8721; // 橘子籽

			if (_pc.getTempCharGfx() == 8900) // 海露拜
				_attckGrfxId = 8904; // 魔法箭

			if (_pc.getTempCharGfx() == 8913) // 朱里安
				_attckGrfxId = 8916; // 魔法箭

			isFly = true;
		}
		else if ((_weaponType == 62) && (_sting != null)) { // 铁手甲 - 有飞刀
			_pc.getInventory().removeItem(_sting, 1); // 移除一个飞刀
			_attckGrfxId = 2989; // 飞刀
			isFly = true;
		}

		// 判断玩家是否发送miss包
		if (!_isHit) { // Miss
			_damage = 0; // 伤害设0, 无受伤动作出现
		}

		int[] data = null; // 攻击封包的参数

		if (isFly) { // 远距离攻击
			data = new int[] { _attckActId, _damage, _attckGrfxId }; // 参数
			_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, _targetX, _targetY, data));
			_pc.broadcastPacket(new S_UseArrowSkill(_pc, _targetId, _targetX, _targetY, data));
		}
		else { // 近距离攻击
			data = new int[] { _attckActId, _damage, _effectId }; // 参数
			_pc.sendPackets(new S_AttackPacket(_pc, _targetId, data)); // 对自身送出
			_pc.broadcastPacket(new S_AttackPacket(_pc, _targetId, data)); // 对非自身送出
		}

		if (_isHit) {
			_target.broadcastPacketExceptTargetSight(new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _pc);
		}
	}

	/** ●●●● ＮＰＣ攻击动作 ●●●● */
	private void actionNpc() {
		int bowActId = 0;
		int npcGfxid = _npc.getTempCharGfx();
		int actId = L1NpcDefaultAction.getInstance().getSpecialAttack(npcGfxid); // 特殊攻击动作
		double dmg = _damage;
		int[] data = null; // 封包参数

		_npc.setHeading(_npc.targetDirection(_targetX, _targetY)); // 改变面向

		// 与目标距离2格以上
		boolean isLongRange = false;
		if (npcGfxid == 4521 || npcGfxid == 4550 || npcGfxid == 5062 || npcGfxid == 5317 || npcGfxid == 5324 || npcGfxid == 5331 || npcGfxid == 5338 || npcGfxid == 5412) {
			isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 2);
		}
		else {
			isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1);
		}
		bowActId = _npc.getPolyArrowGfx(); // 被变身后的远距图像
		if (bowActId == 0) {
			bowActId = _npc.getNpcTemplate().getBowActId();
		}
		if (getActId() == 0) {
			if ((actId != 0) && ((Random.nextInt(100) + 1) <= 40)) {
				dmg *= 1.2;
			}
			else {
				if (!isLongRange || bowActId == 0) { // 近距离
					actId = L1NpcDefaultAction.getInstance().getDefaultAttack(npcGfxid);
					if (bowActId > 0) { // 远距离怪物，近距离时攻击力加成
						dmg *= 1.2;
					}
				}
				else { // 远距离
					actId = L1NpcDefaultAction.getInstance().getRangedAttack(npcGfxid);
				}
			}
		}
		else {
			actId = getActId(); // 攻击动作由 mobskill控制
		}
		_damage = (int) dmg;

		if (!_isHit) { // 判断是否发送miss包
			_damage = 0; // 伤害设0, 无受伤动作出现
		}

		// 距离2格以上使用 弓 攻击
		if (isLongRange && (bowActId > 0)) {
			data = new int[] { actId, _damage, bowActId }; // data = {actid,
															// dmg, spellgfx}
			_npc.broadcastPacket(new S_UseArrowSkill(_npc, _targetId, _targetX, _targetY, data));
		}
		else {
			if (getGfxId() > 0) {
				data = new int[] { actId, _damage, getGfxId(), 6 }; // data =
																	// {actid,
																	// dmg,
																	// spellgfx,
																	// use_type}
				_npc.broadcastPacket(new S_UseAttackSkill(_npc, _targetId, _targetX, _targetY, data));
			}
			else {
				data = new int[] { actId, _damage, 0 }; // data = {actid, dmg,
														// effect}
				_npc.broadcastPacket(new S_AttackPacket(_npc, _targetId, data)); // 对非自身送出
			}
		}
		if (_isHit) {
			_target.broadcastPacketExceptTargetSight(new S_DoActionGFX(_targetId, ActionCodes.ACTION_Damage), _npc);
		}
	}

	/** ■■■■■■■■■■■■■■■ 计算结果反映 ■■■■■■■■■■■■■■■ */
	public void commit() {
		if (_isHit) {
			if ((_calcType == PC_PC) || (_calcType == NPC_PC)) {
				commitPc();
			}
			else if ((_calcType == PC_NPC) || (_calcType == NPC_NPC)) {
				commitNpc();
			}
		}

		// 伤害值与命中率确认
		if (!Config.ALT_ATKMSG) {
			return;
		}
		if (Config.ALT_ATKMSG) {
			if (((_calcType == PC_PC) || (_calcType == PC_NPC)) && !_pc.isGm()) {
				return;
			}
			if (((_calcType == PC_PC) || (_calcType == NPC_PC)) && !_targetPc.isGm()) {
				return;
			}
		}
		String msg0 = "";
		String msg1 = " 造成 ";
		String msg2 = "";
		String msg3 = "";
		String msg4 = "";
		if ((_calcType == PC_PC) || (_calcType == PC_NPC)) { // 攻击者为ＰＣ的场合
			msg0 = "物攻 对";
		}
		else if (_calcType == NPC_PC) { // 攻击者为ＮＰＣ的场合
			msg0 = _npc.getNameId() + "(物攻)：";
		}

		if ((_calcType == NPC_PC) || (_calcType == PC_PC)) { // 目标为ＰＣ的场合
			msg4 = _targetPc.getName();
			msg2 = "，剩余 " + _targetPc.getCurrentHp() + "，命中	" + _hitRate + "%";
		}
		else if (_calcType == PC_NPC) { // 目标为ＮＰＣ的场合
			msg4 = _targetNpc.getNameId();
			msg2 = "，剩余 " + _targetNpc.getCurrentHp() + "，命中 " + _hitRate + "%";
		}
		msg3 = _isHit ? _damage + " 伤害" : "  0 伤害";

		// 物攻 对 目标 造成 X 伤害，剩余 Y，命中 Z %。
		if ((_calcType == PC_PC) || (_calcType == PC_NPC)) {
			_pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4));
		}
		// 攻击者(物攻)： X伤害，剩余 Y，命中%。
		else if ((_calcType == NPC_PC)) {
			_targetPc.sendPackets(new S_ServerMessage(166, msg0, null, msg2, msg3, null));
		}
	}

	/** ●●●● 反映PC的计算结果 ●●●● */
	private void commitPc() {
		if (_calcType == PC_PC) {
			if ((_drainMana > 0) && (_targetPc.getCurrentMp() > 0)) {
				if (_drainMana > _targetPc.getCurrentMp()) {
					_drainMana = _targetPc.getCurrentMp();
				}
				short newMp = (short) (_targetPc.getCurrentMp() - _drainMana);
				_targetPc.setCurrentMp(newMp);
				newMp = (short) (_pc.getCurrentMp() + _drainMana);
				_pc.setCurrentMp(newMp);
			}
			if (_drainHp > 0) { // HP吸收恢复
				short newHp = (short) (_pc.getCurrentHp() + _drainHp);
				_pc.setCurrentHp(newHp);
			}
			damagePcWeaponDurability(); // 武器有损坏。
			_targetPc.receiveDamage(_pc, _damage, false);
		}
		else if (_calcType == NPC_PC) {
			_targetPc.receiveDamage(_npc, _damage, false);
		}
	}

	/** ●●●● 反映ＮＰＣ的计算结果 ●●●● */
	private void commitNpc() {
		if (_calcType == PC_NPC) {
			if (_drainMana > 0) {
				int drainValue = _targetNpc.drainMana(_drainMana);
				int newMp = _pc.getCurrentMp() + drainValue;
				_pc.setCurrentMp(newMp);
				if (drainValue > 0) {
					int newMp2 = _targetNpc.getCurrentMp() - drainValue;
					_targetNpc.setCurrentMpDirect(newMp2);
				}
			}
			if (_drainHp > 0) { // HP吸收恢复
				short newHp = (short) (_pc.getCurrentHp() + _drainHp);
				_pc.setCurrentHp(newHp);
			}
			damageNpcWeaponDurability(); // 武器有损坏。
			_targetNpc.receiveDamage(_pc, _damage);
		}
		else if (_calcType == NPC_NPC) {
			_targetNpc.receiveDamage(_npc, _damage);
		}
	}

	/* ■■■■■■■■■■■■■■■ 反击屏障 ■■■■■■■■■■■■■■■ */

	/** ■■■■ 发送反击屏障时的攻击动作 ■■■■ */
	public void actionCounterBarrier() {
		if (_calcType == PC_PC) {
			_pc.setHeading(_pc.targetDirection(_targetX, _targetY)); // 设置面向
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId));
			_pc.broadcastPacket(new S_AttackMissPacket(_pc, _targetId));
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
			_pc.broadcastPacket(new S_DoActionGFX(_pc.getId(), ActionCodes.ACTION_Damage));
		}
		else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.setHeading(_npc.targetDirection(_targetX, _targetY)); // 设置面向
			if (getActId() > 0) {
				actId = getActId();
			}
			else {
				actId = ActionCodes.ACTION_Attack;
			}
			if (getGfxId() > 0) {
				int[] data = { actId, 0, getGfxId(), 6 }; // data = {actId, dmg, getGfxId(), use_type}
				_npc.broadcastPacket(new S_UseAttackSkill(_target, _npc.getId(), _targetX, _targetY, data));
			}
			else {
				_npc.broadcastPacket(new S_AttackMissPacket(_npc, _targetId, actId));
			}
			_npc.broadcastPacket(new S_DoActionGFX(_npc.getId(), ActionCodes.ACTION_Damage));
		}
	}

	/** ■■■■ 对手的攻击对反击屏障是否有效判别 ■■■■ */
	public boolean isShortDistance() {
		boolean isShortDistance = true;
		if (_calcType == PC_PC) {
			if ((_weaponType == 20) || (_weaponType == 62)) { // 单手弓？
				isShortDistance = false;
			}
		}
		else if (_calcType == NPC_PC) {
			boolean isLongRange = (_npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1);
			int bowActId = _npc.getPolyArrowGfx();
			if (bowActId == 0) {
				bowActId = _npc.getNpcTemplate().getBowActId();
			}
			// 距离2格以上、攻击者の弓のアクションIDがある场合は远攻击
			if (isLongRange && (bowActId > 0)) {
				isShortDistance = false;
			}
		}
		return isShortDistance;
	}

	/** ■■■■ 反映反击屏障伤害 ■■■■ */
	public void commitCounterBarrier() {
		int damage = calcCounterBarrierDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		}
		else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	/** ●●●● 反击屏障伤害 ●●●● */
	private int calcCounterBarrierDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;
		weapon = _targetPc.getWeapon();
		if (weapon != null) {
			if (weapon.getItem().getType() == 3) { // 双手剑
				// (BIG最大伤害+强化数+额外伤害)*2
				damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier()) * 2;
			}
		}
		return damage;
	}

	/**
	 * 武器的损伤。 对NPC的场合、10%的几率损坏。祝福武器3%的几率损坏。
	 */
	private void damageNpcWeaponDurability() {
		int chance = 10;
		int bchance = 3;

		// 不攻击NPC、空手、不会损坏的武器、SOF中的场合什么都不做。
		if ((_calcType != PC_NPC) || (_targetNpc.getNpcTemplate().is_hard() == false) || (_weaponType == 0) || (weapon.getItem().get_canbedmg() == 0) || _pc.hasSkillEffect(SOUL_OF_FLAME)) {
			return;
		}
		// 普通武器・诅咒武器
		if (((_weaponBless == 1) || (_weaponBless == 2)) && ((Random.nextInt(100) + 1) < chance)) {
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName())); // \f1你的%0%s坏了。
			_pc.getInventory().receiveDamage(weapon);
		}
		// 祝福武器
		if ((_weaponBless == 0) && ((Random.nextInt(100) + 1) < bchance)) {
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName())); // \f1你的%0%s坏了。
			_pc.getInventory().receiveDamage(weapon);
		}
	}

	/**
	 * 反弹攻击 损坏武器。 反弹攻击损坏的几率10%
	 */
	private void damagePcWeaponDurability() {
		// PvP以外、空手、弓、ガントトレット、对手未使用反弹攻击、SOF中的场合什么都不做
		if ((_calcType != PC_PC) || (_weaponType == 0) || (_weaponType == 20) || (_weaponType == 62) || (_targetPc.hasSkillEffect(BOUNCE_ATTACK) == false) || _pc.hasSkillEffect(SOUL_OF_FLAME)) {
			return;
		}

		if (Random.nextInt(100) + 1 <= 10) {
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName())); // \f1你的%0%s坏了。
			_pc.getInventory().receiveDamage(weapon);
		}
	}

	/** 弱点曝光 */
	private void WeaknessExposure() {
		if (weapon != null) {
			int random = Random.nextInt(100) + 1;
			if (_weaponType2 == 18) { // 锁链剑
				// 使用屠宰者...
				if (_pc.isFoeSlayer()) {
					return;
				}
				if (_pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV3)) { // 目前阶段三
					if (random > 30 && random <= 60) { // 阶段三
						_pc.killSkillEffectTimer(SPECIAL_EFFECT_WEAKNESS_LV3);
						_pc.setSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV3, 16 * 1000);
						_pc.sendPackets(new S_SkillIconGFX(75, 3));
					}
				}
				else if (_pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV2)) { // 目前阶段二
					if (random <= 30) { // 阶段二
						_pc.killSkillEffectTimer(SPECIAL_EFFECT_WEAKNESS_LV2);
						_pc.setSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV2, 16 * 1000);
						_pc.sendPackets(new S_SkillIconGFX(75, 2));
					}
					else if (random >= 70) { // 阶段三
						_pc.killSkillEffectTimer(SPECIAL_EFFECT_WEAKNESS_LV2);
						_pc.setSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV3, 16 * 1000);
						_pc.sendPackets(new S_SkillIconGFX(75, 3));
					}
				}
				else if (_pc.hasSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV1)) { // 目前阶段一
					if (random <= 40) { // 阶段一
						_pc.killSkillEffectTimer(SPECIAL_EFFECT_WEAKNESS_LV1);
						_pc.setSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV1, 16 * 1000);
						_pc.sendPackets(new S_SkillIconGFX(75, 1));
					}
					else if (random >= 70) { // 阶段二
						_pc.killSkillEffectTimer(SPECIAL_EFFECT_WEAKNESS_LV1);
						_pc.setSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV2, 16 * 1000);
						_pc.sendPackets(new S_SkillIconGFX(75, 2));
					}
				}
				else {
					if (random <= 40) { // 阶段一
						_pc.setSkillEffect(SPECIAL_EFFECT_WEAKNESS_LV1, 16 * 1000);
						_pc.sendPackets(new S_SkillIconGFX(75, 1));
					}
				}
			}
		}
	}
}
