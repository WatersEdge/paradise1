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

import java.util.List;

import l1j.server.server.Opcodes;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.shop.L1AssessedItem;
import l1j.server.server.model.shop.L1Shop;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket, S_SystemMessage

/**
 * 商店收购清单
 */
public class S_ShopBuyList extends ServerBasePacket {

	private static final String S_SHOP_BUY_LIST = "[S] S_ShopBuyList";

	public S_ShopBuyList(final int objid, final L1PcInstance pc) {
		final L1Object object = L1World.getInstance().findObject(objid);
		if (!(object instanceof L1NpcInstance)) {
			return;
		}
		final L1NpcInstance npc = (L1NpcInstance) object;
		final int npcId = npc.getNpcTemplate().get_npcId();
		final L1Shop shop = ShopTable.getInstance().get(npcId);
		if (shop == null) {
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		final List<L1AssessedItem> assessedItems = shop.assessItems(pc.getInventory());
		if (assessedItems.isEmpty()) {
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		writeC(Opcodes.S_OPCODE_SHOWSHOPSELLLIST);
		writeD(objid);
		writeH(assessedItems.size());

		for (final L1AssessedItem item : assessedItems) {
			writeD(item.getTargetId());
			writeD(item.getAssessedPrice());
		}
		writeH(0x0007); // 7 = 金币为单位 显示总金额
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_SHOP_BUY_LIST;
	}
}