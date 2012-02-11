package lineage.console.connector.cite;

import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.UniversalUseItem;

/**
 * 变身效果 (变形卷轴)
 * 
 * @author jrwz
 */
public class UsePolyScroll implements UniversalUseItem {

	@Override
	public void useItem(L1PcInstance pc, L1ItemInstance item, int itemId, int effect, int time, int gfxid) {

		// 取回字符串
		final String text = pc.getText();
		// 清空字符串
		pc.setText(null);

		final L1PolyMorph poly = PolyTable.getInstance().getTemplate(text);

		if ((poly != null) || text.equals("")) {
			if (text.equals("")) {
				pc.removeSkillEffect(SHAPE_CHANGE);
			}
			else if ((poly.getMinLevel() <= pc.getLevel()) || pc.isGm()) {
				L1PolyMorph.doPoly(pc, poly.getPolyId(), time, L1PolyMorph.MORPH_BY_ITEMMAGIC);
			}
			pc.getInventory().removeItem(item, 1);
		}
		else {
			pc.sendPackets(new S_ServerMessage(181)); // \f1无法变成你指定的怪物。
		}
	}

}
