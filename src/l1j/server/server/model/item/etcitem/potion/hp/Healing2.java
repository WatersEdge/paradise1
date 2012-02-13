package l1j.server.server.model.item.etcitem.potion.hp;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_AddHp;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 强力治愈药水 - 40011
 * 
 * @author jrwz
 */
public class Healing2 extends ItemExecutor {

	public static ItemExecutor get() {
		return new Healing2();
	}

	private Healing2() {
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

		final UniversalUseItem b = new UsePotion_AddHp();
		b.useItem(pc, item, 0, 45, 0, 194);
		// 基本加血量与动画ID
		// Factory.getPotion().useHealingPotion(pc, 45, 194);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
