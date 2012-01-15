package l1j.server.data.item_etcitem.mp;

import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;

import l1j.server.data.executor.ItemExecutor;
import l1j.server.data.executor.remove.RemoveSkillEffect;
import l1j.server.data.executor.stop.StopUsePotion;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;

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
			final int itemId = item.getItemId();
			this.useBluePotion(pc, itemId);
			pc.getInventory().removeItem(item, 1);
		}
	}

	/**
	 * 执行效果
	 * 
	 * @param pc
	 *            角色
	 * @param item_id
	 *            道具ID
	 */
	private void useBluePotion(final L1PcInstance pc, final int item_id) {

		// 解除绝对屏障效果
		RemoveSkillEffect.RemoveAbsoluteBarrierEffect(pc);

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

		// 删除重复的蓝水状态
		if (pc.hasSkillEffect(STATUS_BLUE_POTION)) {
			pc.killSkillEffectTimer(STATUS_BLUE_POTION);
		}

		pc.sendPackets(new S_SkillIconGFX(34, time)); // 发送状态图示
		pc.setSkillEffect(STATUS_BLUE_POTION, time * 1000); // 给予蓝水时间 (秒)
		pc.sendPackets(new S_SkillSound(pc.getId(), 190));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190));
		pc.sendPackets(new S_ServerMessage(1007)); // 你感觉到魔力恢复速度加快。
	}
}
