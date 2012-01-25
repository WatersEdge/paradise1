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
 * 角色资讯
 */
public class S_CharPacks extends ServerBasePacket {

	private static final String S_CHAR_PACKS = "[S] S_CharPacks";

	/**
	 * 角色资讯
	 * 
	 * @param name
	 * @param clanName
	 * @param type
	 * @param sex
	 * @param lawful
	 * @param hp
	 * @param mp
	 * @param ac
	 * @param lv
	 * @param str
	 * @param dex
	 * @param con
	 * @param wis
	 * @param cha
	 * @param intel
	 * @param accessLevel
	 * @param birthday
	 */
	public S_CharPacks(String name, String clanName, int type, int sex, int lawful, int hp, int mp, int ac, int lv, int str, int dex, int con, int wis, int cha, int intel, int accessLevel, int birthday) {
		writeC(Opcodes.S_OPCODE_CHARLIST);
		writeS(name);
		writeS(clanName);
		writeC(type);
		writeC(sex);
		writeH(lawful);
		writeH(hp);
		writeH(mp);
		writeC(ac);
		writeC(lv);
		writeC(str);
		writeC(dex);
		writeC(con);
		writeC(wis);
		writeC(cha);
		writeC(intel);
		writeC(0);// is Administrator
		writeD(birthday);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_CHAR_PACKS;
	}
}
