package l1j.server.server.model.item.etcitem.potion.hp;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_AddHp;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 终极治愈药水 - 40012
 * 
 * @author jrwz
 */
public class Healing3 extends ItemExecutor {

	public static ItemExecutor get() {
		return new Healing3();
	}

	private Healing3() {
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

		final UniversalUseItem c = new UsePotion_AddHp();
		c.useItem(pc, item, 0, 75, 0, 197);
		// 基本加血量与动画ID
		// Factory.getPotion().useHealingPotion(pc, 75, 197);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
