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
package l1j.server.server.model.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import l1j.server.server.IdFactory;
import l1j.server.server.datatables.DoorTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.RaceTicketTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.shop.L1Shop;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.templates.L1RaceTicket;
import l1j.server.server.templates.L1ShopItem;

/**
 * 奇岩 食人妖精竞赛
 */
public class L1BugBearRace {
	private class BugBearRunning extends TimerTask {
		L1NpcInstance _bugBear;
		int _runnerNumber;

		BugBearRunning(final int runnerNumber) {
			_bugBear = _runner[runnerNumber];
			_runnerNumber = runnerNumber;
		}

		public void begin(final int startTime) {
			final Timer _timer = new Timer();
			_timer.schedule(this, startTime);
		}

		@Override
		public void run() {
			int sleepTime = 0;
			while (getGameStatus() == STATUS_PLAYING) {
				try {
					Thread.sleep(sleepTime);
				}
				catch (final InterruptedException e) {
					e.printStackTrace();
				}
				while (!_bugBear.getMap().isPassable(_bugBear.getX(), _bugBear.getY(), _bugBear.getHeading())) {
					if (_bugBear.getMap().isPassable(_bugBear.getX(), _bugBear.getY(), _bugBear.getHeading() + 1)) {
						_bugBear.setHeading(rePressHeading(_bugBear.getHeading() + 1));
						break;
					}
					else {
						_bugBear.setHeading(rePressHeading(_bugBear.getHeading() - 1));
						if (_bugBear.getMap().isPassable(_bugBear.getX(), _bugBear.getY(), _bugBear.getHeading())) {
							break;
						}
					}
				}
				_bugBear.setDirectionMove(_bugBear.getHeading());// ヘッジ方向
				if (checkPosition(_runnerNumber)) {
					_bugBear = null;
					return;
				}
				else {
					// new BugBearRunning(_runnerNumber).
					// インスタンスを生成しないでください　メモリリークが発生します
					sleepTime = calcSleepTime(_bugBear.getPassispeed(), _runnerNumber);
				}
			}
		}
	}

	private class RaceTimer extends TimerTask {
		int _startTime;

		RaceTimer(final int startTime) {
			_startTime = startTime;
		}

		public void begin() {
			final Timer timer = new Timer();
			timer.schedule(this, _startTime * 1000);
		}

		@Override
		public void run() {

			try {
				// 游戏的状态为NONE（10分前）
				setGameStatus(STATUS_NONE);
				sendMessage("$376 10 $377");
				for (int loop = 0; loop < WAIT_TIME; loop++) {
					Thread.sleep(1000);
				}
				clearRunner();
				setRound(getRound() + 1);
				/* 比赛时间 - 录制过程 */
				final L1RaceTicket ticket = new L1RaceTicket();
				ticket.set_itemobjid(FIRST_ID); // 重複可能
				ticket.set_allotment_percentage(0);
				ticket.set_round(getRound());
				ticket.set_runner_num(0);
				ticket.set_victory(0);
				RaceTicketTable.getInstance().storeNewTiket(ticket); // 记录用
				RaceTicketTable.getInstance().oldTicketDelete(getRound()); // 删除旧记录
				/**/
				setRandomRunner(); // 运动员准备
				setRandomCondition();
				/* 存储SHOP BuyList */
				final L1Shop shop1 = ShopTable.getInstance().get(70035);
				final L1Shop shop2 = ShopTable.getInstance().get(70041);
				final L1Shop shop3 = ShopTable.getInstance().get(70042);
				for (int i = 0; i < 5; i++) {
					final L1ShopItem shopItem1 = new L1ShopItem(40309, 500, 1);
					shopItem1.setName(i);
					final L1ShopItem shopItem2 = new L1ShopItem(40309, 500, 1);
					shopItem2.setName(i);
					final L1ShopItem shopItem3 = new L1ShopItem(40309, 500, 1);
					shopItem3.setName(i);
					shop1.getSellingItems().add(shopItem1);
					shop2.getSellingItems().add(shopItem2);
					shop3.getSellingItems().add(shopItem3);
				}
				/**/
				setWinnigAverage();
				setGameStatus(STATUS_READY);
				for (int loop = 0; loop < READY_TIME - 1; loop++) {
					if (loop % 60 == 0) {
						sendMessage("$376 " + (1 + (READY_TIME - loop) / 60) + " $377");
					}
					Thread.sleep(1000);
				}
				sendMessage("$363");// 363 レディー！
				Thread.sleep(1000);
				for (int loop = 10; loop > 0; loop--) {
					sendMessage("" + loop);
					Thread.sleep(1000);
				}
				sendMessage("$364");// 364 ゴー！
				setGameStatus(STATUS_PLAYING);
				/* 删除SHOP BuyList */
				shop1.getSellingItems().clear();
				shop2.getSellingItems().clear();
				shop3.getSellingItems().clear();
				/**/
				for (final L1DoorInstance door : DoorTable.getInstance().getDoorList()) {
					if ((door.getDoorId() <= 812) && (door.getDoorId() >= 808)) {
						door.open();
					}
				}
				for (int i = 0; i < _runner.length; i++) {
					new BugBearRunning(i).begin(0);
				}

				new StartBuffTimer().begin();

				for (int i = 0; i < _runner.length; i++) {
					if (getBetCount(i) > 0) {
						_allotment_percentage[i] = (getAllBet() / (getBetCount(i)) / 500D);
					}
					else {
						_allotment_percentage[i] = 0.0;
					}
				}
				for (int i = 0; i < _runner.length; i++) {
					Thread.sleep(1000);
					sendMessage(NpcTable.getInstance().getTemplate(_runner[i].getNpcId()).get_nameid() + " $402 "// 一文字3バイトだが面倒なのでネームIDを復元しない・・・
							+ String.valueOf(_allotment_percentage[i]));// 402
																		// の配当率は
				}
				cancel();
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private class StartBuffTimer extends TimerTask {
		StartBuffTimer() {
		}

		public void begin() {
			final Timer _timer = new Timer();
			_timer.scheduleAtFixedRate(this, 1000, 1000);
		}

		@Override
		public void run() {
			if (getGameStatus() == STATUS_PLAYING) {
				for (int i = 0; i < _runner.length; i++) {
					if (getRandomProbability() <= _winning_average[i] * (1 + (0.2 * getCondition(i)))) {
						_runner[i].setBraveSpeed(1);
					}
					else {
						_runner[i].setBraveSpeed(0);
					}
				}
			}
			else {
				cancel();
			}
		}
	}

	L1MerchantInstance pory;
	L1MerchantInstance cecile;
	L1MerchantInstance parkin;
	private static final int FIRST_ID = 0x0000000;
	private static final int STATUS_NONE = 0;
	private static final int STATUS_READY = 1;
	private static final int STATUS_PLAYING = 2;
	private static final int STATUS_END = 3;
	private static final int WAIT_TIME = 60;
	private static final int READY_TIME = 9 * 60 - 10;// test 60;//
	private static final int FIRST_NPCID = 91350;// ~20

	public static L1BugBearRace getInstance() {
		if (instance == null) {
			instance = new L1BugBearRace();
		}
		return instance;
	}

	private final L1NpcInstance[] _runner;
	private final int[] _runnerStatus = new int[5];
	private final double[] _winning_average = new double[5];

	private final double[] _allotment_percentage = new double[5];

	private final int[] _condition = new int[5];

	private int _allBet;

	private final int[] _betCount = new int[5];

	private int _round;

	private static Random _random = new Random();

	private static L1BugBearRace instance;

	private int _status = 0;

	L1BugBearRace() {
		setRound(RaceTicketTable.getInstance().getRoundNumOfMax());
		_runner = new L1NpcInstance[5];
		for (final L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1MerchantInstance) {
				if (((L1MerchantInstance) obj).getNpcId() == 70041) {
					parkin = (L1MerchantInstance) obj;
				}
			}
		}
		for (final L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1MerchantInstance) {
				if (((L1MerchantInstance) obj).getNpcId() == 70035) {
					cecile = (L1MerchantInstance) obj;
				}
			}
		}
		for (final L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1MerchantInstance) {
				if (((L1MerchantInstance) obj).getNpcId() == 70042) {
					pory = (L1MerchantInstance) obj;
				}
			}
		}
		new RaceTimer(0).begin();
	}

	/** 检查当前的位置 */
	public boolean checkPosition(final int runnerNumber) {
		final int[] defaultHead = { 6, 7, 0, 1, 2, 2 };
		if (getGameStatus() != STATUS_PLAYING) {
			return false;
		}
		boolean flag = false;// ゴールするまではfalseを返す
		final L1NpcInstance npc = _runner[runnerNumber];
		final int x = npc.getX();
		final int y = npc.getY();
		if (_runnerStatus[runnerNumber] == 0) {// スタート　直線
			if (// x==33476+(runnerNumber*2)&&y==32861+(runnerNumber*2)
			((x >= 33476) && (x <= 33476 + 8)) && ((y >= 32861) && (y <= 32861 + 8))) {
				_runnerStatus[runnerNumber] = _runnerStatus[runnerNumber] + 1;
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを変更
			}
			else {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを復元
			}
		}
		else if (_runnerStatus[runnerNumber] == 1) {//
			if (((x <= 33473) && (x >= 33473 - 9)) && (y == 32858)) {
				_runnerStatus[runnerNumber] = _runnerStatus[runnerNumber] + 1;
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを変更
			}
			else {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを復元
			}
		}
		else if (_runnerStatus[runnerNumber] == 2) {//
			if (((x <= 33473) && (x >= 33473 - 9)) && (y == 32852)) {
				_runnerStatus[runnerNumber] = _runnerStatus[runnerNumber] + 1;
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを変更
			}
			else {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを復元
			}
		}
		else if (_runnerStatus[runnerNumber] == 3) {//
			if (((x == 33478) && ((y <= 32847) && (y >= 32847 - 9)))) {
				_runnerStatus[runnerNumber] = _runnerStatus[runnerNumber] + 1;
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを変更
			}
			else {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを復元
			}
		}
		else if (_runnerStatus[runnerNumber] == 4) {//
			if ((x == 33523) && ((y >= 32847 - 9) && (y <= 32847))) {
				_runnerStatus[runnerNumber] = _runnerStatus[runnerNumber] + 1;
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを変更
				// goal
				goal(runnerNumber);
			}
			else {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを復元
			}
		}
		else if (_runnerStatus[runnerNumber] == 5) {//
			if ((x == 33527) && ((y >= 32847 - 8) && (y <= 32847))) {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを変更
				finish();
				flag = true;
			}
			else {
				npc.setHeading(defaultHead[_runnerStatus[runnerNumber]]);// ヘッジを復元
			}
		}
		return flag;
	}

	public int getAllBet() {
		return _allBet;
	}

	public int getBetCount(final int i) {
		return _betCount[i];
	}

	public int getCondition(final int num) {
		return _condition[num];
	}

	// TODO 判定圈数　end

	public int getGameStatus() {
		return _status;
	}

	public int getRound() {
		return _round;
	}

	public L1NpcInstance getRunner(final int num) {
		return _runner[num];
	}

	public double getWinningAverage(final int num) {
		return _winning_average[num];
	}

	public void setAllBet(final int allBet) {// allbetは3桁の整数
		_allBet = (int) (allBet * 0.9);// 1割は管理人が取得
	}

	public void setBetCount(final int i, final int count) {
		_betCount[i] = count;
	}

	public void setGameStatus(final int i) {
		_status = i;
	}

	private int calcSleepTime(int sleepTime, final int runnerNumber) {
		final L1NpcInstance npc = _runner[runnerNumber];
		if (npc.getBraveSpeed() == 1) {
			sleepTime -= (sleepTime * 0.25);
		}

		return sleepTime;
	}

	private boolean checkDuplicate(final int npcid, final int curi) {
		for (int i = 0; i < curi; i++) {
			if (_runner[i] != null) {
				if (_runner[i].getNpcId() == npcid) {
					return true;
				}
			}
		}
		return false;
	}

	/** 检查重复 */
	private boolean checkDuplicateAverage(final double winning_average, final int curi) {
		// 胜率跟状态都一样算重复
		for (int i = 0; i < curi; i++) {
			if ((_winning_average[i] == winning_average) && (_condition[i] == _condition[curi])) {
				return true;
			}
		}
		return false;
	}

	private void clearRunner() {
		for (int i = 0; i < 5; i++) {
			if (_runner[i] != null) {
				_runner[i].deleteMe();
				if (_runner[i].getMap().isInMap(_runner[i].getX(), _runner[i].getY())) {
					_runner[i].getMap().setPassable(_runner[i].getX(), _runner[i].getY(), true);
				}
			}
			_runner[i] = null;
			_runnerStatus[i] = 0;
			_condition[i] = 0;
			_winning_average[i] = 0;
			_allotment_percentage[i] = 0.0;
			setBetCount(i, 0);
		}
		setAllBet(0);
		for (final L1DoorInstance door : DoorTable.getInstance().getDoorList()) {
			if ((door.getDoorId() <= 812) && (door.getDoorId() >= 808)) {
				door.close();
			}
		}
	}

	private void finish() {
		int cnt = 0;
		for (final int _runnerStatu : _runnerStatus) {
			if (_runnerStatu == 5) {
				cnt++;
			}
		}
		if (cnt == 5) {
			setGameStatus(STATUS_END);
			new RaceTimer(30).begin();
			/* 存储SHOP */

			/**/
		}
	}

	private double getRandomProbability() {
		return (_random.nextInt(10000) + 1) / 100D;
	}

	private void goal(final int runnberNumber) {
		int cnt = 0;
		for (final int _runnerStatu : _runnerStatus) {
			if (_runnerStatu == 5) {
				cnt++;
			}
		}
		if (cnt == 1) {
			cecile.wideBroadcastPacket(new S_NpcChatPacket(cecile, "第 " + getRound() + " $366 " + NpcTable.getInstance().getTemplate(_runner[runnberNumber].getNpcId()).get_nameid() + " $367", 2));// 5>3字节
			/* 存储DB */
			RaceTicketTable.getInstance().updateTicket(getRound(), _runner[runnberNumber].getNpcId() - FIRST_NPCID + 1, _allotment_percentage[runnberNumber]);
			/**/
		}
	}

	private int rePressHeading(int heading) {
		if (0 > heading) {// 0未満ならば7
			heading = 7;
		}
		if (7 < heading) {// 7より大きいなら0
			heading = 0;
		}
		return heading;
	}

	private void sendMessage(final String id) {
		parkin.wideBroadcastPacket(new S_NpcChatPacket(parkin, id, 2));
		// cecile.broadcastPacket(new S_NpcChatPacket(cecile,id, 2));
		pory.wideBroadcastPacket(new S_NpcChatPacket(pory, id, 2));
	}

	private void setRandomCondition() {
		for (int i = 0; i < _condition.length; i++) {
			_condition[i] = -1 + _random.nextInt(3);
		}
	}

	/*
	 * private void setCondition(int num, int condition) { this._condition[num] = condition; }
	 */

	private void setRandomRunner() {
		for (int i = 0; i < 5; i++) {
			int npcid = FIRST_NPCID + _random.nextInt(20);
			while (checkDuplicate(npcid, i)) {
				npcid = FIRST_NPCID + _random.nextInt(20);
			}
			final L1Location loc = new L1Location(33522 - (i * 2), 32861 + (i * 2), 4);
			_runner[i] = spawnOne(loc, npcid, 6);

		}
	}

	private void setRound(final int round) {
		_round = round;
	}

	private void setWinnigAverage() {
		for (int i = 0; i < _winning_average.length; i++) {
			double winningAverage = getRandomProbability();

			while (checkDuplicateAverage(winningAverage, i)) {
				winningAverage = getRandomProbability();
			}
			_winning_average[i] = winningAverage;
		}
	}

	/**
	 * 指定されたロケーションに任意のNpcを一匹生成する。
	 * 
	 * @param loc
	 *            出現位置
	 * @param npcid
	 *            任意のNpcId
	 * @param heading
	 *            向き
	 * @return L1NpcInstance 戻り値 : 成功=生成したインスタンス 失敗=null
	 */
	@SuppressWarnings("unused")
	private L1NpcInstance spawnOne(final L1Location loc, final int npcid, final int heading) {
		final L1NpcInstance mob = new L1NpcInstance(NpcTable.getInstance().getTemplate(npcid));
		if (mob == null) {
			return mob;
		}

		mob.setNameId("#" + (mob.getNpcId() - FIRST_NPCID + 1) + " " + mob.getNameId());
		mob.setId(IdFactory.getInstance().nextId());
		mob.setHeading(heading);
		mob.setX(loc.getX());
		mob.setHomeX(loc.getX());
		mob.setY(loc.getY());
		mob.setHomeY(loc.getY());
		mob.setMap((short) loc.getMapId());
		mob.setPassispeed(mob.getPassispeed() * 2);
		L1World.getInstance().storeObject(mob);
		L1World.getInstance().addVisibleObject(mob);

		final S_NPCPack s_npcPack = new S_NPCPack(mob);
		for (final L1PcInstance pc : L1World.getInstance().getRecognizePlayer(mob)) {
			pc.addKnownObject(mob);
			mob.addKnownObject(pc);
			pc.sendPackets(s_npcPack);
		}
		// 怪物ＡＩ开始
		mob.onNpcAI();
		mob.turnOnOffLight();
		mob.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 开始喊话
		return mob;
	}
}
