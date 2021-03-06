package l1j.server.server.model.item.etcitem.potion.black;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_Blind;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 黑色药水 - 40025
 * 
 * @author jrwz
 */
public class BlackPotion extends ItemExecutor {

	public static ItemExecutor get() {
		return new BlackPotion();
	}

	private BlackPotion() {
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

		final UniversalUseItem a = new UsePotion_Blind();
		a.useItem(pc, item, 0, 0, 16, 0);
		// 效果时间 (秒)
		// Factory.getPotion().useBlindPotion(pc, 16);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
