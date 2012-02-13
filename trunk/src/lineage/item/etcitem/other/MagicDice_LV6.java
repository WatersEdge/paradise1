package lineage.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.Random;
import lineage.console.executor.ItemExecutor;

/**
 * 六段式魔法骰子 - 40328
 * 
 * @author jrwz
 */
public class MagicDice_LV6 extends ItemExecutor {

	public static ItemExecutor get() {
		return new MagicDice_LV6();
	}

	private MagicDice_LV6() {
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

		int A = 0;
		if (pc.getInventory().consumeItem(40318, 1)) {
			int SK = Random.nextInt(5) + 1;
			{

				if (SK >= 1 && SK <= 6) {

					A = SK;
					pc.sendPackets(new S_SystemMessage("你骰出的点数是(" + (A) + ")"));
					pc.sendPackets(new S_SkillSound(pc.getId(), 3203 + A)); // 骰子效果显示
					pc.broadcastPacket(new S_SkillSound(pc.getId(), 3203 + A));
				}
			}
		}
		else {
			// \f1没有任何事情发生。
			pc.sendPackets(new S_ServerMessage(79));
		}
	}
}
