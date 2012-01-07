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

import l1j.server.server.model.Instance.L1PcInstance;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

/**
 * 聊天频道
 */
public class S_ChatPacket extends ServerBasePacket {

	private static final String _S__1F_NORMALCHATPACK = "[S] S_ChatPacket";

	private byte[] _byte = null;

	/**
	 * 聊天频道
	 * @param pc
	 * @param chat
	 * @param opcode
	 * @param type
	 */
	public S_ChatPacket(L1PcInstance pc, String chat, int opcode, int type) {

		if (type == 0) { // 一般频道
			writeC(opcode);
			writeC(type);
			if (pc.isInvisble()) {
				writeD(0);
			}
			else {
				writeD(pc.getId());
			}
			writeS(pc.getName() + ": " + chat);
		}
		else if (type == 2) { // 大喊频道
			writeC(opcode);
			writeC(type);
			if (pc.isInvisble()) {
				writeD(0);
			}
			else {
				writeD(pc.getId());
			}
			writeS("<" + pc.getName() + "> " + chat);
			writeH(pc.getX());
			writeH(pc.getY());
		}
		else if (type == 3) { // 全体频道
			writeC(opcode);
			writeC(type);
			if (pc.isGm() == true) {
				writeS("[******] " + chat);
			}
			else {
				writeS("[" + pc.getName() + "] " + chat);
			}
		}
		else if (type == 4) { // 血盟频道
			writeC(opcode);
			writeC(type);
			writeS("{" + pc.getName() + "} " + chat);
		}
		else if (type == 9) { // 密语频道 (发送)
			writeC(opcode);
			writeC(type);
			writeS("-> (" + pc.getName() + ") " + chat);
		}
		else if (type == 11) { // 组队频道
			writeC(opcode);
			writeC(type);
			writeS("(" + pc.getName() + ") " + chat);
		}
		else if (type == 12) { // 交易频道
			writeC(opcode);
			writeC(type);
			writeS("[" + pc.getName() + "] " + chat);
		}
		else if (type == 13) { // 联盟频道
			writeC(opcode);
			writeC(type);
			writeS("{{" + pc.getName() + "}} " + chat);
		}
		else if (type == 14) { // 组队频道 (聊天)
			writeC(opcode);
			writeC(type);
			if (pc.isInvisble()) {
				writeD(0);
			}
			else {
				writeD(pc.getId());
			}
			writeS("(" + pc.getName() + ") " + chat);
		}
		else if (type == 16) { // 密语频道
			writeC(opcode);
			writeS(pc.getName());
			writeS(chat);
		}
	}

	@Override
	public byte[] getContent() {
		if (null == _byte) {
			_byte = _bao.toByteArray();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return _S__1F_NORMALCHATPACK;
	}

}