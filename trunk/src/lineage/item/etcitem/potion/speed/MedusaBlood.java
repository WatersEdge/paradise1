package lineage.item.etcitem.potion.speed;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UseSpeedPotion_1;

/**
 * 梅杜莎之血 - 41342
 * 
 * @author jrwz
 */
public class MedusaBlood extends ItemExecutor {

	private MedusaBlood() {
	}

	public static ItemExecutor get() {
		return new MedusaBlood();
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

		final UniversalUseItem a = new UseSpeedPotion_1();
		a.useItem(pc, item, 0, 0, 1800, 191);

		// 效果时间 (秒)与动画ID
		// Factory.getPotion().useGreenPotion(pc, 1800, 191);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
