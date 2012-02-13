package lineage.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 封印的傲慢之塔传送符 (11F-91F) (40280 - 40288)
 * 
 * @author jrwz
 */
public class SealTeleportAmulet extends ItemExecutor {

	public static ItemExecutor get() {
		return new SealTeleportAmulet();
	}

	private SealTeleportAmulet() {
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

		pc.getInventory().removeItem(item, 1);
		final int itemId = item.getItemId();
		final L1ItemInstance item1 = pc.getInventory().storeItem(itemId + 9, 1);
		if (item != null) {
			pc.sendPackets(new S_ServerMessage(403, item1.getLogName())); // 获得%0%o 。
		}
	}
}
