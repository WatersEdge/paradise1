package lineage.item.etcitem.other;

import l1j.server.Config;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.Random;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 精工的蓝、绿、红宝石 - (40931 - 40942)
 * 
 * @author jrwz
 */
public class Seiko_Blue_Green_Ruby extends ItemExecutor {

	public static ItemExecutor get() {
		return new Seiko_Blue_Green_Ruby();
	}

	private Seiko_Blue_Green_Ruby() {
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

		final int itemId = item.getItemId();
		final int itemobj = data[0];
		final L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(itemobj);
		final int earing3Id = l1iteminstance1.getItem().getItemId();

		// 神秘的耳环类
		if ((earing3Id >= 41161) && (41172 >= earing3Id)) {
			if (earing3Id == (itemId + 230)) {
				if ((Random.nextInt(99) + 1) < Config.CREATE_CHANCE_PROCESSING) {
					int[] earingId = { 41161, 41162, 41163, 41164, 41165, 41166, 41167, 41168, 41169, 41170, 41171, 41172 };
					int[] earinglevel = { 21014, 21006, 21007, 21015, 21009, 21008, 21016, 21012, 21010, 21017, 21013, 21011 };
					for (int i = 0; i < earingId.length; i++) {
						if (earing3Id == earingId[i]) {
							pc.createNewItem(pc, earinglevel[i], 1);
							break;
						}
					}
				}
				else {
					pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName())); // \f1%0%s 消失。
				}
				pc.getInventory().removeItem(l1iteminstance1, 1);
				pc.getInventory().removeItem(item, 1);
			}
			else {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
