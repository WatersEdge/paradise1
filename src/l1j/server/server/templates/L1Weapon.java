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
 * 武器相关
 */
public class L1Weapon extends L1Item {

	/**
	 * 序列版本UID
	 */
	private static final long serialVersionUID = 1L;

	public L1Weapon() {
	}

	/** 射程范围 */
	private int _range = 0;

	@Override
	public int getRange() {
		return _range;
	}

	/** 设定射程范围 */
	public void setRange(int i) {
		_range = i;
	}

	/** 命中率修正 */
	private int _hitModifier = 0;

	@Override
	public int getHitModifier() {
		return _hitModifier;
	}

	/** 设定命中率修正 */
	public void setHitModifier(int i) {
		_hitModifier = i;
	}

	/** 伤害修正 */
	private int _dmgModifier = 0;

	@Override
	public int getDmgModifier() {
		return _dmgModifier;
	}

	/** 设定伤害修正 */
	public void setDmgModifier(int i) {
		_dmgModifier = i;
	}

	/** DB、双倍伤害发动几率 */
	private int _doubleDmgChance;

	@Override
	public int getDoubleDmgChance() {
		return _doubleDmgChance;
	}

	/** 设定DB、双倍伤害发动几率 */
	public void setDoubleDmgChance(int i) {
		_doubleDmgChance = i;
	}

	/** 魔法攻击的伤害修正 */
	private int _magicDmgModifier = 0;

	@Override
	public int getMagicDmgModifier() {
		return _magicDmgModifier;
	}

	/** 设定魔法攻击的伤害修正 */
	public void setMagicDmgModifier(int i) {
		_magicDmgModifier = i;
	}

	/** 有无损伤 */
	private int _canbedmg = 0;

	@Override
	public int get_canbedmg() {
		return _canbedmg;
	}

	/** 设定有无损伤 */
	public void set_canbedmg(int i) {
		_canbedmg = i;
	}

	@Override
	public boolean isTwohandedWeapon() {
		int weapon_type = getType();

		boolean bool = (weapon_type == 3 // 巨剑
				|| weapon_type == 4 // 弓
				|| weapon_type == 5 // 长矛
				|| weapon_type == 11 // 钢爪
				|| weapon_type == 12 // 双刀
				|| weapon_type == 15 // 双手斧
				|| weapon_type == 16 // 双手魔杖
				|| weapon_type == 18 // 锁链剑
		|| weapon_type == 19 // 不明
		);

		return bool;
	}
}
