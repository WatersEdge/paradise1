package lineage.console.connector.cite;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.UniversalUseItem;
import lineage.console.delete.DeleteSkillEffect;

/**
 * 药水效果 (增加魔量)
 * 
 * @author jrwz
 */
public class UsePotion_AddMp implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		DeleteSkillEffect.DeleteEffectOfAbsoluteBarrier(pc); // 删除绝对屏障效果

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(338, "$1084")); // 你的 魔力 渐渐恢复。
		effect *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机加魔量
		pc.setCurrentMp(pc.getCurrentMp() + effect); // 为角色增加MP
	}

}
