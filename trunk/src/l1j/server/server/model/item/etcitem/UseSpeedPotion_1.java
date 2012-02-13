package l1j.server.server.model.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.ENTANGLE;
import static l1j.server.server.model.skill.L1SkillId.GREATER_HASTE;
import static l1j.server.server.model.skill.L1SkillId.HASTE;
import static l1j.server.server.model.skill.L1SkillId.MASS_SLOW;
import static l1j.server.server.model.skill.L1SkillId.SLOW;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 加速药水效果 (一段)
 * 
 * @author jrwz
 */
public class UseSpeedPotion_1 implements UniversalUseItem {

	@Override
	public void useItem(final L1PcInstance pc, final L1ItemInstance item, final int itemId, final int effect, final int time, final int gfxid) {

		// 如果正在使用加速装备时停止
		if (pc.getHasteItemEquipped() > 0) {
			return;
		}

		// 解除醉酒状态
		pc.setDrink(false);

		// 删除重复的一段加速状态
		pc.delEffectOfGreenPotion(STATUS_HASTE); // 一段加速
		pc.delEffectOfGreenPotion(HASTE); // 加速术
		pc.delEffectOfGreenPotion(GREATER_HASTE); // // 强力加速术
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)

		// 删除重复的缓速状态 (相互抵消、不加速)
		if (pc.getMoveSpeed() == 2) {
			pc.delEffectOfSlow(SLOW); // 缓速术
			pc.delEffectOfSlow(MASS_SLOW); // 集体缓速术
			pc.delEffectOfSlow(ENTANGLE); // 地面障碍
		}
		else {
			pc.sendPackets(new S_SkillHaste(pc.getId(), 1, time)); // 加速效果与时间 (自己看得到)
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 1, 0)); // 加速效果与时间 (同画面的其他人看得到)
			pc.setSkillEffect(STATUS_HASTE, time * 1000); // 给予一段加速时间 (秒)
			pc.setMoveSpeed(1); // 设置为加速状态
		}
	}

}
