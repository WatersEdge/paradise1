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
package l1j.server.server.clientpackets;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.ClientThread;

/**
 * 解析客户端封包
 */
public abstract class ClientBasePacket {

	/** 提示信息 */
	private static Logger _log = Logger.getLogger(ClientBasePacket.class.getName());

	/** 客户端编码 */
	private static final String CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;

	private byte _decrypt[];

	private int _off;

	/**
	 * 载入 byte 阵列
	 * 
	 * @param abyte0
	 */
	public ClientBasePacket(byte abyte0[]) {
		_log.finest("type=" + getType() + ", len=" + abyte0.length);
		_decrypt = abyte0;
		_off = 1;
	}

	public ClientBasePacket(ByteBuffer bytebuffer, ClientThread clientthread) {
	}

	/**
	 * 由byte[]中取回一个 int
	 * 
	 * @return
	 */
	public int readD() {
		int i = _decrypt[_off++] & 0xff;
		i |= _decrypt[_off++] << 8 & 0xff00;
		i |= _decrypt[_off++] << 16 & 0xff0000;
		i |= _decrypt[_off++] << 24 & 0xff000000;
		return i;
	}

	/**
	 * 由byte[]中取回一个 byte
	 * 
	 * @return
	 */
	public int readC() {
		int i = _decrypt[_off++] & 0xff;
		return i;
	}

	/**
	 * 由byte[]中取回一个 short
	 * 
	 * @return
	 */
	public int readH() {
		int i = _decrypt[_off++] & 0xff;
		i |= _decrypt[_off++] << 8 & 0xff00;
		return i;
	}

	/**
	 * 由byte[]中取回一个 short
	 * 
	 * @return
	 */
	public int readCH() {
		int i = _decrypt[_off++] & 0xff;
		i |= _decrypt[_off++] << 8 & 0xff00;
		i |= _decrypt[_off++] << 16 & 0xff0000;
		return i;
	}

	/**
	 * 由byte[]中取回一个 double
	 * 
	 * @return
	 */
	public double readF() {
		long l = _decrypt[_off++] & 0xff;
		l |= _decrypt[_off++] << 8 & 0xff00;
		l |= _decrypt[_off++] << 16 & 0xff0000;
		l |= _decrypt[_off++] << 24 & 0xff000000;
		l |= (long) _decrypt[_off++] << 32 & 0xff00000000L;
		l |= (long) _decrypt[_off++] << 40 & 0xff0000000000L;
		l |= (long) _decrypt[_off++] << 48 & 0xff000000000000L;
		l |= (long) _decrypt[_off++] << 56 & 0xff00000000000000L;
		return Double.longBitsToDouble(l);
	}

	/**
	 * 由byte[]中取回一个 String
	 * 
	 * @return
	 */
	public String readS() {
		String s = null;
		try {
			s = new String(_decrypt, _off, _decrypt.length - _off, CLIENT_LANGUAGE_CODE);
			s = s.substring(0, s.indexOf('\0'));
			_off += s.getBytes(CLIENT_LANGUAGE_CODE).length + 1;
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, "OpCode=" + (_decrypt[0] & 0xff), e);
		}
		return s;
	}

	/**
	 * 由byte[]中取回一组byte[]
	 * 
	 * @return
	 */
	public byte[] readByte() {
		byte[] result = new byte[_decrypt.length - _off];
		try {
			System.arraycopy(_decrypt, _off, result, 0, _decrypt.length - _off);
			_off = _decrypt.length;
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, "OpCode=" + (_decrypt[0] & 0xff), e);
		}
		return result;
	}

	/**
	 * 返回一个字符串，表示客户端的数据包类型。("[C] C_DropItem" 等)
	 */
	public String getType() {
		return "[C] " + this.getClass().getSimpleName();
	}
}
