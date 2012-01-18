package lineage.item.etcitem.potion.speed;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.action.Potion;
import lineage.console.executor.ItemExecutor;

/**
 * 受祝福的 强化 自我加速药水 - 140018
 * 
 * @author jrwz
 */
public class Green_Powerful_B extends ItemExecutor {

	private Green_Powerful_B() {
	}

	public static ItemExecutor get() {
		return new Green_Powerful_B();
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
		Potion.useGreenPotion(pc, 2100);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
