package lineage.item.etcitem.potion;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Liquor;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 酒 - 40858
 * 
 * @author jrwz
 */
public class Liquor extends ItemExecutor {

	public static ItemExecutor get() {
		return new Liquor();
	}

	private Liquor() {
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

		// 醉酒状态开
		pc.setDrink(true);
		pc.sendPackets(new S_Liquor(pc.getId(), 1));

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
