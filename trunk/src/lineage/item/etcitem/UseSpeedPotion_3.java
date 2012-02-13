package lineage.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.STATUS_THIRD_SPEED;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Liquor;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.UniversalUseItem;

/**
 * 加速药水效果 (三段) (巧克力蛋糕)
 * 
 * @author jrwz
 */
public class UseSpeedPotion_3 implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		pc.delRepeatSkillEffect(STATUS_THIRD_SPEED); // 删除重复的三段加速效果

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_Liquor(pc.getId(), 8)); // 人物 * 1.15
		pc.broadcastPacket(new S_Liquor(pc.getId(), 8)); // 人物 * 1.15
		pc.sendPackets(new S_ServerMessage(1065)); // 将发生神秘的奇迹力量。
		pc.setSkillEffect(STATUS_THIRD_SPEED, time * 1000); // 给予三段加速时间 (秒)
	}

}
