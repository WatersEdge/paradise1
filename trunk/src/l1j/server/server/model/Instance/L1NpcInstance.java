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
package l1j.server.server.model.Instance;

import static l1j.server.server.model.identity.L1ItemId.B_POTION_OF_GREATER_HASTE_SELF;
import static l1j.server.server.model.identity.L1ItemId.B_POTION_OF_HASTE_SELF;
import static l1j.server.server.model.identity.L1ItemId.POTION_OF_EXTRA_HEALING;
import static l1j.server.server.model.identity.L1ItemId.POTION_OF_GREATER_HASTE_SELF;
import static l1j.server.server.model.identity.L1ItemId.POTION_OF_GREATER_HEALING;
import static l1j.server.server.model.identity.L1ItemId.POTION_OF_HASTE_SELF;
import static l1j.server.server.model.identity.L1ItemId.POTION_OF_HEALING;
import static l1j.server.server.model.skill.L1SkillId.CANCELLATION;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHACKLE;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.NpcChatTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1HateList;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Magic;
import l1j.server.server.model.L1MobGroupInfo;
import l1j.server.server.model.L1MobSkillUse;
import l1j.server.server.model.L1NpcChatTimer;
import l1j.server.server.model.L1NpcRegenerationTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Spawn;
import l1j.server.server.model.L1World;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.npc.action.L1NpcDefaultAction;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_MoveCharPacket;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NpcChangeShape;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1NpcChat;
import l1j.server.server.types.Point;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.TimerPool;
import l1j.server.server.utils.collections.Lists;
import l1j.server.server.utils.collections.Maps;

/**
 * Npc控制项
 */
public class L1NpcInstance extends L1Character {

	/** 删除定时器 */
	protected static class DeleteTimer extends TimerTask {
		private final int _id;

		protected DeleteTimer(final int oId) {
			_id = oId;
			if (!(L1World.getInstance().findObject(_id) instanceof L1NpcInstance)) {
				throw new IllegalArgumentException("allowed only L1NpcInstance");
			}
		}

		@Override
		public void run() {
			final L1NpcInstance npc = (L1NpcInstance) L1World.getInstance().findObject(_id);
			if ((npc == null) || !npc.isDead() || npc._destroyed) {
				return; // 复活してるか、既に破弃济みだったら拔け
			}
			try {
				npc.deleteMe();
			}
			catch (final Exception e) { // 绝对例外を投げないように
				e.printStackTrace();
			}
		}
	}

	class DigestItemTimer implements Runnable {
		@Override
		public void run() {
			_digestItemRunning = true;
			while (!_destroyed && (_digestItems.size() > 0)) {
				try {
					Thread.sleep(1000);
				}
				catch (final Exception exception) {
					break;
				}

				final Object[] keys = _digestItems.keySet().toArray();
				for (final Object key2 : keys) {
					final Integer key = (Integer) key2;
					Integer digestCounter = _digestItems.get(key);
					digestCounter -= 1;
					if (digestCounter <= 0) {
						_digestItems.remove(key);
						final L1ItemInstance digestItem = getInventory().getItem(key);
						if (digestItem != null) {
							getInventory().removeItem(digestItem, digestItem.getCount());
						}
					}
					else {
						_digestItems.put(key, digestCounter);
					}
				}
			}
			_digestItemRunning = false;
		}
	}

	class HprTimer extends TimerTask {
		private final int _point;

		public HprTimer(int point) {
			if (point < 1) {
				point = 1;
			}
			_point = point;
		}

		@Override
		public void run() {
			try {
				if ((!_destroyed && !isDead()) && ((getCurrentHp() > 0) && (getCurrentHp() < getMaxHp()))) {
					setCurrentHp(getCurrentHp() + _point);
				}
				else {
					cancel();
					_hprRunning = false;
				}
			}
			catch (final Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}

	class MprTimer extends TimerTask {
		private final int _point;

		public MprTimer(int point) {
			if (point < 1) {
				point = 1;
			}
			_point = point;
		}

		@Override
		public void run() {
			try {
				if ((!_destroyed && !isDead()) && ((getCurrentHp() > 0) && (getCurrentMp() < getMaxMp()))) {
					setCurrentMp(getCurrentMp() + _point);
				}
				else {
					cancel();
					_mprRunning = false;
				}
			}
			catch (final Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}

	interface NpcAI {
		public void start();
	}

	class NpcAIThreadImpl implements Runnable, NpcAI {
		@Override
		public void run() {
			try {
				setAiRunning(true);
				while (!_destroyed && !isDead() && (getCurrentHp() > 0) && (getHiddenStatus() == HIDDEN_STATUS_NONE)) {
					/*
					 * if (_paralysisTime > 0) { try { Thread.sleep(_paralysisTime); } catch (Exception exception) { break; } finally { setParalyzed(false); _paralysisTime = 0; } }
					 */
					while (isParalyzed() || isSleeped()) {
						try {
							Thread.sleep(200);
						}
						catch (final InterruptedException e) {
							setParalyzed(false);
						}
					}

					if (AIProcess()) {
						break;
					}
					try {
						// 指定时间停止线程
						Thread.sleep(getSleepTime());
					}
					catch (final Exception e) {
						break;
					}
				}
				mobSkill.resetAllSkillUseCount();
				do {
					try {
						Thread.sleep(getSleepTime());
					}
					catch (final Exception e) {
						break;
					}
				} while (isDeathProcessing());
				allTargetClear();
				setAiRunning(false);
			}
			catch (final Exception e) {
				_log.log(Level.WARNING, "NpcAI发生例外的错误。", e);
			}
		}

		@Override
		public void start() {
			GeneralThreadPool.getInstance().execute(NpcAIThreadImpl.this);
		}
	}

	class NpcAITimerImpl extends TimerTask implements NpcAI {
		/**
		 * 处理死亡等待定时器
		 */
		private class DeathSyncTimer extends TimerTask {
			@Override
			public void run() {
				if (isDeathProcessing()) {
					schedule(getSleepTime());
					return;
				}
				allTargetClear();
				setAiRunning(false);
			}

			private void schedule(final int delay) {
				_timerPool.getTimer().schedule(new DeathSyncTimer(), delay);
			}
		}

		@Override
		public void run() {
			try {
				if (notContinued()) {
					stop();
					return;
				}

				// XXX 同期がとても怪しげな麻痹判定
				if (0 < _paralysisTime) {
					schedule(_paralysisTime);
					_paralysisTime = 0;
					setParalyzed(false);
					return;
				}
				else if (isParalyzed() || isSleeped()) {
					schedule(200);
					return;
				}

				if (!AIProcess()) { // AIを续けるべきであれば、次の实行をスケジュールし、终了
					schedule(getSleepTime());
					return;
				}
				stop();
			}
			catch (final Exception e) {
				_log.log(Level.WARNING, "NpcAI发生例外的错误。", e);
			}
		}

		@Override
		public void start() {
			setAiRunning(true);
			_timerPool.getTimer().schedule(NpcAITimerImpl.this, 0);
		}

		private boolean notContinued() {
			return _destroyed || isDead() || (getCurrentHp() <= 0) || (getHiddenStatus() != HIDDEN_STATUS_NONE);
		}

		// 同じインスタンスをTimerへ登录できない为、苦肉の策。
		private void schedule(final int delay) {
			_timerPool.getTimer().schedule(new NpcAITimerImpl(), delay);
		}

		private void stop() {
			mobSkill.resetAllSkillUseCount();
			_timerPool.getTimer().schedule(new DeathSyncTimer(), 0); // 死亡同期を开始
		}
	}

	private static final long serialVersionUID = 1L;
	private static Logger _log = Logger.getLogger(L1NpcInstance.class.getName());
	/** 移动速度 */
	public static final int MOVE_SPEED = 0;
	/** 攻击速度 */
	public static final int ATTACK_SPEED = 1;
	/** 施法速度 */
	public static final int MAGIC_SPEED = 2;
	/** 隐藏状态 NONE */
	public static final int HIDDEN_STATUS_NONE = 0;
	/** 隐藏状态 SINK */
	public static final int HIDDEN_STATUS_SINK = 1;
	/** 隐藏状态 FLY */
	public static final int HIDDEN_STATUS_FLY = 2;
	/** 隐藏状态 ICE */
	public static final int HIDDEN_STATUS_ICE = 3;
	/** 隐藏状态 (吉尔塔斯反击屏障) */
	public static final int HIDDEN_STATUS_COUNTERATTACK_BARRIER = 4;
	/** 怪物喊话设定？ */
	public static final int CHAT_TIMING_APPEARANCE = 0;
	/**  */
	public static final int CHAT_TIMING_DEAD = 1;
	/**  */
	public static final int CHAT_TIMING_HIDE = 2;
	/**  */
	public static final int CHAT_TIMING_GAME_TIME = 3;
	/**  */
	private L1Npc _npcTemplate;
	/**  */
	private L1Spawn _spawn;
	/**  */
	private int _spawnNumber; // L1Spawnで管理されているナンバー

	/** 宠物的费用 */
	private int _petcost; // ペットになったときのコスト
	/**  */
	public L1Inventory _inventory = new L1Inventory();

	// ■■■■■■■■■■■■■ ＡＩ关连 ■■■■■■■■■■■

	/**  */
	private L1MobSkillUse mobSkill;

	/** 第一次发现对象。（用于传送） */
	private boolean firstFound = true;

	/** 经路探索范围（半径） ※上げすぎ注意！！ */
	public static int courceRange = 15;

	/** 吸取 MP */
	private int _drainedMana = 0;

	/** 休息 */
	private boolean _rest = false;

	// 随机距离和方向移动时
	/** 随机移动距离 */
	private int _randomMoveDistance = 0;

	/** 随机移动方向 */
	private int _randomMoveDirection = 0;

	/**
	 * 多（核心）处理器，支持为计时器池。定时器用于场合的AI类型实装。
	 */
	private static final TimerPool _timerPool = new TimerPool(4);

	/** 替代 */
	public static void shuffle(final L1Object[] arr) {
		for (int i = arr.length - 1; i > 0; i--) {
			final int t = Random.nextInt(i);

			// 被选为替代值
			final L1Object tmp = arr[i];
			arr[i] = arr[t];
			arr[t] = tmp;
		}
	}

	private boolean _aiRunning = false; // ＡＩ运行中

	// ※ＡＩをスタートさせる时にすでに实行されてないか确认する时に使用
	private boolean _actived = false; // ＮＰＣがアクティブか

	// ※この值がfalseで_targetがいる场合、アクティブになって初行动とみなしヘイストポーション等を使わせる判定で使用
	private boolean _firstAttack = false; // ファーストアッタクされたか

	private int _sleep_time; // ＡＩを停止する时间(ms) ※行动を起こした场合に所要する时间をセット

	protected L1HateList _hateList = new L1HateList();

	protected L1HateList _dropHateList = new L1HateList();

	// ※攻击するターゲットの判定とＰＴ时のドロップ判定で使用
	protected List<L1ItemInstance> _targetItemList = Lists.newList(); // ダーゲットアイテム一览

	protected L1Character _target = null; // 现在のターゲット

	protected L1ItemInstance _targetItem = null; // 现在のターゲットアイテム

	protected L1Character _master = null; // 主人orグループリーダー

	private boolean _deathProcessing = false; // 死亡处理中か

	private int _paralysisTime = 0; // Paralysis RestTime

	/** ＨＰ自然回复 */
	private boolean _hprRunning = false;

	private HprTimer _hprTimer;

	/** ＭＰ自然回复 */
	private boolean _mprRunning = false;

	private MprTimer _mprTimer;

	/** 消化项目 */
	private Map<Integer, Integer> _digestItems;

	/** 运行消化项目 */
	public boolean _digestItemRunning = false;

	/**  */
	private int _passispeed;

	/** 攻击速度 */
	private int _atkspeed;

	/** 捡取道具 */
	private boolean _pickupItem;

	// 该项目的使用判定及使用
	/** 恢复药水系统 */
	public static final int USEITEM_HEAL = 0;

	/** 加速药水系统 */
	public static final int USEITEM_HASTE = 1;

	/** 治愈药水 */
	public static int[] healPotions = { POTION_OF_GREATER_HEALING, POTION_OF_EXTRA_HEALING, POTION_OF_HEALING };

	/** 加速药水 */
	public static int[] haestPotions = { B_POTION_OF_GREATER_HASTE_SELF, POTION_OF_GREATER_HASTE_SELF, B_POTION_OF_HASTE_SELF, POTION_OF_HASTE_SELF };

	// 检查前进方向的障碍物、ある场合は前方斜め左右も确认后进める方向を返す
	// ※从来あった处理に、バックできない仕样を省いて、目标の反对（左右含む）には进まないようにしたもの
	public static int checkObject(final int x, final int y, final short m, final int d) { // 起点Ｘ 起点Ｙ
		// マップＩＤ
		// 进行方向
		final L1Map map = L1WorldMap.getInstance().getMap(m);
		if (d == 1) {
			if (map.isPassable(x, y, 1)) {
				return 1;
			}
			else if (map.isPassable(x, y, 0)) {
				return 0;
			}
			else if (map.isPassable(x, y, 2)) {
				return 2;
			}
		}
		else if (d == 2) {
			if (map.isPassable(x, y, 2)) {
				return 2;
			}
			else if (map.isPassable(x, y, 1)) {
				return 1;
			}
			else if (map.isPassable(x, y, 3)) {
				return 3;
			}
		}
		else if (d == 3) {
			if (map.isPassable(x, y, 3)) {
				return 3;
			}
			else if (map.isPassable(x, y, 2)) {
				return 2;
			}
			else if (map.isPassable(x, y, 4)) {
				return 4;
			}
		}
		else if (d == 4) {
			if (map.isPassable(x, y, 4)) {
				return 4;
			}
			else if (map.isPassable(x, y, 3)) {
				return 3;
			}
			else if (map.isPassable(x, y, 5)) {
				return 5;
			}
		}
		else if (d == 5) {
			if (map.isPassable(x, y, 5)) {
				return 5;
			}
			else if (map.isPassable(x, y, 4)) {
				return 4;
			}
			else if (map.isPassable(x, y, 6)) {
				return 6;
			}
		}
		else if (d == 6) {
			if (map.isPassable(x, y, 6)) {
				return 6;
			}
			else if (map.isPassable(x, y, 5)) {
				return 5;
			}
			else if (map.isPassable(x, y, 7)) {
				return 7;
			}
		}
		else if (d == 7) {
			if (map.isPassable(x, y, 7)) {
				return 7;
			}
			else if (map.isPassable(x, y, 6)) {
				return 6;
			}
			else if (map.isPassable(x, y, 0)) {
				return 0;
			}
		}
		else if (d == 0) {
			if (map.isPassable(x, y, 0)) {
				return 0;
			}
			else if (map.isPassable(x, y, 7)) {
				return 7;
			}
			else if (map.isPassable(x, y, 1)) {
				return 1;
			}
		}
		return -1;
	}

	// ----------From L1Character-------------
	/** 名称ＩＤ */
	private String _nameId; // ● 名称ＩＤ

	/**  */
	private boolean _Agro; // ● アクティブか

	/**  */
	private boolean _Agrocoi; // ● インビジアクティブか

	/**  */
	private boolean _Agrososc; // ● 主动变身

	/**  */
	private int _homeX; // ● ホームポイントＸ（モンスターの戻る位置とかペットの警戒位置）

	/**  */
	private int _homeY; // ● ホームポイントＹ（モンスターの戻る位置とかペットの警戒位置）

	// EXP、Drop分配中はターゲットリスト、ヘイトリストをクリアしない

	/**  */
	private boolean _reSpawn; // ● 再ポップするかどうか

	/**  */
	private int _lightSize; // ● ライト ０．なし １～１４．大きさ

	/** 武器损坏 */
	private boolean _weaponBreaked;

	/** 隐藏状态 */
	private int _hiddenStatus; // ● 潜入地下、飞到空中的状态

	/** 移动距离 */
	private int _movementDistance = 0;

	/** 临时正义值 */
	private int _tempLawful = 0;

	public boolean _destroyed = false; // 此实例是否已被释放

	private boolean _isResurrect;

	// ■■■■■■■■■■■■ 计时器关连 ■■■■■■■■■■

	/** 妖精森林 物品掉落 */
	private boolean _isDropitems = false;

	private boolean _forDropitems = false;

	// 死んでから消えるまでの时间计测用
	private DeleteTimer _deleteTask;

	private ScheduledFuture<?> _future = null;

	/** MOB族群信息 */
	private L1MobGroupInfo _mobGroupInfo = null;

	/** MOB族群ID */
	private int _mobGroupId = 0;

	/** 变身远程攻击 */
	private int _polyAtkRanged = -1;
	/** 变身箭头GFX */
	private int _polyArrowGfx = 0;

	public L1NpcInstance(final L1Npc template) {
		setStatus(0);
		setMoveSpeed(0);
		setDead(false);
		setreSpawn(false);

		if (template != null) {
			setting_template(template);
		}
	}

	// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

	/** 删除所有目标 */
	public void allTargetClear() {
		_hateList.clear();
		_dropHateList.clear();
		_target = null;
		_targetItemList.clear();
		_targetItem = null;
	}

	/** 怪物解除遁地、飞天、冰冻 */
	public void appearOnGround(final L1PcInstance pc) {
		if (getHiddenStatus() == HIDDEN_STATUS_SINK) {
			setHiddenStatus(HIDDEN_STATUS_NONE);
			setStatus(L1NpcDefaultAction.getInstance().getStatus(getTempCharGfx()));
			broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Appear));
			broadcastPacket(new S_CharVisualUpdate(this, getStatus()));
			if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) // 法师魔法 (隐身术)、黑暗妖精魔法 (暗隐术)中以外、GM以外
					&& !pc.isGm()) {
				_hateList.add(pc, 0);
				_target = pc;
			}
			onNpcAI(); // 怪物AI，开始
			startChat(CHAT_TIMING_HIDE);
		}
		else if (getHiddenStatus() == HIDDEN_STATUS_FLY) {
			setHiddenStatus(HIDDEN_STATUS_NONE);
			setStatus(L1NpcDefaultAction.getInstance().getStatus(getTempCharGfx()));
			broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Movedown));
			if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) // 法师魔法 (隐身术)、黑暗妖精魔法 (暗隐术)中以外、GM以外
					&& !pc.isGm()) {
				_hateList.add(pc, 0);
				_target = pc;
			}
			onNpcAI(); // 怪物AI，开始
			startChat(CHAT_TIMING_HIDE);
		}
		else if (getHiddenStatus() == HIDDEN_STATUS_ICE) {
			setHiddenStatus(HIDDEN_STATUS_NONE);
			setStatus(L1NpcDefaultAction.getInstance().getStatus(getTempCharGfx()));
			broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_AxeWalk));
			broadcastPacket(new S_CharVisualUpdate(this, getStatus()));
			if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) // 法师魔法 (隐身术)、黑暗妖精魔法 (暗隐术)中以外、GM以外
					&& !pc.isGm()) {
				_hateList.add(pc, 0);
				_target = pc;
			}
			onNpcAI(); // 怪物AI，开始
			startChat(CHAT_TIMING_HIDE);
		}
		// 吉尔塔斯反击屏障判断 (解除)
		else if (getHiddenStatus() == HIDDEN_STATUS_COUNTERATTACK_BARRIER) {
			setHiddenStatus(HIDDEN_STATUS_NONE);
			setStatus(L1NpcDefaultAction.getInstance().getStatus(getTempCharGfx()));
			broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Appear));
			broadcastPacket(new S_CharVisualUpdate(this, getStatus()));
			if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) // 法师魔法 (隐身术)、黑暗妖精魔法 (暗隐术)中以外、GM以外
					&& !pc.isGm()) {
				_hateList.add(pc, 0);
				_target = pc;
			}
			onNpcAI(); // 怪物AI，开始
			startChat(CHAT_TIMING_HIDE);
		}
	}

	/**  */
	public void approachPlayer(final L1PcInstance pc) {
		if (pc.hasSkillEffect(60) || pc.hasSkillEffect(97)) { // 法师魔法 (隐身术)、黑暗妖精魔法 (暗隐术)中
			return;
		}
		if (getHiddenStatus() == HIDDEN_STATUS_SINK) {
			if (getCurrentHp() == getMaxHp()) {
				if (pc.getLocation().getTileLineDistance(getLocation()) <= 2) {
					appearOnGround(pc);
				}
			}
		}
		else if (getHiddenStatus() == HIDDEN_STATUS_FLY) {
			if (getCurrentHp() == getMaxHp()) {
				if (pc.getLocation().getTileLineDistance(getLocation()) <= 1) {
					appearOnGround(pc);
				}
			}
			else {
				// if (getNpcTemplate().get_npcId() != 45681) { // リンドビオル以外
				searchItemFromAir();
				// }
			}
		}
		else if (getHiddenStatus() == HIDDEN_STATUS_ICE) {
			if (getCurrentHp() < getMaxHp()) {
				appearOnGround(pc);
			}
		}
		// 吉尔塔斯反击屏障判断 (解除)
		else if (getHiddenStatus() == HIDDEN_STATUS_COUNTERATTACK_BARRIER) {
			if (getCurrentHp() == getMaxHp()) {
				if (pc.getLocation().getTileLineDistance(getLocation()) <= 2) {
					appearOnGround(pc);
				}
			}
		}
	}

	/** 指定的攻击目标 */
	public void attackTarget(final L1Character target) {
		if (target instanceof L1PcInstance) {
			final L1PcInstance player = (L1PcInstance) target;
			if (player.isTeleport()) { // 瞬移处理中
				return;
			}
		}
		else if (target instanceof L1PetInstance) {
			final L1PetInstance pet = (L1PetInstance) target;
			final L1Character cha = pet.getMaster();
			if (cha instanceof L1PcInstance) {
				final L1PcInstance player = (L1PcInstance) cha;
				if (player.isTeleport()) { // 瞬移处理中
					return;
				}
			}
		}
		else if (target instanceof L1SummonInstance) {
			final L1SummonInstance summon = (L1SummonInstance) target;
			final L1Character cha = summon.getMaster();
			if (cha instanceof L1PcInstance) {
				final L1PcInstance player = (L1PcInstance) cha;
				if (player.isTeleport()) { // 瞬移处理中
					return;
				}
			}
		}
		if (this instanceof L1PetInstance) {
			final L1PetInstance pet = (L1PetInstance) this;
			final L1Character cha = pet.getMaster();
			if (cha instanceof L1PcInstance) {
				final L1PcInstance player = (L1PcInstance) cha;
				if (player.isTeleport()) { // 瞬移处理中
					return;
				}
			}
		}
		else if (this instanceof L1SummonInstance) {
			final L1SummonInstance summon = (L1SummonInstance) this;
			final L1Character cha = summon.getMaster();
			if (cha instanceof L1PcInstance) {
				final L1PcInstance player = (L1PcInstance) cha;
				if (player.isTeleport()) { // 瞬移处理中
					return;
				}
			}
		}

		if (target instanceof L1NpcInstance) {
			final L1NpcInstance npc = (L1NpcInstance) target;
			if (npc.getHiddenStatus() != HIDDEN_STATUS_NONE) { // 潜入地下、飞到天上
				allTargetClear();
				return;
			}
		}

		boolean isCounterBarrier = false;
		final L1Attack attack = new L1Attack(this, target);
		if (attack.calcHit()) {
			if (target.hasSkillEffect(COUNTER_BARRIER)) {
				final L1Magic magic = new L1Magic(target, this);
				final boolean isProbability = magic.calcProbabilityMagic(COUNTER_BARRIER);
				final boolean isShortDistance = attack.isShortDistance();
				if (isProbability && isShortDistance) {
					isCounterBarrier = true;
				}
			}
			if (!isCounterBarrier) {
				attack.calcDamage();
			}
		}
		if (isCounterBarrier) {
			attack.actionCounterBarrier();
			attack.commitCounterBarrier();
		}
		else {
			attack.action();
			attack.commit();
		}
		setSleepTime(calcSleepTime(getAtkspeed(), ATTACK_SPEED));
	}

	/** 检查设定有效目标 */
	public void checkTarget() {
		if ((_target == null) || (_target.getMapId() != getMapId()) || (_target.getCurrentHp() <= 0) || _target.isDead() || (_target.isInvisble() && !getNpcTemplate().is_agrocoi() && !_hateList.containsKey(_target))
		// 目标距离超过30以上
				|| (_target.getTileLineDistance(this) > 30)) {
			if (_target != null) {
				tagertClear();
			}
			if (!_hateList.isEmpty()) {
				_target = _hateList.getMaxHateCharacter();
				checkTarget();
			}
		}
	}

	/** 检查目标项目 */
	public void checkTargetItem() {
		if ((_targetItem == null) || (_targetItem.getMapId() != getMapId()) || (getLocation().getTileDistance(_targetItem.getLocation()) > 15)) {
			if (!_targetItemList.isEmpty()) {
				_targetItem = _targetItemList.get(0);
				_targetItemList.remove(0);
				checkTargetItem();
			}
			else {
				_targetItem = null;
			}
		}
	}

	/** 删除自己 */
	public void deleteMe() {
		_destroyed = true;
		if (getInventory() != null) {
			getInventory().clearItems();
		}
		allTargetClear();
		_master = null;
		L1World.getInstance().removeVisibleObject(this);
		L1World.getInstance().removeObject(this);
		final List<L1PcInstance> players = L1World.getInstance().getRecognizePlayer(this);
		if (players.size() > 0) {
			final S_RemoveObject s_deleteNewObject = new S_RemoveObject(this);
			for (final L1PcInstance pc : players) {
				if (pc != null) {
					pc.removeKnownObject(this);
					// if(!L1Character.distancepc(user, this))
					pc.sendPackets(s_deleteNewObject);
				}
			}
		}
		removeAllKnownObjects();

		// 设置重生
		final L1MobGroupInfo mobGroupInfo = getMobGroupInfo();
		if (mobGroupInfo == null) {
			if (isReSpawn()) {
				onDecay(true);
			}
		}
		else {
			if (mobGroupInfo.removeMember(this) == 0) { // 集团成员湮灭
				setMobGroupInfo(null);
				if (isReSpawn()) {
					onDecay(false);
				}
			}
		}
	}

	public int drainMana(final int drain) {
		if (_drainedMana >= Config.MANA_DRAIN_LIMIT_PER_NPC) {
			return 0;
		}
		int result = Math.min(drain, getCurrentMp());
		if (_drainedMana + result > Config.MANA_DRAIN_LIMIT_PER_NPC) {
			result = Config.MANA_DRAIN_LIMIT_PER_NPC - _drainedMana;
		}
		_drainedMana += result;
		return result;
	}

	public boolean forDropitems() {
		return _forDropitems;
	}

	/** 取得远程攻击 */
	public int getAtkRanged() {
		if (_polyAtkRanged == -1) {
			return getNpcTemplate().get_ranged();
		}
		return _polyAtkRanged;
	}

	/** 获得攻击速度 */
	public int getAtkspeed() {
		return _atkspeed;
	}

	public L1HateList getHateList() {
		return _hateList;
	}

	/** 获得隐藏状态 */
	public int getHiddenStatus() {
		return _hiddenStatus;
	}

	/**  */
	public int getHomeX() {
		return _homeX;
	}

	/**  */
	public int getHomeY() {
		return _homeY;
	}

	@Override
	public L1Inventory getInventory() {
		return _inventory;
	}

	/**  */
	public int getLightSize() {
		return _lightSize;
	}

	/** 获得主人 */
	public L1Character getMaster() {
		return _master;
	}

	/** 取得MOB族群ID */
	public int getMobGroupId() {
		return _mobGroupId;
	}

	/** 取得MOB族群信息 */
	public L1MobGroupInfo getMobGroupInfo() {
		return _mobGroupInfo;
	}

	/** 获得移动距离 */
	public int getMovementDistance() {
		return _movementDistance;
	}

	/** 获得名称ＩＤ */
	public String getNameId() {
		return _nameId;
	}

	/** 取得NPC ID */
	public int getNpcId() {
		return _npcTemplate.get_npcId();
	}

	/** 取得NPC范本 */
	public L1Npc getNpcTemplate() {
		return _npcTemplate;
	}

	public int getParalysisTime() {
		return _paralysisTime;
	}

	/**  */
	public int getPassispeed() {
		return _passispeed;
	}

	/** 取得宠物的费用 */
	public int getPetcost() {
		return _petcost;
	}

	/** 取得变身箭头GFX */
	public int getPolyArrowGfx() {
		return _polyArrowGfx;
	}

	/** 取得变身远程攻击 */
	public int getPolyAtkRanged() {
		return _polyAtkRanged;
	}

	/**  */
	public L1Spawn getSpawn() {
		return _spawn;
	}

	/**  */
	public int getSpawnNumber() {
		return _spawnNumber;
	}

	// ■■■■■■■■■■■■■ 移动关连 ■■■■■■■■■■■

	/** 获得临时正义值 */
	public int getTempLawful() {
		return _tempLawful;
	}

	public void giveDropItems(final boolean i) {
		_forDropitems = i;
	}

	/**  */
	public boolean isAgro() {
		return _Agro;
	}

	/**  */
	public boolean isAgrocoi() {
		return _Agrocoi;
	}

	/**  */
	public boolean isAgrososc() {
		return _Agrososc;
	}

	public boolean isDropitems() {
		return _isDropitems;
	}

	/** MOB族群 */
	public boolean isInMobGroup() {
		return getMobGroupInfo() != null;
	}

	/** 捡取道具 */
	public boolean isPickupItem() {
		return _pickupItem;
	}

	/**  */
	public boolean isReSpawn() {
		return _reSpawn;
	}

	// ■■■■■■■■■■■■ 项目关连 ■■■■■■■■■■

	public boolean isRest() {
		return _rest;
	}

	public boolean isResurrect() {
		return _isResurrect;
	}

	/** 武器损坏中 */
	public boolean isWeaponBreaked() {
		return _weaponBreaked;
	}

	public int moveDirection(final int x, final int y) { // 目标点Ｘ 目标点Ｙ
		return moveDirection(x, y, getLocation().getLineDistance(new Point(x, y)));
	}

	/** 返回离目标最佳的移动方向 */
	public int moveDirection(final int x, final int y, final double d) { // 目标点Ｘ 目标点Ｙ 目标的距离
		int dir = 0;
		if ((hasSkillEffect(40) == true) && (d >= 2D)) { // ダークネスが挂かっていて、距离が2以上の场合追迹终了
			return -1;
		}
		else if (d > 30D) { // 距离が激しく远い场合は追迹终了
			return -1;
		}
		else if (d > courceRange) { // 距离が远い场合は单纯计算
			dir = targetDirection(x, y);
			dir = checkObject(getX(), getY(), getMapId(), dir);
		}
		else { // 探索目标的最短经路
			dir = _serchCource(x, y);
			if (dir == -1) { // 目标までの经路がなっかた场合はとりあえず近づいておく
				dir = targetDirection(x, y);
				if (!isExsistCharacterBetweenTarget(dir)) {
					dir = checkObject(getX(), getY(), getMapId(), dir);
				}
			}
		}
		return dir;
	}

	/** 瞬移到临近目标 */
	public boolean nearTeleport(int nx, int ny) {
		final int rdir = Random.nextInt(8);
		int dir;
		for (int i = 0; i < 8; i++) {
			dir = rdir + i;
			if (dir > 7) {
				dir -= 8;
			}
			if (dir == 1) {
				nx++;
				ny--;
			}
			else if (dir == 2) {
				nx++;
			}
			else if (dir == 3) {
				nx++;
				ny++;
			}
			else if (dir == 4) {
				ny++;
			}
			else if (dir == 5) {
				nx--;
				ny++;
			}
			else if (dir == 6) {
				nx--;
			}
			else if (dir == 7) {
				nx--;
				ny--;
			}
			else if (dir == 0) {
				ny--;
			}
			if (getMap().isPassable(nx, ny)) {
				dir += 4;
				if (dir > 7) {
					dir -= 8;
				}
				teleport(nx, ny, dir);
				setCurrentMp(getCurrentMp() - 10);
				return true;
			}
		}
		return false;
	}

	/** 处理时没有目标（不论是否返回值处理退出AI） */
	public boolean noTarget() {
		if ((_master != null) && (_master.getMapId() == getMapId()) && (getLocation().getTileLineDistance(_master.getLocation()) > 2)) { // 主人が同じマップにいて离れてる场合は追尾
			final int dir = moveDirection(_master.getX(), _master.getY());
			if (dir != -1) {
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
			}
			else {
				return true;
			}
		}
		else {
			if (L1World.getInstance().getRecognizePlayer(this).isEmpty()) {
				return true; // 周りにプレイヤーがいなくなったらＡＩ处理终了
			}
			// 移动できるキャラはランダムに动いておく
			if ((_master == null) && (getPassispeed() > 0) && !isRest()) {
				// グループに属していないorグループに属していてリーダーの场合、ランダムに动いておく
				final L1MobGroupInfo mobGroupInfo = getMobGroupInfo();
				if ((mobGroupInfo == null) || ((mobGroupInfo != null) && mobGroupInfo.isLeader(this))) {
					// 移动する予定の距离を移动し终えたら、新たに距离と方向を决める
					// そうでないなら、移动する予定の距离をデクリメント
					if (_randomMoveDistance == 0) {
						_randomMoveDistance = Random.nextInt(5) + 1;
						_randomMoveDirection = Random.nextInt(20);
						// ホームポイントから离れすぎないように、一定の确率でホームポイントの方向に补正
						if ((getHomeX() != 0) && (getHomeY() != 0) && (_randomMoveDirection < 8) && (Random.nextInt(3) == 0)) {
							_randomMoveDirection = moveDirection(getHomeX(), getHomeY());
						}
					}
					else {
						_randomMoveDistance--;
					}
					final int dir = checkObject(getX(), getY(), getMapId(), _randomMoveDirection);
					if (dir != -1) {
						setDirectionMove(dir);
						setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
					}
				}
				else { // リーダーを追尾
					final L1NpcInstance leader = mobGroupInfo.getLeader();
					if (getLocation().getTileLineDistance(leader.getLocation()) > 2) {
						final int dir = moveDirection(leader.getX(), leader.getY());
						if (dir == -1) {
							return true;
						}
						else {
							setDirectionMove(dir);
							setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
						}
					}
				}
			}
		}
		return false;
	}

	// ■■■■■■■■■■■■■ 技能关连(npcskillsテーブル实装されたら消すかも) ■■■■■■■■■■■

	/** NPC延迟时间 */
	public void npcSleepTime(final int i, final int type) {
		setSleepTime(calcSleepTime(SprTable.getInstance().getSprSpeed(getTempCharGfx(), i), type));
	}

	// 对象ID重用SpawnTask传递
	// 怪物不会被重用 因为会变得复杂
	public void onDecay(final boolean isReuseId) {
		int id = 0;
		if (isReuseId) {
			id = getId();
		}
		else {
			id = 0;
		}
		_spawn.executeSpawnTask(_spawnNumber, id);
	}

	/** 变形怪变形 */
	public void onDoppel(final boolean isChangeShape) {
	}

	/** 在最后的动作 */
	public void onFinalAction(final L1PcInstance pc, final String s) {
	}

	/** 获取项目 */
	public void onGetItem(final L1ItemInstance item) {
		refineItem();
		getInventory().shuffle();
		if (getNpcTemplate().get_digestitem() > 0) {
			setDigestItem(item);
		}
	}

	// 使用的物品处理（Ｔｙｐｅによって结构违うのでオーバライドで实装）
	public void onItemUse() {
	}

	/** 开始ＡＩ */
	public void onNpcAI() {
	}

	@Override
	public void onPerceive(final L1PcInstance perceivedFrom) {
		perceivedFrom.addKnownObject(this);
		perceivedFrom.sendPackets(new S_NPCPack(this));
		onNpcAI();
	}

	/** 目标处理 */
	public void onTarget() {
		setActived(true);
		_targetItemList.clear();
		_targetItem = null;
		final L1Character target = _target; // ここから先は_targetが变わると影响出るので别领域に参照确保
		if (getAtkspeed() == 0) { // 逃げるキャラ
			if (getPassispeed() > 0) { // 角色可以移动
				int escapeDistance = 15;
				if (hasSkillEffect(40) == true) {
					escapeDistance = 1;
				}
				if (getLocation().getTileLineDistance(target.getLocation()) > escapeDistance) { // ターゲットから逃げるの终了
					tagertClear();
				}
				else { // ターゲットから逃げる
					int dir = targetReverseDirection(target.getX(), target.getY());
					dir = checkObject(getX(), getY(), getMapId(), dir);
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
				}
			}
		}
		else { // 逃げないキャラ
			if (isAttackPosition(target.getX(), target.getY(), getAtkRanged())) { // 攻击可能位置
				if (mobSkill.isSkillTrigger(target)) { // トリガの条件に合うスキルがある
					if (mobSkill.skillUse(target, true)) { // スキル使用(mobskill.sqlのTriRndに从う)
						setSleepTime(calcSleepTime(mobSkill.getSleepTime(), MAGIC_SPEED));
					}
					else { // スキル使用が失败したら物理攻击
						setHeading(targetDirection(target.getX(), target.getY()));
						attackTarget(target);
					}
				}
				else {
					setHeading(targetDirection(target.getX(), target.getY()));
					attackTarget(target);
				}
			}
			else { // 攻击不可能位置
				if (mobSkill.skillUse(target, false)) { // 使用技能(mobskill.sqlのTriRndに从わず、発动确率は100%。ただしサモン、强制变身は常にTriRndに从う。)
					setSleepTime(calcSleepTime(mobSkill.getSleepTime(), MAGIC_SPEED));
					return;
				}

				if (getPassispeed() > 0) {
					// 角色可以移动
					final int distance = getLocation().getTileDistance(target.getLocation());
					if ((firstFound == true) && getNpcTemplate().is_teleport() && (distance > 3) && (distance < 15)) {
						if (nearTeleport(target.getX(), target.getY()) == true) {
							firstFound = false;
							return;
						}
					}

					if (getNpcTemplate().is_teleport() && (20 > Random.nextInt(100)) && (getCurrentMp() >= 10) && (distance > 6) && (distance < 15)) { // 瞬移移动
						if (nearTeleport(target.getX(), target.getY()) == true) {
							return;
						}
					}
					final int dir = moveDirection(target.getX(), target.getY());
					if (dir == -1) {
						// 假如怪物走不过去 就找附近下一个玩家攻击
						searchTarget();
					}
					else {
						setDirectionMove(dir);
						setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
					}
				}
				else {
					// 角色不能移动（从目标排除、ＰＴのときドロップチャンスがリセットされるけどまぁ自业自得）
					tagertClear();
				}
			}
		}
	}

	/** 处理目标项目 */
	public void onTargetItem() {
		if (getLocation().getTileLineDistance(_targetItem.getLocation()) == 0) { // 可以拿起的位置
			pickupTargetItem(_targetItem);
		}
		else { // 不可能位置
			final int dir = moveDirection(_targetItem.getX(), _targetItem.getY());
			if (dir == -1) { // 拾うの谛め
				_targetItemList.remove(_targetItem);
				_targetItem = null;
			}
			else { // 移至目标项
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
			}
		}
	}

	/** 拿起一个项目 */
	public void pickupTargetItem(final L1ItemInstance targetItem) {
		final L1Inventory groundInventory = L1World.getInstance().getInventory(targetItem.getX(), targetItem.getY(), targetItem.getMapId());
		final L1ItemInstance item = groundInventory.tradeItem(targetItem, targetItem.getCount(), getInventory());
		turnOnOffLight();
		onGetItem(item);
		_targetItemList.remove(_targetItem);
		_targetItem = null;
		setSleepTime(1000);
	}

	/** 受到伤害 */
	public void receiveDamage(final L1Character attacker, final int damage) {
	}

	/** 受到吸魔伤害 */
	public void ReceiveManaDamage(final L1Character attacker, final int damageMp) {
	}

	/** 净化项目 */
	public void refineItem() {

		int[] materials = null;
		int[] counts = null;
		int[] createitem = null;
		int[] createcount = null;

		if (_npcTemplate.get_npcId() == 45032) { // 布拉伯
			// 奥里哈鲁根的剑身
			if ((getExp() != 0) && !_inventory.checkItem(20)) {
				materials = new int[] { 40508, 40521, 40045 }; // 奥里哈鲁根、精灵羽翼、红宝石
				counts = new int[] { 150, 3, 3 };
				createitem = new int[] { 20 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						_inventory.storeItem(createitem[j], createcount[j]);
					}
				}
			}
			// 长剑的剑身
			if ((getExp() != 0) && !_inventory.checkItem(19)) {
				materials = new int[] { 40494, 40521 }; // 纯粹的米索莉块、精灵羽翼
				counts = new int[] { 150, 3 };
				createitem = new int[] { 19 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						_inventory.storeItem(createitem[j], createcount[j]);
					}
				}
			}
			// 短剑的剑身
			if ((getExp() != 0) && !_inventory.checkItem(3)) {
				materials = new int[] { 40494, 40521 }; // 纯粹的米索莉块、精灵羽翼
				counts = new int[] { 50, 1 };
				createitem = new int[] { 3 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						_inventory.storeItem(createitem[j], createcount[j]);
					}
				}
			}
			// 覆上奥里哈鲁根的角
			if ((getExp() != 0) && !_inventory.checkItem(100)) {
				materials = new int[] { 88, 40508, 40045 }; // 潘的角、奥里哈鲁根、红宝石
				counts = new int[] { 4, 80, 3 };
				createitem = new int[] { 100 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						_inventory.storeItem(createitem[j], createcount[j]);
					}
				}
			}
			// 覆上米索莉的角
			if ((getExp() != 0) && !_inventory.checkItem(89)) {
				materials = new int[] { 88, 40494 }; // 潘的角、纯粹的米索莉块
				counts = new int[] { 2, 80 };
				createitem = new int[] { 89 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						final L1ItemInstance item = _inventory.storeItem(createitem[j], createcount[j]);
						if (getNpcTemplate().get_digestitem() > 0) {
							setDigestItem(item);
						}
					}
				}
			}
		}
		else if (_npcTemplate.get_npcId() == 81069) { // 变形怪（法师45级任务）
			// 变形怪的血
			if ((getExp() != 0) && !_inventory.checkItem(40542)) {
				materials = new int[] { 40032 }; // 伊娃的祝福
				counts = new int[] { 1 };
				createitem = new int[] { 40542 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						_inventory.storeItem(createitem[j], createcount[j]);
					}
				}
			}
		}
		else if ((_npcTemplate.get_npcId() == 45166 // 南瓜怪
				)
				|| (_npcTemplate.get_npcId() == 45167)) { // 南瓜怪
			// 南瓜种子
			if ((getExp() != 0) && !_inventory.checkItem(40726)) {
				materials = new int[] { 40725 }; // 南瓜糖果
				counts = new int[] { 1 };
				createitem = new int[] { 40726 };
				createcount = new int[] { 1 };
				if (_inventory.checkItem(materials, counts)) {
					for (int i = 0; i < materials.length; i++) {
						_inventory.consumeItem(materials[i], counts[i]);
					}
					for (int j = 0; j < createitem.length; j++) {
						_inventory.storeItem(createitem[j], createcount[j]);
					}
				}
			}
		}
	}

	@Override
	public synchronized void resurrect(final int hp) {
		if (_destroyed) {
			return;
		}
		if (_deleteTask != null) {
			if (!_future.cancel(false)) { // 不能被取消
				return;
			}
			_deleteTask = null;
			_future = null;
		}
		super.resurrect(hp);

		// キャンセレーションをエフェクトなしでかける
		// 本来は死亡时に行うべきだが、负荷が大きくなるため复活时に行う
		final L1SkillUse skill = new L1SkillUse();
		skill.handleCommands(null, CANCELLATION, getId(), getX(), getY(), null, 0, L1SkillUse.TYPE_LOGIN, this);
	}

	/** 怪物飞天中，发现特定道具时解除飞天捡拾道具 */
	public void searchItemFromAir() {
		final List<L1GroundInventory> gInventorys = Lists.newList();

		for (final L1Object obj : L1World.getInstance().getVisibleObjects(this)) {
			if ((obj != null) && (obj instanceof L1GroundInventory)) {
				gInventorys.add((L1GroundInventory) obj);
			}
		}
		if (gInventorys.isEmpty()) {
			return;
		}

		final int pickupIndex = Random.nextInt(gInventorys.size());
		final L1GroundInventory inventory = gInventorys.get(pickupIndex);
		for (final L1ItemInstance item : inventory.getItems()) {
			if ((item.getItem().getType() == 6) // potion(药水)
					|| (item.getItem().getType() == 7)) { // food(食物)
				if (getInventory().checkAddItem(item, item.getCount()) == L1Inventory.OK) {
					if (getHiddenStatus() == HIDDEN_STATUS_FLY) {
						setHiddenStatus(HIDDEN_STATUS_NONE);
						setStatus(L1NpcDefaultAction.getInstance().getStatus(getTempCharGfx()));
						broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Movedown));
						onNpcAI();
						startChat(CHAT_TIMING_HIDE);
						_targetItem = item;
						_targetItemList.add(_targetItem);
					}
				}
			}
		}
	}

	// 寻找目标（Ｔｙｐｅによって结构违うのでオーバライドで实装）
	public void searchTarget() {
		tagertClear();
	}

	/** 找到目标项目 */
	public void searchTargetItem() {
		final List<L1GroundInventory> gInventorys = Lists.newList();

		for (final L1Object obj : L1World.getInstance().getVisibleObjects(this)) {
			if ((obj != null) && (obj instanceof L1GroundInventory)) {
				gInventorys.add((L1GroundInventory) obj);
			}
		}
		if (gInventorys.size() == 0) {
			return;
		}

		// 拿起一个随机选择项目（库存）
		final int pickupIndex = Random.nextInt(gInventorys.size());
		final L1GroundInventory inventory = gInventorys.get(pickupIndex);
		for (final L1ItemInstance item : inventory.getItems()) {
			if (getInventory().checkAddItem(item, item.getCount()) == L1Inventory.OK) { // 持てるならターゲットアイテムに加える
				_targetItem = item;
				_targetItemList.add(_targetItem);
			}
		}
	}

	// 仲间意识によりアクティブになるＮＰＣの检索（攻击者对一个玩家有效）
	public void serchLink(final L1PcInstance targetPlayer, final int family) {
		final List<L1Object> targetKnownObjects = targetPlayer.getKnownObjects();
		for (final Object knownObject : targetKnownObjects) {
			if (knownObject instanceof L1NpcInstance) {
				final L1NpcInstance npc = (L1NpcInstance) knownObject;
				if (npc.getNpcTemplate().get_agrofamily() > 0) {
					// 仲间に对してアクティブになる场合
					if (npc.getNpcTemplate().get_agrofamily() == 1) {
						// 同种族に对してのみ仲间意识
						if (npc.getNpcTemplate().get_family() == family) {
							npc.setLink(targetPlayer);
						}
					}
					else {
						// 全てのＮＰＣに对して仲间意识
						npc.setLink(targetPlayer);
					}
				}
				final L1MobGroupInfo mobGroupInfo = getMobGroupInfo();
				if (mobGroupInfo != null) {
					if ((getMobGroupId() != 0) && (getMobGroupId() == npc.getMobGroupId())) { // 同じグループ
						npc.setLink(targetPlayer);
					}
				}
			}
		}
	}

	/**  */
	public void setAgro(final boolean flag) {
		_Agro = flag;
	}

	/**  */
	public void setAgrocoi(final boolean flag) {
		_Agrocoi = flag;
	}

	/**  */
	public void setAgrososc(final boolean flag) {
		_Agrososc = flag;
	}

	/** 设置攻击速度 */
	public void setAtkspeed(final int i) {
		_atkspeed = i;
	}

	/** 设置消化道具 */
	public void setDigestItem(final L1ItemInstance item) {
		_digestItems.put(new Integer(item.getId()), new Integer(getNpcTemplate().get_digestitem()));
		if (!_digestItemRunning) {
			final DigestItemTimer digestItemTimer = new DigestItemTimer();
			GeneralThreadPool.getInstance().execute(digestItemTimer);
		}
	}

	// 在指定的方向移动
	/** 设置移动方向 */
	public void setDirectionMove(final int dir) {
		if (dir >= 0) {
			int nx = 0;
			int ny = 0;

			switch (dir) {
				case 1:
					nx = 1;
					ny = -1;
					setHeading(1);
					break;

				case 2:
					nx = 1;
					ny = 0;
					setHeading(2);
					break;

				case 3:
					nx = 1;
					ny = 1;
					setHeading(3);
					break;

				case 4:
					nx = 0;
					ny = 1;
					setHeading(4);
					break;

				case 5:
					nx = -1;
					ny = 1;
					setHeading(5);
					break;

				case 6:
					nx = -1;
					ny = 0;
					setHeading(6);
					break;

				case 7:
					nx = -1;
					ny = -1;
					setHeading(7);
					break;

				case 0:
					nx = 0;
					ny = -1;
					setHeading(0);
					break;

				default:
					break;

			}

			getMap().setPassable(getLocation(), true);

			final int nnx = getX() + nx;
			final int nny = getY() + ny;
			setX(nnx);
			setY(nny);

			getMap().setPassable(getLocation(), false);

			broadcastPacket(new S_MoveCharPacket(this));

			// movement_distanceマス以上离れたらホームポイントへテレポート
			if (getMovementDistance() > 0) {
				if ((this instanceof L1GuardInstance) || (this instanceof L1MerchantInstance) || (this instanceof L1MonsterInstance)) {
					if (getLocation().getLineDistance(new Point(getHomeX(), getHomeY())) > getMovementDistance()) {
						teleport(getHomeX(), getHomeY(), getHeading());
					}
				}
			}
			// 判断士兵的怨灵、怨灵、哈蒙将军的怨灵离开墓园范围时传送回墓园！
			if ((getNpcTemplate().get_npcId() >= 45912) && (getNpcTemplate().get_npcId() <= 45916)) {
				if (!((getX() >= 32591) && (getX() <= 32644) && (getY() >= 32643) && (getY() <= 32688) && (getMapId() == 4))) {
					teleport(getHomeX(), getHomeY(), getHeading());
				}
			}
		}
	}

	public void setDropItems(final boolean i) {
		_isDropitems = i;
	}

	/** 仇恨设置 */
	public void setHate(final L1Character cha, int hate) {
		if ((cha != null) && (cha.getId() != getId())) {
			if (!isFirstAttack() && (hate != 0)) {
				// hate += 20; // ＦＡヘイト
				hate += getMaxHp() / 10; // ＦＡヘイト
				setFirstAttack(true);
			}

			_hateList.add(cha, hate);
			_dropHateList.add(cha, hate);
			_target = _hateList.getMaxHateCharacter();
			checkTarget();
		}
	}

	/** 设置隐藏状态 */
	public void setHiddenStatus(final int i) {
		_hiddenStatus = i;
	}

	/**  */
	public void setHomeX(final int i) {
		_homeX = i;
	}

	/**  */
	public void setHomeY(final int i) {
		_homeY = i;
	}

	/** 设定角色身上的道具 */
	public void setInventory(final L1Inventory inventory) {
		_inventory = inventory;
	}

	/**  */
	public void setLightSize(final int i) {
		_lightSize = i;
	}

	// 设置链接
	public void setLink(final L1Character cha) {
	}

	/** 设置主人 */
	public void setMaster(final L1Character cha) {
		_master = cha;
	}

	/** 设定MOB族群ID */
	public void setMobGroupId(final int i) {
		_mobGroupId = i;
	}

	/** 设定MOB族群信息 */
	public void setMobGroupInfo(final L1MobGroupInfo m) {
		_mobGroupInfo = m;
	}

	/** 设置移动距离 */
	public void setMovementDistance(final int i) {
		_movementDistance = i;
	}

	/** 设置名称ＩＤ */
	public void setNameId(final String s) {
		_nameId = s;
	}

	public void setParalysisTime(final int ptime) {
		_paralysisTime = ptime;
	}

	/**  */
	public void setPassispeed(final int i) {
		_passispeed = i;
	}

	/** 设定宠物的费用 */
	public void setPetcost(final int i) {
		_petcost = i;
	}

	/** 设定捡取道具 */
	public void setPickupItem(final boolean flag) {
		_pickupItem = flag;
	}

	/** 设定变身箭头GFX */
	public void setPolyArrowGfx(final int i) {
		_polyArrowGfx = i;
	}

	/** 设定变身远程攻击 */
	public void setPolyAtkRanged(final int i) {
		_polyAtkRanged = i;
	}

	/**  */
	public void setreSpawn(final boolean flag) {
		_reSpawn = flag;
	}

	public void setRest(final boolean _rest) {
		this._rest = _rest;
	}

	public void setResurrect(final boolean flag) {
		_isResurrect = flag;
	}

	/**  */
	public void setSpawn(final L1Spawn spawn) {
		_spawn = spawn;
	}

	/**  */
	public void setSpawnNumber(final int number) {
		_spawnNumber = number;
	}

	/** 设置临时正义值 */
	public void setTempLawful(final int i) {
		_tempLawful = i;
	}

	/** 初始化指定的各种模板值 */
	public void setting_template(final L1Npc template) {
		_npcTemplate = template;
		int randomlevel = 0;
		double rate = 0;
		double diff = 0;
		setName(template.get_name());
		setNameId(template.get_nameid());
		if (template.get_randomlevel() == 0) { // 没有指定Lv
			setLevel(template.get_level());
		}
		else { // 指定随机Lv（最小值:get_level(),最大值:get_randomlevel()）
			randomlevel = Random.nextInt(template.get_randomlevel() - template.get_level() + 1);
			diff = template.get_randomlevel() - template.get_level();
			rate = randomlevel / diff;
			randomlevel += template.get_level();
			setLevel(randomlevel);
		}
		if (template.get_randomhp() == 0) {
			setMaxHp(template.get_hp());
			setCurrentHpDirect(template.get_hp());
		}
		else {
			final double randomhp = rate * (template.get_randomhp() - template.get_hp());
			final int hp = (int) (template.get_hp() + randomhp);
			setMaxHp(hp);
			setCurrentHpDirect(hp);
		}
		if (template.get_randommp() == 0) {
			setMaxMp(template.get_mp());
			setCurrentMpDirect(template.get_mp());
		}
		else {
			final double randommp = rate * (template.get_randommp() - template.get_mp());
			final int mp = (int) (template.get_mp() + randommp);
			setMaxMp(mp);
			setCurrentMpDirect(mp);
		}
		if (template.get_randomac() == 0) {
			setAc(template.get_ac());
		}
		else {
			final double randomac = rate * (template.get_randomac() - template.get_ac());
			final int ac = (int) (template.get_ac() + randomac);
			setAc(ac);
		}
		if (template.get_randomlevel() == 0) {
			setStr(template.get_str());
			setCon(template.get_con());
			setDex(template.get_dex());
			setInt(template.get_int());
			setWis(template.get_wis());
			setMr(template.get_mr());
		}
		else {
			setStr((byte) Math.min(template.get_str() + diff, 127));
			setCon((byte) Math.min(template.get_con() + diff, 127));
			setDex((byte) Math.min(template.get_dex() + diff, 127));
			setInt((byte) Math.min(template.get_int() + diff, 127));
			setWis((byte) Math.min(template.get_wis() + diff, 127));
			setMr((byte) Math.min(template.get_mr() + diff, 127));

			addHitup((int) diff * 2);
			addDmgup((int) diff * 2);
		}
		setAgro(template.is_agro());
		setAgrocoi(template.is_agrocoi());
		setAgrososc(template.is_agrososc());
		setTempCharGfx(template.get_gfxid());
		setGfxId(template.get_gfxid());
		setStatus(L1NpcDefaultAction.getInstance().getStatus(getTempCharGfx()));
		setPolyAtkRanged(template.get_ranged());
		setPolyArrowGfx(template.getBowActId());

		// 移动
		if (template.get_passispeed() != 0) {
			setPassispeed(SprTable.getInstance().getSprSpeed(getTempCharGfx(), getStatus()));
		}
		else {
			setPassispeed(0);
		}
		// 攻击
		if (template.get_atkspeed() != 0) {
			int actid = (getStatus() + 1);
			if (L1NpcDefaultAction.getInstance().getDefaultAttack(getTempCharGfx()) != actid) {
				actid = L1NpcDefaultAction.getInstance().getDefaultAttack(getTempCharGfx());
			}
			setAtkspeed(SprTable.getInstance().getSprSpeed(getTempCharGfx(), actid));
		}
		else {
			setAtkspeed(0);
		}

		if (template.get_randomexp() == 0) {
			setExp(template.get_exp());
		}
		else {
			final int level = getLevel();
			int exp = level * level;
			exp += 1;
			setExp(exp);
		}
		if (template.get_randomlawful() == 0) {
			setLawful(template.get_lawful());
			setTempLawful(template.get_lawful());
		}
		else {
			final double randomlawful = rate * (template.get_randomlawful() - template.get_lawful());
			final int lawful = (int) (template.get_lawful() + randomlawful);
			setLawful(lawful);
			setTempLawful(lawful);
		}
		setPickupItem(template.is_picupitem());
		if (template.is_bravespeed()) {
			setBraveSpeed(1);
		}
		else {
			setBraveSpeed(0);
		}
		if (template.get_digestitem() > 0) {
			_digestItems = Maps.newMap();
		}
		setKarma(template.getKarma());
		setLightSize(template.getLightSize());

		mobSkill = new L1MobSkillUse(this);
	}

	// ※破弃后に动かないよう强制的にＡＩ等のスレッド处理中止（念のため）

	/** 设定武器损坏 */
	public void setWeaponBreaked(final boolean flag) {
		_weaponBreaked = flag;
	}

	/** 开始怪物喊话 */
	public void startChat(final int chatTiming) {
		// 出现时与死亡时的喊话
		if ((chatTiming == CHAT_TIMING_APPEARANCE) && isDead()) {
			return;
		}
		if ((chatTiming == CHAT_TIMING_DEAD) && !isDead()) {
			return;
		}
		if ((chatTiming == CHAT_TIMING_HIDE) && isDead()) {
			return;
		}
		if ((chatTiming == CHAT_TIMING_GAME_TIME) && isDead()) {
			return;
		}

		final int npcId = getNpcTemplate().get_npcId();
		L1NpcChat npcChat = null;
		if (chatTiming == CHAT_TIMING_APPEARANCE) {
			npcChat = NpcChatTable.getInstance().getTemplateAppearance(npcId);
		}
		else if (chatTiming == CHAT_TIMING_DEAD) {
			npcChat = NpcChatTable.getInstance().getTemplateDead(npcId);
		}
		else if (chatTiming == CHAT_TIMING_HIDE) {
			npcChat = NpcChatTable.getInstance().getTemplateHide(npcId);
		}
		else if (chatTiming == CHAT_TIMING_GAME_TIME) {
			npcChat = NpcChatTable.getInstance().getTemplateGameTime(npcId);
		}
		if (npcChat == null) {
			return;
		}

		final Timer timer = new Timer(true);
		final L1NpcChatTimer npcChatTimer = new L1NpcChatTimer(this, npcChat);
		if (!npcChat.isRepeat()) {
			timer.schedule(npcChatTimer, npcChat.getStartDelayTime());
		}
		else {
			timer.scheduleAtFixedRate(npcChatTimer, npcChat.getStartDelayTime(), npcChat.getRepeatInterval());
		}
	}

	/** HP自然回复 */
	public final void startHpRegeneration() {
		final int hprInterval = getNpcTemplate().get_hprinterval();
		final int hpr = getNpcTemplate().get_hpr();
		if (!_hprRunning && (hprInterval > 0) && (hpr > 0)) {
			_hprTimer = new HprTimer(hpr);
			L1NpcRegenerationTimer.getInstance().scheduleAtFixedRate(_hprTimer, hprInterval, hprInterval);
			_hprRunning = true;
		}
	}

	/** MP自然回复 */
	public final void startMpRegeneration() {
		final int mprInterval = getNpcTemplate().get_mprinterval();
		final int mpr = getNpcTemplate().get_mpr();
		if (!_mprRunning && (mprInterval > 0) && (mpr > 0)) {
			_mprTimer = new MprTimer(mpr);
			L1NpcRegenerationTimer.getInstance().scheduleAtFixedRate(_mprTimer, mprInterval, mprInterval);
			_mprRunning = true;
		}
	}

	/** 停止回复HP */
	public final void stopHpRegeneration() {
		if (_hprRunning) {
			_hprTimer.cancel();
			_hprRunning = false;
		}
	}

	/** 停止回复MP */
	public final void stopMpRegeneration() {
		if (_mprRunning) {
			_mprTimer.cancel();
			_mprRunning = false;
		}
	}

	/** 删除现在的目标 */
	public void tagertClear() {
		if (_target == null) {
			return;
		}
		_hateList.remove(_target);
		_target = null;
	}

	/** 删除指定的目标 */
	public void targetRemove(final L1Character target) {
		_hateList.remove(target);
		if ((_target != null) && _target.equals(target)) {
			_target = null;
		}
	}

	/** 返回目标相反的方向 */
	public int targetReverseDirection(final int tx, final int ty) { // 目标点Ｘ 目标点Ｙ
		int dir = targetDirection(tx, ty);
		dir += 4;
		if (dir > 7) {
			dir -= 8;
		}
		return dir;
	}

	/** 瞬移到目标 */
	public void teleport(final int nx, final int ny, final int dir) {
		for (final L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.sendPackets(new S_SkillSound(getId(), 169));
			pc.sendPackets(new S_RemoveObject(this));
			pc.removeKnownObject(this);
		}
		setX(nx);
		setY(ny);
		setHeading(dir);
	}

	/** 使用道具 */
	public void useItem(final int type, final int chance) { // 使用する种类 使用する可能性(％)
		if (hasSkillEffect(71)) {
			return; // ディケイ ポーション状态かチェック
		}

		if (Random.nextInt(100) > chance) {
			return; // 使用する可能性
		}

		if (type == USEITEM_HEAL) { // 恢复药水系统
			// 回复量の大きい顺
			if (getInventory().consumeItem(POTION_OF_GREATER_HEALING, 1)) {
				useHealPotion(75, 197);
			}
			else if (getInventory().consumeItem(POTION_OF_EXTRA_HEALING, 1)) {
				useHealPotion(45, 194);
			}
			else if (getInventory().consumeItem(POTION_OF_HEALING, 1)) {
				useHealPotion(15, 189);
			}
		}
		else if (type == USEITEM_HASTE) { // 加速药水系统
			if (hasSkillEffect(1001)) { // 一段加速
				return; // 检查加速状态
			}

			// 效果延长
			if (getInventory().consumeItem(B_POTION_OF_GREATER_HASTE_SELF, 1)) {
				useHastePotion(2100);
			}
			else if (getInventory().consumeItem(POTION_OF_GREATER_HASTE_SELF, 1)) {
				useHastePotion(1800);
			}
			else if (getInventory().consumeItem(B_POTION_OF_HASTE_SELF, 1)) {
				useHastePotion(350);
			}
			else if (getInventory().consumeItem(POTION_OF_HASTE_SELF, 1)) {
				useHastePotion(300);
			}
		}
	}

	/**  */
	private void _getFront(final int[] ary, final int d) {
		if (d == 1) {
			ary[4] = 2;
			ary[3] = 0;
			ary[2] = 1;
			ary[1] = 3;
			ary[0] = 7;
		}
		else if (d == 2) {
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 0;
			ary[1] = 1;
			ary[0] = 3;
		}
		else if (d == 3) {
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 1;
			ary[1] = 3;
			ary[0] = 5;
		}
		else if (d == 4) {
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 6;
			ary[1] = 3;
			ary[0] = 5;
		}
		else if (d == 5) {
			ary[4] = 4;
			ary[3] = 6;
			ary[2] = 3;
			ary[1] = 5;
			ary[0] = 7;
		}
		else if (d == 6) {
			ary[4] = 4;
			ary[3] = 6;
			ary[2] = 0;
			ary[1] = 5;
			ary[0] = 7;
		}
		else if (d == 7) {
			ary[4] = 6;
			ary[3] = 0;
			ary[2] = 1;
			ary[1] = 5;
			ary[0] = 7;
		}
		else if (d == 0) {
			ary[4] = 2;
			ary[3] = 6;
			ary[2] = 0;
			ary[1] = 1;
			ary[0] = 7;
		}
	}

	/** 移动位置 */
	private void _moveLocation(final int[] ary, final int d) {
		if (d == 1) {
			ary[0] = ary[0] + 1;
			ary[1] = ary[1] - 1;
		}
		else if (d == 2) {
			ary[0] = ary[0] + 1;
		}
		else if (d == 3) {
			ary[0] = ary[0] + 1;
			ary[1] = ary[1] + 1;
		}
		else if (d == 4) {
			ary[1] = ary[1] + 1;
		}
		else if (d == 5) {
			ary[0] = ary[0] - 1;
			ary[1] = ary[1] + 1;
		}
		else if (d == 6) {
			ary[0] = ary[0] - 1;
		}
		else if (d == 7) {
			ary[0] = ary[0] - 1;
			ary[1] = ary[1] - 1;
		}
		else if (d == 0) {
			ary[1] = ary[1] - 1;
		}
		ary[2] = d;
	}

	// 返回目标最短路径的方向
	// ※目标を中心とした探索范围のマップで探索
	private int _serchCource(final int x, final int y) // 目标点Ｘ 目标点Ｙ
	{
		int i;
		final int locCenter = courceRange + 1;
		final int diff_x = x - locCenter; // Ｘの实际のロケーションとの差
		final int diff_y = y - locCenter; // Ｙの实际のロケーションとの差
		int[] locBace = { getX() - diff_x, getY() - diff_y, 0, 0 }; // Ｘ Ｙ
		// 方向
		// 初期方向
		final int[] locNext = new int[4];
		int[] locCopy;
		final int[] dirFront = new int[5];
		final boolean serchMap[][] = new boolean[locCenter * 2 + 1][locCenter * 2 + 1];
		final LinkedList<int[]> queueSerch = new LinkedList<int[]>();

		// 设置探索地图
		for (int j = courceRange * 2 + 1; j > 0; j--) {
			for (i = courceRange - Math.abs(locCenter - j); i >= 0; i--) {
				serchMap[j][locCenter + i] = true;
				serchMap[j][locCenter - i] = true;
			}
		}

		// 初期方向の设置
		final int[] firstCource = { 2, 4, 6, 0, 1, 3, 5, 7 };
		for (i = 0; i < 8; i++) {
			System.arraycopy(locBace, 0, locNext, 0, 4);
			_moveLocation(locNext, firstCource[i]);
			if ((locNext[0] - locCenter == 0) && (locNext[1] - locCenter == 0)) {
				// 最短经路が见つかった场合:邻
				return firstCource[i];
			}
			if (serchMap[locNext[0]][locNext[1]]) {
				final int tmpX = locNext[0] + diff_x;
				final int tmpY = locNext[1] + diff_y;
				boolean found = false;
				if (i == 0) {
					found = getMap().isPassable(tmpX, tmpY + 1, i);
				}
				else if (i == 1) {
					found = getMap().isPassable(tmpX - 1, tmpY + 1, i);
				}
				else if (i == 2) {
					found = getMap().isPassable(tmpX - 1, tmpY, i);
				}
				else if (i == 3) {
					found = getMap().isPassable(tmpX - 1, tmpY - 1, i);
				}
				else if (i == 4) {
					found = getMap().isPassable(tmpX, tmpY - 1, i);
				}
				else if (i == 5) {
					found = getMap().isPassable(tmpX + 1, tmpY - 1, i);
				}
				else if (i == 6) {
					found = getMap().isPassable(tmpX + 1, tmpY, i);
				}
				else if (i == 7) {
					found = getMap().isPassable(tmpX + 1, tmpY + 1, i);
				}
				if (found)// 移动经路があった场合
				{
					locCopy = new int[4];
					System.arraycopy(locNext, 0, locCopy, 0, 4);
					locCopy[2] = firstCource[i];
					locCopy[3] = firstCource[i];
					queueSerch.add(locCopy);
				}
				serchMap[locNext[0]][locNext[1]] = false;
			}
		}
		locBace = null;

		// 最短经路を探索
		while (queueSerch.size() > 0) {
			locBace = queueSerch.removeFirst();
			_getFront(dirFront, locBace[2]);
			for (i = 4; i >= 0; i--) {
				System.arraycopy(locBace, 0, locNext, 0, 4);
				_moveLocation(locNext, dirFront[i]);
				if ((locNext[0] - locCenter == 0) && (locNext[1] - locCenter == 0)) {
					return locNext[3];
				}
				if (serchMap[locNext[0]][locNext[1]]) {
					final int tmpX = locNext[0] + diff_x;
					final int tmpY = locNext[1] + diff_y;
					boolean found = false;
					if (i == 0) {
						found = getMap().isPassable(tmpX, tmpY + 1, i);
					}
					else if (i == 1) {
						found = getMap().isPassable(tmpX - 1, tmpY + 1, i);
					}
					else if (i == 2) {
						found = getMap().isPassable(tmpX - 1, tmpY, i);
					}
					else if (i == 3) {
						found = getMap().isPassable(tmpX - 1, tmpY - 1, i);
					}
					else if (i == 4) {
						found = getMap().isPassable(tmpX, tmpY - 1, i);
					}
					if (found) // 移动经路があった场合
					{
						locCopy = new int[4];
						System.arraycopy(locNext, 0, locCopy, 0, 4);
						locCopy[2] = dirFront[i];
						queueSerch.add(locCopy);
					}
					serchMap[locNext[0]][locNext[1]] = false;
				}
			}
			locBace = null;
		}
		return -1; // 目标までの经路がない场合
	}

	/** ＡＩの处理 (返回ＡＩ处理是否结束) */
	private boolean AIProcess() {
		setSleepTime(300);

		checkTarget();
		if ((_target == null) && (_master == null)) {
			// 空目标的场合 尝试找到目标
			// （有主人的场合寻找一个目标）
			searchTarget();
		}

		onDoppel(true);
		onItemUse();

		if (_target == null) {
			// 没有目标的场合
			checkTargetItem();
			if (isPickupItem() && (_targetItem == null)) {
				// アイテム拾う子の场合はアイテムを探してみる
				searchTargetItem();
			}

			if (_targetItem == null) {
				if (noTarget()) {
					return true;
				}
			}
			else {
				// onTargetItem();
				final L1Inventory groundInventory = L1World.getInstance().getInventory(_targetItem.getX(), _targetItem.getY(), _targetItem.getMapId());
				if (groundInventory.checkItem(_targetItem.getItemId())) {
					onTargetItem();
				}
				else {
					_targetItemList.remove(_targetItem);
					_targetItem = null;
					setSleepTime(1000);
					return false;
				}
			}
		}
		else { // 有目标的场合
			if (getHiddenStatus() == HIDDEN_STATUS_NONE) {
				onTarget();
			}
			else {
				return true;
			}
		}

		return false; // ＡＩ处理续行
	}

	/** 是存在角色之间的目标 */
	private boolean isExsistCharacterBetweenTarget(final int dir) {
		if (!(this instanceof L1MonsterInstance)) { // 怪物以外的对象
			return false;
		}
		if (_target == null) { // 如果没有目标
			return false;
		}

		final int locX = getX();
		final int locY = getY();
		int targetX = locX;
		int targetY = locY;

		if (dir == 1) {
			targetX = locX + 1;
			targetY = locY - 1;
		}
		else if (dir == 2) {
			targetX = locX + 1;
		}
		else if (dir == 3) {
			targetX = locX + 1;
			targetY = locY + 1;
		}
		else if (dir == 4) {
			targetY = locY + 1;
		}
		else if (dir == 5) {
			targetX = locX - 1;
			targetY = locY + 1;
		}
		else if (dir == 6) {
			targetX = locX - 1;
		}
		else if (dir == 7) {
			targetX = locX - 1;
			targetY = locY - 1;
		}
		else if (dir == 0) {
			targetY = locY - 1;
		}

		for (final L1Object object : L1World.getInstance().getVisibleObjects(this, 1)) {
			// PC, Summon, Petがいる场合
			if ((object instanceof L1PcInstance) || (object instanceof L1SummonInstance) || (object instanceof L1PetInstance)) {
				final L1Character cha = (L1Character) object;
				// 进行方向に立ちふさがっている场合、ターゲットリストに加える
				if ((cha.getX() == targetX) && (cha.getY() == targetY) && (cha.getMapId() == getMapId())) {
					if (object instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) object;
						if (pc.isGhost()) { // 排除UB观战中的PC
							continue;
						}
					}
					_hateList.add(cha, 0);
					_target = cha;
					return true;
				}
			}
		}
		return false;
	}

	/** 使用加速药水 */
	private void useHastePotion(final int time) {
		broadcastPacket(new S_SkillHaste(getId(), 1, time));
		broadcastPacket(new S_SkillSound(getId(), 191));
		setMoveSpeed(1);
		setSkillEffect(STATUS_HASTE, time * 1000);
	}

	/** 使用治愈药水 */
	private void useHealPotion(int healHp, final int effectId) {
		broadcastPacket(new S_SkillSound(getId(), effectId));
		if (hasSkillEffect(POLLUTE_WATER)) { // 污浊之水 恢复量减半
			healHp /= 2;
		}
		if (this instanceof L1PetInstance) {
			((L1PetInstance) this).setCurrentHp(getCurrentHp() + healHp);
		}
		else if (this instanceof L1SummonInstance) {
			((L1SummonInstance) this).setCurrentHp(getCurrentHp() + healHp);
		}
		else {
			setCurrentHpDirect(getCurrentHp() + healHp);
		}
	}

	protected int calcSleepTime(int sleepTime, final int type) {
		switch (getMoveSpeed()) {
			case 0: // 通常
				break;
			case 1: // 加速
				sleepTime -= (sleepTime * 0.25);
				break;
			case 2: // 减速
				sleepTime *= 2;
				break;
		}
		if (getBraveSpeed() == 1) {
			sleepTime -= (sleepTime * 0.25);
		}
		if (hasSkillEffect(WIND_SHACKLE)) {
			if ((type == ATTACK_SPEED) || (type == MAGIC_SPEED)) {
				sleepTime += (sleepTime * 0.25);
			}
		}
		return sleepTime;
	}

	protected int getSleepTime() {
		return _sleep_time;
	}

	protected boolean isActived() {
		return _actived;
	}

	/** 设置AI运行中 */
	protected boolean isAiRunning() {
		return _aiRunning;
	}

	protected boolean isDeathProcessing() {
		return _deathProcessing;
	}

	protected boolean isFirstAttack() {
		return _firstAttack;
	}

	protected void setActived(final boolean actived) {
		_actived = actived;
	}

	/** 设置AI运行 */
	protected void setAiRunning(final boolean aiRunning) {
		_aiRunning = aiRunning;
	}

	protected void setDeathProcessing(final boolean deathProcessing) {
		_deathProcessing = deathProcessing;
	}

	protected void setFirstAttack(final boolean firstAttack) {
		_firstAttack = firstAttack;
	}

	protected void setSleepTime(final int sleep_time) {
		_sleep_time = sleep_time;
	}

	protected void startAI() {
		if (Config.NPCAI_IMPLTYPE == 1) {
			new NpcAITimerImpl().start();
		}
		else if (Config.NPCAI_IMPLTYPE == 2) {
			new NpcAIThreadImpl().start();
		}
		else {
			new NpcAITimerImpl().start();
		}
	}

	/** 开始定时器 */
	protected synchronized void startDeleteTimer() {
		if (_deleteTask != null) {
			return;
		}
		_deleteTask = new DeleteTimer(getId());
		_future = GeneralThreadPool.getInstance().schedule(_deleteTask, Config.NPC_DELETION_TIME * 1000);
	}

	// NPCが别のNPCに变わる场合の处理
	protected void transform(final int transformId) {
		stopHpRegeneration();
		stopMpRegeneration();
		final int transformGfxId = getNpcTemplate().getTransformGfxId();
		if (transformGfxId != 0) {
			broadcastPacket(new S_SkillSound(getId(), transformGfxId));
		}
		final L1Npc npcTemplate = NpcTable.getInstance().getTemplate(transformId);
		setting_template(npcTemplate);

		broadcastPacket(new S_NpcChangeShape(getId(), getTempCharGfx(), getLawful(), getStatus()));
		for (final L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			onPerceive(pc);
		}

	}

}
