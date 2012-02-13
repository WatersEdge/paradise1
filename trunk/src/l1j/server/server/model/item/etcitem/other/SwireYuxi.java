package l1j.server.server.model.item.etcitem.other;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 太古的玉玺 - 41428 (可额外新增一个角色)
 * 
 * @author jrwz
 */
public class SwireYuxi extends ItemExecutor {

	public static ItemExecutor get() {
		return new SwireYuxi();
	}

	private SwireYuxi() {
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

		if ((pc != null) && (item != null)) {
			final Account account = Account.load(pc.getAccountName());

			if (account == null) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
				return;
			}

			int characterSlot = account.getCharacterSlot();
			final int maxAmount = Config.DEFAULT_CHARACTER_SLOT + characterSlot;

			if (maxAmount >= 8) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
				return;
			}

			if (characterSlot < 0) {
				characterSlot = 0;
			}
			else {
				characterSlot += 1;
			}

			account.setCharacterSlot(characterSlot);
			Account.updateCharacterSlot(account);
			pc.getInventory().removeItem(item, 1);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79));
		}
	}
}
