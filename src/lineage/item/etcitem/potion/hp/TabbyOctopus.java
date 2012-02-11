package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.connector.cite.UsePotion_AddHp;
import lineage.console.executor.ItemExecutor;

/**
 * 虎斑带鱼 - 41299
 * 
 * @author jrwz
 */
public class TabbyOctopus extends ItemExecutor {

	private TabbyOctopus() {
	}

	public static ItemExecutor get() {
		return new TabbyOctopus();
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
		b.useItem(pc, item, 0, 15, 0, 194);
		// 基本加血量与动画ID
		// Factory.getPotion().useHealingPotion(pc, 15, 194);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
