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
package l1j.server.server.model.skill;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.ActionCodes;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Magic;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1AuctionBoardInstance;
import l1j.server.server.model.Instance.L1BoardInstance;
import l1j.server.server.model.Instance.L1CrownInstance;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1DwarfInstance;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1FurnitureInstance;
import l1j.server.server.model.Instance.L1GuardInstance;
import l1j.server.server.model.Instance.L1HousekeeperInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.Instance.L1TeleporterInstance;
import l1j.server.server.model.Instance.L1TowerInstance;
import l1j.server.server.model.trap.L1WorldTraps;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_Dexup;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_Poison;
import l1j.server.server.serverpackets.S_RangeSkill;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconAura;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillIconShield;
import l1j.server.server.serverpackets.S_SkillIconWaterLife;
import l1j.server.server.serverpackets.S_SkillIconWindShackle;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.serverpackets.S_Strup;
import l1j.server.server.serverpackets.S_TrueTarget;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1BookMark;

import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.collections.Lists;

/**
 * 使用技能判断
 */
public class L1SkillUse {

	/** 构建 */
	public L1SkillUse() {
	}

	/** 一般类型 */
	public static final int TYPE_NORMAL = 0;
	/** 登入类型 */
	public static final int TYPE_LOGIN = 1;
	/** 魔法卷轴类型 */
	public static final int TYPE_SPELLSC = 2;
	/** NPC BUFF类型 */
	public static final int TYPE_NPCBUFF = 3;
	/** GM BUFF类型 */
	public static final int TYPE_GMBUFF = 4;
	/** 技能 */
	private L1Skills _skill;
	/** 技能ID */
	private int _skillId;
	/** 伤害 */
	private int _dmg;
	/** 取得状态持续时间 */
	private int _getBuffDuration;
	/** 昏迷状态持续时间 */
	private int _shockStunDuration;
	/** 取得状态图示的时间 */
	private int _getBuffIconDuration;
	/** 目标 ID */
	private int _targetID;
	/** MP 消耗 */
	private int _mpConsume = 0;
	/** HP 消耗 */
	private int _hpConsume = 0;
	/** 目标 X 坐标 */
	private int _targetX = 0;
	/** 目标Y 坐标 */
	private int _targetY = 0;
	/** 通知信息 */
	private String _message = null;
	/** 技能时间 */
	private int _skillTime = 0;
	/** 类型 */
	private int _type = 0;
	/** PK中 */
	private boolean _isPK = false;
	/** 记忆书签ID */
	private int _bookmarkId = 0;
	/** 道具的唯一ID */
	private int _itemobjid = 0;
	/** 检查所使用技能 */
	private boolean _checkedUseSkill = false;
	/** 攻击倍率 (1/10倍) */
	private int _leverage = 10; // 1/10倍なので10で1倍
	/** 魔法攻击距离 */
	private int _skillRanged = 0;
	/** 魔法攻击范围 */
	private int _skillArea = 0;
	/** 被冻结 */
	private boolean _isFreeze = false;
	/** 魔法屏障状态 */
	private boolean _isCounterMagic = true;
	/**  */
	private boolean _isGlanceCheckFail = false;
	/** 使用者 */
	private L1Character _user = null;
	/** 目标 */
	private L1Character _target = null;
	/** 使用者为PC */
	private L1PcInstance _player = null;
	/** 使用者为NPC */
	private L1NpcInstance _npc = null;
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
	/** 目标清单 */
	private List<TargetStatus> _targetList;
	/** 动作 ID */
	private int _actid = 0;
	/** 图像 ID */
	private int _gfxid = 0;

	private static Logger _log = Logger.getLogger(L1SkillUse.class.getName());

	/**  */
	private static final int[] CAST_WITH_INVIS = { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, REDUCTION_ARMOR, BOUNCE_ATTACK, SOLID_CARRIAGE, COUNTER_BARRIER, 97, 98, 99, 100, 101,
			102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 133, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159, 163, 164, 165, 166, 168, 169, 170, 171, SOUL_OF_FLAME, ADDITIONAL_FIRE, DRAGON_SKIN, AWAKEN_ANTHARAS,
			AWAKEN_FAFURION, AWAKEN_VALAKAS, MIRROR_IMAGE, ILLUSION_OGRE, ILLUSION_LICH, PATIENCE, ILLUSION_DIA_GOLEM, INSIGHT, ILLUSION_AVATAR };

	/** 设定魔法屏障不可抵挡的魔法 */
	private static final int[] EXCEPT_COUNTER_MAGIC = { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, SHOCK_STUN, REDUCTION_ARMOR, BOUNCE_ATTACK, SOLID_CARRIAGE, COUNTER_BARRIER, 97, 98,
			99, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 132, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159, 161, 163, 164, 165, 166, 168, 169, 170, 171, SOUL_OF_FLAME, ADDITIONAL_FIRE, DRAGON_SKIN,
			AWAKEN_ANTHARAS, AWAKEN_FAFURION, AWAKEN_VALAKAS, MIRROR_IMAGE, ILLUSION_OGRE, ILLUSION_LICH, PATIENCE, 10026, 10027, ILLUSION_DIA_GOLEM, INSIGHT, ILLUSION_AVATAR, 10028, 10029 };

	/** 目标状态 */
	private static class TargetStatus {

		/** 目标 */
		private L1Character _target = null;
		/** 计算器 */
		private boolean _isCalc = true; // ダメージや确率魔法の计算をする必要があるか？
										// (你需要计算魔法伤害和确率？)

		public TargetStatus(L1Character _cha) {
			_target = _cha;
		}

		public TargetStatus(L1Character _cha, boolean _flg) {
			_isCalc = _flg;
		}

		public L1Character getTarget() {
			return _target;
		}

		public boolean isCalc() {
			return _isCalc;
		}
	}

	/**
	 * 设定魔法攻击距离变更。
	 * 
	 * @param i
	 */
	public void setSkillRanged(int i) {
		_skillRanged = i;
	}

	/**
	 * 取得魔法攻击距离变更。
	 * 
	 * @return
	 */
	public int getSkillRanged() {
		if (_skillRanged == 0) {
			return _skill.getRanged();
		}
		return _skillRanged;
	}

	/**
	 * 设定魔法攻击范围变更。
	 * 
	 * @param i
	 */
	public void setSkillArea(int i) {
		_skillArea = i;
	}

	/**
	 * 取得魔法攻击范围变更。
	 * 
	 * @return
	 */
	public int getSkillArea() {
		if (_skillArea == 0) {
			return _skill.getArea();
		}
		return _skillArea;
	}

	/**
	 * 设定攻击倍率 (1/10)
	 * 
	 * @param i
	 */
	public void setLeverage(int i) {
		_leverage = i;
	}

	/**
	 * 取得攻击倍率 (1/10)
	 * 
	 * @return
	 */
	public int getLeverage() {
		return _leverage;
	}

	/**
	 * 检查所使用技能
	 * 
	 * @return
	 */
	private boolean isCheckedUseSkill() {
		return _checkedUseSkill;
	}

	/**
	 * 设定检查所使用技能
	 * 
	 * @param flg
	 */
	private void setCheckedUseSkill(boolean flg) {
		_checkedUseSkill = flg;
	}

	/**
	 * @param player
	 *            攻击者为PC
	 * @param skillid
	 *            技能编号
	 * @param target_id
	 *            目标OBJID
	 * @param x
	 *            X坐标
	 * @param y
	 *            Y坐标
	 * @param message
	 *            讯息
	 * @param time
	 *            时间
	 * @param type
	 *            类型
	 * @param attacker
	 *            攻击者为NPC
	 * @return
	 */
	public boolean checkUseSkill(L1PcInstance player, int skillid, int target_id, int x, int y, String message, int time, int type, L1Character attacker) {
		return checkUseSkill(player, skillid, target_id, x, y, message, time, type, attacker, 0, 0, 0);
	}

	/**
	 * @param player
	 *            攻击者为PC
	 * @param skillid
	 *            技能编号
	 * @param target_id
	 *            目标OBJID
	 * @param x
	 *            X坐标
	 * @param y
	 *            Y坐标
	 * @param message
	 *            讯息
	 * @param time
	 *            时间
	 * @param type
	 *            类型
	 * @param attacker
	 *            攻击者为NPC
	 * @param actid
	 *            动作ID
	 * @param mpConsume
	 *            消耗MP
	 * @return
	 */
	public boolean checkUseSkill(L1PcInstance player, int skillid, int target_id, int x, int y, String message, int time, int type, L1Character attacker, int actid, int gfxid, int mpConsume) {
		// 初期从这里设定
		setCheckedUseSkill(true);
		_targetList = Lists.newList(); // 目标名单的初始化

		_skill = SkillsTable.getInstance().getTemplate(skillid);
		_skillId = skillid;
		_targetX = x;
		_targetY = y;
		_message = message;
		_skillTime = time;
		_type = type;
		_actid = actid;
		_gfxid = gfxid;
		_mpConsume = mpConsume;
		boolean checkedResult = true;

		if (attacker == null) {
			// pc
			_player = player;
			_user = _player;
		}
		else {
			// npc
			_npc = (L1NpcInstance) attacker;
			_user = _npc;
		}

		if (_skill.getTarget().equals("none")) {
			_targetID = _user.getId();
			_targetX = _user.getX();
			_targetY = _user.getY();
		}
		else {
			_targetID = target_id;
		}

		if (type == TYPE_NORMAL) { // 通使用一般魔法
			checkedResult = isNormalSkillUsable();
		}
		else if (type == TYPE_SPELLSC) { // 使用魔法卷轴
			checkedResult = isSpellScrollUsable();
		}
		else if (type == TYPE_NPCBUFF) {
			checkedResult = true;
		}
		if (!checkedResult) {
			return false;
		}

		// 火牢、治愈能量风暴对象坐标
		// 精准目标的使用者坐标例外
		if ((_skillId == FIRE_WALL) || (_skillId == LIFE_STREAM) || (_skillId == TRUE_TARGET)) {
			return true;
		}

		L1Object l1object = L1World.getInstance().findObject(_targetID);
		if (l1object instanceof L1ItemInstance) {
			_log.fine("技能目标项目名称: " + ((L1ItemInstance) l1object).getViewName());
			// 精灵石可以成为技能目标。
			// Linux环境确认（Windows未确认）
			// 2008.5.4追加：对地面和物品使用魔法。继续发生错误时return
			return false;
		}
		if (_user instanceof L1PcInstance) {
			if (l1object instanceof L1PcInstance) {
				_calcType = PC_PC;
			}
			else {
				_calcType = PC_NPC;
			}
		}
		else if (_user instanceof L1NpcInstance) {
			if (l1object instanceof L1PcInstance) {
				_calcType = NPC_PC;
			}
			else if (_skill.getTarget().equals("none")) {
				_calcType = NPC_PC;
			}
			else {
				_calcType = NPC_NPC;
			}
		}

		switch (_skillId) {

		// 可使用传送控制戒指的技能
		case TELEPORT: // 法师魔法 (指定传送)
		case MASS_TELEPORT: // 法师魔法 (集体传送术)
			_bookmarkId = target_id;
			break;

		// 技能对象为道具
		case CREATE_MAGICAL_WEAPON: // 法师魔法 (创造魔法武器)
		case BRING_STONE: // 黑暗妖精魔法 (提炼魔石)
		case BLESSED_ARMOR: // 法师魔法 (铠甲护持)
		case ENCHANT_WEAPON: // 法师魔法 (拟似魔法武器)
		case SHADOW_FANG: // 黑暗妖精魔法 (暗影之牙)
			_itemobjid = target_id;
			break;
		}

		_target = (L1Character) l1object;

		// 在目标怪物以外的攻击技能、其他场合与PK模式。
		if (!(_target instanceof L1MonsterInstance) && _skill.getTarget().equals("attack") && (_user.getId() != target_id)) {
			_isPK = true;
		}

		// 到目前为止的初期设定
		// 预检查

		// 目标角色以外的情况什么也不做。
		if (!(l1object instanceof L1Character)) {
			checkedResult = false;
		}

		// 技能发动 目标清单判定
		makeTargetList();

		// 目标列表为空&&使用者为NPC
		if (_targetList.isEmpty() && (_user instanceof L1NpcInstance)) {
			checkedResult = false;
		}
		// 预先检查结果
		return checkedResult;
	}

	/**
	 * 施法时判断角色目前状态能否使用
	 * 
	 * @return false 不能使用
	 */
	private boolean isNormalSkillUsable() {

		// 检查PC使用技能的场合
		if (_user instanceof L1PcInstance) {

			// 取得使用者 (PC)
			L1PcInstance pc = (L1PcInstance) _user;

			// 传送中
			if (pc.isTeleport()) {
				return false;
			}

			// 麻痹・冻结状态
			if (pc.isParalyzed()) {
				return false;
			}

			// 隐身中无法使用技能
			if ((pc.isInvisble() || pc.isInvisDelay()) && !isInvisUsableSkill()) {
				return false;
			}

			// 负重过高
			if (pc.getInventory().getWeight242() >= 197) {
				pc.sendPackets(new S_ServerMessage(316)); // \f1你携带太多物品，因此无法使用法术。
				return false;
			}

			// 取得变身ID
			int polyId = pc.getTempCharGfx();

			// 取得变身
			L1PolyMorph poly = PolyTable.getInstance().getTemplate(polyId);

			// 有变身、该变身不能使用的技能
			if ((poly != null) && !poly.canUseSkill()) {
				pc.sendPackets(new S_ServerMessage(285)); // \f1在此状态下无法使用魔法。
				return false;
			}

			// 精灵魔法、属性不一致不能使用。
			if (!isAttrAgrees()) {
				return false;
			}

			// 精灵没有定系无法使用单属性防御
			if ((_skillId == ELEMENTAL_PROTECTION) && (pc.getElfAttr() == 0)) {
				pc.sendPackets(new S_ServerMessage(280)); // \f1施咒失败。
				return false;
			}

			// 水中无法使用火属性魔法
			if (pc.getMap().isUnderwater() && _skill.getAttr() == 2) {
				pc.sendPackets(new S_ServerMessage(280)); // \f1施咒失败。
				return false;
			}

			// 技能延迟中无法使用
			if (pc.isSkillDelay()) {
				return false;
			}

			// 魔法封印、封印禁地、卡毒、幻想
			if (pc.hasSkillEffect(SILENCE) || pc.hasSkillEffect(AREA_OF_SILENCE) || pc.hasSkillEffect(STATUS_POISON_SILENCE) || pc.hasSkillEffect(CONFUSION_ING)) {
				pc.sendPackets(new S_ServerMessage(285)); // \f1在此状态下无法使用魔法。
				return false;
			}

			// 正义值小于500不能使用 究极光裂术
			if ((_skillId == DISINTEGRATE) && (pc.getLawful() < 500)) {
				// 未确认得到这个信息
				pc.sendPackets(new S_ServerMessage(352, "$967")); // 若要使用这个法术，属性必须成为
																	// (正义)。
				return false;
			}

			// 在同样的立方体效果范围中无法再次使用
			if ((_skillId == CUBE_IGNITION // 幻术士魔法 (立方：燃烧)
					)
					|| (_skillId == CUBE_QUAKE // 幻术士魔法 (立方：地裂)
					) || (_skillId == CUBE_SHOCK // 幻术士魔法 (立方：冲击)
					) || (_skillId == CUBE_BALANCE // 幻术士魔法 (立方：和谐)
					)) {

				// 附近有无立方体
				boolean isNearSameCube = false;

				// 动画ID
				int gfxId = 0;

				for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 3)) {
					if (obj instanceof L1EffectInstance) {
						L1EffectInstance effect = (L1EffectInstance) obj;
						gfxId = effect.getGfxId();
						if (((_skillId == CUBE_IGNITION) && (gfxId == 6706)) || ((_skillId == CUBE_QUAKE) && (gfxId == 6712)) || ((_skillId == CUBE_SHOCK) && (gfxId == 6718)) || ((_skillId == CUBE_BALANCE) && (gfxId == 6724))) {
							isNearSameCube = true;
							break;
						}
					}
				}
				if (isNearSameCube) {
					pc.sendPackets(new S_ServerMessage(1412)); // 已在地板上召唤了魔法立方块。
					return false;
				}
			}

			// 觉醒状态 - 非觉醒技能无法使用
			if (((pc.getAwakeSkillId() == AWAKEN_ANTHARAS) && (_skillId != AWAKEN_ANTHARAS)) || ((pc.getAwakeSkillId() == AWAKEN_FAFURION) && (_skillId != AWAKEN_FAFURION)) || ((pc.getAwakeSkillId() == AWAKEN_VALAKAS) && (_skillId != AWAKEN_VALAKAS))
					&& (_skillId != MAGMA_BREATH) && (_skillId != SHOCK_SKIN) && (_skillId != FREEZING_BREATH)) {
				pc.sendPackets(new S_ServerMessage(1385)); // 目前状态中无法使用觉醒魔法。
				return false;
			}

			// 法术消耗道具判断。
			if ((isItemConsume() == false) && !_player.isGm()) {
				_player.sendPackets(new S_ServerMessage(299)); // \f1施放魔法所需材料不足。
				return false;
			}
		}

		// 检查使用者技能对NPC的情况
		else if (_user instanceof L1NpcInstance) {

			// 禁言状态无法使用
			if (_user.hasSkillEffect(SILENCE)) {
				// NPC禁言状态只能有1回的效果。(只能使用一次)
				_user.removeSkillEffect(SILENCE);
				return false;
			}
		}

		// PC、NPC共通检查HP、MP是否足够
		if (!isHPMPConsume()) { // 花费的HP、MP计算
			return false;
		}
		return true;
	}

	/**
	 * 判断目前状态能否使用魔法卷轴
	 * 
	 * @return false 不能使用
	 */
	private boolean isSpellScrollUsable() {

		// 要使用魔法卷轴的PC
		L1PcInstance pc = (L1PcInstance) _user;

		// 传送中
		if (pc.isTeleport()) {
			return false;
		}

		// 麻痹・冻结状态
		if (pc.isParalyzed()) {
			return false;
		}

		// 隐身中无法使用
		if ((pc.isInvisble() || pc.isInvisDelay()) && !isInvisUsableSkill()) {
			return false;
		}

		return true;
	}

	/**
	 * 隐身状态可以使用的技能
	 * 
	 * @return 可以使用、true
	 */
	private boolean isInvisUsableSkill() {
		for (int skillId : CAST_WITH_INVIS) {
			if (skillId == _skillId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * pc 施放技能判断
	 * 
	 * @param player
	 *            角色
	 * @param skillId
	 *            技能ID
	 * @param targetId
	 *            目标ID
	 * @param x
	 *            X坐标
	 * @param y
	 *            Y坐标
	 * @param message
	 *            讯息
	 * @param timeSecs
	 *            时间:秒
	 * @param type
	 *            类型
	 */
	public void handleCommands(L1PcInstance player, int skillId, int targetId, int x, int y, String message, int timeSecs, int type) {
		L1Character attacker = null;
		handleCommands(player, skillId, targetId, x, y, message, timeSecs, type, attacker);
	}

	/**
	 * 通用技能施放判断
	 * 
	 * @param player
	 *            角色
	 * @param skillId
	 *            技能ID
	 * @param targetId
	 *            目标ID
	 * @param x
	 *            X坐标
	 * @param y
	 *            Y坐标
	 * @param message
	 *            讯息
	 * @param timeSecs
	 *            时间:秒
	 * @param type
	 *            类型
	 * @param attacker
	 *            攻击者
	 */
	public void handleCommands(L1PcInstance player, int skillId, int targetId, int x, int y, String message, int timeSecs, int type, L1Character attacker) {

		try {
			// 提前检查？
			if (!isCheckedUseSkill()) {
				boolean isUseSkill = checkUseSkill(player, skillId, targetId, x, y, message, timeSecs, type, attacker);

				// 不能使用技能
				if (!isUseSkill) {
					failSkill(); // 做失败处理
					return;
				}
			}

			switch (type) {
			case TYPE_NORMAL: // 魔法咏唱时
				if (!_isGlanceCheckFail || (getSkillArea() > 0) || _skill.getTarget().equals("none")) {
					runSkill();
					useConsume();
					sendGrfx(true);
					sendFailMessageHandle();
					setDelay();
				}
				break;

			case TYPE_LOGIN: // 登陆时（不消耗材料与HPMP、没有图形）
				runSkill();
				break;

			case TYPE_SPELLSC: // 使用魔法卷轴时 (不消耗材料与HPMP)
				runSkill();
				sendGrfx(true);
				break;

			case TYPE_GMBUFF: // GMBUFF使用时（不消耗材料与HPMP、没有魔法动作）
				runSkill();
				sendGrfx(false);
				break;

			case TYPE_NPCBUFF: // NPCBUFF使用时（不消耗材料与HPMP）
				runSkill();
				sendGrfx(true);
				break;
			}
			setCheckedUseSkill(false);
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, "", e);
		}
	}

	/**
	 * 技能失败处理(仅PC)
	 */
	private void failSkill() {
		setCheckedUseSkill(false);
		switch (_skillId) {
		// 瞬移技能
		case TELEPORT:
		case MASS_TELEPORT:
		case TELEPORT_TO_MATHER:
			// 解除传送锁定状态
			_player.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
			break;
		}
	}

	/**
	 * 可加入设置目标的判断
	 * 
	 * @param cha
	 *            加入判断的目标物件
	 * @return true:可加入目标 false:不可加入目标
	 * @throws Exception
	 */
	private boolean isTarget(L1Character cha) throws Exception {

		// 角色为空
		if (cha == null) {
			return false;
		}

		// 目标为角色
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.isGhost() || pc.isGmInvis()) {
				return false;
			}
		}

		boolean _flg = false;

		// NPC 对 PC 目标为PC或PC的宠物召唤物
		if ((_calcType == NPC_PC) && ((cha instanceof L1PcInstance) || (cha instanceof L1PetInstance) || (cha instanceof L1SummonInstance))) {
			_flg = true;
		}

		// 不能破坏的门
		if (cha instanceof L1DoorInstance) {
			if ((cha.getMaxHp() == 0) || (cha.getMaxHp() == 1)) {
				return false;
			}
		}

		// 只能对魔法娃娃释放加速术
		if ((cha instanceof L1DollInstance) && (_skillId != HASTE)) {
			return false;
		}

		// 元のターゲットがPet、Summon以外のNPCの场合、PC、Pet、Summonは对象外
		if ((_calcType == PC_NPC) && (_target instanceof L1NpcInstance) && !(_target instanceof L1PetInstance) && !(_target instanceof L1SummonInstance) && ((cha instanceof L1PetInstance) || (cha instanceof L1SummonInstance) || (cha instanceof L1PcInstance))) {
			return false;
		}

		// 元のターゲットがガード以外のNPCの场合、ガードは对象外
		if ((_calcType == PC_NPC) && (_target instanceof L1NpcInstance) && !(_target instanceof L1GuardInstance) && (cha instanceof L1GuardInstance)) {
			return false;
		}

		// NPC对PCでターゲットがモンスターの场合ターゲットではない。
		if ((_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK)) && (_calcType == NPC_PC) && !(cha instanceof L1PetInstance) && !(cha instanceof L1SummonInstance) && !(cha instanceof L1PcInstance)) {
			return false;
		}

		// NPC对NPCで使用者がMOBで、ターゲットがMOBの场合ターゲットではない。
		if ((_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK)) && (_calcType == NPC_NPC) && (_user instanceof L1MonsterInstance) && (cha instanceof L1MonsterInstance)) {
			return false;
		}

		// 无方向范围攻击魔法 不能攻击NPC对象外
		if (_skill.getTarget().equals("none")
				&& (_skill.getType() == L1Skills.TYPE_ATTACK)
				&& ((cha instanceof L1AuctionBoardInstance) || (cha instanceof L1BoardInstance) || (cha instanceof L1CrownInstance) || (cha instanceof L1DwarfInstance) || (cha instanceof L1EffectInstance) || (cha instanceof L1FieldObjectInstance)
						|| (cha instanceof L1FurnitureInstance) || (cha instanceof L1HousekeeperInstance) || (cha instanceof L1MerchantInstance) || (cha instanceof L1TeleporterInstance))) {
			return false;
		}

		// 攻击型魔法无法攻击自己
		if ((_skill.getType() == L1Skills.TYPE_ATTACK) && (cha.getId() == _user.getId())) {
			return false;
		}

		// 体力回复术判断施法者不补血
		if ((cha.getId() == _user.getId()) && (_skillId == HEAL_ALL)) {
			return false;
		}

		if ((((_skill.getTargetTo() & L1Skills.TARGET_TO_PC) == L1Skills.TARGET_TO_PC) || ((_skill.getTargetTo() & L1Skills.TARGET_TO_CLAN) == L1Skills.TARGET_TO_CLAN) || ((_skill.getTargetTo() & L1Skills.TARGET_TO_PARTY) == L1Skills.TARGET_TO_PARTY))
				&& (cha.getId() == _user.getId()) && (_skillId != HEAL_ALL)) {
			return true; // ターゲットがパーティーかクラン员のものは自分に效果がある。（ただし、ヒールオールは除外）
		}

		// 技能使用者为PC、不是PK模式、自己召唤的宠物以外
		if ((_user instanceof L1PcInstance) && (_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK)) && (_isPK == false)) {
			// 目标为召唤物
			if (cha instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) cha;
				// 自己的召唤物
				if (_player.getId() == summon.getMaster().getId()) {
					return false;
				}
			}
			// 目标为宠物
			else if (cha instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) cha;
				// 自己的宠物
				if (_player.getId() == pet.getMaster().getId()) {
					return false;
				}
			}
		}

		if ((_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK))
		// 目标不是怪物
				&& !(cha instanceof L1MonsterInstance)
				// 不是PK状态
				&& (_isPK == false)
				// 目标是人物
				&& (_target instanceof L1PcInstance)) {
			L1PcInstance enemy = (L1PcInstance) cha;
			// 强力无所遁形术
			if ((_skillId == COUNTER_DETECTION) && (enemy.getZoneType() != 1) && (cha.hasSkillEffect(INVISIBILITY) || cha.hasSkillEffect(BLIND_HIDING))) {
				return true; // 隐身术或暗隐术中
			}
			if ((_player.getClanid() != 0) && (enemy.getClanid() != 0)) { // 有血盟
				// 取得全部战争列表
				for (L1War war : L1World.getInstance().getWarList()) {
					if (war.CheckClanInWar(_player.getClanname())) { // 自己在盟战中
						if (war.CheckClanInSameWar( // 俩血盟在同一场战争中
								_player.getClanname(), enemy.getClanname())) {
							if (L1CastleLocation.checkInAllWarArea(enemy.getX(), enemy.getY(), enemy.getMapId())) {
								return true;
							}
						}
					}
				}
			}
			return false; // 攻击スキルでPKモードじゃない场合
		}

		if ((_user.glanceCheck(cha.getX(), cha.getY()) == false) && (_skill.isThrough() == false)) {
			// 变形术、复活术判断有无障碍物
			if (!((_skill.getType() == L1Skills.TYPE_CHANGE) || (_skill.getType() == L1Skills.TYPE_RESTORE))) {
				_isGlanceCheckFail = true;
				return false; // 直线距离上有障碍物
			}
		}

		if (cha.hasSkillEffect(ICE_LANCE) || cha.hasSkillEffect(FREEZING_BLIZZARD) || cha.hasSkillEffect(FREEZING_BREATH) || cha.hasSkillEffect(ICE_LANCE_COCKATRICE) || cha.hasSkillEffect(ICE_LANCE_BASILISK)) {
			if (_skillId == ICE_LANCE || _skillId == FREEZING_BLIZZARD || _skillId == FREEZING_BREATH || _skillId == ICE_LANCE_COCKATRICE || _skillId == ICE_LANCE_BASILISK) {
				return false;
			}
		}

		// 大地屏障不能连续使用
		if (cha.hasSkillEffect(EARTH_BIND) && (_skillId == EARTH_BIND)) {
			return false;
		}

		// 目标不是怪物 不能使用 迷魅或造尸
		if (!(cha instanceof L1MonsterInstance) && ((_skillId == TAMING_MONSTER) || (_skillId == CREATE_ZOMBIE))) {
			return false;
		}

		// 目标已死亡 法术非复活类
		if (cha.isDead() && ((_skillId != CREATE_ZOMBIE) && (_skillId != RESURRECTION) && (_skillId != GREATER_RESURRECTION) && (_skillId != CALL_OF_NATURE))) {
			return false;
		}

		// 目标未死亡 法术复活类
		if ((cha.isDead() == false) && ((_skillId == CREATE_ZOMBIE) || (_skillId == RESURRECTION) || (_skillId == GREATER_RESURRECTION) || (_skillId == CALL_OF_NATURE))) {
			return false;
		}

		// 塔跟门不可放复活法术
		if (((cha instanceof L1TowerInstance) || (cha instanceof L1DoorInstance)) && ((_skillId == CREATE_ZOMBIE) || (_skillId == RESURRECTION) || (_skillId == GREATER_RESURRECTION) || (_skillId == CALL_OF_NATURE))) {
			return false;
		}

		// 对绝对屏障有效的魔法
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) { // 绝对屏障状态中
				switch (_skillId) {
				case CURSE_BLIND:
				case WEAPON_BREAK:
				case DARKNESS:
				case WEAKNESS:
				case DISEASE:
				case FOG_OF_SLEEPING:
				case MASS_SLOW:
				case SLOW:
				case CANCELLATION:
				case SILENCE:
				case DECAY_POTION:
				case MASS_TELEPORT:
				case DETECTION:
				case COUNTER_DETECTION:
				case ERASE_MAGIC:
				case ENTANGLE:
				case PHYSICAL_ENCHANT_DEX:
				case PHYSICAL_ENCHANT_STR:
				case BLESS_WEAPON:
				case EARTH_SKIN:
				case IMMUNE_TO_HARM:
				case REMOVE_CURSE:
					return true;
				default:
					return false;
				}
			}
		}

		// 对隐藏状态的怪物有效的魔法 (钻地)
		if (cha instanceof L1NpcInstance) {
			int hiddenStatus = ((L1NpcInstance) cha).getHiddenStatus();
			switch (hiddenStatus) {
			case L1NpcInstance.HIDDEN_STATUS_SINK:
				switch (_skillId) {
				case DETECTION:
				case COUNTER_DETECTION: // 无所遁形、强力无所遁形
					return true;
				}
				return false;

			case L1NpcInstance.HIDDEN_STATUS_FLY: // 对飞天的怪物无效
				return false;
			}
		}

		// 目标PC
		if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PC) == L1Skills.TARGET_TO_PC) && (cha instanceof L1PcInstance)) {
			_flg = true;
		}
		// 目标NPC
		else if (((_skill.getTargetTo() & L1Skills.TARGET_TO_NPC) == L1Skills.TARGET_TO_NPC) && ((cha instanceof L1MonsterInstance) || (cha instanceof L1NpcInstance) || (cha instanceof L1SummonInstance) || (cha instanceof L1PetInstance))) {
			_flg = true;
		}
		// 目标 召唤怪与宠物
		else if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PET) == L1Skills.TARGET_TO_PET) && (_user instanceof L1PcInstance)) {
			if (cha instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (summon.getMaster() != null) {
					if (_player.getId() == summon.getMaster().getId()) {
						_flg = true;
					}
				}
			}
			else if (cha instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) cha;
				if (pet.getMaster() != null) {
					if (_player.getId() == pet.getMaster().getId()) {
						_flg = true;
					}
				}
			}
		}

		if ((_calcType == PC_PC) && (cha instanceof L1PcInstance)) {
			// 目标血盟
			if (((_skill.getTargetTo() & L1Skills.TARGET_TO_CLAN) == L1Skills.TARGET_TO_CLAN) && (((_player.getClanid() != 0) && (_player.getClanid() == ((L1PcInstance) cha).getClanid())) || _player.isGm())) {
				return true;
			}
			// 目标组队
			if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PARTY) == L1Skills.TARGET_TO_PARTY) && (_player.getParty().isMember((L1PcInstance) cha) || _player.isGm())) {
				return true;
			}
		}

		return _flg;
	}

	/**
	 * 技能发动 目标清单判定
	 */
	private void makeTargetList() {
		try {
			if (_type == TYPE_LOGIN) { // 仅用于登录时 (死亡时 包括取消死亡状态？)
				_targetList.add(new TargetStatus(_user));
				return;
			}
			if ((_skill.getTargetTo() == L1Skills.TARGET_TO_ME) && ((_skill.getType() & L1Skills.TYPE_ATTACK) != L1Skills.TYPE_ATTACK)) {
				_targetList.add(new TargetStatus(_user)); // 目标使用者
				return;
			}

			// 具有攻击范围 (画面内的对象)
			if (getSkillRanged() != -1) {
				if (_user.getLocation().getTileLineDistance(_target.getLocation()) > getSkillRanged()) {
					return; // 射程范围外
				}
			}
			else {
				if (!_user.getLocation().isInScreen(_target.getLocation())) {
					return; // 射程范围外
				}
			}

			if ((isTarget(_target) == false) && !(_skill.getTarget().equals("none"))) {
				// 对象が违うのでスキルが発动しない。
				return;
			}

			// 直线的范围
			switch (_skillId) {
			case LIGHTNING:
			case FREEZING_BREATH: // 极光雷电、寒冰喷吐
				List<L1Object> al1object = L1World.getInstance().getVisibleLineObjects(_user, _target);

				for (L1Object tgobj : al1object) {
					if (tgobj == null) {
						continue;
					}
					if (!(tgobj instanceof L1Character)) { // 角色没有目标的场合。
						continue;
					}
					L1Character cha = (L1Character) tgobj;
					if (isTarget(cha) == false) {
						continue;
					}
					_targetList.add(new TargetStatus(cha));
				}
				return;
			}

			// 单体攻击
			if (getSkillArea() == 0) {
				if (!_user.glanceCheck(_target.getX(), _target.getY())) { // 直线上有障碍物
					if (((_skill.getType() & L1Skills.TYPE_ATTACK) == L1Skills.TYPE_ATTACK) && (_skillId != 10026) && (_skillId != 10027) && (_skillId != 10028) && (_skillId != 10029)) { // 安息攻击以外の攻击スキル
						_targetList.add(new TargetStatus(_target, false)); // 不能发生伤害、ダメージモーションも発生しないが、スキルは発动
						return;
					}
				}
				_targetList.add(new TargetStatus(_target));
			}

			// 范围攻击
			else {
				if (!_skill.getTarget().equals("none")) {
					_targetList.add(new TargetStatus(_target));
				}

				// 法师魔法 (体力回复术)
				if ((_skillId != HEAL_ALL) && !(_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK))) {
					// 攻击系以外のスキルとH-A以外はターゲット自身を含める
					_targetList.add(new TargetStatus(_user));
				}

				List<L1Object> objects;

				// 全画面物件
				if (getSkillArea() == -1) {
					objects = L1World.getInstance().getVisibleObjects(_user);
				}
				// 指定范围物件
				else {
					objects = L1World.getInstance().getVisibleObjects(_target, getSkillArea());
				}
				for (L1Object tgobj : objects) {
					if (tgobj == null) {
						continue;
					}
					if (!(tgobj instanceof L1Character)) { // ターゲットがキャラクター以外の场合何もしない。
						continue;
					}
					L1Character cha = (L1Character) tgobj;
					if (!isTarget(cha)) {
						continue;
					}

					// 技能发动 目标清单判定:加入目标清单 - 回圈
					_targetList.add(new TargetStatus(cha));
				}
				return;
			}

		}
		catch (Exception e) {
			_log.log(Level.FINEST, "exception in L1Skilluse makeTargetList{0}", e);
		}
	}

	/**
	 * 发送发生的信息（发生了什么事时）
	 * 
	 * @param pc
	 */
	private void sendHappenMessage(L1PcInstance pc) {
		int msgID = _skill.getSysmsgIdHappen();
		if (msgID > 0) {
			// 效果讯息排除施法者本身。
			if (_skillId == AREA_OF_SILENCE && _user.getId() == pc.getId()) { // 封印禁地
				return;
			}
			pc.sendPackets(new S_ServerMessage(msgID));
		}
	}

	/** 处理失败的消息显示 */
	private void sendFailMessageHandle() {
		// 攻击スキル以外で对象を指定するスキルが失败した场合は失败したメッセージをクライアントに送信
		// ※攻击スキルは障害物があっても成功时と同じアクションであるべき。
		if ((_skill.getType() != L1Skills.TYPE_ATTACK) && !_skill.getTarget().equals("none") && _targetList.isEmpty()) {
			sendFailMessage();
		}
	}

	/** 发送失败信息（失败） */
	private void sendFailMessage() {
		int msgID = _skill.getSysmsgIdFail();
		if ((msgID > 0) && (_user instanceof L1PcInstance)) {
			_player.sendPackets(new S_ServerMessage(msgID));
		}
	}

	/** 精灵魔法的属性与使用者的属性不一致 */
	private boolean isAttrAgrees() {
		int magicattr = _skill.getAttr();
		if (_user instanceof L1NpcInstance) { // NPC使用的任何场合都OK
			return true;
		}

		if ((_skill.getSkillLevel() >= 17) && (_skill.getSkillLevel() <= 22) && (magicattr != 0 // 精灵魔法、无属性魔法、
				) && (magicattr != _player.getElfAttr() // 使用者魔法的属性不一致。
				) && !_player.isGm()) { // GM例外
			return false;
		}
		return true;
	}

	/**
	 * 判断技能的使用是否需要消耗HP/MP
	 * 
	 * @return
	 */
	private boolean isHPMPConsume() {
		if (_mpConsume == 0) {
			_mpConsume = _skill.getMpConsume();
		}
		_hpConsume = _skill.getHpConsume();
		int currentMp = 0;
		int currentHp = 0;

		if (_user instanceof L1NpcInstance) {
			currentMp = _npc.getCurrentMp();
			currentHp = _npc.getCurrentHp();
		}
		else {
			currentMp = _player.getCurrentMp();
			currentHp = _player.getCurrentHp();

			// 智力的MP减免
			if ((_player.getInt() > 12) && (_skillId > HOLY_WEAPON) && (_skillId <= FREEZING_BLIZZARD)) { // LV2以上
				_mpConsume--;
			}
			if ((_player.getInt() > 13) && (_skillId > STALAC) && (_skillId <= FREEZING_BLIZZARD)) { // LV3以上
				_mpConsume--;
			}
			if ((_player.getInt() > 14) && (_skillId > WEAK_ELEMENTAL) && (_skillId <= FREEZING_BLIZZARD)) { // LV4以上
				_mpConsume--;
			}
			if ((_player.getInt() > 15) && (_skillId > MEDITATION) && (_skillId <= FREEZING_BLIZZARD)) { // LV5以上
				_mpConsume--;
			}
			if ((_player.getInt() > 16) && (_skillId > DARKNESS) && (_skillId <= FREEZING_BLIZZARD)) { // LV6以上
				_mpConsume--;
			}
			if ((_player.getInt() > 17) && (_skillId > BLESS_WEAPON) && (_skillId <= FREEZING_BLIZZARD)) { // LV7以上
				_mpConsume--;
			}
			if ((_player.getInt() > 18) && (_skillId > DISEASE) && (_skillId <= FREEZING_BLIZZARD)) { // LV8以上
				_mpConsume--;
			}

			// 骑士智力减免
			if ((_player.getInt() > 12) && (_skillId >= SHOCK_STUN) && (_skillId <= COUNTER_BARRIER)) {
				if (_player.getInt() <= 17)
					_mpConsume -= (_player.getInt() - 12);
				else {
					_mpConsume -= 5; // int > 18
					if (_mpConsume > 1) { // 法术还可以减免
						byte extraInt = (byte) (_player.getInt() - 17);
						// 减免公式
						for (int first = 1, range = 2; first <= extraInt; first += range, range++)
							_mpConsume--;
					}
				}

			}

			// 装备MP减免 一次只需判断一个
			if ((_skillId == PHYSICAL_ENCHANT_DEX) && _player.getInventory().checkEquipped(20013)) { // 敏捷魔法头盔使用通畅气脉术
				_mpConsume /= 2;
			}
			else if ((_skillId == HASTE) && _player.getInventory().checkEquipped(20013)) { // 敏捷魔法头盔使用加速术
				_mpConsume /= 2;
			}
			else if ((_skillId == HEAL) && _player.getInventory().checkEquipped(20014)) { // 治愈魔法头盔使用初级治愈术
				_mpConsume /= 2;
			}
			else if ((_skillId == EXTRA_HEAL) && _player.getInventory().checkEquipped(20014)) { // 治愈魔法头盔使用中级治愈术
				_mpConsume /= 2;
			}
			else if ((_skillId == ENCHANT_WEAPON) && _player.getInventory().checkEquipped(20015)) { // 力量魔法头盔使用拟似魔法武器
				_mpConsume /= 2;
			}
			else if ((_skillId == DETECTION) && _player.getInventory().checkEquipped(20015)) { // 力量魔法头盔使用无所遁形术
				_mpConsume /= 2;
			}
			else if ((_skillId == PHYSICAL_ENCHANT_STR) && _player.getInventory().checkEquipped(20015)) { // 力量魔法头盔使用体魄强健术
				_mpConsume /= 2;
			}
			else if ((_skillId == HASTE) && _player.getInventory().checkEquipped(20008)) { // 小型风之头盔使用加速术
				_mpConsume /= 2;
			}
			else if ((_skillId == HASTE) && _player.getInventory().checkEquipped(20023)) { // 风之头盔使用加速术
				_mpConsume = 25;
			}
			else if ((_skillId == GREATER_HASTE) && _player.getInventory().checkEquipped(20023)) { // 风之头盔使用强力加速术
				_mpConsume /= 2;
			}

			// 初始能力减免
			if (_player.getOriginalMagicConsumeReduction() > 0) {
				_mpConsume -= _player.getOriginalMagicConsumeReduction();
			}

			if (0 < _skill.getMpConsume()) {
				_mpConsume = Math.max(_mpConsume, 1); // 最小值 1
			}
		}

		if (currentHp < _hpConsume + 1) {
			if (_user instanceof L1PcInstance) {
				_player.sendPackets(new S_ServerMessage(279)); // \f1因体力不足而无法使用魔法。
			}
			return false;
		}
		else if (currentMp < _mpConsume) {
			if (_user instanceof L1PcInstance) {
				_player.sendPackets(new S_ServerMessage(278)); // \f1因魔力不足而无法使用魔法。
			}
			return false;
		}

		return true;
	}

	/**
	 * 判断技能的使用是否需要其他物品
	 * 
	 * @return
	 */
	private boolean isItemConsume() {

		int itemConsume = _skill.getItemConsumeId();
		int itemConsumeCount = _skill.getItemConsumeCount();

		// 施放魔法不需要材料
		if (itemConsume == 0) {
			return true;
		}

		// 必要材料不足。
		if (!_player.getInventory().checkItem(itemConsume, itemConsumeCount)) {
			return false;
		}

		return true;
	}

	/**
	 * 使用技能后，相应的HP和MP、Lawful、材料的减少
	 */
	private void useConsume() {

		// 如果为NPC、仅减少HP、MP
		if (_user instanceof L1NpcInstance) {
			int current_hp = _npc.getCurrentHp() - _hpConsume;
			_npc.setCurrentHp(current_hp);

			int current_mp = _npc.getCurrentMp() - _mpConsume;
			_npc.setCurrentMp(current_mp);
			return;
		}

		// HP・MP消耗 已经计算使用量
		if (_skillId == FINAL_BURN) { // 会心一击
			_player.setCurrentHp(1);
			_player.setCurrentMp(0);
		}
		else {
			int current_hp = _player.getCurrentHp() - _hpConsume;
			_player.setCurrentHp(current_hp);

			int current_mp = _player.getCurrentMp() - _mpConsume;
			_player.setCurrentMp(current_mp);
		}

		// 减去Lawful
		int lawful = _player.getLawful() + _skill.getLawful();
		if (lawful > 32767) {
			lawful = 32767;
		}
		else if (lawful < -32767) {
			lawful = -32767;
		}
		_player.setLawful(lawful);

		int itemConsume = _skill.getItemConsumeId();
		int itemConsumeCount = _skill.getItemConsumeCount();

		if (itemConsume == 0) {
			return; // 施放魔法没有必要材料
		}

		// 减去使用材料
		_player.getInventory().consumeItem(itemConsume, itemConsumeCount);
	}

	/**
	 * 使用相应技能要对玩家人物或者使用的相应道具追加效果时间
	 */
	private void addMagicList(L1Character cha, boolean repetition) {
		if (_skillTime == 0) {
			_getBuffDuration = _skill.getBuffDuration() * 1000; // 效果时间
			if (_skill.getBuffDuration() == 0) {
				if (_skillId == INVISIBILITY) { // 隐身术
					cha.setSkillEffect(INVISIBILITY, 0);
				}
				return;
			}
		}
		else {
			_getBuffDuration = _skillTime * 1000; // 如果参数time非0、设定效果时间
		}

		if (_skillId == SHOCK_STUN) {
			_getBuffDuration = _shockStunDuration;
		}
		if (_skillId == CURSE_POISON) { // 毒咒効果移至L1Poison处理。
			return;
		}
		if ((_skillId == CURSE_PARALYZE) || (_skillId == CURSE_PARALYZE2)) { // 木乃伊的咀咒、石化持续时间移至
																				// L1CurseParalysis
																				// 处理。
			return;
		}
		if (_skillId == SHAPE_CHANGE) { // 变形术持续时间移至 L1PolyMorph 处理。
			return;
		}
		if ((_skillId == BLESSED_ARMOR) || (_skillId == HOLY_WEAPON // 武器・防具に效果がある处理はL1ItemInstanceに移让。
				) || (_skillId == ENCHANT_WEAPON) || (_skillId == BLESS_WEAPON) || (_skillId == SHADOW_FANG)) {
			return;
		}
		if (((_skillId == ICE_LANCE) || (_skillId == FREEZING_BLIZZARD) || (_skillId == FREEZING_BREATH) || (_skillId == ICE_LANCE_COCKATRICE) || (_skillId == ICE_LANCE_BASILISK)) && !_isFreeze) { // 冻结失败
			return;
		}
		if ((_skillId == AWAKEN_ANTHARAS) || (_skillId == AWAKEN_FAFURION) || (_skillId == AWAKEN_VALAKAS)) { // 觉醒の效果处理はL1Awakeに移让。
			return;
		}
		// 骷髅毁坏持续时间另外处理
		if (_skillId == BONE_BREAK || _skillId == CONFUSION) {
			return;
		}
		cha.setSkillEffect(_skillId, _getBuffDuration);

		if (_skillId == ELEMENTAL_FALL_DOWN && repetition) { // 弱化属性重复施放
			if (_skillTime == 0) {
				_getBuffIconDuration = _skill.getBuffDuration(); // 效果时间
			}
			else {
				_getBuffIconDuration = _skillTime;
			}
			_target.removeSkillEffect(ELEMENTAL_FALL_DOWN);
			runSkill();
			return;
		}
		if ((cha instanceof L1PcInstance) && repetition) { // 对象がPCで既にスキルが重复している场合
			L1PcInstance pc = (L1PcInstance) cha;
			sendIcon(pc);
		}
	}

	/**
	 * 发送技能图示
	 * 
	 * @param pc
	 */
	private void sendIcon(L1PcInstance pc) {
		if (_skillTime == 0) {
			_getBuffIconDuration = _skill.getBuffDuration(); // 效果时间
		}
		else {
			_getBuffIconDuration = _skillTime; // パラメータのtimeが0以外なら、效果时间として设定する
		}

		switch (_skillId) {
		case SHIELD: // 防护罩
			pc.sendPackets(new S_SkillIconShield(5, _getBuffIconDuration));
			break;

		case SHADOW_ARMOR: // 影之防护
			pc.sendPackets(new S_SkillIconShield(3, _getBuffIconDuration));
			break;

		case DRESS_DEXTERITY: // 敏捷提升
			pc.sendPackets(new S_Dexup(pc, 2, _getBuffIconDuration));
			break;

		case DRESS_MIGHTY: // 力量提升
			pc.sendPackets(new S_Strup(pc, 2, _getBuffIconDuration));
			break;

		case GLOWING_AURA: // 激励士气
			pc.sendPackets(new S_SkillIconAura(113, _getBuffIconDuration));
			break;

		case SHINING_AURA: // 钢铁士气
			pc.sendPackets(new S_SkillIconAura(114, _getBuffIconDuration));
			break;

		case BRAVE_AURA: // 冲击士气
			pc.sendPackets(new S_SkillIconAura(116, _getBuffIconDuration));
			break;

		case FIRE_WEAPON: // 火焰武器
			pc.sendPackets(new S_SkillIconAura(147, _getBuffIconDuration));
			break;

		case WIND_SHOT: // 风之神射
			pc.sendPackets(new S_SkillIconAura(148, _getBuffIconDuration));
			break;

		case FIRE_BLESS: // 烈炎气息
			pc.sendPackets(new S_SkillIconAura(154, _getBuffIconDuration));
			break;

		case STORM_EYE: // 暴风之眼
			pc.sendPackets(new S_SkillIconAura(155, _getBuffIconDuration));
			break;

		case EARTH_BLESS: // 大地的祝福
			pc.sendPackets(new S_SkillIconShield(7, _getBuffIconDuration));
			break;

		case BURNING_WEAPON: // 烈炎武器
			pc.sendPackets(new S_SkillIconAura(162, _getBuffIconDuration));
			break;

		case STORM_SHOT: // 暴风神射
			pc.sendPackets(new S_SkillIconAura(165, _getBuffIconDuration));
			break;

		case IRON_SKIN: // 钢铁防护
			pc.sendPackets(new S_SkillIconShield(10, _getBuffIconDuration));
			break;

		case EARTH_SKIN: // 大地防护
			pc.sendPackets(new S_SkillIconShield(6, _getBuffIconDuration));
			break;

		case PHYSICAL_ENCHANT_STR: // 体魄强健术：STR
			pc.sendPackets(new S_Strup(pc, 5, _getBuffIconDuration));
			break;

		case PHYSICAL_ENCHANT_DEX: // 通畅气脉术：DEX
			pc.sendPackets(new S_Dexup(pc, 5, _getBuffIconDuration));
			break;

		case HASTE:
		case GREATER_HASTE: // 加速术,强力加速术
			pc.sendPackets(new S_SkillHaste(pc.getId(), 1, _getBuffIconDuration));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 1, 0));
			break;

		case HOLY_WALK:
		case MOVING_ACCELERATION:
		case WIND_WALK: // 神圣疾走、行走加速、风之疾走
			pc.sendPackets(new S_SkillBrave(pc.getId(), 4, _getBuffIconDuration));
			pc.broadcastPacket(new S_SkillBrave(pc.getId(), 4, 0));
			break;

		case BLOODLUST: // 血之渴望
			pc.sendPackets(new S_SkillBrave(pc.getId(), 6, _getBuffIconDuration));
			pc.broadcastPacket(new S_SkillBrave(pc.getId(), 6, 0));
			break;

		case SLOW:
		case MASS_SLOW:
		case ENTANGLE: // 缓速、集体缓速、地面障碍
			pc.sendPackets(new S_SkillHaste(pc.getId(), 2, _getBuffIconDuration));
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 2, 0));
			break;

		case IMMUNE_TO_HARM: // 圣结界
			pc.sendPackets(new S_SkillIconGFX(40, _getBuffIconDuration));
			break;

		case WIND_SHACKLE: // 风之枷锁
			pc.sendPackets(new S_SkillIconWindShackle(pc.getId(), _getBuffIconDuration));
			pc.broadcastPacket(new S_SkillIconWindShackle(pc.getId(), _getBuffIconDuration));
			break;
		}
		pc.sendPackets(new S_OwnCharStatus(pc));
	}

	/**
	 * 技能使用完毕后发送结束提示图标
	 * 
	 * @param isSkillAction
	 */
	private void sendGrfx(boolean isSkillAction) {
		if (_actid == 0) {
			_actid = _skill.getActionId();
		}
		if (_gfxid == 0) {
			_gfxid = _skill.getCastGfx();
		}
		if (_gfxid == 0) {
			return; // 如果图形显示 无
		}
		int[] data = null;

		// 使用者为PC
		if (_user instanceof L1PcInstance) {

			int targetid = 0;
			if (_skillId != FIRE_WALL) {
				targetid = _target.getId();
			}
			L1PcInstance pc = (L1PcInstance) _user;

			switch (_skillId) {
			case FIRE_WALL: // 火牢
			case LIFE_STREAM: // 治愈能量风暴
			case ELEMENTAL_FALL_DOWN: // 弱化属性
				if (_skillId == FIRE_WALL) {
					pc.setHeading(pc.targetDirection(_targetX, _targetY));
					pc.sendPackets(new S_ChangeHeading(pc));
					pc.broadcastPacket(new S_ChangeHeading(pc));
				}
				S_DoActionGFX gfx = new S_DoActionGFX(pc.getId(), _actid);
				pc.sendPackets(gfx);
				pc.broadcastPacket(gfx);
				return;
			case SHOCK_STUN: // 冲击之晕
				if (_targetList.isEmpty()) { // 失败
					return;
				}
				else {
					if (_target instanceof L1PcInstance) {
						L1PcInstance targetPc = (L1PcInstance) _target;
						targetPc.sendPackets(new S_SkillSound(targetid, 4434));
						targetPc.broadcastPacket(new S_SkillSound(targetid, 4434));
					}
					else if (_target instanceof L1NpcInstance) {
						_target.broadcastPacket(new S_SkillSound(targetid, 4434));
					}
					return;
				}
			case LIGHT: // 日光术
				pc.sendPackets(new S_Sound(145));
				break;
			case MIND_BREAK: // 心灵破坏
			case JOY_OF_PAIN: // 疼痛的欢愉
				data = new int[] { _actid, _dmg, 0 }; // data = {actid, dmg,
														// effect}
				pc.sendPackets(new S_AttackPacket(pc, targetid, data));
				pc.broadcastPacket(new S_AttackPacket(pc, targetid, data));
				pc.sendPackets(new S_SkillSound(targetid, _gfxid));
				pc.broadcastPacket(new S_SkillSound(targetid, _gfxid));
				return;
			case CONFUSION: // 混乱
				data = new int[] { _actid, _dmg, 0 }; // data = {actid, dmg,
														// effect}
				pc.sendPackets(new S_AttackPacket(pc, targetid, data));
				pc.broadcastPacket(new S_AttackPacket(pc, targetid, data));
				return;
			case SMASH: // 暴击
				pc.sendPackets(new S_SkillSound(targetid, _gfxid));
				pc.broadcastPacket(new S_SkillSound(targetid, _gfxid));
				return;
			case TAMING_MONSTER: // 迷魅
				pc.sendPackets(new S_EffectLocation(_targetX, _targetY, _gfxid));
				pc.broadcastPacket(new S_EffectLocation(_targetX, _targetY, _gfxid));
				return;
			default:
				break;
			}

			if (_targetList.isEmpty() && !(_skill.getTarget().equals("none"))) {

				int tempchargfx = _player.getTempCharGfx();

				switch (tempchargfx) {
				case 5727:
				case 5730:
					_actid = ActionCodes.ACTION_SkillBuff;
					break;

				case 5733:
				case 5736:
					_actid = ActionCodes.ACTION_Attack;
					break;
				}
				if (isSkillAction) {
					S_DoActionGFX gfx = new S_DoActionGFX(_player.getId(), _actid);
					_player.sendPackets(gfx);
					_player.broadcastPacket(gfx);
				}
				return;
			}

			if (_skill.getTarget().equals("attack") && (_skillId != 18)) {
				if (isPcSummonPet(_target)) { // 目标玩家、宠物、召唤兽
					if ((_player.getZoneType() == 1) // 自己在安全区
							|| (_target.getZoneType() == 1) // 目标在安全区
							|| _player.checkNonPvP(_player, _target)) { // Non-PvP设定
						data = new int[] { _actid, 0, _gfxid, 6 };
						_player.sendPackets(new S_UseAttackSkill(_player, _target.getId(), _targetX, _targetY, data));
						_player.broadcastPacket(new S_UseAttackSkill(_player, _target.getId(), _targetX, _targetY, data));
						return;
					}
				}

				// 单体攻击魔法 (NPC / PC 技能使用)
				if (getSkillArea() == 0) {
					data = new int[] { _actid, _dmg, _gfxid, 6 };
					_player.sendPackets(new S_UseAttackSkill(_player, targetid, _targetX, _targetY, data));
					_player.broadcastPacket(new S_UseAttackSkill(_player, targetid, _targetX, _targetY, data));
					_target.broadcastPacketExceptTargetSight(new S_DoActionGFX(targetid, ActionCodes.ACTION_Damage), _player);
				}

				// 有方向范围攻击魔法
				else {
					L1Character[] cha = new L1Character[_targetList.size()];
					int i = 0;
					for (TargetStatus ts : _targetList) {
						cha[i] = ts.getTarget();
						i++;
					}
					_player.sendPackets(new S_RangeSkill(_player, cha, _gfxid, _actid, S_RangeSkill.TYPE_DIR));
					_player.broadcastPacket(new S_RangeSkill(_player, cha, _gfxid, _actid, S_RangeSkill.TYPE_DIR));
				}
			}

			// 无方向范围攻击魔法
			else if (_skill.getTarget().equals("none") && (_skill.getType() == L1Skills.TYPE_ATTACK)) { // 无方向范围攻击魔法
				L1Character[] cha = new L1Character[_targetList.size()];
				int i = 0;
				for (TargetStatus ts : _targetList) {
					cha[i] = ts.getTarget();
					cha[i].broadcastPacketExceptTargetSight(new S_DoActionGFX(cha[i].getId(), ActionCodes.ACTION_Damage), _player);
					i++;
				}
				_player.sendPackets(new S_RangeSkill(_player, cha, _gfxid, _actid, S_RangeSkill.TYPE_NODIR));
				_player.broadcastPacket(new S_RangeSkill(_player, cha, _gfxid, _actid, S_RangeSkill.TYPE_NODIR));
			}

			// 辅助魔法
			else {
				// 指定传送、集体传送术、世界树的呼唤以外
				if ((_skillId != TELEPORT) && (_skillId != MASS_TELEPORT) && (_skillId != TELEPORT_TO_MATHER)) {
					// 施法动作
					if (isSkillAction) {
						S_DoActionGFX gfx = new S_DoActionGFX(_player.getId(), _skill.getActionId());
						_player.sendPackets(gfx);
						_player.broadcastPacket(gfx);
					}
					// 魔法屏障、反击屏障、镜反射 魔法效果只有自身显示
					if ((_skillId == COUNTER_MAGIC) || (_skillId == COUNTER_BARRIER) || (_skillId == COUNTER_MIRROR)) {
						_player.sendPackets(new S_SkillSound(targetid, _gfxid));
					}
					else if ((_skillId == AWAKEN_ANTHARAS // 觉醒：安塔瑞斯
							)
							|| (_skillId == AWAKEN_FAFURION // 觉醒：法利昂
							) || (_skillId == AWAKEN_VALAKAS)) { // 觉醒：巴拉卡斯
						if (_skillId == _player.getAwakeSkillId()) { // 再次使用就解除觉醒状态
							_player.sendPackets(new S_SkillSound(targetid, _gfxid));
							_player.broadcastPacket(new S_SkillSound(targetid, _gfxid));
						}
						else {
							return;
						}
					}
					else {
						_player.sendPackets(new S_SkillSound(targetid, _gfxid));
						_player.broadcastPacket(new S_SkillSound(targetid, _gfxid));
					}
				}

				// スキルのエフェクト表示はターゲット全员だが、あまり必要性がないので、ステータスのみ送信
				for (TargetStatus ts : _targetList) {
					L1Character cha = ts.getTarget();
					if (cha instanceof L1PcInstance) {
						L1PcInstance chaPc = (L1PcInstance) cha;
						chaPc.sendPackets(new S_OwnCharStatus(chaPc));
					}
				}
			}
		}

		// 施法者为NPC
		else if (_user instanceof L1NpcInstance) {
			int targetid = _target.getId();

			if (_user instanceof L1MerchantInstance) {
				_user.broadcastPacket(new S_SkillSound(targetid, _gfxid));
				return;
			}

			if (_skillId == CURSE_PARALYZE || _skillId == WEAKNESS || _skillId == DISEASE) { // 木乃伊的诅咒、弱化术、疾病术
				_user.setHeading(_user.targetDirection(_targetX, _targetY)); // 改变面向
				_user.broadcastPacket(new S_ChangeHeading(_user));
			}

			if (_targetList.isEmpty() && !(_skill.getTarget().equals("none"))) {
				// ターゲット数が０で对象を指定するスキルの场合、魔法使用エフェクトだけ表示して终了
				S_DoActionGFX gfx = new S_DoActionGFX(_user.getId(), _actid);
				_user.broadcastPacket(gfx);
				return;
			}

			if (_skill.getTarget().equals("attack") && (_skillId != 18)) {
				if (getSkillArea() == 0) { // 单体攻击魔法
					data = new int[] { _actid, _dmg, _gfxid, 6 };
					_user.broadcastPacket(new S_UseAttackSkill(_user, targetid, _targetX, _targetY, data));
					_target.broadcastPacketExceptTargetSight(new S_DoActionGFX(targetid, ActionCodes.ACTION_Damage), _user);
				}
				else { // 有方向范围攻击魔法
					L1Character[] cha = new L1Character[_targetList.size()];
					int i = 0;
					for (TargetStatus ts : _targetList) {
						cha[i] = ts.getTarget();
						cha[i].broadcastPacketExceptTargetSight(new S_DoActionGFX(cha[i].getId(), ActionCodes.ACTION_Damage), _user);
						i++;
					}
					_user.broadcastPacket(new S_RangeSkill(_user, cha, _gfxid, _actid, S_RangeSkill.TYPE_DIR));
				}
			}

			// 无方向范围魔法
			else if (_skill.getTarget().equals("none") && (_skill.getType() == L1Skills.TYPE_ATTACK)) { // 无方向范围攻击魔法
				L1Character[] cha = new L1Character[_targetList.size()];
				int i = 0;
				for (TargetStatus ts : _targetList) {
					cha[i] = ts.getTarget();
					i++;
				}
				_user.broadcastPacket(new S_RangeSkill(_user, cha, _gfxid, _actid, S_RangeSkill.TYPE_NODIR));
			}

			// 辅助魔法
			else {
				// テレポート、マステレ、テレポートトゥマザー以外
				if ((_skillId != 5) && (_skillId != 69) && (_skillId != 131)) {
					// 魔法を使う动作のエフェクトは使用者だけ
					S_DoActionGFX gfx = new S_DoActionGFX(_user.getId(), _actid);
					_user.broadcastPacket(gfx);
					_user.broadcastPacket(new S_SkillSound(targetid, _gfxid));
				}
			}
		}
	}

	/**
	 * 删除不能重复/同时使用的技能，图标更改为刚使用时的图标
	 * 
	 * @param cha
	 */
	private void deleteRepeatedSkills(L1Character cha) {
		final int[][] repeatedSkills = {

				// 火焰武器、风之神射、烈炎气息、暴风之眼、烈炎武器、暴风神射、妈祖的祝福
				{ FIRE_WEAPON, WIND_SHOT, FIRE_BLESS, STORM_EYE, BURNING_WEAPON, STORM_SHOT, EFFECT_BLESS_OF_MAZU },
				// 防护罩、影之防护、大地防护、大地的祝福、钢铁防护
				{ SHIELD, SHADOW_ARMOR, EARTH_SKIN, EARTH_BLESS, IRON_SKIN },
				// 勇敢药水、精灵饼干、(神圣疾走、行走加速、风之疾走)、超级加速、血之渴望
				{ STATUS_BRAVE, STATUS_ELFBRAVE, HOLY_WALK, MOVING_ACCELERATION, WIND_WALK, STATUS_BRAVE2, BLOODLUST },
				// 加速术、强力加速术、自我加速药水
				{ HASTE, GREATER_HASTE, STATUS_HASTE },
				// 缓速、集体缓术、地面障碍
				{ SLOW, MASS_SLOW, ENTANGLE },
				// 通畅气脉术、敏捷提升
				{ PHYSICAL_ENCHANT_DEX, DRESS_DEXTERITY },
				// 体魄强健术、力量提升
				{ PHYSICAL_ENCHANT_STR, DRESS_MIGHTY },
				// 激励士气、钢铁士气
				{ GLOWING_AURA, SHINING_AURA },
				// 镜像、暗影闪避
				{ MIRROR_IMAGE, UNCANNY_DODGE } };

		for (int[] skills : repeatedSkills) {
			for (int id : skills) {
				if (id == _skillId) {
					stopSkillList(cha, skills);
				}
			}
		}
	}

	/**
	 * 删除全部重复的正在使用的技能
	 * 
	 * @param cha
	 * @param repeat_skill
	 */
	private void stopSkillList(L1Character cha, int[] repeat_skill) {
		for (int skillId : repeat_skill) {
			if (skillId != _skillId) {
				cha.removeSkillEffect(skillId);
			}
		}
	}

	// 设定技能使用延迟
	private void setDelay() {
		if (_skill.getReuseDelay() > 0) {
			L1SkillDelay.onSkillUse(_user, _skill.getReuseDelay());
		}
	}

	/**
	 * 发动技能效果
	 */
	private void runSkill() {

		switch (_skillId) {
		case LIFE_STREAM: // 治愈能量风暴
			L1EffectSpawn.getInstance().spawnEffect(81169, _skill.getBuffDuration() * 1000, _targetX, _targetY, _user.getMapId());
			return;
		case CUBE_IGNITION: // 立方:燃烧
			L1EffectSpawn.getInstance().spawnEffect(80149, _skill.getBuffDuration() * 1000, _targetX, _targetY, _user.getMapId(), (L1PcInstance) _user, _skillId);
			return;
		case CUBE_QUAKE: // 立方:地裂
			L1EffectSpawn.getInstance().spawnEffect(80150, _skill.getBuffDuration() * 1000, _targetX, _targetY, _user.getMapId(), (L1PcInstance) _user, _skillId);
			return;
		case CUBE_SHOCK: // 立方:冲击
			L1EffectSpawn.getInstance().spawnEffect(80151, _skill.getBuffDuration() * 1000, _targetX, _targetY, _user.getMapId(), (L1PcInstance) _user, _skillId);
			return;
		case CUBE_BALANCE: // 立方:和谐
			L1EffectSpawn.getInstance().spawnEffect(80152, _skill.getBuffDuration() * 1000, _targetX, _targetY, _user.getMapId(), (L1PcInstance) _user, _skillId);
			return;
		case FIRE_WALL: // 火牢
			L1EffectSpawn.getInstance().doSpawnFireWall(_user, _targetX, _targetY);
			return;
		case BLKS_FIRE_WALL: // 巴拉卡斯火牢
			_user.setSkillEffect(_skillId, 11 * 1000);
			L1EffectSpawn.getInstance().doSpawnFireWallforNpc(_user, _target);
			return;
		case TRUE_TARGET: // 精准目标
			if (_user instanceof L1PcInstance) {
				L1PcInstance pri = (L1PcInstance) _user;
				L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(80153, 5 * 1000, _targetX + 2, _targetY - 1, _user.getMapId());
				if (_targetID != 0) {
					pri.sendPackets(new S_TrueTarget(_targetID, pri.getId(), _message));
					if (pri.getClanid() != 0) {
						L1PcInstance players[] = L1World.getInstance().getClan(pri.getClanname()).getOnlineClanMember();
						for (L1PcInstance pc : players) {
							pc.sendPackets(new S_TrueTarget(_targetID, pc.getId(), _message));
						}
					}
				}
				else if (effect != null) {
					pri.sendPackets(new S_TrueTarget(effect.getId(), pri.getId(), _message));
					if (pri.getClanid() != 0) {
						L1PcInstance players[] = L1World.getInstance().getClan(pri.getClanname()).getOnlineClanMember();
						for (L1PcInstance pc : players) {
							pc.sendPackets(new S_TrueTarget(effect.getId(), pc.getId(), _message));
						}
					}
				}
			}
			return;
		default:
			break;
		}

		// 魔法屏障不可抵挡的魔法
		for (int skillId : EXCEPT_COUNTER_MAGIC) {
			if (_skillId == skillId) {
				_isCounterMagic = false; // 无效
				break;
			}
		}

		// NPCにショックスタンを使用させるとonActionでNullPointerExceptionが発生するため
		// とりあえずPCが使用した时のみ
		if ((_skillId == SHOCK_STUN) && (_user instanceof L1PcInstance)) {
			_target.onAction(_player);
		}

		if (!isTargetCalc(_target)) {
			return;
		}

		try {
			TargetStatus ts = null;
			L1Character cha = null;
			int dmg = 0;
			int drainMana = 0;
			int heal = 0;
			boolean isSuccess = false;
			int undeadType = 0;

			for (Iterator<TargetStatus> iter = _targetList.iterator(); iter.hasNext();) {
				ts = null;
				cha = null;
				dmg = 0;
				heal = 0;
				isSuccess = false;
				undeadType = 0;

				ts = iter.next();
				cha = ts.getTarget();

				if (!ts.isCalc() || !isTargetCalc(cha)) {
					continue; // 计算する必要がない。
				}

				L1Magic _magic = new L1Magic(_user, cha);
				_magic.setLeverage(getLeverage());

				if (cha instanceof L1MonsterInstance) { // 不死系判断
					undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
				}

				// 确率系技能失败确定
				if (((_skill.getType() == L1Skills.TYPE_CURSE) || (_skill.getType() == L1Skills.TYPE_PROBABILITY)) && isTargetFailure(cha)) {
					iter.remove();
					continue;
				}

				if (cha instanceof L1PcInstance) { // 只有在目标为PC的情况下发送图标，代表使用成功。
					if (_skillTime == 0) {
						_getBuffIconDuration = _skill.getBuffDuration(); // 效果时间
					}
					else {
						_getBuffIconDuration = _skillTime; // パラメータのtimeが0以外なら、效果时间として设定する
					}
				}

				deleteRepeatedSkills(cha); // 删除无法共同存在的魔法状态

				// 攻击系技能和使用者除外。
				if ((_skill.getType() == L1Skills.TYPE_ATTACK) && (_user.getId() != cha.getId())) {
					if (isUseCounterMagic(cha)) { // カウンターマジックが発动した场合、リストから削除
						iter.remove();
						continue;
					}
					dmg = _magic.calcMagicDamage(_skillId);
					_dmg = dmg;
					cha.removeSkillEffect(ERASE_MAGIC); // イレースマジック中なら、攻击魔法で解除
				}
				else if ((_skill.getType() == L1Skills.TYPE_CURSE) || (_skill.getType() == L1Skills.TYPE_PROBABILITY)) { // 确率系スキル
					isSuccess = _magic.calcProbabilityMagic(_skillId);
					if (_skillId != ERASE_MAGIC) {
						cha.removeSkillEffect(ERASE_MAGIC); // イレースマジック中なら、确率魔法で解除
					}
					if (_skillId != FOG_OF_SLEEPING) {
						cha.removeSkillEffect(FOG_OF_SLEEPING); // フォグオブスリーピング中なら、确率魔法で解除
					}
					if (isSuccess) { // 成功したがカウンターマジックが発动した场合、リストから削除
						if (isUseCounterMagic(cha)) { // カウンターマジックが発动したか
							iter.remove();
							continue;
						}
					}
					else { // 失败した场合、リストから削除
						if ((_skillId == FOG_OF_SLEEPING) && (cha instanceof L1PcInstance)) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_ServerMessage(297)); // 你感觉些微地晕眩。
						}
						iter.remove();
						continue;
					}
				}

				// 治愈性魔法
				else if (_skill.getType() == L1Skills.TYPE_HEAL) {
					// 回复量
					dmg = -1 * _magic.calcHealing(_skillId);
					if (cha.hasSkillEffect(WATER_LIFE)) { // 水之元气-效果 2倍
						dmg *= 2;
						cha.killSkillEffectTimer(WATER_LIFE); // 效果只有一次
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_SkillIconWaterLife());
						}
					}
					if (cha.hasSkillEffect(POLLUTE_WATER)) { // 污浊之水-效果减半
						dmg /= 2;
					}
				}
				// 显示团体魔法效果在队友或盟友
				else if ((_skillId == FIRE_BLESS || _skillId == STORM_EYE // 烈炎气息、暴风之眼
						|| _skillId == EARTH_BLESS // 大地的祝福
						|| _skillId == GLOWING_AURA // 激励士气
						|| _skillId == SHINING_AURA || _skillId == BRAVE_AURA) // 钢铁士气、冲击士气
						&& _user.getId() != cha.getId()) {
					if (cha instanceof L1PcInstance) {
						L1PcInstance _targetPc = (L1PcInstance) cha;
						_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), _skill.getCastGfx()));
						_targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), _skill.getCastGfx()));
					}
				}

				// ■■■■ 个别处理のあるスキルのみ书いてください。 ■■■■

				// 除了冲晕、骷髅毁坏之外魔法效果存在时，只更新效果时间跟图示。
				if (cha.hasSkillEffect(_skillId) && (_skillId != SHOCK_STUN && _skillId != BONE_BREAK && _skillId != CONFUSION && _skillId != THUNDER_GRAB)) {
					addMagicList(cha, true); // 魔法效果已存在时
					if (_skillId != SHAPE_CHANGE) { // 除了变形术之外
						continue;
					}
				}

				switch (_skillId) {
				// 加速术
				case HASTE:
					if (cha.getMoveSpeed() != 2) { // 缓速中以外
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							if (pc.getHasteItemEquipped() > 0) {
								continue;
							}
							pc.setDrink(false);
							pc.sendPackets(new S_SkillHaste(pc.getId(), 1, _getBuffIconDuration));
						}
						cha.broadcastPacket(new S_SkillHaste(cha.getId(), 1, 0));
						cha.setMoveSpeed(1);
					}
					else { // 缓速中
						int skillNum = 0;
						if (cha.hasSkillEffect(SLOW)) {
							skillNum = SLOW;
						}
						else if (cha.hasSkillEffect(MASS_SLOW)) {
							skillNum = MASS_SLOW;
						}
						else if (cha.hasSkillEffect(ENTANGLE)) {
							skillNum = ENTANGLE;
						}
						if (skillNum != 0) {
							cha.removeSkillEffect(skillNum);
							cha.removeSkillEffect(HASTE);
							cha.setMoveSpeed(0);
							continue;
						}
					}
					break;
				// 强力加速术
				case GREATER_HASTE:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getHasteItemEquipped() > 0) {
							continue;
						}
						if (pc.getMoveSpeed() != 2) { // 缓速中以外
							pc.setDrink(false);
							pc.setMoveSpeed(1);
							pc.sendPackets(new S_SkillHaste(pc.getId(), 1, _getBuffIconDuration));
							pc.broadcastPacket(new S_SkillHaste(pc.getId(), 1, 0));
						}
						else { // 缓速中
							int skillNum = 0;
							if (pc.hasSkillEffect(SLOW)) {
								skillNum = SLOW;
							}
							else if (pc.hasSkillEffect(MASS_SLOW)) {
								skillNum = MASS_SLOW;
							}
							else if (pc.hasSkillEffect(ENTANGLE)) {
								skillNum = ENTANGLE;
							}
							if (skillNum != 0) {
								pc.removeSkillEffect(skillNum);
								pc.removeSkillEffect(GREATER_HASTE);
								pc.setMoveSpeed(0);
								continue;
							}
						}
					}
					break;
				// 缓速术、集体缓速术、地面障碍
				case SLOW:
				case MASS_SLOW:
				case ENTANGLE:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getHasteItemEquipped() > 0) {
							continue;
						}
					}
					if (cha.getMoveSpeed() == 0) {
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_SkillHaste(pc.getId(), 2, _getBuffIconDuration));
						}
						cha.broadcastPacket(new S_SkillHaste(cha.getId(), 2, _getBuffIconDuration));
						cha.setMoveSpeed(2);
					}
					else if (cha.getMoveSpeed() == 1) {
						int skillNum = 0;
						if (cha.hasSkillEffect(HASTE)) {
							skillNum = HASTE;
						}
						else if (cha.hasSkillEffect(GREATER_HASTE)) {
							skillNum = GREATER_HASTE;
						}
						else if (cha.hasSkillEffect(STATUS_HASTE)) {
							skillNum = STATUS_HASTE;
						}
						if (skillNum != 0) {
							cha.removeSkillEffect(skillNum);
							cha.removeSkillEffect(_skillId);
							cha.setMoveSpeed(0);
							continue;
						}
					}
					break;
				// 寒冷战栗、吸血鬼之吻
				case CHILL_TOUCH:
				case VAMPIRIC_TOUCH:
					heal = dmg;
					break;
				// 亚力安冰矛围篱
				case ICE_LANCE_COCKATRICE:
					// 邪恶蜥蜴冰矛围篱
				case ICE_LANCE_BASILISK:
					// 冰毛围篱、冰雪飓风、寒冰喷吐
				case ICE_LANCE:
				case FREEZING_BLIZZARD:
				case FREEZING_BREATH:
					_isFreeze = _magic.calcProbabilityMagic(_skillId);
					if (_isFreeze) {
						int time = _skill.getBuffDuration() * 1000;
						L1EffectSpawn.getInstance().spawnEffect(81168, time, cha.getX(), cha.getY(), cha.getMapId());
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_Poison(pc.getId(), 2));
							pc.broadcastPacket(new S_Poison(pc.getId(), 2));
							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true));
						}
						else if ((cha instanceof L1MonsterInstance) || (cha instanceof L1SummonInstance) || (cha instanceof L1PetInstance)) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							npc.broadcastPacket(new S_Poison(npc.getId(), 2));
							npc.setParalyzed(true);
							npc.setParalysisTime(time);
						}
					}
					break;
				// 大地屏障
				case EARTH_BIND:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Poison(pc.getId(), 2));
						pc.broadcastPacket(new S_Poison(pc.getId(), 2));
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true));
					}
					else if ((cha instanceof L1MonsterInstance) || (cha instanceof L1SummonInstance) || (cha instanceof L1PetInstance)) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						npc.broadcastPacket(new S_Poison(npc.getId(), 2));
						npc.setParalyzed(true);
						npc.setParalysisTime(_skill.getBuffDuration() * 1000);
					}
					break;
				case 20011: // 毒雾-前方 3X3
					_user.setHeading(_user.targetDirection(_targetX, _targetY)); // 改变面向
					int locX = 0;
					int locY = 0;
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							switch (_user.getHeading()) {
							case 0:
								locX = (-1 + j);
								locY = -1 * (-3 + i);
								break;
							case 1:
								locX = -1 * (2 + j - i);
								locY = -1 * (-4 + j + i);
								break;
							case 2:
								locX = -1 * (3 - i);
								locY = (-1 + j);
								break;
							case 3:
								locX = -1 * (4 - j - i);
								locY = -1 * (2 + j - i);
								break;
							case 4:
								locX = (1 - j);
								locY = -1 * (3 - i);
								break;
							case 5:
								locX = -1 * (-2 - j + i);
								locY = -1 * (4 - j - i);
								break;
							case 6:
								locX = -1 * (-3 + i);
								locY = (1 - j);
								break;
							case 7:
								locX = -1 * (-4 + j + i);
								locY = -1 * (-2 - j + i);
								break;
							}
							L1EffectSpawn.getInstance().spawnEffect(93002, 10000, _user.getX() - locX, _user.getY() - locY, _user.getMapId());
						}
					}
					break;
				// 冲击之晕
				case SHOCK_STUN:
					int[] stunTimeArray = { 500, 1000, 1500, 2000, 2500, 3000 };
					int rnd = Random.nextInt(stunTimeArray.length);
					_shockStunDuration = stunTimeArray[rnd];
					if ((cha instanceof L1PcInstance) && cha.hasSkillEffect(SHOCK_STUN)) {
						_shockStunDuration += cha.getSkillEffectTimeSec(SHOCK_STUN) * 1000;
					}

					L1EffectSpawn.getInstance().spawnEffect(81162, _shockStunDuration, cha.getX(), cha.getY(), cha.getMapId());
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, true));
					}
					else if ((cha instanceof L1MonsterInstance) || (cha instanceof L1SummonInstance) || (cha instanceof L1PetInstance)) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						npc.setParalyzed(true);
						npc.setParalysisTime(_shockStunDuration);
					}
					break;
				// 夺命之雷
				case THUNDER_GRAB:
					isSuccess = _magic.calcProbabilityMagic(_skillId);
					if (isSuccess) {
						if (!cha.hasSkillEffect(THUNDER_GRAB_START) && !cha.hasSkillEffect(STATUS_FREEZE)) {
							if (cha instanceof L1PcInstance) {
								L1PcInstance pc = (L1PcInstance) cha;
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true));
								pc.sendPackets(new S_SkillSound(pc.getId(), 4184));
								pc.broadcastPacket(new S_SkillSound(pc.getId(), 4184));
							}
							else if (cha instanceof L1NpcInstance) {
								L1NpcInstance npc = (L1NpcInstance) cha;
								npc.setParalyzed(true);
								npc.broadcastPacket(new S_SkillSound(npc.getId(), 4184));
							}
							cha.setSkillEffect(THUNDER_GRAB_START, 500);
						}
					}
					break;
				// 起死回生术
				case TURN_UNDEAD:
					if (undeadType == 1 || undeadType == 3) {
						dmg = cha.getCurrentHp();
					}
					break;
				// 魔力夺取
				case MANA_DRAIN:
					int chance = Random.nextInt(10) + 5;
					drainMana = chance + (_user.getInt() / 2);
					if (cha.getCurrentMp() < drainMana) {
						drainMana = cha.getCurrentMp();
					}
					break;
				// 指定传送、集体传送术
				case TELEPORT:
				case MASS_TELEPORT:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1BookMark bookm = pc.getBookMark(_bookmarkId);
						if (bookm != null) { // 从书签中取出一个传送坐标
							if (pc.getMap().isEscapable() || pc.isGm()) {
								int newX = bookm.getLocX();
								int newY = bookm.getLocY();
								short mapId = bookm.getMapId();

								if (_skillId == MASS_TELEPORT) { // 集体传送术
									List<L1PcInstance> clanMember = L1World.getInstance().getVisiblePlayer(pc);
									for (L1PcInstance member : clanMember) {
										if ((pc.getLocation().getTileLineDistance(member.getLocation()) <= 3) && (member.getClanid() == pc.getClanid()) && (pc.getClanid() != 0) && (member.getId() != pc.getId())) {
											L1Teleport.teleport(member, newX, newY, mapId, 5, true);
										}
									}
								}
								L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
							}
							else {
								pc.sendPackets(new S_ServerMessage(79));
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, true));
							}
						}
						else { // ブックマークが取得出来なかった、あるいは“任意の场所”を选択した场合の处理
							if (pc.getMap().isTeleportable() || pc.isGm()) {
								L1Location newLocation = pc.getLocation().randomLocation(200, true);
								int newX = newLocation.getX();
								int newY = newLocation.getY();
								short mapId = (short) newLocation.getMapId();

								if (_skillId == MASS_TELEPORT) {
									List<L1PcInstance> clanMember = L1World.getInstance().getVisiblePlayer(pc);
									for (L1PcInstance member : clanMember) {
										if ((pc.getLocation().getTileLineDistance(member.getLocation()) <= 3) && (member.getClanid() == pc.getClanid()) && (pc.getClanid() != 0) && (member.getId() != pc.getId())) {
											L1Teleport.teleport(member, newX, newY, mapId, 5, true);
										}
									}
								}
								L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
							}
							else {
								pc.sendPackets(new S_ServerMessage(276)); // \f1在此无法使用传送。
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, true));
							}
						}
					}
					break;
				// 呼唤盟友
				case CALL_CLAN:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1PcInstance clanPc = (L1PcInstance) L1World.getInstance().findObject(_targetID);
						if (clanPc != null) {
							clanPc.setTempID(pc.getId());
							clanPc.sendPackets(new S_Message_YN(729, "")); // 盟主正在呼唤你，你要接受他的呼唤吗？(Y/N)
						}
					}
					break;
				// 援护盟友
				case RUN_CLAN:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1PcInstance clanPc = (L1PcInstance) L1World.getInstance().findObject(_targetID);
						if (clanPc != null) {
							if (pc.getMap().isEscapable() || pc.isGm()) {
								boolean castle_area = L1CastleLocation.checkInAllWarArea(clanPc.getX(), clanPc.getY(), clanPc.getMapId());
								if (((clanPc.getMapId() == 0) || (clanPc.getMapId() == 4) || (clanPc.getMapId() == 304)) && (castle_area == false)) {
									L1Teleport.teleport(pc, clanPc.getX(), clanPc.getY(), clanPc.getMapId(), 5, true);
								}
								else {
									pc.sendPackets(new S_ServerMessage(79));
								}
							}
							else {
								// 这附近的能量影响到瞬间移动。在此地无法使用瞬间移动。
								pc.sendPackets(new S_ServerMessage(647));
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, true));
							}
						}
					}
					break;
				// 强力无所遁形
				case COUNTER_DETECTION:
					if (cha instanceof L1PcInstance) {
						dmg = _magic.calcMagicDamage(_skillId);
					}
					else if (cha instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						int hiddenStatus = npc.getHiddenStatus();
						if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
							npc.appearOnGround(_player);
						}
						else {
							dmg = 0;
						}
					}
					else {
						dmg = 0;
					}
					break;
				// 创造魔法武器
				case CREATE_MAGICAL_WEAPON:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if ((item != null) && (item.getItem().getType2() == 1)) {
							int item_type = item.getItem().getType2();
							int safe_enchant = item.getItem().get_safeenchant();
							int enchant_level = item.getEnchantLevel();
							String item_name = item.getName();
							if (safe_enchant < 0) { // 强化不可
								pc.sendPackets( // \f1没有任何事情发生。
								new S_ServerMessage(79));
							}
							else if (safe_enchant == 0) { // 安全圏+0
								pc.sendPackets( // \f1没有任何事情发生。
								new S_ServerMessage(79));
							}
							else if ((item_type == 1) && (enchant_level == 0)) {
								if (!item.isIdentified()) {// 未鉴定
									pc.sendPackets( // \f1%0%s %2 %1 光芒。
									new S_ServerMessage(161, item_name, "$245", "$247"));
								}
								else {
									item_name = "+0 " + item_name;
									pc.sendPackets( // \f1%0%s %2 %1 光芒。
									new S_ServerMessage(161, "+0 " + item_name, "$245", "$247"));
								}
								item.setEnchantLevel(1);
								pc.getInventory().updateItem(item, L1PcInventory.COL_ENCHANTLVL);
							}
							else {
								pc.sendPackets( // \f1没有任何事情发生。
								new S_ServerMessage(79));
							}
						}
						else {
							pc.sendPackets( // \f1没有任何事情发生。
							new S_ServerMessage(79));
						}
					}
					break;
				// 提炼魔石
				case BRING_STONE:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;

						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if (item != null) {
							int dark = (int) (10 + (pc.getLevel() * 0.8) + (pc.getWis() - 6) * 1.2);
							int brave = (int) (dark / 2.1);
							int wise = (int) (brave / 2.0);
							int kayser = (int) (wise / 1.9);
							int run = Random.nextInt(100) + 1;
							if (item.getItem().getItemId() == 40320) {
								pc.getInventory().removeItem(item, 1);
								if (dark >= run) {
									pc.getInventory().storeItem(40321, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2475")); // 获得%0%o 。
								}
								else {
									pc.sendPackets(new S_ServerMessage(280)); // \f1施咒失败。
								}
							}
							else if (item.getItem().getItemId() == 40321) {
								pc.getInventory().removeItem(item, 1);
								if (brave >= run) {
									pc.getInventory().storeItem(40322, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2476")); // 获得%0%o 。
								}
								else {
									pc.sendPackets(new S_ServerMessage(280));// \f1施咒失败。
								}
							}
							else if (item.getItem().getItemId() == 40322) {
								pc.getInventory().removeItem(item, 1);
								if (wise >= run) {
									pc.getInventory().storeItem(40323, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2477")); // 获得%0%o 。
								}
								else {
									pc.sendPackets(new S_ServerMessage(280));// \f1施咒失败。
								}
							}
							else if (item.getItem().getItemId() == 40323) {
								pc.getInventory().removeItem(item, 1);
								if (kayser >= run) {
									pc.getInventory().storeItem(40324, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2478")); // 获得%0%o 。
								}
								else {
									pc.sendPackets(new S_ServerMessage(280));// \f1施咒失败。
								}
							}
						}
					}
					break;
				// 日光术
				case LIGHT:
					if (cha instanceof L1PcInstance) {
					}
					break;
				// 暗影之牙
				case SHADOW_FANG:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if ((item != null) && (item.getItem().getType2() == 1)) {
							item.setSkillWeaponEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
						}
						else {
							pc.sendPackets(new S_ServerMessage(79));
						}
					}
					break;
				// 拟似魔法武器
				case ENCHANT_WEAPON:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if ((item != null) && (item.getItem().getType2() == 1)) {
							pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
							item.setSkillWeaponEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
						}
						else {
							pc.sendPackets(new S_ServerMessage(79));
						}
					}
					break;
				// 神圣武器、祝福魔法武器
				case HOLY_WEAPON:
				case BLESS_WEAPON:
					if (cha instanceof L1PcInstance) {
						if (!(cha instanceof L1PcInstance)) {
							return;
						}
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getWeapon() == null) {
							pc.sendPackets(new S_ServerMessage(79));
							return;
						}
						for (L1ItemInstance item : pc.getInventory().getItems()) {
							if (pc.getWeapon().equals(item)) {
								pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
								item.setSkillWeaponEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
								return;
							}
						}
					}
					break;
				// 铠甲护持
				case BLESSED_ARMOR:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if ((item != null) && (item.getItem().getType2() == 2) && (item.getItem().getType() == 2)) {
							pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
							item.setSkillArmorEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
						}
						else {
							pc.sendPackets(new S_ServerMessage(79));
						}
					}
					break;
				default:
					L1BuffUtil.skillEffect(_user, cha, _target, _skillId, _getBuffIconDuration, dmg);
					break;
				}

				// ■■■■ 到目前为止的个别处理 ■■■■

				// 治愈性魔法攻击不死系的怪物。
				if ((_skill.getType() == L1Skills.TYPE_HEAL) && (_calcType == PC_NPC) && (undeadType == 1)) {
					dmg *= -1;
				}
				// 治愈性魔法无法对此不死系起作用
				if ((_skill.getType() == L1Skills.TYPE_HEAL) && (_calcType == PC_NPC) && (undeadType == 3)) {
					dmg = 0;
				}
				// 无法对城门、守护塔补血
				if (((cha instanceof L1TowerInstance) || (cha instanceof L1DoorInstance)) && (dmg < 0)) {
					dmg = 0;
				}
				// 吸取魔力。
				if ((dmg > 0) || (drainMana != 0)) {
					_magic.commit(dmg, drainMana);
				}
				// 补血判断
				if ((_skill.getType() == L1Skills.TYPE_HEAL) && (dmg < 0)) {
					cha.setCurrentHp((dmg * -1) + cha.getCurrentHp());
				}
				// 非治愈性魔法补血判断(寒战、吸吻等)
				if (heal > 0) {
					_user.setCurrentHp(heal + _user.getCurrentHp());
				}

				if (cha instanceof L1PcInstance) { // 更新自身状态
					L1PcInstance pc = (L1PcInstance) cha;
					pc.turnOnOffLight();
					pc.sendPackets(new S_OwnCharAttrDef(pc));
					pc.sendPackets(new S_OwnCharStatus(pc));
					sendHappenMessage(pc); // 发送消息到目标
				}

				addMagicList(cha, false); // 为目标设定魔法效果时间

				if (cha instanceof L1PcInstance) { // 如果目标为PC、更新灯光状态
					L1PcInstance pc = (L1PcInstance) cha;
					pc.turnOnOffLight(); // 打开灯
				}
			}

			// 解除隐身
			if ((_skillId == DETECTION) || (_skillId == COUNTER_DETECTION)) { // 无所遁形、强力无所遁形
				detection(_player);
			}

		}
		catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 检测隐身
	 * 
	 * @param pc
	 */
	private void detection(L1PcInstance pc) {
		if (!pc.isGmInvis() && pc.isInvisble()) { // 自己隐身中
			pc.delInvis();
			pc.beginInvisTimer();
		}

		for (L1PcInstance tgt : L1World.getInstance().getVisiblePlayer(pc)) { // 画面内其他隐身者
			if (!tgt.isGmInvis() && tgt.isInvisble()) {
				tgt.delInvis();
			}
		}
		L1WorldTraps.getInstance().onDetection(pc); // 侦测陷阱处理
	}

	/**
	 * 目标判定
	 * 
	 * @param cha
	 * @param cha
	 * @return
	 */
	private boolean isTargetCalc(L1Character cha) {
		// 三重矢、屠宰者、暴击、骷髅毁坏
		if ((_user instanceof L1PcInstance) && (_skillId == TRIPLE_ARROW || _skillId == FOE_SLAYER || _skillId == SMASH || _skillId == BONE_BREAK)) {
			return true;
		}
		// 攻击魔法のNon－PvP判定
		if (_skill.getTarget().equals("attack") && (_skillId != 18)) { // 攻击魔法
			if (isPcSummonPet(cha)) { // 对象为PC、召唤、宠物
				if ((_player.getZoneType() == 1) || (cha.getZoneType() == 1 // 攻击方或被攻击方在安全区
						) || _player.checkNonPvP(_player, cha)) { // Non-PvP设定
					return false;
				}
			}
		}

		// 沉睡之雾的对象是自己
		if ((_skillId == FOG_OF_SLEEPING) && (_user.getId() == cha.getId())) {
			return false;
		}

		// 集体缓速对自己和自己的宠物
		if (_skillId == MASS_SLOW) {
			if (_user.getId() == cha.getId()) {
				return false;
			}
			if (cha instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (_user.getId() == summon.getMaster().getId()) {
					return false;
				}
			}
			else if (cha instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) cha;
				if (_user.getId() == pet.getMaster().getId()) {
					return false;
				}
			}
		}

		// 集体传送的对象只是自己（同血盟成员可以传送）
		if (_skillId == MASS_TELEPORT) {
			if (_user.getId() != cha.getId()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 目标对象 是否为PC、召唤物、宠物
	 * 
	 * @param cha
	 * @return
	 */
	private boolean isPcSummonPet(L1Character cha) {
		if (_calcType == PC_PC) { // 对象为PC
			return true;
		}

		if (_calcType == PC_NPC) {
			if (cha instanceof L1SummonInstance) { // 对象召唤物
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (summon.isExsistMaster()) { // 有主人
					return true;
				}
			}
			if (cha instanceof L1PetInstance) { // 对象宠物
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回目标失败
	 * 
	 * @param cha
	 */
	private boolean isTargetFailure(L1Character cha) {
		boolean isTU = false;
		boolean isErase = false;
		boolean isManaDrain = false;
		int undeadType = 0;

		if ((cha instanceof L1TowerInstance) || (cha instanceof L1DoorInstance)) { // 守护塔、门
																					// 确率系技能无效
			return true;
		}

		if (cha instanceof L1PcInstance) { // 对PCの场合
			if ((_calcType == PC_PC) && _player.checkNonPvP(_player, cha)) { // Non-PvP设定
				L1PcInstance pc = (L1PcInstance) cha;
				if ((_player.getId() == pc.getId()) || ((pc.getClanid() != 0) && (_player.getClanid() == pc.getClanid()))) {
					return false;
				}
				return true;
			}
			return false;
		}

		if (cha instanceof L1MonsterInstance) { // ターンアンデット可能か判定
			isTU = ((L1MonsterInstance) cha).getNpcTemplate().get_IsTU();
		}

		if (cha instanceof L1MonsterInstance) { // イレースマジック可能か判定
			isErase = ((L1MonsterInstance) cha).getNpcTemplate().get_IsErase();
		}

		if (cha instanceof L1MonsterInstance) { // アンデットの判定
			undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
		}

		// マナドレインが可能か？
		if (cha instanceof L1MonsterInstance) {
			isManaDrain = true;
		}
		/*
		 * 成功除外条件１：T-Uが成功したが、对象がアンデットではない。 成功除外条件２：T-Uが成功したが、对象にはターンアンデット无效。 成功除外条件３：スロー、マススロー、マナドレイン、エンタングル、イレースマジック、ウィンドシャックル无效 成功除外条件４：マナドレインが成功したが、モンスター以外の场合
		 */
		if (((_skillId == TURN_UNDEAD) && ((undeadType == 0) || (undeadType == 2))) || ((_skillId == TURN_UNDEAD) && (isTU == false))
				|| (((_skillId == ERASE_MAGIC) || (_skillId == SLOW) || (_skillId == MANA_DRAIN) || (_skillId == MASS_SLOW) || (_skillId == ENTANGLE) || (_skillId == WIND_SHACKLE)) && (isErase == false)) || ((_skillId == MANA_DRAIN) && (isManaDrain == false))) {
			return true;
		}
		return false;
	}

	/**
	 * 魔法屏障发动判断
	 * 
	 * @param cha
	 */
	private boolean isUseCounterMagic(L1Character cha) {
		if (_isCounterMagic && cha.hasSkillEffect(COUNTER_MAGIC)) {
			cha.removeSkillEffect(COUNTER_MAGIC);
			int castgfx = SkillsTable.getInstance().getTemplate(COUNTER_MAGIC).getCastGfx();
			cha.broadcastPacket(new S_SkillSound(cha.getId(), castgfx));
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
			}
			return true;
		}
		return false;
	}

}
