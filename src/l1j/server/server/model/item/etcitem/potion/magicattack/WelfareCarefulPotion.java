package l1j.server.server.model.item.etcitem.potion.magicattack;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_Wisdom;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 福利慎重药水 - 49307
 * 
 * @author jrwz
 */
public class WelfareCarefulPotion extends ItemExecutor {

	public static ItemExecutor get() {
		return new WelfareCarefulPotion();
	}

	private WelfareCarefulPotion() {
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
			final UniversalUseItem a = new UsePotion_Wisdom();
			a.useItem(pc, item, 0, 0, 1000, 750);
			// 效果时间 (秒)与动画ID
			// Factory.getPotion().useWisdomPotion(pc, 1000, 750);
		}

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
