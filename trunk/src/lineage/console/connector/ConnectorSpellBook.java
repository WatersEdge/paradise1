package lineage.console.connector;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 定义接口:(技能书)
 * 
 * @author jrwz
 */
public interface ConnectorSpellBook {

	/**
	 * 使用技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId);

	/**
	 * 使用王族技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param l1iteminstance
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useCrownSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance, final int itemId);

	/**
	 * 使用精灵技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useElfSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId);

	/**
	 * 使用黑暗精灵技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useDarkElfSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId);

	/**
	 * 使用骑士技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useKnightSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId);

	/**
	 * 使用龙骑士技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useDragonKnightSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId);

	/**
	 * 使用幻术师技能书
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param itemId
	 *            道具编号
	 */
	void useIllusionistSpellBook(final L1PcInstance pc, final L1ItemInstance item, final int itemId);

	/**
	 * 法师技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 * @param isLawful
	 *            动画效果:正义、邪恶 ? 224 : 231
	 */
	void SpellBook(final L1PcInstance pc, final L1ItemInstance item, final boolean isLawful);

	/**
	 * 王族技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 */
	void CrownSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance);

	/**
	 * 精灵技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 */
	void ElfSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance);

	/**
	 * 黑暗精灵技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 */
	void DarkElfSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance);

	/**
	 * 骑士技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 */
	void KnightSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance);

	/**
	 * 龙骑士技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 */
	void DragonKnightSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance);

	/**
	 * 幻术师技能
	 * 
	 * @param pc
	 *            使用对象
	 * @param item
	 *            使用道具
	 */
	void IllusionistSpellBook(final L1PcInstance pc, final L1ItemInstance l1iteminstance);
}
