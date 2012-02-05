package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 库杰的粮食 - 41403
 * 
 * @author jrwz
 */
public class LibraryJieFood extends ItemExecutor {

	private LibraryJieFood() {
	}

	public static ItemExecutor get() {
		return new LibraryJieFood();
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

		// 基本加血量与动画ID
		Factory.getPotion().useHealingPotion(pc, 300, 189);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
