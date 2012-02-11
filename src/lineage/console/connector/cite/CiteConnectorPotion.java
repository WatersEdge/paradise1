package lineage.console.connector.cite;

import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CurseBlind;
import lineage.console.connector.ConnectorPotion;
import lineage.console.delete.DeleteSkillEffect;

/**
 * 实例化 (接口引用:药水类)
 * 
 * @author jrwz
 */
public class CiteConnectorPotion implements ConnectorPotion {

	// 黑色药水 (失明药水)
	@Override
	public final void useBlindPotion(final L1PcInstance pc, final int time) {

		// 删除重复的技能效果
		DeleteSkillEffect.DeleteEffectOfRepeat(pc, CURSE_BLIND); // 法师魔法 (闇盲咒术)
		DeleteSkillEffect.DeleteEffectOfRepeat(pc, DARKNESS); // 法师魔法 (黑闇之影)

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
