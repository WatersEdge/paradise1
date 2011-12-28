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

import static l1j.server.server.model.skill.L1SkillId.STATUS_CHAT_PROHIBITED;

import java.util.StringTokenizer;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SystemMessage;

/**
 * GM指令：禁言
 */
public class L1ChatNG implements L1CommandExecutor {
	private L1ChatNG() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1ChatNG();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			StringTokenizer st = new StringTokenizer(arg);
			String name = st.nextToken();
			int time = Integer.parseInt(st.nextToken());

			L1PcInstance tg = L1World.getInstance().getPlayer(name);

			if (tg != null) {
				tg.setSkillEffect(STATUS_CHAT_PROHIBITED, time * 60 * 1000);
				tg.sendPackets(new S_SkillIconGFX(36, time * 60));
				tg.sendPackets(new S_ServerMessage(286, String.valueOf(time))); // \f3因你不正当的行为而 %0分钟之内无法交谈。
				pc.sendPackets(new S_ServerMessage(287, name)); // 你已经被禁止交谈%0 分钟。
			}
		}
		catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("请输入 " + cmdName + " 玩家名称 时间(分)。"));
		}
	}
}