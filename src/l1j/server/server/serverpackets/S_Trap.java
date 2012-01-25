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

import java.io.IOException;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1TrapInstance;

/**
 * 物件封包 - 陷阱 (GM测试用)
 */
public class S_Trap extends ServerBasePacket {

	/**
	 * 物件封包 - 陷阱 (GM测试用)
	 * 
	 * @param trap
	 * @param name
	 */
	public S_Trap(L1TrapInstance trap, String name) {

		writeC(Opcodes.S_OPCODE_DROPITEM);
		writeH(trap.getX());
		writeH(trap.getY());
		writeD(trap.getId());
		writeH(7); // adena
		writeC(0); // 物件外观属性
		writeC(0); // 方向
		writeC(0); // 亮度 0:normal, 1:fast, 2:slow
		writeC(0); // 速度
		writeD(0); // 数量 经验值
		writeC(0); //
		writeC(0); //
		writeS(name); // 名称
		writeC(0); //
		writeD(0); //
		writeD(0); //
		writeC(255); //
		writeC(0); //
		writeC(0); //
		writeC(0); //
		writeH(65535); //
		// writeD(0x401799a);
		writeD(0); //
		writeC(8); //
		writeC(0); //
	}

	@Override
	public byte[] getContent() throws IOException {
		return getBytes();
	}
}
