package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UsePotion_AddHp;

/**
 * 受诅咒的 治愈药水 - 240010
 * 
 * @author jrwz
 */
public class C_General1 extends ItemExecutor {

	public static ItemExecutor get() {
		return new C_General1();
	}

	private C_General1() {
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

		final UniversalUseItem a = new UsePotion_AddHp();
		a.useItem(pc, item, 0, 10, 0, 189);
		// 基本加血量与动画ID
		// Factory.getPotion().useHealingPotion(pc, 10, 189);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
