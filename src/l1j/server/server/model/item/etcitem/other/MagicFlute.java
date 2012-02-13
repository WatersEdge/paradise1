package l1j.server.server.model.item.etcitem.other;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.Instance.L1GuardianInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_Sound;

/**
 * 魔法笛子 - 40493
 * 
 * @author jrwz
 */
public class MagicFlute extends ItemExecutor {

	public static ItemExecutor get() {
		return new MagicFlute();
	}

	private MagicFlute() {
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

		pc.sendPackets(new S_Sound(165));
		pc.broadcastPacket(new S_Sound(165));
		for (final L1Object visible : pc.getKnownObjects()) {
			if (visible instanceof L1GuardianInstance) {
				final L1GuardianInstance guardian = (L1GuardianInstance) visible;
				if (guardian.getNpcTemplate().get_npcId() == 70850) { // 潘
					if (pc.createNewItem(pc, 88, 1)) {
						pc.getInventory().removeItem(item, 1);
					}
				}
			}
		}
	}
}
