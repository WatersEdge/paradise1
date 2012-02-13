package lineage.item.etcitem.spellbook;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UseSpellBook_Crown;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 王族技能书
 * 
 * @author jrwz
 */
public class CrownSpellBook extends ItemExecutor {

	public static ItemExecutor get() {
		return new CrownSpellBook();
	}

	private CrownSpellBook() {
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

		if (pc.isCrown() || pc.isGm()) {
			final int itemId = item.getItemId();
			final UniversalUseItem a = new UseSpellBook_Crown();
			a.useItem(pc, item, itemId, 0, 0, 0);
			// Factory.getSpellBook().useCrownSpellBook(pc, item, itemId);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
