package lineage.console.connector;

import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 定义接口:(药水类)
 * 
 * @author jrwz
 */
public interface ConnectorPotion {

	/**
	 * 使用一段加速药水 (绿色药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useGreenPotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 使用二段加速药水 (勇敢药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useBravePotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 使用二段加速药水 (精灵饼干)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useElfBravePotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 使用二段加速药水 (生命之树果实)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useRiBravePotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 使用三段加速药水 (巧克力蛋糕)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useThirdSpeedPotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 使用治愈类药水 (红色药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param healHp
	 *            增加血量
	 * @param gfxid
	 *            动画ID
	 */
	void useHeallingPotion(L1PcInstance pc, int healHp, int gfxid);

	/**
	 * 使用加魔类药水 (月饼)
	 * 
	 * @param pc
	 *            使用对象
	 * @param newMp
	 *            增加魔量
	 * @param gfxid
	 *            动画ID
	 */
	void useMpPotion(L1PcInstance pc, int newMp, int gfxid);

	/**
	 * 恢复魔力药水 (蓝色药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useBluePotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 增加魔攻药水 (智慧药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 * @param gfxid
	 *            动画ID
	 */
	void useWisdomPotion(L1PcInstance pc, int time, int gfxid);

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
	void useBlessOfEvaPotion(L1PcInstance pc, int time, int gfxid);

	/**
	 * 使用黑色药水 (失明药水)
	 * 
	 * @param pc
	 *            使用对象
	 * @param time
	 *            效果时间
	 */
	void useBlindPotion(L1PcInstance pc, int time);
}
