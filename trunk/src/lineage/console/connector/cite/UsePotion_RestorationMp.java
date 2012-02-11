package lineage.console.connector.cite;

import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.UniversalUseItem;
import lineage.console.delete.DeleteSkillEffect;

/**
 * 药水效果 (恢复魔量)
 * 
 * @author jrwz
 */
public class UsePotion_RestorationMp implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		DeleteSkillEffect.DeleteEffectOfAbsoluteBarrier(pc); // 删除绝对屏障效果
		DeleteSkillEffect.DeleteEffectOfRepeat(pc, STATUS_BLUE_POTION); // 删除重复的蓝水效果

		pc.sendPackets(new S_SkillIconGFX(34, time)); // 发送状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(1007)); // 你感觉到魔力恢复速度加快。
		pc.setSkillEffect(STATUS_BLUE_POTION, time * 1000); // 给予蓝水时间 (秒)
	}

}
