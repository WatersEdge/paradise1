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

public class L1Pet {
	private int _itemobjid;

	private int _objid;

	private int _npcid;

	private String _name;

	private int _level;

	private int _hp;

	private int _mp;

	private int _exp;

	private int _lawful;

	// 饱食度
	private int _food;

	public L1Pet() {
	}

	public int get_exp() {
		return _exp;
	}

	public int get_food() {
		return _food;
	}

	public int get_hp() {
		return _hp;
	}

	public int get_itemobjid() {
		return _itemobjid;
	}

	public int get_lawful() {
		return _lawful;
	}

	public int get_level() {
		return _level;
	}

	public int get_mp() {
		return _mp;
	}

	public String get_name() {
		return _name;
	}

	public int get_npcid() {
		return _npcid;
	}

	public int get_objid() {
		return _objid;
	}

	public void set_exp(int i) {
		_exp = i;
	}

	public void set_food(int i) {
		_food = i;
	}

	public void set_hp(int i) {
		_hp = i;
	}

	public void set_itemobjid(int i) {
		_itemobjid = i;
	}

	public void set_lawful(int i) {
		_lawful = i;
	}

	public void set_level(int i) {
		_level = i;
	}

	public void set_mp(int i) {
		_mp = i;
	}

	public void set_name(String s) {
		_name = s;
	}

	public void set_npcid(int i) {
		_npcid = i;
	}

	public void set_objid(int i) {
		_objid = i;
	}
}