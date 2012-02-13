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

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;

import java.util.List;
import java.util.StringTokenizer;

import l1j.server.server.datatables.ArmorSetTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1ArmorSets;
import l1j.server.server.utils.collections.Lists;

/**
 * 套装
 */
public abstract class L1ArmorSet {

	private static List<L1ArmorSet> _allSet = Lists.newList();

	/*
	 * 这里的初始化会觉得是丑什么的〜〜V
	 */
	static {
		L1ArmorSetImpl impl;

		for (final L1ArmorSets armorSets : ArmorSetTable.getInstance().getAllList()) {
			try {

				impl = new L1ArmorSetImpl(getArray(armorSets.getSets(), ","));
				if (armorSets.getPolyId() != -1) {
					impl.addEffect(new PolymorphEffect(armorSets.getPolyId()));
				}
				impl.addEffect(new AcHpMpBonusEffect(armorSets.getAc(), armorSets.getHp(), armorSets.getMp(), armorSets.getHpr(), armorSets.getMpr(), armorSets.getMr()));
				impl.addEffect(new StatBonusEffect(armorSets.getStr(), armorSets.getDex(), armorSets.getCon(), armorSets.getWis(), armorSets.getCha(), armorSets.getIntl()));
				impl.addEffect(new DefenseBonusEffect(armorSets.getDefenseWater(), armorSets.getDefenseWind(), armorSets.getDefenseFire(), armorSets.getDefenseWind()));
				impl.addEffect(new HitDmgModifierEffect(armorSets.getHitModifier(), armorSets.getDmgModifier(), armorSets.getBowHitModifier(), armorSets.getBowDmgModifier(), armorSets.getSp()));
				_allSet.add(impl);
			}
			catch (final Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static List<L1ArmorSet> getAllSet() {
		return _allSet;
	}

	private static int[] getArray(final String s, final String sToken) {
		final StringTokenizer st = new StringTokenizer(s, sToken);
		final int size = st.countTokens();
		String temp = null;
		final int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			temp = st.nextToken();
			array[i] = Integer.parseInt(temp);
		}
		return array;
	}

	public abstract void cancelEffect(L1PcInstance pc);

	public abstract void giveEffect(L1PcInstance pc);

	public abstract boolean isEquippedRingOfArmorSet(L1PcInstance pc);

	public abstract boolean isPartOfSet(int id);

	public abstract boolean isValid(L1PcInstance pc);
}

class AcHpMpBonusEffect implements L1ArmorSetEffect {
	private final int _ac;

	private final int _addHp;

	private final int _addMp;

	private final int _regenHp;

	private final int _regenMp;

	private final int _addMr;

	public AcHpMpBonusEffect(final int ac, final int addHp, final int addMp, final int regenHp, final int regenMp, final int addMr) {
		this._ac = ac;
		this._addHp = addHp;
		this._addMp = addMp;
		this._regenHp = regenHp;
		this._regenMp = regenMp;
		this._addMr = addMr;
	}

	@Override
	public void cancelEffect(final L1PcInstance pc) {
		pc.addAc(-this._ac);
		pc.addMaxHp(-this._addHp);
		pc.addMaxMp(-this._addMp);
		pc.addHpr(-this._regenHp);
		pc.addMpr(-this._regenMp);
		pc.addMr(-this._addMr);
	}

	@Override
	public void giveEffect(final L1PcInstance pc) {
		pc.addAc(this._ac);
		pc.addMaxHp(this._addHp);
		pc.addMaxMp(this._addMp);
		pc.addHpr(this._regenHp);
		pc.addMpr(this._regenMp);
		pc.addMr(this._addMr);
	}
}

// 水、风、火、地属性
class DefenseBonusEffect implements L1ArmorSetEffect {
	private final int _defenseWater;

	private final int _defenseWind;

	private final int _defenseFire;

	private final int _defenseEarth;

	public DefenseBonusEffect(final int defenseWater, final int defenseWind, final int defenseFire, final int defenseEarth) {
		this._defenseWater = defenseWater;
		this._defenseWind = defenseWind;
		this._defenseFire = defenseFire;
		this._defenseEarth = defenseEarth;
	}

	// @Override
	@Override
	public void cancelEffect(final L1PcInstance pc) {
		pc.addWater(-this._defenseWater);
		pc.addWind(-this._defenseWind);
		pc.addFire(-this._defenseFire);
		pc.addEarth(-this._defenseEarth);
	}

	// @Override
	@Override
	public void giveEffect(final L1PcInstance pc) {
		pc.addWater(this._defenseWater);
		pc.addWind(this._defenseWind);
		pc.addFire(this._defenseFire);
		pc.addEarth(this._defenseEarth);
	}
}

// 命中率、额外攻击力、魔攻
class HitDmgModifierEffect implements L1ArmorSetEffect {
	private final int _hitModifier;

	private final int _dmgModifier;

	private final int _bowHitModifier;

	private final int _bowDmgModifier;

	private final int _sp;

	public HitDmgModifierEffect(final int hitModifier, final int dmgModifier, final int bowHitModifier, final int bowDmgModifier, final int sp) {
		this._hitModifier = hitModifier;
		this._dmgModifier = dmgModifier;
		this._bowHitModifier = bowHitModifier;
		this._bowDmgModifier = bowDmgModifier;
		this._sp = sp;
	}

	// @Override
	@Override
	public void cancelEffect(final L1PcInstance pc) {
		pc.addHitModifierByArmor(-this._hitModifier);
		pc.addDmgModifierByArmor(-this._dmgModifier);
		pc.addBowHitModifierByArmor(-this._bowHitModifier);
		pc.addBowDmgModifierByArmor(-this._bowDmgModifier);
		pc.addSp(-this._sp);
	}

	// @Override
	@Override
	public void giveEffect(final L1PcInstance pc) {
		pc.addHitModifierByArmor(this._hitModifier);
		pc.addDmgModifierByArmor(this._dmgModifier);
		pc.addBowHitModifierByArmor(this._bowHitModifier);
		pc.addBowDmgModifierByArmor(this._bowDmgModifier);
		pc.addSp(this._sp);
	}
}

interface L1ArmorSetEffect {
	public void cancelEffect(L1PcInstance pc);

	public void giveEffect(L1PcInstance pc);
}

class L1ArmorSetImpl extends L1ArmorSet {
	private final int _ids[];

	private final List<L1ArmorSetEffect> _effects;

	protected L1ArmorSetImpl(final int ids[]) {
		this._ids = ids;
		this._effects = Lists.newList();
	}

	public void addEffect(final L1ArmorSetEffect effect) {
		this._effects.add(effect);
	}

	@Override
	public void cancelEffect(final L1PcInstance pc) {
		for (final L1ArmorSetEffect effect : this._effects) {
			effect.cancelEffect(pc);
		}
	}

	@Override
	public void giveEffect(final L1PcInstance pc) {
		for (final L1ArmorSetEffect effect : this._effects) {
			effect.giveEffect(pc);
		}
	}

	@Override
	public boolean isEquippedRingOfArmorSet(final L1PcInstance pc) {
		final L1PcInventory pcInventory = pc.getInventory();
		L1ItemInstance armor = null;
		boolean isSetContainRing = false;

		// セット装備にリングが含まれているか調べる
		for (final int id : this._ids) {
			armor = pcInventory.findItemId(id);
			if ((armor.getItem().getType2() == 2) && (armor.getItem().getType() == 9)) { // ring
				isSetContainRing = true;
				break;
			}
		}

		// リングを2つ装備していて、それが両方セット装備か調べる
		if ((armor != null) && isSetContainRing) {
			final int itemId = armor.getItem().getItemId();
			if (pcInventory.getTypeEquipped(2, 9) == 2) {
				L1ItemInstance ring[] = new L1ItemInstance[2];
				ring = pcInventory.getRingEquipped();
				if ((ring[0].getItem().getItemId() == itemId) && (ring[1].getItem().getItemId() == itemId)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isPartOfSet(final int id) {
		for (final int i : this._ids) {
			if (id == i) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final boolean isValid(final L1PcInstance pc) {
		return pc.getInventory().checkEquipped(this._ids);
	}

	public void removeEffect(final L1ArmorSetEffect effect) {
		this._effects.remove(effect);
	}

}

class PolymorphEffect implements L1ArmorSetEffect {
	private int _gfxId;

	public PolymorphEffect(final int gfxId) {
		this._gfxId = gfxId;
	}

	@Override
	public void cancelEffect(final L1PcInstance pc) {
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身。
			return;
		}
		if (this._gfxId == 6080) {
			if (pc.get_sex() == 0) {
				this._gfxId = 6094;
			}
		}
		if (pc.getTempCharGfx() != this._gfxId) {
			return;
		}
		L1PolyMorph.undoPoly(pc);
	}

	@Override
	public void giveEffect(final L1PcInstance pc) {
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身。
			return;
		}
		if ((this._gfxId == 6080) || (this._gfxId == 6094)) {
			if (pc.get_sex() == 0) {
				this._gfxId = 6094;
			}
			else {
				this._gfxId = 6080;
			}
			if (!this.isRemainderOfCharge(pc)) { // 残チャージ数なし
				return;
			}
		}
		L1PolyMorph.doPoly(pc, this._gfxId, 0, L1PolyMorph.MORPH_BY_ITEMMAGIC);
	}

	private boolean isRemainderOfCharge(final L1PcInstance pc) {
		boolean isRemainderOfCharge = false;
		if (pc.getInventory().checkItem(20383, 1)) { // 军马头盔
			final L1ItemInstance item = pc.getInventory().findItemId(20383);
			if (item != null) {
				if (item.getChargeCount() != 0) {
					isRemainderOfCharge = true;
				}
			}
		}
		return isRemainderOfCharge;
	}

}

class StatBonusEffect implements L1ArmorSetEffect {
	private final int _str;

	private final int _dex;

	private final int _con;

	private final int _wis;

	private final int _cha;

	private final int _intl;

	public StatBonusEffect(final int str, final int dex, final int con, final int wis, final int cha, final int intl) {
		this._str = str;
		this._dex = dex;
		this._con = con;
		this._wis = wis;
		this._cha = cha;
		this._intl = intl;
	}

	@Override
	public void cancelEffect(final L1PcInstance pc) {
		pc.addStr((byte) -this._str);
		pc.addDex((byte) -this._dex);
		pc.addCon((byte) -this._con);
		pc.addWis((byte) -this._wis);
		pc.addCha((byte) -this._cha);
		pc.addInt((byte) -this._intl);
	}

	@Override
	public void giveEffect(final L1PcInstance pc) {
		pc.addStr((byte) this._str);
		pc.addDex((byte) this._dex);
		pc.addCon((byte) this._con);
		pc.addWis((byte) this._wis);
		pc.addCha((byte) this._cha);
		pc.addInt((byte) this._intl);
	}
}
