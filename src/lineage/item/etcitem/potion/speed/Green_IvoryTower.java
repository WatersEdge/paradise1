package lineage.item.etcitem.potion.speed;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.action.Potion;
import lineage.console.executor.ItemExecutor;

/**
 * 象牙塔加速药水 - 40030
 * 
 * @author jrwz
 */
public class Green_IvoryTower extends ItemExecutor {

	private Green_IvoryTower() {
	}

	public static ItemExecutor get() {
		return new Green_IvoryTower();
	}

	/**
	 * 道具执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            对象
	 * @param item
	 *            道具
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {

		// 效果时间 (秒)
		Potion.useGreenPotion(pc, 300);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
