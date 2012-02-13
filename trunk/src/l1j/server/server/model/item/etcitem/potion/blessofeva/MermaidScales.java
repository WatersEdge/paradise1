package l1j.server.server.model.item.etcitem.potion.blessofeva;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_BlessOfEva;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 人鱼之鳞 - 40041
 * 
 * @author jrwz
 */
public class MermaidScales extends ItemExecutor {

	public static ItemExecutor get() {
		return new MermaidScales();
	}

	private MermaidScales() {
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

		final UniversalUseItem a = new UsePotion_BlessOfEva();
		a.useItem(pc, item, 0, 0, 300, 190);
		// 效果时间 (秒)与动画ID
		// Factory.getPotion().useBlessOfEvaPotion(pc, 300, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
