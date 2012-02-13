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
 * 技能相关
 */
public class L1Skills {

	/** 无属性 */
	public static final int ATTR_NONE = 0;
	/** 地属性 */
	public static final int ATTR_EARTH = 1;
	/** 火属性 */
	public static final int ATTR_FIRE = 2;
	/** 火属性 */
	public static final int ATTR_WATER = 4;
	/** 风属性 */
	public static final int ATTR_WIND = 8;
	/** 光属性 */
	public static final int ATTR_RAY = 16;
	/** 确率类型 */
	public static final int TYPE_PROBABILITY = 1;
	/** 变化类型 */
	public static final int TYPE_CHANGE = 2;
	/** 诅咒类型 */
	public static final int TYPE_CURSE = 4;
	/** 死亡类型 */
	public static final int TYPE_DEATH = 8;
	/** 治愈类型 */
	public static final int TYPE_HEAL = 16;
	/** 复活类型 */
	public static final int TYPE_RESTORE = 32;
	/** 攻击类型 */
	public static final int TYPE_ATTACK = 64;
	/** 其他类型 */
	public static final int TYPE_OTHER = 128;
	/** 目标自己 */
	public static final int TARGET_TO_ME = 0;
	/** 目标PC */
	public static final int TARGET_TO_PC = 1;
	/** 目标NPC */
	public static final int TARGET_TO_NPC = 2;
	/** 目标血盟 */
	public static final int TARGET_TO_CLAN = 4;
	/** 目标组队 */
	public static final int TARGET_TO_PARTY = 8;
	/** 目标宠物 */
	public static final int TARGET_TO_PET = 16;
	/** 目标位置 */
	public static final int TARGET_TO_PLACE = 32;
	/** 技能ID */
	private int _skillId;

	/** 技能名称 */
	private String _name;

	/** 技能等级 */
	private int _skillLevel;

	/** 技能编号 */
	private int _skillNumber;

	/** 消耗MP */
	private int _mpConsume;

	/** 消耗HP */
	private int _hpConsume;

	/** 消耗道具ID */
	private int _itmeConsumeId;

	/** 消耗道具数量 */
	private int _itmeConsumeCount;

	/** 技能重用延迟 (单位:毫秒) */
	private int _reuseDelay; // 单位：毫秒

	/** BUFF持续时间 (单位:秒) */
	private int _buffDuration; // 単位：秒

	/** 目标 */
	private String _target;

	/** 对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点 */
	private int _targetTo; // 对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点

	/** 伤害值 */
	private int _damageValue;

	/** 伤害骰子 */
	private int _damageDice;

	/** 伤害骰子数量 */
	private int _damageDiceCount;

	/** 概率值 */
	private int _probabilityValue;

	/** 概率骰子 */
	private int _probabilityDice;

	/** 属性 */
	private int _attr;

	/** 技能效果类型 */
	private int _type; // 类型

	/** 技能正义值 */
	private int _lawful;

	/** 远程技能 */
	private int _ranged;

	/** 技能范围 */
	private int _area;

	boolean _isThrough;

	private int _id;

	/** 技能名称ID */
	private String _nameId;

	/** 技能动作ID */
	private int _actionId;

	/** 技能图像Gfx */
	private int _castGfx;

	/** 技能图像Gfx2 */
	private int _castGfx2;

	/** 开始系统消息表ID */
	private int _sysmsgIdHappen;

	/** 停止系统消息表ID */
	private int _sysmsgIdStop;

	/** 缺少系统消息表 */
	private int _sysmsgIdFail;

	/** 取得技能动作ID */
	public int getActionId() {
		return _actionId;
	}

	/** 取得技能范围 */
	public int getArea() {
		return _area;
	}

	/**
	 * 返回技能的属性。<br>
	 * 0.无属性魔法,1.地魔法,2.火魔法,4.水魔法,8.风魔法,16.光魔法
	 */
	public int getAttr() {
		return _attr;
	}

	/** 取得BUFF持续时间 (单位:秒) */
	public int getBuffDuration() {
		return _buffDuration;
	}

	/** 取得技能图像Gfx */
	public int getCastGfx() {
		return _castGfx;
	}

	/** 取得技能图像Gfx2 */
	public int getCastGfx2() {
		return _castGfx2;
	}

	/** 取得伤害骰子 */
	public int getDamageDice() {
		return _damageDice;
	}

	/** 取得伤害骰子数量 */
	public int getDamageDiceCount() {
		return _damageDiceCount;
	}

	/** 取得伤害值 */
	public int getDamageValue() {
		return _damageValue;
	}

	/** 取得消耗HP */
	public int getHpConsume() {
		return _hpConsume;
	}

	public int getId() {
		return _id;
	}

	/** 取得消耗道具数量 */
	public int getItemConsumeCount() {
		return _itmeConsumeCount;
	}

	/** 取得消耗道具ID */
	public int getItemConsumeId() {
		return _itmeConsumeId;
	}

	/** 取得技能正义值 */
	public int getLawful() {
		return _lawful;
	}

	/** 取得消耗MP */
	public int getMpConsume() {
		return _mpConsume;
	}

	/** 取得技能名称 */
	public String getName() {
		return _name;
	}

	/** 取得技能名称ID */
	public String getNameId() {
		return _nameId;
	}

	/** 取得概率骰子 */
	public int getProbabilityDice() {
		return _probabilityDice;
	}

	/** 取得概率值 */
	public int getProbabilityValue() {
		return _probabilityValue;
	}

	/** 取得远程技能 */
	public int getRanged() {
		return _ranged;
	}

	/** 取得技能重用延迟 (单位:毫秒) */
	public int getReuseDelay() {
		return _reuseDelay;
	}

	/** 取得技能ID */
	public int getSkillId() {
		return _skillId;
	}

	/** 取得技能等级 */
	public int getSkillLevel() {
		return _skillLevel;
	}

	/** 取得技能编号 */
	public int getSkillNumber() {
		return _skillNumber;
	}

	/** 取得缺少系统消息表 */
	public int getSysmsgIdFail() {
		return _sysmsgIdFail;
	}

	/** 取得开始系统消息表ID */
	public int getSysmsgIdHappen() {
		return _sysmsgIdHappen;
	}

	/** 取得停止系统消息表ID */
	public int getSysmsgIdStop() {
		return _sysmsgIdStop;
	}

	/** 取得目标 */
	public String getTarget() {
		return _target;
	}

	/** 取得对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点 */
	public int getTargetTo() {
		return _targetTo;
	}

	/**
	 * 返回技能效果类型。<br>
	 * 1.几率系,2.变化,4.诅咒,8.死亡,16.治疗,32.复活,64.攻击,128.其他特殊
	 */
	public int getType() {
		return _type;
	}

	public boolean isThrough() {
		return _isThrough;
	}

	/** 设定技能动作ID */
	public void setActionId(final int i) {
		_actionId = i;
	}

	/** 设定技能范围 */
	public void setArea(final int i) {
		_area = i;
	}

	/** 设定技能属性 */
	public void setAttr(final int i) {
		_attr = i;
	}

	/** 设定BUFF持续时间 (单位:秒) */
	public void setBuffDuration(final int i) {
		_buffDuration = i;
	}

	/** 设定技能图像Gfx */
	public void setCastGfx(final int i) {
		_castGfx = i;
	}

	/** 设定技能图像Gfx2 */
	public void setCastGfx2(final int i) {
		_castGfx2 = i;
	}

	/** 设定伤害骰子 */
	public void setDamageDice(final int i) {
		_damageDice = i;
	}

	/** 设定伤害骰子数量 */
	public void setDamageDiceCount(final int i) {
		_damageDiceCount = i;
	}

	/** 设定伤害值 */
	public void setDamageValue(final int i) {
		_damageValue = i;
	}

	/** 设定消耗HP */
	public void setHpConsume(final int i) {
		_hpConsume = i;
	}

	public void setId(final int i) {
		_id = i;
	}

	/** 设定消耗道具数量 */
	public void setItemConsumeCount(final int i) {
		_itmeConsumeCount = i;
	}

	/** 设定消耗道具ID */
	public void setItemConsumeId(final int i) {
		_itmeConsumeId = i;
	}

	/** 设定技能正义值 */
	public void setLawful(final int i) {
		_lawful = i;
	}

	/** 设定消耗MP */
	public void setMpConsume(final int i) {
		_mpConsume = i;
	}

	/** 设定技能名称 */
	public void setName(final String s) {
		_name = s;
	}

	/** 设定技能名称ID */
	public void setNameId(final String s) {
		_nameId = s;
	}

	/** 设定概率骰子 */
	public void setProbabilityDice(final int i) {
		_probabilityDice = i;
	}

	/** 设定概率值 */
	public void setProbabilityValue(final int i) {
		_probabilityValue = i;
	}

	/** 设定远程技能 */
	public void setRanged(final int i) {
		_ranged = i;
	}

	/** 设定技能重用延迟 (单位:毫秒) */
	public void setReuseDelay(final int i) {
		_reuseDelay = i;
	}

	/** 设定技能ID */
	public void setSkillId(final int i) {
		_skillId = i;
	}

	/** 设定技能等级 */
	public void setSkillLevel(final int i) {
		_skillLevel = i;
	}

	/** 设定技能编号 */
	public void setSkillNumber(final int i) {
		_skillNumber = i;
	}

	/** 设定缺少系统消息表 */
	public void setSysmsgIdFail(final int i) {
		_sysmsgIdFail = i;
	}

	/** 设定开始系统消息表ID */
	public void setSysmsgIdHappen(final int i) {
		_sysmsgIdHappen = i;
	}

	/** 设定停止系统消息表ID */
	public void setSysmsgIdStop(final int i) {
		_sysmsgIdStop = i;
	}

	/** 设定目标 */
	public void setTarget(final String s) {
		_target = s;
	}

	/** 设定对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点 */
	public void setTargetTo(final int i) {
		_targetTo = i;
	}

	public void setThrough(final boolean flag) {
		_isThrough = flag;
	}

	/** 设定技能效果类型 */
	public void setType(final int i) {
		_type = i;
	}

}
