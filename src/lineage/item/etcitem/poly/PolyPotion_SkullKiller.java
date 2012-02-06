package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 海贼骷髅刀手变身药水 - 41145
 * 
 * @author jrwz
 */
public class PolyPotion_SkullKiller extends ItemExecutor {

	private PolyPotion_SkullKiller() {
	}

	public static ItemExecutor get() {
		return new PolyPotion_SkullKiller();
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

		// 变身时间 (秒)与变身编号
		Factory.getPoly().usePolyPotion(pc, item, 900, 6088);
	}
}
