package l1j.server.server.model.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import l1j.server.console.UniversalUseItem;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;

/**
 * 变身效果 (变形卷轴、变形药水等直接变身道具)
 * 
 * @author jrwz
 */
public class UsePolyOther implements UniversalUseItem {

	@Override
	public void useItem(final L1PcInstance pc, final L1ItemInstance item, final int itemId, final int effect, final int time, final int gfxid) {

		// 不能变身的状态
		// 取回觉醒技能ID
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身。
			return;
		}

		L1PolyMorph.doPoly(pc, effect, time, L1PolyMorph.MORPH_BY_ITEMMAGIC);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}

}
