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

import static l1j.server.server.model.skill.L1SkillId.BLIND_HIDING;
import static l1j.server.server.model.skill.L1SkillId.GMSTATUS_FINDINVIS;
import static l1j.server.server.model.skill.L1SkillId.INVISIBILITY;
import static l1j.server.server.model.skill.L1SkillId.LIGHT;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_BARLOG;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_YAHEE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import static l1j.server.server.model.skill.L1SkillId.SECRET_MEDICINE_OF_DESTRUCTION;

import java.util.List;
import java.util.Map;

import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FollowerInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.poison.L1Poison;
import l1j.server.server.model.skill.L1SkillTimer;
import l1j.server.server.model.skill.L1SkillTimerCreator;
import l1j.server.server.serverpackets.S_Light;
import l1j.server.server.serverpackets.S_PetCtrlMenu;
import l1j.server.server.serverpackets.S_Poison;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.ServerBasePacket;
import l1j.server.server.templates.L1MagicDoll;
import l1j.server.server.types.Point;
import l1j.server.server.utils.IntRange;
import l1j.server.server.utils.collections.Lists;
import l1j.server.server.utils.collections.Maps;

// Referenced classes of package l1j.server.server.model:
// L1Object, Die, L1PcInstance, L1MonsterInstance,
// L1World, ActionFailed

/**
 *	角色相关
 */
public class L1Character extends L1Object {

	private static final long serialVersionUID = 1L;
	/** 毒 */
	private L1Poison _poison = null;
	/** 麻痹 */
	private boolean _paralyzed;
	/** 睡眠 */
	private boolean _sleeped;
	/** 宠物清单 */
	private final Map<Integer, L1NpcInstance> _petlist = Maps.newMap();
	/** 娃娃清单 */
	private final Map<Integer, L1DollInstance> _dolllist = Maps.newMap();
	/** 技能效果 */
	private final Map<Integer, L1SkillTimer> _skillEffect = Maps.newMap();
	/** 道具延迟 */
	private final Map<Integer, L1ItemDelay.ItemDelayTimer> _itemdelay = Maps.newMap();
	/** 跟随清单 */
	private final Map<Integer, L1FollowerInstance> _followerlist = Maps.newMap();

	public L1Character() {
		_level = 1;
	}

	/**
	 * 复活角色。
	 * 
	 * @param hp
	 *            复活后的HP
	 */
	public void resurrect(int hp) {
		if (!isDead()) {
			return;
		}
		if (hp <= 0) {
			hp = 1;
		}
		setCurrentHp(hp);
		setDead(false);
		setStatus(0);
		L1PolyMorph.undoPoly(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.sendPackets(new S_RemoveObject(this));
			pc.removeKnownObject(this);
			pc.updateObject();
		}
	}

	/** 当前HP */
	private int _currentHp;
	/**
	 * 取得角色的当前HP。
	 * 
	 * @return 现在的HP
	 */
	public int getCurrentHp() {
		return _currentHp;
	}
	/**
	 * 设定角色的当前HP。
	 * 
	 * @param i
	 *            人物新的HP
	 */
	// 特殊处理的场合覆盖（分组传输等）
	public void setCurrentHp(int i) {
		_currentHp = i;
		if (_currentHp >= getMaxHp()) {
			_currentHp = getMaxHp();
		}
	}
	/**
	 * 设定检查角色的当前HP。
	 * 
	 * @param i
	 *            人物新的HP
	 */
	public void setCurrentHpDirect(int i) {
		_currentHp = i;
	}

	/** 真正MP */
	private int _currentMp;
	/**
	 * 取得人物的当前MP。
	 * 
	 * @return 现在的MP
	 */
	public int getCurrentMp() {
		return _currentMp;
	}
	/**
	 * 设定人物的当前MP。
	 * 
	 * @param i
	 *            人物新的MP
	 */
	// 特殊处理的场合覆盖（分组传输等）
	public void setCurrentMp(int i) {
		_currentMp = i;
		if (_currentMp >= getMaxMp()) {
			_currentMp = getMaxMp();
		}
	}
	/**
	 * 设定检查人物的当前MP。
	 * 
	 * @param i
	 *            人物新的MP
	 */
	public void setCurrentMpDirect(int i) {
		_currentMp = i;
	}

	/**
	 * 取得人物的睡眠状态。
	 * 
	 * @return 表示睡眠值状态。如果睡眠状态true。
	 */
	public boolean isSleeped() {
		return _sleeped;
	}

	/**
	 * 设定人物的睡眠状态。
	 * 
	 * @param sleeped
	 *            表示睡眠值状态。如果睡眠状态true。
	 */
	public void setSleeped(boolean sleeped) {
		_sleeped = sleeped;
	}

	/**
	 * 取得人物的麻痹状态。
	 * 
	 * @return 表示麻痹值状态。如果麻痹状态true。
	 */
	public boolean isParalyzed() {
		return _paralyzed;
	}

	/**
	 * 设定人物的麻痹状态。
	 * 
	 * @param i
	 *            表示麻痹值状态。如果麻痹状态true。
	 */
	public void setParalyzed(boolean paralyzed) {
		_paralyzed = paralyzed;
	}

	/** 麻痹 */
	L1Paralysis _paralysis;

	/** 取得麻痹 */
	public L1Paralysis getParalysis() {
		return _paralysis;
	}
	/** 设定麻痹 */
	public void setParalaysis(L1Paralysis p) {
		_paralysis = p;
	}
	/** 治愈麻痹 */
	public void cureParalaysis() {
		if (_paralysis != null) {
			_paralysis.cure();
		}
	}

	/**
	 * 恰好是可见的人物的范围，并发送数据包。
	 * 
	 * @param packet
	 *            ServerBasePacket对象，表示要发送的数据包。
	 */
	public void broadcastPacket(ServerBasePacket packet) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(this)) {
			// 旅馆内判断
			if (pc.getMapId() < 16384 || pc.getMapId() > 25088 || pc.getInnKeyId() == getInnKeyId()) 
				pc.sendPackets(packet);			
		}
	}

	/**
	 * 恰好是可见的人物的范围，并发送数据包。 但中心的画面目标不能发送。
	 * 
	 * @param packet
	 *            ServerBasePacket对象，表示要发送的数据包。
	 */
	public void broadcastPacketExceptTargetSight(ServerBasePacket packet, L1Character target) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayerExceptTargetSight(this, target)) {
			pc.sendPackets(packet);
		}
	}

	/**
	 * キャラクターの可视范围でインビジを见破れるor见破れないプレイヤーを区别して、要发送的数据包。
	 * 
	 * @param packet
	 *            ServerBasePacket对象，表示要发送的数据包。
	 * @param isFindInvis
	 *            true : 见破れるプレイヤーにだけパケットを送信する。 false : 见破れないプレイヤーにだけパケットを送信する。
	 */
	public void broadcastPacketForFindInvis(ServerBasePacket packet,
			boolean isFindInvis) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(this)) {
			if (isFindInvis) {
				if (pc.hasSkillEffect(GMSTATUS_FINDINVIS)) {
					pc.sendPackets(packet);
				}
			} else {
				if (!pc.hasSkillEffect(GMSTATUS_FINDINVIS)) {
					pc.sendPackets(packet);
				}
			}
		}
	}

	/**
	 * キャラクターの50マス以内に居るプレイヤーへ、要发送的数据包。
	 * 
	 * @param packet
	 *            ServerBasePacket对象，表示要发送的数据包。
	 */
	public void wideBroadcastPacket(ServerBasePacket packet) {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(this, 50)) {
			pc.sendPackets(packet);
		}
	}

	/**
	 * 取得角色正面的坐标。
	 * 
	 * @return 正面の坐标
	 */
	public int[] getFrontLoc() {
		int[] loc = new int[2];
		int x = getX();
		int y = getY();
		int heading = getHeading();
		if (heading == 0) {
			y--;
		} else if (heading == 1) {
			x++;
			y--;
		} else if (heading == 2) {
			x++;
		} else if (heading == 3) {
			x++;
			y++;
		} else if (heading == 4) {
			y++;
		} else if (heading == 5) {
			x--;
			y++;
		} else if (heading == 6) {
			x--;
		} else if (heading == 7) {
			x--;
			y--;
		}
		loc[0] = x;
		loc[1] = y;
		return loc;
	}

	/**
	 * 返回指定的坐标的方向。
	 * 
	 * @param tx
	 *            坐标的X值
	 * @param ty
	 *            坐标的Y值
	 * @return 指定的方向坐标
	 */
	public int targetDirection(int tx, int ty) {
		float dis_x = Math.abs(getX() - tx); // 距离目标在X方向
		float dis_y = Math.abs(getY() - ty); // 距离目标在Y方向
		float dis = Math.max(dis_x, dis_y); // 距离目标
		if (dis == 0) {
			return getHeading(); // 回到同一个位置的面向
		}
		int avg_x = (int) Math.floor((dis_x / dis) + 0.59f); // 上下左右がちょっと优先な丸め
		int avg_y = (int) Math.floor((dis_y / dis) + 0.59f); // 上下左右がちょっと优先な丸め

		int dir_x = 0;
		int dir_y = 0;
		if (getX() < tx) {
			dir_x = 1;
		}
		if (getX() > tx) {
			dir_x = -1;
		}
		if (getY() < ty) {
			dir_y = 1;
		}
		if (getY() > ty) {
			dir_y = -1;
		}

		if (avg_x == 0) {
			dir_x = 0;
		}
		if (avg_y == 0) {
			dir_y = 0;
		}

		if ((dir_x == 1) && (dir_y == -1)) {
			return 1; // 上
		}
		if ((dir_x == 1) && (dir_y == 0)) {
			return 2; // 右上
		}
		if ((dir_x == 1) && (dir_y == 1)) {
			return 3; // 右
		}
		if ((dir_x == 0) && (dir_y == 1)) {
			return 4; // 右下
		}
		if ((dir_x == -1) && (dir_y == 1)) {
			return 5; // 下
		}
		if ((dir_x == -1) && (dir_y == 0)) {
			return 6; // 左下
		}
		if ((dir_x == -1) && (dir_y == -1)) {
			return 7; // 左
		}
		if ((dir_x == 0) && (dir_y == -1)) {
			return 0; // 左上
		}
		return getHeading(); // ここにはこない。はず
	}

	/**
	 * 返回指定的坐标在一条直线上、存不存在障碍物。
	 * 
	 * @param tx
	 *            坐标のX值
	 * @param ty
	 *            坐标のY值
	 * @return 如果没有障碍物true、有障碍物false。
	 */
	public boolean glanceCheck(int tx, int ty) {
		int chx = getX();
		int chy = getY();
		for (int i = 0; i < 15; i++) {
			if (chx == tx && chy == ty) {
				break;
			}

			if (!getMap().isArrowPassable(chx, chy, targetDirection(tx, ty))) {
				return false;
			}

			// Targetへ1タイル进める
			chx += Math.max(-1, Math.min(1, tx - chx));
			chy += Math.max(-1, Math.min(1, ty - chy));
		}
		return true;
	}

	/**
	 * 返回指定坐标是否可以攻击
	 * 
	 * @param x
	 *            坐标的X值。
	 * @param y
	 *            坐标的Y值。
	 * @param range
	 *            可以攻击的范围(タイル数)
	 * @return 可以攻击true,不能攻击false
	 */
	public boolean isAttackPosition(int x, int y, int range) {
		if (range >= 7) // 远程武器（走出画面考虑至少7对角线的情况下）
		{
			if (getLocation().getTileDistance(new Point(x, y)) > range) {
				return false;
			}
		} else // 近身武器
		{
			if (getLocation().getTileLineDistance(new Point(x, y)) > range) {
				return false;
			}
		}
		return glanceCheck(x, y);
	}

	/**
	 * 取得角色身上的道具。
	 * 
	 * @return 表示角色身上的道具，L1Inventory对象。
	 */
	public L1Inventory getInventory() {
		return null;
	}

	/**
	 * 为角色，增加新的技能效果。
	 * 
	 * @param skillId
	 *            要增加的技能效果ID。
	 * @param timeMillis
	 *            设定技能效果的持续时间。无限制是0。
	 */
	private void addSkillEffect(int skillId, int timeMillis) {
		L1SkillTimer timer = null;
		if (0 < timeMillis) {
			timer = L1SkillTimerCreator.create(this, skillId, timeMillis);
			timer.begin();
		}
		_skillEffect.put(skillId, timer);
	}

	/**
	 * 为人物，设定技能的效果。<br>
	 * 如果没有重复的技能、追加新技能的效果。<br>
	 * 如果有重复、优先取剩余效果时间较长的。
	 * 
	 * @param skillId
	 *            设定技能效果的ID。
	 * @param timeMillis
	 *            设定技能效果的持续时间。无限制是0。
	 */
	public void setSkillEffect(int skillId, int timeMillis) {
		if (hasSkillEffect(skillId)) {
			int remainingTimeMills = getSkillEffectTimeSec(skillId) * 1000;

			// 残り时间が有限で、パラメータの效果时间の方が长いか无限の场合は上书きする。
			if ((remainingTimeMills >= 0)
					&& ((remainingTimeMills < timeMillis) || (timeMillis == 0))) {
				killSkillEffectTimer(skillId);
				addSkillEffect(skillId, timeMillis);
			}
		} else {
			addSkillEffect(skillId, timeMillis);
		}
	}

	/**
	 * 从人物、删除技能效果。
	 * 
	 * @param skillId
	 *            删除技能效果的ID
	 */
	public void removeSkillEffect(int skillId) {
		L1SkillTimer timer = _skillEffect.remove(skillId);
		if (timer != null) {
			timer.end();
		}
	}

	/**
	 * 从人物、删除技能效果的计时器。 技能效果是不会被删除。
	 * 
	 * @param skillId
	 *            删除计时器的技能ＩＤ
	 */
	public void killSkillEffectTimer(int skillId) {
		L1SkillTimer timer = _skillEffect.remove(skillId);
		if (timer != null) {
			timer.kill();
		}
	}

	/**
	 * 从人物、删除所有技能效果计时器。技能效果是不会被删除。
	 */
	public void clearSkillEffectTimer() {
		for (L1SkillTimer timer : _skillEffect.values()) {
			if (timer != null) {
				timer.kill();
			}
		}
		_skillEffect.clear();
	}

	/**
	 * 从人物、取得是否有技能效果。
	 * 
	 * @param skillId
	 *            检查技能效果的ID。
	 * @return 如果有技能效果true、没有false。
	 */
	public boolean hasSkillEffect(int skillId) {
		return _skillEffect.containsKey(skillId);
	}

	/**
	 * 从人物、取得技能效果的持续有效时间。
	 * 
	 * @param skillId
	 *            检查技能效果的ID
	 * @return 技能效果剩余时间(秒)。如果时间是无限的技能它生效与否、-1。
	 */
	public int getSkillEffectTimeSec(int skillId) {
		L1SkillTimer timer = _skillEffect.get(skillId);
		if (timer == null) {
			return -1;
		}
		return timer.getRemainingTime();
	}

	private boolean _isSkillDelay = false;

	/**
	 * 从人物、设定技能延迟。
	 * 
	 * @param flag
	 */
	public void setSkillDelay(boolean flag) {
		_isSkillDelay = flag;
	}

	/**
	 * 技能延迟中。
	 * 
	 * @return 技能延迟。
	 */
	public boolean isSkillDelay() {
		return _isSkillDelay;
	}

	/**
	 * 从人物、追加道具延迟时间。
	 * 
	 * @param delayId
	 *            延迟物品ID。 如果是正常的道具0、隐形斗篷、血腥炎魔披风1。
	 * @param timer
	 *            表示延迟时间、L1ItemDelay.ItemDelayTimer对象。
	 */
	public void addItemDelay(int delayId, L1ItemDelay.ItemDelayTimer timer) {
		_itemdelay.put(delayId, timer);
	}

	/**
	 * 从人物、删除道具延迟时间。
	 * 
	 * @param delayId
	 *            延迟物品ID。 如果是正常的道具0、隐形斗篷、血腥炎魔披风1。
	 */
	public void removeItemDelay(int delayId) {
		_itemdelay.remove(delayId);
	}

	/**
	 * 从人物、取得该项目是否有延迟。
	 * 
	 * @param delayId
	 *            检查延迟项目ID。 如果是正常的道具0、隐形斗篷、血腥炎魔披风1。
	 * @return 如果项目有延迟true、没有false。
	 */
	public boolean hasItemDelay(int delayId) {
		return _itemdelay.containsKey(delayId);
	}

	/**
	 * 代表一个角色项目的时间延迟、L1ItemDelay.ItemDelayTimer取得。
	 * 
	 * @param delayId
	 *            检查延迟项目ID。 如果是正常的道具0、隐形斗篷、血腥炎魔披风1。
	 * @return 代表了项目的时间延迟、L1ItemDelay.ItemDelayTimer。
	 */
	public L1ItemDelay.ItemDelayTimer getItemDelayTimer(int delayId) {
		return _itemdelay.get(delayId);
	}

	/**
	 * 对角色添加、新宠物、召唤怪物、怪物Teimingu、造尸。
	 * 
	 * @param npc
	 *            添加到Npc表、L1NpcInstance对象。
	 */
	public void addPet(L1NpcInstance npc) {
		_petlist.put(npc.getId(), npc);
		//if (_petlist.size() < 2) {
			sendPetCtrlMenu(npc, true); // 显示宠物控制图形介面
		//}

	}

	/**
	 * 对角色删除、新宠物、召唤怪物、怪物Teimingu、造尸。
	 * 
	 * @param npc
	 *            添加到Npc表、L1NpcInstance对象。
	 */
	public void removePet(L1NpcInstance npc) {
		_petlist.remove(npc.getId());
		//if (_petlist.isEmpty()) {
			sendPetCtrlMenu(npc, false); // 关闭宠物控制图形介面
		//}
	}

	/**
	 * 3.3C PetMenu 控制
	 * 
	 * @param npc
	 * @param type
	 *            1:显示 0:关闭
	 */
	public void sendPetCtrlMenu(L1NpcInstance npc, boolean type) {
		if (npc instanceof L1PetInstance) {
			L1PetInstance pet = (L1PetInstance) npc;
			L1Character cha = pet.getMaster();
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_PetCtrlMenu(cha, npc, type));
				// 处理宠物控制图形介面
			}
		} else if (npc instanceof L1SummonInstance) {
			L1SummonInstance summon = (L1SummonInstance) npc;
			L1Character cha = summon.getMaster();
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_PetCtrlMenu(cha, npc, type));
			}
		}
	}

	/**
	 * 取得宠物清单。
	 * 
	 * @return 
	 *         代表宠物清单，HashMap对象的角色。这个对象Key的对象ID、ValueはL1NpcInstance
	 */
	public Map<Integer, L1NpcInstance> getPetList() {
		return _petlist;
	}

	/**
	 * 增加魔法娃娃。
	 * 
	 * @param doll
	 *            添加到doll表、L1DollInstance对象。
	 */
	public void addDoll(L1DollInstance doll) {
		_dolllist.put(doll.getId(), doll);
	}

	/**
	 * 删除魔法娃娃。
	 * 
	 * @param doll
	 *            删除doll表、L1DollInstance对象。
	 */
	public void removeDoll(L1DollInstance doll) {
		_dolllist.remove(doll.getId());
	}

	/**
	 * 取得魔法娃娃清单。
	 * 
	 * @return 魔法娃娃代表一个角色列表、HashMap对象。这个对象Key的对象ID、ValueはL1NpcInstance
	 */
	public Map<Integer, L1DollInstance> getDollList() {
		return _dolllist;
	}

	/**
	 * 添加跟随。
	 * 
	 * @param follower
	 *            添加到follower表、L1FollowerInstance对象。
	 */
	public void addFollower(L1FollowerInstance follower) {
		_followerlist.put(follower.getId(), follower);
	}

	/**
	 * 删除跟随。
	 * 
	 * @param follower
	 *            删除follower表、L1FollowerInstance对象。
	 */
	public void removeFollower(L1FollowerInstance follower) {
		_followerlist.remove(follower.getId());
	}

	/**
	 * 取得跟随名单。
	 * 
	 * @return 代表一个跟随名单、HashMap对象。这个对象Key的对象ID、ValueはL1NpcInstance
	 */
	public Map<Integer, L1FollowerInstance> getFollowerList() {
		return _followerlist;
	}

	/**
	 * 设定 追加毒。
	 * 
	 * @param poison
	 *            毒列表、L1Poison对象。
	 */
	public void setPoison(L1Poison poison) {
		_poison = poison;
	}

	/**
	 * 解毒。
	 */
	public void curePoison() {
		if (_poison == null) {
			return;
		}
		_poison.cure();
	}

	/**
	 * 取得角色的中毒状态。
	 * 
	 * @return 表示角色中毒、L1Poison对象。
	 */
	public L1Poison getPoison() {
		return _poison;
	}

	/**
	 * 设定角色的中毒效果
	 * 
	 * @param effectId
	 * @see S_Poison#S_Poison(int, int)
	 */
	public void setPoisonEffect(int effectId) {
		broadcastPacket(new S_Poison(getId(), effectId));
	}

	/**
	 * 取得角色所在区域。
	 * 
	 * @return 表示坐标值区。1:安全区，-1:战斗区，0:一般区。
	 */
	public int getZoneType() {
		if (getMap().isSafetyZone(getLocation())) {
			return 1;
		} else if (getMap().isCombatZone(getLocation())) {
			return -1;
		} else { // 正常区
			return 0;
		}
	}

	/** 经验值 */
	private int _exp;
	/**
	 * 取得角色的，保存经验值。
	 * 
	 * @return 经验值。
	 */
	public int getExp() {
		return _exp;
	}
	/**
	 * 设定保存的经验值。
	 * 
	 * @param exp
	 *            经验值。
	 */
	public void setExp(int exp) {
		_exp = exp;
	}

	// ■■■■■■■■■■ L1PcInstanceへ移动するプロパティ ■■■■■■■■■■
	private final List<L1Object> _knownObjects = Lists.newConcurrentList();

	private final List<L1PcInstance> _knownPlayer = Lists.newConcurrentList();

	/**
	 * 取得指定的对象，并让角色知道。
	 * 
	 * @param obj
	 *            检查对象。
	 * @return 如果是知道的角色对象true、不知道false。 对自己false。
	 */
	public boolean knownsObject(L1Object obj) {
		return _knownObjects.contains(obj);
	}

	/**
	 * 所有已知角色取得一个对象。
	 * 
	 * @return L1Object的ArrayList，它包含对象来表示角色识别。
	 */
	public List<L1Object> getKnownObjects() {
		return _knownObjects;
	}

	/**
	 * 所有角色取得到已知的角色。
	 * 
	 * @return L1PcInstance的ArrayList，它包含对象来表示角色识别。
	 */
	public List<L1PcInstance> getKnownPlayers() {
		return _knownPlayer;
	}

	/**
	 * 角色，增加了新的目标识别。
	 * 
	 * @param obj
	 *            认识新对象。
	 */
	public void addKnownObject(L1Object obj) {
		if (!_knownObjects.contains(obj)) {
			_knownObjects.add(obj);
			if (obj instanceof L1PcInstance) {
				_knownPlayer.add((L1PcInstance) obj);
			}
		}
	}

	/**
	 * 删除目标识别。
	 * 
	 * @param obj
	 *            对象被删除。
	 */
	public void removeKnownObject(L1Object obj) {
		_knownObjects.remove(obj);
		if (obj instanceof L1PcInstance) {
			_knownPlayer.remove(obj);
		}
	}

	/**
	 * 为角色、删除所有被确认的对象。
	 */
	public void removeAllKnownObjects() {
		_knownObjects.clear();
		_knownPlayer.clear();
	}

	// ■■■■■■■■■■ 角色属性 ■■■■■■■■■■

	/** 名称 */
	private String _name; // ● 名称
	/** 取得名称 */
	public String getName() {
		return _name;
	}
	/** 设定名称 */
	public void setName(String s) {
		_name = s;
	}

	/** 等级 */
	private int _level; // ● 等级
	/** 取得等级 */
	public synchronized int getLevel() {
		return _level;
	}
	/** 设定等级 */
	public synchronized void setLevel(long level) {
		_level = (int) level;
	}

	/** 最高ＨＰ（1～32767） */
	private short _maxHp = 0;
	/** 真正的最高ＨＰ */
	private int _trueMaxHp = 0;
	/** 取得最高ＨＰ */
	public short getMaxHp() {
		return _maxHp;
	}
	/** 设定最高ＨＰ */
	public void setMaxHp(int hp) {
		_trueMaxHp = hp;
		_maxHp = (short) IntRange.ensure(_trueMaxHp, 1, 32767);
		_currentHp = Math.min(_currentHp, _maxHp);
	}
	/** 增加最高ＨＰ */
	public void addMaxHp(int i) {
		setMaxHp(_trueMaxHp + i);
	}

	/** 最高ＭＰ（0～32767） */
	private short _maxMp = 0;
	/** 真正的最高ＭＰ */
	private int _trueMaxMp = 0;
	/** 取得最高ＭＰ */
	public short getMaxMp() {
		return _maxMp;
	}
	/** 设定最高ＭＰ */
	public void setMaxMp(int mp) {
		_trueMaxMp = mp;
		_maxMp = (short) IntRange.ensure(_trueMaxMp, 0, 32767);
		_currentMp = Math.min(_currentMp, _maxMp);
	}
	/** 增加最高ＭＰ */
	public void addMaxMp(int i) {
		setMaxMp(_trueMaxMp + i);
	}

	/** 物理防御（-128～127） */
	private int _ac = 0;
	/** 真正的物理防御 */
	private int _trueAc = 0;
	/** 取得物理防御 */
	public int getAc() {
		return _ac + L1MagicDoll.getAcByDoll(this); // TODO 魔法娃娃效果 - 防御增加
	}
	/** 设定物理防御 */
	public void setAc(int i) {
		_trueAc = i;
		_ac = IntRange.ensure(i, -211, 211);
	}
	/** 增加物理防御 */
	public void addAc(int i) {
		setAc(_trueAc + i);
	}

	/** 力量值（1～127） */
	private byte _str = 0;
	/** 真正的力量值 */
	private short _trueStr = 0;
	/** 取得力量值 */
	public byte getStr() {
		return _str;
	}
	/** 设定力量值 */
	public void setStr(int i) {
		_trueStr = (short) i;
		_str = (byte) IntRange.ensure(i, 1, 127);
	}
	/** 增加力量值 */
	public void addStr(int i) {
		setStr(_trueStr + i);
	}

	/** 体质值（1～127） */
	private byte _con = 0;
	/** 真正的体质值 */
	private short _trueCon = 0;
	/** 取得体质值 */
	public byte getCon() {
		return _con;
	}
	/** 设定体质值 */
	public void setCon(int i) {
		_trueCon = (short) i;
		_con = (byte) IntRange.ensure(i, 1, 127);
	}
	/** 增加体质值 */
	public void addCon(int i) {
		setCon(_trueCon + i);
	}

	/** 敏捷值（1～127） */
	private byte _dex = 0;
	/** 真正的敏捷值 */
	private short _trueDex = 0;
	/** 取得敏捷值 */
	public byte getDex() {
		return _dex;
	}
	/** 设定敏捷值 */
	public void setDex(int i) {
		_trueDex = (short) i;
		_dex = (byte) IntRange.ensure(i, 1, 127);
	}
	/** 增加敏捷值 */
	public void addDex(int i) {
		setDex(_trueDex + i);
	}

	/** 魅力值（1～127） */
	private byte _cha = 0;
	/** 真正的魅力值 */
	private short _trueCha = 0;
	/** 取得魅力值 */
	public byte getCha() {
		return _cha;
	}
	/** 设定魅力值 */
	public void setCha(int i) {
		_trueCha = (short) i;
		_cha = (byte) IntRange.ensure(i, 1, 127);
	}
	/** 增加魅力值 */
	public void addCha(int i) {
		setCha(_trueCha + i);
	}

	/** 智力值（1～127） */
	private byte _int = 0;
	/** 真正的智力值 */
	private short _trueInt = 0;
	/** 取得智力值 */
	public byte getInt() {
		return _int;
	}
	/** 设定智力值 */
	public void setInt(int i) {
		_trueInt = (short) i;
		_int = (byte) IntRange.ensure(i, 1, 127);
	}
	/** 增加智力值 */
	public void addInt(int i) {
		setInt(_trueInt + i);
	}

	/** 精神值（1～127） */
	private byte _wis = 0;
	/** 真正的精神值 */
	private short _trueWis = 0;
	/** 取得精神值 */
	public byte getWis() {
		return _wis;
	}
	/** 设定精神值 */
	public void setWis(int i) {
		_trueWis = (short) i;
		_wis = (byte) IntRange.ensure(i, 1, 127);
	}
	/** 增加精神值 */
	public void addWis(int i) {
		setWis(_trueWis + i);
	}

	/** 风属性防御（-128～127） */
	private int _wind = 0;
	/** 真正的风属性防御 */
	private int _trueWind = 0;
	/** 取得风属性防御 */
	public int getWind() {
		return _wind;
	} // 当你使用
	/** 增加风属性防御 */
	public void addWind(int i) {
		_trueWind += i;
		if (_trueWind >= 127) {
			_wind = 127;
		} else if (_trueWind <= -128) {
			_wind = -128;
		} else {
			_wind = _trueWind;
		}
	}
	/** 水属性防御（-128～127） */
	private int _water = 0;
	/** 真正的水属性防御 */
	private int _trueWater = 0;
	/** 取得水属性防御 */
	public int getWater() {
		return _water;
	} // 当你使用
	/** 增加水属性防御 */
	public void addWater(int i) {
		_trueWater += i;
		if (_trueWater >= 127) {
			_water = 127;
		} else if (_trueWater <= -128) {
			_water = -128;
		} else {
			_water = _trueWater;
		}
	}
	/** 火属性防御（-128～127） */
	private int _fire = 0;
	/** 真正的火属性防御 */
	private int _trueFire = 0;
	/** 取得火属性防御 */
	public int getFire() {
		return _fire;
	} // 当你使用
	/** 增加火属性防御 */
	public void addFire(int i) {
		_trueFire += i;
		if (_trueFire >= 127) {
			_fire = 127;
		} else if (_trueFire <= -128) {
			_fire = -128;
		} else {
			_fire = _trueFire;
		}
	}
	/** 地属性防御（-128～127） */
	private int _earth = 0;
	/** 真正的地属性防御 */
	private int _trueEarth = 0;
	/** 取得地属性防御 */
	public int getEarth() {
		return _earth;
	} // 当你使用
	/** 增加地属性防御 */
	public void addEarth(int i) {
		_trueEarth += i;
		if (_trueEarth >= 127) {
			_earth = 127;
		} else if (_trueEarth <= -128) {
			_earth = -128;
		} else {
			_earth = _trueEarth;
		}
	}

	/** 增加属性种类 */
	private int _addAttrKind; // エレメンタルフォールダウンで减少した属性の种类
	/** 取得属性种类 */
	public int getAddAttrKind() {
		return _addAttrKind;
	}
	/** 设定属性种类 */
	public void setAddAttrKind(int i) {
		_addAttrKind = i;
	}

	/** 昏迷耐性 */
	private int _registStun = 0;
	/** 真正的昏迷耐性 */
	private int _trueRegistStun = 0;
	/** 取得昏迷耐性 */
	public int getRegistStun() {
		return (_registStun + L1MagicDoll.getRegistStunByDoll(this));
	}
	/** 增加昏迷耐性 */
	public void addRegistStun(int i) {
		_trueRegistStun += i;
		if (_trueRegistStun > 127) {
			_registStun = 127;
		} else if (_trueRegistStun < -128) {
			_registStun = -128;
		} else {
			_registStun = _trueRegistStun;
		}
	}

	/** 石化耐性 */
	private int _registStone = 0;
	/** 真正的石化耐性 */
	private int _trueRegistStone = 0;
	/** 取得石化耐性 */
	public int getRegistStone() {
		return (_registStone + L1MagicDoll.getRegistStoneByDoll(this));
	}
	/** 增加石化耐性 */
	public void addRegistStone(int i) {
		_trueRegistStone += i;
		if (_trueRegistStone > 127) {
			_registStone = 127;
		} else if (_trueRegistStone < -128) {
			_registStone = -128;
		} else {
			_registStone = _trueRegistStone;
		}
	}

	/** 睡眠耐性 */
	private int _registSleep = 0;
	/** 真正的睡眠耐性 */
	private int _trueRegistSleep = 0;
	/** 取得睡眠耐性 */
	public int getRegistSleep() {
		return (_registSleep + L1MagicDoll.getRegistSleepByDoll(this));
	}
	/** 增加睡眠耐性 */
	public void addRegistSleep(int i) {
		_trueRegistSleep += i;
		if (_trueRegistSleep > 127) {
			_registSleep = 127;
		} else if (_trueRegistSleep < -128) {
			_registSleep = -128;
		} else {
			_registSleep = _trueRegistSleep;
		}
	}

	/** 寒冰耐性 */
	private int _registFreeze = 0;
	/** 真正的寒冰耐性 */
	private int _trueRegistFreeze = 0;
	/** 取得寒冰耐性 */
	public int getRegistFreeze() {
		return (_registFreeze
				+ L1MagicDoll.getRegistFreezeByDoll(this)); // TODO 魔法娃娃效果 - 寒冰耐性增加
	}
	/** 增加寒冰耐性 */
	public void add_regist_freeze(int i) {
		_trueRegistFreeze += i;
		if (_trueRegistFreeze > 127) {
			_registFreeze = 127;
		} else if (_trueRegistFreeze < -128) {
			_registFreeze = -128;
		} else {
			_registFreeze = _trueRegistFreeze;
		}
	}

	/** 支撑耐性 */
	private int _registSustain = 0;
	/** 真正的支撑耐性 */
	private int _trueRegistSustain = 0;
	/** 取得支撑耐性 */
	public int getRegistSustain() {
		return (_registSustain + L1MagicDoll.getRegistSustainByDoll(this));
	}
	/** 增加支撑耐性 */
	public void addRegistSustain(int i) {
		_trueRegistSustain += i;
		if (_trueRegistSustain > 127) {
			_registSustain = 127;
		} else if (_trueRegistSustain < -128) {
			_registSustain = -128;
		} else {
			_registSustain = _trueRegistSustain;
		}
	}

	/** 闇黑耐性 */
	private int _registBlind = 0;
	/** 真正的闇黑耐性 */
	private int _trueRegistBlind = 0;
	/** 取得闇黑耐性 */
	public int getRegistBlind() {
		return (_registBlind + L1MagicDoll.getRegistBlindByDoll(this));
	}
	/** 增加闇黑耐性 */
	public void addRegistBlind(int i) {
		_trueRegistBlind += i;
		if (_trueRegistBlind > 127) {
			_registBlind = 127;
		} else if (_trueRegistBlind < -128) {
			_registBlind = -128;
		} else {
			_registBlind = _trueRegistBlind;
		}
	}

	/** 近距离攻击力修正（-128～127） */
	private int _dmgup = 0;
	/** 真正的近距离的攻击力修正 */
	private int _trueDmgup = 0;
	/** 取得近距离的攻击力修正 */
	public int getDmgup() {
		return _dmgup + L1MagicDoll.getDamageAddByDoll(this); // 魔法娃娃效果 - 近距离的攻击力增加
	} // 当你使用
	/** 增加近距离的攻击力修正 */
	public void addDmgup(int i) {
		_trueDmgup += i;
		if (_trueDmgup >= 127) {
			_dmgup = 127;
		} else if (_trueDmgup <= -128) {
			_dmgup = -128;
		} else {
			_dmgup = _trueDmgup;
		}
	}

	/** 弓的攻击力修正（-128～127） */
	private int _bowDmgup = 0;
	/** 真正的弓的攻击力修正 */
	private int _trueBowDmgup = 0;
	/** 取得弓的攻击力修正 */
	public int getBowDmgup() {
		return _bowDmgup + L1MagicDoll.getBowDamageByDoll(this); // 魔法娃娃效果 - 远距离的攻击力增加
	}
	/** 增加弓的攻击力修正 */
	public void addBowDmgup(int i) {
		_trueBowDmgup += i;
		if (_trueBowDmgup >= 127) {
			_bowDmgup = 127;
		} else if (_trueBowDmgup <= -128) {
			_bowDmgup = -128;
		} else {
			_bowDmgup = _trueBowDmgup;
		}
	}

	/** 近距离的命中率修正（-128～127） */
	private int _hitup = 0;
	/** 真正的近距离的命中率修正 */
	private int _trueHitup = 0;
	/** 取得近距离的命中率修正 */
	public int getHitup() {
		return (_hitup
				+ L1MagicDoll.getHitAddByDoll(this)); // TODO 魔法娃娃效果 - 近距离的命中率增加
	}
	/** 增加近距离的命中率修正 */
	public void addHitup(int i) {
		_trueHitup += i;
		if (_trueHitup >= 127) {
			_hitup = 127;
		} else if (_trueHitup <= -128) {
			_hitup = -128;
		} else {
			_hitup = _trueHitup;
		}
	}

	/** 弓的命中率修正（-128～127） */
	private int _bowHitup = 0;
	/** 真正的弓的命中率修正 */
	private int _trueBowHitup = 0;
	/** 取得弓的命中率修正 */
	public int getBowHitup() {
		return (_bowHitup
				+ L1MagicDoll.getBowHitAddByDoll(this)); // TODO 魔法娃娃效果 - 弓的命中率增加
	}
	/** 增加弓的命中率修正 */
	public void addBowHitup(int i) {
		_trueBowHitup += i;
		if (_trueBowHitup >= 127) {
			_bowHitup = 127;
		} else if (_trueBowHitup <= -128) {
			_bowHitup = -128;
		} else {
			_bowHitup = _trueBowHitup;
		}
	}

	/** 魔法防御（0～） */
	private int _mr = 0; // ● 魔法防御（0～）
	/** 真正的魔法防御 */
	private int _trueMr = 0;
	/** 取得魔法防御 */
	public int getMr() {
		if (hasSkillEffect(153) == true) {
			return _mr / 4;
		} else {
			return _mr;
		}
	} // 当你使用
	/** 取得真正的魔法防御 */
	public int getTrueMr() {
		return _trueMr;
	} // 当你设定
	/** 增加魔法防御 */
	public void addMr(int i) {
		_trueMr += i;
		if (_trueMr <= 0) {
			_mr = 0;
		} else {
			_mr = _trueMr;
		}
	}

	/** 魔攻 */
	private int _sp = 0;
	/** 取得魔攻 */
	public int getSp() {
		return getTrueSp() + _sp;
	}
	/** 取得真正的魔攻 */
	public int getTrueSp() {
		return getMagicLevel() + getMagicBonus();
	}
	/** 增加魔攻 */
	public void addSp(int i) {
		_sp += i;
	}

	/** 死亡状态 */
	private boolean _isDead; // ● 死亡状态
	/** 是死亡状态 */
	public boolean isDead() {
		return _isDead;
	}
	/** 设定死亡状态 */
	public void setDead(boolean flag) {
		_isDead = flag;
	}

	/** 状态 */
	private int _status; // ● 状态？
	/** 取得状态 */
	public int getStatus() {
		return _status;
	}
	/** 设定状态 */
	public void setStatus(int i) {
		_status = i;
	}

	/** 封号 */
	private String _title; // ● 头衔
	/** 取得封号 */
	public String getTitle() {
		return _title;
	}
	/** 设定封号 */
	public void setTitle(String s) {
		_title = s;
	}

	/** 正义值 */
	private int _lawful; // ● 正义值
	/** 取得正义值 */
	public int getLawful() {
		return _lawful;
	}
	/** 设定正义值 */
	public void setLawful(int i) {
		_lawful = i;
	}
	/** 增加正义值 */
	public synchronized void addLawful(int i) {
		_lawful += i;
		if (_lawful > 32767) {
			_lawful = 32767;
		} else if (_lawful < -32768) {
			_lawful = -32768;
		}
	}

	/** 面向: 0.左上 1.上 2.右上 3.右 4.右下 5.下 6.左下 7.左 */
	private int _heading; // ● 面向 0.左上 1.上 2.右上 3.右 4.右下 5.下 6.左下 7.左
	/** 取得面向: 0.左上 1.上 2.右上 3.右 4.右下 5.下 6.左下 7.左 */
	public int getHeading() {
		return _heading;
	}
	/** 设定面向: 0.左上 1.上 2.右上 3.右 4.右下 5.下 6.左下 7.左 */
	public void setHeading(int i) {
		_heading = i;
	}

	/** 速度: 0.通常 1.加速 2.缓速 */
	private int _moveSpeed; // ● 速度 0.通常 1.加速 2.缓速
	/** 取得速度: 0.通常 1.加速 2.缓速 */
	public int getMoveSpeed() {
		return _moveSpeed;
	}
	/** 设定速度: 0.通常 1.加速 2.缓速 */
	public void setMoveSpeed(int i) {
		_moveSpeed = i;
	}

	/** 勇敢状态: 0，通常  1，勇敢  */
	private int _braveSpeed; // ● 勇敢状态 0，通常1。勇敢
	/** 取得勇敢状态: 0，通常  1，勇敢  */
	public int getBraveSpeed() {
		return _braveSpeed;
	}
	/** 设定勇敢状态: 0，通常  1，勇敢  */
	public void setBraveSpeed(int i) {
		_braveSpeed = i;
	}

	/** 暂时变身的ID */
	private int _tempCharGfx; // ● 暂时变身的ID
	/** 取得暂时变身的ID */
	public int getTempCharGfx() {
		return _tempCharGfx;
	}
	/** 设定暂时变身的ID */
	public void setTempCharGfx(int i) {
		_tempCharGfx = i;
	}

	/** 原本图形的ＩＤ */
	private int _gfxid; // ● 原本图型的ＩＤ
	/** 取得原本图形的ＩＤ */
	public int getGfxId() {
		return _gfxid;
	}
	/** 设定原本图形的ＩＤ */
	public void setGfxId(int i) {
		_gfxid = i;
	}

	/** 取得魔法等级 */
	public int getMagicLevel() {
		return getLevel() / 4;
	}
	/** 取得魔法加成 */
	public int getMagicBonus() {
		int i = getInt();
		if (i <= 5) {
			return -2;
		} else if (i <= 8) {
			return -1;
		} else if (i <= 11) {
			return 0;
		} else if (i <= 14) {
			return 1;
		} else if (i <= 17) {
			return 2;
		} else if (i <= 24) {
			return i - 15;
		} else if (i <= 35) {
			return 10;
		} else if (i <= 42) {
			return 11;
		} else if (i <= 49) {
			return 12;
		} else if (i <= 50) {
			return 13;
		} else {
			return i - 25;
		}
	}

	/** 是隐身状态 */
	public boolean isInvisble() {
		return (hasSkillEffect(INVISIBILITY) || hasSkillEffect(BLIND_HIDING));
	}

	/** 恢复HP */
	public void healHp(int pt) {
		setCurrentHp(getCurrentHp() + pt);
	}

	/** 友好度 */
	private int _karma;
	/**
	 * 取得友好度。
	 * 
	 * @return 友好度。
	 */
	public int getKarma() {
		return _karma;
	}
	/**
	 * 设定友好度。
	 * 
	 * @param karma
	 *            友好度。
	 */
	public void setKarma(int karma) {
		_karma = karma;
	}

	/** 设定魔防 */
	public void setMr(int i) {
		_trueMr = i;
		if (_trueMr <= 0) {
			_mr = 0;
		} else {
			_mr = _trueMr;
		}
	}

	/** 打开灯 */
	public void turnOnOffLight() {
		int lightSize = 0;
		if (this instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) this;
			lightSize = npc.getLightSize(); // npc.sqlのライトサイズ
		}
		if (hasSkillEffect(LIGHT)) {
			lightSize = 14;
		}

		for (L1ItemInstance item : getInventory().getItems()) {
			if ((item.getItem().getType2() == 0)
					&& (item.getItem().getType() == 2)) { // light系アイテム
				int itemlightSize = item.getItem().getLightRange();
				if ((itemlightSize != 0) && item.isNowLighting()) {
					if (itemlightSize > lightSize) {
						lightSize = itemlightSize;
					}
				}
			}
		}

		if (this instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) this;
			pc.sendPackets(new S_Light(pc.getId(), lightSize));
		}
		if (!isInvisble()) {
			broadcastPacket(new S_Light(getId(), lightSize));
		}

		setOwnLightSize(lightSize); // S_OwnCharPackのライト范围
		setChaLightSize(lightSize); // S_OtherCharPack, S_NPCPackなどのライト范围
	}

	/** 亮度范围 */
	private int _chaLightSize;
	/** 取得亮度范围 */
	public int getChaLightSize() {
		if (isInvisble()) {
			return 0;
		}
		return _chaLightSize;
	}
	/** 设定亮度范围 */
	public void setChaLightSize(int i) {
		_chaLightSize = i;
	}

	/** 自身亮度范围(S_OwnCharPack用) */
	private int _ownLightSize;
	/** 取得自身亮度范围(S_OwnCharPack用) */
	public int getOwnLightSize() {
		return _ownLightSize;
	}
	/** 设定自身亮度范围(S_OwnCharPack用) */
	public void setOwnLightSize(int i) {
		_ownLightSize = i;
	}

	/** 龙之门扉编号 */
	private int _portalNumber = -1;
	/** 取得龙之门扉编号 */
	public int getPortalNumber() {
		return _portalNumber;
	}
	/** 设定龙之门扉编号 */
	public void setPortalNumber(int portalNumber) {
		_portalNumber = portalNumber;
	}

	/** 饱食度 */
	private int _food;
	/** 取得饱食度 */
	public int get_food() {
		return _food;
	}
	/** 设定饱食度 */
	public void set_food(int i) {
		_food = i;
	}

	/** 附魔石等级 */
	private byte _magicStoneLevel;
	/** 取得附魔石等级 */
	public byte getMagicStoneLevel() {
		return _magicStoneLevel;
	}
	/** 设定附魔石等级 */
	public void setMagicStoneLevel(byte i) {
		_magicStoneLevel = i;
	}

	/** 闪避率 + */
	private byte _dodge = 0;
	/** 取得闪避率 + */
	public byte getDodge() {
		return _dodge;
	}
	/** 增加闪避率 + */
	public void addDodge(byte i) {
		_dodge += i;
		if (_dodge >= 10) {
			_dodge = 10;
		} else if (_dodge <= 0){
			_dodge = 0;
		}
	}

	/** 闪避率 - */
	private byte _nDodge = 0;
	/** 取得闪避率 - */
	public byte getNdodge() {
		return _nDodge;
	}
	/** 增加闪避率 - */
	public void addNdodge(byte i) {
		_nDodge += i;
		if (_nDodge >= 10) {
			_nDodge = 10;
		} else if (_nDodge <= 0){
			_nDodge = 0;
		}
	}

	/** 旅馆编号 */
	private int _innRoomNumber;
	/** 取得旅馆编号 */
	public int getInnRoomNumber() {
		return _innRoomNumber;
	}
	/** 设定旅馆编号 */
	public void setInnRoomNumber(int i) {
		_innRoomNumber = i;
	}
	/** 旅馆钥匙ID */
	private int _innKeyId;
	/** 取得旅馆钥匙ID */
	public int getInnKeyId() {
		return _innKeyId;
	}
	/** 设定旅馆钥匙ID */
	public void setInnKeyId(int i) {
		_innKeyId = i;
	}

	/** 大厅 */
	private boolean _isHall;
	/** 检查房间或大厅 */
	public boolean checkRoomOrHall() {
		return _isHall;
	}
	/** 设定大厅 */
	public void setHall(boolean i) {
		_isHall = i;
	}

	/** 判断特定状态下才可攻击 NPC */
	public boolean isAttackMiss(L1Character cha , int npcId) {
		switch (npcId) {
			case 45912: // 士兵的怨灵
			case 45913: // 士兵的怨灵
			case 45914: // 怨灵
			case 45915: // 怨灵
				if (!cha.hasSkillEffect(STATUS_HOLY_WATER)) {
					return true;
				}
				return false;
			case 45916: // 哈蒙将军的怨灵
				if (!cha.hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
					return true;
				}
				return false;
			case 45941: // 受诅咒的巫女莎尔
				if (!cha.hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
					return true;
				}
				return false;
			case 45752: // 炎魔(变身前)
				if (!cha.hasSkillEffect(STATUS_CURSE_BARLOG)) {
					return true;
				}
				return false;
			case 45753: // 炎魔(变身后)
				if (!cha.hasSkillEffect(STATUS_CURSE_BARLOG)) {
					return true;
				}
				return false;
			case 45675: // 火焰之影(变身前)
				if (!cha.hasSkillEffect(STATUS_CURSE_YAHEE)) {
					return true;
				}
				return false;
			case 81082: // 火焰之影(变身后)
				if (!cha.hasSkillEffect(STATUS_CURSE_YAHEE)) {
					return true;
				}
				return false;
			case 45625: // 混沌
				if (!cha.hasSkillEffect(STATUS_CURSE_YAHEE)) {
					return true;
				}
				return false;
			case 45674: // 死亡
				if (!cha.hasSkillEffect(STATUS_CURSE_YAHEE)) {
					return true;
				}
				return false;
			case 45685: // 堕落
				if (!cha.hasSkillEffect(STATUS_CURSE_YAHEE)) {
					return true;
				}
				return false;
			case 81341: // 再生之祭坛
				if (!cha.hasSkillEffect(SECRET_MEDICINE_OF_DESTRUCTION)) {
					return true;
				}
			default:
				if ((npcId >= 46068) && (npcId <= 46091) // 原生魔族
						&& (cha.getTempCharGfx() == 6035)) {
					return true;
				}
				else if ((npcId >= 46092) && (npcId <= 46106) // 不死魔族
						&& (cha.getTempCharGfx() == 6034)) {
					return true;
				}
				return false;
		}
	}
}
