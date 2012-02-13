package lineage.item.etcitem.quest;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.L1SpawnUtil;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 完成的时间水晶球 - 49201
 * 
 * @author jrwz
 */
public class TimeCrystalBall extends ItemExecutor {

	public static ItemExecutor get() {
		return new TimeCrystalBall();
	}

	private TimeCrystalBall() {
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

		if (pc.isIllusionist() && (pc.getMapId() == 4)) { // 火龙窟
			boolean found = false;
			for (final L1Object obj : L1World.getInstance().getObject()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob != null) {
						if (mob.getNpcTemplate().get_npcId() == 81254) { // 时空裂痕
							found = true;
							break;
						}
					}
				}
			}
			if (found) {
				pc.sendPackets(new S_ServerMessage(79));
			}
			else {
				L1SpawnUtil.spawn(pc, 81254, 0, 0);
			}
			pc.getInventory().consumeItem(49201, 1);
		}
	}
}
