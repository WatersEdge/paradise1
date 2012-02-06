package lineage.console.connector.cite;

import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.connector.ConnectorPoly;
import lineage.console.utils.CheckUtil;

/**
 * 实例化 (接口引用:变身相关)
 * 
 * @author jrwz
 */
public class CiteConnectorPoly implements ConnectorPoly {

	@Override
	public void usePolyScroll(final L1PcInstance pc, final L1ItemInstance item, final int time) {

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

	@Override
	public void usePolyScale(final L1PcInstance pc, final L1ItemInstance item, final int time, final int polyId) {

		// 不能变身的状态
		if (!CheckUtil.checkPoly(pc)) {
			return;
		}

		L1PolyMorph.doPoly(pc, polyId, time, L1PolyMorph.MORPH_BY_ITEMMAGIC);

		// 删除道具
		pc.getInventory().removeItem(item, 1);

	}

	@Override
	public void usePolyPotion(final L1PcInstance pc, final L1ItemInstance item, final int time, final int polyId) {

		// 不能变身的状态
		if (!CheckUtil.checkPoly(pc)) {
			return;
		}

		L1PolyMorph.doPoly(pc, polyId, time, L1PolyMorph.MORPH_BY_ITEMMAGIC);

		// 删除道具
		pc.getInventory().removeItem(item, 1);
	}
}
