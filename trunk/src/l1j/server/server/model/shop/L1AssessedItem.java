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
package l1j.server.server.model.shop;

import l1j.server.server.datatables.RaceTicketTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.templates.L1RaceTicket;

/**
 * 评估道具
 */
public class L1AssessedItem {
	/** 目标ID */
	private final int _targetId;
	/** 评估价格 */
	private int _assessedPrice;

	L1AssessedItem(int targetId, int assessedPrice) {
		_targetId = targetId;
		L1ItemInstance item = (L1ItemInstance) L1World.getInstance().findObject(getTargetId());

		if (item.getItemId() == 40309) { // Race Tickets(食人妖精竞赛票)
			L1RaceTicket ticket = RaceTicketTable.getInstance().getTemplate(_targetId);

			int price = 0;
			if (ticket != null) {
				price = (int) (assessedPrice * ticket.get_allotment_percentage() * ticket.get_victory());
			}
			_assessedPrice = price;
		}
		else {
			_assessedPrice = assessedPrice;
		}
	}

	/** 获得目标ID */
	public int getTargetId() {
		return _targetId;
	}

	/** 获得评估价格 */
	public int getAssessedPrice() {
		return _assessedPrice;
	}
}
