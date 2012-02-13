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
package l1j.server.server.model.gametime;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.utils.collections.Lists;

/**
 * 游戏时钟
 */
public class L1GameTimeClock {

	/** 更新时间 */
	private class TimeUpdater implements Runnable {
		@Override
		public void run() {
			while (true) {
				_previousTime = _currentTime;
				_currentTime = L1GameTime.fromSystemCurrentTime();
				notifyChanged();

				try {
					Thread.sleep(500);
				}
				catch (final InterruptedException e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
		}
	}

	private static Logger _log = Logger.getLogger(L1GameTimeClock.class.getName());
	private static L1GameTimeClock _instance;

	public static L1GameTimeClock getInstance() {
		return _instance;
	}

	/** 初始化 */
	public static void init() {
		_instance = new L1GameTimeClock();
	}

	/** 当前时间 */
	private volatile L1GameTime _currentTime = L1GameTime.fromSystemCurrentTime();

	/** 以前的时间 */
	private L1GameTime _previousTime = null;

	/**  */
	private final List<L1GameTimeListener> _listeners = Lists.newConcurrentList();

	private L1GameTimeClock() {
		GeneralThreadPool.getInstance().execute(new TimeUpdater());
	}

	/** 增加监听 */
	public void addListener(final L1GameTimeListener listener) {
		_listeners.add(listener);
	}

	/** 当前时间 */
	public L1GameTime currentTime() {
		return _currentTime;
	}

	/** 删除监听 */
	public void removeListener(final L1GameTimeListener listener) {
		_listeners.remove(listener);
	}

	/** 改变 */
	private boolean isFieldChanged(final int field) {
		return _previousTime.get(field) != _currentTime.get(field);
	}

	/** 通知改变 */
	private void notifyChanged() {
		if (isFieldChanged(Calendar.MONTH)) {
			for (final L1GameTimeListener listener : _listeners) {
				listener.onMonthChanged(_currentTime);
			}
		}
		if (isFieldChanged(Calendar.DAY_OF_MONTH)) {
			for (final L1GameTimeListener listener : _listeners) {
				listener.onDayChanged(_currentTime);
			}
		}
		if (isFieldChanged(Calendar.HOUR_OF_DAY)) {
			for (final L1GameTimeListener listener : _listeners) {
				listener.onHourChanged(_currentTime);
			}
		}
		if (isFieldChanged(Calendar.MINUTE)) {
			for (final L1GameTimeListener listener : _listeners) {
				listener.onMinuteChanged(_currentTime);
			}
		}
	}
}
