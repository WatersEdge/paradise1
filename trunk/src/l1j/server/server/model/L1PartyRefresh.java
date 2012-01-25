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
package l1j.server.server.model;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Party;

/**
 * 刷新组队
 */
public class L1PartyRefresh extends TimerTask {

	private static Logger _log = Logger.getLogger(L1PartyRefresh.class.getName());

	private final L1PcInstance _pc;

	public L1PartyRefresh(L1PcInstance pc) {
		_pc = pc;
	}

	/**
	 * 3.3C 更新队伍封包
	 */
	public void fresh() {
		_pc.sendPackets(new S_Party(110, _pc));
	}

	@Override
	public void run() {
		try {
			if (_pc.isDead() || _pc.getParty() == null) {
				_pc.stopRefreshParty();
				return;
			}
			fresh();
		}
		catch (Throwable e) {
			_pc.stopRefreshParty();
			_log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
	}
}
