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
package l1j.server.server.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.UBSpawnTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.identity.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.IntRange;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.collections.Lists;

// Referenced classes of package l1j.server.server.model:
// L1UltimateBattle

/**
 * 无限大战
 */
public class L1UltimateBattle {

	class UbThread implements Runnable {
		/**
		 * 线程过程。
		 */
		@Override
		public void run() {
			try {
				setActive(true);
				countDown();
				setNowUb(true);
				for (int round = 1; round <= 4; round++) {
					sendRoundMessage(round);

					final L1UbPattern pattern = UBSpawnTable.getInstance().getPattern(_ubId, _pattern);

					final List<L1UbSpawn> spawnList = pattern.getSpawnList(round);

					for (final L1UbSpawn spawn : spawnList) {
						if (getMembersCount() > 0) {
							spawn.spawnAll();
						}

						Thread.sleep(spawn.getSpawnDelay() * 1000);
						// removeRetiredMembers();
					}

					if (getMembersCount() > 0) {
						spawnSupplies(round);
					}

					waitForNextRound(round);
				}

				for (final L1PcInstance pc : getMembersArray()) // 竞技场内的PC出来
				{
					final int rndx = Random.nextInt(4);
					final int rndy = Random.nextInt(4);
					final int locx = 33503 + rndx;
					final int locy = 32764 + rndy;
					final short mapid = 4;
					L1Teleport.teleport(pc, locx, locy, mapid, 5, true);
					removeMember(pc);
				}
				clearColosseum();
				setActive(false);
				setNowUb(false);
			}
			catch (final Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}

		/**
		 * 竞技场开始倒计时。
		 * 
		 * @throws InterruptedException
		 */
		private void countDown() throws InterruptedException {
			// XXX - 此ID错误
			final int MSGID_COUNT = 637;
			final int MSGID_START = 632;

			for (int loop = 0; loop < BEFORE_MINUTE * 60 - 10; loop++) { // 开始前等待10秒
				Thread.sleep(1000);
				// removeRetiredMembers();
			}
			removeRetiredMembers();

			sendMessage(MSGID_COUNT, "10"); // 10秒前

			Thread.sleep(5000);
			sendMessage(MSGID_COUNT, "5"); // 5秒前

			Thread.sleep(1000);
			sendMessage(MSGID_COUNT, "4"); // 4秒前

			Thread.sleep(1000);
			sendMessage(MSGID_COUNT, "3"); // 3秒前

			Thread.sleep(1000);
			sendMessage(MSGID_COUNT, "2"); // 2秒前

			Thread.sleep(1000);
			sendMessage(MSGID_COUNT, "1"); // 1秒前

			Thread.sleep(1000);
			sendMessage(MSGID_START, "无限大战开始"); // 开始
			removeRetiredMembers();
		}

		/**
		 * 全部的怪物出现后、待机 等待下一轮开始。
		 * 
		 * @param curRound
		 *            本轮
		 * @throws InterruptedException
		 */
		private void waitForNextRound(final int curRound) throws InterruptedException {
			final int WAIT_TIME_TABLE[] = { 6, 6, 2, 18 };

			final int wait = WAIT_TIME_TABLE[curRound - 1];
			for (int i = 0; i < wait; i++) {
				Thread.sleep(10000);
				// removeRetiredMembers();
			}
			removeRetiredMembers();
		}
	}

	private static Calendar getRealTime() {
		final TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		final Calendar cal = Calendar.getInstance(_tz);
		return cal;
	}

	private int _locX;

	private int _locY;

	private L1Location _location; // 中心点

	private short _mapId;

	private int _locX1;

	private int _locY1;

	private int _locX2;

	private int _locY2;

	private int _ubId;

	private int _pattern;

	private boolean _isNowUb;

	private boolean _active; // UB入場可能～競技終了までtrue

	private int _minLevel;

	private int _maxLevel;

	private int _maxPlayer;

	private boolean _enterRoyal;

	private boolean _enterKnight;

	private boolean _enterMage;

	private boolean _enterElf;

	private boolean _enterDarkelf;

	private boolean _enterDragonKnight;

	private boolean _enterIllusionist;

	private boolean _enterMale;

	private boolean _enterFemale;

	private boolean _usePot;

	private int _hpr;

	private int _mpr;

	private static int BEFORE_MINUTE = 5; // 入场前5分钟开始

	private final Set<Integer> _managers = new HashSet<Integer>();

	private final SortedSet<Integer> _ubTimes = new TreeSet<Integer>();

	private static final Logger _log = Logger.getLogger(L1UltimateBattle.class.getName());

	private static String intToTimeFormat(final int n) {
		return n / 100 + ":" + n % 100 / 10 + "" + n % 10;
	}

	private final List<L1PcInstance> _members = Lists.newList();

	private String[] _ubInfo;

	/**
	 * 构造。
	 */
	public L1UltimateBattle() {
	}

	public void addManager(final int npcId) {
		_managers.add(npcId);
	}

	/**
	 * 加入角色的名单。
	 * 
	 * @param pc
	 *            新角色参加
	 */
	public void addMember(final L1PcInstance pc) {
		if (!_members.contains(pc)) {
			_members.add(pc);
		}
	}

	public void addUbTime(final int time) {
		_ubTimes.add(time);
	}

	/**
	 * UB参加可能、等级、检查角色的类。
	 * 
	 * @param pc
	 *            检查可以参加UB的PC
	 * @return 能参加true,不能false
	 */
	public boolean canPcEnter(final L1PcInstance pc) {
		_log.log(Level.FINE, "pcname={0} ubid={1} minlvl={2} maxlvl={3}", new Object[] { pc.getName(), _ubId, _minLevel, _maxLevel });
		// 什么级别可以参加
		if (!IntRange.includes(pc.getLevel(), _minLevel, _maxLevel)) {
			return false;
		}

		// 可以参加的玩家类别
		if (!((pc.isCrown() && _enterRoyal) || (pc.isKnight() && _enterKnight) || (pc.isWizard() && _enterMage) || (pc.isElf() && _enterElf) || (pc.isDarkelf() && _enterDarkelf) || (pc.isDragonKnight() && _enterDragonKnight) || (pc.isIllusionist() && _enterIllusionist))) {
			return false;
		}

		return true;
	}

	public boolean canUsePot() {
		return _usePot;
	}

	public boolean checkUbTime() {
		final SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		final Calendar realTime = getRealTime();
		realTime.add(Calendar.MINUTE, BEFORE_MINUTE);
		final int nowTime = Integer.valueOf(sdf.format(realTime.getTime()));
		return _ubTimes.contains(nowTime);
	}

	/**
	 * 清除参加人员名单。
	 */
	public void clearMembers() {
		_members.clear();
	}

	public boolean containsManager(final int npcId) {
		return _managers.contains(npcId);
	}

	public int getHpr() {
		return _hpr;
	}

	public L1Location getLocation() {
		return _location;
	}

	public int getLocX1() {
		return _locX1;
	}

	public int getLocX2() {
		return _locX2;
	}

	public int getLocY1() {
		return _locY1;
	}

	public int getLocY2() {
		return _locY2;
	}

	public short getMapId() {
		return _mapId;
	}

	public int getMaxLevel() {
		return _maxLevel;
	}

	public int getMaxPlayer() {
		return _maxPlayer;
	}

	/**
	 * 返回参加的角色是否组队。
	 * 
	 * @return 参加者的组队
	 */
	public L1PcInstance[] getMembersArray() {
		return _members.toArray(new L1PcInstance[_members.size()]);
	}

	/**
	 * 返回参与成员数量。
	 * 
	 * @return 参加人数
	 */
	public int getMembersCount() {
		return _members.size();
	}

	public int getMinLevel() {
		return _minLevel;
	}

	public int getMpr() {
		return _mpr;
	}

	public String getNextUbTime() {
		return intToTimeFormat(nextUbTime());
	}

	public int getUbId() {
		return _ubId;
	}

	/**
	 * @return UB入場可能～競技終了true,否则false。
	 */
	public boolean isActive() {
		return _active;
	}

	/**
	 * 玩家、返回是否参加。
	 * 
	 * @param pc
	 *            检查玩家
	 * @return 如果参加true、否则false。
	 */
	public boolean isMember(final L1PcInstance pc) {
		return _members.contains(pc);
	}

	/**
	 * 返回是否在UB中。
	 * 
	 * @return UB中true、否则false。
	 */
	public boolean isNowUb() {
		return _isNowUb;
	}

	public String[] makeUbInfoStrings() {
		if (_ubInfo != null) {
			return _ubInfo;
		}
		final String nextUbTime = getNextUbTime();
		// 类
		final StringBuilder classesBuff = new StringBuilder();
		if (_enterDarkelf) {
			classesBuff.append("黑暗精灵 ");
		}
		if (_enterMage) {
			classesBuff.append("魔法师 ");
		}
		if (_enterElf) {
			classesBuff.append("精灵 ");
		}
		if (_enterKnight) {
			classesBuff.append("骑士 ");
		}
		if (_enterRoyal) {
			classesBuff.append("王族 ");
		}
		if (_enterDragonKnight) {
			classesBuff.append("龙骑士 ");
		}
		if (_enterIllusionist) {
			classesBuff.append("幻术师 ");
		}
		final String classes = classesBuff.toString().trim();
		// 性別
		final StringBuilder sexBuff = new StringBuilder();
		if (_enterMale) {
			sexBuff.append("男 ");
		}
		if (_enterFemale) {
			sexBuff.append("女 ");
		}
		final String sex = sexBuff.toString().trim();
		final String loLevel = String.valueOf(_minLevel);
		final String hiLevel = String.valueOf(_maxLevel);
		final String teleport = _location.getMap().isEscapable() ? "可能" : "不可能";
		final String res = _location.getMap().isUseResurrection() ? "可能" : "不可能";
		final String pot = "可能";
		final String hpr = String.valueOf(_hpr);
		final String mpr = String.valueOf(_mpr);
		final String summon = _location.getMap().isTakePets() ? "可能" : "不可能";
		final String summon2 = _location.getMap().isRecallPets() ? "可能" : "不可能";
		_ubInfo = new String[] { nextUbTime, classes, sex, loLevel, hiLevel, teleport, res, pot, hpr, mpr, summon, summon2 };
		return _ubInfo;
	}

	/**
	 * 删除参加的角色名单。
	 * 
	 * @param pc
	 *            删除角色
	 */
	public void removeMember(final L1PcInstance pc) {
		_members.remove(pc);
	}

	// setされたlocx1～locy2から中心点を求める。
	public void resetLoc() {
		_locX = (_locX2 + _locX1) / 2;
		_locY = (_locY2 + _locY1) / 2;
		_location = new L1Location(_locX, _locY, _mapId);
	}

	public void setEnterDarkelf(final boolean enterDarkelf) {
		_enterDarkelf = enterDarkelf;
	}

	public void setEnterDragonKnight(final boolean enterDragonKnight) {
		_enterDragonKnight = enterDragonKnight;
	}

	public void setEnterElf(final boolean enterElf) {
		_enterElf = enterElf;
	}

	public void setEnterFemale(final boolean enterFemale) {
		_enterFemale = enterFemale;
	}

	public void setEnterIllusionist(final boolean enterIllusionist) {
		_enterIllusionist = enterIllusionist;
	}

	public void setEnterKnight(final boolean enterKnight) {
		_enterKnight = enterKnight;
	}

	public void setEnterMage(final boolean enterMage) {
		_enterMage = enterMage;
	}

	public void setEnterMale(final boolean enterMale) {
		_enterMale = enterMale;
	}

	public void setEnterRoyal(final boolean enterRoyal) {
		_enterRoyal = enterRoyal;
	}

	public void setHpr(final int hpr) {
		_hpr = hpr;
	}

	public void setLocX1(final int locX1) {
		_locX1 = locX1;
	}

	public void setLocX2(final int locX2) {
		_locX2 = locX2;
	}

	public void setLocY1(final int locY1) {
		_locY1 = locY1;
	}

	public void setLocY2(final int locY2) {
		_locY2 = locY2;
	}

	public void setMapId(final short mapId) {
		_mapId = mapId;
	}

	public void setMaxLevel(final int level) {
		_maxLevel = level;
	}

	public void setMaxPlayer(final int count) {
		_maxPlayer = count;
	}

	public void setMinLevel(final int level) {
		_minLevel = level;
	}

	public void setMpr(final int mpr) {
		_mpr = mpr;
	}

	public void setUbId(final int id) {
		_ubId = id;
	}

	public void setUsePot(final boolean usePot) {
		_usePot = usePot;
	}

	/**
	 * 开始无限大战。
	 * 
	 * @param ubId
	 *            开始无限大战的ID
	 */
	public void start() {
		final int patternsMax = UBSpawnTable.getInstance().getMaxPattern(_ubId);
		_pattern = Random.nextInt(patternsMax) + 1; // 确定出现模式

		final UbThread ub = new UbThread();
		GeneralThreadPool.getInstance().execute(ub);
	}

	/**
	 * 删除竞技场内所有的怪物与道具。
	 */
	private void clearColosseum() {
		for (final Object obj : L1World.getInstance().getVisibleObjects(_mapId).values()) {
			if (obj instanceof L1MonsterInstance) // 删除怪物
			{
				final L1MonsterInstance mob = (L1MonsterInstance) obj;
				if (!mob.isDead()) {
					mob.setDead(true);
					mob.setStatus(ActionCodes.ACTION_Die);
					mob.setCurrentHpDirect(0);
					mob.deleteMe();

				}
			}
			else if (obj instanceof L1Inventory) // 删除道具
			{
				final L1Inventory inventory = (L1Inventory) obj;
				inventory.clearItems();
			}
		}
	}

	private int nextUbTime() {
		final SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		final int nowTime = Integer.valueOf(sdf.format(getRealTime().getTime()));
		SortedSet<Integer> tailSet = _ubTimes.tailSet(nowTime);
		if (tailSet.isEmpty()) {
			tailSet = _ubTimes;
		}
		return tailSet.first();
	}

	/**
	 * 从成员列表删除退出人员。
	 */
	private void removeRetiredMembers() {
		final L1PcInstance[] temp = getMembersArray();
		for (final L1PcInstance element : temp) {
			if (element.getMapId() != _mapId) {
				removeMember(element);
			}
		}
	}

	/**
	 * UB参加人员发送信息(S_ServerMessage)。
	 * 
	 * @param type
	 *            消息类型
	 * @param msg
	 *            发送消息
	 */
	private void sendMessage(final int type, final String msg) {
		for (final L1PcInstance pc : getMembersArray()) {
			pc.sendPackets(new S_ServerMessage(type, msg));
		}
	}

	/**
	 * 全面开始时发送消息。
	 * 
	 * @param curRound
	 *            回合开始
	 */
	private void sendRoundMessage(final int curRound) {
		// XXX - 此ID错误
		final int MSGID_ROUND_TABLE[] = { 893, 894, 895, 896 };

		sendMessage(MSGID_ROUND_TABLE[curRound - 1], "");
	}

	private void setActive(final boolean f) {
		_active = f;
	}

	/**
	 * 设置UB。
	 * 
	 * @param i
	 *            true/false
	 */
	private void setNowUb(final boolean i) {
		_isNowUb = i;
	}

	/**
	 * 在竞技场出现的道具。
	 * 
	 * @param itemId
	 *            出现道具的编号ID
	 * @param stackCount
	 *            道具的堆叠数量
	 * @param count
	 *            出现数量
	 */
	private void spawnGroundItem(final int itemId, final int stackCount, final int count) {
		final L1Item temp = ItemTable.getInstance().getTemplate(itemId);
		if (temp == null) {
			return;
		}

		for (int i = 0; i < count; i++) {
			final L1Location loc = _location.randomLocation((getLocX2() - getLocX1()) / 2, false);
			if (temp.isStackable()) {
				final L1ItemInstance item = ItemTable.getInstance().createItem(itemId);
				item.setEnchantLevel(0);
				item.setCount(stackCount);
				final L1GroundInventory ground = L1World.getInstance().getInventory(loc.getX(), loc.getY(), _mapId);
				if (ground.checkAddItem(item, stackCount) == L1Inventory.OK) {
					ground.storeItem(item);
				}
			}
			else {
				L1ItemInstance item = null;
				for (int createCount = 0; createCount < stackCount; createCount++) {
					item = ItemTable.getInstance().createItem(itemId);
					item.setEnchantLevel(0);
					final L1GroundInventory ground = L1World.getInstance().getInventory(loc.getX(), loc.getY(), _mapId);
					if (ground.checkAddItem(item, stackCount) == L1Inventory.OK) {
						ground.storeItem(item);
					}
				}
			}
		}
	}

	/**
	 * 药水等补给项目出现。
	 * 
	 * @param curRound
	 *            本轮
	 */
	private void spawnSupplies(final int curRound) {
		if (curRound == 1) {
			spawnGroundItem(L1ItemId.ADENA, 1000, 60);
			spawnGroundItem(L1ItemId.POTION_OF_CURE_POISON, 3, 20);
			spawnGroundItem(L1ItemId.POTION_OF_EXTRA_HEALING, 5, 20);
			spawnGroundItem(L1ItemId.POTION_OF_GREATER_HEALING, 3, 20);
			spawnGroundItem(40317, 1, 5); // 磨刀石
			spawnGroundItem(40079, 1, 20); // 传送回家的卷轴
		}
		else if (curRound == 2) {
			spawnGroundItem(L1ItemId.ADENA, 5000, 50);
			spawnGroundItem(L1ItemId.POTION_OF_CURE_POISON, 5, 20);
			spawnGroundItem(L1ItemId.POTION_OF_EXTRA_HEALING, 10, 20);
			spawnGroundItem(L1ItemId.POTION_OF_GREATER_HEALING, 5, 20);
			spawnGroundItem(40317, 1, 7); // 磨刀石
			spawnGroundItem(40093, 1, 10); // 空的魔法卷轴(Lv4)
			spawnGroundItem(40079, 1, 5); // 传送回家的卷轴
		}
		else if (curRound == 3) {
			spawnGroundItem(L1ItemId.ADENA, 10000, 30);
			spawnGroundItem(L1ItemId.POTION_OF_CURE_POISON, 7, 20);
			spawnGroundItem(L1ItemId.POTION_OF_EXTRA_HEALING, 20, 20);
			spawnGroundItem(L1ItemId.POTION_OF_GREATER_HEALING, 10, 20);
			spawnGroundItem(40317, 1, 10); // 磨刀石
			spawnGroundItem(40094, 1, 10); // 空的魔法卷轴(Lv5)
		}
	}
}
