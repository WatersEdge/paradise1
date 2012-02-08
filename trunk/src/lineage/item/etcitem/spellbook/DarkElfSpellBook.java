package lineage.item.etcitem.spellbook;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 黑暗精灵技能书
 * 
 * @author jrwz
 */
public class DarkElfSpellBook extends ItemExecutor {

	private DarkElfSpellBook() {
	}

	public static ItemExecutor get() {
		return new DarkElfSpellBook();
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

		final int itemId = item.getItemId();
		Factory.getSpellBook().useDarkElfSpellBook(pc, item, itemId);
	}
}
