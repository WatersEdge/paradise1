package lineage.item.etcitem.poly;

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 海贼骷髅首领变身药水 - 41143 <br>
 * 海贼骷髅士兵变身药水 - 41144 <br>
 * 海贼骷髅刀手变身药水 - 41145 <br>
 * 夏纳的变身卷轴(30级) - 49149 <br>
 * 夏纳的变身卷轴(40级) - 49150 <br>
 * 夏纳的变身卷轴(52级) - 49151 <br>
 * 夏纳的变身卷轴(55级) - 49152 <br>
 * 夏纳的变身卷轴(60级) - 49153 <br>
 * 夏纳的变身卷轴(65级) - 49154 <br>
 * 夏纳的变身卷轴(70级) - 49155 <br>
 * 
 * @author jrwz
 */
public class PolyPotion extends ItemExecutor {

	private PolyPotion() {
	}

	public static ItemExecutor get() {
		return new PolyPotion();
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
		final int itemId = item.getItemId();
		this.usePolyPotion(pc, itemId);
		pc.getInventory().removeItem(item, 1);
	}

	private void usePolyPotion(final L1PcInstance pc, final int itemId) {
		int time = 1800;
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身。
			return;
		}

		int polyId = 0;
		switch (itemId) {
			case 41143: // 海贼骷髅首领变身药水
				polyId = 6086;
				time = 900;
				break;

			case 41144: // 海贼骷髅士兵变身药水
				polyId = 6087;
				time = 900;
				break;

			case 41145: // 海贼骷髅刀手变身药水
				polyId = 6088;
				time = 900;
				break;

			case 49139: // 起司蛋糕
				polyId = 6137; // 52级死亡骑士
				time = 900;
				break;

			case 49149: // 夏纳的变身卷轴(等级30)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6822;
						}
						else if (pc.isKnight()) {
							polyId = 6824;
						}
						else if (pc.isElf()) {
							polyId = 6826;
						}
						else if (pc.isWizard()) {
							polyId = 6828;
						}
						else if (pc.isDarkelf()) {
							polyId = 6830;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7139;
						}
						else if (pc.isIllusionist()) {
							polyId = 7141;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6823;
						}
						else if (pc.isKnight()) {
							polyId = 6825;
						}
						else if (pc.isElf()) {
							polyId = 6827;
						}
						else if (pc.isWizard()) {
							polyId = 6829;
						}
						else if (pc.isDarkelf()) {
							polyId = 6831;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7140;
						}
						else if (pc.isIllusionist()) {
							polyId = 7142;
						}
						break;
				}
				break;

			case 49150: // 夏纳的变身卷轴(等级40)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6832;
						}
						else if (pc.isKnight()) {
							polyId = 6834;
						}
						else if (pc.isElf()) {
							polyId = 6836;
						}
						else if (pc.isWizard()) {
							polyId = 6838;
						}
						else if (pc.isDarkelf()) {
							polyId = 6840;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7143;
						}
						else if (pc.isIllusionist()) {
							polyId = 7145;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6833;
						}
						else if (pc.isKnight()) {
							polyId = 6835;
						}
						else if (pc.isElf()) {
							polyId = 6837;
						}
						else if (pc.isWizard()) {
							polyId = 6839;
						}
						else if (pc.isDarkelf()) {
							polyId = 6841;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7144;
						}
						else if (pc.isIllusionist()) {
							polyId = 7146;
						}
						break;
				}
				break;

			case 49151: // 夏纳的变身卷轴(等级52)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6842;
						}
						else if (pc.isKnight()) {
							polyId = 6844;
						}
						else if (pc.isElf()) {
							polyId = 6846;
						}
						else if (pc.isWizard()) {
							polyId = 6848;
						}
						else if (pc.isDarkelf()) {
							polyId = 6850;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7147;
						}
						else if (pc.isIllusionist()) {
							polyId = 7149;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6843;
						}
						else if (pc.isKnight()) {
							polyId = 6845;
						}
						else if (pc.isElf()) {
							polyId = 6847;
						}
						else if (pc.isWizard()) {
							polyId = 6849;
						}
						else if (pc.isDarkelf()) {
							polyId = 6851;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7148;
						}
						else if (pc.isIllusionist()) {
							polyId = 7150;
						}
						break;
				}
				break;

			case 49152: // 夏纳的变身卷轴(等级55)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6852;
						}
						else if (pc.isKnight()) {
							polyId = 6854;
						}
						else if (pc.isElf()) {
							polyId = 6856;
						}
						else if (pc.isWizard()) {
							polyId = 6858;
						}
						else if (pc.isDarkelf()) {
							polyId = 6860;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7151;
						}
						else if (pc.isIllusionist()) {
							polyId = 7153;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6853;
						}
						else if (pc.isKnight()) {
							polyId = 6855;
						}
						else if (pc.isElf()) {
							polyId = 6857;
						}
						else if (pc.isWizard()) {
							polyId = 6859;
						}
						else if (pc.isDarkelf()) {
							polyId = 6861;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7152;
						}
						else if (pc.isIllusionist()) {
							polyId = 7154;
						}
						break;
				}
				break;

			case 49153: // 夏纳的变身卷轴(等级60)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6862;
						}
						else if (pc.isKnight()) {
							polyId = 6864;
						}
						else if (pc.isElf()) {
							polyId = 6866;
						}
						else if (pc.isWizard()) {
							polyId = 6868;
						}
						else if (pc.isDarkelf()) {
							polyId = 6870;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7155;
						}
						else if (pc.isIllusionist()) {
							polyId = 7157;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6863;
						}
						else if (pc.isKnight()) {
							polyId = 6865;
						}
						else if (pc.isElf()) {
							polyId = 6867;
						}
						else if (pc.isWizard()) {
							polyId = 6869;
						}
						else if (pc.isDarkelf()) {
							polyId = 6871;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7156;
						}
						else if (pc.isIllusionist()) {
							polyId = 7158;
						}
						break;
				}
				break;

			case 49154: // 夏纳的变身卷轴(等级65)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6872;
						}
						else if (pc.isKnight()) {
							polyId = 6874;
						}
						else if (pc.isElf()) {
							polyId = 6876;
						}
						else if (pc.isWizard()) {
							polyId = 6878;
						}
						else if (pc.isDarkelf()) {
							polyId = 6880;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7159;
						}
						else if (pc.isIllusionist()) {
							polyId = 7161;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6873;
						}
						else if (pc.isKnight()) {
							polyId = 6875;
						}
						else if (pc.isElf()) {
							polyId = 6877;
						}
						else if (pc.isWizard()) {
							polyId = 6879;
						}
						else if (pc.isDarkelf()) {
							polyId = 6881;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7160;
						}
						else if (pc.isIllusionist()) {
							polyId = 7162;
						}
						break;
				}
				break;

			case 49155: // 夏纳的变身卷轴(等级70)
				switch (pc.get_sex()) {
					case 0:
						if (pc.isCrown()) {
							polyId = 6882;
						}
						else if (pc.isKnight()) {
							polyId = 6884;
						}
						else if (pc.isElf()) {
							polyId = 6886;
						}
						else if (pc.isWizard()) {
							polyId = 6888;
						}
						else if (pc.isDarkelf()) {
							polyId = 6890;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7163;
						}
						else if (pc.isIllusionist()) {
							polyId = 7165;
						}
						break;

					case 1:
						if (pc.isCrown()) {
							polyId = 6883;
						}
						else if (pc.isKnight()) {
							polyId = 6885;
						}
						else if (pc.isElf()) {
							polyId = 6887;
						}
						else if (pc.isWizard()) {
							polyId = 6889;
						}
						else if (pc.isDarkelf()) {
							polyId = 6891;
						}
						else if (pc.isDragonKnight()) {
							polyId = 7164;
						}
						else if (pc.isIllusionist()) {
							polyId = 7166;
						}
						break;
				}
				break;
		}
		L1PolyMorph.doPoly(pc, polyId, time, L1PolyMorph.MORPH_BY_ITEMMAGIC);
	}
}
