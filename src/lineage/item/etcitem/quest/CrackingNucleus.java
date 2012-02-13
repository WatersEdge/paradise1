package lineage.item.etcitem.quest;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 龟裂之核 - 49092
 * 
 * @author jrwz
 */
public class CrackingNucleus extends ItemExecutor {

	public static ItemExecutor get() {
		return new CrackingNucleus();
	}

	private CrackingNucleus() {
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
		final int targetItemId = l1iteminstance1.getItem().getItemId();

		// 上锁的欧西里斯初级宝箱、上锁的欧西里斯高级宝箱
		if ((targetItemId == 49095) || (targetItemId == 49099)
		// 上锁的库库尔坎初级宝箱、上锁的库库尔坎高级宝箱
				|| (targetItemId == 49318) || (targetItemId == 49322)) {
			pc.createNewItem(pc, targetItemId + 1, 1);
			pc.getInventory().consumeItem(targetItemId, 1);
			pc.getInventory().consumeItem(49092, 1);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
