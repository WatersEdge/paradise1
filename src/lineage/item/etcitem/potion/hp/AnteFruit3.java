package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.action.Potion;
import lineage.console.executor.ItemExecutor;

/**
 * 安特的水果 - 40506
 * 
 * @author jrwz
 */
public class AnteFruit3 extends ItemExecutor {

	private AnteFruit3() {
	}

	public static ItemExecutor get() {
		return new AnteFruit3();
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

		// 基本加血量与动画ID
		Potion.UseHeallingPotion(pc, 70, 197);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
