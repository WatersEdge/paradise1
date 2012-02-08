package lineage.item.etcitem.scroll;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillUse;
import lineage.console.executor.ItemExecutor;

/**
 * 魔法卷轴 (日光术) - 40860 <br>
 * 魔法卷轴 (保护罩) - 40861 <br>
 * 魔法卷轴 (无所遁形术) - 40871 <br>
 * 魔法卷轴 (负重强化) - 40872 <br>
 * 魔法卷轴 (魔法屏障) - 40889 <br>
 * 魔法卷轴 (冥想术) - 40890 <br>
 * 魔法卷轴 (体力回复术) - 49283 <br>
 * 魔法卷轴 (神圣疾走) - 49284 <br>
 * 魔法卷轴 (强力加速术) - 49285 <br>
 * 
 * @author jrwz
 */
public class MagicScroll_Normal extends ItemExecutor {

	private MagicScroll_Normal() {
	}

	public static ItemExecutor get() {
		return new MagicScroll_Normal();
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

		final int useCount = 1;
		if (pc.getInventory().removeItem(item, useCount) >= useCount) {

			final int itemId = item.getItemId();
			int skillid = itemId - 40858;

			switch (itemId) {
				case 49283: // 魔法卷轴 (体力回复术)
					skillid = 49;
					break;

				case 49284: // 魔法卷轴 (神圣疾走)
					skillid = 52;
					break;

				case 49285: // 魔法卷轴 (强力加速术)
					skillid = 54;
					break;
			}

			final L1SkillUse l1skilluse = new L1SkillUse();
			l1skilluse.handleCommands(pc, skillid, pc.getId(), 0, 0, null, 0, L1SkillUse.TYPE_SPELLSC);
		}
	}
}
