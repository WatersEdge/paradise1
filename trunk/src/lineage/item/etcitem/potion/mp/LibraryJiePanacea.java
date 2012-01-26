package lineage.item.etcitem.potion.mp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 库杰的灵药 - 41404
 * 
 * @author jrwz
 */
public class LibraryJiePanacea extends ItemExecutor {

	private LibraryJiePanacea() {
	}

	public static ItemExecutor get() {
		return new LibraryJiePanacea();
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

		// 基本加魔量与动画ID
		Factory.getPotion().useAddMpPotion(pc, 80, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
