package lineage.item.etcitem.potion.hp;

import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.executor.ItemExecutor;
import lineage.console.remove.RemoveSkillEffect;

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
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {

		// 基本加血量与动画ID
		useHealingPotion(pc, 55, 197);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}

	/**
	 * 执行效果
	 * 
	 * @param pc
	 *            执行对象
	 * @param healHp
	 *            增加血量
	 * @param gfxid
	 *            动画编号
	 */
	private static void useHealingPotion(final L1PcInstance pc, int healHp, final int gfxid) {

		// 删除绝对屏障效果
		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc);

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(77)); // \f1你觉得舒服多了。
		healHp *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机加血量

		// 污浊之水 - 效果减半
		if (pc.hasSkillEffect(POLLUTE_WATER)) {
			healHp /= 2;
		}

		pc.setCurrentHp(pc.getCurrentHp() + healHp); // 为对象增加HP
	}
}
