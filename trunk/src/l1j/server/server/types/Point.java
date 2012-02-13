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
package l1j.server.server.types;

/**
 * 坐标点
 */
public class Point {

	protected int _x = 0;

	protected int _y = 0;

	private static final int HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

	private static final int HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	public Point() {
	}

	/**
	 * 坐标点
	 * 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		_x = x;
		_y = y;
	}

	/**
	 * 坐标点
	 * 
	 * @param pt
	 */
	public Point(Point pt) {
		_x = pt._x;
		_y = pt._y;
	}

	/**
	 * 指定面向反向前进位置坐标。
	 * 
	 * @param heading
	 *            面向(0~7)
	 */
	public void backward(int heading) {
		_x -= HEADING_TABLE_X[heading];
		_y -= HEADING_TABLE_Y[heading];
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}
		Point pt = (Point) obj;
		return (getX() == pt.getX()) && (getY() == pt.getY());
	}

	/**
	 * 指定面向前进位置坐标。
	 * 
	 * @param heading
	 *            面向(0~7)
	 */
	public void forward(int heading) {
		_x += HEADING_TABLE_X[heading];
		_y += HEADING_TABLE_Y[heading];
	}

	/**
	 * 指定坐标的直线距离。
	 * 
	 * @param pt
	 *            坐标保存的Point对象
	 * @return 直线距离值
	 */
	public double getLineDistance(Point pt) {
		long diffX = pt.getX() - getX();
		long diffY = pt.getY() - getY();
		return Math.sqrt((diffX * diffX) + (diffY * diffY));
	}

	/**
	 * 指定坐标直线距离 (相对位置最大距离)。
	 * 
	 * @param pt
	 *            坐标保存的Point对象
	 * @return 直线距离值。
	 */
	public int getTileDistance(Point pt) {
		return Math.abs(pt.getX() - getX()) + Math.abs(pt.getY() - getY());
	}

	/**
	 * 指定坐标直线距离 (相对位置最大距离)。
	 * 
	 * @param pt
	 *            坐标保存的Point对象
	 * @return 直线距离值。
	 */
	public int getTileLineDistance(Point pt) {
		return Math.max(Math.abs(pt.getX() - getX()), Math.abs(pt.getY() - getY()));
	}

	/**
	 * 取得X坐标点
	 * 
	 * @return
	 */
	public int getX() {
		return _x;
	}

	/**
	 * 取得Y坐标点
	 * 
	 * @return
	 */
	public int getY() {
		return _y;
	}

	@Override
	public int hashCode() {
		return 7 * getX() + getY();
	}

	/**
	 * 指定された座標が画面内に見えるかを返す プレイヤーの座標を(0,0)とすれば見える範囲の座標は 左上(2,-15)右上(15,-2)左下(-15,2)右下(-2,15)となる。 チャット欄に隠れて見えない部分も画面内に含まれる。
	 * 
	 * @param pt
	 *            坐标保存的Point对象
	 * @return 指定された座標が画面内に見える場合はtrue。そうでない場合はfalse。
	 */
	public boolean isInScreen(Point pt) {
		int dist = getTileDistance(pt);

		if (dist > 19) { // 当tile距离 > 19 的时候，判定为不在画面内(false)
			return false;
		}
		else if (dist <= 18) { // 当tile距离 <= 18 的时候，判定为位于同一个画面内(true)
			return true;
		}
		else {
			// 左右の画面外部分を除外
			// プレイヤーの座標を(18, 18)とした場合に(0, 0)にあたる座標からの距離で判断
			// Point pointZero = new Point(this.getX() - 18, this.getY() - 18);
			// int dist2 = pointZero.getTileDistance(pt);
			// 显示区的坐标系统 (18, 18)
			int dist2 = Math.abs(pt.getX() - (getX() - 18)) + Math.abs(pt.getY() - (getY() - 18));
			if ((19 <= dist2) && (dist2 <= 52)) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 是否与制定坐标位置重叠。
	 * 
	 * @param pt
	 *            坐标保存的Point对象
	 * @return true是 false否。
	 */
	public boolean isSamePoint(Point pt) {
		return ((pt.getX() == getX()) && (pt.getY() == getY()));
	}

	/**
	 * 设定坐标点
	 * 
	 * @param x
	 *            坐标点X
	 * @param y
	 *            坐标点Y
	 */
	public void set(int x, int y) {
		_x = x;
		_y = y;
	}

	/**
	 * 设定坐标点
	 * 
	 * @param pt
	 *            坐标点
	 */
	public void set(Point pt) {
		_x = pt._x;
		_y = pt._y;
	}

	/**
	 * 设定X坐标点
	 * 
	 * @param x
	 */
	public void setX(int x) {
		_x = x;
	}

	/**
	 * 设定Y坐标点
	 * 
	 * @param y
	 */
	public void setY(int y) {
		_y = y;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", _x, _y);
	}
}
