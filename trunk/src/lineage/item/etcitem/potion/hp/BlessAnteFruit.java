package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 受祝福的 安特的水果 - 140506
 * 
 * @author jrwz
 */
public class BlessAnteFruit extends ItemExecutor {

	private BlessAnteFruit() {
	}

	public static ItemExecutor get() {
		return new BlessAnteFruit();
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

		// 基本加血量与动画ID
		Factory.getPotion().useHealingPotion(pc, 80, 197);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}