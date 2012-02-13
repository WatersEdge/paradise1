package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePolyOther;

/**
 * 夏纳的变身卷轴(60级) - 49153
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV60 extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV60();
	}

	private PolyPotion_ShinerLV60() {
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
					polyId = 6862;
				}
				else if (pc.isKnight()) {
					polyId = 6864;
				}
				else if (pc.isElf()) {
					polyId = 6866;
				}
				else if (pc.isWizard()) {
					polyId = 6868;
				}
				else if (pc.isDarkelf()) {
					polyId = 6870;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7155;
				}
				else if (pc.isIllusionist()) {
					polyId = 7157;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6863;
				}
				else if (pc.isKnight()) {
					polyId = 6865;
				}
				else if (pc.isElf()) {
					polyId = 6867;
				}
				else if (pc.isWizard()) {
					polyId = 6869;
				}
				else if (pc.isDarkelf()) {
					polyId = 6871;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7156;
				}
				else if (pc.isIllusionist()) {
					polyId = 7158;
				}
				break;
		}

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, polyId, 1800, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
