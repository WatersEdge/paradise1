package l1j.server.server.model.item.etcitem.quest;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 欧西里斯初级宝箱碎片(下) - 49094
 * 
 * @author jrwz
 */
public class TreasureBoxDebris_Osiris_1 extends ItemExecutor {

	public static ItemExecutor get() {
		return new TreasureBoxDebris_Osiris_1();
	}

	private TreasureBoxDebris_Osiris_1() {
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

		if (l1iteminstance1.getItem().getItemId() == 49093) {
			pc.getInventory().consumeItem(49093, 1); // 欧西里斯初级宝箱碎片(上)
			pc.getInventory().consumeItem(49094, 1);
			pc.createNewItem(pc, 49095, 1); // 上锁的欧西里斯初级宝箱
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
