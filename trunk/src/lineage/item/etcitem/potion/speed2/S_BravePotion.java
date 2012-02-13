package lineage.item.etcitem.potion.speed2;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UseSpeedPotion_2_Brave;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 强化勇气的药水 - 41415
 * 
 * @author jrwz
 */
public class S_BravePotion extends ItemExecutor {

	public static ItemExecutor get() {
		return new S_BravePotion();
	}

	private S_BravePotion() {
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

		// 骑士
		if (pc.isKnight()) {
			final UniversalUseItem b = new UseSpeedPotion_2_Brave();
			b.useItem(pc, item, 0, 0, 1800, 751);
			// 效果时间 (秒)与动画ID
			// Factory.getPotion().useBravePotion(pc, 1800, 751);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
