package lineage.item.etcitem.other;

import l1j.server.Config;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.Random;
import lineage.console.executor.ItemExecutor;

/**
 * 召唤球之核 - 41029
 * 
 * @author jrwz
 */
public class Call_Ball_Nuclear extends ItemExecutor {

	private Call_Ball_Nuclear() {
	}

	public static ItemExecutor get() {
		return new Call_Ball_Nuclear();
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
		final int dantesId = l1iteminstance1.getItem().getItemId();

		// 召唤球碎片・各阶段
		if ((dantesId >= 41030) && (41034 >= dantesId)) {
			if ((Random.nextInt(99) + 1) < Config.CREATE_CHANCE_DANTES) {
				pc.createNewItem(pc, dantesId + 1, 1);
			}
			else {
				pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName())); // \f1%0%s 消失。
			}
			pc.getInventory().removeItem(l1iteminstance1, 1);
			pc.getInventory().removeItem(item, 1);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
