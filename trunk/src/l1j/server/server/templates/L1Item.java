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
	 * 道具类型2
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
	 * @param  type 0 if L1EtcItem, 1 if L1Weapon, 2 if L1Armor
	 */
	public void setType2(int type) {
		_type2 = type;
	}

	/**
	 * 道具ＩＤ
	 */
	private int _itemId;

	/**
	 * 获得道具ＩＤ
	 * @return
	 */
	public int getItemId() {
		return _itemId;
	}

	/**
	 * 设置道具ＩＤ
	 * @param itemId
	 */
	public void setItemId(int itemId) {
		_itemId = itemId;
	}

	/**
	 * 道具名称
	 */
	private String _name;

	/**
	 * 获得道具名称
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * 设置道具名称
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * 未鉴定道具的名称ＩＤ
	 */
	private String _unidentifiedNameId;

	/**
	 * 获得未鉴定道具的名称ＩＤ
	 * @return
	 */
	public String getUnidentifiedNameId() {
		return _unidentifiedNameId;
	}

	/**
	 * 设置未鉴定道具的名称ＩＤ
	 * @param unidentifiedNameId
	 */
	public void setUnidentifiedNameId(String unidentifiedNameId) {
		_unidentifiedNameId = unidentifiedNameId;
	}

	/**
	 * 鉴定道具的名称ＩＤ
	 */
	private String _identifiedNameId;

	/**
	 * 获得鉴定道具的名称ＩＤ
	 * @return
	 */
	public String getIdentifiedNameId() {
		return _identifiedNameId;
	}

	/**
	 * 设置鉴定道具的名称ＩＤ
	 * @param identifiedNameId
	 */
	public void setIdentifiedNameId(String identifiedNameId) {
		_identifiedNameId = identifiedNameId;
	}

	/**
	 * 道具的详细类型
	 */
	private int _type;

	/**
	 * 返回该项目的种类<br>
	 *
	 * @return <p>
	 *         <font color=#ff0000>[etcitem]-道具类型</font><br>
	 *         0:arrow <font color=#00800>箭</font><br>
	 *         1:wand <font color=#00800>魔杖</font><br>
	 *         2:light <font color=#00800>光线 (灯)</font><br>
	 *         3:gem <font color=#00800>宝物 (金币)</font><br>
	 *         4:totem <font color=#00800>图腾</font><br>
	 *         5:firecracker <font color=#00800>烟火</font><br>
	 *         6:potion <font color=#00800>货币 (名誉货币)</font><br>
	 *         7:food <font color=#00800>肉</font><br>
	 *         8:scroll <font color=#00800>卷轴</font><br>
	 *         9:questitem <font color=#00800>任务物品</font><br>
	 *         10:spellbook <font color=#00800>魔法书</font><br>
	 *         11:petitem <font color=#00800>宠物装备</font><br>
	 *         12:other <font color=#00800>其他</font><br>
	 *         13:material <font color=#00800>材料</font><br>
	 *         14:event <font color=#00800>活动物品</font><br>
	 *         15:sting <font color=#00800>飞刀</font><br>
	 *         </p>
	 *         <p>
	 *         <font color=#ff0000>[weapon]-武器类型</font><br>
	 *         1: sword <font color=#00800>剑(单手)</font><br>
	 *         2: dagger <font color=#00800>匕首(单手)</font><br>
	 *         3: tohandsword <font color=#00800>双手剑(双手)</font><br>
	 *         4: bow <font color=#00800>弓(双手)</font><br>
	 *         5: spear <font color=#00800>矛(双手)</font><br>
	 *         6: blunt <font color=#00800>斧(单手)</font><br>
	 *         7: staff <font color=#00800>魔杖(单手)</font><br>
	 *         8: throwingknife <font color=#00800>飞刀</font><br>
	 *         9: arrow <font color=#00800>箭</font><br>
	 *         10: gauntlet <font color=#00800>铁手甲</font><br>
	 *         11: claw <font color=#00800>钢爪(双手)</font><br>
	 *         12: edoryu <font color=#00800>双刀(双手)</font><br>
	 *         13: singlebow <font color=#00800>弓(单手)</font><br>
	 *         14: singlespear <font color=#00800>矛(单手)</font><br>
	 *         15: tohandblunt <font color=#00800>双手斧(双手)</font><br>
	 *         16: tohandstaff <font color=#00800>魔杖(双手)</font><br>
	 *         17: kiringku <font color=#00800>奇古兽(单手)</font><br>
	 *         18: chainsword <font color=#00800>锁链剑(单手)</font><br>
	 *         </p>
	 *         <p>
	 *         <font color=#ff0000>[armor]-防具类型</font><br>
	 *         1: helm <font color=#00800>头盔</font><br>
	 *         2: armor <font color=#00800>盔甲</font><br>
	 *         3: T <font color=#00800>内衣</font><br>
	 *         4: cloak <font color=#00800>斗篷</font><br>
	 *         5: glove <font color=#00800>手套</font><br>
	 *         6: boots <font color=#00800>靴子</font><br>
	 *         7: shield <font color=#00800>盾</font><br>
	 *         8: amulet <font color=#00800>项链</font><br>
	 *         9: ring <font color=#00800>戒指</font><br>
	 *         10: belt <font color=#00800>腰带</font><br>
	 *         11: ring2 <font color=#00800>戒指2</font><br>
	 *         12: earring <font color=#00800>耳环</font> <br>
	 *         13: guarder <font color=#00800>臂甲</font><br>
	 *         14: tattoo_r <font color=#00800>辅助装备 (右)</font><br>
	 *         15: tattoo_l <font color=#00800>辅助装备 (左)</font><br>
	 *         16: tattoo_m <font color=#00800>辅助装备 (中)</font><br>
	 *         </p>
	 */
	public int getType() {
		return _type;
	}

	/**
	 * 设置道具种类
	 * @param type
	 */
	public void setType(int type) {
		_type = type;
	}

	/**
	 * 道具类型1 (武器)
	 */
	private int _type1;

	/**
	 * 返回该项目的种类<br>
	 *
	 * @return <p>
	 *         <font color=#ff0000>[weapon]-武器类型</font><br>
	 *         sword: 4 <font color=#00800>长剑</font><br>
	 *         dagger: 46 <font color=#00800>匕首</font><br>
	 *         tohandsword: 50 <font color=#00800>双手剑</font><br>
	 *         bow: 20 <font color=#00800>弓</font><br>
	 *         blunt: 11 <font color=#00800>斧(单手)</font><br>
	 *         spear: 24 <font color=#00800>矛(双手)</font><br>
	 *         staff: 40 <font color=#00800>魔杖</font><br>
	 *         throwingknife: 2922 <font color=#00800>飞刀</font><br>
	 *         arrow: 66 <font color=#00800>箭</font><br>
	 *         gauntlet: 62 <font color=#00800>铁手甲</font><br>
	 *         claw: 58 <font color=#00800>钢爪</font><br>
	 *         edoryu: 54 <font color=#00800>双刀</font><br>
	 *         singlebow: 20 <font color=#00800>弓(单手)</font><br>
	 *         singlespear: 24 <font color=#00800>矛(单手)</font><br>
	 *         tohandblunt: 11 <font color=#00800>双手斧</font><br>
	 *         tohandstaff: 40 <font color=#00800>魔杖(双手)</font><br>
	 *         kiringku: 58 <font color=#00800>奇古兽</font><br>
	 *         chainsword: 24 <font color=#00800>锁链剑</font><br>
	 *         </p>
	 */
	public int getType1() {
		return _type1;
	}

	/**
	 * 设置道具类型1 (武器)
	 * @param type1
	 */
	public void setType1(int type1) {
		_type1 = type1;
	}

	/**
	 * 道具的材质
	 */
	private int _material;

	/**
	 * 返回道具的材质<br>
	 * 
	 * @return <p>
	 * 		   <font color=#ff0000>道具的材质</font><br>
	 * 			none: 0 <font color=#00800>无</font><br>
	 * 			liquid: 1 <font color=#00800>液体</font><br>
	 * 			web: 2 <font color=#00800>蜡</font><br>
	 *			vegetation: 3 <font color=#00800>植物性</font><br>
	 * 			animalmatter: 4 <font color=#00800>动物性</font><br>
	 * 			paper: 5 <font color=#00800>纸</font><br>
	 * 			cloth: 6 <font color=#00800>布</font><br>
	 * 			leather: 7 <font color=#00800>皮革</font><br>
	 * 			wood: 8 <font color=#00800>木</font><br>
	 * 			bone: 9 <font color=#00800>骨</font><br>
	 * 			dragonscale: 10 <font color=#00800>龙鳞</font><br>
	 * 			iron: 11 <font color=#00800>铁</font><br>
	 * 			steel: 12 <font color=#00800>钢铁</font><br>
	 * 			copper: 13 <font color=#00800>铜</font><br>
	 * 			silver: 14 <font color=#00800>银</font><br>
	 * 			gold: 15 <font color=#00800>金</font><br>
	 * 			platinum: 16 <font color=#00800>白金</font><br>
	 * 			mithril: 17 <font color=#00800>米索莉</font><br>
	 * 			blackmithril: 18 <font color=#00800>黑色米索莉</font><br>
	 * 			glass: 19 <font color=#00800>玻璃</font><br>
	 * 			gemstone: 20 <font color=#00800>宝石</font><br>
	 * 			mineral: 21 <font color=#00800>矿物</font><br>
	 * 			oriharukon: 22 <font color=#00800>奥里哈鲁根</font><br>
	 * 			</p>
	 */
	public int getMaterial() {
		return _material;
	}

	/**
	 * 设置道具的材质
	 * @param material
	 */
	public void setMaterial(int material) {
		_material = material;
	}

	/**
	 * 重量
	 */
	private int _weight;

	/**
	 * 获得重量
	 * @return
	 */
	public int getWeight() {
		return _weight;
	}

	/**
	 * 设置重量
	 * @param weight
	 */
	public void setWeight(int weight) {
		_weight = weight;
	}

	/**
	 * 清单内的图形ＩＤ
	 */
	private int _gfxId;

	/**
	 * 获得清单内的图形ＩＤ
	 * @return
	 */
	public int getGfxId() {
		return _gfxId;
	}

	/**
	 * 设置清单内的图形ＩＤ
	 * @param gfxId
	 */
	public void setGfxId(int gfxId) {
		_gfxId = gfxId;
	}

	/**
	 * 道具放到地面上的图形ＩＤ
	 */
	private int _groundGfxId;

	/**
	 * 获得道具放到地面上的图形ＩＤ
	 * @return
	 */
	public int getGroundGfxId() {
		return _groundGfxId;
	}

	/**
	 * 设置道具放到地面上的图形ＩＤ
	 * @param groundGfxId
	 */
	public void setGroundGfxId(int groundGfxId) {
		_groundGfxId = groundGfxId;
	}

	/**
	 * 道具的ItemDesc.tbl信息
	 */
	private int _itemDescId;

	/**
	 * 返回鉴定时显示的ItemDesc.tbl信息。
	 * @return
	 */
	public int getItemDescId() {
		return _itemDescId;
	}

	/**
	 * 设置鉴定时显示的ItemDesc.tbl信息
	 * @param descId
	 */
	public void setItemDescId(int descId) {
		_itemDescId = descId;
	}

	/**
	 * 能使用装备的最低ＬＶ
	 */
	private int _minLevel;

	/**
	 * 获得能使用装备的最低ＬＶ
	 * @return
	 */
	public int getMinLevel() {
		return _minLevel;
	}

	/**
	 * 设置能使用装备的最低ＬＶ
	 * @param level
	 */
	public void setMinLevel(int level) {
		_minLevel = level;
	}

	/**
	 * 能使用装备的最高ＬＶ
	 */
	private int _maxLevel;

	/**
	 * 获得能使用装备的最高ＬＶ
	 * @return
	 */
	public int getMaxLevel() {
		return _maxLevel;
	}

	/**
	 * 设置能使用装备的最高ＬＶ
	 * @param maxlvl
	 */
	public void setMaxLevel(int maxlvl) {
		_maxLevel = maxlvl;
	}

	/**
	 * 取得属性
	 */
	private int _bless;

	/**
	 * 取得属性
	 * @return 0:祝福 1:一般 2:诅咒
	 */
	public int getBless() {
		return _bless;
	}

	/**
	 * 设置取得属性
	 * @param i
	 */
	public void setBless(int i) {
		_bless = i;
	}

	/**
	 * 可交易／不可
	 */
	private boolean _tradable;

	/**
	 * 交易
	 * @return true:可以 false:不可以
	 */
	public boolean isTradable() {
		return _tradable;
	}

	/**
	 * 设置交易
	 * @param flag
	 */
	public void setTradable(boolean flag) {
		_tradable = flag;
	}

	/**
	 * 删除
	 */
	private boolean _cantDelete;

	/**
	 * 删除
	 * @return true:可以 false:不可以
	 */
	public boolean isCantDelete() {
		return _cantDelete;
	}

	/**
	 * 设置删除
	 * @param flag
	 */
	public void setCantDelete(boolean flag) {
		_cantDelete = flag;
	}

	/**
	 * 将道具的数量变化写入资料库
	 */
	private boolean _save_at_once;

	/**
	 * 将道具的数量变化写入资料库。
	 * @return
	 */
	public boolean isToBeSavedAtOnce() {
		return _save_at_once;
	}

	/**
	 * 设置将道具的数量变化写入资料库
	 * @param flag
	 */
	public void setToBeSavedAtOnce(boolean flag) {
		_save_at_once = flag;
	}

	// ■■■■■■ L1EtcItem,L1Weapon 共通项目 ■■■■■■

	/**
	 * 最低伤害
	 */
	private int _dmgSmall = 0;

	/**
	 * 获得最低伤害
	 * @return
	 */
	public int getDmgSmall() {
		return _dmgSmall;
	}

	/**
	 * 设置最低伤害
	 * @param dmgSmall
	 */
	public void setDmgSmall(int dmgSmall) {
		_dmgSmall = dmgSmall;
	}

	/**
	 * 最高伤害
	 */
	private int _dmgLarge = 0;

	/**
	 * 获得最高伤害
	 * @return
	 */
	public int getDmgLarge() {
		return _dmgLarge;
	}

	/**
	 * 设置最高伤害
	 * @param dmgLarge
	 */
	public void setDmgLarge(int dmgLarge) {
		_dmgLarge = dmgLarge;
	}

	// ■■■■■■ L1EtcItem,L1Armor 共通项目 ■■■■■■

	// ■■■■■■ L1Weapon,L1Armor 共通项目 ■■■■■■

	/**
	 * 安定值
	 */
	private int _safeEnchant = 0;

	/**
	 * 安定值
	 * @return
	 */
	public int get_safeenchant() {
		return _safeEnchant;
	}

	/**
	 * 设定安定值
	 * @param safeenchant
	 */
	public void set_safeenchant(int safeenchant) {
		_safeEnchant = safeenchant;
	}

	/**
	 * 王族可用装备
	 */
	private boolean _useRoyal = false;

	/**
	 * 是王族可用装备
	 * @return
	 */
	public boolean isUseRoyal() {
		return _useRoyal;
	}

	/**
	 * 设置王族可用装备
	 * @param flag
	 */
	public void setUseRoyal(boolean flag) {
		_useRoyal = flag;
	}

	/**
	 * 骑士可用装备
	 */
	private boolean _useKnight = false;

	/**
	 * 是骑士可用装备
	 * @return
	 */
	public boolean isUseKnight() {
		return _useKnight;
	}

	/**
	 * 设置骑士可用装备
	 * @param flag
	 */
	public void setUseKnight(boolean flag) {
		_useKnight = flag;
	}

	/**
	 * 精灵可用装备
	 */
	private boolean _useElf = false;

	/**
	 * 是精灵可用装备
	 * @return
	 */
	public boolean isUseElf() {
		return _useElf;
	}

	/**
	 * 设置精灵可用装备
	 * @param flag
	 */
	public void setUseElf(boolean flag) {
		_useElf = flag;
	}

	/**
	 * 法师可用装备
	 */
	private boolean _useMage = false;

	/**
	 * 是法师可用装备
	 * @return
	 */
	public boolean isUseMage() {
		return _useMage;
	}

	/**
	 * 设置法师可用装备
	 * @param flag
	 */
	public void setUseMage(boolean flag) {
		_useMage = flag;
	}

	/**
	 * 黑暗妖精可用装备
	 */
	private boolean _useDarkelf = false;

	/**
	 * 是黑暗妖精可用装备
	 * @return
	 */
	public boolean isUseDarkelf() {
		return _useDarkelf;
	}

	/**
	 * 设置黑暗妖精可用装备
	 * @param flag
	 */
	public void setUseDarkelf(boolean flag) {
		_useDarkelf = flag;
	}

	/**
	 * 龙骑士可用装备
	 */
	private boolean _useDragonknight = false;

	/**
	 * 是龙骑士可用装备
	 * @return
	 */
	public boolean isUseDragonknight() {
		return _useDragonknight;
	}

	/**
	 * 设置龙骑士可用装备
	 * @param flag
	 */
	public void setUseDragonknight(boolean flag) {
		_useDragonknight = flag;
	}

	/**
	 * 幻术师可用装备
	 */
	private boolean _useIllusionist = false;

	/**
	 * 是幻术师可用装备
	 * @return
	 */
	public boolean isUseIllusionist() {
		return _useIllusionist;
	}

	/**
	 * 设置幻术师可用装备
	 * @param flag
	 */
	public void setUseIllusionist(boolean flag) {
		_useIllusionist = flag;
	}

	/**
	 * 增加ＳＴＲ
	 */
	private byte _addstr = 0;

	/**
	 * 获得增加ＳＴＲ
	 * @return
	 */
	public byte get_addstr() {
		return _addstr;
	}

	/**
	 * 设置增加ＳＴＲ
	 * @param addstr
	 */
	public void set_addstr(byte addstr) {
		_addstr = addstr;
	}

	/**
	 * 增加ＤＥＸ
	 */
	private byte _adddex = 0;

	/**
	 * 获得增加ＤＥＸ
	 * @return
	 */
	public byte get_adddex() {
		return _adddex;
	}

	/**
	 * 设置增加ＤＥＸ
	 * @param adddex
	 */
	public void set_adddex(byte adddex) {
		_adddex = adddex;
	}

	/**
	 * 增加ＣＯＮ
	 */
	private byte _addcon = 0;

	/**
	 * 获得增加ＣＯＮ
	 * @return
	 */
	public byte get_addcon() {
		return _addcon;
	}

	/**
	 * 设置增加ＣＯＮ
	 * @param addcon
	 */
	public void set_addcon(byte addcon) {
		_addcon = addcon;
	}

	/**
	 * 增加ＩＮＴ
	 */
	private byte _addint = 0;

	/**
	 * 获得增加ＩＮＴ
	 * @return
	 */
	public byte get_addint() {
		return _addint;
	}

	/**
	 * 设置增加ＩＮＴ
	 * @param addint
	 */
	public void set_addint(byte addint) {
		_addint = addint;
	}

	/**
	 * 增加ＷＩＳ
	 */
	private byte _addwis = 0;

	/**
	 * 获得增加ＷＩＳ
	 * @return
	 */
	public byte get_addwis() {
		return _addwis;
	}

	/**
	 * 设置增加ＷＩＳ
	 * @param addwis
	 */
	public void set_addwis(byte addwis) {
		_addwis = addwis;
	}

	/**
	 * 增加ＣＨＡ
	 */
	private byte _addcha = 0;

	/**
	 * 获得增加ＣＨＡ
	 * @return
	 */
	public byte get_addcha() {
		return _addcha;
	}

	/**
	 * 设置增加ＣＨＡ
	 * @param addcha
	 */
	public void set_addcha(byte addcha) {
		_addcha = addcha;
	}

	/**
	 * 增加ＨＰ
	 */
	private int _addhp = 0;

	/**
	 * 获得增加ＨＰ
	 * @return
	 */
	public int get_addhp() {
		return _addhp;
	}

	/**
	 * 设置增加ＨＰ
	 * @param addhp
	 */
	public void set_addhp(int addhp) {
		_addhp = addhp;
	}

	/**
	 * 增加ＭＰ
	 */
	private int _addmp = 0;

	/**
	 * 获得增加ＭＰ
	 * @return
	 */
	public int get_addmp() {
		return _addmp;
	}

	/**
	 * 设置增加ＭＰ
	 * @param addmp
	 */
	public void set_addmp(int addmp) {
		_addmp = addmp;
	}

	/**
	 * 增加ＨＰＲ
	 */
	private int _addhpr = 0;

	/**
	 * 获得增加ＨＰＲ
	 * @return
	 */
	public int get_addhpr() {
		return _addhpr;
	}

	/**
	 * 设置增加ＨＰＲ
	 * @param addhpr
	 */
	public void set_addhpr(int addhpr) {
		_addhpr = addhpr;
	}

	/**
	 * 增加ＭＰＲ
	 */
	private int _addmpr = 0;

	/**
	 * 获得增加ＭＰＲ
	 * @return
	 */
	public int get_addmpr() {
		return _addmpr;
	}

	/**
	 * 设置增加ＭＰＲ
	 * @param addmpr
	 */
	public void set_addmpr(int addmpr) {
		_addmpr = addmpr;
	}

	/**
	 * 增加ＳＰ
	 */
	private int _addsp = 0;

	/**
	 * 获得增加ＳＰ
	 * @return
	 */
	public int get_addsp() {
		return _addsp;
	}

	/**
	 * 设置增加ＳＰ
	 * @param addsp
	 */
	public void set_addsp(int addsp) {
		_addsp = addsp;
	}

	/**
	 * 抗魔(MR)
	 */
	private int _mdef = 0;

	/**
	 * 取得抗魔(MR)
	 * @return
	 */
	public int get_mdef() {
		return _mdef;
	}

	/**
	 * 设定抗魔(MR)
	 * @param i
	 */
	public void set_mdef(int i) {
		this._mdef = i;
	}

	/**
	 * 是否具有加速效果
	 */
	private boolean _isHasteItem = false;

	/**
	 * 是否具有加速效果
	 * @return
	 */
	public boolean isHasteItem() {
		return _isHasteItem;
	}

	/**
	 * 设定是否具有加速效果
	 * @param flag
	 */
	public void setHasteItem(boolean flag) {
		_isHasteItem = flag;
	}

	/**
	 * 物品可使用时间(能持有的时间)
	 */
	private int _maxUseTime = 0;

	/**
	 * 取得物品可使用时间(能持有的时间)
	 * @return
	 */
	public int getMaxUseTime() {
		return _maxUseTime;
	}

	/**
	 * 设置物品可使用时间(能持有的时间)
	 * @param i
	 */
	public void setMaxUseTime(int i) {
		_maxUseTime = i;
	}

	/**
	 * 物品使用封包类型
	 */
	private int _useType;

	/**
	 * 取得物品使用封包类型
	 * @return
	 */
	public int getUseType() {
		return _useType;
	}

	/**
	 * 设定物品使用封包类型
	 * @param useType
	 */
	public void setUseType(int useType) {
		_useType = useType;
	}

	/**
	 * 食品类道具饱食度
	 */
	private int _foodVolume;

	/**
	 * 取得食品类道具饱食度
	 * @return
	 */
	public int getFoodVolume() {
		return _foodVolume;
	}

	/**
	 * 设定食品类道具饱食度
	 * @param volume
	 */
	public void setFoodVolume(int volume) {
		_foodVolume = volume;
	}

	/**
	 * 取得照明类道具的亮度设置。
	 * @return
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
	 * @return
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

	/**
	 * 物品可堆叠
	 * @return true:可 false:不可
	 */
	public boolean isStackable() {
		return false;
	}

	/**
	 * 取得X坐标
	 * @return
	 */
	public int get_locx() {
		return 0;
	}

	/**
	 * 取得Y坐标
	 * @return
	 */
	public int get_locy() {
		return 0;
	}

	/**
	 * 取得地图ID
	 * @return
	 */
	public short get_mapid() {
		return 0;
	}

	/**
	 * 取得延迟编号
	 * @return
	 */
	public int get_delayid() {
		return 0;
	}

	/**
	 * 取得延迟时间
	 * @return
	 */
	public int get_delaytime() {
		return 0;
	}

	/**
	 * 取得最大可用次数
	 * @return
	 */
	public int getMaxChargeCount() {
		return 0;
	}

	/**
	 * 是可以封印
	 * @return
	 */
	public boolean isCanSeal() {
		return false;
	}

	// ■■■■■■ 由L1Weapon 覆盖的项目 ■■■■■■

	/**
	 * 取得武器的射程范围
	 * @return
	 */
	public int getRange() {
		return 0;
	}

	/**
	 * 取得命中率修正
	 * @return
	 */
	public int getHitModifier() {
		return 0;
	}

	/**
	 * 取得伤害修正
	 * @return
	 */
	public int getDmgModifier() {
		return 0;
	}

	/**
	 * 取得双倍伤害发动几率
	 * @return
	 */
	public int getDoubleDmgChance() {
		return 0;
	}

	/**
	 * 取得魔法攻击的伤害修正
	 * @return
	 */
	public int getMagicDmgModifier() {
		return 0;
	}

	/**
	 * 取得有无损伤
	 * @return
	 */
	public int get_canbedmg() {
		return 0;
	}

	/**
	 * 是双手武器
	 * @return
	 */
	public boolean isTwohandedWeapon() {
		return false;
	}

	// ■■■■■■ 由L1Armor 覆盖的项目 ■■■■■■

	/**
	 * 取得物理防御
	 * @return
	 */
	public int get_ac() {
		return 0;
	}

	/**
	 * 取得伤害减免
	 * @return
	 */
	public int getDamageReduction() {
		return 0;
	}

	/**
	 * 取得负重减轻
	 * @return
	 */
	public int getWeightReduction() {
		return 0;
	}

	/**
	 * 取得装备的命中修正
	 * @return
	 */
	public int getHitModifierByArmor() {
		return 0;
	}

	/**
	 * 取得装备的伤害修正
	 * @return
	 */
	public int getDmgModifierByArmor() {
		return 0;
	}

	/**
	 * 取得弓类装备的命中修正
	 * @return
	 */
	public int getBowHitModifierByArmor() {
		return 0;
	}

	/**
	 * 取得弓类装备的伤害修正
	 * @return
	 */
	public int getBowDmgModifierByArmor() {
		return 0;
	}

	/**
	 * 增加水属性
	 * @return
	 */
	public int get_defense_water() {
		return 0;
	}

	/**
	 * 增加火属性
	 * @return
	 */
	public int get_defense_fire() {
		return 0;
	}

	/**
	 * 增加地属性
	 * @return
	 */
	public int get_defense_earth() {
		return 0;
	}

	/**
	 * 增加风属性
	 * @return
	 */
	public int get_defense_wind() {
		return 0;
	}

	/**
	 * 昏迷耐性
	 * @return
	 */
	public int get_regist_stun() {
		return 0;
	}

	/**
	 * 石化耐性
	 * @return
	 */
	public int get_regist_stone() {
		return 0;
	}

	/**
	 * 睡眠耐性
	 * @return
	 */
	public int get_regist_sleep() {
		return 0;
	}

	/**
	 * 寒冰耐性
	 * @return
	 */
	public int get_regist_freeze() {
		return 0;
	}

	/**
	 * 支撑耐性
	 * @return
	 */
	public int get_regist_sustain() {
		return 0;
	}

	/**
	 * 暗黑耐性
	 * @return
	 */
	public int get_regist_blind() {
		return 0;
	}

	/**
	 * 饰品强度
	 * @return
	 */
	public int getGrade() {
		return 0;
	}

}
