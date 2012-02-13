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
package l1j.server.server;

/**
 * 封包代码
 */
public class Opcodes {

	// 3.5C Client Packet （客户端代码）
	/** 请求驱逐队伍 */
	public static final int C_OPCODE_BANPARTY = 0;

	/**
	 * @3.5C Taiwan Server <b>2011.08.09 Lin.bin
	 */

	/** 请求下船 */
	public static final int C_OPCODE_SHIP = 1;
	/** 玩家传送锁定(回溯检测用) */
	public static final int C_OPCODE_TELEPORTLOCK = 2;
	/** 请求学习魔法 */
	public static final int C_OPCODE_SKILLBUYOK = 4;
	/** 请求新增好友 */
	public static final int C_OPCODE_ADDBUDDY = 5;
	/** 请求变更仓库密码 && 送出仓库密码 */
	public static final int C_OPCODE_WAREHOUSELOCK = 8;
	/** 请求丢弃物品 */
	public static final int C_OPCODE_DROPITEM = 9;
	/** 请求查看下一页布告栏的讯息 */
	public static final int C_OPCODE_BOARDNEXT = 11;
	/** 请求宠物回报选单 */
	public static final int C_OPCODE_PETMENU = 12;
	/** 请求加入血盟 */
	public static final int C_OPCODE_JOINCLAN = 13;
	/** 请求给予物品 */
	public static final int C_OPCODE_GIVEITEM = 14;
	/** 请求使用技能 */
	public static final int C_OPCODE_USESKILL = 16;
	/** 请求取得列表中的项目 */
	public static final int C_OPCODE_RESULT = 17;
	/** 请求删除角色 */
	public static final int C_OPCODE_DELETECHAR = 19;
	/** 请求浏览公布栏 */
	public static final int C_OPCODE_BOARD = 21;
	/** 请求取消交易 */
	public static final int C_OPCODE_TRADEADDCANCEL = 23;
	/** 请求使用物品 */
	public static final int C_OPCODE_USEITEM = 24;
	/** 请求结婚 */
	public static final int C_OPCODE_PROPOSE = 25;
	/** 请求删除公布栏内容 */
	public static final int C_OPCODE_BOARDDELETE = 26;
	/** 请求改变角色面向 */
	public static final int C_OPCODE_CHANGEHEADING = 27;
	/** 请求删除记忆座标 */
	public static final int C_OPCODE_BOOKMARKDELETE = 28;
	/** 请求修理道具 */
	public static final int C_OPCODE_SELECTLIST = 29;
	/** 请求攻击指定物件(宠物&召唤) */
	public static final int C_OPCODE_SELECTTARGET = 32;
	/** 请求使用开启名单(拒绝指定人物讯息) */
	public static final int C_OPCODE_DELEXCLUDE = 33;
	/** 请求查询好友名单 */
	public static final int C_OPCODE_BUDDYLIST = 34;
	/** 请求传送位置 */
	public static final int C_OPCODE_SENDLOCATION = 35;
	/** 请求赋予封号 */
	public static final int C_OPCODE_TITLE = 37;
	/** 请求完成交易 */
	public static final int C_OPCODE_TRADEADDOK = 38;
	/** 请求上传盟徽 */
	public static final int C_OPCODE_EMBLEM = 39;
	/** 请求移动角色 */
	public static final int C_OPCODE_MOVECHAR = 40;
	/** 请求查询PK次数 */
	public static final int C_OPCODE_CHECKPK = 41;
	/** 请求下一步 (伺服器公告) */
	public static final int C_OPCODE_COMMONCLICK = 42;
	/** 请求离开游戏 */
	public static final int C_OPCODE_QUITGAME = 43;
	/** 请求将资金存入城堡宝库 */
	public static final int C_OPCODE_DEPOSIT = 44;
	/** 请求使用乐豆自动登录伺服器 (未实装) */
	public static final int C_OPCODE_BEANFUN_LOGIN = 45;
	/** 请求增加记忆座标 */
	public static final int C_OPCODE_BOOKMARK = 46;
	/** 请求开设个人商店 */
	public static final int C_OPCODE_SHOP = 47;
	/** 请求使用密语聊天频道 */
	public static final int C_OPCODE_CHATWHISPER = 48;
	/** 请求购买指定的个人商店商品 */
	public static final int C_OPCODE_PRIVATESHOPLIST = 49;
	/** 请求角色表情动作 */
	public static final int C_OPCODE_EXTCOMMAND = 52;
	/** 请求验证客户端版本 */
	public static final int C_OPCODE_CLIENTVERSION = 54;
	/** 请求登录角色 */
	public static final int C_OPCODE_LOGINTOSERVER = 55;
	/** 请求点选项目的结果 */
	public static final int C_OPCODE_ATTR = 56;
	/** 请求对话视窗 */
	public static final int C_OPCODE_NPCTALK = 57;
	/** 请求创造角色 */
	public static final int C_OPCODE_NEWCHAR = 58;
	/** 请求交易 */
	public static final int C_OPCODE_TRADE = 59;
	/** 请求删除好友 */
	public static final int C_OPCODE_DELBUDDY = 61;
	/** 请求驱逐血盟成员 */
	public static final int C_OPCODE_BANCLAN = 62;
	/** 请求钓鱼收竿 */
	public static final int C_OPCODE_FISHCLICK = 63;
	/** 请求离开血盟 */
	public static final int C_OPCODE_LEAVECLANE = 65;
	/** 请求配置税收 */
	public static final int C_OPCODE_TAXRATE = 66;
	/** 请求重新开始 */
	public static final int C_OPCODE_RESTART = 70;
	/** 请求传送 (进入地监) */
	public static final int C_OPCODE_ENTERPORTAL = 71;
	/** 请求查询可以学习的魔法清单 */
	public static final int C_OPCODE_SKILLBUY = 72;
	/** 请求解除传送锁定 */
	public static final int C_OPCODE_TELEPORT = 73;
	/** 请求删除物品 */
	public static final int C_OPCODE_DELETEINVENTORYITEM = 74;
	/** 请求使用一般聊天频道 */
	public static final int C_OPCODE_CHAT = 75;
	/** 请求使用远距攻击 */
	public static final int C_OPCODE_ARROWATTACK = 77;
	/** 请求使用宠物装备 */
	public static final int C_OPCODE_USEPETITEM = 78;
	/** 请求使用拒绝名单(开启指定人物讯息) */
	public static final int C_OPCODE_EXCLUDE = 79;
	/** 请求查询损坏的道具 */
	public static final int C_OPCODE_FIX_WEAPON_LIST = 80;
	/** 请求查询血盟成员 */
	public static final int C_OPCODE_PLEDGE = 84;
	/** 请求执行对话视窗的动作 */
	public static final int C_OPCODE_NPCACTION = 87;
	/** 请求退出观看模式 */
	public static final int C_OPCODE_EXIT_GHOST = 90;
	/** 请求传送至指定的外挂使用者身旁 */
	public static final int C_OPCODE_CALL = 91;
	/** 请求打开邮箱 */
	public static final int C_OPCODE_MAIL = 92;
	/** 请求查询游戏人数 */
	public static final int C_OPCODE_WHO = 93;
	/** 请求拾取物品 */
	public static final int C_OPCODE_PICKUPITEM = 94;
	/** 要求重置人物点数 */
	public static final int C_OPCODE_CHARRESET = 95;
	/** 请求传回选取的数量 */
	public static final int C_OPCODE_AMOUNT = 96;
	/** 请求给予角色血盟阶级 */
	public static final int C_OPCODE_RANK = 103;
	/** 请求决斗 */
	public static final int C_OPCODE_FIGHT = 104;
	/** 请求领取城堡宝库资金 */
	public static final int C_OPCODE_DRAWAL = 105;
	/** 请求更新连线状态 */
	public static final int C_OPCODE_KEEPALIVE = 106;
	/** 请求纪录快速键 */
	public static final int C_OPCODE_CHARACTERCONFIG = 108;
	/** 请求使用广播聊天频道 */
	public static final int C_OPCODE_CHATGLOBAL = 109;
	/** 请求宣战 */
	public static final int C_OPCODE_WAR = 110;
	/** 请求创立血盟 */
	public static final int C_OPCODE_CREATECLAN = 112;
	/** 请求配置角色设定 */
	public static final int C_OPCODE_LOGINTOSERVEROK = 114;
	/** 请求登录伺服器 */
	public static final int C_OPCODE_LOGINPACKET = 115;
	/** 请求开门或关门 */
	public static final int C_OPCODE_DOOR = 116;
	/** 请求攻击对象 */
	public static final int C_OPCODE_ATTACK = 117;
	/** 请求交易(添加物品) */
	public static final int C_OPCODE_TRADEADDITEM = 119;
	/** 请求传送简讯 */
	public static final int C_OPCODE_SMS = 121;
	/** 请求退出队伍 */
	public static final int C_OPCODE_LEAVEPARTY = 123;
	/** 请求管理城内治安 */
	public static final int C_OPCODE_CASTLESECURITY = 124;
	/** 请求阅读布告单个栏讯息 */
	public static final int C_OPCODE_BOARDREAD = 125;
	/** 请求切换角色 */
	public static final int C_OPCODE_CHANGECHAR = 126;
	/** 请求查询队伍成员 */
	public static final int C_OPCODE_PARTYLIST = 127;
	/** 请求撰写新的布告栏讯息 */
	public static final int C_OPCODE_BOARDWRITE = 129;
	/** 请求邀请加入队伍或建立队伍 */
	public static final int C_OPCODE_CREATEPARTY = 130;
	/** 请求聊天队伍 */
	public static final int C_OPCODE_CAHTPARTY = 131;
	// 3.5C Server Packet （服务端代码）
	/** 配置已的雇用佣兵 */
	public static final int S_OPCODE_PUTSOLDIER = 0;

	/** 学习魔法 (何仑) */
	public static final int S_OPCODE_SKILLBUY_2 = 1;
	/** 商店收购清单 */
	public static final int S_OPCODE_SHOWSHOPSELLLIST = 2;
	/** Ping Time */
	public static final int S_OPCODE_PINGTIME = 3;
	/** 角色移除 (立即或非立即) */
	public static final int S_OPCODE_DETELECHAROK = 4;
	/** 物件面向 */
	public static final int S_OPCODE_CHANGEHEADING = 5;
	/** 魔法效果 : 防御颣 */
	public static final int S_OPCODE_SKILLICONSHIELD = 6;
	/** 范围魔法 */
	public static final int S_OPCODE_RANGESKILLS = 7;
	/** 输入数量要产生的数量 */
	public static final int S_OPCODE_INPUTAMOUNT = 8;
	/** 移除指定的魔法 */
	public static final int S_OPCODE_DELSKILL = 9;
	/** 配置佣兵 */
	public static final int S_OPCODE_PUTHIRESOLDIER = 10;
	/** 魔法或物品产生的加速效果 */
	public static final int S_OPCODE_SKILLHASTE = 11;
	/** 角色列表 */
	public static final int S_OPCODE_CHARAMOUNT = 12;
	/** 插入记忆座标 */
	public static final int S_OPCODE_BOOKMARKS = 13;
	/** 例外事件_3 */
	public static final int S_OPCODE_EXCEPTION_3 = 14;
	/** 魔力与最大魔力更新 */
	public static final int S_OPCODE_MPUPDATE = 15;
	/** 例外事件_2 */
	public static final int S_OPCODE_EXCEPTION_2 = 16;
	/** 伺服器版本 */
	public static final int S_OPCODE_SERVERVERSION = 17;
	/** 切换物件外观动作 */
	public static final int S_OPCODE_CHARVISUALUPDATE = 18;
	/** 魔法效果 : 麻痹类 */
	public static final int S_OPCODE_PARALYSIS = 19;
	/** 移动锁定封包(疑似开加速器则会用这个封包将玩家锁定) */
	public static final int S_OPCODE_MOVELOCK = 20;
	/** 删除物品 */
	public static final int S_OPCODE_DELETEINVENTORYITEM = 21;
	/** 不明封包 (会变更头衔) */
	public static final int S_OPCODE_NEW1 = 22;
	// 23 仿佛是伺服器选单
	/** 雇用佣兵 */
	public static final int S_OPCODE_HIRESOLDIER = 24;
	/** 角色名称变紫色 */
	public static final int S_OPCODE_PINKNAME = 25;
	/** 传送术或瞬间移动卷轴-传送锁定 */
	public static final int S_OPCODE_TELEPORT = 26;
	/** 初始化演算法 */
	public static final int S_OPCODE_INITPACKET = 27;
	/** 改变物件名称 */
	public static final int S_OPCODE_CHANGENAME = 28;
	/** 角色创造例外 */
	public static final int S_OPCODE_NEWCHARWRONG = 29;
	/** 领取城堡宝库资金 */
	public static final int S_OPCODE_DRAWAL = 30;
	/** 更新现在的地图 */
	public static final int S_OPCODE_MAPID = 32;
	/** 更新现在的地图 （水中） */
	public static final int S_OPCODE_UNDERWATER = 32;
	/** 增加交易物品封包 */
	public static final int S_OPCODE_TRADEADDITEM = 33;
	/** 角色属性与能力值 */
	public static final int S_OPCODE_OWNCHARSTATUS = 34;
	/** 例外事件_1 */
	public static final int S_OPCODE_EXCEPTION_1 = 35;
	/** 公告视窗 */
	public static final int S_OPCODE_COMMONNEWS = 36;
	/** 法术效果-精准目标 */
	public static final int S_OPCODE_TRUETARGET = 37;
	/** 体力与最大体力更新 */
	public static final int S_OPCODE_HPUPDATE = 38;
	/** 交易是否成功 */
	public static final int S_OPCODE_TRADESTATUS = 39;
	/** 商店贩售清单 */
	public static final int S_OPCODE_SHOWSHOPBUYLIST = 40;
	/** 进入游戏 */
	public static final int S_OPCODE_LOGINTOGAME = 41;
	/** 物件隐形或现形 */
	public static final int S_OPCODE_INVIS = 42;
	/** 角色重置 */
	public static final int S_OPCODE_CHARRESET = 43;
	/** 宠物控制介面移除 */
	public static final int S_OPCODE_PETCTRL = 43;
	/** 设定围成时间 */
	public static final int S_OPCODE_WARTIME = 44;
	/** 物品资讯讯息 */
	public static final int S_OPCODE_IDENTIFYDESC = 45;
	/** 红色讯息 */
	public static final int S_OPCODE_BLUEMESSAGE = 46;
	/** 魔法效果:中毒 */
	public static final int S_OPCODE_POISON = 47;
	/** 更新目前游戏时间 */
	public static final int S_OPCODE_GAMETIME = 48;
	/** 魔法购买 (金币) */
	public static final int S_OPCODE_SKILLBUY = 50;
	/** 交易封包 */
	public static final int S_OPCODE_TRADE = 51;
	/** 血盟战争 */
	public static final int S_OPCODE_WAR = 52;
	/** 一般聊天或大喊聊天频道 */
	public static final int S_OPCODE_NPCSHOUT = 53;
	/** 系统讯息视窗 */
	public static final int S_OPCODE_COMMONNEWS2 = 54;
	/** 物件封包 */
	public static final int S_OPCODE_CHARPACK = 55;
	/** 物件封包 (道具) */
	public static final int S_OPCODE_DROPITEM = 55;
	/** 一般聊天或大喊聊天频道 */
	public static final int S_OPCODE_NORMALCHAT = 56;
	/** 邮件封包 */
	public static final int S_OPCODE_MAIL = 57;
	/** 力量提升封包 */
	public static final int S_OPCODE_STRUP = 58;
	/** 法术效果-暗盲咒术 */
	public static final int S_OPCODE_CURSEBLIND = 59;
	/** 物品属性状态 */
	public static final int S_OPCODE_ITEMCOLOR = 60;
	/** 魔杖的使用次数 */
	public static final int S_OPCODE_USECOUNT = 61;
	/** 移动物件 */
	public static final int S_OPCODE_MOVEOBJECT = 62;
	/** 布告栏 (对话视窗) */
	public static final int S_OPCODE_BOARD = 63;
	/** 物品增加封包 */
	public static final int S_OPCODE_ADDITEM = 64;
	/** 仓库物品名单 */
	public static final int S_OPCODE_SHOWRETRIEVELIST = 65;
	/** 强制重新选择角色 */
	public static final int S_OPCODE_RESTART = 66;
	/** 确认视窗 */
	public static final int S_OPCODE_YES_NO = 68;
	/** 插入批次道具 */
	public static final int S_OPCODE_INVLIST = 69;
	/** 角色能力值 */
	public static final int S_OPCODE_OWNCHARSTATUS2 = 70;
	/** 不明封包 (商店) */
	public static final int S_OPCODE_NEW3 = 71;
	/** 物件血条 */
	public static final int S_OPCODE_HPMETER = 72;
	/** 修理武器清单 */
	public static final int S_OPCODE_FIX_WEAPON_MENU = 73;
	/** 损坏武器名单 */
	public static final int S_OPCODE_SELECTLIST = 73;
	/** 进入传送点-传送锁定 */
	public static final int S_OPCODE_TELEPORTLOCK = 74;
	/** 个人商店贩卖或购买 */
	public static final int S_OPCODE_PRIVATESHOPLIST = 75;
	/** 广播聊天频道 */
	public static final int S_OPCODE_GLOBALCHAT = 76;
	/** 伺服器讯息 */
	public static final int S_OPCODE_SYSMSG = 76;
	/** 增加魔法进魔法名单 */
	public static final int S_OPCODE_ADDSKILL = 77;
	/** 魔法或物品效果图示-勇气药水类 */
	public static final int S_OPCODE_SKILLBRAVE = 78;
	/** 游戏天气 */
	public static final int S_OPCODE_WEATHER = 79;
	/** 角色资讯 */
	public static final int S_OPCODE_CHARLIST = 80;
	/** 角色属性值 */
	public static final int S_OPCODE_OWNCHARATTRDEF = 81;
	/** 产生动画 [座标] */
	public static final int S_OPCODE_EFFECTLOCATION = 82;
	/** 魔法攻击力与魔法防御力 */
	public static final int S_OPCODE_SPMR = 83;
	/** 选择一个目标 */
	public static final int S_OPCODE_SELECTTARGET = 84;
	/** 布告栏(讯息阅读) */
	public static final int S_OPCODE_BOARDREAD = 85;
	/** 产生动画 [自身] */
	public static final int S_OPCODE_SKILLSOUNDGFX = 86;
	/** 立即中断连线 */
	public static final int S_OPCODE_DISCONNECT = 88;
	/** 特殊攻击 */
	public static final int S_OPCODE_SPECIALATTACK = 89;
	/** 特别变身封包 */
	public static final int S_OPCODE_SPOLY = 90;
	/** 产生对话视窗 */
	public static final int S_OPCODE_SHOWHTML = 91;
	/** 配置封包 */
	public static final int S_OPCODE_ABILITY = 92;
	/** 存入资金城堡宝库 */
	public static final int S_OPCODE_DEPOSIT = 93;
	/** 物件攻击 */
	public static final int S_OPCODE_ATTACKPACKET = 94;
	/** 物品状态更新 */
	public static final int S_OPCODE_ITEMSTATUS = 95;
	/** 物品可用次数 */
	public static final int S_OPCODE_ITEMAMOUNT = 95;
	/** 不明封包 (会将头衔变更为空白) */
	public static final int S_OPCODE_NEW2 = 97;
	/** 角色创造成功 */
	public static final int S_OPCODE_NEWCHARPACK = 98;
	/** 多功能封包 */
	public static final int S_OPCODE_PACKETBOX = 100;
	/** 多功能封包 */
	public static final int S_OPCODE_ACTIVESPELLS = 100;
	/** 多功能封包 */
	public static final int S_OPCODE_SKILLICONGFX = 100;
	/** 多功能封包 */
	public static final int S_OPCODE_UNKNOWN2 = 100;
	/** 敏捷提升封包 */
	public static final int S_OPCODE_DEXUP = 101;
	/** 物件亮度 */
	public static final int S_OPCODE_LIGHT = 102;
	/** 改变外型 */
	public static final int S_OPCODE_POLY = 103;
	/** 播放音效 */
	public static final int S_OPCODE_SOUND = 104;
	/** 效果图示 (水底呼吸) */
	public static final int S_OPCODE_BLESSOFEVA = 106;
	/** 角色封号 */
	public static final int S_OPCODE_CHARTITLE = 108;
	/** 设定税收封包 */
	public static final int S_OPCODE_TAXRATE = 109;
	/** 物品名称 */
	public static final int S_OPCODE_ITEMNAME = 110;
	/** 魔法学习-材料不足 */
	public static final int S_OPCODE_MATERIAL = 111;
	/** 密语聊天频道 */
	public static final int S_OPCODE_WHISPERCHAT = 113;
	/** 画面正中出现红色/新增未使用 */
	public static final int S_OPCODE_REDMESSAGE = 114;
	/** 物件属性 */
	public static final int S_OPCODE_ATTRIBUTE = 115;
	/** 经验值更新 */
	public static final int S_OPCODE_EXP = 116;
	/** 正义值更新 */
	public static final int S_OPCODE_LAWFUL = 117;
	/** 登入状态 */
	public static final int S_OPCODE_LOGINRESULT = 118;
	/** 角色皇冠 */
	public static final int S_OPCODE_CASTLEMASTER = 119;
	/** 系统讯息 */
	public static final int S_OPCODE_SERVERMSG = 120;
	/** 血盟小屋地图 [地点] */
	public static final int S_OPCODE_HOUSEMAP = 121;
	/** 将死亡的对象复活 */
	public static final int S_OPCODE_RESURRECTION = 122;
	/** 执行物件外观动作 */
	public static final int S_OPCODE_DOACTIONGFX = 123;
	/** 物件删除 */
	public static final int S_OPCODE_REMOVE_OBJECT = 124;
	/** 下载血盟徽章 */
	public static final int S_OPCODE_EMBLEM = 125;
	/** 海浪波浪 */
	public static final int S_OPCODE_LIQUOR = 126;
	/** 血盟小屋名单 */
	public static final int S_OPCODE_HOUSELIST = 127;
	/** 3.2C ServerPacket (3.5C 未抓取) id非正确 */
	public static final int S_OPCODE_USEMAP = 130;

	public static final int S_LETTER = 131;
	/** 3.3C Client Packet (3.5C 未抓取) id非正确 */
	public static final int C_OPCODE_RETURNTOLOGIN = 140;// 要求回到选人画面

	public static final int C_OPCODE_HIRESOLDIER = 141;// 要求雇佣佣兵列表(购买)
	public static final int C_OPCODE_CLAN = 142;// //要求血盟数据(例如盟标)**[未抓取]
	public static final int C_OPCODE_CHANGEWARTIME = 144;// 修正城堡总管全部功能
	public static final int C_OPCODE_PUTSOLDIER = 145;// 要求配置已雇用士兵
	public static final int C_OPCODE_SELECTWARTIME = 146;// 要求选择 变更攻城时间(but3.3C无使用)
	public static final int C_OPCODE_PUTBOWSOLDIER = 147;// 要求配置城墙上弓手

	public Opcodes() {
	}

}
