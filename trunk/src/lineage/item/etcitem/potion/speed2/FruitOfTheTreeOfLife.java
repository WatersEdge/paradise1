package lineage.item.etcitem.potion.speed2;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;
import lineage.item.etcitem.UseSpeedPotion_2_RiBrave;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 生命之树果实 - 49158
 * 
 * @author jrwz
 */
public class FruitOfTheTreeOfLife extends ItemExecutor {

	public static ItemExecutor get() {
		return new FruitOfTheTreeOfLife();
	}

	private FruitOfTheTreeOfLife() {
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

		// 龙骑士、幻术师
		if (pc.isDragonKnight() || pc.isIllusionist()) {
			final UniversalUseItem b = new UseSpeedPotion_2_RiBrave();
			b.useItem(pc, item, 0, 0, 480, 7110);
			// 效果时间 (秒)与动画ID
			// Factory.getPotion().useRiBravePotion(pc, 480, 7110);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
