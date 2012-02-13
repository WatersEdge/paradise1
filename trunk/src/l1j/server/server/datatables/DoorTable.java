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
package l1j.server.server.datatables;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.ActionCodes;
import l1j.server.server.IdFactory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.templates.L1DoorGfx;
import l1j.server.server.templates.L1DoorSpawn;
import l1j.server.server.utils.collections.Lists;
import l1j.server.server.utils.collections.Maps;

/**
 * 门资料表
 */
public class DoorTable {

	private static Logger _log = Logger.getLogger(DoorTable.class.getName());

	private static DoorTable _instance;

	public static DoorTable getInstance() {
		return _instance;
	}

	/** 初始化 */
	public static void initialize() {
		_instance = new DoorTable();
	}

	/** 门 */
	private final Map<L1Location, L1DoorInstance> _doors = Maps.newConcurrentHashMap();

	/** 门的方向 */
	private final Map<L1Location, L1DoorInstance> _doorDirections = Maps.newConcurrentHashMap();

	private DoorTable() {
		loadDoors();
	}

	/**
	 * 创建门
	 * 
	 * @param doorId
	 * @param gfx
	 * @param loc
	 * @param hp
	 * @param keeper
	 * @param isOpening
	 */
	public L1DoorInstance createDoor(int doorId, L1DoorGfx gfx, L1Location loc, int hp, int keeper, boolean isOpening) {
		if (_doors.containsKey(loc)) {
			return null;
		}
		L1DoorInstance door = new L1DoorInstance(doorId, gfx, loc, hp, keeper, isOpening);

		door.setId(IdFactory.getInstance().nextId());

		L1World.getInstance().storeObject(door);
		L1World.getInstance().addVisibleObject(door);

		_doors.put(door.getLocation(), door);
		putDirections(door);
		return door;
	}

	/**
	 * 删除门位置
	 * 
	 * @param loc
	 */
	public void deleteDoorByLocation(L1Location loc) {
		L1DoorInstance door = _doors.remove(loc);
		if (door != null) {
			removeDirections(door);
			door.deleteMe();
		}
	}

	/**
	 * 查找门的ID
	 * 
	 * @param doorId
	 */
	public L1DoorInstance findByDoorId(int doorId) {
		for (L1DoorInstance door : _doors.values()) {
			if (door.getDoorId() == doorId) {
				return door;
			}
		}
		return null;
	}

	/**
	 * 取得门的方向
	 * 
	 * @param loc
	 */
	public int getDoorDirection(L1Location loc) {
		L1DoorInstance door = _doorDirections.get(loc);
		if (door == null || door.getOpenStatus() == ActionCodes.ACTION_Open) {
			return -1;
		}
		return door.getDirection();
	}

	/**
	 * 取得门列表
	 * 
	 * @return
	 */
	public L1DoorInstance[] getDoorList() {
		return _doors.values().toArray(new L1DoorInstance[_doors.size()]);
	}

	/**
	 * 加载门
	 */
	private void loadDoors() {
		for (L1DoorSpawn spawn : L1DoorSpawn.all()) {
			L1Location loc = spawn.getLocation();
			if (_doors.containsKey(loc)) {
				_log.log(Level.WARNING, String.format("重复的门 位置: id = %d", spawn.getId()));
				continue;
			}
			createDoor(spawn.getId(), spawn.getGfx(), loc, spawn.getHp(), spawn.getKeeper(), spawn.isOpening());
		}
	}

	/**
	 * 创建方向Keys
	 * 
	 * @param door
	 */
	private List<L1Location> makeDirectionsKeys(L1DoorInstance door) {
		List<L1Location> keys = Lists.newArrayList();
		int left = door.getLeftEdgeLocation();
		int right = door.getRightEdgeLocation();
		if (door.getDirection() == 0) {
			for (int x = left; x <= right; x++) {
				keys.add(new L1Location(x, door.getY(), door.getMapId()));
			}
		}
		else {
			for (int y = left; y <= right; y++) {
				keys.add(new L1Location(door.getX(), y, door.getMapId()));
			}
		}
		return keys;
	}

	/**
	 * 打开方向
	 * 
	 * @param door
	 */
	private void putDirections(L1DoorInstance door) {
		for (L1Location key : makeDirectionsKeys(door)) {
			_doorDirections.put(key, door);
		}
	}

	/**
	 * 删除方向
	 * 
	 * @param door
	 */
	private void removeDirections(L1DoorInstance door) {
		for (L1Location key : makeDirectionsKeys(door)) {
			_doorDirections.remove(key);
		}
	}
}
