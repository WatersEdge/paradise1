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

public class L1PetItem {
	private int _itemId;

	private int _hitModifier;

	private int _damageModifier;

	private int _AddAc;

	private int _addStr;

	private int _addCon;

	private int _addDex;

	private int _addInt;

	private int _addWis;

	private int _addHp;

	private int _addMp;

	private int _addSp;

	private int _addMr;

	// 使用类型 - 牙齿? 1 防具? 0
	private int _useType;

	public L1PetItem() {
	}

	public int getAddAc() {
		return _AddAc;
	}

	public int getAddCon() {
		return _addCon;
	}

	public int getAddDex() {
		return _addDex;
	}

	public int getAddHp() {
		return _addHp;
	}

	public int getAddInt() {
		return _addInt;
	}

	public int getAddMp() {
		return _addMp;
	}

	public int getAddMr() {
		return _addMr;
	}

	public int getAddSp() {
		return _addSp;
	}

	public int getAddStr() {
		return _addStr;
	}

	public int getAddWis() {
		return _addWis;
	}

	public int getDamageModifier() {
		return _damageModifier;
	}

	public int getHitModifier() {
		return _hitModifier;
	}

	public int getItemId() {
		return _itemId;
	}

	public int getUseType() {
		return _useType;
	}

	public void setAddAc(final int i) {
		_AddAc = i;
	}

	public void setAddCon(final int i) {
		_addCon = i;
	}

	public void setAddDex(final int i) {
		_addDex = i;
	}

	public void setAddHp(final int i) {
		_addHp = i;
	}

	public void setAddInt(final int i) {
		_addInt = i;
	}

	public void setAddMp(final int i) {
		_addMp = i;
	}

	public void setAddMr(final int i) {
		_addMr = i;
	}

	public void setAddSp(final int i) {
		_addSp = i;
	}

	public void setAddStr(final int i) {
		_addStr = i;
	}

	public void setAddWis(final int i) {
		_addWis = i;
	}

	public void setDamageModifier(final int i) {
		_damageModifier = i;
	}

	public void setHitModifier(final int i) {
		_hitModifier = i;
	}

	public void setItemId(final int i) {
		_itemId = i;
	}

	public void setUseType(final int i) {
		_useType = i;
	}

}
