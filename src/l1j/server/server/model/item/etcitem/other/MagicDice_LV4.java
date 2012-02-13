package l1j.server.server.model.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.Random;

/**
 * 四段式魔法骰子 - 40327
 * 
 * @author jrwz
 */
public class MagicDice_LV4 extends ItemExecutor {

	public static ItemExecutor get() {
		return new MagicDice_LV4();
	}

	private MagicDice_LV4() {
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
			final int SK = Random.nextInt(4) + 1;
			{

				if ((SK >= 1) && (SK <= 4)) {

					A = SK;
					switch (A) {
						case 1:
							pc.sendPackets(new S_SystemMessage("(右上)"));
							break;
						case 2:
							pc.sendPackets(new S_SystemMessage("(左上)"));
							break;
						case 3:
							pc.sendPackets(new S_SystemMessage("(右下)"));
							break;
						case 4:
							pc.sendPackets(new S_SystemMessage("(左下)"));
							break;
					}
					pc.sendPackets(new S_SkillSound(pc.getId(), 3240 + A)); // 骰子效果显示
					pc.broadcastPacket(new S_SkillSound(pc.getId(), 3240 + A));
				}
			}
		}
		else {
			// \f1没有任何事情发生。
			pc.sendPackets(new S_ServerMessage(79));
		}
	}
}
