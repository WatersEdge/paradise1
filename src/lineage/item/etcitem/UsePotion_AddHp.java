package lineage.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.UniversalUseItem;

/**
 * 药水效果 (治愈类)
 * 
 * @author jrwz
 */
public class UsePotion_AddHp implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		pc.delAbsoluteBarrier(); // 删除绝对屏障效果

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(77)); // \f1你觉得舒服多了。
		effect *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机加血量

		// 污浊之水 - 效果减半
		if (pc.hasSkillEffect(POLLUTE_WATER)) {
			effect /= 2;
		}
		pc.setCurrentHp(pc.getCurrentHp() + effect); // 为角色增加HP
	}

}
