package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePolyOther;

/**
 * 海贼骷髅士兵变身药水 - 41144
 * 
 * @author jrwz
 */
public class PolyPotion_SkullSoldiers extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_SkullSoldiers();
	}

	private PolyPotion_SkullSoldiers() {
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
		a.useItem(pc, item, 0, 6087, 900, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 900, 6087);
	}
}
