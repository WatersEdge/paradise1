package lineage.item.etcitem.potion.speed2;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.UseSpeedPotion_2_ElfBrave;

/**
 * 精灵饼干 - 40068
 * 
 * @author jrwz
 */
public class WizardBiscuits extends ItemExecutor {

	private WizardBiscuits() {
	}

	public static ItemExecutor get() {
		return new WizardBiscuits();
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

		// 精灵
		if (pc.isElf()) {
			final UniversalUseItem b = new UseSpeedPotion_2_ElfBrave();
			b.useItem(pc, item, 0, 0, 480, 751);
			// 效果时间 (秒)与动画ID
			// Factory.getPotion().useElfBravePotion(pc, 480, 751);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
