package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UsePolyOther;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 恨之鳞 - 41157
 * 
 * @author jrwz
 */
public class PolyScale_Hate extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyScale_Hate();
	}

	private PolyScale_Hate() {
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

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, 3784, 900, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyScale(pc, item, 900, 3784);
	}
}
