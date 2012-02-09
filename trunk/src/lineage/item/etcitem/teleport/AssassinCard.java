package lineage.item.etcitem.teleport;

import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 刺客之证 - 40572
 * 
 * @author jrwz
 */
public class AssassinCard extends ItemExecutor {

	private AssassinCard() {
	}

	public static ItemExecutor get() {
		return new AssassinCard();
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

		if ((pc.getX() == 32778) && (pc.getY() == 32738) && (pc.getMapId() == 21)) {
			L1Teleport.teleport(pc, 32781, 32728, (short) 21, 5, true);
		}
		else if ((pc.getX() == 32781) && (pc.getY() == 32728) && (pc.getMapId() == 21)) {
			L1Teleport.teleport(pc, 32778, 32738, (short) 21, 5, true);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79));
		}
	}
}
