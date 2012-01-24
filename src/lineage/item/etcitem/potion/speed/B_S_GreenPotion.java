package lineage.item.etcitem.potion.speed;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 受祝福的 强化 绿色药水 - 140018
 * 
 * @author jrwz
 */
public class B_S_GreenPotion extends ItemExecutor {

	private B_S_GreenPotion() {
	}

	public static ItemExecutor get() {
		return new B_S_GreenPotion();
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
		Factory.getPotion().useGreenPotion(pc, 2100, 191);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
