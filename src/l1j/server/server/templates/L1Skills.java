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
 *	技能相关
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
	/** 取得技能ID */
	public int getSkillId() {
		return _skillId;
	}
	/** 设定技能ID */
	public void setSkillId(int i) {
		_skillId = i;
	}
	/** 技能名称 */
	private String _name;
	/** 取得技能名称 */
	public String getName() {
		return _name;
	}
	/** 设定技能名称 */
	public void setName(String s) {
		_name = s;
	}
	/** 技能等级 */
	private int _skillLevel;
	/** 取得技能等级 */
	public int getSkillLevel() {
		return _skillLevel;
	}
	/** 设定技能等级 */
	public void setSkillLevel(int i) {
		_skillLevel = i;
	}
	/** 技能编号 */
	private int _skillNumber;
	/** 取得技能编号 */
	public int getSkillNumber() {
		return _skillNumber;
	}
	/** 设定技能编号 */
	public void setSkillNumber(int i) {
		_skillNumber = i;
	}
	/** 消耗MP */
	private int _mpConsume;
	/** 取得消耗MP */
	public int getMpConsume() {
		return _mpConsume;
	}
	/** 设定消耗MP */
	public void setMpConsume(int i) {
		_mpConsume = i;
	}
	/** 消耗HP */
	private int _hpConsume;
	/** 取得消耗HP */
	public int getHpConsume() {
		return _hpConsume;
	}
	/** 设定消耗HP */
	public void setHpConsume(int i) {
		_hpConsume = i;
	}
	/** 消耗道具ID */
	private int _itmeConsumeId;
	/** 取得消耗道具ID */
	public int getItemConsumeId() {
		return _itmeConsumeId;
	}
	/** 设定消耗道具ID */
	public void setItemConsumeId(int i) {
		_itmeConsumeId = i;
	}
	/** 消耗道具数量 */
	private int _itmeConsumeCount;
	/** 取得消耗道具数量 */
	public int getItemConsumeCount() {
		return _itmeConsumeCount;
	}
	/** 设定消耗道具数量 */
	public void setItemConsumeCount(int i) {
		_itmeConsumeCount = i;
	}
	/** 技能重用延迟 (单位:毫秒) */
	private int _reuseDelay; // 单位：毫秒
	/** 取得技能重用延迟 (单位:毫秒) */
	public int getReuseDelay() {
		return _reuseDelay;
	}
	/** 设定技能重用延迟 (单位:毫秒) */
	public void setReuseDelay(int i) {
		_reuseDelay = i;
	}
	/** BUFF持续时间 (单位:秒) */
	private int _buffDuration; // 単位：秒
	/** 取得BUFF持续时间 (单位:秒) */
	public int getBuffDuration() {
		return _buffDuration;
	}
	/** 设定BUFF持续时间 (单位:秒) */
	public void setBuffDuration(int i) {
		_buffDuration = i;
	}
	/** 目标 */
	private String _target;
	/** 取得目标 */
	public String getTarget() {
		return _target;
	}
	/** 设定目标 */
	public void setTarget(String s) {
		_target = s;
	}
	/** 对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点 */
	private int _targetTo; // 对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点
	/** 取得对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点 */
	public int getTargetTo() {
		return _targetTo;
	}
	/** 设定对象 0:自身 1:PC 2:NPC 4:血盟 8:组队 16:宠物 32:地点 */
	public void setTargetTo(int i) {
		_targetTo = i;
	}
	/** 伤害值 */
	private int _damageValue;
	/** 取得伤害值 */
	public int getDamageValue() {
		return _damageValue;
	}
	/** 设定伤害值 */
	public void setDamageValue(int i) {
		_damageValue = i;
	}
	/** 伤害骰子 */
	private int _damageDice;
	/** 取得伤害骰子 */
	public int getDamageDice() {
		return _damageDice;
	}
	/** 设定伤害骰子 */
	public void setDamageDice(int i) {
		_damageDice = i;
	}
	/** 伤害骰子数量 */
	private int _damageDiceCount;
	/** 取得伤害骰子数量 */
	public int getDamageDiceCount() {
		return _damageDiceCount;
	}
	/** 设定伤害骰子数量 */
	public void setDamageDiceCount(int i) {
		_damageDiceCount = i;
	}
	/** 概率值 */
	private int _probabilityValue;
	/** 取得概率值 */
	public int getProbabilityValue() {
		return _probabilityValue;
	}
	/** 设定概率值 */
	public void setProbabilityValue(int i) {
		_probabilityValue = i;
	}
	/** 概率骰子 */
	private int _probabilityDice;
	/** 取得概率骰子 */
	public int getProbabilityDice() {
		return _probabilityDice;
	}
	/** 设定概率骰子 */
	public void setProbabilityDice(int i) {
		_probabilityDice = i;
	}
	/** 属性 */
	private int _attr;

	/**
	 * 返回技能的属性。<br>
	 * 0.无属性魔法,1.地魔法,2.火魔法,4.水魔法,8.风魔法,16.光魔法
	 */
	public int getAttr() {
		return _attr;
	}
	/** 设定技能属性 */
	public void setAttr(int i) {
		_attr = i;
	}
	/** 技能效果类型 */
	private int _type; // 类型

	/**
	 * 返回技能效果类型。<br>
	 * 1.几率系,2.变化,4.诅咒,8.死亡,16.治疗,32.复活,64.攻击,128.其他特殊
	 */
	public int getType() {
		return _type;
	}
	/** 设定技能效果类型 */
	public void setType(int i) {
		_type = i;
	}
	/** 技能正义值 */
	private int _lawful;
	/** 取得技能正义值 */
	public int getLawful() {
		return _lawful;
	}
	/** 设定技能正义值 */
	public void setLawful(int i) {
		_lawful = i;
	}
	/** 远程技能 */
	private int _ranged;
	/** 取得远程技能 */
	public int getRanged() {
		return _ranged;
	}
	/** 设定远程技能 */
	public void setRanged(int i) {
		_ranged = i;
	}
	/** 技能范围 */
	private int _area;
	/** 取得技能范围 */
	public int getArea() {
		return _area;
	}
	/** 设定技能范围 */
	public void setArea(int i) {
		_area = i;
	}

	boolean _isThrough;

	public boolean isThrough() {
		return _isThrough;
	}

	public void setThrough(boolean flag) {
		_isThrough = flag;
	}

	private int _id;

	public int getId() {
		return _id;
	}

	public void setId(int i) {
		_id = i;
	}
	/** 技能名称ID */
	private String _nameId;
	/** 取得技能名称ID */
	public String getNameId() {
		return _nameId;
	}
	/** 设定技能名称ID */
	public void setNameId(String s) {
		_nameId = s;
	}
	/** 技能动作ID */
	private int _actionId;
	/** 取得技能动作ID */
	public int getActionId() {
		return _actionId;
	}
	/** 设定技能动作ID */
	public void setActionId(int i) {
		_actionId = i;
	}
	/** 技能图像Gfx */
	private int _castGfx;
	/** 取得技能图像Gfx */
	public int getCastGfx() {
		return _castGfx;
	}
	/** 设定技能图像Gfx */
	public void setCastGfx(int i) {
		_castGfx = i;
	}
	/** 技能图像Gfx2 */
	private int _castGfx2;
	/** 取得技能图像Gfx2 */
	public int getCastGfx2() {
		return _castGfx2;
	}
	/** 设定技能图像Gfx2 */
	public void setCastGfx2(int i) {
		_castGfx2 = i;
	}
	/** 开始系统消息表ID */
	private int _sysmsgIdHappen;
	/** 取得开始系统消息表ID */
	public int getSysmsgIdHappen() {
		return _sysmsgIdHappen;
	}
	/** 设定开始系统消息表ID */
	public void setSysmsgIdHappen(int i) {
		_sysmsgIdHappen = i;
	}
	/** 停止系统消息表ID */
	private int _sysmsgIdStop;
	/** 取得停止系统消息表ID */
	public int getSysmsgIdStop() {
		return _sysmsgIdStop;
	}
	/** 设定停止系统消息表ID */
	public void setSysmsgIdStop(int i) {
		_sysmsgIdStop = i;
	}
	/** 缺少系统消息表 */
	private int _sysmsgIdFail;
	/** 取得缺少系统消息表 */
	public int getSysmsgIdFail() {
		return _sysmsgIdFail;
	}
	/** 设定缺少系统消息表 */
	public void setSysmsgIdFail(int i) {
		_sysmsgIdFail = i;
	}

}
