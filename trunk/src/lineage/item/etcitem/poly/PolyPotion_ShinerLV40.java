package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePolyOther;

/**
 * 夏纳的变身卷轴(40级) - 49150
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV40 extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV40();
	}

	private PolyPotion_ShinerLV40() {
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
					polyId = 6832;
				}
				else if (pc.isKnight()) {
					polyId = 6834;
				}
				else if (pc.isElf()) {
					polyId = 6836;
				}
				else if (pc.isWizard()) {
					polyId = 6838;
				}
				else if (pc.isDarkelf()) {
					polyId = 6840;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7143;
				}
				else if (pc.isIllusionist()) {
					polyId = 7145;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6833;
				}
				else if (pc.isKnight()) {
					polyId = 6835;
				}
				else if (pc.isElf()) {
					polyId = 6837;
				}
				else if (pc.isWizard()) {
					polyId = 6839;
				}
				else if (pc.isDarkelf()) {
					polyId = 6841;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7144;
				}
				else if (pc.isIllusionist()) {
					polyId = 7146;
				}
				break;
		}

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, polyId, 1800, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
