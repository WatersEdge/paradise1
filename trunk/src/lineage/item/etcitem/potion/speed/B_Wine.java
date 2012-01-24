package lineage.item.etcitem.potion.speed;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 受祝福的 葡萄酒 - 41338
 * 
 * @author jrwz
 */
public class B_Wine extends ItemExecutor {

	private B_Wine() {
	}

	public static ItemExecutor get() {
		return new B_Wine();
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
		Factory.getPotion().useGreenPotion(pc, 1800, 191);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
