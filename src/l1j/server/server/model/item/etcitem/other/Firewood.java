package l1j.server.server.model.item.etcitem.other;

import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 柴火 - 41260
 * 
 * @author jrwz
 */
public class Firewood extends ItemExecutor {

	public static ItemExecutor get() {
		return new Firewood();
	}

	private Firewood() {
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

		for (final L1Object object : L1World.getInstance().getVisibleObjects(pc, 3)) {
			if (object instanceof L1EffectInstance) {
				if (((L1NpcInstance) object).getNpcTemplate().get_npcId() == 81170) { // 料理用火堆
					// 附近已经有柴火了。
					pc.sendPackets(new S_ServerMessage(1162));
					return;
				}
			}
		}
		int[] loc = new int[2];
		loc = pc.getFrontLoc();
		L1EffectSpawn.getInstance().spawnEffect(81170, 600000, loc[0], loc[1], pc.getMapId());
		pc.getInventory().removeItem(item, 1);
	}
}
