package lineage.item.etcitem.potion.mp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UsePotion_RestorationMp;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 受祝福的 蓝色药水 - 140015
 * 
 * @author jrwz
 */
public class BlessBluePotion extends ItemExecutor {

	public static ItemExecutor get() {
		return new BlessBluePotion();
	}

	private BlessBluePotion() {
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

		final UniversalUseItem a = new UsePotion_RestorationMp();
		a.useItem(pc, item, 0, 0, 700, 190);
		// 效果时间 (秒)与动画ID
		// Factory.getPotion().useBluePotion(pc, 700, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
