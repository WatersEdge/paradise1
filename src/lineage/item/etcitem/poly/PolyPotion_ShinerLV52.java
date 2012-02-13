package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePolyOther;

/**
 * 夏纳的变身卷轴(52级) - 49151
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV52 extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV52();
	}

	private PolyPotion_ShinerLV52() {
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
					polyId = 6842;
				}
				else if (pc.isKnight()) {
					polyId = 6844;
				}
				else if (pc.isElf()) {
					polyId = 6846;
				}
				else if (pc.isWizard()) {
					polyId = 6848;
				}
				else if (pc.isDarkelf()) {
					polyId = 6850;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7147;
				}
				else if (pc.isIllusionist()) {
					polyId = 7149;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6843;
				}
				else if (pc.isKnight()) {
					polyId = 6845;
				}
				else if (pc.isElf()) {
					polyId = 6847;
				}
				else if (pc.isWizard()) {
					polyId = 6849;
				}
				else if (pc.isDarkelf()) {
					polyId = 6851;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7148;
				}
				else if (pc.isIllusionist()) {
					polyId = 7150;
				}
				break;
		}

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, polyId, 1800, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
