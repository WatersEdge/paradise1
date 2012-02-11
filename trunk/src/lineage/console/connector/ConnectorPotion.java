package lineage.console.connector;

import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 定义接口:(药水类)
 * 
 * @author jrwz
 */
public interface ConnectorPotion {

	/**
	 * 使用黑色药水 (失明药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	void useBlindPotion(final L1PcInstance pc, final int time);
}
