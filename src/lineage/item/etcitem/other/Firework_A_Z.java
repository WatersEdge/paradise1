package lineage.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.executor.ItemExecutor;

/**
 * 字母烟花 A-Z (41357 - 41382)
 * 
 * @author jrwz
 */
public class Firework_A_Z extends ItemExecutor {

	public static ItemExecutor get() {
		return new Firework_A_Z();
	}

	private Firework_A_Z() {
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
		int soundid = itemId - 34946;
		S_SkillSound s_skillsound = new S_SkillSound(pc.getId(), soundid);
		pc.sendPackets(s_skillsound);
		pc.broadcastPacket(s_skillsound);
		pc.getInventory().removeItem(item, 1);
	}
}
