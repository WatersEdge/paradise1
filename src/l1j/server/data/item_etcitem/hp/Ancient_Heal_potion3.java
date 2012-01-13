package l1j.server.data.item_etcitem.hp;

import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;

import l1j.server.data.executor.ItemExecutor;
import l1j.server.data.executor.remove.RemoveSkillEffect;
import l1j.server.data.executor.stop.StopUsePotion;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 古代终极体力恢复剂 (ID:40024)
 * 
 * @author jrwz
 */
public class Ancient_Heal_potion3 extends ItemExecutor {

	private Ancient_Heal_potion3() {
	}

	public static ItemExecutor get() {
		return new Ancient_Heal_potion3();
	}

	/**
	 * 道具执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            角色
	 * @param item
	 *            道具
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc,
			final L1ItemInstance item) {

		// 道具为空
		if (item == null) {
			return;
		}

		// 角色为空
		if (pc == null) {
			return;
		}

		// 可以使用
		if (StopUsePotion.stopUsePotion(pc)) {
			UseHealingPotion(pc, 55, 197); // 基本加血量与动画ID
			pc.getInventory().removeItem(item, 1); // 删除道具
		}
	}

	/**
	 * 执行效果
	 * 
	 * @param pc
	 *            角色
	 * @param healHp
	 *            增加血量
	 * @param gfxid
	 *            动画ID
	 */
	private static void UseHealingPotion(final L1PcInstance pc, int healHp,
			final int gfxid) {

		// 解除绝对屏障效果
		RemoveSkillEffect.RemoveAbsoluteBarrierEffect(pc);

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画
		pc.sendPackets(new S_ServerMessage(77)); // \f1你觉得舒服多了。
		healHp *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机

		// 污浊之水 - 效果减半
		if (pc.hasSkillEffect(POLLUTE_WATER)) {
			healHp /= 2;
		}

		pc.setCurrentHp(pc.getCurrentHp() + healHp); // 为PC增加HP
	}
}
