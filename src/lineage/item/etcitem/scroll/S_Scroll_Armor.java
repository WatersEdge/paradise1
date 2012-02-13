package lineage.item.etcitem.scroll;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.ScrollEnchant;
import lineage.console.executor.ItemExecutor;
import lineage.item.etcitem.CiteScrollEnchant;

/**
 * 对盔甲施法的卷轴 <br>
 * scroll.S_Scroll_Armor
 * 40078 - 古代人的咒术卷轴 <br>
 * 40074 - 对盔甲施法的卷轴 <br>
 * 40127 - 对盔甲施法的幻象卷轴 <br>
 * 40129 - 奇安的卷轴 <br>
 * 
 * 49311 - 象牙塔对盔甲施法的卷轴 <br>
 * 
 * 140074 - 受祝福的 对盔甲施法的卷轴 <br>
 * 140129 - 受祝福的 奇安的卷轴 <br>
 * 240074 - 受诅咒的 对盔甲施法的卷轴 <br>
 * 
 * @author jrwz
 */
public class S_Scroll_Armor extends ItemExecutor {

	private S_Scroll_Armor() {
	}

	public static ItemExecutor get() {
		return new S_Scroll_Armor();
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

		final int targetID = data[0];
		final L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(targetID);
		final int itemId = item.getItemId();
		final ScrollEnchant Enchant = new CiteScrollEnchant();

		switch (itemId) {
			case 49311: // 象牙塔对盔甲施法的卷轴
				Enchant.scrollOfEnchantArmorIvoryTower(pc, item, l1iteminstance1);
				break;

			default: // 其他
				Enchant.scrollOfEnchantArmor(pc, item, l1iteminstance1);
				break;
		}
	}
}
