package l1j.server.server.model.item.etcitem.poly;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePolyScroll;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 受祝福的 变形卷轴 - 140088
 * 
 * @author jrwz
 */
public class B_PolyScroll extends ItemExecutor {

	public static ItemExecutor get() {
		return new B_PolyScroll();
	}

	private B_PolyScroll() {
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

		final UniversalUseItem a = new UsePolyScroll();
		a.useItem(pc, item, 0, 0, 2100, 0);
		// 变身时间 (秒)
		// Factory.getPoly().usePolyScroll(pc, item, 2100);
	}
}
