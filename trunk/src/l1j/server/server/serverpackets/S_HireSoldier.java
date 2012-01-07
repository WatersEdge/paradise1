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
import l1j.server.server.model.Instance.L1PcInstance;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

/**
 * 雇佣佣兵数量清单
 */
public class S_HireSoldier extends ServerBasePacket {

	private static final String S_HIRE_SOLDIER = "[S] S_HireSldier";

	private byte[] _byte = null;

	// 表示HTML打开后发送的数据包npcdeloy-j.html
	// 按OK按钮飞C_127
	public S_HireSoldier(L1PcInstance pc) {
		writeC(Opcodes.S_OPCODE_HIRESOLDIER);
		writeH(0); // ? 包含客户端返回的数据包
		writeH(0); // ? 包含客户端返回的数据包
		writeH(0); // 雇佣佣兵的总数
		writeS(pc.getName());
		writeD(0); // ? 包含客户端返回的数据包
		writeH(0); // 可以雇佣佣兵的总数
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
		return S_HIRE_SOLDIER;
	}
}
