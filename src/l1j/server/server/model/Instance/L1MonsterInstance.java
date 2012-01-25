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

import static l1j.server.server.model.skill.L1SkillId.EFFECT_BLOODSTAIN_OF_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.IdFactory;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.datatables.UBTable;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1DragonSlayer;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1NpcTalkData;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1UltimateBattle;
import l1j.server.server.model.L1World;
import l1j.server.server.model.skill.L1BuffUtil;
import l1j.server.server.serverpackets.S_ChangeName;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_HPMeter;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_NpcChangeShape;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.CalcExp;
import l1j.server.server.utils.Random;

/**
 * 怪物控制项
 */
public class L1MonsterInstance extends L1NpcInstance {

	private static final long serialVersionUID = 1L;

	private static Logger _log = Logger.getLogger(L1MonsterInstance.class.getName());

	private boolean _storeDroped; // ドロップアイテムの读迂が完了したか

	private boolean isDoppel;

	// 使用的物品处理
	@Override
	public void onItemUse() {
		if (!isActived() && (_target != null)) {
			useItem(USEITEM_HASTE, 40); // ４０％使用加速药水
			// 变形判断
			onDoppel(true);
		}
		if (getCurrentHp() * 100 / getMaxHp() < 40) { // ＨＰが４０％きったら
			useItem(USEITEM_HEAL, 50); // ５０％の确率で回复ポーション使用
		}
	}

	// 变形怪变成玩家判断
	@Override
	public void onDoppel(boolean isChangeShape) {
		if (getNpcTemplate().is_doppel()) {
			boolean updateObject = false;

			if (!isChangeShape) { // 复原
				updateObject = true;
				// setName(getNpcTemplate().get_name());
				// setNameId(getNpcTemplate().get_nameid());
				setTempLawful(getNpcTemplate().get_lawful());
				setGfxId(getNpcTemplate().get_gfxid());
				setTempCharGfx(getNpcTemplate().get_gfxid());
			}
			else if (!isDoppel && (_target instanceof L1PcInstance)) { // 未变形
				setSleepTime(300);
				L1PcInstance targetPc = (L1PcInstance) _target;
				isDoppel = true;
				updateObject = true;
				setName(targetPc.getName());
				setNameId(targetPc.getName());
				setTempLawful(targetPc.getLawful());
				setGfxId(targetPc.getClassId());
				setTempCharGfx(targetPc.getClassId());

				if (targetPc.getClassId() != 6671) { // 非幻术师拿剑
					setStatus(4);
				}
				else { // 幻术师拿斧头
					setStatus(11);
				}
			}
			// 移动、攻击速度
			setPassispeed(SprTable.getInstance().getMoveSpeed(getTempCharGfx(), getStatus()));
			setAtkspeed(SprTable.getInstance().getAttackSpeed(getTempCharGfx(), getStatus() + 1));
			// 变形
			if (updateObject) {
				for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
					if (!isChangeShape) {
						pc.sendPackets(new S_ChangeName(getId(), getNpcTemplate().get_nameid()));
					}
					else {
						pc.sendPackets(new S_ChangeName(getId(), getNameId()));
					}
					pc.sendPackets(new S_NpcChangeShape(getId(), getGfxId(), getTempLawful(), getStatus()));
				}
			}
		}
	}

	@Override
	public void onPerceive(L1PcInstance perceivedFrom) {
		perceivedFrom.addKnownObject(this);
		if (0 < getCurrentHp()) {
			perceivedFrom.sendPackets(new S_NPCPack(this));
			onNpcAI(); // 怪物AI，开始
			if (getBraveSpeed() == 1) { // 二段加速状态
				perceivedFrom.sendPackets(new S_SkillBrave(getId(), 1, 600000));
				setBraveSpeed(1);
			}
		}
		else {
			// 水龙 阶段一、二 死亡隐形
			if (getGfxId() != 7864 && getGfxId() != 7869) {
				perceivedFrom.sendPackets(new S_NPCPack(this));
			}
		}
	}

	// 寻找目标
	public static int[][] _classGfxId = { { 0, 1 }, { 48, 61 }, { 37, 138 }, { 734, 1186 }, { 2786, 2796 } };

	@Override
	public void searchTarget() {
		// 目标搜索
		L1PcInstance lastTarget = null;
		L1PcInstance targetPlayer = null;

		if (_target != null && _target instanceof L1PcInstance) {
			lastTarget = (L1PcInstance) _target;
			tagertClear();
		}

		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(this)) {

			if (pc == lastTarget || (pc.getCurrentHp() <= 0) || pc.isDead() || pc.isGm() || pc.isMonitor() || pc.isGhost()) {
				continue;
			}

			// 斗技场内は变身／未变身に限らず全てアクティブ
			int mapId = getMapId();
			if ((mapId == 88 // 奇岩竞技场
					)
					|| (mapId == 98 // 威顿竞技场
					) || (mapId == 92 // 古鲁丁竞技场
					) || (mapId == 91 // 说话之岛竞技场
					) || (mapId == 95 // 银骑士竞技场
					)) {
				if (!pc.isInvisble() || getNpcTemplate().is_agrocoi()) { // 检查隐身
					targetPlayer = pc;
					break;
				}
			}

			if (getNpcId() == 45600) { // 克特
				if (pc.isCrown() || pc.isDarkelf() || (pc.getTempCharGfx() != pc.getClassId())) { // 未变身的君主、黑暗精灵
					targetPlayer = pc;
					break;
				}
			}

			// 如果你满足任一条件、友好と见なされ先制攻击されない。
			// ・モンスターのカルマがマイナス值（バルログ侧モンスター）でPCのカルマレベルが1以上（バルログ友好）
			// ・モンスターのカルマがプラス值（ヤヒ侧モンスター）でPCのカルマレベルが-1以下（ヤヒ友好）
			if (((getNpcTemplate().getKarma() < 0) && (pc.getKarmaLevel() >= 1)) || ((getNpcTemplate().getKarma() > 0) && (pc.getKarmaLevel() <= -1))) {
				continue;
			}
			// 见弃てられた者たちの地 カルマクエストの变身中は、各阵営のモンスターから先制攻击されない
			if (((pc.getTempCharGfx() == 6034) && (getNpcTemplate().getKarma() < 0)) || ((pc.getTempCharGfx() == 6035) && (getNpcTemplate().getKarma() > 0)) || ((pc.getTempCharGfx() == 6035) && (getNpcTemplate().get_npcId() == 46070))
					|| ((pc.getTempCharGfx() == 6035) && (getNpcTemplate().get_npcId() == 46072))) {
				continue;
			}

			if (!getNpcTemplate().is_agro() && !getNpcTemplate().is_agrososc() && (getNpcTemplate().is_agrogfxid1() < 0) && (getNpcTemplate().is_agrogfxid2() < 0)) { // 完全なノンアクティブモンスター
				if (pc.getLawful() < -1000) { // プレイヤーがカオティック
					targetPlayer = pc;
					break;
				}
				continue;
			}

			if (!pc.isInvisble() || getNpcTemplate().is_agrocoi()) { // インビジチェック
				if (pc.hasSkillEffect(67)) { // 变身してる
					if (getNpcTemplate().is_agrososc()) { // 变身に对してアクティブ
						targetPlayer = pc;
						break;
					}
				}
				else if (getNpcTemplate().is_agro()) { // アクティブモンスター
					targetPlayer = pc;
					break;
				}

				// 特定のクラスorグラフィックＩＤにアクティブ
				if ((getNpcTemplate().is_agrogfxid1() >= 0) && (getNpcTemplate().is_agrogfxid1() <= 4)) { // クラス指定
					if ((_classGfxId[getNpcTemplate().is_agrogfxid1()][0] == pc.getTempCharGfx()) || (_classGfxId[getNpcTemplate().is_agrogfxid1()][1] == pc.getTempCharGfx())) {
						targetPlayer = pc;
						break;
					}
				}
				else if (pc.getTempCharGfx() == getNpcTemplate().is_agrogfxid1()) { // グラフィックＩＤ指定
					targetPlayer = pc;
					break;
				}

				if ((getNpcTemplate().is_agrogfxid2() >= 0) && (getNpcTemplate().is_agrogfxid2() <= 4)) { // クラス指定
					if ((_classGfxId[getNpcTemplate().is_agrogfxid2()][0] == pc.getTempCharGfx()) || (_classGfxId[getNpcTemplate().is_agrogfxid2()][1] == pc.getTempCharGfx())) {
						targetPlayer = pc;
						break;
					}
				}
				else if (pc.getTempCharGfx() == getNpcTemplate().is_agrogfxid2()) { // グラフィックＩＤ指定
					targetPlayer = pc;
					break;
				}
			}
		}
		if (targetPlayer != null) {
			_hateList.add(targetPlayer, 0);
			_target = targetPlayer;
		}
	}

	// 设置链接
	@Override
	public void setLink(L1Character cha) {
		if ((cha != null) && _hateList.isEmpty()) { // ターゲットがいない场合のみ追加
			_hateList.add(cha, 0);
			checkTarget();
		}
	}

	public L1MonsterInstance(L1Npc template) {
		super(template);
		_storeDroped = false;
	}

	@Override
	public void onNpcAI() {
		if (isAiRunning()) {
			return;
		}
		if (!_storeDroped) // 无驮なオブジェクトＩＤを発行しないようにここでセット
		{
			DropTable.getInstance().setDrop(this, getInventory());
			getInventory().shuffle();
			_storeDroped = true;
		}
		setActived(false);
		startAI();
	}

	@Override
	public void onTalkAction(L1PcInstance pc) {
		int objid = getId();
		L1NpcTalkData talking = NPCTalkDataTable.getInstance().getTemplate(getNpcTemplate().get_npcId());

		// html表示パケット送信
		if (pc.getLawful() < -1000) { // プレイヤーがカオティック
			pc.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
		}
		else {
			pc.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
		}
	}

	@Override
	public void onAction(L1PcInstance pc) {
		onAction(pc, 0);
	}

	@Override
	public void onAction(L1PcInstance pc, int skillId) {
		if ((getCurrentHp() > 0) && !isDead()) {
			L1Attack attack = new L1Attack(pc, this, skillId);
			if (attack.calcHit()) {
				attack.calcDamage();
				attack.calcStaffOfMana();
				attack.addPcPoisonAttack(pc, this);
				attack.addChaserAttack();
			}
			attack.action();
			attack.commit();
		}
	}

	@Override
	public void ReceiveManaDamage(L1Character attacker, int mpDamage) { // 攻击でＭＰを减らすときはここを使用
		if ((mpDamage > 0) && !isDead()) {
			// int Hate = mpDamage / 10 + 10; // 注意！计算适当 ダメージの１０分の１＋ヒットヘイト１０
			// setHate(attacker, Hate);
			setHate(attacker, mpDamage);

			onNpcAI();

			if (attacker instanceof L1PcInstance) { // 仲间意识をもつモンスターのターゲットに设定
				serchLink((L1PcInstance) attacker, getNpcTemplate().get_family());
			}

			int newMp = getCurrentMp() - mpDamage;
			if (newMp < 0) {
				newMp = 0;
			}
			setCurrentMp(newMp);
		}
	}

	// 打怪才显示血条
	Timer cancel_broadcast = null;
	L1PcInstance pc = null;
	L1PcInstance pc_party[] = null;

	@Override
	public void receiveDamage(L1Character attacker, int damage) { // 攻击でＨＰを减らすときはここを使用
		if ((getCurrentHp() > 0) && !isDead()) {
			if ((getHiddenStatus() == HIDDEN_STATUS_SINK) || (getHiddenStatus() == HIDDEN_STATUS_FLY)) {
				return;
			}
			if (damage >= 0) {
				if (!(attacker instanceof L1EffectInstance)) { // FWはヘイトなし
					setHate(attacker, damage);
				}
			}
			if (damage > 0) {
				removeSkillEffect(FOG_OF_SLEEPING);
			}

			onNpcAI();

			if (attacker instanceof L1PcInstance) { // 仲间意识をもつモンスターのターゲットに设定
				serchLink((L1PcInstance) attacker, getNpcTemplate().get_family());
			}

			// 血痕相克伤害增加 1.5倍
			if ((getNpcTemplate().get_npcId() == 97044 || getNpcTemplate().get_npcId() == 97045 || getNpcTemplate().get_npcId() == 97046) && (attacker.hasSkillEffect(EFFECT_BLOODSTAIN_OF_ANTHARAS))) { // 有安塔瑞斯的血痕时对法利昂增伤
				damage *= 1.5;
			}

			if ((attacker instanceof L1PcInstance) && (damage > 0)) {
				L1PcInstance player = (L1PcInstance) attacker;
				player.setPetTarget(this);
			}

			// 打怪才显示血条
			if (L1World.getInstance().getPlayer(attacker.getName()) != null)
				pc = L1World.getInstance().getPlayer(attacker.getName());
			else {
				L1NpcInstance pet = (L1NpcInstance) L1World.getInstance().findObject(attacker.getId());
				pc = L1World.getInstance().getPlayer(pet.getMaster().getName());
			}
			if (pc.getParty() != null)
				pc_party = pc.getParty().getMembers();

			int newHp = getCurrentHp() - damage;
			if ((newHp <= 0) && !isDead()) {
				int transformId = getNpcTemplate().getTransformId();

				// 打怪才显示血条
				pc.sendPackets(new S_HPMeter(this.getId(), 0xFF)); // 关闭血条
				if (pc.getParty() != null) {
					for (int i = 0; i < pc_party.length; i++) {
						pc_party[i].sendPackets(new S_HPMeter(this.getId(), 0xFF));
					}
				}

				// 变身しないモンスター
				if (transformId == -1) {
					if (getPortalNumber() != -1) {
						if (getNpcTemplate().get_npcId() == 97006 || getNpcTemplate().get_npcId() == 97044) {
							// 准备阶段二
							L1DragonSlayer.getInstance().startDragonSlayer2rd(getPortalNumber());
						}
						else if (getNpcTemplate().get_npcId() == 97007 || getNpcTemplate().get_npcId() == 97045) {
							// 准备阶段三
							L1DragonSlayer.getInstance().startDragonSlayer3rd(getPortalNumber());
						}
						else if (getNpcTemplate().get_npcId() == 97008 || getNpcTemplate().get_npcId() == 97046) {
							bloodstain();
							// 结束屠龙副本
							L1DragonSlayer.getInstance().endDragonSlayer(getPortalNumber());
						}
					}
					setCurrentHpDirect(0);
					setDead(true);
					setStatus(ActionCodes.ACTION_Die);
					openDoorWhenNpcDied(this);
					Death death = new Death(attacker);
					GeneralThreadPool.getInstance().execute(death);
					// Death(attacker);
					if (getPortalNumber() == -1 && (getNpcTemplate().get_npcId() == 97006 || getNpcTemplate().get_npcId() == 97007 || getNpcTemplate().get_npcId() == 97044 || getNpcTemplate().get_npcId() == 97045)) {
						doNextDragonStep(attacker, getNpcTemplate().get_npcId());
					}
				}
				else { // 变身するモンスター
						// distributeExpDropKarma(attacker);
					transform(transformId);
				}
			}
			if (newHp > 0) {
				setCurrentHp(newHp);
				hide();

				// 打怪才显示血条
				pc.sendPackets(new S_HPMeter(this));
				if (pc.getParty() != null) {
					for (int i = 0; i < pc_party.length; i++) {
						pc_party[i].sendPackets(new S_HPMeter(this));
					}
				}
				if (cancel_broadcast != null) {
					cancel_broadcast.cancel();
					cancel_broadcast = null;
				}
			}
			cancel_broadcast = new Timer();
			final L1Object broadcastMonster = this;
			cancel_broadcast.schedule(new TimerTask() {
				@Override
				public void run() {
					pc.sendPackets(new S_HPMeter(broadcastMonster.getId(), 0xFF)); // 关闭血条
					if (pc.getParty() != null) {
						for (int i = 0; i < pc_party.length; i++) {
							pc_party[i].sendPackets(new S_HPMeter(broadcastMonster.getId(), 0xFF));
						}
					}
					cancel_broadcast.cancel();
				}
			}, 10000); // 10秒没继续打怪就关闭血条
		}
		else if (!isDead()) { // 念のため
			setDead(true);
			setStatus(ActionCodes.ACTION_Die);
			Death death = new Death(attacker);
			GeneralThreadPool.getInstance().execute(death);
			// Death(attacker);
			if (getPortalNumber() == -1 && (getNpcTemplate().get_npcId() == 97006 || getNpcTemplate().get_npcId() == 97007 || getNpcTemplate().get_npcId() == 97044 || getNpcTemplate().get_npcId() == 97045)) {
				doNextDragonStep(attacker, getNpcTemplate().get_npcId());
			}
		}
	}

	private static void openDoorWhenNpcDied(L1NpcInstance npc) {
		int[] npcId = { 46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152 };
		int[] doorId = { 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010 };

		for (int i = 0; i < npcId.length; i++) {
			if (npc.getNpcTemplate().get_npcId() == npcId[i]) {
				openDoorInCrystalCave(doorId[i]);
				break;
			}
		}
	}

	private static void openDoorInCrystalCave(int doorId) {
		for (L1Object object : L1World.getInstance().getObject()) {
			if (object instanceof L1DoorInstance) {
				L1DoorInstance door = (L1DoorInstance) object;
				if (door.getDoorId() == doorId) {
					door.open();
				}
			}
		}
	}

	/**
	 * 距离が5以上离れているpcを距离3～4の位置に引き寄せる。
	 * 
	 * @param pc
	 */
	/*
	 * private void recall(L1PcInstance pc) { if (getMapId() != pc.getMapId()) { return; } if (getLocation().getTileLineDistance(pc.getLocation()) > 4) { for (int count = 0; count < 10; count++) { L1Location newLoc = getLocation().randomLocation(3, 4, false); if
	 * (glanceCheck(newLoc.getX(), newLoc.getY())) { L1Teleport.teleport(pc, newLoc.getX(), newLoc.getY(), getMapId(), 5, true); break; } } } }
	 */

	@Override
	public void setCurrentHp(int i) {
		int currentHp = i;
		if (currentHp >= getMaxHp()) {
			currentHp = getMaxHp();
		}
		setCurrentHpDirect(currentHp);

		if (getMaxHp() > getCurrentHp()) {
			startHpRegeneration();
		}
	}

	@Override
	public void setCurrentMp(int i) {
		int currentMp = i;
		if (currentMp >= getMaxMp()) {
			currentMp = getMaxMp();
		}
		setCurrentMpDirect(currentMp);

		if (getMaxMp() > getCurrentMp()) {
			startMpRegeneration();
		}
	}

	class Death implements Runnable {
		L1Character _lastAttacker;

		public Death(L1Character lastAttacker) {
			_lastAttacker = lastAttacker;
		}

		@Override
		public void run() {
			setDeathProcessing(true);
			setCurrentHpDirect(0);
			setDead(true);
			setStatus(ActionCodes.ACTION_Die);

			getMap().setPassable(getLocation(), true);

			broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Die));
			// 变形判断
			onDoppel(false);

			startChat(CHAT_TIMING_DEAD);

			distributeExpDropKarma(_lastAttacker);
			giveUbSeal();

			setDeathProcessing(false);

			setExp(0);
			setKarma(0);
			allTargetClear();

			startDeleteTimer();
		}
	}

	private void distributeExpDropKarma(L1Character lastAttacker) {
		if (lastAttacker == null) {
			return;
		}
		L1PcInstance pc = null;
		if (lastAttacker instanceof L1PcInstance) {
			pc = (L1PcInstance) lastAttacker;
		}
		else if (lastAttacker instanceof L1PetInstance) {
			pc = (L1PcInstance) ((L1PetInstance) lastAttacker).getMaster();
		}
		else if (lastAttacker instanceof L1SummonInstance) {
			pc = (L1PcInstance) ((L1SummonInstance) lastAttacker).getMaster();
		}

		if (pc != null) {
			ArrayList<L1Character> targetList = _hateList.toTargetArrayList();
			ArrayList<Integer> hateList = _hateList.toHateArrayList();
			long exp = getExp();
			CalcExp.calcExp(pc, getId(), targetList, hateList, exp);
			// 死亡した场合はドロップとカルマも分配、死亡せず变身した场合はEXPのみ
			if (isDead()) {
				distributeDrop();
				giveKarma(pc);
			}
		}
		else if (lastAttacker instanceof L1EffectInstance) { // FWが倒した场合
			ArrayList<L1Character> targetList = _hateList.toTargetArrayList();
			ArrayList<Integer> hateList = _hateList.toHateArrayList();
			// ヘイトリストにキャラクターが存在する
			if (!hateList.isEmpty()) {
				// 最大ヘイトを持つキャラクターが倒したものとする
				int maxHate = 0;
				for (int i = hateList.size() - 1; i >= 0; i--) {
					if (maxHate < (hateList.get(i))) {
						maxHate = (hateList.get(i));
						lastAttacker = targetList.get(i);
					}
				}
				if (lastAttacker instanceof L1PcInstance) {
					pc = (L1PcInstance) lastAttacker;
				}
				else if (lastAttacker instanceof L1PetInstance) {
					pc = (L1PcInstance) ((L1PetInstance) lastAttacker).getMaster();
				}
				else if (lastAttacker instanceof L1SummonInstance) {
					pc = (L1PcInstance) ((L1SummonInstance) lastAttacker).getMaster();
				}
				if (pc != null) {
					long exp = getExp();
					CalcExp.calcExp(pc, getId(), targetList, hateList, exp);
					// 死亡した场合はドロップとカルマも分配、死亡せず变身した场合はEXPのみ
					if (isDead()) {
						distributeDrop();
						giveKarma(pc);
					}
				}
			}
		}
	}

	private void distributeDrop() {
		ArrayList<L1Character> dropTargetList = _dropHateList.toTargetArrayList();
		ArrayList<Integer> dropHateList = _dropHateList.toHateArrayList();
		try {
			int npcId = getNpcTemplate().get_npcId();
			if ((npcId != 45640) // 梦幻岛 独角兽
					|| ((npcId == 45640) && (getTempCharGfx() == 2332))) {
				DropTable.getInstance().dropShare(L1MonsterInstance.this, dropTargetList, dropHateList);
			}
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	/** 给予友好度 */
	private void giveKarma(L1PcInstance pc) {
		int karma = getKarma();
		if (karma != 0) {
			int karmaSign = Integer.signum(karma);
			int pcKarmaLevel = pc.getKarmaLevel();
			int pcKarmaLevelSign = Integer.signum(pcKarmaLevel);
			// カルマ背信行为は5倍
			if ((pcKarmaLevelSign != 0) && (karmaSign != pcKarmaLevelSign)) {
				karma *= 5;
			}
			// カルマは止めを刺したプレイヤーに设定。ペットorサモンで倒した场合も入る。
			pc.addKarma((int) (karma * Config.RATE_KARMA));
		}
	}

	/** 给予UB勋章 */
	private void giveUbSeal() {
		if (getUbSealCount() != 0) { // UB勇者的勋章
			L1UltimateBattle ub = UBTable.getInstance().getUb(getUbId());
			if (ub != null) {
				for (L1PcInstance pc : ub.getMembersArray()) {
					if ((pc != null) && !pc.isDead() && !pc.isGhost()) {
						L1ItemInstance item = pc.getInventory().storeItem(41402, getUbSealCount()); // 勇者的勋章
						pc.sendPackets(new S_ServerMessage(403, item.getLogName())); // 获得%0%o 。
					}
				}
			}
		}
	}

	public boolean is_storeDroped() {
		return _storeDroped;
	}

	public void set_storeDroped(boolean flag) {
		_storeDroped = flag;
	}

	private int _ubSealCount = 0; // UBで倒された时、给予参加者勇者之证的个数

	public int getUbSealCount() {
		return _ubSealCount;
	}

	public void setUbSealCount(int i) {
		_ubSealCount = i;
	}

	private int _ubId = 0; // UBID

	public int getUbId() {
		return _ubId;
	}

	public void setUbId(int i) {
		_ubId = i;
	}

	/** 隐藏动作 */
	private void hide() {
		int npcid = getNpcTemplate().get_npcId();
		if ((npcid == 45061) // 弱化史巴托
				|| (npcid == 45161) // 史巴托 (普通)
				|| (npcid == 45181) // 史巴托 (SC)
				|| (npcid == 45455)) { // 残暴的史巴托
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = Random.nextInt(10);
				if (2 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_SINK);
					broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Hide));
					setStatus(11);
					broadcastPacket(new S_CharVisualUpdate(this, getStatus()));
				}
			}
		}
		else if (npcid == 45682) { // 安塔瑞斯
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = Random.nextInt(50);
				if (1 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_SINK);
					broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_AntharasHide));
					setStatus(20);
					broadcastPacket(new S_CharVisualUpdate(this, getStatus()));
				}
			}
		}
		else if ((npcid == 45067) // 弱化哈维 (新手村庄)
				|| (npcid == 45264) // 哈维 (普通)
				|| (npcid == 45452) // 哈维 (遗忘之岛)
				|| (npcid == 45090) // 弱化格利芬 (新手村庄)
				|| (npcid == 45321) // 格利芬 (普通)
				|| (npcid == 45445)) { // 格利芬 (遗忘之岛)
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = Random.nextInt(10);
				if (2 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_FLY);
					broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Moveup));
				}
			}
		}
		else if (npcid == 45681) { // 林德拜尔
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = Random.nextInt(50);
				if (1 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_FLY);
					broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Moveup));
				}
			}
		}
		else if ((npcid == 46107) // 底比斯 曼陀罗草(白)
				|| (npcid == 46108)) { // 底比斯 曼陀罗草(黑)
			if (getMaxHp() / 4 > getCurrentHp()) {
				int rnd = Random.nextInt(10);
				if (2 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_SINK);
					broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Hide));
					setStatus(11);
					broadcastPacket(new S_CharVisualUpdate(this, getStatus()));
				}
			}
		}
	}

	/** 初始化隐藏动作 */
	public void initHide() {
		// 出现的隐藏动作
		// MOB有一定概率钻入地下或飞到天上
		int npcid = getNpcTemplate().get_npcId();
		if ((npcid == 45061) // 弱化史巴托
				|| (npcid == 45161) // 弱化史巴托
				|| (npcid == 45181) // 史巴托 (SC)
				|| (npcid == 45455)) { // 残暴的史巴托
			int rnd = Random.nextInt(3);
			if (1 > rnd) {
				setHiddenStatus(HIDDEN_STATUS_SINK);
				setStatus(11);
			}
		}
		else if ((npcid == 45045) // 弱化石头高仑
				|| (npcid == 45126) // 石头高仑 (普通)
				|| (npcid == 45134) // 石头高仑 (SC)
				|| (npcid == 45281)) { // 奇岩 石头高仑
			int rnd = Random.nextInt(3);
			if (1 > rnd) {
				setHiddenStatus(HIDDEN_STATUS_SINK);
				setStatus(4);
			}
		}
		else if ((npcid == 45067) // 弱化哈维 (新手村庄)
				|| (npcid == 45264) // 哈维 (普通)
				|| (npcid == 45452) // 哈维 (遗忘之岛)
				|| (npcid == 45090) // 弱化格利芬 (新手村庄)
				|| (npcid == 45321) // 格利芬 (普通)
				|| (npcid == 45445)) { // 格利芬 (遗忘之岛)
			setHiddenStatus(HIDDEN_STATUS_FLY);
		}
		else if (npcid == 45681) { // 林德拜尔
			setHiddenStatus(HIDDEN_STATUS_FLY);
		}
		else if ((npcid == 46107) // 底比斯 曼陀罗草 (白)
				|| (npcid == 46108)) { // 底比斯 曼陀罗草 (黑)
			int rnd = Random.nextInt(3);
			if (1 > rnd) {
				setHiddenStatus(HIDDEN_STATUS_SINK);
				setStatus(11);
			}
		}
		else if ((npcid >= 46125) && (npcid <= 46128)) {
			setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_ICE);
			setStatus(4);
		}
	}

	public void initHideForMinion(L1NpcInstance leader) {
		// グループに属するモンスターの出现直后の隐れる动作（リーダーと同じ动作にする）
		int npcid = getNpcTemplate().get_npcId();
		if (leader.getHiddenStatus() == HIDDEN_STATUS_SINK) {
			if ((npcid == 45061) // 弱化史巴托
					|| (npcid == 45161) // 史巴托 (普通)
					|| (npcid == 45181) // 史巴托 (SC)
					|| (npcid == 45455)) { // 残暴的史巴托
				setHiddenStatus(HIDDEN_STATUS_SINK);
				setStatus(11);
			}
			else if ((npcid == 45045) // 弱化石头高仑
					|| (npcid == 45126) // 石头高仑 (普通)
					|| (npcid == 45134) // 石头高仑 (SC)
					|| (npcid == 45281)) { // 奇岩 石头高仑
				setHiddenStatus(HIDDEN_STATUS_SINK);
				setStatus(4);
			}
			else if ((npcid == 46107) // 底比斯 曼陀罗草 (白)
					|| (npcid == 46108)) { // 底比斯 曼陀罗草 (黑)
				setHiddenStatus(HIDDEN_STATUS_SINK);
				setStatus(11);
			}
		}
		else if (leader.getHiddenStatus() == HIDDEN_STATUS_FLY) {
			if ((npcid == 45067) // 弱化哈维 (新手村庄)
					|| (npcid == 45264) // 哈维 (普通)
					|| (npcid == 45452) // 哈维 (遗忘之岛)
					|| (npcid == 45090) // 弱化格利芬 (新手村庄)
					|| (npcid == 45321) // 格利芬 (普通)
					|| (npcid == 45445)) { // 格利芬 (遗忘之岛)
				setHiddenStatus(HIDDEN_STATUS_FLY);
				setStatus(4);
			}
			else if (npcid == 45681) { // 林德拜尔
				setHiddenStatus(HIDDEN_STATUS_FLY);
				setStatus(11);
			}
		}
		else if ((npcid >= 46125) && (npcid <= 46128)) {
			setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_ICE);
			setStatus(4);
		}
	}

	@Override
	protected void transform(int transformId) {
		super.transform(transformId);

		// DROPの再设定
		getInventory().clearItems();
		DropTable.getInstance().setDrop(this, getInventory());
		getInventory().shuffle();
	}

	private boolean _nextDragonStepRunning = false;

	protected void setNextDragonStepRunning(boolean nextDragonStepRunning) {
		_nextDragonStepRunning = nextDragonStepRunning;
	}

	protected boolean isNextDragonStepRunning() {
		return _nextDragonStepRunning;
	}

	/** 龙之血痕 */
	private void bloodstain() {
		for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(this, 50)) {
			if (getNpcTemplate().get_npcId() == 97008) { // 安塔瑞斯 第三阶段
				pc.sendPackets(new S_ServerMessage(1580)); // 安塔瑞斯：黑暗的诅咒将会降临到你们身上！席琳，
															// 我的母亲，请让我安息吧...
				L1BuffUtil.bloodstain(pc, (byte) 0, 4320, true);
			}
			else if (getNpcTemplate().get_npcId() == 97046) { // 法利昂 第三阶段
				pc.sendPackets(new S_ServerMessage(1668)); // 法利昂：莎尔...你这个家伙...怎么...对得起我的母亲...席琳啊...请拿走...我的生命吧...
				L1BuffUtil.bloodstain(pc, (byte) 1, 4320, true);
			}
		}
	}

	/** 下一阶段的龙 */
	private void doNextDragonStep(L1Character attacker, int npcid) {
		if (!isNextDragonStepRunning()) {
			int[] dragonId = { 97006, // 安塔瑞斯 第一阶段
					97007, // 安塔瑞斯 第二阶段
					97044, // 法利昂 第一阶段
					97045 // 法利昂 第二阶段
			};
			int[] nextStepId = { 97007, // 安塔瑞斯 第二阶段
					97008, // 安塔瑞斯 第三阶段
					97045, // 法利昂 第二阶段
					97046 // 法利昂 第三阶段
			};
			int nextSpawnId = 0;
			for (int i = 0; i < dragonId.length; i++) {
				if (npcid == dragonId[i]) {
					nextSpawnId = nextStepId[i];
					break;
				}
			}
			if (attacker != null && nextSpawnId > 0) {
				L1PcInstance _pc = null;
				if (attacker instanceof L1PcInstance) {
					_pc = (L1PcInstance) attacker;
				}
				else if (attacker instanceof L1PetInstance) {
					L1PetInstance pet = (L1PetInstance) attacker;
					L1Character cha = pet.getMaster();
					if (cha instanceof L1PcInstance) {
						_pc = (L1PcInstance) cha;
					}
				}
				else if (attacker instanceof L1SummonInstance) {
					L1SummonInstance summon = (L1SummonInstance) attacker;
					L1Character cha = summon.getMaster();
					if (cha instanceof L1PcInstance) {
						_pc = (L1PcInstance) cha;
					}
				}
				if (_pc != null) {
					NextDragonStep nextDragonStep = new NextDragonStep(_pc, this, nextSpawnId);
					GeneralThreadPool.getInstance().execute(nextDragonStep);
				}
			}
		}
	}

	class NextDragonStep implements Runnable {
		L1PcInstance _pc;
		L1MonsterInstance _mob;
		int _npcid;
		int _transformId;
		int _x;
		int _y;
		int _h;
		short _m;
		L1Location _loc = new L1Location();

		public NextDragonStep(L1PcInstance pc, L1MonsterInstance mob, int transformId) {
			_pc = pc;
			_mob = mob;
			_transformId = transformId;
			_x = mob.getX();
			_y = mob.getY();
			_h = mob.getHeading();
			_m = mob.getMapId();
			_loc = mob.getLocation();
		}

		@Override
		public void run() {
			setNextDragonStepRunning(true);
			try {
				Thread.sleep(10500);
				L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(_transformId);
				npc.setId(IdFactory.getInstance().nextId());
				npc.setMap(_m);
				npc.setHomeX(_x);
				npc.setHomeY(_y);
				npc.setHeading(_h);
				npc.getLocation().set(_loc);
				npc.getLocation().forward(_h);
				npc.setPortalNumber(getPortalNumber());

				broadcastPacket(new S_NPCPack(npc));
				broadcastPacket(new S_DoActionGFX(npc.getId(), ActionCodes.ACTION_Hide));

				L1World.getInstance().storeObject(npc);
				L1World.getInstance().addVisibleObject(npc);
				npc.turnOnOffLight();
				npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 开始喊话
				setNextDragonStepRunning(false);
			}
			catch (InterruptedException e) {
			}
		}
	}
}
