package lineage.console.connector.cite;

import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_UNDERWATER_BREATH;
import static l1j.server.server.model.skill.L1SkillId.STATUS_WISDOM_POTION;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CurseBlind;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillIconWisdomPotion;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.connector.ConnectorPotion;
import lineage.console.delete.DeleteSkillEffect;

/**
 * 实例化 (接口引用:药水类)
 * 
 * @author jrwz
 */
public class CiteConnectorPotion implements ConnectorPotion {

	// 加魔类药水 (月饼)
	@Override
	public final void useAddMpPotion(final L1PcInstance pc, int newMp, final int gfxid) {

		DeleteSkillEffect.DeleteEffectOfAbsoluteBarrier(pc); // 删除绝对屏障效果

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(338, "$1084")); // 你的 魔力 渐渐恢复。
		newMp *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机加魔量
		pc.setCurrentMp(pc.getCurrentMp() + newMp); // 为角色增加MP
	}

	// 恢复魔力药水 (蓝色药水)
	@Override
	public final void useBluePotion(final L1PcInstance pc, final int time, final int gfxid) {

		DeleteSkillEffect.DeleteEffectOfAbsoluteBarrier(pc); // 删除绝对屏障效果
		DeleteSkillEffect.DeleteEffectOfRepeat(pc, STATUS_BLUE_POTION); // 删除重复的蓝水效果

		pc.sendPackets(new S_SkillIconGFX(34, time)); // 发送状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(1007)); // 你感觉到魔力恢复速度加快。
		pc.setSkillEffect(STATUS_BLUE_POTION, time * 1000); // 给予蓝水时间 (秒)
	}

	// 增加魔攻药水 (智慧药水)
	@Override
	public final void useWisdomPotion(final L1PcInstance pc, final int time, final int gfxid) {

		DeleteSkillEffect.DeleteEffectOfRepeat(pc, STATUS_WISDOM_POTION); // 删除重复的智慧药水效果

		pc.sendPackets(new S_SkillIconWisdomPotion((time / 4))); // 状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_WISDOM_POTION, time * 1000); // 给予智慧药水时间 (秒)
		pc.addSp(2); // 魔攻 + 2
	}

	// 可以在水中呼吸的药水 (伊娃的祝福)
	@Override
	public final void useBlessOfEvaPotion(final L1PcInstance pc, int time, final int gfxid) {

		// 持续时间可累加
		if (pc.hasSkillEffect(STATUS_UNDERWATER_BREATH)) {
			int timeSec = pc.getSkillEffectTimeSec(STATUS_UNDERWATER_BREATH);
			time += timeSec;
			if (time > 7200) {
				time = 7200;
			}
			pc.killSkillEffectTimer(STATUS_UNDERWATER_BREATH);
		}
		pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), time)); // 状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_UNDERWATER_BREATH, time * 1000); // 给予时间 (秒)
	}

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
