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
import l1j.server.server.model.L1NpcTalkData;
import l1j.server.server.model.npc.L1NpcHtml;

/**
 * NPC对话视窗
 */
public class S_NPCTalkReturn extends ServerBasePacket {

	private static final String _S__25_TalkReturn = "[S] _S__25_TalkReturn";

	private byte[] _byte = null;

	/**
	 * NPC对话视窗
	 * 
	 * @param objid
	 * @param html
	 */
	public S_NPCTalkReturn(final int objid, final L1NpcHtml html) {
		buildPacket(objid, html.getName(), html.getArgs());
	}

	/**
	 * NPC对话视窗
	 * 
	 * @param objid
	 * @param htmlid
	 */
	public S_NPCTalkReturn(final int objid, final String htmlid) {
		buildPacket(objid, htmlid, null);
	}

	/**
	 * NPC对话视窗
	 * 
	 * @param objid
	 * @param htmlid
	 * @param data
	 */
	public S_NPCTalkReturn(final int objid, final String htmlid, final String[] data) {
		buildPacket(objid, htmlid, data);
	}

	/**
	 * NPC对话视窗
	 * 
	 * @param npc
	 * @param objid
	 * @param action
	 */
	public S_NPCTalkReturn(final L1NpcTalkData npc, final int objid, final int action) {
		this(npc, objid, action, null);
	}

	/**
	 * NPC对话视窗
	 * 
	 * @param npc
	 * @param objid
	 * @param action
	 * @param data
	 */
	public S_NPCTalkReturn(final L1NpcTalkData npc, final int objid, final int action, final String[] data) {

		String htmlid = "";

		if (action == 1) {
			htmlid = npc.getNormalAction();
		}
		else if (action == 2) {
			htmlid = npc.getCaoticAction();
		}
		else {
			throw new IllegalArgumentException();
		}

		buildPacket(objid, htmlid, data);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return _S__25_TalkReturn;
	}

	private void buildPacket(final int objid, final String htmlid, final String[] data) {

		writeC(Opcodes.S_OPCODE_SHOWHTML);
		writeD(objid);
		writeS(htmlid);
		if ((data != null) && (1 <= data.length)) {
			writeH(0x01); // 如果有人知道请修复未知字节
			writeH(data.length); // 数的参数
			for (final String datum : data) {
				writeS(datum);
			}
		}
		else {
			writeH(0x00);
			writeH(0x00);
		}
	}
}
