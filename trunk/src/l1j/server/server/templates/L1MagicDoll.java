package l1j.server.server.templates;

import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MagicDollTable;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.utils.Random;

/**
 * 魔法娃娃
 */
public class L1MagicDoll {

	/** 防御力增加 */
	public static int getAcByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getAc();
			}
		}
		return s;
	}
	/** 弓的攻击力增加 */
	public static int getBowDamageByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getBowDmg();
			}
		}
		return s;
	}
	/** 弓的命中率增加 */
	public static int getBowHitAddByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getBowHit();
			}
		}
		return s;
	}
	/** 近距离的攻击力增加 */
	public static int getDamageAddByDoll(L1Character _master) {
		int s = 0;
		int chance = Random.nextInt(100) + 1;
		boolean isAdd = false;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getDmgChance() > 0 && !isAdd) { // 额外伤害发动机率
					if (doll.getDmgChance() >= chance) {
						s += doll.getDmg();
						isAdd = true;
					}
				}
				else if (doll.getDmg() != 0) { // 额外伤害
					s += doll.getDmg();
				}
			}
		}
		if (isAdd) {
			if (_master instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) _master;
				pc.sendPackets(new S_SkillSound(_master.getId(), 6319));
			}
			_master.broadcastPacket(new S_SkillSound(_master.getId(), 6319));
		}
		return s;
	}
	/** 伤害回避 */
	public static int getDamageEvasionByDoll(L1Character _master) {
		int chance = Random.nextInt(100) + 1;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getDmgEvasionChance() >= chance) { // 伤害回避发动机率
					if (_master instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) _master;
						pc.sendPackets(new S_SkillSound(_master.getId(), 6320));
					}
					_master.broadcastPacket(new S_SkillSound(_master.getId(), 6320));
					return 1;
				}
			}
		}
		return 0;
	}
	/** 取得魔法娃娃伤害减免 */
	public static int getDamageReductionByDoll(L1Character _master) {
		int s = 0;
		int chance = Random.nextInt(100) + 1;
		boolean isReduction = false;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getDmgReductionChance() > 0 && !isReduction) { // 伤害减免发动机率
					if (doll.getDmgReductionChance() >= chance) {
						s += doll.getDmgReduction();
						isReduction = true;
					}
				}
				else if (doll.getDmgReduction() != 0) { // 伤害减免
					s += doll.getDmgReduction();
				}
			}
		}
		if (isReduction) {
			if (_master instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) _master;
				pc.sendPackets(new S_SkillSound(_master.getId(), 6320));
			}
			_master.broadcastPacket(new S_SkillSound(_master.getId(), 6320));
		}
		return s;
	}
	/** 取得魔法娃娃效果 */
	public static int getEffectByDoll(L1Character _master, byte type) {
		int chance = Random.nextInt(100) + 1;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll.getEffectChance() > chance) {
				if (doll != null) {
					if (doll.getEffect() == type) {
						return type;
					}
				}
			}
		}
		return 0;
	}
	/** 近距离的命中率增加 */
	public static int getHitAddByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getHit();
			}
		}
		return s;
	}
	/** 体力回覆量 (时间固定性) */
	public static int getHpByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getHprTime() && doll.getHpr() != 0) {
					s += doll.getHpr();
				}
			}
		}
		return s;
	}
	/** 体力回覆量 */
	public static int getHprByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (!doll.getHprTime() && doll.getHpr() != 0) {
					s += doll.getHpr();
				}
			}
		}
		return s;
	}
	/** 取得道具 */
	public static int getMakeItemId(L1Character _master) {
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				L1Item item = ItemTable.getInstance().getTemplate((doll.getMakeItemId()));
				if (item != null) {
					return item.getItemId();
				}
			}
		}
		return 0;
	}
	/** 魔力回覆量 (时间固定性) */
	public static int getMpByDoll(L1Character _master) {
		int s = 0;

		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getMprTime() && doll.getMpr() != 0) {
					s += doll.getMpr();
				}
			}
		}
		return s;
	}
	/** 魔力回覆量 */
	public static int getMprByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (!doll.getMprTime() && doll.getMpr() != 0) {
					s += doll.getMpr();
				}
			}
		}
		return s;
	}
	/** 闇黑耐性增加 */
	public static int getRegistBlindByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getRegistBlind();
			}
		}
		return s;
	}
	/** 寒冰耐性增加 */
	public static int getRegistFreezeByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getRegistFreeze();
			}
		}
		return s;
	}
	/** 睡眠耐性增加 */
	public static int getRegistSleepByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getRegistSleep();
			}
		}
		return s;
	}
	/** 石化耐性增加 */
	public static int getRegistStoneByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getRegistStone();
			}
		}
		return s;
	}
	/** 昏迷耐性增加 */
	public static int getRegistStunByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getRegistStun();
			}
		}
		return s;
	}
	/** 支撑耐性增加 */
	public static int getRegistSustainByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getRegistSustain();
			}
		}
		return s;
	}
	/** 负重减轻 */
	public static int getWeightReductionByDoll(L1Character _master) {
		int s = 0;
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				s += doll.getWeightReduction();
			}
		}
		return s;
	}
	/** 回血判断 (时间固定性) */
	public static boolean isHpRegeneration(L1Character _master) {
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getHprTime() && doll.getHpr() != 0) {
					return true;
				}
			}
		}
		return false;
	}
	/** 创造道具 */
	public static boolean isItemMake(L1Character _master) {
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				L1Item item = ItemTable.getInstance().getTemplate((doll.getMakeItemId()));
				if (item != null) {
					return true;
				}
			}
		}
		return false;
	}
	/** 回魔判断 (时间固定性) */
	public static boolean isMpRegeneration(L1Character _master) {
		for (L1DollInstance dollIns : _master.getDollList().values()) {
			L1MagicDoll doll = MagicDollTable.getInstance().getTemplate(dollIns.getItemId());
			if (doll != null) {
				if (doll.getMprTime() && doll.getMpr() != 0) {
					return true;
				}
			}
		}
		return false;
	}
	/** 道具ID */
	private int _itemId;
	/** 娃娃ID */
	private int _dollId;

	/** 物理防御 */
	private int _ac;

	/** 回血 */
	private int _hpr;

	/** 回魔 */
	private int _mpr;

	/** 回血时间 */
	private boolean _hprTime;

	/** 会魔时间 */
	private boolean _mprTime;

	/** 伤害 */
	private int _dmg;

	/** 弓伤害 */
	private int _bowDmg;

	/** 伤害几率 */
	private int _dmgChance;

	/** 命中率 */
	private int _hit;

	/** 弓的命中率 */
	private int _bowHit;

	/** 伤害减免 */
	private int _dmgReduction;

	/** 伤害减免的几率 */
	private int _dmgReductionChance;

	/** 伤害回避 */
	private int _dmgEvasionChance;

	/** 负重轻减 */
	private int _weightReduction;

	/** 昏迷耐性 */
	private int _registStun;

	/** 石化耐性 */
	private int _registStone;

	/** 睡眠耐性 */
	private int _registSleep;

	/** 寒冰耐性 */
	private int _registFreeze;

	/** 支撑耐性 */
	private int _registSustain;

	/** 闇黑耐性 */
	private int _registBlind;

	/** 取得道具 */
	private int _makeItemId;

	/** 效果 */
	private byte _effect;

	/** 效果几率 */
	private int _EffectChance;

	/** 取得AC */
	public int getAc() {
		return _ac;
	}

	/** 取得弓伤害 */
	public int getBowDmg() {
		return _bowDmg;
	}

	/** 取得弓的命中率 */
	public int getBowHit() {
		return _bowHit;
	}

	/** 取得伤害 */
	public int getDmg() {
		return _dmg;
	}

	/** 取得伤害几率 */
	public int getDmgChance() {
		return _dmgChance;
	}

	/** 取得伤害回避 */
	public int getDmgEvasionChance() {
		return _dmgEvasionChance;
	}

	/** 取得伤害减免 */
	public int getDmgReduction() {
		return _dmgReduction;
	}

	/** 取得伤害减免的几率 */
	public int getDmgReductionChance() {
		return _dmgReductionChance;
	}

	/** 取得娃娃ID */
	public int getDollId() {
		return _dollId;
	}

	/** 取得效果 */
	public byte getEffect() {
		return _effect;
	}

	/** 取得效果几率 */
	public int getEffectChance() {
		return _EffectChance;
	}

	/** 取得命中率 */
	public int getHit() {
		return _hit;
	}

	/** 取得回血 */
	public int getHpr() {
		return _hpr;
	}

	/** 取得回血时间 */
	public boolean getHprTime() {
		return _hprTime;
	}

	/** 取得道具ID */
	public int getItemId() {
		return _itemId;
	}

	/** 返回取得道具 */
	public int getMakeItemId() {
		return _makeItemId;
	}

	/** 取得回魔 */
	public int getMpr() {
		return _mpr;
	}

	/** 取得回魔时间 */
	public boolean getMprTime() {
		return _mprTime;
	}

	/** 取得闇黑耐性 */
	public int getRegistBlind() {
		return _registBlind;
	}

	/** 取得寒冰耐性 */
	public int getRegistFreeze() {
		return _registFreeze;
	}

	/** 取得睡眠耐性 */
	public int getRegistSleep() {
		return _registSleep;
	}

	/** 取得石化耐性 */
	public int getRegistStone() {
		return _registStone;
	}

	/** 取得昏迷耐性 */
	public int getRegistStun() {
		return _registStun;
	}

	/** 取得支撑耐性 */
	public int getRegistSustain() {
		return _registSustain;
	}

	/** 取得负重轻减 */
	public int getWeightReduction() {
		return _weightReduction;
	}

	/** 设定AC */
	public void setAc(int i) {
		_ac = i;
	}

	/** 设定弓伤害 */
	public void setBowDmg(int i) {
		_bowDmg = i;
	}

	/** 设定弓的命中率 */
	public void setBowHit(int i) {
		_bowHit = i;
	}

	/** 设定伤害 */
	public void setDmg(int i) {
		_dmg = i;
	}

	/** 设定伤害几率 */
	public void setDmgChance(int i) {
		_dmgChance = i;
	}

	/** 设定伤害回避 */
	public void setDmgEvasionChance(int i) {
		_dmgEvasionChance = i;
	}

	/** 设定伤害减免 */
	public void setDmgReduction(int i) {
		_dmgReduction = i;
	}

	/** 设定伤害减免的几率 */
	public void setDmgReductionChance(int i) {
		_dmgReductionChance = i;
	}

	/** 设定娃娃ID */
	public void setDollId(int i) {
		_dollId = i;
	}

	/** 设定效果 */
	public void setEffect(byte i) {
		_effect = i;
	}

	/** 设定效果几率 */
	public void setEffectChance(int i) {
		_EffectChance = i;
	}

	/** 设定命中率 */
	public void setHit(int i) {
		_hit = i;
	}

	/** 设定回血 */
	public void setHpr(int i) {
		_hpr = i;
	}

	/** 设定回血时间 */
	public void setHprTime(boolean i) {
		_hprTime = i;
	}

	/** 设定道具ID */
	public void setItemId(int i) {
		_itemId = i;
	}

	/** 设定取得道具 */
	public void setMakeItemId(int i) {
		_makeItemId = i;
	}

	/** 设定回魔 */
	public void setMpr(int i) {
		_mpr = i;
	}

	/** 设定回魔时间 */
	public void setMprTime(boolean i) {
		_mprTime = i;
	}

	/** 设定闇黑耐性 */
	public void setRegistBlind(int i) {
		_registBlind = i;
	}

	/** 设定寒冰耐性 */
	public void setRegistFreeze(int i) {
		_registFreeze = i;
	}

	/** 设定睡眠耐性 */
	public void setRegistSleep(int i) {
		_registSleep = i;
	}

	/** 设定石化耐性 */
	public void setRegistStone(int i) {
		_registStone = i;
	}

	/** 设定昏迷耐性 */
	public void setRegistStun(int i) {
		_registStun = i;
	}

	/** 设定支撑耐性 */
	public void setRegistSustain(int i) {
		_registSustain = i;
	}

	/** 设定负重轻减 */
	public void setWeightReduction(int i) {
		_weightReduction = i;
	}

}
