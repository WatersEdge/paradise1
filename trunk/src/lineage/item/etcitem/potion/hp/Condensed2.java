package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UsePotion_AddHp;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 浓缩强力体力恢复剂 - 40020
 * 
 * @author jrwz
 */
public class Condensed2 extends ItemExecutor {

	public static ItemExecutor get() {
		return new Condensed2();
	}

	private Condensed2() {
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
