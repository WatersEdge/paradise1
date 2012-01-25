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
package l1j.server.server.utils;

import static l1j.server.server.model.skill.L1SkillId.COOKING_1_7_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_7_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_7_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_7_S;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_150;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_175;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_200;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_225;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_EXP_250;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_POTION_OF_BATTLE;

import java.util.List;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_PetPack;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Pet;

// Referenced classes of package l1j.server.server.utils:
// CalcStat

/**
 * 计算取得的经验值
 */
public class CalcExp {

	private CalcExp() {
	}

	/**
	 * 序列版本UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 纪录用
	 */
	private static Logger _log = Logger.getLogger(CalcExp.class.getName());

	/**
	 * 最高经验值
	 */
	public static final int MAX_EXP = ExpTable.getExpByLevel(100) - 1;

	/**
	 * 取得序列版本UID
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 计算取得的经验值
	 * 
	 * @param l1pcinstance
	 *            PC
	 * @param targetid
	 *            目标ID
	 * @param acquisitorList
	 *            累计列表
	 * @param hateList
	 * @param exp
	 *            经验值
	 */
	public static void calcExp(L1PcInstance l1pcinstance, int targetid, List<L1Character> acquisitorList, List<Integer> hateList, int exp) {

		int i = 0;
		double party_level = 0;
		double dist = 0;
		int member_exp = 0;
		int member_lawful = 0;
		L1Object l1object = L1World.getInstance().findObject(targetid);
		L1NpcInstance npc = (L1NpcInstance) l1object;

		// 取得累积的 hate
		L1Character acquisitor;
		int hate = 0;
		int acquire_exp = 0;
		int acquire_lawful = 0;
		int party_exp = 0;
		int party_lawful = 0;
		int totalHateExp = 0;
		int totalHateLawful = 0;
		int partyHateExp = 0;
		int partyHateLawful = 0;
		int ownHateExp = 0;
		double tattoo_m_exp_125 = 1.25; // 1.25倍经验肩章 (经验倍率)
		double tattoo_m_exp_150 = 1.50; // 1.50倍经验肩章 (经验倍率)
		double tattoo_m_exp_175 = 1.75; // 1.75倍经验肩章 (经验倍率)

		// 1.25倍经验肩章
		if ((l1pcinstance.getInventory().checkEquipped(200036)) // [1小时]
				|| (l1pcinstance.getInventory().checkEquipped(200037)) // [3小时]
				|| (l1pcinstance.getInventory().checkEquipped(200038)) // [12小时]
				|| (l1pcinstance.getInventory().checkEquipped(200039)) // [24小时]
		) {
			exp = (int) (exp * tattoo_m_exp_125);
		}

		// 1.5倍经验肩章
		if ((l1pcinstance.getInventory().checkEquipped(200040)) // [1小时]
				|| (l1pcinstance.getInventory().checkEquipped(200041)) // [3小时]
				|| (l1pcinstance.getInventory().checkEquipped(200042)) // [12小时]
				|| (l1pcinstance.getInventory().checkEquipped(200043)) // [24小时]
		) {
			exp = (int) (exp * tattoo_m_exp_150);
		}

		// 1.75倍经验肩章
		if ((l1pcinstance.getInventory().checkEquipped(200044)) // [1小时]
				|| (l1pcinstance.getInventory().checkEquipped(200045)) // [3小时]
				|| (l1pcinstance.getInventory().checkEquipped(200046)) // [12小时]
				|| (l1pcinstance.getInventory().checkEquipped(200047)) // [24小时]
		) {
			exp = (int) (exp * tattoo_m_exp_175);
		}

		if (acquisitorList.size() != hateList.size()) {
			return;
		}
		for (i = hateList.size() - 1; i >= 0; i--) {
			acquisitor = acquisitorList.get(i);
			hate = hateList.get(i);
			if ((acquisitor != null) && !acquisitor.isDead()) {
				totalHateExp += hate;
				if (acquisitor instanceof L1PcInstance) {
					totalHateLawful += hate;
				}
			}
			else { // 排除 null、删除 或 死亡
				acquisitorList.remove(i);
				hateList.remove(i);
			}
		}
		if (totalHateExp == 0) { // 没有取得经验值的情况
			return;
		}

		if ((l1object != null) && !(npc instanceof L1PetInstance) && !(npc instanceof L1SummonInstance)) {
			// int exp = npc.get_exp();
			/*
			 * if (!L1World.getInstance().isProcessingContributionTotal() && (l1pcinstance.getHomeTownId() > 0)) { int contribution = npc.getLevel() / 10; l1pcinstance.addContribution(contribution); }
			 */// 取消由打怪获得村庄贡献度，改由制作村庄福利品获得贡献度 for 3.3C
			int lawful = npc.getLawful();

			if (l1pcinstance.isInParty()) { // 组队中
				// 计算组队的 hate
				// 组队以外的仍然分配
				partyHateExp = 0;
				partyHateLawful = 0;
				for (i = hateList.size() - 1; i >= 0; i--) {
					acquisitor = acquisitorList.get(i);
					hate = hateList.get(i);

					// PC
					if (acquisitor instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) acquisitor;
						if (pc == l1pcinstance) {
							partyHateExp += hate;
							partyHateLawful += hate;
						}
						else if (l1pcinstance.getParty().isMember(pc)) {
							partyHateExp += hate;
							partyHateLawful += hate;
						}
						else {
							if (totalHateExp > 0) {
								acquire_exp = (exp * hate / totalHateExp);
							}
							if (totalHateLawful > 0) {
								acquire_lawful = (lawful * hate / totalHateLawful);
							}
							AddExp(pc, acquire_exp, acquire_lawful);
						}
					}

					// 宠物
					else if (acquisitor instanceof L1PetInstance) {
						L1PetInstance pet = (L1PetInstance) acquisitor;
						L1PcInstance master = (L1PcInstance) pet.getMaster();
						if (master == l1pcinstance) {
							partyHateExp += hate;
						}
						else if (l1pcinstance.getParty().isMember(master)) {
							partyHateExp += hate;
						}
						else {
							if (totalHateExp > 0) {
								acquire_exp = (exp * hate / totalHateExp);
							}
							AddExpPet(pet, acquire_exp);
						}
					}

					// 召唤物
					else if (acquisitor instanceof L1SummonInstance) {
						L1SummonInstance summon = (L1SummonInstance) acquisitor;
						L1PcInstance master = (L1PcInstance) summon.getMaster();
						if (master == l1pcinstance) {
							partyHateExp += hate;
						}
						else if (l1pcinstance.getParty().isMember(master)) {
							partyHateExp += hate;
						}
						else {
						}
					}
				}
				if (totalHateExp > 0) {
					party_exp = (exp * partyHateExp / totalHateExp);
				}
				if (totalHateLawful > 0) {
					party_lawful = (lawful * partyHateLawful / totalHateLawful);
				}

				// EXP、ロウフル配分

				// 预奖励
				double pri_bonus = 0;
				L1PcInstance leader = l1pcinstance.getParty().getLeader();
				if (leader.isCrown() && (l1pcinstance.knownsObject(leader) || l1pcinstance.equals(leader))) {
					pri_bonus = 0.059;
				}

				// PT经验值の计算
				L1PcInstance[] ptMembers = l1pcinstance.getParty().getMembers();
				double pt_bonus = 0;
				for (L1PcInstance each : ptMembers) {
					if (l1pcinstance.knownsObject(each) || l1pcinstance.equals(each)) {
						party_level += each.getLevel() * each.getLevel();
					}
					if (l1pcinstance.knownsObject(each)) {
						pt_bonus += 0.04;
					}
				}

				// 1.25倍经验肩章
				if ((l1pcinstance.getInventory().checkEquipped(200036)) // [1小时]
						|| (l1pcinstance.getInventory().checkEquipped(200037)) // [3小时]
						|| (l1pcinstance.getInventory().checkEquipped(200038)) // [12小时]
						|| (l1pcinstance.getInventory().checkEquipped(200039)) // [24小时]
				) {
					party_exp = (int) (party_exp * tattoo_m_exp_125 * (1 + pt_bonus + pri_bonus));
				}

				// 1.5倍经验肩章
				else if ((l1pcinstance.getInventory().checkEquipped(200040)) // [1小时]
						|| (l1pcinstance.getInventory().checkEquipped(200041)) // [3小时]
						|| (l1pcinstance.getInventory().checkEquipped(200042)) // [12小时]
						|| (l1pcinstance.getInventory().checkEquipped(200043)) // [24小时]
				) {
					party_exp = (int) (party_exp * tattoo_m_exp_150 * (1 + pt_bonus + pri_bonus));
				}

				// 1.75倍经验肩章
				else if ((l1pcinstance.getInventory().checkEquipped(200044)) // [1小时]
						|| (l1pcinstance.getInventory().checkEquipped(200045)) // [3小时]
						|| (l1pcinstance.getInventory().checkEquipped(200046)) // [12小时]
						|| (l1pcinstance.getInventory().checkEquipped(200047)) // [24小时]
				) {
					party_exp = (int) (party_exp * tattoo_m_exp_175 * (1 + pt_bonus + pri_bonus));
				}
				else {
					party_exp = (int) (party_exp * (1 + pt_bonus + pri_bonus));
				}

				// 计算自己和召唤物宠物的 Hate
				if (party_level > 0) {
					dist = ((l1pcinstance.getLevel() * l1pcinstance.getLevel()) / party_level);
				}
				member_exp = (int) (party_exp * dist);
				member_lawful = (int) (party_lawful * dist);

				ownHateExp = 0;
				for (i = hateList.size() - 1; i >= 0; i--) {
					acquisitor = acquisitorList.get(i);
					hate = hateList.get(i);
					if (acquisitor instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) acquisitor;
						if (pc == l1pcinstance) {
							ownHateExp += hate;
						}
					}
					else if (acquisitor instanceof L1PetInstance) {
						L1PetInstance pet = (L1PetInstance) acquisitor;
						L1PcInstance master = (L1PcInstance) pet.getMaster();
						if (master == l1pcinstance) {
							ownHateExp += hate;
						}
					}
					else if (acquisitor instanceof L1SummonInstance) {
						L1SummonInstance summon = (L1SummonInstance) acquisitor;
						L1PcInstance master = (L1PcInstance) summon.getMaster();
						if (master == l1pcinstance) {
							ownHateExp += hate;
						}
					}
				}
				// 分配自己和召唤物宠物
				if (ownHateExp != 0) { // 参加了攻击
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = acquisitorList.get(i);
						hate = hateList.get(i);
						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;
							if (pc == l1pcinstance) {
								if (ownHateExp > 0) {
									acquire_exp = (member_exp * hate / ownHateExp);
								}
								AddExp(pc, acquire_exp, member_lawful);
							}
						}
						else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) pet.getMaster();
							if (master == l1pcinstance) {
								if (ownHateExp > 0) {
									acquire_exp = (member_exp * hate / ownHateExp);
								}
								AddExpPet(pet, acquire_exp);
							}
						}
						else if (acquisitor instanceof L1SummonInstance) {
						}
					}
				}
				else { // 没有参加攻击
						// 只分配自己的
					AddExp(l1pcinstance, member_exp, member_lawful);
				}

				// 计算队员的召唤物宠物的 Hate 总和
				for (L1PcInstance ptMember : ptMembers) {
					if (l1pcinstance.knownsObject(ptMember)) {
						if (party_level > 0) {
							dist = ((ptMember.getLevel() * ptMember.getLevel()) / party_level);
						}
						member_exp = (int) (party_exp * dist);
						member_lawful = (int) (party_lawful * dist);

						ownHateExp = 0;
						for (i = hateList.size() - 1; i >= 0; i--) {
							acquisitor = acquisitorList.get(i);
							hate = hateList.get(i);
							if (acquisitor instanceof L1PcInstance) {
								L1PcInstance pc = (L1PcInstance) acquisitor;
								if (pc == ptMember) {
									ownHateExp += hate;
								}
							}
							else if (acquisitor instanceof L1PetInstance) {
								L1PetInstance pet = (L1PetInstance) acquisitor;
								L1PcInstance master = (L1PcInstance) pet.getMaster();
								if (master == ptMember) {
									ownHateExp += hate;
								}
							}
							else if (acquisitor instanceof L1SummonInstance) {
								L1SummonInstance summon = (L1SummonInstance) acquisitor;
								L1PcInstance master = (L1PcInstance) summon.getMaster();
								if (master == ptMember) {
									ownHateExp += hate;
								}
							}
						}
						// 分配队员的召唤物宠物的 Hate
						if (ownHateExp != 0) { // 参加过攻击
							for (i = hateList.size() - 1; i >= 0; i--) {
								acquisitor = acquisitorList.get(i);
								hate = hateList.get(i);
								if (acquisitor instanceof L1PcInstance) {
									L1PcInstance pc = (L1PcInstance) acquisitor;
									if (pc == ptMember) {
										if (ownHateExp > 0) {
											acquire_exp = (member_exp * hate / ownHateExp);
										}
										AddExp(pc, acquire_exp, member_lawful);
									}
								}
								else if (acquisitor instanceof L1PetInstance) {
									L1PetInstance pet = (L1PetInstance) acquisitor;
									L1PcInstance master = (L1PcInstance) pet.getMaster();
									if (master == ptMember) {
										if (ownHateExp > 0) {
											acquire_exp = (member_exp * hate / ownHateExp);
										}
										AddExpPet(pet, acquire_exp);
									}
								}
								else if (acquisitor instanceof L1SummonInstance) {
								}
							}
						}
						else { // 没有参加攻击
								// 只分配该队员自己的
							AddExp(ptMember, member_exp, member_lawful);
						}
					}
				}
			}
			else { // パーティーを组んでいない
					// EXP、ロウフルの分配
				for (i = hateList.size() - 1; i >= 0; i--) {
					acquisitor = acquisitorList.get(i);
					hate = hateList.get(i);
					acquire_exp = (exp * hate / totalHateExp);
					if (acquisitor instanceof L1PcInstance) {
						if (totalHateLawful > 0) {
							acquire_lawful = (lawful * hate / totalHateLawful);
						}
					}

					if (acquisitor instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) acquisitor;
						AddExp(pc, acquire_exp, acquire_lawful);
					}
					else if (acquisitor instanceof L1PetInstance) {
						L1PetInstance pet = (L1PetInstance) acquisitor;
						AddExpPet(pet, acquire_exp);
					}
					else if (acquisitor instanceof L1SummonInstance) {
					}
				}
			}
		}
	}

	/**
	 * 增加经验值
	 * 
	 * @param pc
	 *            角色
	 * @param exp
	 *            经验值
	 * @param lawful
	 *            正义值
	 */
	private static void AddExp(L1PcInstance pc, int exp, int lawful) {

		int add_lawful = (int) (lawful * Config.RATE_LA) * -1; // 计算可取得的正义值
		pc.addLawful(add_lawful); // 为PC增加正义值
		double exppenalty = ExpTable.getPenaltyRate(pc.getLevel()); // 目前等级可获得的经验值
		double foodBonus = 1.0; // 魔法料理经验加成
		double expBonus = 1.0; // 战斗药水经验加成

		// 魔法料理经验加成
		if (pc.hasSkillEffect(COOKING_1_7_N) || pc.hasSkillEffect(COOKING_1_7_S)) {
			foodBonus = 1.01;
		}
		if (pc.hasSkillEffect(COOKING_2_7_N) || pc.hasSkillEffect(COOKING_2_7_S)) {
			foodBonus = 1.02;
		}
		if (pc.hasSkillEffect(COOKING_3_7_N) || pc.hasSkillEffect(COOKING_3_7_S)) {
			foodBonus = 1.03;
		}

		// 战斗药水、神力药水经验加成
		if (pc.hasSkillEffect(EFFECT_POTION_OF_BATTLE)) {
			expBonus = 1.2;
		}
		else if (pc.hasSkillEffect(EFFECT_POTION_OF_EXP_150)) {
			expBonus = 2.5;
		}
		else if (pc.hasSkillEffect(EFFECT_POTION_OF_EXP_175)) {
			expBonus = 2.75;
		}
		else if (pc.hasSkillEffect(EFFECT_POTION_OF_EXP_200)) {
			expBonus = 3.0;
		}
		else if (pc.hasSkillEffect(EFFECT_POTION_OF_EXP_225)) {
			expBonus = 3.25;
		}
		else if (pc.hasSkillEffect(EFFECT_POTION_OF_EXP_250)) {
			expBonus = 3.5;
		}

		int add_exp = (int) (exp // 基本经验值
				* exppenalty // 目前等级可获得的经验值
				* Config.RATE_XP // 经验值倍率
				* foodBonus // 魔法料理经验加成
		* expBonus // 战斗药水经验加成
		);
		pc.addExp(add_exp); // 为PC增加经验值
	}

	/**
	 * 增加宠物经验
	 * 
	 * @param pet
	 *            宠物
	 * @param exp
	 *            经验值
	 */
	private static void AddExpPet(L1PetInstance pet, int exp) {

		// 宠物主人
		L1PcInstance pc = (L1PcInstance) pet.getMaster();

		// 宠物道具
		int petItemObjId = pet.getItemObjId();

		// 宠物等级
		int levelBefore = pet.getLevel();

		// 宠物经验值
		int totalExp = (int) (exp * Config.RATE_XP + pet.getExp());

		// 宠物最高等级
		final int maxLevel = 51;
		if (totalExp >= ExpTable.getExpByLevel(maxLevel)) {
			totalExp = ExpTable.getExpByLevel(maxLevel) - 1;
		}

		// 设定宠物经验值
		pet.setExp(totalExp);

		// 设定宠物等级
		pet.setLevel(ExpTable.getLevelByExp(totalExp));

		int expPercentage = ExpTable.getExpPercentage(pet.getLevel(), totalExp);

		int gap = pet.getLevel() - levelBefore;
		for (int i = 1; i <= gap; i++) {
			IntRange hpUpRange = pet.getPetType().getHpUpRange();
			IntRange mpUpRange = pet.getPetType().getMpUpRange();
			pet.addMaxHp(hpUpRange.randomValue());
			pet.addMaxMp(mpUpRange.randomValue());
		}

		pet.setExpPercent(expPercentage);
		pc.sendPackets(new S_PetPack(pet, pc));

		if (gap != 0) { // 将宠物等级等变化保存至资料库
			L1Pet petTemplate = PetTable.getInstance().getTemplate(petItemObjId);
			if (petTemplate == null) { // PetTable无
				_log.warning("L1Pet == null");
				return;
			}
			petTemplate.set_exp(pet.getExp()); // 经验值
			petTemplate.set_level(pet.getLevel()); // 等级
			petTemplate.set_hp(pet.getMaxHp()); // HP
			petTemplate.set_mp(pet.getMaxMp()); // MP
			PetTable.getInstance().storePet(petTemplate); // 保存资料
			pc.sendPackets(new S_ServerMessage(320, pet.getName())); // \f1%0升级了。
		}
	}
}
