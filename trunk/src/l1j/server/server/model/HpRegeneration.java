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

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.server.utils.Random;

import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.types.Point;
import static l1j.server.server.model.skill.L1SkillId.*;

/**
 * HP的再生(恢复量)
 */
public class HpRegeneration extends TimerTask {

	private static final Logger _log = Logger.getLogger(HpRegeneration.class.getName());

	private final L1PcInstance _pc;
	/** 再生的最大值 */
	private int _regenMax = 0;
	/** 再生点 */
	private int _regenPoint = 0;
	/** 当前点 */
	private int _curPoint = 4;

	public HpRegeneration(L1PcInstance pc) {
		_pc = pc;

		updateLevel();
	}

	public void setState(int state) {
		if (_curPoint < state) {
			return;
		}

		_curPoint = state;
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead()) {
				return;
			}

			_regenPoint += _curPoint;
			_curPoint = 4;

			synchronized (this) {
				if (_regenMax <= _regenPoint) {
					_regenPoint = 0;
					regenHp();
				}
			}
		} catch (Throwable e) {
			_log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}

	/** 更新级别 */
	public void updateLevel() {
		final int lvlTable[] =
		new int[] { 30, 25, 20, 16, 14, 12, 11, 10, 9, 3, 2 };

		int regenLvl = Math.min(10, _pc.getLevel());
		if (30 <= _pc.getLevel() && _pc.isKnight()) {
			regenLvl = 11;
		}

		synchronized (this) {
			_regenMax = lvlTable[regenLvl - 1] * 4;
		}
	}

	public void regenHp() {
		if (_pc.isDead()) {
			return;
		}

		int maxBonus = 1;

		// CON奖励
		if (11 < _pc.getLevel() && 14 <= _pc.getCon()) {
			maxBonus = _pc.getCon() - 12;
			if (25 < _pc.getCon()) {
				maxBonus = 14;
			}
		}

		int equipHpr = _pc.getInventory().hpRegenPerTick();
		equipHpr += _pc.getHpr();
		int bonus = Random.nextInt(maxBonus) + 1;

		if (_pc.hasSkillEffect(NATURES_TOUCH)) { // 生命之泉
			bonus += 15;
		}
		if (L1HouseLocation.isInHouse(_pc.getX(), _pc.getY(), _pc.getMapId())) {
			bonus += 5;
		}
		if (_pc.getMapId() == 16384 || _pc.getMapId() == 16896
				|| _pc.getMapId() == 17408 || _pc.getMapId() == 17920
				|| _pc.getMapId() == 18432 || _pc.getMapId() == 18944
				|| _pc.getMapId() == 19968 || _pc.getMapId() == 19456
				|| _pc.getMapId() == 20480 || _pc.getMapId() == 20992
				|| _pc.getMapId() == 21504 || _pc.getMapId() == 22016
				|| _pc.getMapId() == 22528 || _pc.getMapId() == 23040
				|| _pc.getMapId() == 23552 || _pc.getMapId() == 24064
				|| _pc.getMapId() == 24576 || _pc.getMapId() == 25088) { // 宿屋
			bonus += 5;
		}
		// 妖精森林大树下
		if ((_pc.getLocation().isInScreen(new Point(33055,32336))
				&& _pc.getMapId() == 4 && _pc.isElf())) {
			bonus += 5;
		}
 		if (_pc.hasSkillEffect(COOKING_1_5_N)
				|| _pc.hasSkillEffect(COOKING_1_5_S)) {
			bonus += 3;
		}
 		if (_pc.hasSkillEffect(COOKING_2_4_N)
				|| _pc.hasSkillEffect(COOKING_2_4_S)
				|| _pc.hasSkillEffect(COOKING_3_6_N)
				|| _pc.hasSkillEffect(COOKING_3_6_S)) {
			bonus += 2;
		}
 		if (_pc.getOriginalHpr() > 0) { // 原始CON HPR补正
 			bonus += _pc.getOriginalHpr();
 		}

		boolean inLifeStream = false;
		if (isPlayerInLifeStream(_pc)) {
			inLifeStream = true;
			// 古代人空间、魔族神殿内HPR+3はなくなる？
			bonus += 3;
		}

		// 检查饥饿与负重
		if (_pc.get_food() < 3 || isOverWeight(_pc)
				|| _pc.hasSkillEffect(BERSERKERS)) { // 狂暴术
			bonus = 0;
			// 由于装备ＨＰＲ增加饱食度、负重减轻、 减少饱食度、重量に関係なく効果が残る
			if (equipHpr > 0) {
				equipHpr = 0;
			}
		}

		int newHp = _pc.getCurrentHp();
		newHp += bonus + equipHpr;

		if (newHp < 1) {
			newHp = 1; // ＨＰＲ减少装备不死亡
		}
		// 在水中减少HP处理
		// ライフストリームで減少をなくせるか不明
		if (isUnderwater(_pc)) {
			newHp -= 20;
			if (newHp < 1) {
				if (_pc.isGm()) {
					newHp = 1;
				} else {
					_pc.death(null); // 如果HP为零将死于窒息。
				}
			}
		}
		// Lv50任务的古代人空间1F2F HP减少处理
		if (isLv50Quest(_pc) && !inLifeStream) {
			newHp -= 10;
			if (newHp < 1) {
				if (_pc.isGm()) {
					newHp = 1;
				} else {
					_pc.death(null); // 如果HP为零将死亡。
				}
			}
		}
		// 魔族神殿的HP减少处理
		if (_pc.getMapId() == 410 && !inLifeStream) {
			newHp -= 10;
			if (newHp < 1) {
				if (_pc.isGm()) {
					newHp = 1;
				} else {
					_pc.death(null); // 如果HP为零将死亡。
				}
			}
		}

		if (!_pc.isDead()) {
			_pc.setCurrentHp(Math.min(newHp, _pc.getMaxHp()));
		}
	}

	/** 水中的负面状态无效 */
	private boolean isUnderwater(L1PcInstance pc) {
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

	/** 超重仍可恢复血魔 */
	private boolean isOverWeight(L1PcInstance pc) {
		// 体能激发状态、能量激发状态
		// 装备巨蚁女皇的金翅膀、不被视为超重。
		if (pc.hasSkillEffect(EXOTIC_VITALIZE)
				|| pc.hasSkillEffect(ADDITIONAL_FIRE)) {
			return false;
		}
		if (pc.getInventory().checkEquipped(20049)) { // 巨蚁女皇的金翅膀
			return false;
		}

		return (121 <= pc.getInventory().getWeight242()) ? true : false;
	}

	/** 50级任务 */
	private boolean isLv50Quest(L1PcInstance pc) {
		int mapId = pc.getMapId();
		return (mapId == 2000 || mapId == 2001) ? true : false;
	}

	/**
	 * 检查指定的PC是否在治愈能量风暴范围内
	 * 
	 * @param pc
	 *            PC
	 * @return true 如果PC在治愈能量风暴范围内
	 */
	private static boolean isPlayerInLifeStream(L1PcInstance pc) {
		for (L1Object object : pc.getKnownObjects()) {
			if (object instanceof L1EffectInstance == false) {
				continue;
			}
			L1EffectInstance effect = (L1EffectInstance) object;
			if (effect.getNpcId() == 81169 && effect.getLocation()
					.getTileLineDistance(pc.getLocation()) < 4) {
				return true;
			}
		}
		return false;
	}
}
