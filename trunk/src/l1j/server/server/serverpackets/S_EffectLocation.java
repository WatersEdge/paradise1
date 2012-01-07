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
import l1j.server.server.model.L1Location;
import l1j.server.server.types.Point;

/**
 * 产生动画 (坐标)
 */
public class S_EffectLocation extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 在指定位置显示动画效果。
	 * 
	 * @param pt - 动画效果坐标 包含Point对象
	 * @param gfxId - 动画ID
	 */
	public S_EffectLocation(Point pt, int gfxId) {
		this(pt.getX(), pt.getY(), gfxId);
	}

	/**
	 * 在指定位置显示动画效果。
	 * 
	 * @param loc - 动画效果坐标 包含L1Location对象
	 * @param gfxId - 动画ID
	 */
	public S_EffectLocation(L1Location loc, int gfxId) {
		this(loc.getX(), loc.getY(), gfxId);
	}

	/**
	 * 在指定位置显示动画效果。
	 * 
	 * @param x - 动画效果X坐标
	 * @param y - 动画效果Y坐标
	 * @param gfxId - 动画ID
	 */
	public S_EffectLocation(int x, int y, int gfxId) {
		writeC(Opcodes.S_OPCODE_EFFECTLOCATION);
		writeH(x);
		writeH(y);
		writeH(gfxId);
		writeC(0);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}

		return _byte;
	}
}
