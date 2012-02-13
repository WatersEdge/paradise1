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

import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_BLESS_OF_MAZU;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_ENCHANTING_BATTLE;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_A_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_B_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_C_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_1;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_2;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_3;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_4;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_5;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_6;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_7;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_8;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_MAGIC_STONE_D_9;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_BATTLE;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_STRENGTHENING_HP;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_STRENGTHENING_MP;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.identity.L1ItemId;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 效果
 */
public class Effect {

	/**
	 * 附魔石效果
	 * 
	 * @param pc
	 * @param skillId
	 * @param time
	 */
	public static void magicStoneEffect(L1PcInstance pc, int skillId, int time) {
		byte type = 0;
		if (!pc.hasSkillEffect(skillId)) {
			switch (skillId) {
				case EFFECT_MAGIC_STONE_A_1:
					pc.addMaxHp(10);
					type = 84;
					break;
				case EFFECT_MAGIC_STONE_A_2:
					pc.addMaxHp(20);
					type = 85;
					break;
				case EFFECT_MAGIC_STONE_A_3:
					pc.addMaxHp(30);
					type = 86;
					break;
				case EFFECT_MAGIC_STONE_A_4:
					pc.addMaxHp(40);
					type = 87;
					break;
				case EFFECT_MAGIC_STONE_A_5:
					pc.addMaxHp(50);
					pc.addHpr(1);
					type = 88;
					break;
				case EFFECT_MAGIC_STONE_A_6:
					pc.addMaxHp(60);
					pc.addHpr(2);
					type = 89;
					break;
				case EFFECT_MAGIC_STONE_A_7:
					pc.addMaxHp(70);
					pc.addHpr(3);
					type = 90;
					break;
				case EFFECT_MAGIC_STONE_A_8:
					pc.addMaxHp(80);
					pc.addHpr(4);
					pc.addHitup(1);
					type = 91;
					break;
				case EFFECT_MAGIC_STONE_A_9:
					pc.addMaxHp(100);
					pc.addHpr(5);
					pc.addHitup(2);
					pc.addDmgup(2);
					pc.addStr((byte) 1);
					pc.sendPackets(new S_OwnCharStatus2(pc));
					type = 92;
					break;
				case EFFECT_MAGIC_STONE_B_1:
					pc.addMaxHp(5);
					pc.addMaxMp(3);
					type = 93;
					break;
				case EFFECT_MAGIC_STONE_B_2:
					pc.addMaxHp(10);
					pc.addMaxMp(6);
					type = 94;
					break;
				case EFFECT_MAGIC_STONE_B_3:
					pc.addMaxHp(15);
					pc.addMaxMp(10);
					type = 95;
					break;
				case EFFECT_MAGIC_STONE_B_4:
					pc.addMaxHp(20);
					pc.addMaxMp(15);
					type = 96;
					break;
				case EFFECT_MAGIC_STONE_B_5:
					pc.addMaxHp(25);
					pc.addMaxMp(20);
					type = 97;
					break;
				case EFFECT_MAGIC_STONE_B_6:
					pc.addMaxHp(30);
					pc.addMaxMp(20);
					pc.addHpr(1);
					type = 98;
					break;
				case EFFECT_MAGIC_STONE_B_7:
					pc.addMaxHp(35);
					pc.addMaxMp(20);
					pc.addHpr(1);
					pc.addMpr(1);
					type = 99;
					break;
				case EFFECT_MAGIC_STONE_B_8:
					pc.addMaxHp(40);
					pc.addMaxMp(25);
					pc.addHpr(2);
					pc.addMpr(1);
					type = 100;
					break;
				case EFFECT_MAGIC_STONE_B_9:
					pc.addMaxHp(50);
					pc.addMaxMp(30);
					pc.addHpr(2);
					pc.addMpr(2);
					pc.addBowDmgup(2);
					pc.addBowHitup(2);
					pc.addDex((byte) 1);
					pc.sendPackets(new S_OwnCharStatus2(pc));
					type = 101;
					break;
				case EFFECT_MAGIC_STONE_C_1:
					pc.addMaxMp(5);
					type = 102;
					break;
				case EFFECT_MAGIC_STONE_C_2:
					pc.addMaxMp(10);
					type = 103;
					break;
				case EFFECT_MAGIC_STONE_C_3:
					pc.addMaxMp(15);
					type = 104;
					break;
				case EFFECT_MAGIC_STONE_C_4:
					pc.addMaxMp(20);
					type = 105;
					break;
				case EFFECT_MAGIC_STONE_C_5:
					pc.addMaxMp(25);
					pc.addMpr(1);
					type = 106;
					break;
				case EFFECT_MAGIC_STONE_C_6:
					pc.addMaxMp(30);
					pc.addMpr(2);
					type = 107;
					break;
				case EFFECT_MAGIC_STONE_C_7:
					pc.addMaxMp(35);
					pc.addMpr(3);
					type = 108;
					break;
				case EFFECT_MAGIC_STONE_C_8:
					pc.addMaxMp(40);
					pc.addMpr(4);
					type = 109;
					break;
				case EFFECT_MAGIC_STONE_C_9:
					pc.addMaxMp(50);
					pc.addMpr(5);
					pc.addInt((byte) 1);
					pc.addSp(1);
					pc.sendPackets(new S_SPMR(pc));
					pc.sendPackets(new S_OwnCharStatus2(pc));
					type = 110;
					break;
				case EFFECT_MAGIC_STONE_D_1:
					pc.addMr(2);
					type = 111;
					break;
				case EFFECT_MAGIC_STONE_D_2:
					pc.addMr(4);
					type = 112;
					break;
				case EFFECT_MAGIC_STONE_D_3:
					pc.addMr(6);
					type = 113;
					break;
				case EFFECT_MAGIC_STONE_D_4:
					pc.addMr(8);
					type = 114;
					break;
				case EFFECT_MAGIC_STONE_D_5:
					pc.addMr(10);
					pc.addAc(-1);
					type = 115;
					break;
				case EFFECT_MAGIC_STONE_D_6:
					pc.addMr(10);
					pc.addAc(-2);
					type = 116;
					break;
				case EFFECT_MAGIC_STONE_D_7:
					pc.addMr(10);
					pc.addAc(-3);
					type = 117;
					break;
				case EFFECT_MAGIC_STONE_D_8:
					pc.addMr(15);
					pc.addAc(-4);
					pc.addDamageReductionByArmor(1);
					type = 118;
					break;
				case EFFECT_MAGIC_STONE_D_9:
					pc.addMr(20);
					pc.addAc(-5);
					pc.addCon((byte) 1);
					pc.addDamageReductionByArmor(3);
					type = 119;
					break;
				default:
					break;
			}

			if (type >= 84 && type <= 92) { // (近战)
				pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
				if (pc.isInParty()) { // 组队中
					pc.getParty().updateMiniHP(pc);
				}
			}
			else if (type >= 93 && type <= 101) { // (远攻)
				pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
				pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
				if (pc.isInParty()) { // 组队中
					pc.getParty().updateMiniHP(pc);
				}
			}
			else if (type >= 102 && type <= 110) { // 恢复
				pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
			}
			else if (type >= 111 && type <= 119) { // 防御
				pc.sendPackets(new S_SPMR(pc));
				pc.sendPackets(new S_OwnCharAttrDef(pc));
				pc.sendPackets(new S_OwnCharStatus2(pc));

			}
		}
		pc.setMagicStoneLevel(type);
		pc.setSkillEffect(skillId, time * 1000);
	}

	/**
	 * 使用效果
	 * 
	 * @param pc
	 *            使用者
	 * @param skillId
	 *            技能ID
	 * @param time
	 *            时间
	 */
	public static void useEffect(L1PcInstance pc, int skillId, int time) {
		if (!pc.hasSkillEffect(skillId)) {
			switch (skillId) {
				case EFFECT_BLESS_OF_MAZU: // 妈祖的祝福
					pc.addHitup(3); // 攻击成功 +3
					pc.addDmgup(3); // 额外攻击点数 +3
					pc.addMpr(2);
					break;
				case EFFECT_ENCHANTING_BATTLE: // 强化战斗卷轴
					pc.addHitup(3); // 攻击成功 +3
					pc.addDmgup(3); // 额外攻击点数 +3
					pc.addBowHitup(3); // 远距离命中率 +3
					pc.addBowDmgup(3); // 远距离攻击力 +3
					pc.addSp(3); // 魔攻 +3
					pc.sendPackets(new S_SPMR(pc));
					break;
				case EFFECT_STRENGTHENING_HP: // 体力增强卷轴
					pc.addMaxHp(50);
					pc.addHpr(4);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					if (pc.isInParty()) { // 组队中
						pc.getParty().updateMiniHP(pc);
					}
					break;
				case EFFECT_STRENGTHENING_MP: // 魔力增强卷轴
					pc.addMaxMp(40);
					pc.addMpr(4);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					break;

				default:
					break;
			}
		}
		pc.setSkillEffect(skillId, time * 1000);
	}

	/**
	 * 道具使用效果
	 * 
	 * @param pc
	 *            使用者
	 * @param item
	 *            道具
	 */
	public static void useEffectItem(L1PcInstance pc, L1ItemInstance item) {
		boolean isMagicStone = false;
		boolean deteleItem = true;

		if (pc.hasSkillEffect(DECAY_POTION)) { // 药水霜化术状态
			pc.sendPackets(new S_ServerMessage(698)); // 喉咙灼热，无法喝东西。
			return;
		}

		int itemId = item.getItem().getItemId();
		int skillId = 0;
		int time = 0;
		int gfxid = 0;
		switch (itemId) {
			case L1ItemId.POTION_OF_EXP_150: // 150%神力药水
			case L1ItemId.POTION_OF_EXP_175: // 175%神力药水
			case L1ItemId.POTION_OF_EXP_200: // 200%神力药水
			case L1ItemId.POTION_OF_EXP_225: // 225%神力药水
			case L1ItemId.POTION_OF_EXP_250: // 250%神力药水
				skillId = itemId - 42999;
				time = 900;
				gfxid = itemId - 39699;
				pc.deleteRepeatedSkills(skillId); // 与战斗药水等相冲
				pc.sendPackets(new S_ServerMessage(1292)); // 狩猎的经验值将会增加。
				break;
			case L1ItemId.BLESS_OF_MAZU: // 妈祖祝福平安符
				skillId = EFFECT_BLESS_OF_MAZU;
				time = 2400;
				gfxid = 7321;
				pc.deleteRepeatedSkills(skillId); // 与妖精属性魔法相冲！
				break;
			case L1ItemId.POTION_OF_BATTLE: // 战斗药水
				skillId = EFFECT_POTION_OF_BATTLE;
				time = 3600;
				gfxid = 7013;
				pc.deleteRepeatedSkills(skillId); // 与神力药水等相冲
				break;
			case L1ItemId.SCROLL_FOR_STRENGTHENING_HP: // 体力增强卷轴
			case L1ItemId.SCROLL_FOR_STRENGTHENING_MP: // 魔力增强卷轴
			case L1ItemId.SCROLL_FOR_ENCHANTING_BATTLE: // 强化战斗卷轴
				skillId = itemId - 42999;
				time = 3600;
				gfxid = itemId - 40014;
				pc.deleteRepeatedSkills(skillId);
				break;

			default:
				if (itemId >= 47064 && itemId <= 47072) { // 附魔石(近战)
					skillId = itemId - 43051;
					gfxid = itemId - 38125;
					time = 600;
					isMagicStone = true;
					pc.deleteRepeatedSkills(skillId); // 附魔石不可共存
				}
				else if (itemId >= 47074 && itemId <= 47082) { // 附魔石(远攻)
					skillId = itemId - 43052;
					gfxid = itemId - 38126;
					time = 600;
					isMagicStone = true;
					pc.deleteRepeatedSkills(skillId); // 附魔石不可共存
				}
				else if (itemId >= 47084 && itemId <= 47092) { // 附魔石(恢复)
					skillId = itemId - 43053;
					gfxid = itemId - 38127;
					time = 600;
					isMagicStone = true;
					pc.deleteRepeatedSkills(skillId); // 附魔石不可共存
				}
				else if (itemId >= 47094 && itemId <= 47102) { // 附魔石(防御)
					skillId = itemId - 43054;
					gfxid = itemId - 38128;
					time = 600;
					isMagicStone = true;
					pc.deleteRepeatedSkills(skillId); // 附魔石不可共存
				}
				else {
					pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
					return;
				}
				break;
		}
		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid));
		pc.broadcastPacket(new S_SkillSound(pc.getId(), gfxid));

		if (isMagicStone) {
			magicStoneEffect(pc, skillId, time);

		}
		else {
			useEffect(pc, skillId, time);
			if (deteleItem) { // 删除道具
				pc.getInventory().removeItem(item, 1);
			}
		}
	}

}
