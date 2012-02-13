package l1j.server.server.model.item.etcitem.spellbook;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UseSpellBook_DarkElf;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 黑暗精灵技能书
 * 
 * @author jrwz
 */
public class DarkElfSpellBook extends ItemExecutor {

	public static ItemExecutor get() {
		return new DarkElfSpellBook();
	}

	private DarkElfSpellBook() {
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

		if (pc.isDarkelf() || pc.isGm()) {
			final int itemId = item.getItemId();
			final UniversalUseItem e = new UseSpellBook_DarkElf();
			e.useItem(pc, item, itemId, 0, 0, 0);
			// Factory.getSpellBook().useDarkElfSpellBook(pc, item, itemId);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
