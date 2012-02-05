package lineage.item.etcitem.potion.hp;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.Factory;
import lineage.console.executor.ItemExecutor;

/**
 * 古代强力体力恢复剂 - 40023
 * 
 * @author jrwz
 */
public class OldTimes2 extends ItemExecutor {

	private OldTimes2() {
	}

	public static ItemExecutor get() {
		return new OldTimes2();
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
		Factory.getPotion().useHealingPotion(pc, 30, 194);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}