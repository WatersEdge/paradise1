package l1j.server.server.model.item.etcitem.potion.mp;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UsePotion_RestorationMp;
import l1j.server.server.model.item.executor.ItemExecutor;

/**
 * 蓝色药水 - 40015
 * 
 * @author jrwz
 */
public class BluePotion extends ItemExecutor {

	public static ItemExecutor get() {
		return new BluePotion();
	}

	private BluePotion() {
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

		final UniversalUseItem a = new UsePotion_RestorationMp();
		a.useItem(pc, item, 0, 0, 600, 190);
		// 效果时间 (秒)与动画ID
		// Factory.getPotion().useBluePotion(pc, 600, 190);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
