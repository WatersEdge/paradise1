package l1j.server.server.model.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.STATUS_RIBRAVE;
import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 加速药水效果 (二段:生命之树果实)
 * 
 * @author jrwz
 */
public class UseSpeedPotion_2_RiBrave implements UniversalUseItem {

	@Override
	public void useItem(final L1PcInstance pc, final L1ItemInstance item, final int itemId, final int effect, final int time, final int gfxid) {

		// 删除状态不明
		pc.setSkillEffect(STATUS_RIBRAVE, time * 1000); // 给予二段加速时间 (秒)
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
	}

}
