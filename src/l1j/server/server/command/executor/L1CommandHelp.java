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
package l1j.server.server.command.executor;

import java.util.List;

import l1j.server.server.command.L1Commands;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Command;

/**
 * GM指令：取得所有指令
 */
public class L1CommandHelp implements L1CommandExecutor {
	public static L1CommandExecutor getInstance() {
		return new L1CommandHelp();
	}

	private L1CommandHelp() {
	}

	@Override
	public void execute(final L1PcInstance pc, final String cmdName, final String arg) {
		final List<L1Command> list = L1Commands.availableCommandList(pc.getAccessLevel());
		pc.sendPackets(new S_SystemMessage(this.join(list, ", ")));
	}

	private String join(final List<L1Command> list, final String with) {
		final StringBuilder result = new StringBuilder();
		for (final L1Command cmd : list) {
			if (result.length() > 0) {
				result.append(with);
			}
			result.append(cmd.getName());
		}
		return result.toString();
	}
}
