package lineage.item.etcitem.spellbook;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UseSpellBook_Knight;

/**
 * 骑士技能书
 * 
 * @author jrwz
 */
public class KnightSpellBook extends ItemExecutor {

	public static ItemExecutor get() {
		return new KnightSpellBook();
	}

	private KnightSpellBook() {
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

		if (pc.isKnight() || pc.isGm()) {
			final int itemId = item.getItemId();
			final UniversalUseItem b = new UseSpellBook_Knight();
			b.useItem(pc, item, itemId, 0, 0, 0);
			// Factory.getSpellBook().useKnightSpellBook(pc, item, itemId);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
