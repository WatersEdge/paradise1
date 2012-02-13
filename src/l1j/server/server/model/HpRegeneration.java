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
package l1j.server.server.model;

import static l1j.server.server.model.skill.L1SkillId.ADDITIONAL_FIRE;
import static l1j.server.server.model.skill.L1SkillId.BERSERKERS;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_5_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_4_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_2_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_6_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_3_6_S;
import static l1j.server.server.model.skill.L1SkillId.EFFECT_HELMET_OF_MAGIC_PUMPKIN;
import static l1j.server.server.model.skill.L1SkillId.EXOTIC_VITALIZE;
import static l1j.server.server.model.skill.L1SkillId.FLA_CURE_WARD;
import static l1j.server.server.model.skill.L1SkillId.NATURES_TOUCH;
import static l1j.server.server.model.skill.L1SkillId.STATUS_UNDERWATER_BREATH;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_bonusstats;
import l1j.server.server.types.Point;
import l1j.server.server.utils.Random;

/**
 * HP的再生(恢复量)
 */
public class HpRegeneration extends TimerTask {

	private static final Logger _log = Logger.getLogger(HpRegeneration.class.getName());

	/**
	 * 检查指定的PC是否在治愈能量风暴范围内
	 * 
	 * @param pc
	 *            PC
	 * @return true 如果PC在治愈能量风暴范围内
	 */
	private static boolean isPlayerInLifeStream(final L1PcInstance pc) {
		for (final L1Object object : pc.getKnownObjects()) {
			if ((object instanceof L1EffectInstance) == false) {
				continue;
			}
			final L1EffectInstance effect = (L1EffectInstance) object;
			if ((effect.getNpcId() == 81169) && (effect.getLocation().getTileLineDistance(pc.getLocation()) < 4)) {
				return true;
			}
		}
		return false;
	}

	private final L1PcInstance _pc;
	/** 再生的最大值 */
	private int _regenMax = 0;
	/** 再生点 */
	private int _regenPoint = 0;

	/** 当前点 */
	private int _curPoint = 4;

	public HpRegeneration(final L1PcInstance pc) {
		this._pc = pc;

		this.updateLevel();
	}

	public void regenHp() {
		if (this._pc.isDead()) {
			return;
		}

		int maxBonus = 1;

		// CON奖励
		if ((11 < this._pc.getLevel()) && (14 <= this._pc.getCon())) {
			maxBonus = this._pc.getCon() - 12;
			if (25 < this._pc.getCon()) {
				maxBonus = 14;
			}
		}

		int equipHpr = this._pc.getInventory().hpRegenPerTick();
		equipHpr += this._pc.getHpr();
		int bonus = Random.nextInt(maxBonus) + 1;

		if (this._pc.hasSkillEffect(NATURES_TOUCH)) { // 生命之泉
			bonus += 15;
		}
		if (L1HouseLocation.isInHouse(this._pc.getX(), this._pc.getY(), this._pc.getMapId())) {
			bonus += 5;
		}
		if ((this._pc.getMapId() == 16384) || (this._pc.getMapId() == 16896) || (this._pc.getMapId() == 17408) || (this._pc.getMapId() == 17920) || (this._pc.getMapId() == 18432) || (this._pc.getMapId() == 18944) || (this._pc.getMapId() == 19968)
				|| (this._pc.getMapId() == 19456) || (this._pc.getMapId() == 20480) || (this._pc.getMapId() == 20992) || (this._pc.getMapId() == 21504) || (this._pc.getMapId() == 22016) || (this._pc.getMapId() == 22528) || (this._pc.getMapId() == 23040)
				|| (this._pc.getMapId() == 23552) || (this._pc.getMapId() == 24064) || (this._pc.getMapId() == 24576) || (this._pc.getMapId() == 25088)) { // 宿屋
			bonus += 5;
		}
		// 妖精森林大树下
		if ((this._pc.getLocation().isInScreen(new Point(33055, 32336)) && (this._pc.getMapId() == 4) && this._pc.isElf())) {
			bonus += 5;
		}
		if (this._pc.hasSkillEffect(COOKING_1_5_N) || this._pc.hasSkillEffect(COOKING_1_5_S)) {
			bonus += 3;
		}
		if (this._pc.hasSkillEffect(COOKING_2_4_N) || this._pc.hasSkillEffect(COOKING_2_4_S) || this._pc.hasSkillEffect(COOKING_3_6_N) || this._pc.hasSkillEffect(COOKING_3_6_S)) {
			bonus += 2;
		}
		if (this._pc.getOriginalHpr() > 0) { // 原始CON HPR补正
			bonus += this._pc.getOriginalHpr();
		}

		boolean inLifeStream = false;
		if (isPlayerInLifeStream(this._pc)) {
			inLifeStream = true;
			// 古代人空间、魔族神殿内HPR+3はなくなる？
			bonus += 3;
		}

		// 检查饥饿与负重
		if ((this._pc.get_food() < 3) || this.isOverWeight(this._pc) || this._pc.hasSkillEffect(BERSERKERS)) { // 狂暴术
			bonus = 0;
			// 由于装备ＨＰＲ增加饱食度、负重减轻、 减少饱食度、重量に関係なく効果が残る
			if (equipHpr > 0) {
				equipHpr = 0;
			}
		}

		int newHp = this._pc.getCurrentHp();
		newHp += bonus + equipHpr;

		if (newHp < 1) {
			newHp = 1; // ＨＰＲ减少装备不死亡
		}
		// 在水中减少HP处理
		// ライフストリームで減少をなくせるか不明
		if (this.isUnderwater(this._pc)) {
			newHp -= 20;
			if (newHp < 1) {
				if (this._pc.isGm()) {
					newHp = 1;
				}
				else {
					this._pc.death(null); // 如果HP为零将死于窒息。
				}
			}
		}
		// Lv50任务的古代人空间1F2F HP减少处理
		if (this.isLv50Quest(this._pc) && !inLifeStream) {
			newHp -= 10;
			if (newHp < 1) {
				if (this._pc.isGm()) {
					newHp = 1;
				}
				else {
					this._pc.death(null); // 如果HP为零将死亡。
				}
			}
		}
		// 魔族神殿的HP减少处理
		if ((this._pc.getMapId() == 410) && !inLifeStream) {
			newHp -= 10;
			if (newHp < 1) {
				if (this._pc.isGm()) {
					newHp = 1;
				}
				else {
					this._pc.death(null); // 如果HP为零将死亡。
				}
			}
		}

		if (!this._pc.isDead()) {
			this._pc.setCurrentHp(Math.min(newHp, this._pc.getMaxHp()));
		}
	}

	@Override
	public void run() {
		try {
			if (this._pc.isDead()) {
				return;
			}

			// 新水龙装备魔法效果(法利昂的治愈结界) 非仿正
			if ((this._pc.getInventory().checkEquipped(21119)) // 法利昂的力量
					|| (this._pc.getInventory().checkEquipped(21120)) // 法利昂的魅惑
					|| (this._pc.getInventory().checkEquipped(21121)) // 法利昂的泉源
					|| (this._pc.getInventory().checkEquipped(21122)) // 法利昂的霸气
			) {
				if (!this._pc.hasSkillEffect(FLA_CURE_WARD) && !this._pc.isDead()) { // 没有法利昂的治愈结界
					this._pc.setSkillEffect(FLA_CURE_WARD, 120 * 1000); // 2分钟
				}
			}

			// 装备南瓜魔法帽获得万圣节南瓜派
			if (this._pc.getInventory().checkEquipped(20380)) { // 检查是否装备南瓜魔法帽
				if (!this._pc.hasSkillEffect(EFFECT_HELMET_OF_MAGIC_PUMPKIN) && !this._pc.isDead()) { // 如果没有南瓜魔法帽效果
					this._pc.setSkillEffect(EFFECT_HELMET_OF_MAGIC_PUMPKIN, 5 * 60 * 1000); // 加上效果并开始倒计时5分钟
				}
			}

			// 免登出可点完奖励点
			if (!(this._pc.isGm() || this._pc.isMonitor())) { // 不是GM或管理员
				if ((this._pc.getLevel() >= 51) && (this._pc.getLevel() - 50 > this._pc.getBonusStats())) {
					if ((this._pc.getBaseStr() + this._pc.getBaseDex() + this._pc.getBaseCon() + this._pc.getBaseInt() + this._pc.getBaseWis() + this._pc.getBaseCha()) < (Config.BONUS_STATS1 * 6)) {
						this._pc.sendPackets(new S_bonusstats(this._pc.getId(), 1));
					}
				}
			}

			this._regenPoint += this._curPoint;
			this._curPoint = 4;

			synchronized (this) {
				if (this._regenMax <= this._regenPoint) {
					this._regenPoint = 0;
					this.regenHp();
				}
			}
		}
		catch (final Throwable e) {
			_log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}

	public void setState(final int state) {
		if (this._curPoint < state) {
			return;
		}

		this._curPoint = state;
	}

	/** 更新级别 */
	public void updateLevel() {
		final int lvlTable[] = new int[] { 30, 25, 20, 16, 14, 12, 11, 10, 9, 3, 2 };

		int regenLvl = Math.min(10, this._pc.getLevel());
		if ((30 <= this._pc.getLevel()) && this._pc.isKnight()) {
			regenLvl = 11;
		}

		synchronized (this) {
			this._regenMax = lvlTable[regenLvl - 1] * 4;
		}
	}

	/** 50级任务 */
	private boolean isLv50Quest(final L1PcInstance pc) {
		final int mapId = pc.getMapId();
		return ((mapId == 2000) || (mapId == 2001)) ? true : false;
	}

	/** 超重仍可恢复血魔 */
	private boolean isOverWeight(final L1PcInstance pc) {
		// 体能激发状态、能量激发状态
		// 装备巨蚁女皇的金翅膀、不被视为超重。
		if (pc.hasSkillEffect(EXOTIC_VITALIZE) || pc.hasSkillEffect(ADDITIONAL_FIRE)) {
			return false;
		}
		if (pc.getInventory().checkEquipped(20049)) { // 巨蚁女皇的金翅膀
			return false;
		}

		return (121 <= pc.getInventory().getWeight242()) ? true : false;
	}

	/** 水中的负面状态无效 */
	private boolean isUnderwater(final L1PcInstance pc) {
		// 启动水下装备时、 伊娃祝福状态、修好的装备 则认为水中的负面状态无效。
		if (pc.getInventory().checkEquipped(20207)) { // 深水长靴
			return false;
		}
		if (pc.hasSkillEffect(STATUS_UNDERWATER_BREATH)) {
			return false;
		}
		if (pc.getInventory().checkEquipped(21048) // 修好的戒指
				&& pc.getInventory().checkEquipped(21049) // 修好的耳环
				&& pc.getInventory().checkEquipped(21050)) { // 修好的项链
			return false;
		}

		return pc.getMap().isUnderwater();
	}
}
