package lineage.item.etcitem.scroll;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 魔法卷轴 (初级治愈术) - 40859 <br>
 * 魔法卷轴 (神圣武器) - 40866 <br>
 * 魔法卷轴 (解毒术) - 40867 <br>
 * 魔法卷轴 (中级治愈术) - 40877 <br>
 * 魔法卷轴 (通畅气脉术) - 40884 <br>
 * 魔法卷轴 (高级治愈术) - 40893 <br>
 * 魔法卷轴 (圣洁之光) - 40895 <br>
 * 魔法卷轴 (体魄强健术) - 49281 <br>
 * 魔法卷轴 (祝福魔法武器) - 49282 <br>
 * 魔法卷轴 (全部治愈术) - 49286 <br>
 * 
 * @author jrwz
 */
public class MagicScroll_Buff extends ItemExecutor {

	public static ItemExecutor get() {
		return new MagicScroll_Buff();
	}

	private MagicScroll_Buff() {
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

		final int targetID = data[0];

		if (targetID == 0) {
			pc.sendPackets(new S_ServerMessage(281)); // \f1施咒取消。
			return;
		}

		final L1Object target = L1World.getInstance().findObject(targetID);

		final int spellsc_x = target.getX();
		final int spellsc_y = target.getY();
		final int useCount = 1;

		if (pc.getInventory().removeItem(item, useCount) >= useCount) {
			final int itemId = item.getItemId();
			int skillid = itemId - 40858;

			switch (itemId) {
				case 49281: // 魔法卷轴 (体魄强健术)
					skillid = 42;
					break;

				case 49282: // 魔法卷轴 (祝福魔法武器)
					skillid = 48;
					break;

				case 49286: // 魔法卷轴 (全部治愈术)
					skillid = 57;
					break;
			}

			final L1SkillUse l1skilluse = new L1SkillUse();
			l1skilluse.handleCommands(pc, skillid, targetID, spellsc_x, spellsc_y, null, 0, L1SkillUse.TYPE_SPELLSC);
		}
	}
}
