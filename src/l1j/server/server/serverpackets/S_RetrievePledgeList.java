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

import java.io.IOException;

import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 物品名单 (血盟仓库)
 */
public class S_RetrievePledgeList extends ServerBasePacket {

	/**
	 * 物品名单 (血盟仓库)
	 * 
	 * @param objid
	 * @param pc
	 */
	public S_RetrievePledgeList(int objid, L1PcInstance pc) {
		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		if (clan == null) {
			return;
		}

		if (clan.getWarehouseUsingChar() != 0 && clan.getWarehouseUsingChar() != pc.getId()) // 本血盟其他人在使用
		{
			pc.sendPackets(new S_ServerMessage(209)); // \f1血盟成员在使用仓库。请稍后再使用。
			return;
		}

		if (pc.getInventory().getSize() < 180) {
			int size = clan.getDwarfForClanInventory().getSize();
			if (size > 0) {
				clan.setWarehouseUsingChar(pc.getId()); // 锁定血盟仓库
				writeC(Opcodes.S_OPCODE_SHOWRETRIEVELIST);
				writeD(objid);
				writeH(size);
				writeC(5); // 血盟仓库
				for (Object itemObject : clan.getDwarfForClanInventory().getItems()) {
					L1ItemInstance item = (L1ItemInstance) itemObject;
					writeD(item.getId());
					writeC(0);
					writeH(item.get_gfxid());
					writeC(item.getBless());
					writeD(item.getCount());
					writeC(item.isIdentified() ? 1 : 0);
					writeS(item.getViewName());
				}
				writeH(0x001e); // 金币30
			}
			else {
				pc.sendPackets(new S_ServerMessage(1625)); // 仓库里没有委托的物品。
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(263)); // \f1一个角色最多可携带180个道具。
		}
	}

	@Override
	public byte[] getContent() throws IOException {
		return getBytes();
	}
}
