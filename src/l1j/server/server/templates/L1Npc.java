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

import l1j.server.server.model.L1Object;

/**
 * 
 */
public class L1Npc extends L1Object implements Cloneable {

	private static final long serialVersionUID = 1L;

	private int _npcid;

	private String _name;

	private String _impl;

	private int _level;

	private int _hp;

	private int _mp;

	private int _ac;

	private byte _str;

	private byte _con;

	private byte _dex;

	private byte _wis;

	private byte _int;

	private int _mr;

	private int _exp;

	private int _lawful;

	private String _size;

	private int _weakAttr;

	private int _ranged;

	private boolean _agrososc;

	private boolean _agrocoi;

	private boolean _tameable;

	private int _passispeed;

	private int _atkspeed;

	private boolean _agro;

	private int _gfxid;

	private String _nameid;

	private int _undead;

	private int _poisonatk;

	private int _paralysisatk;

	private int _family;

	private int _agrofamily;

	private int _agrogfxid1;

	private int _agrogfxid2;

	private boolean _picupitem;

	private int _digestitem;

	private boolean _bravespeed;

	private int _hprinterval;

	private int _hpr;

	private int _mprinterval;

	private int _mpr;

	private boolean _teleport;

	private int _randomlevel;

	private int _randomhp;

	private int _randommp;

	private int _randomac;

	private int _randomexp;

	private int _randomlawful;

	private int _damagereduction;

	private boolean _hard;

	private boolean _doppel;

	private boolean _tu;

	private boolean _erase;

	private int bowActId = 0;

	private int _karma;

	private int _transformId;

	private int _transformGfxId;

	private int _altAtkSpeed;

	private int _atkMagicSpeed;

	private int _subMagicSpeed;

	private int _lightSize;

	private boolean _amountFixed;

	private boolean _changeHead;

	private boolean _isCantResurrect;

	public L1Npc() {
	}

	@Override
	public L1Npc clone() {
		try {
			return (L1Npc) (super.clone());
		}
		catch (final CloneNotSupportedException e) {
			throw (new InternalError(e.getMessage()));
		}
	}

	public int get_ac() {
		return _ac;
	}

	public int get_agrofamily() {
		return _agrofamily;
	}

	public int get_atkspeed() {
		return _atkspeed;
	}

	public byte get_con() {
		return _con;
	}

	public int get_damagereduction() {
		return _damagereduction;
	}

	public byte get_dex() {
		return _dex;
	}

	public int get_digestitem() {
		return _digestitem;
	}

	public int get_exp() {
		return _exp;
	}

	public int get_family() {
		return _family;
	}

	public int get_gfxid() {
		return _gfxid;
	}

	public int get_hp() {
		return _hp;
	}

	public int get_hpr() {
		return _hpr;
	}

	public int get_hprinterval() {
		return _hprinterval;
	}

	public byte get_int() {
		return _int;
	}

	public boolean get_IsErase() {
		return _erase;
	}

	public boolean get_IsTU() {
		return _tu;
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

	public int get_mpr() {
		return _mpr;
	}

	public int get_mprinterval() {
		return _mprinterval;
	}

	public int get_mr() {
		return _mr;
	}

	public String get_name() {
		return _name;
	}

	public String get_nameid() {
		return _nameid;
	}

	public int get_npcId() {
		return _npcid;
	}

	public int get_paralysisatk() {
		return _paralysisatk;
	}

	public int get_passispeed() {
		return _passispeed;
	}

	public int get_poisonatk() {
		return _poisonatk;
	}

	public int get_randomac() {
		return _randomac;
	}

	public int get_randomexp() {
		return _randomexp;
	}

	public int get_randomhp() {
		return _randomhp;
	}

	public int get_randomlawful() {
		return _randomlawful;
	}

	public int get_randomlevel() {
		return _randomlevel;
	}

	public int get_randommp() {
		return _randommp;
	}

	public int get_ranged() {
		return _ranged;
	}

	public String get_size() {
		return _size;
	}

	public byte get_str() {
		return _str;
	}

	public int get_undead() {
		return _undead;
	}

	public int get_weakAttr() {
		return _weakAttr;
	}

	public byte get_wis() {
		return _wis;
	}

	public int getAltAtkSpeed() {
		return _altAtkSpeed;
	}

	public int getAtkMagicSpeed() {
		return _atkMagicSpeed;
	}

	public int getBowActId() {
		return bowActId;
	}

	public boolean getChangeHead() {
		return _changeHead;
	}

	public String getImpl() {
		return _impl;
	}

	public int getKarma() {
		return _karma;
	}

	public int getLightSize() {
		return _lightSize;
	}

	public int getSubMagicSpeed() {
		return _subMagicSpeed;
	}

	public int getTransformGfxId() {
		return _transformGfxId;
	}

	public int getTransformId() {
		return _transformId;
	}

	public boolean is_agro() {
		return _agro;
	}

	public boolean is_agrocoi() {
		return _agrocoi;
	}

	public int is_agrogfxid1() {
		return _agrogfxid1;
	}

	public int is_agrogfxid2() {
		return _agrogfxid2;
	}

	public boolean is_agrososc() {
		return _agrososc;
	}

	public boolean is_bravespeed() {
		return _bravespeed;
	}

	public boolean is_doppel() {
		return _doppel;
	}

	public boolean is_hard() {
		return _hard;
	}

	public boolean is_picupitem() {
		return _picupitem;
	}

	public boolean is_teleport() {
		return _teleport;
	}

	/**
	 * mapidsテーブルで設定されたモンスター量倍率の影響を受けるかどうかを返す。
	 * 
	 * @return 影響を受けないように設定されている場合はtrueを返す。
	 */
	public boolean isAmountFixed() {
		return _amountFixed;
	}

	public boolean isCantResurrect() {
		return _isCantResurrect;
	}

	public boolean isTamable() {
		return _tameable;
	}

	public void set_ac(final int i) {
		_ac = i;
	}

	public void set_agro(final boolean flag) {
		_agro = flag;
	}

	public void set_agrocoi(final boolean flag) {
		_agrocoi = flag;
	}

	public void set_agrofamily(final int i) {
		_agrofamily = i;
	}

	public void set_agrogfxid1(final int i) {
		_agrogfxid1 = i;
	}

	public void set_agrogfxid2(final int i) {
		_agrogfxid2 = i;
	}

	public void set_agrososc(final boolean flag) {
		_agrososc = flag;
	}

	public void set_atkspeed(final int i) {
		_atkspeed = i;
	}

	public void set_bravespeed(final boolean flag) {
		_bravespeed = flag;
	}

	public void set_con(final byte i) {
		_con = i;
	}

	public void set_damagereduction(final int i) {
		_damagereduction = i;
	}

	public void set_dex(final byte i) {
		_dex = i;
	}

	public void set_digestitem(final int i) {
		_digestitem = i;
	}

	public void set_doppel(final boolean flag) {
		_doppel = flag;
	}

	public void set_exp(final int i) {
		_exp = i;
	}

	public void set_family(final int i) {
		_family = i;
	}

	public void set_gfxid(final int i) {
		_gfxid = i;
	}

	public void set_hard(final boolean flag) {
		_hard = flag;
	}

	public void set_hp(final int i) {
		_hp = i;
	}

	public void set_hpr(final int i) {
		_hpr = i;
	}

	public void set_hprinterval(final int i) {
		_hprinterval = i;
	}

	public void set_int(final byte i) {
		_int = i;
	}

	public void set_IsErase(final boolean i) {
		_erase = i;
	}

	public void set_IsTU(final boolean i) {
		_tu = i;
	}

	public void set_lawful(final int i) {
		_lawful = i;
	}

	public void set_level(final int i) {
		_level = i;
	}

	public void set_mp(final int i) {
		_mp = i;
	}

	public void set_mpr(final int i) {
		_mpr = i;
	}

	public void set_mprinterval(final int i) {
		_mprinterval = i;
	}

	public void set_mr(final int i) {
		_mr = i;
	}

	public void set_name(final String s) {
		_name = s;
	}

	public void set_nameid(final String s) {
		_nameid = s;
	}

	public void set_npcId(final int i) {
		_npcid = i;
	}

	public void set_paralysisatk(final int i) {
		_paralysisatk = i;
	}

	public void set_passispeed(final int i) {
		_passispeed = i;
	}

	public void set_picupitem(final boolean flag) {
		_picupitem = flag;
	}

	public void set_poisonatk(final int i) {
		_poisonatk = i;
	}

	public void set_randomac(final int i) {
		_randomac = i;
	}

	public void set_randomexp(final int i) {
		_randomexp = i;
	}

	public void set_randomhp(final int i) {
		_randomhp = i;
	}

	public void set_randomlawful(final int i) {
		_randomlawful = i;
	}

	public void set_randomlevel(final int i) {
		_randomlevel = i;
	}

	public void set_randommp(final int i) {
		_randommp = i;
	}

	public void set_ranged(final int i) {
		_ranged = i;
	}

	public void set_size(final String s) {
		_size = s;
	}

	public void set_str(final byte i) {
		_str = i;
	}

	public void set_teleport(final boolean flag) {
		_teleport = flag;
	}

	public void set_undead(final int i) {
		_undead = i;
	}

	public void set_weakAttr(final int i) {
		_weakAttr = i;
	}

	public void set_wis(final byte i) {
		_wis = i;
	}

	public void setAltAtkSpeed(final int altAtkSpeed) {
		_altAtkSpeed = altAtkSpeed;
	}

	public void setAmountFixed(final boolean fixed) {
		_amountFixed = fixed;
	}

	public void setAtkMagicSpeed(final int atkMagicSpeed) {
		_atkMagicSpeed = atkMagicSpeed;
	}

	public void setBowActId(final int i) {
		bowActId = i;
	}

	public void setCantResurrect(final boolean isCantResurrect) {
		_isCantResurrect = isCantResurrect;
	}

	public void setChangeHead(final boolean changeHead) {
		_changeHead = changeHead;
	}

	public void setImpl(final String s) {
		_impl = s;
	}

	public void setKarma(final int i) {
		_karma = i;
	}

	public void setLightSize(final int lightSize) {
		_lightSize = lightSize;
	}

	public void setSubMagicSpeed(final int subMagicSpeed) {
		_subMagicSpeed = subMagicSpeed;
	}

	public void setTamable(final boolean flag) {
		_tameable = flag;
	}

	public void setTransformGfxId(final int i) {
		_transformGfxId = i;
	}

	public void setTransformId(final int transformId) {
		_transformId = transformId;
	}
}
