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
 * 魔法效果：力量提升
 */
public class S_Strup extends ServerBasePacket {

	/**
	 * 魔法效果：力量提升
	 * 
	 * @param pc
	 *            使用者
	 * @param type
	 *            增加值
	 * @param time
	 *            时间
	 */
	public S_Strup(final L1PcInstance pc, final int type, final int time) {
		this.writeC(Opcodes.S_OPCODE_STRUP);
		this.writeH(time);
		this.writeC(pc.getStr());
		this.writeC(pc.getInventory().getWeight242());
		this.writeC(type);
		this.writeD(0);
	}

	@Override
	public byte[] getContent() {
		return this.getBytes();
	}

	@Override
	public String getType() {
		return "[S] S_Strup";
	}
}
