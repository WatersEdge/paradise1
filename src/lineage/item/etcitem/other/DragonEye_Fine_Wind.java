package lineage.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 风龙之精致魔眼 - 47043
 * 
 * @author jrwz
 */
public class DragonEye_Fine_Wind extends ItemExecutor {

	private DragonEye_Fine_Wind() {
	}

	public static ItemExecutor get() {
		return new DragonEye_Fine_Wind();
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

		if (l1iteminstance1.getItem().getItemId() == 47045) {
			pc.getInventory().consumeItem(47043, 1);
			pc.getInventory().consumeItem(47045, 1);
			pc.createNewItem(pc, 47022, 1); // 形象之魔眼
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
