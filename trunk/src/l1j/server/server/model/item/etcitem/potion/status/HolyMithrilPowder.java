package l1j.server.server.model.item.etcitem.potion.status;

import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 神圣的米索莉粉 - 41316
 * 
 * @author jrwz
 */
public class HolyMithrilPowder extends ItemExecutor {

	public static ItemExecutor get() {
		return new HolyMithrilPowder();
	}

	private HolyMithrilPowder() {
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

		// 伊娃圣水
		if (pc.hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			return;
		}

		// 删除圣水状态
		if (pc.hasSkillEffect(STATUS_HOLY_WATER)) {
			pc.removeSkillEffect(STATUS_HOLY_WATER);
		}

		pc.setSkillEffect(STATUS_HOLY_MITHRIL_POWDER, 900 * 1000);
		pc.sendPackets(new S_SkillSound(pc.getId(), 190));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190));
		pc.sendPackets(new S_ServerMessage(1142)); // 你得到可以攻击哈蒙将军的力量。
		pc.getInventory().removeItem(item, 1);
	}
}
