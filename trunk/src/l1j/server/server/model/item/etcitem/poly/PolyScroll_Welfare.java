package l1j.server.server.model.item.etcitem.poly;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePolyScroll;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.utils.Random;

/**
 * 福利变形药水 - 49308
 * 
 * @author jrwz
 */
public class PolyScroll_Welfare extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyScroll_Welfare();
	}

	private PolyScroll_Welfare() {
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

		// 变身时间随机
		int time = 0;
		time = Random.nextInt(2401, 4800);

		final UniversalUseItem a = new UsePolyScroll();
		a.useItem(pc, item, 0, 0, time, 0);
		// 变身时间 (秒)
		// Factory.getPoly().usePolyScroll(pc, item, time);
	}
}
