package lineage.item.etcitem.potion.mp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePotion_AddMp;

/**
 * 神秘的魔力药水 - 41142
 * 
 * @author jrwz
 */
public class MysteriousMagicPotion extends ItemExecutor {

	private MysteriousMagicPotion() {
	}

	public static ItemExecutor get() {
		return new MysteriousMagicPotion();
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

		final UniversalUseItem b = new UsePotion_AddMp();
		b.useItem(pc, item, 0, 50, 0, 190);
		// 基本加魔量与动画ID
		// Factory.getPotion().useAddMpPotion(pc, 50, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
