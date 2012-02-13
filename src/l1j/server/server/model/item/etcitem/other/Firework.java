package l1j.server.server.model.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_SkillSound;

/**
 * 烟花 (40136 - 40161)
 * 
 * @author jrwz
 */
public class Firework extends ItemExecutor {

	public static ItemExecutor get() {
		return new Firework();
	}

	private Firework() {
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

		final int itemId = item.getItemId();
		int soundid = 3198;

		switch (itemId) {
			case 40152: // 绿色烟火
				soundid = 2031;
				break;

			case 40154: // 圣诞烟火
				soundid = 3198;
				break;

			case 40141: // 蓝色烟火
				soundid = 2028;
				break;

			case 40145: // 红色烟火
				soundid = 2029;
				break;

			case 40159: // 黄色烟火
				soundid = 2033;
				break;

			case 40160: // 淡黄色烟火
				soundid = 2030;
				break;

			case 40151: // 淡绿色烟火
				soundid = 2032;
				break;

			case 40161: // 黄色心型烟火
				soundid = 2037;
				break;

			case 40142: // 蓝色心型烟火
				soundid = 2036;
				break;

			case 40146: // 红色心型烟火
				soundid = 2039;
				break;

			case 40148: // 绿色两段烟火
				soundid = 2043;
				break;

			case 40143: // 红色两段烟火
				soundid = 2041;
				break;

			case 40156: // 黄色两段烟火
				soundid = 2042;
				break;

			case 40139: // 蓝色两段烟火
				soundid = 2040;
				break;

			case 40136: // 三连发烟火
				soundid = 2046;
				break;

			case 40137: // 六连发烟火
				soundid = 2047;
				break;

			case 40138: // 高级六连发烟火
				soundid = 2048;
				break;

			case 40140: // 蓝色仙女棒
				soundid = 2051;
				break;

			case 40144: // 红色仙女棒
				soundid = 2053;
				break;

			case 40147: // 绿色两段圆形烟火
				soundid = 2045;
				break;

			case 40149: // 蓝色圆形烟火
				soundid = 2034;
				break;

			case 40150: // 绿色仙女棒
				soundid = 2055;
				break;

			case 40153: // 绿色心型烟火
				soundid = 2038;
				break;

			case 40155: // 黄色两段圆形烟火
				soundid = 2044;
				break;

			case 40157: // 黄色圆形烟火
				soundid = 2035;
				break;

			case 40158: // 黄色仙女棒
				soundid = 2049;
				break;

			default:
				soundid = 3198;
				break;
		}

		final S_SkillSound s_skillsound = new S_SkillSound(pc.getId(), soundid);
		pc.sendPackets(s_skillsound);
		pc.broadcastPacket(s_skillsound);
		pc.getInventory().removeItem(item, 1);
	}
}
