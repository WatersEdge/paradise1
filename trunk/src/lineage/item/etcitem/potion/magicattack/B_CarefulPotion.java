package lineage.item.etcitem.potion.magicattack;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 受祝福的 慎重药水 - 140016
 * 
 * @author jrwz
 */
public class B_CarefulPotion extends ItemExecutor {

	private B_CarefulPotion() {
	}

	public static ItemExecutor get() {
		return new B_CarefulPotion();
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

		// 法师
		if (pc.isWizard()) {
			// 效果时间 (秒)与动画ID
			Factory.getPotion().useWisdomPotion(pc, 360, 750);
		}

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
