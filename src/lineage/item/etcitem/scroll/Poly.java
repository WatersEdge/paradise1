package lineage.item.etcitem.scroll;

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import lineage.console.executor.ItemExecutor;

/**
 * 变形卷轴 - 40088<br>
 * 象牙塔变形卷轴 - 40096<br>
 * 受祝福的 变形卷轴 - 140088<br>
 * 福利变形药水 - 49308<br>
 * 
 * @author jrwz
 */
public class Poly extends ItemExecutor {

	private Poly() {
	}

	public static ItemExecutor get() {
		return new Poly();
	}

	/**
	 * 道具执行
	 * 
	 * @param data
	 *            参数
	 * @param pc
	 *            对象
	 * @param item
	 *            道具
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {

		// 取回字符串
		final String text = pc.getText();
		// 清空字符串
		pc.setText(null);
		// 取回道具ID
		final int itemId = item.getItemId();
		// 时间
		int time = 0;

		switch (itemId) {
			case 40088: // 变形卷轴
			case 40096: // 象牙塔变身卷轴
				time = 1800;
				break;

			case 140088: // 受祝福的 变形卷轴
				time = 2100;
				break;
		}

		// 取回觉醒技能ID
		final int awakeSkillId = pc.getAwakeSkillId();
		if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
			pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身
			return;
		}

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
