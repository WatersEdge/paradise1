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
package l1j.server.server.templates;

/**
 * Mob技能
 */
public class L1MobSkill implements Cloneable {

	/** 空类型 */
	public static final int TYPE_NONE = 0;
	/** 物理攻击类型 */
	public static final int TYPE_PHYSICAL_ATTACK = 1;
	/** 魔法攻击类型 */
	public static final int TYPE_MAGIC_ATTACK = 2;
	/** 召唤类型 */
	public static final int TYPE_SUMMON = 3;
	/** 变身类型 */
	public static final int TYPE_POLY = 4;
	/** 目标无变化 */
	public static final int CHANGE_TARGET_NO = 0;
	/** 改变目标 (同伴) */
	public static final int CHANGE_TARGET_COMPANION = 1;
	/** 改变目标(自己) */
	public static final int CHANGE_TARGET_ME = 2;
	/** 随机改变目标 */
	public static final int CHANGE_TARGET_RANDOM = 3;
	/** 技能大小 */
	private final int skillSize;

	@Override
	public L1MobSkill clone() {
		try {
			return (L1MobSkill) (super.clone());
		} catch (CloneNotSupportedException e) {
			throw (new InternalError(e.getMessage()));
		}
	}

	/** 获得技能大小 */
	public int getSkillSize() {
		return skillSize;
	}

	public L1MobSkill(int sSize) {
		skillSize = sSize;

		type = new int[skillSize];
		mpConsume = new int[skillSize];
		triRnd = new int[skillSize];
		triHp = new int[skillSize];
		triCompanionHp = new int[skillSize];
		triRange = new int[skillSize];
		triCount = new int[skillSize];
		changeTarget = new int[skillSize];
		range = new int[skillSize];
		areaWidth = new int[skillSize];
		areaHeight = new int[skillSize];
		leverage = new int[skillSize];
		skillId = new int[skillSize];
		skillArea = new int[skillSize];
		gfxid = new int[skillSize];
		actid = new int[skillSize];
		summon = new int[skillSize];
		summonMin = new int[skillSize];
		summonMax = new int[skillSize];
		polyId = new int[skillSize];
	}

	/** MobID */
	private int mobid;
	/** 获得MobID */
	public int get_mobid() {
		return mobid;
	}
	/** 设置MobID */
	public void set_mobid(int i) {
		mobid = i;
	}

	/** Mob名称 */
	private String mobName;
	/** 获得Mob名称 */
	public String getMobName() {
		return mobName;
	}
	/** 设置Mob名称 */
	public void setMobName(String s) {
		mobName = s;
	}

	/**
	 * 技能类型 0→不采取行动、1→物理攻击、2→魔法攻击、3→サモン
	 */
	private int type[];
	/** 获得技能类型 */
	public int getType(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return type[idx];
	}
	/** 设置技能类型 */
	public void setType(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		type[idx] = i;
	}

	/**
	 * 技能范围设定
	 */
	int skillArea[];
	/** 获得技能范围设定 */
	public int getSkillArea(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return skillArea[idx];
	}
	/** 设置技能范围设定 */
	public void setSkillArea(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		skillArea[idx] = i;
	}

	/**
	 * 魔力消耗判断
	 */
	int mpConsume[];
	/** 获得魔力消耗判断 */
	public int getMpConsume(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return mpConsume[idx];
	}
	/** 设置魔力消耗判断 */
	public void setMpConsume(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		mpConsume[idx] = i;
	}

	/**
	 * 技能发动条件：随机发动概率（0%～100%）
	 */
	private int triRnd[];
	/** 获得随机发动概率（0%～100%） */
	public int getTriggerRandom(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return triRnd[idx];
	}
	/** 设置随机发动概率（0%～100%） */
	public void setTriggerRandom(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		triRnd[idx] = i;
	}

	/**
	 * 技能发动条件：HP%以下发动
	 */
	int triHp[];
	/** 获得HP%以下发动 */
	public int getTriggerHp(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return triHp[idx];
	}
	/** 设置HP%以下发动 */
	public void setTriggerHp(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		triHp[idx] = i;
	}

	/**
	 * 技能发动条件：同族のHP%以下发动
	 */
	int triCompanionHp[];
	/** 获得同族のHP%以下发动 */
	public int getTriggerCompanionHp(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return triCompanionHp[idx];
	}
	/** 设置同族のHP%以下发动 */
	public void setTriggerCompanionHp(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		triCompanionHp[idx] = i;
	}

	/**
	 * 技能发动条件：triRange<0の場合、対象との距離がabs(triRange)以下のとき発動
	 * triRange>0の場合、対象との距離がtriRange以上のとき発動
	 */
	int triRange[];
	/** 获得技能触发范围 */
	public int getTriggerRange(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return triRange[idx];
	}
	/** 设置技能触发范围 */
	public void setTriggerRange(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		triRange[idx] = i;
	}

	/** distance指定idx技能的发动条件 */
	public boolean isTriggerDistance(int idx, int distance) {
		int triggerRange = getTriggerRange(idx);

		if ((triggerRange < 0 && distance <= Math.abs(triggerRange))
				|| (triggerRange > 0 && distance >= triggerRange)) {
			return true;
		}
		return false;
	}

	/** 触发次数 */
	int triCount[];

	/**
	 * 技能发动条件：技能的发动次数triCount以下发动
	 */
	public int getTriggerCount(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return triCount[idx];
	}
	/** 设置技能触发次数 */
	public void setTriggerCount(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		triCount[idx] = i;
	}

	/**
	 * 技能发动时、改变目标
	 */
	int changeTarget[];
	/** 获得技能发动时、改变目标 */
	public int getChangeTarget(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return changeTarget[idx];
	}
	/** 设置技能发动时、改变目标 */
	public void setChangeTarget(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		changeTarget[idx] = i;
	}

	/**
	 * rangeまでの距離ならば攻撃可能、物理攻撃をするならば近接攻撃の場合でも1以上を設定
	 */
	int range[];
	/** 获得范围攻击的距离 */
	public int getRange(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return range[idx];
	}
	/** 设置获得范围攻击的距离  */
	public void setRange(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		range[idx] = i;
	}

	/**
	 * 范围攻击的宽度、单体攻击设定0、范围攻击设定1以上
	 * WidthとHeightの設定は攻撃者からみて横幅をWidth、奥行きをHeightとする。
	 * Widthは+-あるので、1を指定すれば、ターゲットを中心として左右1までが対象となる。
	 */
	int areaWidth[];
	/** 获得范围攻击的宽度 */
	public int getAreaWidth(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return areaWidth[idx];
	}
	/** 设置范围攻击的宽度 */
	public void setAreaWidth(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		areaWidth[idx] = i;
	}

	/**
	 * 范围攻击的高度、单体攻击设定0、范围攻击设定1以上
	 */
	int areaHeight[];
	/** 获得范围攻击的高度 */
	public int getAreaHeight(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return areaHeight[idx];
	}
	/** 设置范围攻击的高度 */
	public void setAreaHeight(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		areaHeight[idx] = i;
	}

	/**
	 * 伤害倍率、1/10で表す。物理攻击、魔法攻击共同有效
	 */
	int leverage[];
	/** 获得伤害倍率 */
	public int getLeverage(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return leverage[idx];
	}
	/** 设置伤害倍率 */
	public void setLeverage(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		leverage[idx] = i;
	}

	/**
	 * 魔法使用场合、SkillId指定
	 */
	int skillId[];
	/** 获得技能ID */
	public int getSkillId(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return skillId[idx];
	}
	/** 设置技能ID */
	public void setSkillId(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		skillId[idx] = i;
	}

	/**
	 * 物理攻击的动画
	 */
	int gfxid[];
	/**  */
	public int getGfxid(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return gfxid[idx];
	}
	/**  */
	public void setGfxid(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		gfxid[idx] = i;
	}

	/**
	 * 物理攻击动作ID
	 */
	int actid[];
	/**  */
	public int getActid(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return actid[idx];
	}
	/**  */
	public void setActid(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		actid[idx] = i;
	}

	/**
	 * 召唤怪のNPCID
	 */
	int summon[];
	/**  */
	public int getSummon(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return summon[idx];
	}
	/**  */
	public void setSummon(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		summon[idx] = i;
	}

	/**
	 * 召唤怪的最低数量
	 */
	int summonMin[];
	/**  */
	public int getSummonMin(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return summonMin[idx];
	}
	/**  */
	public void setSummonMin(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		summonMin[idx] = i;
	}

	/**
	 * 召唤怪的最高数量
	 */
	int summonMax[];
	/**  */
	public int getSummonMax(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return summonMax[idx];
	}
	/**  */
	public void setSummonMax(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		summonMax[idx] = i;
	}

	/**
	 * 强制变身ID
	 */
	int polyId[];
	/**  */
	public int getPolyId(int idx) {
		if (idx < 0 || idx >= getSkillSize()) {
			return 0;
		}
		return polyId[idx];
	}
	/** 设置强制变身ID */
	public void setPolyId(int idx, int i) {
		if (idx < 0 || idx >= getSkillSize()) {
			return;
		}
		polyId[idx] = i;
	}
}
