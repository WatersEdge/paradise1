package lineage.console.connector.cite;

import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.connector.UniversalUseItem;
import lineage.console.utils.CheckUtil;

/**
 * 变身效果 (变形卷轴、变形药水等直接变身道具)
 * 
 * @author jrwz
 */
public class UsePolyOther implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		// 不能变身的状态
		if (!CheckUtil.checkPoly(pc)) {
			return;
		}

		L1PolyMorph.doPoly(pc, effect, time, L1PolyMorph.MORPH_BY_ITEMMAGIC);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}

}
