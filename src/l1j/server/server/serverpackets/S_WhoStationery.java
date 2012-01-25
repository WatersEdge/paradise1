package l1j.server.server.serverpackets;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.L1GameReStart;

/**
 * 玩家输入WHO显示信息为布告栏(讯息阅读)模式
 */
public class S_WhoStationery extends ServerBasePacket {

	private static final String _S_WhoStationery = "[S] _S_WhoStationery";

	private byte[] _byte = null;

	/**
	 * 玩家输入WHO显示信息为布告栏(讯息阅读)模式
	 * 
	 * @param pc
	 *            查询的玩家
	 */
	public S_WhoStationery(L1PcInstance pc) {

		double EXP = Config.RATE_XP;
		double RWL = Config.RATE_WEIGHT_LIMIT;
		double RDI = Config.RATE_DROP_ITEMS;
		double RDA = Config.RATE_DROP_ADENA;
		double RLA = Config.RATE_LA;
		double RKA = Config.RATE_KARMA;
		int RKC = pc.get_PKcount();
		int time = L1GameReStart.getWillRestartTime();

		final String S_WhoCharinfo = "经验倍率:" + EXP + " 倍\r\n" + "负重倍率:" + RWL + " 倍\r\n" + "掉宝倍率:" + RDI + " 倍\r\n" + "金币倍率:" + RDA + " 倍\r\n" + "正义倍率:" + RLA + " 倍\r\n" + "友好倍率:" + RKA + " 倍\r\n" + "总PK次数:" + RKC + " 次\r\n" + "距离重启时间剩余:" + (time / 60) / 60 + "小时" + (time / 60)
				% 60 + "分钟";

		// 当前的 年、月、日 (范例:12/01/10)
		SimpleDateFormat setDateFormat = new SimpleDateFormat("yy/MM/dd");
		writeC(Opcodes.S_OPCODE_BOARDREAD);
		writeD(0x00);
		writeS("Lineage"); // 作者
		writeS("系统讯息"); // 标题
		writeS(setDateFormat.format(Calendar.getInstance().getTime())); // 讨论编号
		writeS(S_WhoCharinfo); // 显示查询信息
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return _S_WhoStationery;
	}

}
