package lineage.item.etcitem.other;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Letter;
import lineage.console.executor.ItemExecutor;

/**
 * (开封) <br>
 * 信纸 - 49017 <br>
 * 血盟的信纸 - 49019 <br>
 * 圣诞卡片 - 49021 <br>
 * 情人节 卡片 - 49023 <br>
 * 白色情人节 卡片 - 49025 <br>
 * 
 * @author jrwz
 */
public class Stationery_Open_1 extends ItemExecutor {

	public static ItemExecutor get() {
		return new Stationery_Open_1();
	}

	private Stationery_Open_1() {
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

		pc.sendPackets(new S_Letter(item));
	}
}
