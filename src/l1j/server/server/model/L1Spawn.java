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

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.IdFactory;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.L1GameTime;
import l1j.server.server.model.gametime.L1GameTimeAdapter;
import l1j.server.server.model.gametime.L1GameTimeClock;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1SpawnTime;
import l1j.server.server.types.Point;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.collections.Lists;
import l1j.server.server.utils.collections.Maps;

/**
 * 产生
 */
public class L1Spawn extends L1GameTimeAdapter {

	private class SpawnTask implements Runnable {
		private final int _spawnNumber;

		private final int _objectId;

		private SpawnTask(final int spawnNumber, final int objectId) {
			_spawnNumber = spawnNumber;
			_objectId = objectId;
		}

		@Override
		public void run() {
			doSpawn(_spawnNumber, _objectId);
		}
	}

	private static Logger _log = Logger.getLogger(L1Spawn.class.getName());

	private final L1Npc _template;

	private int _id; // just to find this in the spawn table

	private String _location;

	private int _maximumCount;

	private int _npcid;

	private int _groupId;

	private int _locx;

	private int _locy;

	private int Randomx;

	private int Randomy;

	private int _locx1;

	private int _locy1;

	private int _locx2;

	private int _locy2;

	private int _heading;

	private int _minRespawnDelay;

	private int _maxRespawnDelay;

	private short _mapid;

	private boolean _respaenScreen;

	private int _movementDistance;

	private boolean _rest;

	private int _spawnType;

	private int _delayInterval;

	private L1SpawnTime _time;

	private Map<Integer, Point> _homePoint = null; // initでspawnした個々のオブジェクトのホームポイント

	private final List<L1NpcInstance> _mobs = Lists.newList();

	private String _name;

	private boolean _initSpawn = false;

	private boolean _spawnHomePoint;

	private static final int SPAWN_TYPE_PC_AROUND = 1;

	private static final int PC_AROUND_DISTANCE = 30;

	public static void doCrystalCave(final int npcId) {
		final int[] npcId2 = { 46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152 };
		final int[] doorId = { 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010 };

		for (int i = 0; i < npcId2.length; i++) {
			if (npcId == npcId2[i]) {
				closeDoorInCrystalCave(doorId[i]);
			}
		}
	}

	private static void closeDoorInCrystalCave(final int doorId) {
		for (final L1Object object : L1World.getInstance().getObject()) {
			if (object instanceof L1DoorInstance) {
				final L1DoorInstance door = (L1DoorInstance) object;
				if (door.getDoorId() == doorId) {
					door.close();
				}
			}
		}
	}

	public L1Spawn(final L1Npc mobTemplate) {
		_template = mobTemplate;
	}

	/**
	 * SpawnTask启动。
	 * 
	 * @param spawnNumber
	 *            L1Spawn管理者的数字。ホームポイントが無ければ何を指定しても良い。
	 */
	public void executeSpawnTask(final int spawnNumber, final int objectId) {
		final SpawnTask task = new SpawnTask(spawnNumber, objectId);
		GeneralThreadPool.getInstance().schedule(task, calcRespawnDelay());
	}

	public int getAmount() {
		return _maximumCount;
	}

	public int getGroupId() {
		return _groupId;
	}

	public int getHeading() {
		return _heading;
	}

	public int getId() {
		return _id;
	}

	public String getLocation() {
		return _location;
	}

	public int getLocX() {
		return _locx;
	}

	public int getLocX1() {
		return _locx1;
	}

	public int getLocX2() {
		return _locx2;
	}

	public int getLocY() {
		return _locy;
	}

	public int getLocY1() {
		return _locy1;
	}

	public int getLocY2() {
		return _locy2;
	}

	public short getMapId() {
		return _mapid;
	}

	public int getMaxRespawnDelay() {
		return _maxRespawnDelay;
	}

	public int getMinRespawnDelay() {
		return _minRespawnDelay;
	}

	public int getMovementDistance() {
		return _movementDistance;
	}

	public String getName() {
		return _name;
	}

	public int getNpcId() {
		return _npcid;
	}

	public int getRandomx() {
		return Randomx;
	}

	public int getRandomy() {
		return Randomy;
	}

	public L1SpawnTime getTime() {
		return _time;
	}

	public void init() {
		if ((_time != null) && _time.isDeleteAtEndTime()) {
			// 時間外削除が指定されているなら、時間経過の通知を受ける。
			L1GameTimeClock.getInstance().addListener(this);
		}
		_delayInterval = _maxRespawnDelay - _minRespawnDelay;
		_initSpawn = true;
		// ホームポイントを持たせるか
		if (Config.SPAWN_HOME_POINT && (Config.SPAWN_HOME_POINT_COUNT <= getAmount()) && (Config.SPAWN_HOME_POINT_DELAY >= getMinRespawnDelay()) && isAreaSpawn()) {
			_spawnHomePoint = true;
			_homePoint = Maps.newMap();
		}

		int spawnNum = 0;
		while (spawnNum < _maximumCount) {
			// spawnNumは1～maxmumCountまで
			doSpawn(++spawnNum);
		}
		_initSpawn = false;
	}

	public boolean isRespawnScreen() {
		return _respaenScreen;
	}

	public boolean isRest() {
		return _rest;
	}

	@Override
	public void onMinuteChanged(final L1GameTime time) {
		if (_time.getTimePeriod().includes(time)) {
			return;
		}
		synchronized (_mobs) {
			if (_mobs.isEmpty()) {
				return;
			}
			// 不在指定的时间删除
			for (final L1NpcInstance mob : _mobs) {
				mob.setCurrentHpDirect(0);
				mob.setDead(true);
				mob.setStatus(ActionCodes.ACTION_Die);
				mob.deleteMe();
			}
			_mobs.clear();
		}
	}

	public void setAmount(final int amount) {
		_maximumCount = amount;
	}

	public void setGroupId(final int i) {
		_groupId = i;
	}

	public void setHeading(final int heading) {
		_heading = heading;
	}

	public void setId(final int id) {
		_id = id;
	}

	public void setLocation(final String location) {
		_location = location;
	}

	public void setLocX(final int locx) {
		_locx = locx;
	}

	public void setLocX1(final int locx1) {
		_locx1 = locx1;
	}

	public void setLocX2(final int locx2) {
		_locx2 = locx2;
	}

	public void setLocY(final int locy) {
		_locy = locy;
	}

	public void setLocY1(final int locy1) {
		_locy1 = locy1;
	}

	public void setLocY2(final int locy2) {
		_locy2 = locy2;
	}

	public void setMapId(final short _mapid) {
		this._mapid = _mapid;
	}

	public void setMaxRespawnDelay(final int i) {
		_maxRespawnDelay = i;
	}

	public void setMinRespawnDelay(final int i) {
		_minRespawnDelay = i;
	}

	public void setMovementDistance(final int i) {
		_movementDistance = i;
	}

	public void setName(final String name) {
		_name = name;
	}

	public void setNpcid(final int npcid) {
		_npcid = npcid;
	}

	public void setRandomx(final int randomx) {
		Randomx = randomx;
	}

	public void setRandomy(final int randomy) {
		Randomy = randomy;
	}

	public void setRespawnScreen(final boolean flag) {
		_respaenScreen = flag;
	}

	public void setRest(final boolean flag) {
		_rest = flag;
	}

	public void setSpawnType(final int type) {
		_spawnType = type;
	}

	public void setTime(final L1SpawnTime time) {
		_time = time;
	}

	private int calcRespawnDelay() {
		int respawnDelay = _minRespawnDelay * 1000;
		if (_delayInterval > 0) {
			respawnDelay += Random.nextInt(_delayInterval) * 1000;
		}
		final L1GameTime currentTime = L1GameTimeClock.getInstance().currentTime();
		if ((_time != null) && !_time.getTimePeriod().includes(currentTime)) { // 指定時間外なら指定時間までの時間を足す
			long diff = (_time.getTimeStart().getTime() - currentTime.toTime().getTime());
			if (diff < 0) {
				diff += 24 * 1000L * 3600L;
			}
			diff /= 6; // real time to game time
			respawnDelay = (int) diff;
		}
		return respawnDelay;
	}

	private int getSpawnType() {
		return _spawnType;
	}

	private boolean isAreaSpawn() {
		return (getLocX1() != 0) && (getLocY1() != 0) && (getLocX2() != 0) && (getLocY2() != 0);
	}

	private boolean isRandomSpawn() {
		return (getRandomx() != 0) || (getRandomy() != 0);
	}

	/**
	 * ホームポイントがある場合は、spawnNumberを基にspawnする。 それ以外の場合は、spawnNumberは未使用。
	 */
	protected void doSpawn(final int spawnNumber) { // 初期配置
		// 指定時間外であれば、次spawnを予約して終わる。
		if ((_time != null) && !_time.getTimePeriod().includes(L1GameTimeClock.getInstance().currentTime())) {
			executeSpawnTask(spawnNumber, 0);
			return;
		}
		doSpawn(spawnNumber, 0);
	}

	protected void doSpawn(final int spawnNumber, final int objectId) { // 再出現
		L1NpcInstance mob = null;
		try {
			int newlocx = getLocX();
			int newlocy = getLocY();
			int tryCount = 0;

			mob = NpcTable.getInstance().newNpcInstance(_template);
			synchronized (_mobs) {
				_mobs.add(mob);
			}
			if (objectId == 0) {
				mob.setId(IdFactory.getInstance().nextId());
			}
			else {
				mob.setId(objectId); // オブジェクトID再利用
			}

			if ((0 <= getHeading()) && (getHeading() <= 7)) {
				mob.setHeading(getHeading());
			}
			else {
				// heading値が正しくない
				mob.setHeading(5);
			}

			final int npcId = mob.getNpcTemplate().get_npcId();
			if ((npcId == 45488) && (getMapId() == 9)) { // 卡士伯
				mob.setMap((short) (getMapId() + Random.nextInt(2)));
			}
			else if ((npcId == 45601) && (getMapId() == 11)) { // 死亡騎士
				mob.setMap((short) (getMapId() + Random.nextInt(3)));
			}
			else if ((npcId == 81322) && (getMapId() == 25)) { // 黑騎士副隊長
				mob.setMap((short) (getMapId() + Random.nextInt(2)));
			}
			else {
				mob.setMap(getMapId());
			}
			mob.setMovementDistance(getMovementDistance());
			mob.setRest(isRest());
			while (tryCount <= 50) {
				switch (getSpawnType()) {
					case SPAWN_TYPE_PC_AROUND: // PC周辺に湧くタイプ
						if (!_initSpawn) { // 初期配置では無条件に通常spawn
							final List<L1PcInstance> players = Lists.newList();
							for (final L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
								if (getMapId() == pc.getMapId()) {
									players.add(pc);
								}
							}
							if (players.size() > 0) {
								final L1PcInstance pc = players.get(Random.nextInt(players.size()));
								final L1Location loc = pc.getLocation().randomLocation(PC_AROUND_DISTANCE, false);
								newlocx = loc.getX();
								newlocy = loc.getY();
								break;
							}
						}
						// フロアにPCがいなければ通常の出現方法
					default:
						if (isAreaSpawn()) { // 座標が範囲指定されている場合
							Point pt = null;
							if (_spawnHomePoint && (null != (pt = _homePoint.get(spawnNumber)))) { // ホームポイントを元に再出現させる場合
								final L1Location loc = new L1Location(pt, getMapId()).randomLocation(Config.SPAWN_HOME_POINT_RANGE, false);
								newlocx = loc.getX();
								newlocy = loc.getY();
							}
							else {
								final int rangeX = getLocX2() - getLocX1();
								final int rangeY = getLocY2() - getLocY1();
								newlocx = Random.nextInt(rangeX) + getLocX1();
								newlocy = Random.nextInt(rangeY) + getLocY1();
							}
							if (tryCount > 49) { // 出現位置が決まらない時はlocx,locyの値
								newlocx = getLocX();
								newlocy = getLocY();
							}
						}
						else if (isRandomSpawn()) { // 座標のランダム値が指定されている場合
							newlocx = (getLocX() + (Random.nextInt(getRandomx()) - Random.nextInt(getRandomx())));
							newlocy = (getLocY() + (Random.nextInt(getRandomy()) - Random.nextInt(getRandomy())));
						}
						else { // どちらも指定されていない場合
							newlocx = getLocX();
							newlocy = getLocY();
						}
				}
				mob.setX(newlocx);
				mob.setHomeX(newlocx);
				mob.setY(newlocy);
				mob.setHomeY(newlocy);

				if (mob.getMap().isInMap(mob.getLocation()) && mob.getMap().isPassable(mob.getLocation())) {
					if (mob instanceof L1MonsterInstance) {
						if (isRespawnScreen()) {
							break;
						}
						final L1MonsterInstance mobtemp = (L1MonsterInstance) mob;
						if (L1World.getInstance().getVisiblePlayer(mobtemp).isEmpty()) {
							break;
						}
						// 画面内にPCが居て出現できない場合は、3秒後にスケジューリングしてやり直し
						final SpawnTask task = new SpawnTask(spawnNumber, mob.getId());
						GeneralThreadPool.getInstance().schedule(task, 3000L);
						return;
					}
				}
				tryCount++;
			}
			if (mob instanceof L1MonsterInstance) {
				((L1MonsterInstance) mob).initHide();
			}

			mob.setSpawn(this);
			mob.setreSpawn(true);
			mob.setSpawnNumber(spawnNumber); // L1Spawnでの管理番号(ホームポイントに使用)
			if (_initSpawn && _spawnHomePoint) { // 初期配置でホームポイントを設定
				final Point pt = new Point(mob.getX(), mob.getY());
				_homePoint.put(spawnNumber, pt); // ここで保存したpointを再出現時に使う
			}

			if (mob instanceof L1MonsterInstance) {
				if (mob.getMapId() == 666) {
					((L1MonsterInstance) mob).set_storeDroped(true);
				}
			}
			if ((npcId == 45573) && (mob.getMapId() == 2)) { // バフォメット
				for (final L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc.getMapId() == 2) {
						L1Teleport.teleport(pc, 32664, 32797, (short) 2, 0, true);
					}
				}
			}

			if (((npcId == 46142) && (mob.getMapId() == 73)) || ((npcId == 46141) && (mob.getMapId() == 74))) {
				for (final L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if ((pc.getMapId() >= 72) && (pc.getMapId() <= 74)) {
						L1Teleport.teleport(pc, 32840, 32833, (short) 72, pc.getHeading(), true);
					}
				}
			}
			if ((npcId == 81341) && ((mob.getMapId() == 2000) || (mob.getMapId() == 2001) || (mob.getMapId() == 2002) || (mob.getMapId() == 2003))) { // 再生之祭壇
				for (final L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if ((pc.getMapId() >= 2000) && (pc.getMapId() <= 2003)) {
						L1Teleport.teleport(pc, 32933, 32988, (short) 410, 5, true);
					}
				}
			}

			doCrystalCave(npcId);

			L1World.getInstance().storeObject(mob);
			L1World.getInstance().addVisibleObject(mob);

			if (mob instanceof L1MonsterInstance) {
				final L1MonsterInstance mobtemp = (L1MonsterInstance) mob;
				if (!_initSpawn && (mobtemp.getHiddenStatus() == 0)) {
					mobtemp.onNpcAI(); // モンスターのＡＩを開始
				}
			}
			if (getGroupId() != 0) {
				L1MobGroupSpawn.getInstance().doSpawn(mob, getGroupId(), isRespawnScreen(), _initSpawn);
			}
			mob.turnOnOffLight();
			mob.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // チャット開始
		}
		catch (final Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}
