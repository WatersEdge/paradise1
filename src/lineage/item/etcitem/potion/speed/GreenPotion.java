package lineage.item.etcitem.potion.speed;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 绿色药水 - 40013
 * 
 * @author jrwz
 */
public class GreenPotion extends ItemExecutor {

	private GreenPotion() {
	}

	public static ItemExecutor get() {
		return new GreenPotion();
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
		Factory.getPotion().useGreenPotion(pc, 300, 191);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
