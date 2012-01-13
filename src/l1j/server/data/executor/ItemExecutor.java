package l1j.server.data.executor;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 道具执行
 * 
 * @author jrwz
 */
public abstract class ItemExecutor {

	/**
	 * 道具执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            角色
	 * @param item
	 *            物件
	 */
	public abstract void execute(int[] data, L1PcInstance pc,
			L1ItemInstance item);
}
