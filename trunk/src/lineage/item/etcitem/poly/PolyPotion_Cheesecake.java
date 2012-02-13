package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePolyOther;

/**
 * 起司蛋糕 - 49139
 * 
 * @author jrwz
 */
public class PolyPotion_Cheesecake extends ItemExecutor {

	private PolyPotion_Cheesecake() {
	}

	public static ItemExecutor get() {
		return new PolyPotion_Cheesecake();
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

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, 6137, 900, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 900, 6137); // 52级死亡骑士
	}
}
