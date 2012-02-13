package lineage.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.STATUS_WISDOM_POTION;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillIconWisdomPotion;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.UniversalUseItem;

/**
 * 药水效果 (增加魔攻)
 * 
 * @author jrwz
 */
public class UsePotion_Wisdom implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		pc.delRepeatSkillEffect(STATUS_WISDOM_POTION); // 删除重复的智慧药水效果

		pc.sendPackets(new S_SkillIconWisdomPotion((time / 4))); // 状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_WISDOM_POTION, time * 1000); // 给予智慧药水时间 (秒)
		pc.addSp(2); // 魔攻 + 2
	}

}
