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
package l1j.server.server;

import l1j.server.server.datatables.LightSpawnTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.gametime.L1GameTimeClock;

/**
 * 光照时间控制器
 */
public class LightTimeController implements Runnable {

	private static LightTimeController _instance;

	public static LightTimeController getInstance() {
		if (_instance == null) {
			_instance = new LightTimeController();
		}
		return _instance;
	}

	private boolean isSpawn = false;

	@Override
	public void run() {
		try {
			while (true) {
				checkLightTime();
				Thread.sleep(60000);
			}
		}
		catch (final Exception e1) {
		}
	}

	// 检查照明时间
	private void checkLightTime() {
		final int serverTime = L1GameTimeClock.getInstance().currentTime().getSeconds();
		final int nowTime = serverTime % 86400;
		if ((nowTime >= ((5 * 3600) + 3300)) && (nowTime < ((17 * 3600) + 3300))) { // 5:55~17:55
			if (isSpawn) {
				isSpawn = false;
				for (final L1Object object : L1World.getInstance().getObject()) {
					if (object instanceof L1FieldObjectInstance) {
						final L1FieldObjectInstance npc = (L1FieldObjectInstance) object;
						if (((npc.getNpcTemplate().get_npcId() == 81177 // 火窟(小)
								)
								|| (npc.getNpcTemplate().get_npcId() == 81178 // 火窟(中)
								) || (npc.getNpcTemplate().get_npcId() == 81179 // 火窟(小底座阴影)
								) || (npc.getNpcTemplate().get_npcId() == 81180 // 火窟(大)
								) || (npc.getNpcTemplate().get_npcId() == 81181 // 火窟(小脚)
								))
								&& ((npc.getMapId() == 0) || (npc.getMapId() == 4))) { // 说话岛与全大陆
							npc.deleteMe();
						}
					}
				}
			}
		}
		else if (((nowTime >= ((17 * 3600) + 3300)) && (nowTime <= 24 * 3600)) || ((nowTime >= 0 * 3600) && (nowTime < ((5 * 3600) + 3300)))) { // 17:55~24:00,0:00~5:55
			if (!isSpawn) {
				isSpawn = true;
				LightSpawnTable.getInstance();
			}
		}
	}

}
