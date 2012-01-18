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

import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FLOATING_EYE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_RIBRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_THIRD_SPEED;
import static l1j.server.server.model.skill.L1SkillId.STATUS_UNDERWATER_BREATH;
import static l1j.server.server.model.skill.L1SkillId.STATUS_WISDOM_POTION;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.identity.L1ItemId;
import l1j.server.server.serverpackets.S_CurseBlind;
import l1j.server.server.serverpackets.S_Liquor;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillIconWisdomPotion;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.utils.Random;
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
		RemoveSkillEffect.removeStatusGreenPotion(pc);

		pc.sendPackets(new S_SkillSound(pc.getId(), 191)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 191)); // 效果动画 (同画面的其他人看得到)

		// 删除重复的缓速状态
		if (pc.getMoveSpeed() == 2) {
			RemoveSkillEffect.removeStatusSlow(pc);
		}
		else {
			pc.sendPackets(new S_SkillHaste(pc.getId(), 1, time)); // 加速效果与时间 (自己看得到)
			pc.broadcastPacket(new S_SkillHaste(pc.getId(), 1, 0)); // 加速效果与时间 (同画面的其他人看得到)
			pc.setMoveSpeed(1); // 设置为加速状态
			pc.setSkillEffect(STATUS_HASTE, time * 1000); // 给予一段加速效果
		}
	}

	/**
	 * 使用二段加速药水 (勇敢药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            道具
	 * @param item_id
	 *            道具ID
	 */
	public static void Brave(L1PcInstance pc, L1ItemInstance item, int item_id) {

		int time = 0;

		// 判断持续时间 && 使用类型
		/* 勇敢药水类 */
		if (item_id == L1ItemId.POTION_OF_EMOTION_BRAVERY || item_id == L1ItemId.B_POTION_OF_EMOTION_BRAVERY || item_id == L1ItemId.POTION_OF_REINFORCED_CASE
				|| item_id == L1ItemId.W_POTION_OF_EMOTION_BRAVERY || item_id == L1ItemId.DEVILS_BLOOD || item_id == L1ItemId.COIN_OF_REPUTATION) {
			if (item_id == L1ItemId.POTION_OF_EMOTION_BRAVERY) { // 勇敢药水
				time = 300;
			}
			else if (item_id == L1ItemId.B_POTION_OF_EMOTION_BRAVERY) { // 受祝福的勇敢药水
				time = 350;
			}
			else if (item_id == L1ItemId.POTION_OF_REINFORCED_CASE) { // 强化勇气的药水
				time = 1800;
			}
			else if (item_id == L1ItemId.DEVILS_BLOOD) { // 恶魔之血
				time = 600;
			}
			else if (item_id == L1ItemId.COIN_OF_REPUTATION) { // 名誉货币
				time = 600;
			}
			else if (item_id == L1ItemId.W_POTION_OF_EMOTION_BRAVERY) { // 福利勇敢药水
				time = 1200;
			}
			buff_brave(pc, STATUS_BRAVE, (byte) 1, time); // 给予勇敢药水效果
			pc.getInventory().removeItem(item, 1);
		}

		/* 精灵饼干 & 祝福的精灵饼干 */
		else if (item_id == L1ItemId.ELVEN_WAFER || item_id == L1ItemId.B_ELVEN_WAFER || item_id == L1ItemId.W_POTION_OF_FOREST) {
			if (item_id == L1ItemId.ELVEN_WAFER) { // 精灵饼干
				time = 480;
			}
			else if (item_id == L1ItemId.B_ELVEN_WAFER) { // 祝福的精灵饼干
				time = 700;
			}
			else if (item_id == L1ItemId.W_POTION_OF_FOREST) { // 福利森林药水
				time = 1920;
			}
			buff_brave(pc, STATUS_ELFBRAVE, (byte) 3, time); // 给予精灵饼干效果
			pc.getInventory().removeItem(item, 1);
		}

		/* 生命之树果实 */
		else if (item_id == L1ItemId.FORBIDDEN_FRUIT) { // 生命之树果实
			time = 480;
			pc.setSkillEffect(STATUS_RIBRAVE, time * 1000);
			pc.sendPackets(new S_SkillSound(pc.getId(), 7110));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), 7110));
			pc.getInventory().removeItem(item, 1);
		}
	}

	/**
	 * 使用三段加速药水
	 * 
	 * @param pc
	 * @param item
	 * @param time
	 */
	public static void ThirdSpeed(L1PcInstance pc, L1ItemInstance item, int time) {

		pc.setSkillEffect(STATUS_THIRD_SPEED, time * 1000);

		pc.sendPackets(new S_SkillSound(pc.getId(), 8031));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 8031));
		pc.sendPackets(new S_Liquor(pc.getId(), 8)); // 人物 * 1.15
		pc.broadcastPacket(new S_Liquor(pc.getId(), 8)); // 人物 * 1.15
		pc.sendPackets(new S_ServerMessage(1065)); // 将发生神秘的奇迹力量。
		pc.getInventory().removeItem(item, 1);
	}

	/**
	 * 二段加速状态 (消除重复状态)
	 * 
	 * @param pc
	 * @param skillId
	 * @param type
	 * @param timeMillis
	 */
	private static void buff_brave(L1PcInstance pc, int skillId, byte type, int timeMillis) {

		// 给予状态 && 效果
		pc.setSkillEffect(skillId, timeMillis * 1000);
		pc.sendPackets(new S_SkillSound(pc.getId(), 751));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 751));
		pc.sendPackets(new S_SkillBrave(pc.getId(), type, timeMillis));
		pc.broadcastPacket(new S_SkillBrave(pc.getId(), type, 0));
		pc.setBraveSpeed(type);
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

		// 删除绝对屏障效果
		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc);

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
	 * 使用MP药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 * @param mp
	 * @param i
	 */
	public static void UseMpPotion(L1PcInstance pc, L1ItemInstance item, int mp, int i) {

		// 删除绝对屏障效果
		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc);

		pc.sendPackets(new S_SkillSound(pc.getId(), 190));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190));
		pc.sendPackets(new S_ServerMessage(338, "$1084")); // 你的 魔力 渐渐恢复。
		int newMp = 0;
		if (i > 0) {
			newMp = Random.nextInt(i, mp);
		}
		else {
			newMp = mp;
		}
		pc.setCurrentMp(pc.getCurrentMp() + newMp);
		pc.getInventory().removeItem(item, 1);
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

		// 删除绝对屏障效果
		RemoveSkillEffect.removeAbsoluteBarrierEffect(pc);

		// 删除重复的蓝水效果
		RemoveSkillEffect.removeStatusBluePotion(pc);

		pc.sendPackets(new S_SkillIconGFX(34, time)); // 发送状态图示
		pc.setSkillEffect(STATUS_BLUE_POTION, time * 1000); // 给予蓝水时间 (秒)
		pc.sendPackets(new S_SkillSound(pc.getId(), 190)); // 效果动画 (自己看得到)
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190)); // 效果动画 (同画面的其他人看得到)
		pc.sendPackets(new S_ServerMessage(1007)); // 你感觉到魔力恢复速度加快。
	}

	/**
	 * 增加魔攻药水 (智慧药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 * @param item_id
	 */
	public static void useWisdomPotion(L1PcInstance pc, L1ItemInstance item, int item_id) {

		// 给予效果的时间
		int time = 0;

		// 慎重药水
		if (item_id == L1ItemId.POTION_OF_EMOTION_WISDOM) {
			time = 300;
		}

		// 受祝福的 慎重药水
		else if (item_id == L1ItemId.B_POTION_OF_EMOTION_WISDOM) {
			time = 360;
		}

		// 福利慎重药水
		else if (item_id == 49307) {
			time = 1000;
		}

		// 智慧药水状态
		if (!pc.hasSkillEffect(STATUS_WISDOM_POTION)) {
			pc.addSp(2);
		}
		else {
			pc.killSkillEffectTimer(STATUS_WISDOM_POTION);
		}

		pc.sendPackets(new S_SkillIconWisdomPotion((time / 4))); // 状态图示
		pc.sendPackets(new S_SkillSound(pc.getId(), 750));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 750));
		pc.setSkillEffect(STATUS_WISDOM_POTION, time * 1000);
		pc.getInventory().removeItem(item, 1);
	}

	/**
	 * 可以在水中呼吸的药水 (伊娃的祝福)
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 * @param item_id
	 */
	public static void useBlessOfEva(L1PcInstance pc, L1ItemInstance item, int item_id) {

		// 给予效果的时间
		int time = 0;

		// 伊娃的祝福
		if (item_id == 40032) {
			time = 1800;
		}

		// 人鱼之鳞
		else if (item_id == 40041) {
			time = 300;
		}

		// 水中的水
		else if (item_id == 41344) {
			time = 2100;
		}

		// 福利呼吸药水
		else if (item_id == 49303) {
			time = 7200;
		}

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
		pc.sendPackets(new S_SkillSound(pc.getId(), 190));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), 190));
		pc.setSkillEffect(STATUS_UNDERWATER_BREATH, time * 1000);
		pc.getInventory().removeItem(item, 1);
	}

	/**
	 * 使用黑色药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 */
	public static void useBlindPotion(L1PcInstance pc, L1ItemInstance item) {

		// 给予效果的时间
		int time = 16;

		// 法师魔法 (闇盲咒术)
		if (pc.hasSkillEffect(CURSE_BLIND)) {
			pc.killSkillEffectTimer(CURSE_BLIND);
		}

		// 法师魔法 (黑闇之影)
		else if (pc.hasSkillEffect(DARKNESS)) {
			pc.killSkillEffectTimer(DARKNESS);
		}

		// 漂浮之眼肉
		if (pc.hasSkillEffect(STATUS_FLOATING_EYE)) {
			pc.sendPackets(new S_CurseBlind(2));
		}
		else {
			pc.sendPackets(new S_CurseBlind(1));
		}

		pc.setSkillEffect(CURSE_BLIND, time * 1000); // 给予16秒效果
		pc.getInventory().removeItem(item, 1); // 删除道具
	}
}
