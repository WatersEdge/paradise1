package lineage.item.etcitem.scroll;

import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 封印卷轴 - 41426
 * 
 * @author jrwz
 */
public class SealScroll extends ItemExecutor {

	public static ItemExecutor get() {
		return new SealScroll();
	}

	private SealScroll() {
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

		final int itemobj = data[0];
		final L1ItemInstance lockItem = pc.getInventory().getItem(itemobj);

		if (((lockItem != null) && (lockItem.getItem().getType2() == 1)) || (lockItem.getItem().getType2() == 2) || ((lockItem.getItem().getType2() == 0) && lockItem.getItem().isCanSeal())) {
			if ((lockItem.getBless() == 0) || (lockItem.getBless() == 1) || (lockItem.getBless() == 2) || (lockItem.getBless() == 3)) {
				int bless = 1;
				switch (lockItem.getBless()) {
					case 0:
						bless = 128;
						break;
					case 1:
						bless = 129;
						break;
					case 2:
						bless = 130;
						break;
					case 3:
						bless = 131;
						break;
				}
				lockItem.setBless(bless);
				pc.getInventory().updateItem(lockItem, L1PcInventory.COL_BLESS);
				pc.getInventory().saveItem(lockItem, L1PcInventory.COL_BLESS);
				pc.getInventory().removeItem(item, 1);
			}
			else {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
