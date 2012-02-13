package lineage.item.etcitem.scroll;

import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_IdentifyDesc;
import lineage.console.executor.ItemExecutor;

/**
 * 鉴定卷轴 - 40126 <br>
 * 象牙塔鉴定卷轴 - 40098 <br>
 * 
 * @author jrwz
 */
public class Appraisal extends ItemExecutor {

	public static ItemExecutor get() {
		return new Appraisal();
	}

	private Appraisal() {
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
		final L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(itemobj);

		if (!l1iteminstance1.isIdentified()) {
			l1iteminstance1.setIdentified(true);
			pc.getInventory().updateItem(l1iteminstance1, L1PcInventory.COL_IS_ID);
		}
		pc.sendPackets(new S_IdentifyDesc(l1iteminstance1));
		pc.getInventory().removeItem(item, 1);
	}
}
