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
package l1j.server.server.model.map;

import l1j.server.server.types.Point;

/**
 * L1Map 保存地图信息提供各种接口。
 */
public abstract class L1Map {
	private static L1NullMap _nullMap = new L1NullMap();

	protected L1Map() {
	}

	/**
	 * 返回地图ID。
	 * 
	 * @return 地图ID
	 */
	public abstract int getId();

	// TODO JavaDoc
	public abstract int getX();

	public abstract int getY();

	public abstract int getWidth();

	public abstract int getHeight();

	/**
	 * 返回指定的坐标值。
	 * 
	 * 不推荐。这种方法是为兼容现有的代码。
	 * L1Map用户通常需要知道什么是地图存储在不值。
	 * 此外，编写代码，就值这不是存储而定。只有在特殊情况下，如调试，您可以使用此方法。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 坐标指定值
	 */
	public abstract int getTile(int x, int y);

	/**
	 * 返回指定的坐标值。
	 * 
	 * 不推荐。这种方法是为兼容现有的代码。
	 * L1Map用户通常需要知道什么是地图存储在不值。
	 * 此外，编写代码，就值这不是存储而定。只有在特殊情况下，如调试，您可以使用此方法。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 坐标指定值
	 */
	public abstract int getOriginalTile(int x, int y);

	/**
	 * 返回指定地图坐标范围。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 范围内true
	 */
	public abstract boolean isInMap(Point pt);

	/**
	 * 返回指定地图坐标范围。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 范围内true
	 */
	public abstract boolean isInMap(int x, int y);

	/**
	 * 返回是否可通过指定的坐标。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 通行可能true
	 */
	public abstract boolean isPassable(Point pt);

	/**
	 * 返回是否可通过指定的坐标。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 通行可能true
	 */
	public abstract boolean isPassable(int x, int y);

	/**
	 * 返回是否可通过指定的坐标方向。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 通行可能true
	 */
	public abstract boolean isPassable(Point pt, int heading);

	/**
	 * 返回是否可通过指定的坐标方向。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 通行可能true
	 */
	public abstract boolean isPassable(int x, int y, int heading);

	/**
	 * 设置指定的坐标可能通过。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @param isPassable
	 *            通行可能true
	 */
	public abstract void setPassable(Point pt, boolean isPassable);

	/**
	 * 设置指定的坐标可能通过。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @param isPassable
	 *            通行可能true
	 */
	public abstract void setPassable(int x, int y, boolean isPassable);

	/**
	 * 返回指定的坐标是否安全区。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 如果安全区true
	 */
	public abstract boolean isSafetyZone(Point pt);

	/**
	 * 返回指定的坐标是否安全区。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 如果安全区true
	 */
	public abstract boolean isSafetyZone(int x, int y);

	/**
	 * 返回指定坐标是否战斗区。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 如果战斗区true
	 */
	public abstract boolean isCombatZone(Point pt);

	/**
	 * 返回指定坐标是否战斗区。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 如果战斗区true
	 */
	public abstract boolean isCombatZone(int x, int y);

	/**
	 * 返回指定坐标是否安全区。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 如果安全区true
	 */
	public abstract boolean isNormalZone(Point pt);

	/**
	 * 返回指定坐标是否安全区。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 如果安全区true
	 */
	public abstract boolean isNormalZone(int x, int y);

	/**
	 * 返回指定的坐标通过魔法箭。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @return 如果通过魔法箭、true
	 */
	public abstract boolean isArrowPassable(Point pt);

	/**
	 * 返回指定的坐标通过魔法箭。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 如果通过魔法箭、true
	 */
	public abstract boolean isArrowPassable(int x, int y);

	/**
	 * 返回指定的坐标方向 (heading)，是否箭头通过。
	 * 
	 * @param pt
	 *            点对象 (Point)，其中包含的坐标
	 * @param heading
	 *            方向
	 * @return 如果通过魔法箭、true
	 */
	public abstract boolean isArrowPassable(Point pt, int heading);

	/**
	 * 返回指定的坐标方向 (heading)，是否箭头通过。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @param heading
	 *            方向
	 * @return 如果通过魔法箭、true
	 */
	public abstract boolean isArrowPassable(int x, int y, int heading);

	/**
	 * 这张地图、返回地图是否在水下。
	 * 
	 * @return 如果在水中、true
	 */
	public abstract boolean isUnderwater();

	/**
	 * 这张地图，返回是否有可记忆坐标。
	 * 
	 * @return 如果可记忆坐标、true
	 */
	public abstract boolean isMarkable();

	/**
	 * 这张地图，返回是否随机传送。
	 * 
	 * @return 如果可能的话，随机传送、true
	 */
	public abstract boolean isTeleportable();

	/**
	 * 这张地图，返回是否超出了地图是可以瞬移。
	 * 
	 * @return 如果可能的传送、true
	 */
	public abstract boolean isEscapable();

	/**
	 * 这张地图，返回是否可复活。
	 * 
	 * @return 如果可能复活、true
	 */
	public abstract boolean isUseResurrection();

	/**
	 * 这张地图，返回是否可使用魔杖。
	 * 
	 * @return 如果可能，使用魔杖、true
	 */
	public abstract boolean isUsePainwand();

	/**
	 * 这张地图，返回是否有死亡惩罚。
	 * 
	 * @return 如果死亡惩罚、true
	 */
	public abstract boolean isEnabledDeathPenalty();

	/**
	 * 这张地图，返回是否可召唤宠物。
	 * 
	 * @return 如果召唤宠物true
	 */
	public abstract boolean isTakePets();

	/**
	 * 这张地图，返回是否可召回宠物。
	 * 
	 * @return 如果召回宠物true
	 */
	public abstract boolean isRecallPets();

	/**
	 * 返回这张地图是否可以使用道具。
	 * 
	 * @return 如果可以使用道具true
	 */
	public abstract boolean isUsableItem();

	/**
	 * 这张地图，返回是否可以使用技能。
	 * 
	 * @return 如果你可以使用你的技能true
	 */
	public abstract boolean isUsableSkill();

	/**
	 * 返回指定坐标是否捕鱼区。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 如果捕鱼区true
	 */
    public abstract boolean isFishingZone(int x, int y);

    /**
	 * 返回指定的坐标是否有一个门。
	 * 
	 * @param x
	 *            坐标的X值
	 * @param y
	 *            坐标的Y值
	 * @return 如果有门true
	 */
    public abstract boolean isExistDoor(int x, int y);

	public static L1Map newNull() {
		return _nullMap;
	}

	/**
	 * 返回指定的字符串表示PT。
	 */
	public abstract String toString(Point pt);

	/**
	 * 返回此地图是否为空 (null)。
	 * 
	 * @return 如果空 (null)、true
	 */
	public boolean isNull() {
		return false;
	}
}

/**
 * 什么都不做的地图 (Map)。
 */
class L1NullMap extends L1Map {
	public L1NullMap() {
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getTile(int x, int y) {
		return 0;
	}

	@Override
	public int getOriginalTile(int x, int y) {
		return 0;
	}

	@Override
	public boolean isInMap(int x, int y) {
		return false;
	}

	@Override
	public boolean isInMap(Point pt) {
		return false;
	}

	@Override
	public boolean isPassable(int x, int y) {
		return false;
	}

	@Override
	public boolean isPassable(Point pt) {
		return false;
	}

	@Override
	public boolean isPassable(int x, int y, int heading) {
		return false;
	}

	@Override
	public boolean isPassable(Point pt, int heading) {
		return false;
	}

	@Override
	public void setPassable(int x, int y, boolean isPassable) {
	}

	@Override
	public void setPassable(Point pt, boolean isPassable) {
	}

	@Override
	public boolean isSafetyZone(int x, int y) {
		return false;
	}

	@Override
	public boolean isSafetyZone(Point pt) {
		return false;
	}

	@Override
	public boolean isCombatZone(int x, int y) {
		return false;
	}

	@Override
	public boolean isCombatZone(Point pt) {
		return false;
	}

	@Override
	public boolean isNormalZone(int x, int y) {
		return false;
	}

	@Override
	public boolean isNormalZone(Point pt) {
		return false;
	}

	@Override
	public boolean isArrowPassable(int x, int y) {
		return false;
	}

	@Override
	public boolean isArrowPassable(Point pt) {
		return false;
	}

	@Override
	public boolean isArrowPassable(int x, int y, int heading) {
		return false;
	}

	@Override
	public boolean isArrowPassable(Point pt, int heading) {
		return false;
	}

	@Override
	public boolean isUnderwater() {
		return false;
	}

	@Override
	public boolean isMarkable() {
		return false;
	}

	@Override
	public boolean isTeleportable() {
		return false;
	}

	@Override
	public boolean isEscapable() {
		return false;
	}

	@Override
	public boolean isUseResurrection() {
		return false;
	}

	@Override
	public boolean isUsePainwand() {
		return false;
	}

	@Override
	public boolean isEnabledDeathPenalty() {
		return false;
	}

	@Override
	public boolean isTakePets() {
		return false;
	}

	@Override
	public boolean isRecallPets() {
		return false;
	}

	@Override
	public boolean isUsableItem() {
		return false;
	}

	@Override
	public boolean isUsableSkill() {
		return false;
	}

	@Override
	public boolean isFishingZone(int x, int y) {
		return false;
	}

	@Override
	public boolean isExistDoor(int x, int y) {
		return false;
	}

	@Override
	public String toString(Point pt) {
		return "null";
	}

	@Override
	public boolean isNull() {
		return true;
	}
}
