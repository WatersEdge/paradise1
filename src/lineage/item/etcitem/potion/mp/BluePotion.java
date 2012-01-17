package lineage.item.etcitem.potion.mp;

import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.executor.ItemExecutor;
import lineage.console.remove.RemoveSkillEffect;

/**
 * 蓝色药水 40015<br>
 * 受祝福的 蓝色药水 140015<br>
 * 智慧货币 40736<br>
 * 福利蓝色药水 49306<br>
 * 
 * @author jrwz
 */
public class BluePotion extends ItemExecutor {

	private BluePotion() {
	}

	public static ItemExecutor get() {
		return new BluePotion();
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

		// 取回道具ID
		final int itemId = item.getItemId();

		// 执行效果
		this.useBluePotion(pc, itemId);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}

	/**
	 * 执行效果
	 * 
	 * @param pc
	 *            对象
	 * @param item_id
	 *            道具ID
	 */
	private void useBluePotion(final L1PcInstance pc, final int item_id) {

		// 删除绝对屏障效果
		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc);

		// 删除重复的蓝水效果
		RemoveSkillEffect.removeStatusBluePotion(pc);

		// 给予效果的时间
		int time = 0;

		switch (item_id) {
		case 40015: // 蓝色药水
		case 40736: // 智慧货币
			time = 600;
			break;

		case 140015: // 受祝福的 蓝色药水
			time = 700;
			break;

		case 49306: // 福利蓝色药水
			time = 2400;
			break;
		}

		pc.sendPackets(new S_SkillIconGFX(34, time)); // 发送状态图示
		pc.setSkillEffect(STATUS_BLUE_POTION, time * 1000); // 给予蓝水时间 (秒)
		pc.sendPackets(new S_SkillSound(pc.getId(), 190)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(1007)); // 你感觉到魔力恢复速度加快。
	}
}
