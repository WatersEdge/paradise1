package l1j.server.server.model.item.etcitem.potion.speed2;

import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.etcitem.UseSpeedPotion_2_ElfBrave;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 福利森林药水 - 49304
 * 
 * @author jrwz
 */
public class WelfareForestPotion extends ItemExecutor {

	public static ItemExecutor get() {
		return new WelfareForestPotion();
	}

	private WelfareForestPotion() {
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
			b.useItem(pc, item, 0, 0, 1920, 751);
			// 效果时间 (秒)与动画ID
			// Factory.getPotion().useElfBravePotion(pc, 1920, 751);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
