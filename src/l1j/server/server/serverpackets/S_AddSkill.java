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
package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

/**
 * 增加魔法进魔法清单的封包
 */
public class S_AddSkill extends ServerBasePacket {

	private static final String S_ADD_SKILL = "[S] S_AddSkill";

	private byte[] _byte = null;

	public S_AddSkill(final int level, final int id) {
		final int ids[] = new int[28];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = 0;
		}
		ids[level] = id;

		final boolean hasLevel5to8 = 0 < (ids[4] + ids[5] + ids[6] + ids[7]);
		final boolean hasLevel9to10 = 0 < (ids[8] + ids[9]);

		writeC(Opcodes.S_OPCODE_ADDSKILL);
		if (hasLevel5to8 && !hasLevel9to10) {
			writeC(50);
		}
		else if (hasLevel9to10) {
			writeC(100);
		}
		else {
			writeC(32);
		}
		for (final int i : ids) {
			writeC(i);
		}
		writeD(0);
		writeD(0);
	}

	public S_AddSkill(final int level1, final int level2, final int level3, final int level4, final int level5, final int level6, final int level7, final int level8, final int level9, final int level10, final int knight, final int l2, final int de1, final int de2,
			final int royal, final int l3, final int elf1, final int elf2, final int elf3, final int elf4, final int elf5, final int elf6, final int k5, final int l5, final int m5, final int n5, final int o5, final int p5) {
		final int i6 = level5 + level6 + level7 + level8;
		final int j6 = level9 + level10;
		writeC(Opcodes.S_OPCODE_ADDSKILL);
		if ((i6 > 0) && (j6 == 0)) {
			writeC(50);
		}
		else if (j6 > 0) {
			writeC(100);
		}
		else {
			writeC(32);
		}
		writeC(level1);
		writeC(level2);
		writeC(level3);
		writeC(level4);
		writeC(level5);
		writeC(level6);
		writeC(level7);
		writeC(level8);
		writeC(level9);
		writeC(level10);
		writeC(knight);
		writeC(l2);
		writeC(de1);
		writeC(de2);
		writeC(royal);
		writeC(l3);
		writeC(elf1);
		writeC(elf2);
		writeC(elf3);
		writeC(elf4);
		writeC(elf5);
		writeC(elf6);
		writeC(k5);
		writeC(l5);
		writeC(m5);
		writeC(n5);
		writeC(o5);
		writeC(p5);
		writeD(0);
		writeD(0);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_ADD_SKILL;
	}

}
