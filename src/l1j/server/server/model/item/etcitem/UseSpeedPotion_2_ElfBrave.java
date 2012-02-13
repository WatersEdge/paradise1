package l1j.server.server.model.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static l1j.server.server.model.skill.L1SkillId.WIND_WALK;
import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 加速药水效果 (二段:精灵饼干)
 * 
 * @author jrwz
 */
public class UseSpeedPotion_2_ElfBrave implements UniversalUseItem {

	@Override
	public void useItem(final L1PcInstance pc, final L1ItemInstance item, final int itemId, final int effect, final int time, final int gfxid) {

		// 删除重复的二段加速效果
		pc.delRepeatSkillEffect(STATUS_BRAVE); // 勇敢药水类 1.33倍
		pc.delRepeatSkillEffect(STATUS_ELFBRAVE); // 精灵饼干 1.15倍
		pc.delRepeatSkillEffect(WIND_WALK); // 风之疾走 移速1.33倍
		pc.delRepeatSkillEffect(STATUS_BRAVE2); // 超级加速 2.66倍

		// 给予状态 && 效果
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_SkillBrave(pc.getId(), 3, time)); // 加速效果与时间 (自己看得到)
		pc.broadcastPacket(new S_SkillBrave(pc.getId(), 3, 0)); // 加速效果与时间 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_ELFBRAVE, time * 1000); // 给予二段加速时间 (秒)
		pc.setBraveSpeed(3); // 设置饼干速度
	}

}
