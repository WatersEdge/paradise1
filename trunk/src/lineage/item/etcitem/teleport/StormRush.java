package lineage.item.etcitem.teleport;

import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 暴风疾走 - 42501
 * 
 * @author jrwz
 */
public class StormRush extends ItemExecutor {

	private StormRush() {
	}

	public static ItemExecutor get() {
		return new StormRush();
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

		final int spellsc_x = data[1];
		final int spellsc_y = data[2];

		if (pc.getCurrentMp() < 10) {
			pc.sendPackets(new S_ServerMessage(278)); // \f1因魔力不足而无法使用魔法。
			return;
		}
		pc.setCurrentMp(pc.getCurrentMp() - 10);
		L1Teleport.teleport(pc, spellsc_x, spellsc_y, pc.getMapId(), pc.getHeading(), true, L1Teleport.CHANGE_POSITION);
	}
}
