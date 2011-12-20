/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.model.gametime;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.ClientThread;
import l1j.server.server.GameServer;
import l1j.server.server.GeneralThreadPool;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.L1GameTime;
import l1j.server.server.model.gametime.L1GameTimeListener;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_BlueMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

/**
 *	定时自动重启伺服器
 */
@SuppressWarnings("unused")
public class L1GameReStart {

	private static Logger _log = Logger.getLogger(L1GameReStart.class.getName());

	private static L1GameReStart _instance;

	private volatile L1GameTime _currentTime = new L1GameTime();

	private L1GameTime _previousTime = null;

	private List<L1GameTimeListener> _listeners = new CopyOnWriteArrayList<L1GameTimeListener>();

	private static int willRestartTime;

	public  int _remnant;

	private class TimeUpdaterRestar implements Runnable {

		public void run() {
			while (true) {
				_previousTime = _currentTime;
				_currentTime = new L1GameTime();
				notifyChanged();
				int remnant = GetRestartTime() * 60;
				System.out.println("╠》正在载入 自动重开设定...完成!\t" + GetRestartTime() + " 分钟后");
				while (remnant > 0) {
					for (int i = remnant ; i >= 0 ; i --) {
						SetRemnant(i);
						willRestartTime = i;

						// (五分钟内 一分钟一次)
						if (i % 60 == 0 && i <= 300 && i != 0) {
							// \f3伺服器将会在 %0 分钟后重新启动。请至安全区准备退出。
							L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166, "\\f3伺服器将会在 " + i/60 + " 分钟后重新启动。请至安全区准备退出"));
							System.out.println("伺服器将于 " + i/60 + " 分钟后重新启动");
						}

						// (30秒内 一秒一次)
						else if (i <= 30 && i != 0) {
							// \f3服务器在 %0秒后关闭。请离开游戏。
							L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(72,"" + i + ""));
							System.out.println("伺服器将于 " + i + " 秒后重新启动");
						}

						// (1秒)
						else if (i == 0) {
							BroadCastToAll("服务器自动重新启动。");
							System.out.println("服务器自动重新启动。");
							GameServer.getInstance().shutdown(); // TODO 修正自动重开角色资料会回溯
							disconnectAllCharacters();
							System.exit(1);
						}

						try {
							Thread.sleep(1000); // 暂停1秒
						} catch (InterruptedException e) {
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
						}

						// 整点报时
						if (Config.HOURLY_CHIME) {
							if (GetNowTime.GetNowHour() == 0 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								BroadCastToAll("\\fR现在时间凌晨12点，长时间在线的玩家注意保护眼睛哦。");
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间凌晨12点"));
							}
							if (GetNowTime.GetNowHour() == 1 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间凌晨1点"));
							}
							if (GetNowTime.GetNowHour() == 2 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间凌晨2点"));
							}
							if (GetNowTime.GetNowHour() == 3 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间凌晨3点"));
							}
							if (GetNowTime.GetNowHour() == 4 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间凌晨4点"));
							}
							if (GetNowTime.GetNowHour() == 5 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间凌晨5点"));
							}
							if (GetNowTime.GetNowHour() == 6 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间早上6点"));
							}
							if (GetNowTime.GetNowHour() == 7 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间早上7点"));
							}
							if (GetNowTime.GetNowHour() == 8 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间上午8点"));
							}
							if (GetNowTime.GetNowHour() == 9 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间上午9点"));
							}
							if (GetNowTime.GetNowHour() == 10 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间上午10点"));
							}
							if (GetNowTime.GetNowHour() == 11 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间上午11点"));
							}
							if (GetNowTime.GetNowHour() == 12 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间中午12点"));
							}
							if (GetNowTime.GetNowHour() == 13 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午1点"));
							}
							if (GetNowTime.GetNowHour() == 14 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午2点"));
							}
							if (GetNowTime.GetNowHour() == 15 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午3点"));
							}
							if (GetNowTime.GetNowHour() == 16 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午4点"));
							}
							if (GetNowTime.GetNowHour() == 17 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午5点"));
							}
							if (GetNowTime.GetNowHour() == 18 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午6点"));
							}
							if (GetNowTime.GetNowHour() == 19 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间下午7点"));
							}
							if (GetNowTime.GetNowHour() == 20 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间晚上8点"));
							}
							if (GetNowTime.GetNowHour() == 21 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间晚上9点"));
							}
							if (GetNowTime.GetNowHour() == 22 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间晚上10点"));
							}
							if (GetNowTime.GetNowHour() == 23 && GetNowTime.GetNowMinute() == 0 && GetNowTime.GetNowSecond() == 0) {
								L1World.getInstance().broadcastPacketToAll(new S_BlueMessage(166,"\\fR现在时间晚上11点"));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 踢掉世界地图中所有的玩家与储存资料。
	 */
	public void disconnectAllCharacters() {
		Collection<L1PcInstance> players = L1World.getInstance().getAllPlayers();
		for (L1PcInstance pc : players) {
			pc.getNetConnection().setActiveChar(null);
			pc.getNetConnection().kick();
		}
		// 踢除所有在线上的玩家
		for (L1PcInstance pc : players) {
			ClientThread.quitGame(pc);
			L1World.getInstance().removeObject(pc);
			Account account = Account.load(pc.getAccountName());
			Account.online(account, false);
		}
	}

	private int GetRestartTime() {
		return Config.REST_TIME;
	}

	private void BroadCastToAll(String string) {
		Collection <L1PcInstance> allpc = L1World.getInstance().getAllPlayers();
		for (L1PcInstance pc : allpc)
			pc.sendPackets(new S_SystemMessage(string));
	}

	public void SetRemnant(int remnant) {
		_remnant = remnant;
	}

	public static int getWillRestartTime() {
		return willRestartTime;
	}

	public int GetRemnant() {
		return _remnant;
	}

	private boolean isFieldChanged(int field) {
		return _previousTime.get(field) != _currentTime.get(field);
	}

	private void notifyChanged() {
		if (isFieldChanged(Calendar.MONTH)) {
			for (L1GameTimeListener listener : _listeners) {
				listener.onMonthChanged(_currentTime);
			}
		}
		if (isFieldChanged(Calendar.DAY_OF_MONTH)) {
			for (L1GameTimeListener listener : _listeners) {
				listener.onDayChanged(_currentTime);
			}
		}
		if (isFieldChanged(Calendar.HOUR_OF_DAY)) {
			for (L1GameTimeListener listener : _listeners) {
				listener.onHourChanged(_currentTime);
			}
		}
		if (isFieldChanged(Calendar.MINUTE)) {
			for (L1GameTimeListener listener : _listeners) {
				listener.onMinuteChanged(_currentTime);
			}
		}
	}

	private L1GameReStart() {
		GeneralThreadPool.getInstance().execute(new TimeUpdaterRestar());
	}

	public static void init() {
		_instance = new L1GameReStart();
	}

	public static L1GameReStart getInstance() {
		return _instance;
	}

	public L1GameTime getGameTime() {
		return _currentTime;
	}

	public void addListener(L1GameTimeListener listener) {
		_listeners.add(listener);
	}

	public void removeListener(L1GameTimeListener listener) {
		_listeners.remove(listener);
	}

}
