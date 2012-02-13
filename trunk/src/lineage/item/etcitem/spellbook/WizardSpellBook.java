package lineage.item.etcitem.spellbook;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UseSpellBook;

/**
 * 法师技能书 (LV1~LV10)
 * 
 * @author jrwz
 */
public class WizardSpellBook extends ItemExecutor {

	private WizardSpellBook() {
	}

	public static ItemExecutor get() {
		return new WizardSpellBook();
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
		final UniversalUseItem d = new UseSpellBook();
		d.useItem(pc, item, itemId, 0, 0, 0);
		// Factory.getSpellBook().useSpellBook(pc, item, itemId);
	}
}
