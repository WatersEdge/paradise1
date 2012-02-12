package lineage.console.connector.cite;

import static l1j.server.server.model.skill.L1SkillId.BLOODLUST;
import static l1j.server.server.model.skill.L1SkillId.HOLY_WALK;
import static l1j.server.server.model.skill.L1SkillId.MOVING_ACCELERATION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static l1j.server.server.model.skill.L1SkillId.WIND_WALK;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.UniversalUseItem;

/**
 * 加速药水效果 (二段:勇敢药水)
 * 
 * @author jrwz
 */
public class UseSpeedPotion_2_Brave implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		// 删除重复的二段加速效果
		pc.delRepeatSkillEffect(STATUS_BRAVE); // 勇敢药水类 1.33倍
		pc.delRepeatSkillEffect(STATUS_ELFBRAVE); // 精灵饼干 1.15倍
		pc.delRepeatSkillEffect(HOLY_WALK); // 神圣疾走 移速1.33倍
		pc.delRepeatSkillEffect(MOVING_ACCELERATION); // 行走加速 移速1.33倍
		pc.delRepeatSkillEffect(WIND_WALK); // 风之疾走 移速1.33倍
		pc.delRepeatSkillEffect(BLOODLUST); // 血之渴望 攻速1.33倍
		pc.delRepeatSkillEffect(STATUS_BRAVE2); // 超级加速 2.66倍

		// 给予状态 && 效果
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_SkillBrave(pc.getId(), 1, time)); // 加速效果与时间 (自己看得到)
		pc.broadcastPacket(new S_SkillBrave(pc.getId(), 1, 0)); // 加速效果与时间 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_BRAVE, time * 1000); // 给予二段加速时间 (秒)
		pc.setBraveSpeed(1); // 设置勇水速度
	}

}
