package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 夏纳的变身卷轴(30级) - 49149
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV30 extends ItemExecutor {

	private PolyPotion_ShinerLV30() {
	}

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV30();
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

		int polyId = 0;

		switch (pc.get_sex()) {
			case 0:
				if (pc.isCrown()) {
					polyId = 6822;
				}
				else if (pc.isKnight()) {
					polyId = 6824;
				}
				else if (pc.isElf()) {
					polyId = 6826;
				}
				else if (pc.isWizard()) {
					polyId = 6828;
				}
				else if (pc.isDarkelf()) {
					polyId = 6830;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7139;
				}
				else if (pc.isIllusionist()) {
					polyId = 7141;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6823;
				}
				else if (pc.isKnight()) {
					polyId = 6825;
				}
				else if (pc.isElf()) {
					polyId = 6827;
				}
				else if (pc.isWizard()) {
					polyId = 6829;
				}
				else if (pc.isDarkelf()) {
					polyId = 6831;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7140;
				}
				else if (pc.isIllusionist()) {
					polyId = 7142;
				}
				break;
		}

		// 变身时间 (秒)与变身编号
		Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
