package l1j.server.server.model.item.etcitem.other;

import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_LIFE;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 生命之魔眼 - 47023
 * 
 * @author jrwz
 */
public class DragonEye_Life extends ItemExecutor {

	public static ItemExecutor get() {
		return new DragonEye_Life();
	}

	private DragonEye_Life() {
	}

	/**
	 * 道具执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            对象
	 * @param item
	 *            道具
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {

		if (pc.hasSkillEffect(DECAY_POTION)) {
			pc.sendPackets(new S_ServerMessage(698)); // 喉咙灼热，无法喝东西。
			return;
		}

		final int skillId = EFFECT_MAGIC_EYE_OF_LIFE;

		pc.deleteRepeatedSkills(skillId);

		final int time = 600;
		final int gfxid = 7678;

		if (!pc.hasSkillEffect(skillId)) {
			pc.setSkillEffect(skillId, time * 1000);
			pc.addDmgup(2); // 额外攻击点数 +2
			// 魔法伤害减免 +50
			// 魔法暴击率 +1
			// 防护中毒状态

			pc.addDodge((byte) 1); // 闪避率 + 10%
			// 更新闪避率显示
			pc.sendPackets(new S_PacketBox(88, pc.getDodge()));
			pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid));
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
