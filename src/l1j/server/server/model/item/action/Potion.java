/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package l1j.server.server.model.item.action;

import static l1j.server.server.model.skill.L1SkillId.BLOODLUST;
import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.ENTANGLE;
import static l1j.server.server.model.skill.L1SkillId.GREATER_HASTE;
import static l1j.server.server.model.skill.L1SkillId.HASTE;
import static l1j.server.server.model.skill.L1SkillId.HOLY_WALK;
import static l1j.server.server.model.skill.L1SkillId.MASS_SLOW;
import static l1j.server.server.model.skill.L1SkillId.MOVING_ACCELERATION;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.SLOW;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_RIBRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_THIRD_SPEED;
import static l1j.server.server.model.skill.L1SkillId.STATUS_UNDERWATER_BREATH;
import static l1j.server.server.model.skill.L1SkillId.STATUS_WISDOM_POTION;
import static l1j.server.server.model.skill.L1SkillId.WIND_WALK;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CurseBlind;
import l1j.server.server.serverpackets.S_Liquor;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillIconWisdomPotion;
import l1j.server.server.serverpackets.S_SkillSound;
import lineage.console.remove.RemoveSkillEffect;

/**
 * 药水类使用效果控制项
 * 
 * @author jrwz
 */
public class Potion {

	/**
	 * 使用一段加速药水 (绿色药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void useGreenPotion(L1PcInstance pc, int time) {

		// 装备加速道具时
		if (pc.getHasteItemEquipped() > 0) {
			return;
		}

		// 解除醉酒状态
		pc.setDrink(false);

		// 删除重复的一段加速状态
		RemoveSkillEffect.removeStatusGreenPotion(pc, STATUS_HASTE); // 一段加速
		RemoveSkillEffect.removeStatusGreenPotion(pc, HASTE); // 加速术
		RemoveSkillEffect.removeStatusGreenPotion(pc, GREATER_HASTE); // // 强力加速术

		pc.sendPackets(new S_SkillSound(pc.getId(), 191)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 191)); // 效果动画 (同画面的其他人看得到)

		// 删除重复的缓速状态
		if (pc.getMoveSpeed() == 2) {
			RemoveSkillEffect.removeStatusSlow(pc, SLOW); // 缓速术
			RemoveSkillEffect.removeStatusSlow(pc, MASS_SLOW); // 集体缓速术
			RemoveSkillEffect.removeStatusSlow(pc, ENTANGLE); // 地面障碍
		}
		else {
			pc.sendPackets(new S_SkillHaste(pc.getId(), 1, time)); // 加速效果与时间 (自己看得到)
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 1, 0)); // 加速效果与时间 (同画面的其他人看得到)
			pc.setSkillEffect(STATUS_HASTE, time * 1000); // 给予一段加速时间 (秒)
			pc.setMoveSpeed(1); // 设置为加速状态
		}
	}

	/**
	 * 使用二段加速药水 (勇敢药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void Brave(L1PcInstance pc, int time) {

		// 删除重复的二段加速效果
		RemoveSkillEffect.removeRepeat(pc, STATUS_BRAVE); // 勇敢药水类 1.33倍
		RemoveSkillEffect.removeRepeat(pc, STATUS_ELFBRAVE); // 精灵饼干 1.15倍
		RemoveSkillEffect.removeRepeat(pc, HOLY_WALK); // 神圣疾走 移速1.33倍
		RemoveSkillEffect.removeRepeat(pc, MOVING_ACCELERATION); // 行走加速 移速1.33倍
		RemoveSkillEffect.removeRepeat(pc, WIND_WALK); // 风之疾走 移速1.33倍
		RemoveSkillEffect.removeRepeat(pc, BLOODLUST); // 血之渴望 攻速1.33倍
		RemoveSkillEffect.removeRepeat(pc, STATUS_BRAVE2); // 超级加速 2.66倍

		// 给予状态 && 效果
		pc.sendPackets(new S_SkillSound(pc.getId(), 751)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 751)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_SkillBrave(pc.getId(), 1, time)); // 加速效果与时间 (自己看得到)
		pc.broadcastPacket(new S_SkillBrave(pc.getId(), 1, 0)); // 加速效果与时间 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_BRAVE, time * 1000); // 给予二段加速时间 (秒)
		pc.setBraveSpeed(1); // 设置勇水速度
	}

	/**
	 * 使用二段加速药水 (精灵饼干)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void ElfBrave(L1PcInstance pc, int time) {

		// 删除重复的二段加速效果
		RemoveSkillEffect.removeRepeat(pc, STATUS_BRAVE); // 勇敢药水类 1.33倍
		RemoveSkillEffect.removeRepeat(pc, STATUS_ELFBRAVE); // 精灵饼干 1.15倍
		RemoveSkillEffect.removeRepeat(pc, WIND_WALK); // 风之疾走 移速1.33倍
		RemoveSkillEffect.removeRepeat(pc, STATUS_BRAVE2); // 超级加速 2.66倍

		// 给予状态 && 效果
		pc.sendPackets(new S_SkillSound(pc.getId(), 751)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 751)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_SkillBrave(pc.getId(), 3, time)); // 加速效果与时间 (自己看得到)
		pc.broadcastPacket(new S_SkillBrave(pc.getId(), 3, 0)); // 加速效果与时间 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_ELFBRAVE, time * 1000); // 给予二段加速时间 (秒)
		pc.setBraveSpeed(3); // 设置饼干速度
	}

	/**
	 * 使用二段加速药水 (生命之树果实)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void RiBrave(L1PcInstance pc, int time) {

		// 删除状态不明
		pc.setSkillEffect(STATUS_RIBRAVE, time * 1000); // 给予二段加速时间 (秒)
		pc.sendPackets(new S_SkillSound(pc.getId(), 7110)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 7110)); // 效果动画 (同画面的其他人看得到)
	}

	/**
	 * 使用三段加速药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void ThirdSpeed(L1PcInstance pc, int time) {

		RemoveSkillEffect.removeRepeat(pc, STATUS_THIRD_SPEED); // 删除重复的三段加速效果

		pc.sendPackets(new S_SkillSound(pc.getId(), 8031)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 8031)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_Liquor(pc.getId(), 8)); // 人物 * 1.15
		pc.broadcastPacket(new S_Liquor(pc.getId(), 8)); // 人物 * 1.15
		pc.sendPackets(new S_ServerMessage(1065)); // 将发生神秘的奇迹力量。
		pc.setSkillEffect(STATUS_THIRD_SPEED, time * 1000); // 给予三段加速时间 (秒)
	}

	/**
	 * 使用治愈类药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param healHp
	 *            增加血量
	 * @param gfxid
	 *            动画ID
	 */
	public static void UseHeallingPotion(L1PcInstance pc, int healHp, int gfxid) {

		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc); // 删除绝对屏障效果

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(77)); // \f1你觉得舒服多了。
		healHp *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机加血量

		// 污浊之水 - 效果减半
		if (pc.hasSkillEffect(POLLUTE_WATER)) {
			healHp /= 2;
		}
		pc.setCurrentHp(pc.getCurrentHp() + healHp); // 为角色增加HP
	}

	/**
	 * 使用加魔类药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param newMp
	 *            增加魔量
	 */
	public static void UseMpPotion(L1PcInstance pc, int newMp) {

		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc); // 删除绝对屏障效果

		pc.sendPackets(new S_SkillSound(pc.getId(), 190)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(338, "$1084")); // 你的 魔力 渐渐恢复。
		newMp *= ((new java.util.Random()).nextGaussian() / 5.0D) + 1.0D; // 随机加魔量
		pc.setCurrentMp(pc.getCurrentMp() + newMp); // 为角色增加MP
	}

	/**
	 * 恢复魔力药水 (蓝色药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void useBluePotion(L1PcInstance pc, int time) {

		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc); // 删除绝对屏障效果
		RemoveSkillEffect.removeRepeat(pc, STATUS_BLUE_POTION); // 删除重复的蓝水效果

		pc.sendPackets(new S_SkillIconGFX(34, time)); // 发送状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), 190)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(1007)); // 你感觉到魔力恢复速度加快。
		pc.setSkillEffect(STATUS_BLUE_POTION, time * 1000); // 给予蓝水时间 (秒)
	}

	/**
	 * 增加魔攻药水 (智慧药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void useWisdomPotion(L1PcInstance pc, int time) {

		RemoveSkillEffect.removeRepeat(pc, STATUS_WISDOM_POTION); // 删除重复的智慧药水效果

		pc.sendPackets(new S_SkillIconWisdomPotion((time / 4))); // 状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), 750)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 750)); // 效果动画 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_WISDOM_POTION, time * 1000); // 给予智慧药水时间 (秒)
		pc.addSp(2); // 魔攻 + 2
	}

	/**
	 * 可以在水中呼吸的药水 (伊娃的祝福)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	public static void useBlessOfEva(L1PcInstance pc, int time) {

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
		pc.sendPackets(new S_SkillSound(pc.getId(), 190)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190)); // 效果动画 (同画面的其他人看得到)
		pc.setSkillEffect(STATUS_UNDERWATER_BREATH, time * 1000); // 给予时间 (秒)
	}

	/**
	 * 使用黑色药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            效果时间
	 */
	public static void useBlindPotion(L1PcInstance pc, int time) {

		// 删除重复的技能效果
		RemoveSkillEffect.removeRepeat(pc, CURSE_BLIND); // 法师魔法 (闇盲咒术)
		RemoveSkillEffect.removeRepeat(pc, DARKNESS); // 法师魔法 (黑闇之影)

		// 漂浮之眼肉
		if (pc.hasSkillEffect(STATUS_FLOATING_EYE)) {
			pc.sendPackets(new S_CurseBlind(2));
		}
		else {
			pc.sendPackets(new S_CurseBlind(1));
		}
		pc.setSkillEffect(CURSE_BLIND, time * 1000); // 给予16秒效果
	}
}
