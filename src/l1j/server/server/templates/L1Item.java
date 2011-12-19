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

import java.io.Serializable;

/**
 * 
 */
public abstract class L1Item implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public L1Item() {
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// throw (new InternalError(e.getMessage()));
			return null;
		}
	}

	// ■■■■■■ L1EtcItem,L1Weapon,L1Armor 共通项目 ■■■■■■

	/**
	 * 道具类型2 <br>
	 * @param 0 = L1EtcItem, 1 = L1Weapon, 2 = L1Armor
	 */
	private int _type2;

	/**
	 * 获得道具类型2 <br>
	 * @return 0 if L1EtcItem, 1 if L1Weapon, 2 if L1Armor
	 */
	public int getType2() {
		return _type2;
	}

	/**
	 * 设置道具类型2 <br>
	 * @return 0 if L1EtcItem, 1 if L1Weapon, 2 if L1Armor
	 */
	public void setType2(int type) {
		_type2 = type;
	}

	/** 道具ＩＤ */
	private int _itemId;
	/** 获得道具ＩＤ */
	public int getItemId() {
		return _itemId;
	}
	/** 设置道具ＩＤ */
	public void setItemId(int itemId) {
		_itemId = itemId;
	}

	/** 道具名称 */
	private String _name;
	/** 获得道具名称 */
	public String getName() {
		return _name;
	}
	/** 设置道具名称 */
	public void setName(String name) {
		_name = name;
	}

	/** 未鉴定道具的名称ＩＤ */
	private String _unidentifiedNameId;
	/** 获得未鉴定道具的名称ＩＤ */
	public String getUnidentifiedNameId() {
		return _unidentifiedNameId;
	}
	/** 设置未鉴定道具的名称ＩＤ */
	public void setUnidentifiedNameId(String unidentifiedNameId) {
		_unidentifiedNameId = unidentifiedNameId;
	}

	/** 鉴定道具的名称ＩＤ */
	private String _identifiedNameId;
	/** 获得鉴定道具的名称ＩＤ */
	public String getIdentifiedNameId() {
		return _identifiedNameId;
	}
	/** 设置鉴定道具的名称ＩＤ */
	public void setIdentifiedNameId(String identifiedNameId) {
		_identifiedNameId = identifiedNameId;
	}

	/** 详细类型 */
	private int _type;

	/*
	 * アイテムの種類を返す。<br>
	 * 
	 * @return <p>
	 *         [etcitem]<br>
	 *         0:arrow, 1:wand, 2:light, 3:gem, 4:totem, 5:firecracker,
	 *         6:potion, 7:food, 8:scroll, 9:questitem, 10:spellbook,
	 *         11:petitem, 12:other, 13:material, 14:event, 15:sting
	 *         </p>
	 *         <p>
	 *         [weapon]<br>
	 *         1:sword, 2:dagger, 3:tohandsword, 4:bow, 5:spear, 6:blunt,
	 *         7:staff, 8:throwingknife, 9:arrow, 10:gauntlet, 11:claw,
	 *         12:edoryu, 13:singlebow, 14:singlespear, 15:tohandblunt,
	 *         16:tohandstaff, 17:kiringku 18chainsword
	 *         </p>
	 *         <p>
	 *         [armor]<br>
	 *         1:helm, 2:armor, 3:T, 4:cloak, 5:glove, 6:boots, 7:shield,
	 *         8:amulet, 9:ring, 10:belt, 11:ring2, 12:earring
	 */
	/**
	 * 返回该项目的种类。<br>
	 * 
	 * @return <p>
	 *         [道具]<br>
	 *         0:箭头, 1:魔杖, 2:光线 (灯), 3:宝物 (金币), 4:图腾, 5:烟火,
	 *         6:货币 (名誉货币), 7:肉, 8:卷轴, 9:任务物品, 10:魔法书,
	 *         11:宠物装备, 12:其他, 13:材料, 14:活动物品, 15:飞刀
	 *         </p>
	 *         <p>
	 *         [武器]<br>
	 *         1:长剑, 2:短剑, 3:巨剑, 4:弓, 5:长矛, 6:斧,
	 *         7:魔杖, 8:飞刀, 9:箭头, 10:铁手甲, 11:钢爪,
	 *         12:双刀, 13:单手弓, 14:单手矛, 15:双手斧,
	 *         16:双手魔杖, 17:奇古兽 18:锁链剑
	 *         </p>
	 *         <p>
	 *         [装备]<br>
	 *         1:头盔, 2:盔甲, 3:T恤, 4:斗篷, 5:手套, 6:长靴, 7:盾牌,
	 *         8:项链, 9:戒指, 10:腰带, 11:戒指2, 12:耳环
	 */
	public int getType() {
		return _type;
	}
	/** 设置道具种类 */
	public void setType(int type) {
		_type = type;
	}

	/** 道具类型1 */
	private int _type1;

	/*
	 * アイテムの種類を返す。<br>
	 * 
	 * @return <p>
	 *         [weapon]<br>
	 *         sword:4, dagger:46, tohandsword:50, bow:20, blunt:11, spear:24,
	 *         staff:40, throwingknife:2922, arrow:66, gauntlet:62, claw:58,
	 *         edoryu:54, singlebow:20, singlespear:24, tohandblunt:11,
	 *         tohandstaff:40, kiringku:58, chainsword:24
	 *         </p>
	 */
	/**
	 * 返回该项目的种类。<br>
	 * 
	 * @return <p>
	 *         [武器]<br>
	 *         长剑:4, 短剑:46, 巨剑:50, 弓:20, 斧:11, 长矛:24,
	 *         魔杖:40, 飞刀:2922, 箭头:66, 铁手甲 :62, 钢爪:58,
	 *         双刀:54, 单手弓:20, 单手矛:24, 双手斧:11,
	 *         双手魔杖:40, 奇古兽:58, 锁链剑:24
	 *         </p>
	 */
	public int getType1() {
		return _type1;
	}
	/** 设置道具类型1 */
	public void setType1(int type1) {
		_type1 = type1;
	}

	/** 道具的材质 */
	private int _material;

	/*
	 * アイテムの素材を返す
	 * 
	 * @return 0:none 1:液体 2:web 3:植物性 4:動物性 5:紙 6:布 7:皮 8:木 9:骨 10:竜の鱗 11:鉄
	 *         12:鋼鉄 13:銅 14:銀 15:金 16:プラチナ 17:ミスリル 18:ブラックミスリル 19:ガラス 20:宝石
	 *         21:鉱物 22:オリハルコン
	 */
	/**
	 * 返回道具的材质
	 * 
	 * @return 0:无 1:液体 2:蛛丝 3:植物性 4:动物性 5:纸 6:布 7:皮 8:木 9:骨 10:龙鳞 11:铁
	 *         12:钢铁 13:铜 14:银 15:金 16:白金 17:米索莉 18:黑色米索莉 19:玻璃 20:宝石
	 *         21:矿物 22:オリハルコン
	 */
	public int getMaterial() {
		return _material;
	}
	/** 设置道具的材质 */
	public void setMaterial(int material) {
		_material = material;
	}

	/** 重量 */
	private int _weight;
	/** 获得重量 */
	public int getWeight() {
		return _weight;
	}
	/** 设置重量 */
	public void setWeight(int weight) {
		_weight = weight;
	}

	/** 清单内的图形ＩＤ */
	private int _gfxId;
	/** 获得清单内的图形ＩＤ */
	public int getGfxId() {
		return _gfxId;
	}
	/** 设置清单内的图形ＩＤ */
	public void setGfxId(int gfxId) {
		_gfxId = gfxId;
	}

	/** 道具放到地面上的图形ＩＤ */
	private int _groundGfxId;
	/** 获得道具放到地面上的图形ＩＤ */
	public int getGroundGfxId() {
		return _groundGfxId;
	}
	/** 设置道具放到地面上的图形ＩＤ */
	public void setGroundGfxId(int groundGfxId) {
		_groundGfxId = groundGfxId;
	}

	/** 道具的ItemDesc.tbl信息 */
	private int _itemDescId;

	/**
	 * 返回鉴定时显示的ItemDesc.tbl信息。
	 */
	public int getItemDescId() {
		return _itemDescId;
	}
	/** 设置鉴定时显示的ItemDesc.tbl信息 */
	public void setItemDescId(int descId) {
		_itemDescId = descId;
	}

	/** 能使用装备的最低ＬＶ */
	private int _minLevel;
	/** 获得能使用装备的最低ＬＶ */
	public int getMinLevel() {
		return _minLevel;
	}
	/** 设置能使用装备的最低ＬＶ */
	public void setMinLevel(int level) {
		_minLevel = level;
	}

	/** 能使用装备的最高ＬＶ */
	private int _maxLevel;
	/** 获得能使用装备的最高ＬＶ */
	public int getMaxLevel() {
		return _maxLevel;
	}
	/** 设置能使用装备的最高ＬＶ */
	public void setMaxLevel(int maxlvl) {
		_maxLevel = maxlvl;
	}

	/** 祝福状态 */
	private int _bless;
	/** 获得祝福状态 */
	public int getBless() {
		return _bless;
	}
	/** 设置祝福状态 */
	public void setBless(int i) {
		_bless = i;
	}

	/** 可交易／不可 */
	private boolean _tradable;
	/** 交易 */
	public boolean isTradable() {
		return _tradable;
	}
	/** 设置交易 */
	public void setTradable(boolean flag) {
		_tradable = flag;
	}

	/** 无法删除 */
	private boolean _cantDelete;
	/** 是无法删除 */
	public boolean isCantDelete() {
		return _cantDelete;
	}
	/** 设置无法删除 */
	public void setCantDelete(boolean flag) {
		_cantDelete = flag;
	}

	/** 将道具保存至资料库 */
	private boolean _save_at_once;

	/**
	 * 返回道具的个数变化并立即写入资料库中。
	 */
	public boolean isToBeSavedAtOnce() {
		return _save_at_once;
	}
	/** 设置道具的个数变化并立即写入资料库中 */
	public void setToBeSavedAtOnce(boolean flag) {
		_save_at_once = flag;
	}

	// ■■■■■■ L1EtcItem,L1Weapon 共通项目 ■■■■■■
	/** 最低伤害 */
	private int _dmgSmall = 0;
	/** 获得最低伤害 */
	public int getDmgSmall() {
		return _dmgSmall;
	}
	/** 设置最低伤害 */
	public void setDmgSmall(int dmgSmall) {
		_dmgSmall = dmgSmall;
	}

	/** 最高伤害 */
	private int _dmgLarge = 0;
	/** 获得最高伤害 */
	public int getDmgLarge() {
		return _dmgLarge;
	}
	/** 设置最高伤害 */
	public void setDmgLarge(int dmgLarge) {
		_dmgLarge = dmgLarge;
	}

	// ■■■■■■ L1EtcItem,L1Armor 共通项目 ■■■■■■

	// ■■■■■■ L1Weapon,L1Armor 共通项目 ■■■■■■
	/** ＯＥ安全圏 */
	private int _safeEnchant = 0;
	/** 获得ＯＥ安全圏 */
	public int get_safeenchant() {
		return _safeEnchant;
	}
	/** 设置ＯＥ安全圏 */
	public void set_safeenchant(int safeenchant) {
		_safeEnchant = safeenchant;
	}

	/** 王族可用装备 */
	private boolean _useRoyal = false;
	/** 是王族可用装备 */
	public boolean isUseRoyal() {
		return _useRoyal;
	}
	/** 设置王族可用装备 */
	public void setUseRoyal(boolean flag) {
		_useRoyal = flag;
	}
	/** 骑士可用装备 */
	private boolean _useKnight = false;
	/** 是骑士可用装备 */
	public boolean isUseKnight() {
		return _useKnight;
	}
	/** 设置骑士可用装备 */
	public void setUseKnight(boolean flag) {
		_useKnight = flag;
	}
	/** 精灵可用装备 */
	private boolean _useElf = false;
	/** 是精灵可用装备 */
	public boolean isUseElf() {
		return _useElf;
	}
	/** 设置精灵可用装备 */
	public void setUseElf(boolean flag) {
		_useElf = flag;
	}
	/** 法师可用装备 */
	private boolean _useMage = false;
	/** 是法师可用装备 */
	public boolean isUseMage() {
		return _useMage;
	}
	/** 设置法师可用装备 */
	public void setUseMage(boolean flag) {
		_useMage = flag;
	}
	/** 黑暗妖精可用装备 */
	private boolean _useDarkelf = false;
	/** 是黑暗妖精可用装备 */
	public boolean isUseDarkelf() {
		return _useDarkelf;
	}
	/** 设置黑暗妖精可用装备 */
	public void setUseDarkelf(boolean flag) {
		_useDarkelf = flag;
	}
	/** 龙骑士可用装备 */
	private boolean _useDragonknight = false;
	/** 是龙骑士可用装备 */
	public boolean isUseDragonknight() {
		return _useDragonknight;
	}
	/** 设置龙骑士可用装备 */
	public void setUseDragonknight(boolean flag) {
		_useDragonknight = flag;
	}
	/** 幻术师可用装备 */
	private boolean _useIllusionist = false;
	/** 是幻术师可用装备 */
	public boolean isUseIllusionist() {
		return _useIllusionist;
	}
	/** 设置幻术师可用装备 */
	public void setUseIllusionist(boolean flag) {
		_useIllusionist = flag;
	}

	/** 增加ＳＴＲ */
	private byte _addstr = 0;
	/** 获得增加ＳＴＲ */
	public byte get_addstr() {
		return _addstr;
	}
	/** 设置增加ＳＴＲ */
	public void set_addstr(byte addstr) {
		_addstr = addstr;
	}
	/** 增加ＤＥＸ */
	private byte _adddex = 0;
	/** 获得增加ＤＥＸ */
	public byte get_adddex() {
		return _adddex;
	}
	/** 设置增加ＤＥＸ */
	public void set_adddex(byte adddex) {
		_adddex = adddex;
	}
	/** 增加ＣＯＮ */
	private byte _addcon = 0;
	/** 获得增加ＣＯＮ */
	public byte get_addcon() {
		return _addcon;
	}
	/** 设置增加ＣＯＮ */
	public void set_addcon(byte addcon) {
		_addcon = addcon;
	}
	/** 增加ＩＮＴ */
	private byte _addint = 0;
	/** 获得增加ＩＮＴ */
	public byte get_addint() {
		return _addint;
	}
	/** 设置增加ＩＮＴ */
	public void set_addint(byte addint) {
		_addint = addint;
	}
	/** 增加ＷＩＳ */
	private byte _addwis = 0;
	/** 获得增加ＷＩＳ */
	public byte get_addwis() {
		return _addwis;
	}
	/** 设置增加ＷＩＳ */
	public void set_addwis(byte addwis) {
		_addwis = addwis;
	}
	/** 增加ＣＨＡ */
	private byte _addcha = 0;
	/** 获得增加ＣＨＡ */
	public byte get_addcha() {
		return _addcha;
	}
	/** 设置增加ＣＨＡ */
	public void set_addcha(byte addcha) {
		_addcha = addcha;
	}
	/** 增加ＨＰ */
	private int _addhp = 0;
	/** 获得增加ＨＰ */
	public int get_addhp() {
		return _addhp;
	}
	/** 设置增加ＨＰ */
	public void set_addhp(int addhp) {
		_addhp = addhp;
	}
	/** 增加ＭＰ */
	private int _addmp = 0;
	/** 获得增加ＭＰ */
	public int get_addmp() {
		return _addmp;
	}
	/** 设置增加ＭＰ */
	public void set_addmp(int addmp) {
		_addmp = addmp;
	}
	/** 增加ＨＰＲ */
	private int _addhpr = 0;
	/** 获得增加ＨＰＲ */
	public int get_addhpr() {
		return _addhpr;
	}
	/** 设置增加ＨＰＲ */
	public void set_addhpr(int addhpr) {
		_addhpr = addhpr;
	}
	/** 增加ＭＰＲ */
	private int _addmpr = 0;
	/** 获得增加ＭＰＲ */
	public int get_addmpr() {
		return _addmpr;
	}
	/** 设置增加ＭＰＲ */
	public void set_addmpr(int addmpr) {
		_addmpr = addmpr;
	}
	/** 增加ＳＰ */
	private int _addsp = 0;
	/** 获得增加ＳＰ */
	public int get_addsp() {
		return _addsp;
	}
	/** 设置增加ＳＰ */
	public void set_addsp(int addsp) {
		_addsp = addsp;
	}
	/** 增加ＭＲ */
	private int _mdef = 0;
	/** 获得增加ＭＲ */
	public int get_mdef() {
		return _mdef;
	}
	/** 设置增加ＭＲ */
	public void set_mdef(int i) {
		this._mdef = i;
	}
	/** 有无加速效果的道具 */
	private boolean _isHasteItem = false;
	/** 是加速道具 */
	public boolean isHasteItem() {
		return _isHasteItem;
	}
	/** 设置加速道具 */
	public void setHasteItem(boolean flag) {
		_isHasteItem = flag;
	}
	/** 最低使用时间 */
	private int _maxUseTime = 0;
	/** 获得最低使用时间 */
	public int getMaxUseTime() {
		return _maxUseTime;
	}
	/** 设置最低使用时间 */
	public void setMaxUseTime(int i) {
		_maxUseTime = i;
	}
	/** 使用类型 */
	private int _useType;

	/**
	 * 取得使用类型
	 */
	public int getUseType() {
		return _useType;
	}
	/** 设定使用类型 */
	public void setUseType(int useType) {
		_useType = useType;
	}
	/** 饱食度物品，如肉类。 */
	private int _foodVolume;

	/** 取得饱食度物品，如肉类。 */
	public int getFoodVolume() {
		return _foodVolume;
	}
	/** 设定饱食度物品，如肉类。 */
	public void setFoodVolume(int volume) {
		_foodVolume = volume;
	}

	/**
	 * 取得照明类道具的亮度设置。
	 */
	public int getLightRange() {
		if (_itemId == 40001) { // 灯
			return 11;
		} else if (_itemId == 40002) { // 灯笼
			return 14;
		} else if (_itemId == 40004) { // 魔法灯笼
			return 14;
		} else if (_itemId == 40005) { // 蜡烛
			return 8;
		} else {
			return 0;
		}
	}

	/**
	 * 取得照明类道具的燃料量。
	 */
	public int getLightFuel() {
		if (_itemId == 40001) { // 灯
			return 6000;
		} else if (_itemId == 40002) { // 灯笼
			return 12000;
		} else if (_itemId == 40003) { // 灯油
			return 12000;
		} else if (_itemId == 40004) { // 魔法灯笼
			return 0;
		} else if (_itemId == 40005) { // 蜡烛
			return 600;
		} else {
			return 0;
		}
	}

	// ■■■■■■ 由L1EtcItem 覆盖的道具 ■■■■■■
	/** 是可堆叠 */
	public boolean isStackable() {
		return false;
	}
	/** 取得X坐标 */
	public int get_locx() {
		return 0;
	}
	/** 取得Y坐标 */
	public int get_locy() {
		return 0;
	}
	/** 取得地图ID */
	public short get_mapid() {
		return 0;
	}
	/** 取得延迟ID */
	public int get_delayid() {
		return 0;
	}
	/** 取得延迟时间 */
	public int get_delaytime() {
		return 0;
	}
	/** 取得最高使用次数 */
	public int getMaxChargeCount() {
		return 0;
	}
	/** 是可以封印 */
	public boolean isCanSeal() {
		return false;
	}

	// ■■■■■■ 由L1Weapon 覆盖的项目 ■■■■■■
	/** 取得武器的射程范围 */
	public int getRange() {
		return 0;
	}
	/** 取得命中率修正 */
	public int getHitModifier() {
		return 0;
	}
	/** 取得伤害修正 */
	public int getDmgModifier() {
		return 0;
	}
	/** 取得双倍伤害发动几率 */
	public int getDoubleDmgChance() {
		return 0;
	}
	/** 取得魔法攻击的伤害修正 */
	public int getMagicDmgModifier() {
		return 0;
	}
	/** 取得有无损伤 */
	public int get_canbedmg() {
		return 0;
	}
	/** 是双手武器 */
	public boolean isTwohandedWeapon() {
		return false;
	}

	// ■■■■■■ 由L1Armor 覆盖的项目 ■■■■■■
	/** 取得AC */
	public int get_ac() {
		return 0;
	}
	/** 取得伤害减免 */
	public int getDamageReduction() {
		return 0;
	}
	/** 取得负重减轻 */
	public int getWeightReduction() {
		return 0;
	}
	/** 取得装备的命中修正 */
	public int getHitModifierByArmor() {
		return 0;
	}
	/** 取得装备的伤害修正 */
	public int getDmgModifierByArmor() {
		return 0;
	}
	/** 取得弓类装备的命中修正 */
	public int getBowHitModifierByArmor() {
		return 0;
	}
	/** 取得弓类装备的伤害修正 */
	public int getBowDmgModifierByArmor() {
		return 0;
	}
	/** 取得水属性防御 */
	public int get_defense_water() {
		return 0;
	}
	/** 取得火属性防御 */
	public int get_defense_fire() {
		return 0;
	}
	/** 取得地属性防御 */
	public int get_defense_earth() {
		return 0;
	}
	/** 取得风属性防御 */
	public int get_defense_wind() {
		return 0;
	}
	/** 取得昏迷耐性 */
	public int get_regist_stun() {
		return 0;
	}
	/** 取得石化耐性 */
	public int get_regist_stone() {
		return 0;
	}
	/** 取得昏睡耐性 */
	public int get_regist_sleep() {
		return 0;
	}
	/** 取得寒冰耐性 */
	public int get_regist_freeze() {
		return 0;
	}
	/** 取得支撑耐性 */
	public int get_regist_sustain() {
		return 0;
	}
	/** 取得暗闇耐性 */
	public int get_regist_blind() {
		return 0;
	}
	/** 取得饰品级别 */
	public int getGrade() {
		return 0;
	}

}
