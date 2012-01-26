package lineage.item.etcitem.potion.mp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 蓝色药水 - 40015
 * 
 * @author jrwz
 */
public class BluePotion extends ItemExecutor {

	private BluePotion() {
	}

	public static ItemExecutor get() {
		return new BluePotion();
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
		Factory.getPotion().useBluePotion(pc, 600, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
