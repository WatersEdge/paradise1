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
package l1j.server.server.utils;

/**
 * <p>
 * 最低値lowと最大値highによって囲まれた、数値の範囲を指定するクラス。
 * </p>
 * <p>
 * <b>このクラスは同期化されない。</b> 複数のスレッドが同時にこのクラスのインスタンスにアクセスし、 1つ以上のスレッドが範囲を変更する場合、外部的な同期化が必要である。
 * </p>
 */
public class IntRange {
	public static int ensure(final int n, final int low, final int high) {
		int r = n;
		r = (low <= r) ? r : low;
		r = (r <= high) ? r : high;
		return r;
	}

	public static boolean includes(final int i, final int low, final int high) {
		return (low <= i) && (i <= high);
	}

	private final int _low;

	private final int _high;

	public IntRange(final int low, final int high) {
		_low = low;
		_high = high;
	}

	public IntRange(final IntRange range) {
		this(range._low, range._high);
	}

	/**
	 * 数値iを、この範囲内に丸める。
	 * 
	 * @param i
	 *            数値
	 * @return 丸められた値
	 */
	public int ensure(final int i) {
		int r = i;
		r = (_low <= r) ? r : _low;
		r = (r <= _high) ? r : _high;
		return r;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof IntRange)) {
			return false;
		}
		final IntRange range = (IntRange) obj;
		return (_low == range._low) && (_high == range._high);
	}

	/** 获得高的 */
	public int getHigh() {
		return _high;
	}

	/** 获得低的 */
	public int getLow() {
		return _low;
	}

	/** 获得宽度 */
	public int getWidth() {
		return _high - _low;
	}

	/**
	 * 数値iが、範囲内にあるかを返す。
	 * 
	 * @param i
	 *            数値
	 * @return 範囲内であればtrue
	 */
	public boolean includes(final int i) {
		return (_low <= i) && (i <= _high);
	}

	/**
	 * 在此范围内生成随机值。
	 * 
	 * @return 在范围内的随机值
	 */
	public int randomValue() {
		return Random.nextInt(getWidth() + 1) + _low;
	}

	@Override
	public String toString() {
		return "low=" + _low + ", high=" + _high;
	}
}
