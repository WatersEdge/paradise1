package lineage.item.etcitem.quest;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.L1SpawnUtil;
import lineage.console.executor.ItemExecutor;

/**
 * 神秘贝壳 - 40566
 * 
 * @author jrwz
 */
public class MysteriousShell extends ItemExecutor {

	private MysteriousShell() {
	}

	public static ItemExecutor get() {
		return new MysteriousShell();
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

		// 象牙塔村南冰镜湖魔法阵
		if (pc.isElf() && ((pc.getX() >= 33971) && (pc.getX() <= 33975)) && ((pc.getY() >= 32324) && (pc.getY() <= 32328)) && (pc.getMapId() == 4) && !pc.getInventory().checkItem(40548)) { // 古代亡灵之袋
			boolean found = false;
			for (L1Object obj : L1World.getInstance().getObject()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob != null) {
						if (mob.getNpcTemplate().get_npcId() == 45300) {
							found = true;
							break;
						}
					}
				}
			}
			if (found) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
			else {
				L1SpawnUtil.spawn(pc, 45300, 0, 0); // 召唤 古代人亡灵 (试炼怪物)
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
