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

import java.util.logging.Logger;

import l1j.server.server.ClientThread;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.templates.L1EtcItem;

/**
 * 物件使用延迟
 * 
 * @author jrwz
 */
public class L1ItemDelay {

	/** 提示信息 */
	private static final Logger _log = Logger.getLogger(L1ItemDelay.class
			.getName());

	/**
	 * 500:武器禁止使用
	 */
	public static final int WEAPON = 500; // 武器禁止使用

	/**
	 * 501:防具禁止使用
	 */
	public static final int ARMOR = 501; // 防具禁止使用

	/**
	 * 502:道具禁止使用
	 */
	public static final int ITEM = 502; // 道具禁止使用

	/**
	 * 503:变身禁止使用
	 */
	public static final int POLY = 503; // 变身禁止使用

	private L1ItemDelay() {
	}

	/**
	 * 道具使用延迟计时器
	 */
	static class ItemDelayTimer implements Runnable {
		private int _delayId;

		private int _delayTime;

		private L1Character _cha;

		public ItemDelayTimer(final L1Character cha, final int id,
				final int delayTime) {
			this._cha = cha;
			this._delayId = id;
			this._delayTime = delayTime;
		}

		@Override
		public void run() {
			this.stopDelayTimer(this._delayId);
		}

		/**
		 * 取得该物件延迟时间
		 */
		public int get_delayTime() {
			return _delayTime;
		}

		/**
		 * 停止该物件使用延迟
		 * 
		 * @param delayId
		 */
		public void stopDelayTimer(final int delayId) {
			this._cha.removeItemDelay(delayId);
		}
	}

	/**
	 * 建立物件使用延迟
	 * 
	 * @param pc
	 *            使用人物
	 * @param delayId
	 *            延迟ID<br>
	 *            500:武器禁止使用<br>
	 *            501:防具禁止使用<br>
	 *            502:道具禁止使用<br>
	 *            503:变身禁止使用<br>
	 *            504:禁止移动<br>
	 * 
	 * @param delayTime
	 *            延迟时间 (毫秒)
	 */
	public static void onItemUse(final L1PcInstance pc, int delayId,
			int delayTime) {
		try {
			if ((delayId != 0) && (delayTime != 0)) {
				final ItemDelayTimer timer = new ItemDelayTimer(pc, delayId,
						delayTime);

				pc.addItemDelay(delayId, timer);
				GeneralThreadPool.getInstance().schedule(timer, delayTime);
			}

		} catch (final Exception e) {
			_log.info(e.getLocalizedMessage());
		}
	}

	/**
	 * 建立物件使用延迟
	 * 
	 * @param client
	 *            执行连线端
	 * @param item
	 *            物件
	 */
	public static void onItemUse(final ClientThread client,
			final L1ItemInstance item) {
		try {
			final L1PcInstance pc = client.getActiveChar();
			if (pc != null) {
				onItemUse(pc, item);
			}

		} catch (final Exception e) {
			_log.info(e.getLocalizedMessage());
		}
	}

	/**
	 * 建立物件使用延迟
	 * 
	 * @param pc
	 *            执行角色
	 * @param item
	 *            物件
	 */
	public static void onItemUse(final L1PcInstance pc,
			final L1ItemInstance item) {
		try {
			int delayId = 0;
			int delayTime = 0;
			switch (item.getItem().getType2()) {
			case 0: // 类别：道具
				delayId = ((L1EtcItem) item.getItem()).get_delayid();
				delayTime = ((L1EtcItem) item.getItem()).get_delaytime();
				break;

			case 1: // 类别：武器
				return;

			case 2: // 类别：防具
				switch (item.getItemId()) {
				case 20077: // 隐身斗篷
				case 120077: // 祝福的 隐身斗篷
				case 20062: // 炎魔的血光斗篷

					// 装备使用中 && 非隐身状态
					if (item.isEquipped() && !pc.isInvisble()) {
						pc.beginInvisTimer();
					}
					break;

				default: // 其他道具
					return;
				}
				break;
			}

			if ((delayId != 0) && (delayTime != 0)) {
				final ItemDelayTimer timer = new ItemDelayTimer(pc, delayId,
						delayTime);

				pc.addItemDelay(delayId, timer);
				GeneralThreadPool.getInstance().schedule(timer, delayTime);
			}

		} catch (final Exception e) {
			_log.info(e.getLocalizedMessage());
		}
	}

	/**
	 * 瞬移解锁定时器
	 */
	static class TeleportUnlockTimer implements Runnable {

		private L1PcInstance _pc;

		public TeleportUnlockTimer(L1PcInstance pc) {
			_pc = pc;
		}

		@Override
		public void run() {
			_pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK,
					true));
		}
	}

	/**
	 * 瞬移解锁
	 */
	public static void teleportUnlock(L1PcInstance pc, L1ItemInstance item) {
		int delayTime = ((L1EtcItem) item.getItem()).get_delaytime();
		TeleportUnlockTimer timer = new TeleportUnlockTimer(pc);
		GeneralThreadPool.getInstance().schedule(timer, delayTime);
	}

}
