package l1j.server.server.model.item.executor;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 物件执行 (道具/武器/防具)
 * 
 * @author jrwz
 */
public abstract class ItemExecutor {

	/**
	 * 物件执行 (道具/武器/防具)
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            对象
	 * @param item
	 *            物件
	 */
	public abstract void execute(int[] data, L1PcInstance pc, L1ItemInstance item);
}
