package l1j.server.server.model.item.etcitem.poly;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePolyOther;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 妖魔密使变形卷轴 - 49220
 * 
 * @author jrwz
 */
public class PolyScale_DemonEmissary extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyScale_DemonEmissary();
	}

	private PolyScale_DemonEmissary() {
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
		a.useItem(pc, item, 0, 6984, 1200, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyScale(pc, item, 1200, 6984);
	}
}
