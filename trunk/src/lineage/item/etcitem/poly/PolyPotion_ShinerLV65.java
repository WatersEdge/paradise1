package lineage.item.etcitem.poly;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePolyOther;

/**
 * 夏纳的变身卷轴(65级) - 49154
 * 
 * @author jrwz
 */
public class PolyPotion_ShinerLV65 extends ItemExecutor {

	public static ItemExecutor get() {
		return new PolyPotion_ShinerLV65();
	}

	private PolyPotion_ShinerLV65() {
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
					polyId = 6872;
				}
				else if (pc.isKnight()) {
					polyId = 6874;
				}
				else if (pc.isElf()) {
					polyId = 6876;
				}
				else if (pc.isWizard()) {
					polyId = 6878;
				}
				else if (pc.isDarkelf()) {
					polyId = 6880;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7159;
				}
				else if (pc.isIllusionist()) {
					polyId = 7161;
				}
				break;

			case 1:
				if (pc.isCrown()) {
					polyId = 6873;
				}
				else if (pc.isKnight()) {
					polyId = 6875;
				}
				else if (pc.isElf()) {
					polyId = 6877;
				}
				else if (pc.isWizard()) {
					polyId = 6879;
				}
				else if (pc.isDarkelf()) {
					polyId = 6881;
				}
				else if (pc.isDragonKnight()) {
					polyId = 7160;
				}
				else if (pc.isIllusionist()) {
					polyId = 7162;
				}
				break;
		}

		final UniversalUseItem a = new UsePolyOther();
		a.useItem(pc, item, 0, polyId, 1800, 0);
		// 变身时间 (秒)与变身编号
		// Factory.getPoly().usePolyPotion(pc, item, 1800, polyId);
	}
}
