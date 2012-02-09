package lineage.item.etcitem.potion.blessofeva;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 水中的水 - 41344
 * 
 * @author jrwz
 */
public class WaterWater extends ItemExecutor {

	private WaterWater() {
	}

	public static ItemExecutor get() {
		return new WaterWater();
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

		// 效果时间 (秒)与动画ID
		Factory.getPotion().useBlessOfEvaPotion(pc, 2100, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}