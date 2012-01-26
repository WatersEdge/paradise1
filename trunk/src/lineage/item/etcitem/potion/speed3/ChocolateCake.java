package lineage.item.etcitem.potion.speed3;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 巧克力蛋糕 - 49138
 * 
 * @author jrwz
 */
public class ChocolateCake extends ItemExecutor {

	private ChocolateCake() {
	}

	public static ItemExecutor get() {
		return new ChocolateCake();
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
		Factory.getPotion().useThirdSpeedPotion(pc, 600, 8031);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
