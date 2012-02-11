package lineage.console.connector;

import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 定义接口:(药水类)
 * 
 * @author jrwz
 */
public interface ConnectorPotion {

	/**
	 * 使用增加魔力类药水 (月饼)
	 * 
	 * @param pc
	 *            使用对象
	 * @param newMp
	 *            增加魔量
	 * @param gfxid
	 *            动画ID
	 */
	void useAddMpPotion(final L1PcInstance pc, final int newMp, final int gfxid);

	/**
	 * 使用恢复魔力类药水 (蓝色药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useBluePotion(final L1PcInstance pc, final int time, final int gfxid);

	/**
	 * 使用增加魔攻类药水 (智慧药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useWisdomPotion(final L1PcInstance pc, final int time, final int gfxid);

	/**
	 * 可以在水中呼吸的药水 (伊娃的祝福)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useBlessOfEvaPotion(final L1PcInstance pc, final int time, final int gfxid);

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
