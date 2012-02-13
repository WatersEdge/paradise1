package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UsePotion_AddHp;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 古代终极体力恢复剂 - 40024
 * 
 * @author jrwz
 */
public class OldTimes3 extends ItemExecutor {

	public static ItemExecutor get() {
		return new OldTimes3();
	}

	private OldTimes3() {
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
		c.useItem(pc, item, 0, 55, 0, 197);
		// 基本加血量与动画ID
		// Factory.getPotion().useHealingPotion(pc, 55, 197);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
