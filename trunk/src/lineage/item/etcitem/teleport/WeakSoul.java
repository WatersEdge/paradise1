package lineage.item.etcitem.teleport;

import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1EtcItem;
import lineage.console.executor.ItemExecutor;

/**
 * 微弱的灵魂 - 41208
 * 
 * @author jrwz
 */
public class WeakSoul extends ItemExecutor {

	public static ItemExecutor get() {
		return new WeakSoul();
	}

	private WeakSoul() {
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

		// 船舶之墓:地上层
		if (((pc.getX() >= 32844) && (pc.getX() <= 32845)) && ((pc.getY() >= 32693) && (pc.getY() <= 32694)) && (pc.getMapId() == 550)) {
			L1Teleport.teleport(pc, ((L1EtcItem) item.getItem()).get_locx(), ((L1EtcItem) item.getItem()).get_locy(), ((L1EtcItem) item.getItem()).get_mapid(), 5, true);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}
}
