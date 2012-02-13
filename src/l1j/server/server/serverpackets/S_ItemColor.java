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
import l1j.server.server.model.Instance.L1ItemInstance;

/**
 * 物品属性(色彩)状态 (背包)
 */
public class S_ItemColor extends ServerBasePacket {

	private static final String S_ITEM_COLOR = "[S] S_ItemColor";

	/**
	 * 改变道具颜色。祝福・诅咒状态变化时发送。
	 */
	public S_ItemColor(final L1ItemInstance item) {
		if (item == null) {
			return;
		}
		buildPacket(item);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_ITEM_COLOR;
	}

	private void buildPacket(final L1ItemInstance item) {
		writeC(Opcodes.S_OPCODE_ITEMCOLOR);
		writeD(item.getId());
		// 0:祝福 1:一般 2:诅咒 3:未鉴定
		// 128:祝福&封印 129:&封印 130:诅咒&封印 131:未鉴定&封印
		writeC(item.getBless());
	}

}
