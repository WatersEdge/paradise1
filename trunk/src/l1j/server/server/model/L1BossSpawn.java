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

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.Random;

/**
 * 产生BOSS
 */
public class L1BossSpawn extends L1Spawn {

	private class SpawnTask implements Runnable {
		private int _spawnNumber;
		private int _objectId;

		private SpawnTask(int spawnNumber, int objectId) {
			_spawnNumber = spawnNumber;
			_objectId = objectId;
		}

		@Override
		public void run() {
			doSpawn(_spawnNumber, _objectId);
		}
	}

	private static Logger _log = Logger.getLogger(L1BossSpawn.class.getName());

	private int _spawnCount;

	private String _cycleType;

	private int _percentage;

	private L1BossCycle _cycle;

	private Calendar _activeSpawnTime;

	public L1BossSpawn(L1Npc mobTemplate) {
		super(mobTemplate);
	}

	/**
	 * SpawnTask启动。
	 * 
	 * @param spawnNumber
	 *            L1Spawn管理这些数字。也可以指定任意一点。
	 */
	@Override
	public void executeSpawnTask(int spawnNumber, int objectId) {
		// countをデクリメントして全部死んだかチェック
		if (subAndGetCount() != 0) {
			return; // 没有全部死
		}
		// 前回出现时间に对して、次の出现时间を算出
		Calendar spawnTime;
		Calendar now = Calendar.getInstance(); // 现时刻
		Calendar latestStart = _cycle.getLatestStartTime(now); // 现时刻に对する最近の周期の开始时间

		Calendar activeStart = _cycle.getSpawnStartTime(_activeSpawnTime); // アクティブだった周期の开始时间
		// アクティブだった周期の开始时间 >= 最近の周期开始时间の场合、次の出现
		if (!activeStart.before(latestStart)) {
			spawnTime = calcNextSpawnTime(activeStart);
		}
		else {
			// アクティブだった周期の开始时间 < 最近の周期开始时间の场合は、最近の周期で出现
			// わかりづらいが确率计算する为に、无理やりcalcNextSpawnTimeを通している。
			latestStart.add(Calendar.SECOND, -1);
			spawnTime = calcNextSpawnTime(_cycle.getLatestStartTime(latestStart));
		}
		spawnBoss(spawnTime, objectId);
	}

	@Override
	public void init() {
		if (_percentage <= 0) {
			return;
		}
		_cycle = L1BossCycle.getBossCycle(_cycleType);
		if (_cycle == null) {
			throw new RuntimeException(_cycleType + " not found");
		}
		Calendar now = Calendar.getInstance();
		// 出现时间
		Calendar spawnTime;
		if (Config.INIT_BOSS_SPAWN && _percentage > Random.nextInt(100)) {
			spawnTime = _cycle.calcSpawnTime(now);

		}
		else {
			spawnTime = calcNextSpawnTime(now);
		}
		spawnBoss(spawnTime, 0);
	}

	public void setCycleType(String type) {
		_cycleType = type;
	}

	public void setPercentage(int percentage) {
		_percentage = percentage;
	}

	/**
	 * 表示现在活动的BOSS 对应周期出现时间表。
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[MOB]npcid:" + getNpcId());
		builder.append(" name:" + getName());
		builder.append("[Type]" + _cycle.getName());
		builder.append("[现在周期]");
		builder.append(_cycle.getSpawnStartTime(_activeSpawnTime).getTime());
		builder.append(" - ");
		builder.append(_cycle.getSpawnEndTime(_activeSpawnTime).getTime());
		builder.append("[出现时间]");
		builder.append(_activeSpawnTime.getTime());
		return builder.toString();
	}

	// 计算出确率计算下一次的出现时间
	private Calendar calcNextSpawnTime(Calendar cal) {
		do {
			cal = _cycle.nextSpawnTime(cal);
		} while (!(_percentage > Random.nextInt(100)));
		return cal;
	}

	// 指定时间BOSS出现计划
	private void spawnBoss(Calendar spawnTime, int objectId) {
		// 保存这次的出现时间。再出现时使用。
		_activeSpawnTime = spawnTime;
		long delay = spawnTime.getTimeInMillis() - System.currentTimeMillis();

		int cnt = _spawnCount;
		_spawnCount = getAmount();
		while (cnt < getAmount()) {
			cnt++;
			GeneralThreadPool.getInstance().schedule(new SpawnTask(0, objectId), delay);
		}
		_log.log(Level.FINE, toString());
	}

	private synchronized int subAndGetCount() {
		return --_spawnCount;
	}
}
