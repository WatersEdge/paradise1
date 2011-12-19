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
 * 防具相关
 */
public class L1Armor extends L1Item {

	private static final long serialVersionUID = 1L;

	public L1Armor() {
	}

	/** 物理防御 */
	private int _ac = 0;
	/** 设定物理防御 */
	public void set_ac(int i) {
		this._ac = i;
	}
	@Override
	public int get_ac() {
		return _ac;
	}

	/** 伤害减免 */
	private int _damageReduction = 0;
	/** 设定伤害减免 */
	public void setDamageReduction(int i) {
		_damageReduction = i;
	}
	@Override
	public int getDamageReduction() {
		return _damageReduction;
	}

	/** 负重轻减 */
	private int _weightReduction = 0;
	/** 设定负重轻减  */
	public void setWeightReduction(int i) {
		_weightReduction = i;
	}
	@Override
	public int getWeightReduction() {
		return _weightReduction;
	}

	/** 装备的命中率修正 */
	private int _hitModifierByArmor = 0;
	/** 设定装备的命中率修正 */
	public void setHitModifierByArmor(int i) {
		_hitModifierByArmor = i;
	}
	@Override
	public int getHitModifierByArmor() {
		return _hitModifierByArmor;
	}

	/** 装备的伤害修正 */
	private int _dmgModifierByArmor = 0;
	/** 设定装备的伤害修正 */
	public void setDmgModifierByArmor(int i) {
		_dmgModifierByArmor = i;
	}
	@Override
	public int getDmgModifierByArmor() {
		return _dmgModifierByArmor;
	}

	/** 弓类装备的命中率修正 */
	private int _bowHitModifierByArmor = 0;
	/** 设定弓类装备的命中率修正 */
	public void setBowHitModifierByArmor(int i) {
		_bowHitModifierByArmor = i;
	}
	@Override
	public int getBowHitModifierByArmor() {
		return _bowHitModifierByArmor;
	}

	/** 弓类装备的伤害修正 */
	private int _bowDmgModifierByArmor = 0;
	/** 设定弓类装备的伤害修正 */
	public void setBowDmgModifierByArmor(int i) {
		_bowDmgModifierByArmor = i;
	}
	@Override
	public int getBowDmgModifierByArmor() {
		return _bowDmgModifierByArmor;
	}

	/** 水属性防御 */
	private int _defense_water = 0;
	/** 设定水属性防御 */
	public void set_defense_water(int i) {
		_defense_water = i;
	}

	@Override
	public int get_defense_water() {
		return this._defense_water;
	}

	/** 风属性防御 */
	private int _defense_wind = 0;
	/** 设定风属性防御 */
	public void set_defense_wind(int i) {
		_defense_wind = i;
	}

	@Override
	public int get_defense_wind() {
		return this._defense_wind;
	}

	/** 火属性防御 */
	private int _defense_fire = 0;
	/** 设定火属性防御 */
	public void set_defense_fire(int i) {
		_defense_fire = i;
	}

	@Override
	public int get_defense_fire() {
		return this._defense_fire;
	}

	/** 地属性防御 */
	private int _defense_earth = 0;
	/** 设定地属性防御 */
	public void set_defense_earth(int i) {
		_defense_earth = i;
	}

	@Override
	public int get_defense_earth() {
		return this._defense_earth;
	}

	/** 昏迷耐性 */
	private int _regist_stun = 0;
	/** 设定昏迷耐性 */
	public void set_regist_stun(int i) {
		_regist_stun = i;
	}

	@Override
	public int get_regist_stun() {
		return this._regist_stun;
	}

	/** 石化耐性 */
	private int _regist_stone = 0;
	/** 设定石化耐性 */
	public void set_regist_stone(int i) {
		_regist_stone = i;
	}

	@Override
	public int get_regist_stone() {
		return this._regist_stone;
	}

	/** 睡眠耐性 */
	private int _regist_sleep = 0;
	/** 设定睡眠耐性 */
	public void set_regist_sleep(int i) {
		_regist_sleep = i;
	}

	@Override
	public int get_regist_sleep() {
		return this._regist_sleep;
	}

	/** 寒冰耐性 */
	private int _regist_freeze = 0;
	/** 设定寒冰耐性 */
	public void set_regist_freeze(int i) {
		_regist_freeze = i;
	}

	@Override
	public int get_regist_freeze() {
		return this._regist_freeze;
	}

	/** 支撑耐性 */
	private int _regist_sustain = 0;
	/** 设定支撑耐性 */
	public void set_regist_sustain(int i) {
		_regist_sustain = i;
	}

	@Override
	public int get_regist_sustain() {
		return this._regist_sustain;
	}

	/** 暗闇耐性 */
	private int _regist_blind = 0;
	/** 设定暗闇耐性 */
	public void set_regist_blind(int i) {
		_regist_blind = i;
	}

	@Override
	public int get_regist_blind() {
		return this._regist_blind;
	}

	/** 饰品级别 */
	private int _grade = 0;
	/** 设定饰品级别 */
	public void setGrade(int i) {
		_grade = i;
	}

	@Override
	public int getGrade() {
		return this._grade;
	}

}