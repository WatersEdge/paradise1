package lineage.item.etcitem.scroll;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.ScrollEnchant;
import lineage.item.etcitem.CiteScrollEnchant;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 饰品强化卷轴 - 49148
 * 
 * @author jrwz
 */
public class S_Scroll_Jewelry extends ItemExecutor {

	public static ItemExecutor get() {
		return new S_Scroll_Jewelry();
	}

	private S_Scroll_Jewelry() {
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

		final int targetID = data[0];
		final L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(targetID);
		final ScrollEnchant Enchant = new CiteScrollEnchant();

		Enchant.scrollOfEnchantAccessory(pc, item, l1iteminstance1);
	}
}
