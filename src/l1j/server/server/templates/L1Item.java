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

	/**
	 * 道具类型2
	 */
	private int _type2;

	/**
	 * 道具的 ClassName
	 */
	private String _className;

	// ■■■■■■ L1EtcItem,L1Weapon,L1Armor 共通项目 ■■■■■■

	/**
	 * 道具ＩＤ
	 */
	private int _itemId;

	/**
	 * 道具名称
	 */
	private String _name;

	/**
	 * 道具名称ID
	 */
	private String _nameId;

	/**
	 * 未鉴定道具的名称ＩＤ
	 */
	private String _unidentifiedNameId;

	/**
	 * 鉴定道具的名称ＩＤ
	 */
	private String _identifiedNameId;

	/**
	 * 道具的详细类型
	 */
	private int _type;

	/**
	 * 道具类型1 (武器)
	 */
	private int _type1;

	/**
	 * 道具的材质
	 */
	private int _material;

	/**
	 * 重量
	 */
	private int _weight;

	/**
	 * 清单内的图形ＩＤ
	 */
	private int _gfxId;

	/**
	 * 道具放到地面上的图形ＩＤ
	 */
	private int _groundGfxId;

	/**
	 * 道具的ItemDesc.tbl信息
	 */
	private int _itemDescId;

	/**
	 * 能使用装备的最低ＬＶ
	 */
	private int _minLevel;

	/**
	 * 能使用装备的最高ＬＶ
	 */
	private int _maxLevel;

	/**
	 * 取得属性
	 */
	private int _bless;

	/**
	 * 可交易／不可
	 */
	private boolean _tradable;

	/**
	 * 删除
	 */
	private boolean _cantDelete;

	/**
	 * 将道具的数量变化写入资料库
	 */
	private boolean _save_at_once;

	/**
	 * 最低伤害
	 */
	private int _dmgSmall = 0;

	/**
	 * 最高伤害
	 */
	private int _dmgLarge = 0;

	/**
	 * 安定值
	 */
	private int _safeEnchant = 0;

	/**
	 * 王族可用装备
	 */
	private boolean _useRoyal = false;

	/**
	 * 骑士可用装备
	 */
	private boolean _useKnight = false;

	/**
	 * 精灵可用装备
	 */
	private boolean _useElf = false;

	/**
	 * 法师可用装备
	 */
	private boolean _useMage = false;

	/**
	 * 黑暗妖精可用装备
	 */
	private boolean _useDarkelf = false;

	/**
	 * 龙骑士可用装备
	 */
	private boolean _useDragonknight = false;

	/**
	 * 幻术师可用装备
	 */
	private boolean _useIllusionist = false;

	/**
	 * 增加ＳＴＲ
	 */
	private byte _addstr = 0;

	/**
	 * 增加ＤＥＸ
	 */
	private byte _adddex = 0;

	/**
	 * 增加ＣＯＮ
	 */
	private byte _addcon = 0;

	/**
	 * 增加ＩＮＴ
	 */
	private byte _addint = 0;

	/**
	 * 增加ＷＩＳ
	 */
	private byte _addwis = 0;

	/**
	 * 增加ＣＨＡ
	 */
	private byte _addcha = 0;

	/**
	 * 增加ＨＰ
	 */
	private int _addhp = 0;

	/**
	 * 增加ＭＰ
	 */
	private int _addmp = 0;

	/**
	 * 增加ＨＰＲ
	 */
	private int _addhpr = 0;

	/**
	 * 增加ＭＰＲ
	 */
	private int _addmpr = 0;

	/**
	 * 增加ＳＰ
	 */
	private int _addsp = 0;

	/**
	 * 抗魔(MR)
	 */
	private int _mdef = 0;

	/**
	 * 是否具有加速效果
	 */
	private boolean _isHasteItem = false;

	/**
	 * 物品可使用时间(能持有的时间)
	 */
	private int _maxUseTime = 0;

	/**
	 * 物品使用封包类型
	 */
	private int _useType;

	/**
	 * 食品类道具饱食度
	 */
	private int _foodVolume;

	public L1Item() {
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		}
		catch (final CloneNotSupportedException e) {
			// throw (new InternalError(e.getMessage()));
			return null;
		}
	}

	/**
	 * 取得物理防御
	 * 
	 * @return
	 */
	public int get_ac() {
		return 0;
	}

	/**
	 * 获得增加ＣＨＡ
	 * 
	 * @return
	 */
	public byte get_addcha() {
		return this._addcha;
	}

	/**
	 * 获得增加ＣＯＮ
	 * 
	 * @return
	 */
	public byte get_addcon() {
		return this._addcon;
	}

	/**
	 * 获得增加ＤＥＸ
	 * 
	 * @return
	 */
	public byte get_adddex() {
		return this._adddex;
	}

	/**
	 * 获得增加ＨＰ
	 * 
	 * @return
	 */
	public int get_addhp() {
		return this._addhp;
	}

	/**
	 * 获得增加ＨＰＲ
	 * 
	 * @return
	 */
	public int get_addhpr() {
		return this._addhpr;
	}

	/**
	 * 获得增加ＩＮＴ
	 * 
	 * @return
	 */
	public byte get_addint() {
		return this._addint;
	}

	/**
	 * 获得增加ＭＰ
	 * 
	 * @return
	 */
	public int get_addmp() {
		return this._addmp;
	}

	/**
	 * 获得增加ＭＰＲ
	 * 
	 * @return
	 */
	public int get_addmpr() {
		return this._addmpr;
	}

	/**
	 * 获得增加ＳＰ
	 * 
	 * @return
	 */
	public int get_addsp() {
		return this._addsp;
	}

	/**
	 * 获得增加ＳＴＲ
	 * 
	 * @return
	 */
	public byte get_addstr() {
		return this._addstr;
	}

	/**
	 * 获得增加ＷＩＳ
	 * 
	 * @return
	 */
	public byte get_addwis() {
		return this._addwis;
	}

	/**
	 * 取得有无损伤
	 * 
	 * @return
	 */
	public int get_canbedmg() {
		return 0;
	}

	/**
	 * 增加地属性
	 * 
	 * @return
	 */
	public int get_defense_earth() {
		return 0;
	}

	// ■■■■■■ L1EtcItem,L1Weapon 共通项目 ■■■■■■

	/**
	 * 增加火属性
	 * 
	 * @return
	 */
	public int get_defense_fire() {
		return 0;
	}

	/**
	 * 增加水属性
	 * 
	 * @return
	 */
	public int get_defense_water() {
		return 0;
	}

	/**
	 * 增加风属性
	 * 
	 * @return
	 */
	public int get_defense_wind() {
		return 0;
	}

	/**
	 * 取得延迟编号
	 * 
	 * @return
	 */
	public int get_delayid() {
		return 0;
	}

	/**
	 * 取得延迟时间
	 * 
	 * @return
	 */
	public int get_delaytime() {
		return 0;
	}

	/**
	 * 取得X坐标
	 * 
	 * @return
	 */
	public int get_locx() {
		return 0;
	}

	// ■■■■■■ L1EtcItem,L1Armor 共通项目 ■■■■■■

	// ■■■■■■ L1Weapon,L1Armor 共通项目 ■■■■■■

	/**
	 * 取得Y坐标
	 * 
	 * @return
	 */
	public int get_locy() {
		return 0;
	}

	/**
	 * 取得地图ID
	 * 
	 * @return
	 */
	public short get_mapid() {
		return 0;
	}

	/**
	 * 取得抗魔(MR)
	 * 
	 * @return
	 */
	public int get_mdef() {
		return this._mdef;
	}

	/**
	 * 暗黑耐性
	 * 
	 * @return
	 */
	public int get_regist_blind() {
		return 0;
	}

	/**
	 * 寒冰耐性
	 * 
	 * @return
	 */
	public int get_regist_freeze() {
		return 0;
	}

	/**
	 * 睡眠耐性
	 * 
	 * @return
	 */
	public int get_regist_sleep() {
		return 0;
	}

	/**
	 * 石化耐性
	 * 
	 * @return
	 */
	public int get_regist_stone() {
		return 0;
	}

	/**
	 * 昏迷耐性
	 * 
	 * @return
	 */
	public int get_regist_stun() {
		return 0;
	}

	/**
	 * 支撑耐性
	 * 
	 * @return
	 */
	public int get_regist_sustain() {
		return 0;
	}

	/**
	 * 安定值
	 * 
	 * @return
	 */
	public int get_safeenchant() {
		return this._safeEnchant;
	}

	/**
	 * 取得属性
	 * 
	 * @return 0:祝福 1:一般 2:诅咒
	 */
	public int getBless() {
		return this._bless;
	}

	/**
	 * 取得弓类装备的伤害修正
	 * 
	 * @return
	 */
	public int getBowDmgModifierByArmor() {
		return 0;
	}

	/**
	 * 取得弓类装备的命中修正
	 * 
	 * @return
	 */
	public int getBowHitModifierByArmor() {
		return 0;
	}

	/**
	 * 取得道具的 ClassName
	 * 
	 * @return
	 */
	public String getClassName() {
		return this._className;
	}

	/**
	 * 取得伤害减免
	 * 
	 * @return
	 */
	public int getDamageReduction() {
		return 0;
	}

	/**
	 * 获得最高伤害
	 * 
	 * @return
	 */
	public int getDmgLarge() {
		return this._dmgLarge;
	}

	/**
	 * 取得伤害修正
	 * 
	 * @return
	 */
	public int getDmgModifier() {
		return 0;
	}

	/**
	 * 取得装备的伤害修正
	 * 
	 * @return
	 */
	public int getDmgModifierByArmor() {
		return 0;
	}

	/**
	 * 获得最低伤害
	 * 
	 * @return
	 */
	public int getDmgSmall() {
		return this._dmgSmall;
	}

	/**
	 * 取得双倍伤害发动几率
	 * 
	 * @return
	 */
	public int getDoubleDmgChance() {
		return 0;
	}

	/**
	 * 取得食品类道具饱食度
	 * 
	 * @return
	 */
	public int getFoodVolume() {
		return this._foodVolume;
	}

	/**
	 * 获得清单内的图形ＩＤ
	 * 
	 * @return
	 */
	public int getGfxId() {
		return this._gfxId;
	}

	/**
	 * 饰品强度
	 * 
	 * @return
	 */
	public int getGrade() {
		return 0;
	}

	/**
	 * 获得道具放到地面上的图形ＩＤ
	 * 
	 * @return
	 */
	public int getGroundGfxId() {
		return this._groundGfxId;
	}

	/**
	 * 取得命中率修正
	 * 
	 * @return
	 */
	public int getHitModifier() {
		return 0;
	}

	/**
	 * 取得装备的命中修正
	 * 
	 * @return
	 */
	public int getHitModifierByArmor() {
		return 0;
	}

	/**
	 * 获得鉴定道具的名称ＩＤ
	 * 
	 * @return
	 */
	public String getIdentifiedNameId() {
		return this._identifiedNameId;
	}

	/**
	 * 返回鉴定时显示的ItemDesc.tbl信息。
	 * 
	 * @return
	 */
	public int getItemDescId() {
		return this._itemDescId;
	}

	/**
	 * 获得道具ＩＤ
	 * 
	 * @return
	 */
	public int getItemId() {
		return this._itemId;
	}

	/**
	 * 取得照明类道具的燃料量。
	 * 
	 * @return
	 */
	public int getLightFuel() {
		if (this._itemId == 40001) { // 灯
			return 6000;
		}
		else if (this._itemId == 40002) { // 灯笼
			return 12000;
		}
		else if (this._itemId == 40003) { // 灯油
			return 12000;
		}
		else if (this._itemId == 40004) { // 魔法灯笼
			return 0;
		}
		else if (this._itemId == 40005) { // 蜡烛
			return 600;
		}
		else {
			return 0;
		}
	}

	/**
	 * 取得照明类道具的亮度设置。
	 * 
	 * @return
	 */
	public int getLightRange() {
		if (this._itemId == 40001) { // 灯
			return 11;
		}
		else if (this._itemId == 40002) { // 灯笼
			return 14;
		}
		else if (this._itemId == 40004) { // 魔法灯笼
			return 14;
		}
		else if (this._itemId == 40005) { // 蜡烛
			return 8;
		}
		else {
			return 0;
		}
	}

	/**
	 * 取得魔法攻击的伤害修正
	 * 
	 * @return
	 */
	public int getMagicDmgModifier() {
		return 0;
	}

	/**
	 * 返回道具的材质<br>
	 * 
	 * @return <p>
	 *         <font color=#ff0000>道具的材质</font><br>
	 *         none: 0 <font color=#00800>无</font><br>
	 *         liquid: 1 <font color=#00800>液体</font><br>
	 *         web: 2 <font color=#00800>蜡</font><br>
	 *         vegetation: 3 <font color=#00800>植物性</font><br>
	 *         animalmatter: 4 <font color=#00800>动物性</font><br>
	 *         paper: 5 <font color=#00800>纸</font><br>
	 *         cloth: 6 <font color=#00800>布</font><br>
	 *         leather: 7 <font color=#00800>皮革</font><br>
	 *         wood: 8 <font color=#00800>木</font><br>
	 *         bone: 9 <font color=#00800>骨</font><br>
	 *         dragonscale: 10 <font color=#00800>龙鳞</font><br>
	 *         iron: 11 <font color=#00800>铁</font><br>
	 *         steel: 12 <font color=#00800>钢铁</font><br>
	 *         copper: 13 <font color=#00800>铜</font><br>
	 *         silver: 14 <font color=#00800>银</font><br>
	 *         gold: 15 <font color=#00800>金</font><br>
	 *         platinum: 16 <font color=#00800>白金</font><br>
	 *         mithril: 17 <font color=#00800>米索莉</font><br>
	 *         blackmithril: 18 <font color=#00800>黑色米索莉</font><br>
	 *         glass: 19 <font color=#00800>玻璃</font><br>
	 *         gemstone: 20 <font color=#00800>宝石</font><br>
	 *         mineral: 21 <font color=#00800>矿物</font><br>
	 *         oriharukon: 22 <font color=#00800>奥里哈鲁根</font><br>
	 *         </p>
	 */
	public int getMaterial() {
		return this._material;
	}

	/**
	 * 取得最大可用次数
	 * 
	 * @return
	 */
	public int getMaxChargeCount() {
		return 0;
	}

	/**
	 * 获得能使用装备的最高ＬＶ
	 * 
	 * @return
	 */
	public int getMaxLevel() {
		return this._maxLevel;
	}

	/**
	 * 取得物品可使用时间(能持有的时间)
	 * 
	 * @return
	 */
	public int getMaxUseTime() {
		return this._maxUseTime;
	}

	/**
	 * 获得能使用装备的最低ＬＶ
	 * 
	 * @return
	 */
	public int getMinLevel() {
		return this._minLevel;
	}

	/**
	 * 获得道具名称
	 * 
	 * @return
	 */
	public String getName() {
		return this._name;
	}

	/**
	 * 获得道具名称ID
	 * 
	 * @return
	 */
	public String getNameId() {
		return this._nameId;
	}

	/**
	 * 取得武器的射程范围
	 * 
	 * @return
	 */
	public int getRange() {
		return 0;
	}

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
		return this._type;
	}

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
		return this._type1;
	}

	/**
	 * 获得道具类型2 <br>
	 * 
	 * @return 0 if L1EtcItem, 1 if L1Weapon, 2 if L1Armor
	 */
	public int getType2() {
		return this._type2;
	}

	/**
	 * 获得未鉴定道具的名称ＩＤ
	 * 
	 * @return
	 */
	public String getUnidentifiedNameId() {
		return this._unidentifiedNameId;
	}

	/**
	 * 物品使用封包类型
	 * 
	 * @return <p>
	 *         green: -4 <font color=#00800>加速类道具 (绿色药水)</font><br>
	 *         mpr: -3 <font color=#00800>回魔类道具 (蓝色药水)</font><br>
	 *         hpr: -2 <font color=#00800>加血类道具 (治愈药水)</font><br>
	 *         none: -1 <font color=#00800>无法使用 (材料等)</font><br>
	 *         normal: 0 <font color=#00800>一般物品</font><br>
	 *         weapon: 1 <font color=#00800>武器</font><br>
	 *         armor: 2 <font color=#00800>盔甲</font><br>
	 *         spell_long: 5 <font color=#00800>魔杖类型 (须选取目标/坐标)</font><br>
	 *         ntele: 6 <font color=#00800>瞬间移动卷轴</font><br>
	 *         identify: 7 <font color=#00800>鉴定卷轴</font><br>
	 *         res: 8 <font color=#00800>复活卷轴</font><br>
	 *         letter: 12 <font color=#00800>信纸</font><br>
	 *         letter_card: 13 <font color=#00800>信纸(寄出)</font><br>
	 *         choice: 14 <font color=#00800>请选择一个物品 (道具栏位)</font><br>
	 *         instrument: 15 <font color=#00800>哨子</font><br>
	 *         sosc: 16 <font color=#00800>变形卷轴</font><br>
	 *         spell_short: 17 <font color=#00800>选取目标 (近距离)</font><br>
	 *         T: 18 <font color=#00800>T恤</font><br>
	 *         cloak: 19 <font color=#00800>斗篷</font><br>
	 *         glove: 20 <font color=#00800>手套</font><br>
	 *         boots: 21 <font color=#00800>长靴</font><br>
	 *         helm: 22 <font color=#00800>头盔</font><br>
	 *         ring: 23 <font color=#00800>戒指</font><br>
	 *         amulet: 24 <font color=#00800>项链</font><br>
	 *         shield: 25 <font color=#00800>盾牌</font><br>
	 *         guarder: 25 <font color=#00800>臂甲</font><br>
	 *         dai: 26 <font color=#00800>对武器施法的卷轴</font><br>
	 *         zel: 27 <font color=#00800>对盔甲施法的卷轴</font><br>
	 *         blank: 28 <font color=#00800>空的魔法卷轴</font><br>
	 *         btele: 29 <font color=#00800>瞬间移动卷轴 (祝福)</font><br>
	 *         spell_buff: 30 <font color=#00800>选取目标 (对NPC需要Ctrl 远距离 无XY座标传回)</font><br>
	 *         ccard: 31 <font color=#00800>圣诞卡片</font><br>
	 *         ccard_w: 32 <font color=#00800>圣诞卡片 (寄出)</font><br>
	 *         vcard: 33 <font color=#00800>情人节卡片</font><br>
	 *         vcard_w: 34 <font color=#00800>情人节卡片 (寄出)</font><br>
	 *         wcard: 35 <font color=#00800>白色情人节卡片</font><br>
	 *         wcard_w: 36 <font color=#00800>白色情人节卡片 (寄出)</font><br>
	 *         belt: 37 <font color=#00800>腰带</font><br>
	 *         earring: 40 <font color=#00800>耳环</font><br>
	 *         fishing_rod: 42 <font color=#00800>钓鱼杆</font><br>
	 *         tattoo_r: 43 <font color=#ff0000>辅助装备 (右)</font><br>
	 *         tattoo_l: 44 <font color=#ff0000>辅助装备 (左)</font><br>
	 *         tattoo_m: 45 <font color=#ff0000>辅助装备 (中)</font><br>
	 *         del: 46 <font color=#ff0000>饰品强化卷轴</font><br>
	 *         </p>
	 * @return
	 */
	public int getUseType() {
		return this._useType;
	}

	/**
	 * 获得重量
	 * 
	 * @return
	 */
	public int getWeight() {
		return this._weight;
	}

	/**
	 * 取得负重减轻
	 * 
	 * @return
	 */
	public int getWeightReduction() {
		return 0;
	}

	/**
	 * 是可以封印
	 * 
	 * @return
	 */
	public boolean isCanSeal() {
		return false;
	}

	/**
	 * 删除
	 * 
	 * @return true:可以 false:不可以
	 */
	public boolean isCantDelete() {
		return this._cantDelete;
	}

	/**
	 * 是否具有加速效果
	 * 
	 * @return
	 */
	public boolean isHasteItem() {
		return this._isHasteItem;
	}

	/**
	 * 物品可堆叠
	 * 
	 * @return true:可 false:不可
	 */
	public boolean isStackable() {
		return false;
	}

	/**
	 * 将道具的数量变化写入资料库。
	 * 
	 * @return
	 */
	public boolean isToBeSavedAtOnce() {
		return this._save_at_once;
	}

	/**
	 * 交易
	 * 
	 * @return true:可以 false:不可以
	 */
	public boolean isTradable() {
		return this._tradable;
	}

	/**
	 * 是双手武器
	 * 
	 * @return
	 */
	public boolean isTwohandedWeapon() {
		return false;
	}

	/**
	 * 是黑暗妖精可用装备
	 * 
	 * @return
	 */
	public boolean isUseDarkelf() {
		return this._useDarkelf;
	}

	/**
	 * 是龙骑士可用装备
	 * 
	 * @return
	 */
	public boolean isUseDragonknight() {
		return this._useDragonknight;
	}

	/**
	 * 是精灵可用装备
	 * 
	 * @return
	 */
	public boolean isUseElf() {
		return this._useElf;
	}

	/**
	 * 是幻术师可用装备
	 * 
	 * @return
	 */
	public boolean isUseIllusionist() {
		return this._useIllusionist;
	}

	/**
	 * 是骑士可用装备
	 * 
	 * @return
	 */
	public boolean isUseKnight() {
		return this._useKnight;
	}

	/**
	 * 是法师可用装备
	 * 
	 * @return
	 */
	public boolean isUseMage() {
		return this._useMage;
	}

	/**
	 * 是王族可用装备
	 * 
	 * @return
	 */
	public boolean isUseRoyal() {
		return this._useRoyal;
	}

	/**
	 * 设置增加ＣＨＡ
	 * 
	 * @param addcha
	 */
	public void set_addcha(final byte addcha) {
		this._addcha = addcha;
	}

	/**
	 * 设置增加ＣＯＮ
	 * 
	 * @param addcon
	 */
	public void set_addcon(final byte addcon) {
		this._addcon = addcon;
	}

	/**
	 * 设置增加ＤＥＸ
	 * 
	 * @param adddex
	 */
	public void set_adddex(final byte adddex) {
		this._adddex = adddex;
	}

	/**
	 * 设置增加ＨＰ
	 * 
	 * @param addhp
	 */
	public void set_addhp(final int addhp) {
		this._addhp = addhp;
	}

	/**
	 * 设置增加ＨＰＲ
	 * 
	 * @param addhpr
	 */
	public void set_addhpr(final int addhpr) {
		this._addhpr = addhpr;
	}

	/**
	 * 设置增加ＩＮＴ
	 * 
	 * @param addint
	 */
	public void set_addint(final byte addint) {
		this._addint = addint;
	}

	/**
	 * 设置增加ＭＰ
	 * 
	 * @param addmp
	 */
	public void set_addmp(final int addmp) {
		this._addmp = addmp;
	}

	/**
	 * 设置增加ＭＰＲ
	 * 
	 * @param addmpr
	 */
	public void set_addmpr(final int addmpr) {
		this._addmpr = addmpr;
	}

	/**
	 * 设置增加ＳＰ
	 * 
	 * @param addsp
	 */
	public void set_addsp(final int addsp) {
		this._addsp = addsp;
	}

	/**
	 * 设置增加ＳＴＲ
	 * 
	 * @param addstr
	 */
	public void set_addstr(final byte addstr) {
		this._addstr = addstr;
	}

	/**
	 * 设置增加ＷＩＳ
	 * 
	 * @param addwis
	 */
	public void set_addwis(final byte addwis) {
		this._addwis = addwis;
	}

	/**
	 * 设定抗魔(MR)
	 * 
	 * @param i
	 */
	public void set_mdef(final int i) {
		this._mdef = i;
	}

	/**
	 * 设定安定值
	 * 
	 * @param safeenchant
	 */
	public void set_safeenchant(final int safeenchant) {
		this._safeEnchant = safeenchant;
	}

	// ■■■■■■ 由L1EtcItem 覆盖的道具 ■■■■■■

	/**
	 * 设置取得属性
	 * 
	 * @param i
	 */
	public void setBless(final int i) {
		this._bless = i;
	}

	/**
	 * 设置删除
	 * 
	 * @param flag
	 */
	public void setCantDelete(final boolean flag) {
		this._cantDelete = flag;
	}

	/**
	 * 设置道具的 ClassName
	 * 
	 * @param classname
	 */
	public void setClassName(final String className) {
		this._className = className;
	}

	/**
	 * 设置最高伤害
	 * 
	 * @param dmgLarge
	 */
	public void setDmgLarge(final int dmgLarge) {
		this._dmgLarge = dmgLarge;
	}

	/**
	 * 设置最低伤害
	 * 
	 * @param dmgSmall
	 */
	public void setDmgSmall(final int dmgSmall) {
		this._dmgSmall = dmgSmall;
	}

	/**
	 * 设定食品类道具饱食度
	 * 
	 * @param volume
	 */
	public void setFoodVolume(final int volume) {
		this._foodVolume = volume;
	}

	/**
	 * 设置清单内的图形ＩＤ
	 * 
	 * @param gfxId
	 */
	public void setGfxId(final int gfxId) {
		this._gfxId = gfxId;
	}

	/**
	 * 设置道具放到地面上的图形ＩＤ
	 * 
	 * @param groundGfxId
	 */
	public void setGroundGfxId(final int groundGfxId) {
		this._groundGfxId = groundGfxId;
	}

	// ■■■■■■ 由L1Weapon 覆盖的项目 ■■■■■■

	/**
	 * 设定是否具有加速效果
	 * 
	 * @param flag
	 */
	public void setHasteItem(final boolean flag) {
		this._isHasteItem = flag;
	}

	/**
	 * 设置鉴定道具的名称ＩＤ
	 * 
	 * @param identifiedNameId
	 */
	public void setIdentifiedNameId(final String identifiedNameId) {
		this._identifiedNameId = identifiedNameId;
	}

	/**
	 * 设置鉴定时显示的ItemDesc.tbl信息
	 * 
	 * @param descId
	 */
	public void setItemDescId(final int descId) {
		this._itemDescId = descId;
	}

	/**
	 * 设置道具ＩＤ
	 * 
	 * @param itemId
	 */
	public void setItemId(final int itemId) {
		this._itemId = itemId;
	}

	/**
	 * 设置道具的材质
	 * 
	 * @param material
	 */
	public void setMaterial(final int material) {
		this._material = material;
	}

	/**
	 * 设置能使用装备的最高ＬＶ
	 * 
	 * @param maxlvl
	 */
	public void setMaxLevel(final int maxlvl) {
		this._maxLevel = maxlvl;
	}

	/**
	 * 设置物品可使用时间(能持有的时间)
	 * 
	 * @param i
	 */
	public void setMaxUseTime(final int i) {
		this._maxUseTime = i;
	}

	// ■■■■■■ 由L1Armor 覆盖的项目 ■■■■■■

	/**
	 * 设置能使用装备的最低ＬＶ
	 * 
	 * @param level
	 */
	public void setMinLevel(final int level) {
		this._minLevel = level;
	}

	/**
	 * 设置道具名称
	 * 
	 * @param name
	 */
	public void setName(final String name) {
		this._name = name;
	}

	/**
	 * 设置道具名称ID
	 * 
	 * @param nameid
	 */
	public void setNameId(final String nameid) {
		this._nameId = nameid;
	}

	/**
	 * 设置将道具的数量变化写入资料库
	 * 
	 * @param flag
	 */
	public void setToBeSavedAtOnce(final boolean flag) {
		this._save_at_once = flag;
	}

	/**
	 * 设置交易
	 * 
	 * @param flag
	 */
	public void setTradable(final boolean flag) {
		this._tradable = flag;
	}

	/**
	 * 设置道具种类
	 * 
	 * @param type
	 */
	public void setType(final int type) {
		this._type = type;
	}

	/**
	 * 设置道具类型1 (武器)
	 * 
	 * @param type1
	 */
	public void setType1(final int type1) {
		this._type1 = type1;
	}

	/**
	 * 设置道具类型2 <br>
	 * 
	 * @param type
	 *            0 if L1EtcItem, 1 if L1Weapon, 2 if L1Armor
	 */
	public void setType2(final int type) {
		this._type2 = type;
	}

	/**
	 * 设置未鉴定道具的名称ＩＤ
	 * 
	 * @param unidentifiedNameId
	 */
	public void setUnidentifiedNameId(final String unidentifiedNameId) {
		this._unidentifiedNameId = unidentifiedNameId;
	}

	/**
	 * 设置黑暗妖精可用装备
	 * 
	 * @param flag
	 */
	public void setUseDarkelf(final boolean flag) {
		this._useDarkelf = flag;
	}

	/**
	 * 设置龙骑士可用装备
	 * 
	 * @param flag
	 */
	public void setUseDragonknight(final boolean flag) {
		this._useDragonknight = flag;
	}

	/**
	 * 设置精灵可用装备
	 * 
	 * @param flag
	 */
	public void setUseElf(final boolean flag) {
		this._useElf = flag;
	}

	/**
	 * 设置幻术师可用装备
	 * 
	 * @param flag
	 */
	public void setUseIllusionist(final boolean flag) {
		this._useIllusionist = flag;
	}

	/**
	 * 设置骑士可用装备
	 * 
	 * @param flag
	 */
	public void setUseKnight(final boolean flag) {
		this._useKnight = flag;
	}

	/**
	 * 设置法师可用装备
	 * 
	 * @param flag
	 */
	public void setUseMage(final boolean flag) {
		this._useMage = flag;
	}

	/**
	 * 设置王族可用装备
	 * 
	 * @param flag
	 */
	public void setUseRoyal(final boolean flag) {
		this._useRoyal = flag;
	}

	/**
	 * 设定物品使用封包类型
	 * 
	 * @param useType
	 */
	public void setUseType(final int useType) {
		this._useType = useType;
	}

	/**
	 * 设置重量
	 * 
	 * @param weight
	 */
	public void setWeight(final int weight) {
		this._weight = weight;
	}

}
