package lineage.item.etcitem.other;

import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_FAFURION;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.executor.ItemExecutor;

/**
 * 水龙之魔眼 - 47018
 * 
 * @author jrwz
 */
public class DragonEye_Water extends ItemExecutor {

	public static ItemExecutor get() {
		return new DragonEye_Water();
	}

	private DragonEye_Water() {
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

		final int skillId = EFFECT_MAGIC_EYE_OF_FAFURION;

		pc.deleteRepeatedSkills(skillId);

		final int time = 600;
		final int gfxid = 7672;

		if (!pc.hasSkillEffect(skillId)) {
			pc.setSkillEffect(skillId, time * 1000);
			pc.add_regist_freeze(3); // 寒冰耐性 +3
			// 魔法伤害减免 +50
			pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid));
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
