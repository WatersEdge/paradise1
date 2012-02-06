package lineage.console.connector;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 定义接口:(变身相关)
 * 
 * @author jrwz
 */
public interface ConnectorPoly {

	/**
	 * 使用变形卷轴
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param time
	 *            效果时间
	 */
	void usePolyScroll(final L1PcInstance pc, final L1ItemInstance item, final int time);

	/**
	 * 使用变形鳞片
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param time
	 *            效果时间
	 * @param polyId
	 *            变身编号
	 */
	void usePolyScale(final L1PcInstance pc, final L1ItemInstance item, final int time, final int polyId);

	/**
	 * 使用变形药水
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param time
	 *            效果时间
	 * @param polyId
	 *            变身编号
	 */
	void usePolyPotion(final L1PcInstance pc, final L1ItemInstance item, final int time, final int polyId);
}
