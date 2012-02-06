package lineage.item.etcitem.poly;

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 暗之鳞 - 41154 <br>
 * 火之鳞 - 41155 <br>
 * 叛之鳞 - 41156 <br>
 * 恨之鳞 - 41157 <br>
 * 妖魔密使变形卷轴 - 49220 <br>
 * 
 * @author jrwz
 */
public class PolyScale extends ItemExecutor {

	private PolyScale() {
	}

	public static ItemExecutor get() {
		return new PolyScale();
	}

	/**
	 * 道具物件执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            执行者
	 * @param item
	 *            物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		final int itemId = item.getItemId();
		this.usePolyScale(pc, itemId);
		pc.getInventory().removeItem(item, 1);
	}

	private void usePolyScale(final L1PcInstance pc, final int itemId) {
		int time = 900;
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身.
			return;
		}

		int polyId = 0;
		switch (itemId) {
			case 41154: // 暗之鳞
				polyId = 3101;
				break;

			case 41155: // 火之鳞
				polyId = 3126;
				break;

			case 41156: // 叛之鳞
				polyId = 3888;
				break;

			case 41157: // 恨之鳞
				polyId = 3784;
				break;

			case 49220: // 妖魔密使变形卷轴
				polyId = 6984;
				time = 1200;
				break;
		}
		L1PolyMorph.doPoly(pc, polyId, time, L1PolyMorph.MORPH_BY_ITEMMAGIC);
	}
}
