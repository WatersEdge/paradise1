package l1j.server.server.model.item.etcitem.potion.hp;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_AddHp;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 伊森之画像 - 49269
 * 
 * @author jrwz
 */
public class Portrait_1 extends ItemExecutor {

	public static ItemExecutor get() {
		return new Portrait_1();
	}

	private Portrait_1() {
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

		final UniversalUseItem a = new UsePotion_AddHp();
		a.useItem(pc, item, 0, 30, 0, 189);
		// 基本加血量与动画ID
		// Factory.getPotion().useHealingPotion(pc, 30, 189);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
