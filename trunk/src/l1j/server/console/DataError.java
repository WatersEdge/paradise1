package l1j.server.console;

import l1j.server.Config;

import org.apache.commons.logging.Log;

/**
 * 错误讯息
 * 
 * @author jrwz
 * 
 */
public class DataError {

	/** 侦错模式 */
	private static boolean _debug = Config.DEBUG;

	/**
	 * 错误讯息
	 * 
	 * @param log
	 * @param string
	 * @param e
	 */
	public static void isError(final Log log, final String string, final Exception e) {
		if (_debug) {
			log.error(string, e);
		}
	}
}
