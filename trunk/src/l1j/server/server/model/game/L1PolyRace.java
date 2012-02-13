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

import java.util.Timer;
import java.util.TimerTask;

import javolution.util.FastTable;
import l1j.server.server.datatables.DoorTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_Race;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.Random;

/**
 * 宠物竞速
 */
public class L1PolyRace {

	/***
	 * [变身清单] 资料提供 CTKI 有错请去干跷他 :)
	 */
	private int[] polyList = { 29, 96, 929, 931, 934, 936, 938, 945, 1540, 1642, 1649, 2001, 2145, 2541, 3107, 3132, 3134, 3143, 3154, 3156, 3178, 3182, 3184, 3188, 3199, 3211, 3783, 3918, 4038, 4133, 4171, 5089, };

	private static L1PolyRace instance;

	public static L1PolyRace getInstance() {
		if (instance == null) {
			instance = new L1PolyRace();
		}
		return instance;
	}

	/** 空闲状态 */
	public static final int STATUS_NONE = 0;
	/** 准备就绪 */
	public static final int STATUS_READY = 1;
	/** 游戏中 */
	public static final int STATUS_PLAYING = 2;
	/** 游戏结束 */
	public static final int STATUS_END = 3;
	/** 游戏圈数 */
	private static final int maxLap = 4; // 游戏圈数 最小:1 最大:你高兴
	/** 最大玩家数 */
	private static final int maxPlayer = 10; // 最大玩家数 1~20
	/** 最小玩家数 */
	private static final int minPlayer = 2; // 最小玩家数
	/** 进场之后等待时间 */
	private static int readyTime = 60 * 1000; // 进场之后等待时间 60秒
	/** 游戏时间 */
	private static int limitTime = 240 * 1000; // 游戏时间 240秒
	/** 玩家清单 */
	private FastTable<L1PcInstance> playerList = new FastTable<L1PcInstance>();

	/** 增加玩家清单 */
	public void addPlayerList(L1PcInstance pc) {
		if (!playerList.contains(pc)) {
			playerList.add(pc);
		}
	}

	/** 删除玩家清单 */
	public void removePlayerList(L1PcInstance pc) {
		if (playerList.contains(pc)) {
			playerList.remove(pc);
		}
	}

	/** 进入游戏 */
	public void enterGame(L1PcInstance pc) {
		if (pc.getLevel() < 30) {
			pc.sendPackets(new S_ServerMessage(1273, "30", "99")); // 所选择的竞赛只有Lv%0 ~ Lv%1 能参加。
			return;
		}
		if (!pc.getInventory().consumeItem(40308, 1000)) {
			pc.sendPackets(new S_ServerMessage(189)); // \f1金币不足。
			return;
		}
		if (playerList.size() + orderList.size() >= maxPlayer) {
			pc.sendPackets(new S_SystemMessage("游戏人数已达上限"));
			return;
		}
		if (getGameStatus() == STATUS_PLAYING || getGameStatus() == STATUS_END) {
			pc.sendPackets(new S_ServerMessage(1182)); // 游戏已经开始了。
			return;
		}
		if (getGameStatus() == STATUS_NONE) {
			addOrderList(pc);
			return;
		}

		addPlayerList(pc);
		L1Teleport.teleport(pc, 32768, 32849, (short) 5143, 6, true);
	}

	/** 预约清单 */
	private FastTable<L1PcInstance> orderList = new FastTable<L1PcInstance>();

	/** 删除预约清单 */
	public void removeOrderList(L1PcInstance pc) {
		orderList.remove(pc);
	}

	/** 预约进场...试做1 */
	public void addOrderList(L1PcInstance pc) {
		if (orderList.contains(pc)) {
			pc.sendPackets(new S_ServerMessage(1254)); // 已预约到场次了。
			return;
		}
		orderList.add(pc);
		pc.setInOrderList(true);
		pc.sendPackets(new S_ServerMessage(1253, String.valueOf(orderList.size()))); // 已预约到第%0顺位进入比赛场地。

		if (orderList.size() >= minPlayer) {
			for (L1PcInstance player : orderList) {
				player.sendPackets(new S_Message_YN(1256, null)); // 要进入到竞赛场地吗？(Y/N)
			}
			setGameStatus(STATUS_READY);
			startReadyTimer();
		}
	}

	/** 检查角色OK */
	private boolean checkPlayersOK() {
		if (getGameStatus() == STATUS_READY) {
			return playerList.size() >= minPlayer;
		}
		return false;
	}

	/** 设定游戏开始 */
	private void setGameStart() {
		setGameStatus(STATUS_PLAYING);
		for (L1PcInstance pc : playerList) {
			speedUp(pc, 0, 0);
			randomPoly(pc, 0, 0);
			pc.sendPackets(new S_ServerMessage(1257)); // 稍后比赛即将开始，请做好准备。
			pc.sendPackets(new S_Race(S_Race.GameStart)); // 5.4.3.2.1.GO!
			pc.sendPackets(new S_Race(maxLap, pc.getLap())); // 圈数
			pc.sendPackets(new S_Race(playerList, pc)); // 玩家名单
		}
		startCompareTimer();
		startClockTimer();
	}

	/** 设定比赛胜利者 */
	private void setGameWinner(L1PcInstance pc) {
		if (getWinner() == null) {
			setWinner(pc);
			setGameEnd(END_STATUS_WINNER);
		}
	}

	/** 有胜利者 */
	private static final int END_STATUS_WINNER = 1;
	/** 时间到没人赢 */
	private static final int END_STATUS_NOWINNER = 2;
	/** 人数不足 */
	private static final int END_STATUS_NOPLAYER = 3;

	/**
	 * 三种情况 1:有胜利者 2:时间到没人赢 3:人数不足
	 * 
	 * @param type
	 *            情况
	 */
	private void setGameEnd(int type) {
		setGameStatus(STATUS_END);
		switch (type) {
		case END_STATUS_WINNER:
			stopCompareTimer();
			stopGameTimeLimitTimer();
			sendEndMessage();
			break;
		case END_STATUS_NOWINNER:
			stopCompareTimer();
			sendEndMessage();
			break;
		case END_STATUS_NOPLAYER:
			for (L1PcInstance pc : playerList) {
				// 未达到比赛最低人数(2人)，因此强制关闭比赛并退还1000个金币。
				pc.sendPackets(new S_ServerMessage(1264));
				pc.getInventory().storeItem(40308, 1000);
			}
			break;
		}
		startEndTimer(); // 5秒后传回村
	}

	/** 奖励获胜者 */
	private void giftWinner() {
		L1PcInstance winner = getWinner();
		L1ItemInstance item = ItemTable.getInstance().createItem(41308); // 勇者的南瓜袋子
		if (winner == null || item == null) {
			return;
		}
		if (winner.getInventory().checkAddItem(item, 1) == L1Inventory.OK) {
			item.setCount(1);
			winner.getInventory().storeItem(item);
			winner.sendPackets(new S_ServerMessage(403, item.getLogName())); // 获得%0%o 。
		}
	}

	/** 设定结束提示信息 */
	private void sendEndMessage() {
		L1PcInstance winner = getWinner();
		for (L1PcInstance pc : playerList) {
			if (winner != null) {
				pc.sendPackets(new S_ServerMessage(1259)); // 稍后将往村庄移动。
				pc.sendPackets(new S_Race(winner.getName(), _time * 2));
				continue;
			}
			pc.sendPackets(new S_Race(S_Race.GameOver));
		}
	}

	/** 初始化 + 下一场准备 */
	private void setGameInit() {
		for (L1PcInstance pc : playerList) {
			pc.sendPackets(new S_Race(S_Race.GameEnd));
			pc.setLap(1);
			pc.setLapCheck(0);
			L1Teleport.teleport(pc, 32616, 32782, (short) 4, 5, true);
			removeSkillEffect(pc);
		}
		setDoorClose(true);
		setGameStatus(STATUS_NONE);
		setWinner(null);
		playerList.clear();
		clearTime();
	}

	// XXX for ClientThread.java
	/** 检查离开游戏 */
	public void checkLeaveGame(L1PcInstance pc) {
		if (pc.getMapId() == 5143) {
			removePlayerList(pc);
			L1PolyMorph.undoPoly(pc);
		}
		if (pc.isInOrderList()) {
			removeOrderList(pc);
		}
	}

	// XXX for C_Attr.java
	/**  */
	public void requsetAttr(L1PcInstance pc, int c) {
		if (c == 0) { // NO
			removeOrderList(pc);
			pc.setInOrderList(false);
			pc.sendPackets(new S_ServerMessage(1255)); // 入场的预约已经被取消了。
		}
		else { // YES
			addPlayerList(pc);
			L1Teleport.teleport(pc, 32768, 32849, (short) 5143, 6, true);
			removeSkillEffect(pc);
			removeOrderList(pc);
			pc.setInOrderList(false);
		}
	}

	/** 排名 */
	private FastTable<L1PcInstance> position = new FastTable<L1PcInstance>();

	/** 判断排名 */
	private void comparePosition() {
		FastTable<L1PcInstance> temp = new FastTable<L1PcInstance>();
		int size = playerList.size();
		int count = 0;
		while (size > count) {
			int maxLapScore = 0;
			for (L1PcInstance pc : playerList) {
				if (temp.contains(pc)) {
					continue;
				}
				if (pc.getLapScore() >= maxLapScore) {
					maxLapScore = pc.getLapScore();
				}
			}
			for (L1PcInstance player : playerList) {
				if (player.getLapScore() == maxLapScore) {
					temp.add(player);
				}
			}
			count++;
		}
		if (!position.equals(temp)) {
			position.clear();
			position.addAll(temp);
			for (L1PcInstance pc : playerList) {
				pc.sendPackets(new S_Race(position, pc));// info(信息)
			}
		}
	}

	/** 设定关门 */
	private void setDoorClose(boolean isClose) {
		L1DoorInstance[] list = DoorTable.getInstance().getDoorList();
		for (L1DoorInstance door : list) {
			if (door.getMapId() == 5143) {
				if (isClose) {
					door.close();
				}
				else {
					door.open();
				}
			}
		}
	}

	/** 删除技能效果 */
	public void removeSkillEffect(L1PcInstance pc) {
		L1SkillUse skill = new L1SkillUse();
		skill.handleCommands(pc, L1SkillId.CANCELLATION, pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_LOGIN);
	}

	/** 很蠢的陷阱设定 ... */
	private void onEffectTrap(L1PcInstance pc) {
		int x = pc.getX();
		int y = pc.getY();
		if (x == 32748 && (y == 32845 || y == 32846)) {
			speedUp(pc, 32748, 32845);
		}
		else if (x == 32748 && (y == 32847 || y == 32848)) {
			speedUp(pc, 32748, 32847);
		}
		else if (x == 32748 && (y == 32849 || y == 32850)) {
			speedUp(pc, 32748, 32849);
		}
		else if (x == 32748 && y == 32851) {
			speedUp(pc, 32748, 32851);
		}
		else if (x == 32762 && (y == 32811 || y == 32812)) {
			speedUp(pc, 32762, 32811);
		}
		else if ((x == 32799 || x == 32800) && y == 32830) {
			speedUp(pc, 32800, 32830);
		}
		else if ((x == 32736 || x == 32737) && y == 32840) {
			randomPoly(pc, 32737, 32840);
		}
		else if ((x == 32738 || x == 32739) && y == 32840) {
			randomPoly(pc, 32739, 32840);
		}
		else if ((x == 32740 || x == 32741) && y == 32840) {
			randomPoly(pc, 32741, 32840);
		}
		else if (x == 32749 && (y == 32818 || y == 32817)) {
			randomPoly(pc, 32749, 32817);
		}
		else if (x == 32749 && (y == 32816 || y == 32815)) {
			randomPoly(pc, 32749, 32815);
		}
		else if (x == 32749 && (y == 32814 || y == 32813)) {
			randomPoly(pc, 32749, 32813);
		}
		else if (x == 32749 && (y == 32812 || y == 32811)) {
			randomPoly(pc, 32749, 32811);
		}
		else if (x == 32790 && (y == 32812 || y == 32813)) {
			randomPoly(pc, 32790, 32812);
		}
		else if ((x == 32793 || x == 32794) && y == 32831) {
			randomPoly(pc, 32794, 32831);
		}
	}

	/** 变身效果 */
	private static int POLY_EFFECT = 15566;
	/** 加速效果 */
	private static int SPEED_EFFECT = 18333;

	/** 变身效果 */
	private void randomPoly(L1PcInstance pc, int x, int y) {
		if (pc.hasSkillEffect(POLY_EFFECT)) {
			return;
		}
		pc.setSkillEffect(POLY_EFFECT, 4 * 1000);

		int i = Random.nextInt(polyList.length);
		L1PolyMorph.doPoly(pc, polyList[i], 3600, L1PolyMorph.MORPH_BY_NPC);

		for (L1PcInstance player : playerList) {
			player.sendPackets(new S_EffectLocation(x, y, 6675));
		}
	}

	/** 加速效果 */
	private void speedUp(L1PcInstance pc, int x, int y) {
		if (pc.hasSkillEffect(SPEED_EFFECT)) {
			return;
		}
		pc.setSkillEffect(SPEED_EFFECT, 4 * 1000);
		int time = 15;
		int objectId = pc.getId();
		// 竞速专用 -超级加速
		pc.sendPackets(new S_SkillBrave(objectId, 5, time));
		pc.broadcastPacket(new S_SkillBrave(objectId, 5, time));
		pc.setSkillEffect(L1SkillId.STATUS_BRAVE2, time * 1000);
		pc.setBraveSpeed(5);
		/**
		 * XXX 注意!加速效果必须给同画面的人知道 否则会造成错位!!! pc.broadcastPacket(new S_SkillBrave(objectId, 5, time))!!!
		 */
		pc.sendPackets(new S_SkillHaste(objectId, 1, time * 10));
		pc.setSkillEffect(L1SkillId.STATUS_HASTE, time * 10 * 1000);
		pc.setMoveSpeed(1);

		for (L1PcInstance player : playerList) {
			player.sendPackets(new S_EffectLocation(x, y, 6674));
		}
	}

	/** 很蠢的判断圈数... */
	public void checkLapFinish(L1PcInstance pc) {
		if (pc.getMapId() != 5143 || getGameStatus() != STATUS_PLAYING) {
			return;
		}

		onEffectTrap(pc);
		int x = pc.getX();
		int y = pc.getY();
		int check = pc.getLapCheck();

		if (x == 32762 && y >= 32845 && check == 0) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32754 && y >= 32845 && check == 1) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32748 && y >= 32845 && check == 2) {
			pc.setLapCheck(check + 1);
		}
		else if (x <= 32743 && y == 32844 && check == 3) {
			pc.setLapCheck(check + 1);
		}
		else if (x <= 32742 && y == 32840 && check == 4) {
			pc.setLapCheck(check + 1);
		}
		else if (x <= 32742 && y == 32835 && check == 5) {
			pc.setLapCheck(check + 1);
		}
		else if (x <= 32742 && y == 32830 && check == 6) {
			pc.setLapCheck(check + 1);
		}
		else if (x <= 32742 && y == 32826 && check == 7) {
			pc.setLapCheck(check + 1);
		}
		else if (x <= 32742 && y == 32822 && check == 8) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32749 && y <= 32818 && check == 9) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32755 && y <= 32818 && check == 10) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32760 && y <= 32818 && check == 11) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32765 && y <= 32818 && check == 12) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32770 && y <= 32818 && check == 13) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32775 && y <= 32818 && check == 14) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32780 && y <= 32818 && check == 15) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32785 && y <= 32818 && check == 16) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32789 && y <= 32818 && check == 17) {
			pc.setLapCheck(check + 1);
		}
		else if (x >= 32792 && y == 32821 && check == 18) {
			pc.setLapCheck(check + 1);
		}
		else if (x >= 32793 && y == 32826 && check == 19) {
			pc.setLapCheck(check + 1);
		}
		else if (x >= 32793 && y == 32831 && check == 20) {
			pc.setLapCheck(check + 1);
		}
		else if (x >= 32793 && y == 32836 && check == 21) {
			pc.setLapCheck(check + 1);
		}
		else if (x >= 32793 && y == 32842 && check == 22) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32790 && y >= 32845 && check == 23) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32785 && y >= 32845 && check == 24) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32780 && y >= 32845 && check == 25) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32775 && y >= 32845 && check == 26) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32770 && y >= 32845 && check == 27) {
			pc.setLapCheck(check + 1);
		}
		else if (x == 32764 && y >= 32845 && check == 28) {
			if (pc.getLap() == maxLap) {
				setGameWinner(pc);
				return;
			}
			pc.setLapCheck(0);
			pc.setLap(pc.getLap() + 1);
			pc.sendPackets(new S_Race(maxLap, pc.getLap())); // lap

		}
	}

	/** 状态 */
	private int _status = 0;

	/** 设定游戏状态 */
	public void setGameStatus(int i) {
		_status = i;
	}

	/** 取得游戏状态 */
	public int getGameStatus() {
		return _status;
	}

	/** 时间 */
	private int _time = 0;

	/** 明确时间 */
	private void clearTime() {
		_time = 0;
	}

	/** 增加时间 */
	private void addTime() {
		_time++;
	}

	/** 胜利者 */
	private L1PcInstance _winner = null;

	/** 设定胜利者 */
	public void setWinner(L1PcInstance pc) {
		_winner = pc;
	}

	/** 取得胜利者 */
	public L1PcInstance getWinner() {
		return _winner;
	}

	// /////////////////////////////////////////////////////////////

	/** 开始准备定时器 */
	private void startReadyTimer() {
		new ReadyTimer().begin();
	}

	/** 开始检查定时器 */
	private void startCheckTimer() {
		new CheckTimer().begin();
	}

	/** 启动时钟定时器 */
	private void startClockTimer() {
		new ClockTimer().begin();
	}

	/** 时间限制定时器 */
	private GameTimeLimitTimer limitTimer;

	/** 启动游戏时间限制定时器 */
	private void startGameTimeLimitTimer() {
		Timer timer = new Timer();
		limitTimer = new GameTimeLimitTimer();
		timer.schedule(limitTimer, limitTime);
	}

	/** 停止游戏时间限制定时器 */
	private void stopGameTimeLimitTimer() {
		limitTimer.stopTimer();
	}

	/** 启动结束定时器 */
	private void startEndTimer() {
		new EndTimer().begin();
	}

	/** 比较计时器 */
	private CompareTimer compareTimer;

	/** 开始比较计时器 */
	private void startCompareTimer() {
		Timer timer = new Timer();
		compareTimer = new CompareTimer();
		timer.schedule(compareTimer, 2000, 2000);
	}

	/** 停止比较计时器 */
	private void stopCompareTimer() {
		compareTimer.stopTimer();
	}

	// ////////////////////////////////////////////////////////

	/** 进场等待--->确认人数 */
	private class ReadyTimer extends TimerTask {
		@Override
		public void run() {
			for (L1PcInstance pc : playerList) {
				pc.sendPackets(new S_ServerMessage(1258));
			}
			startCheckTimer();
			this.cancel();
		}

		public void begin() {
			Timer timer = new Timer();
			timer.schedule(this, readyTime);
		}
	}

	/** 确认人数OK --->开始 */
	private class CheckTimer extends TimerTask {
		@Override
		public void run() {
			if (checkPlayersOK()) {
				setGameStart();
			}
			else {
				setGameEnd(END_STATUS_NOPLAYER);
			}
			this.cancel();
		}

		public void begin() {
			Timer timer = new Timer();
			timer.schedule(this, 30 * 1000); // 60s
		}
	}

	/** 倒数5秒--->开始计时 */
	private class ClockTimer extends TimerTask {
		@Override
		public void run() {
			// 计时封包
			for (L1PcInstance pc : playerList) {
				pc.sendPackets(new S_Race(S_Race.CountDown));
			}
			setDoorClose(false);
			startGameTimeLimitTimer();
			this.cancel();
		}

		public void begin() {
			Timer timer = new Timer();
			timer.schedule(this, 5000); // 5s
		}
	}

	/** 开始计时--->游戏结束 */
	private class GameTimeLimitTimer extends TimerTask {
		@Override
		public void run() {
			setGameEnd(END_STATUS_NOWINNER);
			this.cancel();
		}

		public void stopTimer() {
			this.cancel();
		}
	}

	/** 结束计时器 */
	private class EndTimer extends TimerTask {
		@Override
		public void run() {
			giftWinner();
			setGameInit();
			this.cancel();
		}

		public void begin() {
			Timer timer = new Timer();
			timer.schedule(this, 5000); // 10s
		}
	}

	/** 对比计时器 */
	private class CompareTimer extends TimerTask {
		@Override
		public void run() {
			comparePosition();
			addTime();
		}

		public void stopTimer() {
			this.cancel();
		}
	}
}
