package lineage.item.etcitem;

import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CurseBlind;
import lineage.console.connector.UniversalUseItem;

/**
 * 药水效果 (黑色药水)
 * 
 * @author jrwz
 */
public class UsePotion_Blind implements UniversalUseItem {

	@Override
	public void useItem(final L1PcInstance pc, final L1ItemInstance item, final int itemId, final int effect, final int time, final int gfxid) {

		// 删除重复的技能效果
		pc.delRepeatSkillEffect(CURSE_BLIND); // 法师魔法 (闇盲咒术)
		pc.delRepeatSkillEffect(DARKNESS); // 法师魔法 (黑闇之影)

		// 漂浮之眼肉
		if (pc.hasSkillEffect(STATUS_FLOATING_EYE)) {
			pc.sendPackets(new S_CurseBlind(2)); // 周边物件可见
		}
		else {
			pc.sendPackets(new S_CurseBlind(1)); // 自己
		}
		pc.setSkillEffect(CURSE_BLIND, time * 1000); // 给予16秒效果
	}

}
