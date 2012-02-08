package lineage.console.connector.cite;

import l1j.server.Config;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_AddSkill;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.types.Point;
import lineage.console.connector.ConnectorSpellBook;

/**
 * 实例化 (接口引用:技能书)
 * 
 * @author jrwz
 */
public class CiteConnectorSpellBook implements ConnectorSpellBook {

	@Override
	public void useSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId) {

		int itemAttr = 0;
		int locAttr = 0; // 0:other 1:law 2:chaos
		boolean isLawful = true;
		int pcX = pc.getX();
		int pcY = pc.getY();
		int mapId = pc.getMapId();
		int level = pc.getLevel();

		switch (itemId) {

			// 正义魔法书
			case 45000:
			case 45008:
			case 45018:
			case 45021:
			case 40171:
			case 40179:
			case 40180:
			case 40182:
			case 40194:
			case 40197:
			case 40202:
			case 40206:
			case 40213:
			case 40220:
			case 40222:
				itemAttr = 1;
				break;

			// 邪恶魔法书
			case 45009:
			case 45010:
			case 45019:
			case 40172:
			case 40173:
			case 40178:
			case 40185:
			case 40186:
			case 40192:
			case 40196:
			case 40201:
			case 40204:
			case 40211:
			case 40221:
			case 40225:
				itemAttr = 2;
				break;
		}

		// 正义神殿
		if (((pcX > 33116) && (pcX < 33128) && (pcY > 32930) && (pcY < 32942) && (mapId == 4)) || ((pcX > 33135) && (pcX < 33147) && (pcY > 32235) && (pcY < 32247) && (mapId == 4)) || ((pcX >= 32783) && (pcX <= 32803) && (pcY >= 32831) && (pcY <= 32851) && (mapId == 77))) {
			locAttr = 1;
			isLawful = true;
		}

		// 邪恶神殿
		if (((pcX > 32880) && (pcX < 32892) && (pcY > 32646) && (pcY < 32658) && (mapId == 4)) || ((pcX > 32662) && (pcX < 32674) && (pcY > 32297) && (pcY < 32309) && (mapId == 4))) {
			locAttr = 2;
			isLawful = false;
		}
		if (pc.isGm()) {
			SpellBook(pc, item, isLawful);
		}
		else if (((itemAttr == locAttr) || (itemAttr == 0)) && (locAttr != 0)) {

			// 骑士
			if (pc.isKnight()) {

				if ((itemId >= 45000) && (itemId <= 45007) && (level >= 50)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 45000) && (itemId <= 45007)) {
					pc.sendPackets(new S_ServerMessage(312));
				}
				else {
					pc.sendPackets(new S_ServerMessage(79));
				}
			}

			// 王族或黑暗精灵
			else if (pc.isCrown() || pc.isDarkelf()) {
				if ((itemId >= 45000) && (itemId <= 45007) && (level >= 10)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 45008) && (itemId <= 45015) && (level >= 20)) {
					SpellBook(pc, item, isLawful);
				}
				else if (((itemId >= 45008) && (itemId <= 45015)) || ((itemId >= 45000) && (itemId <= 45007))) {
					pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
				}
				else {
					pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
				}
			}

			// 精灵
			else if (pc.isElf()) {
				if ((itemId >= 45000) && (itemId <= 45007) && (level >= 8)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 45008) && (itemId <= 45015) && (level >= 16)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 45016) && (itemId <= 45022) && (level >= 24)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40170) && (itemId <= 40177) && (level >= 32)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40178) && (itemId <= 40185) && (level >= 40)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40186) && (itemId <= 40193) && (level >= 48)) {
					SpellBook(pc, item, isLawful);
				}
				else if (((itemId >= 45000) && (itemId <= 45022)) || ((itemId >= 40170) && (itemId <= 40193))) {
					pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
				}
				else {
					pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
				}
			}

			// 法师
			else if (pc.isWizard()) {
				if ((itemId >= 45000) && (itemId <= 45007) && (level >= 4)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 45008) && (itemId <= 45015) && (level >= 8)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 45016) && (itemId <= 45022) && (level >= 12)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40170) && (itemId <= 40177) && (level >= 16)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40178) && (itemId <= 40185) && (level >= 20)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40186) && (itemId <= 40193) && (level >= 24)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40194) && (itemId <= 40201) && (level >= 28)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40202) && (itemId <= 40209) && (level >= 32)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40210) && (itemId <= 40217) && (level >= 36)) {
					SpellBook(pc, item, isLawful);
				}
				else if ((itemId >= 40218) && (itemId <= 40225) && (level >= 40)) {
					SpellBook(pc, item, isLawful);
				}
				else {
					pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
				}
			}
		}

		// 学习地点不对扣除技能书并施以惩罚 - 极道落雷
		else if ((itemAttr != locAttr) && (itemAttr != 0) && (locAttr != 0)) {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			S_SkillSound effect = new S_SkillSound(pc.getId(), 10);
			pc.sendPackets(effect);
			pc.broadcastPacket(effect);
			// 减血
			pc.setCurrentHp(Math.max(pc.getCurrentHp() - 45, 0));
			if (pc.getCurrentHp() <= 0) {
				pc.death(null);
			}
			pc.getInventory().removeItem(item, 1);
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}

	@Override
	public void useCrownSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance, final int itemId) {

		if (pc.isCrown() || pc.isGm()) {
			if ((itemId == 40226) && (pc.getLevel() >= 15)) {
				CrownSpellBook(pc, l1iteminstance);
			}
			else if ((itemId == 40228) && (pc.getLevel() >= 30)) {
				CrownSpellBook(pc, l1iteminstance);
			}
			else if ((itemId == 40227) && (pc.getLevel() >= 40)) {
				CrownSpellBook(pc, l1iteminstance);
			}
			else if (((itemId == 40231) || (itemId == 40232)) && (pc.getLevel() >= 45)) {
				CrownSpellBook(pc, l1iteminstance);
			}
			else if ((itemId == 40230) && (pc.getLevel() >= 50)) {
				CrownSpellBook(pc, l1iteminstance);
			}
			else if ((itemId == 40229) && (pc.getLevel() >= 55)) {
				CrownSpellBook(pc, l1iteminstance);
			}
			else {
				pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79));
		}
	}

	@Override
	public void useElfSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId) {

		final int level = pc.getLevel();
		if ((pc.isElf() || pc.isGm()) && isLearnElfMagic(pc)) {
			if ((itemId >= 40232) && (itemId <= 40234) && (level >= 10)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40235) && (itemId <= 40236) && (level >= 20)) {
				ElfSpellBook(pc, item);
			}
			if ((itemId >= 40237) && (itemId <= 40240) && (level >= 30)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40241) && (itemId <= 40243) && (level >= 40)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40244) && (itemId <= 40246) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40247) && (itemId <= 40248) && (level >= 30)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40249) && (itemId <= 40250) && (level >= 40)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40251) && (itemId <= 40252) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 40253) && (level >= 30)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 40254) && (level >= 40)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 40255) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 40256) && (level >= 30)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 40257) && (level >= 40)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40258) && (itemId <= 40259) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40260) && (itemId <= 40261) && (level >= 30)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 40262) && (level >= 40)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 40263) && (itemId <= 40264) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 41149) && (itemId <= 41150) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId == 41151) && (level >= 40)) {
				ElfSpellBook(pc, item);
			}
			else if ((itemId >= 41152) && (itemId <= 41153) && (level >= 50)) {
				ElfSpellBook(pc, item);
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79));
		}
	}

	@Override
	public void useDarkElfSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId) {

		if (pc.isDarkelf() || pc.isGm()) {
			if ((itemId >= 40265) && (itemId <= 40269) && (pc.getLevel() >= 15)) {
				DarkElfSpellBook(pc, item);
			}
			else if ((itemId >= 40270) && (itemId <= 40274) && (pc.getLevel() >= 30)) {
				DarkElfSpellBook(pc, item);
			}
			else if ((itemId >= 40275) && (itemId <= 40279) && (pc.getLevel() >= 45)) {
				DarkElfSpellBook(pc, item);
			}
			else {
				pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79));
		}
	}

	@Override
	public void useKnightSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId) {

		if (pc.isKnight() || pc.isGm()) {
			if ((itemId >= 40164) && (itemId <= 40165 // 技术书(冲击之晕)、技术书(增幅防御)
					) && (pc.getLevel() >= 50)) {
				KnightSpellBook(pc, item);
			}
			else if ((itemId >= 41147) && (itemId <= 41148 // 技术书(坚固防护)、技术书(反击屏障)
					) && (pc.getLevel() >= 50)) {
				KnightSpellBook(pc, item);
			}
			else if ((itemId == 40166) && (pc.getLevel() >= 60)) { // 技术书(尖刺盔甲)
				KnightSpellBook(pc, item);
			}
			else {
				pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}

	@Override
	public void useDragonKnightSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId) {

		if (pc.isDragonKnight() || pc.isGm()) {
			if ((itemId >= 49102) && (itemId <= 49106 // 龙骑士秘技 LV1
					) && (pc.getLevel() >= 15)) {
				DragonKnightSpellBook(pc, item);
			}
			else if ((itemId >= 49107) && (itemId <= 49111 // 龙骑士秘技 LV2
					) && (pc.getLevel() >= 30)) {
				DragonKnightSpellBook(pc, item);
			}
			else if ((itemId >= 49112) && (itemId <= 49116 // 龙骑士秘技 LV3
					) && (pc.getLevel() >= 45)) {
				DragonKnightSpellBook(pc, item);
			}
			else {
				pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}

	@Override
	public void useIllusionistSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId) {

		if (pc.isIllusionist() || pc.isGm()) {
			if ((itemId >= 49117) && (itemId <= 49121 // 幻术师 魔法 LV1
					) && (pc.getLevel() >= 10)) {
				IllusionistSpellBook(pc, item);
			}
			else if ((itemId >= 49122) && (itemId <= 49126 // 幻术师 魔法 LV2
					) && (pc.getLevel() >= 20)) {
				IllusionistSpellBook(pc, item);
			}
			else if ((itemId >= 49127) && (itemId <= 49131 // 幻术师 魔法 LV3
					) && (pc.getLevel() >= 30)) {
				IllusionistSpellBook(pc, item);
			}
			else if ((itemId >= 49132) && (itemId <= 49136 // 幻术师 魔法 LV4
					) && (pc.getLevel() >= 40)) {
				IllusionistSpellBook(pc, item);
			}
			else {
				pc.sendPackets(new S_ServerMessage(312)); // 你还不能学习法术。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // 没有任何事情发生。
		}
	}

	@Override
	public void SpellBook(final L1PcInstance pc, final L1ItemInstance item, final boolean isLawful) {

		String s = "";
		int i = 0;
		int level1 = 0;
		int level2 = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		for (int skillId = 1; skillId < 81; skillId++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("魔法书(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("魔法书(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("魔法书(").append(l1skills.getName()).append(")").toString();
			}
			if (item.getItem().getName().equalsIgnoreCase(s1)) {
				int skillLevel = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (skillLevel) {
					case 1: // '\001'
						level1 = i7;
						break;

					case 2: // '\002'
						level2 = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;
				}
			}
		}

		int objid = pc.getId();
		pc.sendPackets(new S_AddSkill(level1, level2, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, 0, 0, 0, 0));
		S_SkillSound s_skillSound = new S_SkillSound(objid, isLawful ? 224 : 231);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(objid, i, s, 0, 0);
		pc.getInventory().removeItem(item, 1);
	}

	@Override
	public void CrownSpellBook(L1PcInstance pc, L1ItemInstance l1iteminstance) {

		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		for (int j6 = 113; j6 < 121; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("魔法书(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("魔法书(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("魔法书(").append(l1skills.getName()).append(")").toString();
			}
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
					case 1: // '\001'
						j = i7;
						break;

					case 2: // '\002'
						k = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;
				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, 0, 0, 0, 0));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	@Override
	public void ElfSpellBook(L1PcInstance pc, L1ItemInstance l1iteminstance) {

		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		for (int j6 = 129; j6 <= 176; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("精灵水晶(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("精灵水晶(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("精灵の水晶(").append(l1skills.getName()).append(")").toString();
			}
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				if (!pc.isGm() && (l1skills.getAttr() != 0) && (pc.getElfAttr() != l1skills.getAttr())) {
					if ((pc.getElfAttr() == 0) || (pc.getElfAttr() == 1) || (pc.getElfAttr() == 2) || (pc.getElfAttr() == 4) || (pc.getElfAttr() == 8)) { // 属性值异常
						pc.sendPackets(new S_ServerMessage(79));
						return;
					}
				}
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
					case 1: // '\001'
						j = i7;
						break;

					case 2: // '\002'
						k = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;
				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, 0, 0, 0, 0));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	@Override
	public void DarkElfSpellBook(L1PcInstance pc, L1ItemInstance l1iteminstance) {

		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		for (int j6 = 97; j6 < 112; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("黑暗精灵水晶(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("黑暗精灵水晶(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("闇精灵の水晶(").append(l1skills.getName()).append(")").toString();
			}
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
					case 1: // '\001'
						j = i7;
						break;

					case 2: // '\002'
						k = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;
				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, 0, 0, 0, 0));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 231);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	@Override
	public void KnightSpellBook(L1PcInstance pc, L1ItemInstance l1iteminstance) {

		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		for (int j6 = 87; j6 <= 91; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);

			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("技术书(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("技术书(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("技术书(").append(l1skills.getName()).append(")").toString();
			}
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
					case 1: // '\001'
						j = i7;
						break;

					case 2: // '\002'
						k = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;
				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, 0, 0, 0, 0));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	@Override
	public void DragonKnightSpellBook(L1PcInstance pc, L1ItemInstance l1iteminstance) {

		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int i8 = 0;
		int j8 = 0;
		int k8 = 0;
		int l8 = 0;
		for (int j6 = 181; j6 <= 195; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("龙骑士书板(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("龙骑士书板(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("ドラゴンナイトの书板（").append(l1skills.getName()).append("）").toString();
			}
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
					case 1: // '\001'
						j = i7;
						break;

					case 2: // '\002'
						k = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;

					case 25: // '\031'
						j8 = i7;
						break;

					case 26: // '\032'
						k8 = i7;
						break;

					case 27: // '\033'
						l8 = i7;
						break;
					case 28: // '\034'
						i8 = i7;
						break;
				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, j8, k8, l8, i8));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	@Override
	public void IllusionistSpellBook(L1PcInstance pc, L1ItemInstance l1iteminstance) {

		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int i8 = 0;
		int j8 = 0;
		int k8 = 0;
		int l8 = 0;
		for (int j6 = 201; j6 <= 220; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = null;
			if (Config.CLIENT_LANGUAGE == 3) {
				s1 = (new StringBuilder()).append("记忆水晶(").append(l1skills.getName()).append(")").toString();
			}
			else if (Config.CLIENT_LANGUAGE == 5) {
				s1 = (new StringBuilder()).append("记忆水晶(").append(l1skills.getName()).append(")").toString();
			}
			else {
				s1 = (new StringBuilder()).append("记忆の水晶(").append(l1skills.getName()).append("）").toString();
			}
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
					case 1: // '\001'
						j = i7;
						break;

					case 2: // '\002'
						k = i7;
						break;

					case 3: // '\003'
						l = i7;
						break;

					case 4: // '\004'
						i1 = i7;
						break;

					case 5: // '\005'
						j1 = i7;
						break;

					case 6: // '\006'
						k1 = i7;
						break;

					case 7: // '\007'
						l1 = i7;
						break;

					case 8: // '\b'
						i2 = i7;
						break;

					case 9: // '\t'
						j2 = i7;
						break;

					case 10: // '\n'
						k2 = i7;
						break;

					case 11: // '\013'
						l2 = i7;
						break;

					case 12: // '\f'
						i3 = i7;
						break;

					case 13: // '\r'
						j3 = i7;
						break;

					case 14: // '\016'
						k3 = i7;
						break;

					case 15: // '\017'
						l3 = i7;
						break;

					case 16: // '\020'
						i4 = i7;
						break;

					case 17: // '\021'
						j4 = i7;
						break;

					case 18: // '\022'
						k4 = i7;
						break;

					case 19: // '\023'
						l4 = i7;
						break;

					case 20: // '\024'
						i5 = i7;
						break;

					case 21: // '\025'
						j5 = i7;
						break;

					case 22: // '\026'
						k5 = i7;
						break;

					case 23: // '\027'
						l5 = i7;
						break;

					case 24: // '\030'
						i6 = i7;
						break;

					case 25: // '\031'
						j8 = i7;
						break;

					case 26: // '\032'
						k8 = i7;
						break;

					case 27: // '\033'
						l8 = i7;
						break;
					case 28: // '\034'
						i8 = i7;
						break;
				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5, i6, j8, k8, l8, i8));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		pc.broadcastPacket(s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	/**
	 * 精灵魔法学习地点
	 * 
	 * @param pc
	 *            对象
	 */
	private boolean isLearnElfMagic(final L1PcInstance pc) {
		final int pcX = pc.getX();
		final int pcY = pc.getY();
		final int pcMapId = pc.getMapId();
		if (((pcX >= 32786) && (pcX <= 32797) && (pcY >= 32842) && (pcY <= 32859) && (pcMapId == 75 // 象牙塔
				))
				|| (pc.getLocation().isInScreen(new Point(33055, 32336)) && (pcMapId == 4))) { // 妖精森林大树下
			return true;
		}
		return false;
	}
}
