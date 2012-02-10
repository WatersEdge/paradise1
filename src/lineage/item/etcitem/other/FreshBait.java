package lineage.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 新鲜的饵 - 47103
 * 
 * @author jrwz
 */
public class FreshBait extends ItemExecutor {

	private FreshBait() {
	}

	public static ItemExecutor get() {
		return new FreshBait();
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

		pc.sendPackets(new S_ServerMessage(452, item.getLogName())); // %0%s 被选择了。
	}
}
