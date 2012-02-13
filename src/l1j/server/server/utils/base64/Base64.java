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
package l1j.server.server.utils.base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * BASE64 encode & decode for Binary & Text
 */
public class Base64 {

	class Shared {

		static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

		static final char pad = '=';

	}

	/**
	 * <b> Decodes Binary
	 * 
	 * @param bytes
	 *            来源位元组
	 * @return 解码后的位元组
	 * @throws RuntimeException
	 */
	public static byte[] decode(byte[] bytes) throws RuntimeException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			decode(inputStream, outputStream);
		}
		catch (IOException e) {
			throw new RuntimeException("Unexpected I/O error", e);
		} finally {
			try {
				inputStream.close();
			}
			catch (Throwable t) {

			}
			try {
				outputStream.close();
			}
			catch (Throwable t) {

			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * <b> 将来源串流解码后，输出到目标串流
	 * 
	 * @param inputStream
	 *            来源串流
	 * @param outputStream
	 *            目标串流
	 * @throws IOException
	 */
	public static void decode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			decode(inputStream, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (Throwable t) {

				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (Throwable t) {

				}
			}
		}
	}

	public static void decode(InputStream inputStream, OutputStream outputStream) throws IOException {
		copy(new Base64InputStream(inputStream), outputStream);
	}

	/**
	 * <b> Decodes String
	 * 
	 * @param str
	 *            来源字串
	 * @return 解码后的字串
	 * @throws RuntimeException
	 */
	public static String decode(String str) throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes("ASCII");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
		byte[] decoded = decode(bytes);
		return new String(decoded);
	}

	/**
	 * <b> Decodes String
	 * 
	 * @param str
	 *            来源字串
	 * @param charset
	 *            字串编码
	 * @return 解码后的字串
	 * @throws RuntimeException
	 */
	public static String decode(String str, String charset) throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes("ASCII");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
		byte[] decoded = decode(bytes);
		try {
			return new String(decoded, charset);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported charset: " + charset, e);
		}
	}

	/**
	 * <b> Encodes Binary
	 * 
	 * @param bytes
	 *            来源位元组
	 * @return 编码后的位元组
	 * @throws RuntimeException
	 */
	public static byte[] encode(byte[] bytes) throws RuntimeException {
		return encode(bytes, 0);
	}

	/**
	 * <b> Encodes Binary
	 * 
	 * @param bytes
	 *            来源位元组
	 * @param wrapAt
	 * @return 编码后的位元组
	 * @throws RuntimeException
	 */
	public static byte[] encode(byte[] bytes, int wrapAt) throws RuntimeException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			encode(inputStream, outputStream, wrapAt);
		}
		catch (IOException e) {
			throw new RuntimeException("Unexpected I/O error", e);
		} finally {
			try {
				inputStream.close();
			}
			catch (Throwable t) {

			}
			try {
				outputStream.close();
			}
			catch (Throwable t) {

			}
		}
		return outputStream.toByteArray();
	}

	public static void encode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64.encode(inputStream, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (Throwable t) {

				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (Throwable t) {

				}
			}
		}
	}

	public static void encode(File source, File target, int wrapAt) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64.encode(inputStream, outputStream, wrapAt);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (Throwable t) {

				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (Throwable t) {

				}
			}
		}
	}

	/**
	 * <b> 将来源串流编码后，输出到目标串流
	 * 
	 * @param inputStream
	 *            来源串流
	 * @param outputStream
	 *            目标串流
	 * @throws IOException
	 */
	public static void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
		encode(inputStream, outputStream, 0);
	}

	public static void encode(InputStream inputStream, OutputStream outputStream, int wrapAt) throws IOException {
		Base64OutputStream aux = new Base64OutputStream(outputStream, wrapAt);
		copy(inputStream, aux);
		aux.commit();
	}

	/**
	 * <b> Encodes String
	 * 
	 * @param str
	 *            来源字串
	 * @return 编码后的字串
	 * @throws RuntimeException
	 */
	public static String encode(String str) throws RuntimeException {
		byte[] bytes = str.getBytes();
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}

	/**
	 * <b> Encodes String
	 * 
	 * @param str
	 *            来源字串
	 * @param charset
	 *            字串的编码
	 * @return 编码后的字串
	 * @throws RuntimeException
	 */
	public static String encode(String str, String charset) throws RuntimeException {
		byte[] bytes;
		try {
			bytes = str.getBytes(charset);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported charset: " + charset, e);
		}
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}

	private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
		// 1KB buffer
		byte[] b = new byte[1024];
		int len;
		while ((len = inputStream.read(b)) != -1) {
			outputStream.write(b, 0, len);
		}
	}
}
