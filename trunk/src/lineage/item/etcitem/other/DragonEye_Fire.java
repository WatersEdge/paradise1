package lineage.item.etcitem.other;

import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_EYE_OF_VALAKAS;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.delete.DeleteSkillEffect;
import lineage.console.executor.ItemExecutor;
import lineage.console.utils.CheckUtil;

/**
 * 火龙之魔眼 - 47020
 * 
 * @author jrwz
 */
public class DragonEye_Fire extends ItemExecutor {

	private DragonEye_Fire() {
	}

	public static ItemExecutor get() {
		return new DragonEye_Fire();
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

		if (!CheckUtil.checkUseItemByDecay(pc)) {
			return;
		}

		final int skillId = EFFECT_MAGIC_EYE_OF_VALAKAS;

		DeleteSkillEffect.deleteRepeatedSkills(pc, skillId);

		final int time = 600;
		final int gfxid = 7674;

		if (!pc.hasSkillEffect(skillId)) {
			pc.setSkillEffect(skillId, time * 1000);
			pc.addRegistStun(3); // 昏迷耐性 +3
			pc.addDmgup(2); // 额外攻击点数 +2
			pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid));
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}
}
