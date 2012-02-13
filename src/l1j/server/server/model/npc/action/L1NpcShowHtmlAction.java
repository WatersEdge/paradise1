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

import java.util.List;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.npc.L1NpcHtml;
import l1j.server.server.utils.IterableElementList;
import l1j.server.server.utils.collections.Lists;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class L1NpcShowHtmlAction extends L1NpcXmlAction {

	private final String _htmlId;

	private final String[] _args;

	public L1NpcShowHtmlAction(final Element element) {
		super(element);

		this._htmlId = element.getAttribute("HtmlId");
		final NodeList list = element.getChildNodes();
		final List<String> dataList = Lists.newList();
		for (final Element elem : new IterableElementList(list)) {
			if (elem.getNodeName().equalsIgnoreCase("Data")) {
				dataList.add(elem.getAttribute("Value"));
			}
		}
		this._args = dataList.toArray(new String[dataList.size()]);
	}

	@Override
	public L1NpcHtml execute(final String actionName, final L1PcInstance pc, final L1Object obj, final byte[] args) {
		return new L1NpcHtml(this._htmlId, this._args);
	}

}
