package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 火之鳞 - 41155
 * 
 * @author jrwz
 */
public class PolyScale_Fire extends ItemExecutor {

	private PolyScale_Fire() {
	}

	public static ItemExecutor get() {
		return new PolyScale_Fire();
	}

	/**
	 * 道具物件执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            执行者
	 * @param item
	 *            物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {

		// 变身时间 (秒)与变身编号
		Factory.getPoly().usePolyScale(pc, item, 900, 3126);
	}
}