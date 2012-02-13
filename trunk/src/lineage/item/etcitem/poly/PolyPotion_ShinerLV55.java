package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UsePolyOther;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 夏纳的变身卷轴(55级) - 49152
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV55 extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV55();
	}

	private PolyPotion_ShinerLV55() {
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
					polyId = 6852;
				}
				else if (pc.isKnight()) {
					polyId = 6854;
				}
				else if (pc.isElf()) {
					polyId = 6856;
				}
				else if (pc.isWizard()) {
					polyId = 6858;
				}
				else if (pc.isDarkelf()) {
					polyId = 6860;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7151;
				}
				else if (pc.isIllusionist()) {
					polyId = 7153;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6853;
				}
				else if (pc.isKnight()) {
					polyId = 6855;
				}
				else if (pc.isElf()) {
					polyId = 6857;
				}
				else if (pc.isWizard()) {
					polyId = 6859;
				}
				else if (pc.isDarkelf()) {
					polyId = 6861;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7152;
				}
				else if (pc.isIllusionist()) {
					polyId = 7154;
				}
				break;
		}

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, polyId, 1800, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
