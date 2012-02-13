package l1j.server.server.model.item.etcitem.poly;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePolyOther;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 夏纳的变身卷轴(70级) - 49155
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV70 extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV70();
	}

	private PolyPotion_ShinerLV70() {
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
					polyId = 6882;
				}
				else if (pc.isKnight()) {
					polyId = 6884;
				}
				else if (pc.isElf()) {
					polyId = 6886;
				}
				else if (pc.isWizard()) {
					polyId = 6888;
				}
				else if (pc.isDarkelf()) {
					polyId = 6890;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7163;
				}
				else if (pc.isIllusionist()) {
					polyId = 7165;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6883;
				}
				else if (pc.isKnight()) {
					polyId = 6885;
				}
				else if (pc.isElf()) {
					polyId = 6887;
				}
				else if (pc.isWizard()) {
					polyId = 6889;
				}
				else if (pc.isDarkelf()) {
					polyId = 6891;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7164;
				}
				else if (pc.isIllusionist()) {
					polyId = 7166;
				}
				break;
		}

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, polyId, 1800, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
