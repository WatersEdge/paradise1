package lineage.item.etcitem.spellbook;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;
import lineage.console.connector.cite.UseSpellBook_Elf;
import lineage.console.executor.ItemExecutor;

/**
 * 精灵技能书
 * 
 * @author jrwz
 */
public class ElfSpellBook extends ItemExecutor {

	private ElfSpellBook() {
	}

	public static ItemExecutor get() {
		return new ElfSpellBook();
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

		if (pc.isElf() || pc.isGm()) {
			final int itemId = item.getItemId();
			final UniversalUseItem c = new UseSpellBook_Elf();
			c.useItem(pc, item, itemId, 0, 0, 0);
			// Factory.getSpellBook().useElfSpellBook(pc, item, itemId);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
