package lineage.item.etcitem;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.serverpackets.S_Sound;
import lineage.console.executor.ItemExecutor;

/**
 * 哨子 - 40315
 * 
 * @author jrwz
 */
public class PetWhistle extends ItemExecutor {

	private PetWhistle() {
	}

	public static ItemExecutor get() {
		return new PetWhistle();
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

		pc.sendPackets(new S_Sound(437));
		pc.broadcastPacket(new S_Sound(437));
		for (L1NpcInstance petNpc : pc.getPetList().values()) {
			if (petNpc instanceof L1PetInstance) { // 宠物
				L1PetInstance pet = (L1PetInstance) petNpc;
				pet.call();
			}
		}
	}
}
