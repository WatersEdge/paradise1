/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package l1j.server.server.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import l1j.server.server.datatables.BossSpawnTable;
import l1j.server.server.utils.PerformanceTimer;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.collections.Maps;

@XmlAccessorType(XmlAccessType.FIELD)
public class L1BossCycle {

	private static Logger _log = Logger.getLogger(L1BossCycle.class.getName());

	@XmlAttribute(name = "Name")
	private String _name;

	@XmlElement(name = "Base")
	private Base _base;

	@XmlAccessorType(XmlAccessType.FIELD)
	private static class Base {
		@XmlAttribute(name = "Date")
		private String _date;

		@XmlAttribute(name = "Time")
		private String _time;

		public String getDate() {
			return _date;
		}

		public void setDate(String date) {
			_date = date;
		}

		public String getTime() {
			return _time;
		}

		public void setTime(String time) {
			_time = time;
		}
	}

	@XmlElement(name = "Cycle")
	private Cycle _cycle;

	@XmlAccessorType(XmlAccessType.FIELD)
	private static class Cycle {
		@XmlAttribute(name = "Period")
		private String _period;

		@XmlAttribute(name = "Start")
		private String _start;

		@XmlAttribute(name = "End")
		private String _end;

		public String getPeriod() {
			return _period;
		}

		public String getStart() {
			return _start;
		}

		public String getEnd() {
			return _end;
		}
	}

	private Calendar _baseDate;

	private int _period; // 分換算

	private int _periodDay;

	private int _periodHour;

	private int _periodMinute;

	private int _startTime; // 分換算

	private int _endTime; // 分換算

	private static SimpleDateFormat _sdfYmd = new SimpleDateFormat("yyyy/MM/dd");

	private static SimpleDateFormat _sdfTime = new SimpleDateFormat("HH:mm");

	private static SimpleDateFormat _sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	private static Date _initDate = new Date();

	private static String _initTime = "0:00";

	private static final Calendar START_UP = Calendar.getInstance();

	public void init() throws Exception {
		// 参考日期和时间设置
		Base base = getBase();
		// 如果没有参考标准、以当前的日期0:00标准
		if (base == null) {
			setBase(new Base());
			getBase().setDate(_sdfYmd.format(_initDate));
			getBase().setTime(_initTime);
			base = getBase();
		}
		else {
			try {
				_sdfYmd.parse(base.getDate());
			}
			catch (Exception e) {
				base.setDate(_sdfYmd.format(_initDate));
			}
			try {
				_sdfTime.parse(base.getTime());
			}
			catch (Exception e) {
				base.setTime(_initTime);
			}
		}
		// 确定时间参考
		Calendar baseCal = Calendar.getInstance();
		baseCal.setTime(_sdf.parse(base.getDate() + " " + base.getTime()));

		// 出现周期初始化,检查
		Cycle spawn = getCycle();
		if ((spawn == null) || (spawn.getPeriod() == null)) {
			throw new Exception("CycleのPeriodは必須");
		}

		String period = spawn.getPeriod();
		_periodDay = getTimeParse(period, "d");
		_periodHour = getTimeParse(period, "h");
		_periodMinute = getTimeParse(period, "m");

		String start = spawn.getStart();
		int sDay = getTimeParse(start, "d");
		int sHour = getTimeParse(start, "h");
		int sMinute = getTimeParse(start, "m");
		String end = spawn.getEnd();
		int eDay = getTimeParse(end, "d");
		int eHour = getTimeParse(end, "h");
		int eMinute = getTimeParse(end, "m");

		// 分換算
		_period = (_periodDay * 24 * 60) + (_periodHour * 60) + _periodMinute;
		_startTime = (sDay * 24 * 60) + (sHour * 60) + sMinute;
		_endTime = (eDay * 24 * 60) + (eHour * 60) + eMinute;
		if (_period <= 0) {
			throw new Exception("must be Period > 0");
		}
		// start補正
		if ((_startTime < 0) || (_period < _startTime)) { // 補正
			_startTime = 0;
		}
		// end補正
		if ((_endTime < 0) || (_period < _endTime) || (end == null)) { // 補正
			_endTime = _period;
		}
		if (_startTime > _endTime) {
			_startTime = _endTime;
		}
		// start,endの相関補正(最低1分钟的间隔开启)
		// start==end还指定、出現時間が次の周期に被らないようにするため
		if (_startTime == _endTime) {
			if (_endTime == _period) {
				_startTime--;
			}
			else {
				_endTime++;
			}
		}

		// 最近の周期まで補正(再計算するときに厳密に算出するので、ここでは近くまで適当に補正するだけ)
		while (!(baseCal.after(START_UP))) {
			baseCal.add(Calendar.DAY_OF_MONTH, _periodDay);
			baseCal.add(Calendar.HOUR_OF_DAY, _periodHour);
			baseCal.add(Calendar.MINUTE, _periodMinute);
		}
		_baseDate = baseCal;
	}

	/*
	 * 指定日時を含む周期(の開始時間)を返す ex.周期が2時間の場合 target base 戻り値 4:59 7:00 3:00 5:00 7:00
	 * 5:00 5:01 7:00 5:00 6:00 7:00 5:00 6:59 7:00 5:00 7:00 7:00 7:00 7:01
	 * 7:00 7:00 9:00 7:00 9:00 9:01 7:00 9:00
	 */
	private Calendar getBaseCycleOnTarget(Calendar target) {
		// 获取参考日期
		Calendar base = (Calendar) _baseDate.clone();
		if (target.after(base)) {
			// target <= baseとなるまで繰り返す
			while (target.after(base)) {
				base.add(Calendar.DAY_OF_MONTH, _periodDay);
				base.add(Calendar.HOUR_OF_DAY, _periodHour);
				base.add(Calendar.MINUTE, _periodMinute);
			}
		}
		if (target.before(base)) {
			while (target.before(base)) {
				base.add(Calendar.DAY_OF_MONTH, -_periodDay);
				base.add(Calendar.HOUR_OF_DAY, -_periodHour);
				base.add(Calendar.MINUTE, -_periodMinute);
			}
		}
		// 尝试计算完成时间、過去の時刻ならボス時間が過ぎている→次の周期を返す。
		Calendar end = (Calendar) base.clone();
		end.add(Calendar.MINUTE, _endTime);
		if (end.before(target)) {
			base.add(Calendar.DAY_OF_MONTH, _periodDay);
			base.add(Calendar.HOUR_OF_DAY, _periodHour);
			base.add(Calendar.MINUTE, _periodMinute);
		}
		return base;
	}

	/**
	 * 检查指定的日期和时间含周期、计算出现的开始时间。
	 * 
	 * @return 周期出现的开始时间
	 */
	public Calendar calcSpawnTime(Calendar now) {
		// 获取参考日期
		Calendar base = getBaseCycleOnTarget(now);
		// 计算出现的时期
		base.add(Calendar.MINUTE, _startTime);
		// 出現時間の決定 start～end迄の間でランダムの秒
		int diff = (_endTime - _startTime) * 60;
		int random = diff > 0 ? Random.nextInt(diff) : 0;
		base.add(Calendar.SECOND, random);
		return base;
	}

	/**
	 * 取得一个指定的日期和时间含周期、计算出现的开始时间。
	 * 
	 * @return 周期出现的开始时间
	 */
	public Calendar getSpawnStartTime(Calendar now) {
		// 获取参考日期
		Calendar startDate = getBaseCycleOnTarget(now);
		// 计算出现的时期
		startDate.add(Calendar.MINUTE, _startTime);
		return startDate;
	}

	/**
	 * 取得一个指定的日期和时间含周期、计算出现的结束时间。
	 * 
	 * @return 周期出现的结束时间
	 */
	public Calendar getSpawnEndTime(Calendar now) {
		// 获取参考日期
		Calendar endDate = getBaseCycleOnTarget(now);
		// 计算出现的时期
		endDate.add(Calendar.MINUTE, _endTime);
		return endDate;
	}

	/**
	 * 对于一个指定的日期和时间含周期、计算下一次出现的时间。
	 * 
	 * @return 未来周期的出现时间
	 */
	public Calendar nextSpawnTime(Calendar now) {
		// 获取参考日期
		Calendar next = (Calendar) now.clone();
		next.add(Calendar.DAY_OF_MONTH, _periodDay);
		next.add(Calendar.HOUR_OF_DAY, _periodHour);
		next.add(Calendar.MINUTE, _periodMinute);
		return calcSpawnTime(next);
	}

	/**
	 * 对于一个指定的日期和时间、要返回最近出现的开始时间。
	 * 
	 * @return 近期出现的开始时间
	 */
	public Calendar getLatestStartTime(Calendar now) {
		// 获取参考日期
		Calendar latestStart = getSpawnStartTime(now);
		if (!now.before(latestStart)) { // now >= latestStart
		}
		else {
			// now < latestStartなら1個前が最近の周期
			latestStart.add(Calendar.DAY_OF_MONTH, -_periodDay);
			latestStart.add(Calendar.HOUR_OF_DAY, -_periodHour);
			latestStart.add(Calendar.MINUTE, -_periodMinute);
		}

		return latestStart;
	}

	private static int getTimeParse(String target, String search) {
		if (target == null) {
			return 0;
		}
		int n = 0;
		Matcher matcher = Pattern.compile("\\d+" + search).matcher(target);
		if (matcher.find()) {
			String match = matcher.group();
			n = Integer.parseInt(match.replace(search, ""));
		}
		return n;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name = "BossCycleList")
	static class L1BossCycleList {
		@XmlElement(name = "BossCycle")
		private List<L1BossCycle> bossCycles;

		public List<L1BossCycle> getBossCycles() {
			return bossCycles;
		}

		public void setBossCycles(List<L1BossCycle> bossCycles) {
			this.bossCycles = bossCycles;
		}
	}

	public static void load() {
		PerformanceTimer timer = new PerformanceTimer();
		System.out.print("╠》正在读取 BossCycle...");
		try {
			// BookOrder クラスをバインディングするコンテキストを生成
			JAXBContext context = JAXBContext.newInstance(L1BossCycle.L1BossCycleList.class);

			// XML -> POJO 変換を行うアンマーシャラを生成
			Unmarshaller um = context.createUnmarshaller();

			// XML -> POJO 変換
			File file = new File("./data/xml/Cycle/BossCycle.xml");
			L1BossCycleList bossList = (L1BossCycleList) um.unmarshal(file);

			for (L1BossCycle cycle : bossList.getBossCycles()) {
				cycle.init();
				_cycleMap.put(cycle.getName(), cycle);
			}

			// user覆盖任何数据
			File userFile = new File("./data/xml/Cycle/users/BossCycle.xml");
			if (userFile.exists()) {
				bossList = (L1BossCycleList) um.unmarshal(userFile);

				for (L1BossCycle cycle : bossList.getBossCycles()) {
					cycle.init();
					_cycleMap.put(cycle.getName(), cycle);
				}
			}
			// spawnlist_boss读取配置
			BossSpawnTable.fillSpawnTable();
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, "BossCycle读取出现错误", e);
			System.exit(0);
		}
		System.out.println("完成!\t\t耗时: " + timer.get() + "\t毫秒");
	}

	/**
	 * 周期名と指定日時に対する出現期間、产生出现时间
	 * 
	 * @param now
	 *            周期产生的时间
	 */
	public void showData(Calendar now) {
		System.out.println("[Type]" + getName());
		System.out.print("  [出现期间]");
		System.out.print(_sdf.format(getSpawnStartTime(now).getTime()) + " - ");
		System.out.println(_sdf.format(getSpawnEndTime(now).getTime()));
	}

	private static Map<String, L1BossCycle> _cycleMap = Maps.newMap();

	public static L1BossCycle getBossCycle(String type) {
		return _cycleMap.get(type);
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public Base getBase() {
		return _base;
	}

	public void setBase(Base base) {
		_base = base;
	}

	public Cycle getCycle() {
		return _cycle;
	}

	public void setCycle(Cycle cycle) {
		_cycle = cycle;
	}

}