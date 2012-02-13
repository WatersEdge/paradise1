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
package l1j.server.server.model.npc.action;

import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.identity.L1ItemId;
import l1j.server.server.model.npc.L1NpcHtml;
import l1j.server.server.serverpackets.S_ServerMessage;

import org.w3c.dom.Element;

/**
 * NPC传送动作
 */
public class L1NpcTeleportAction extends L1NpcXmlAction {

	private final L1Location _loc;
	private final int _heading;
	private final int _price;
	private final boolean _effect;

	public L1NpcTeleportAction(final Element element) {
		super(element);

		final int x = L1NpcXmlParser.getIntAttribute(element, "X", -1);
		final int y = L1NpcXmlParser.getIntAttribute(element, "Y", -1);
		final int mapId = L1NpcXmlParser.getIntAttribute(element, "Map", -1);
		_loc = new L1Location(x, y, mapId);

		_heading = L1NpcXmlParser.getIntAttribute(element, "Heading", 5);

		_price = L1NpcXmlParser.getIntAttribute(element, "Price", 0);
		_effect = L1NpcXmlParser.getBoolAttribute(element, "Effect", true);
	}

	@Override
	public L1NpcHtml execute(final String actionName, final L1PcInstance pc, final L1Object obj, final byte[] args) {
		if (!pc.getInventory().checkItem(L1ItemId.ADENA, _price)) {
			pc.sendPackets(new S_ServerMessage(337, "$4")); // \f1%0不足%s。
			return L1NpcHtml.HTML_CLOSE;
		}
		pc.getInventory().consumeItem(L1ItemId.ADENA, _price);
		L1Teleport.teleport(pc, _loc.getX(), _loc.getY(), (short) _loc.getMapId(), _heading, _effect);
		return null;
	}

}
