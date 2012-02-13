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

import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.IdFactory;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_DollPack;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1MagicDoll;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.Random;

/**
 * 魔法娃娃控制项
 */
public class L1DollInstance extends L1NpcInstance {

	// 时间计测用
	class DollTimer implements Runnable {
		@Override
		public void run() {
			if (_destroyed) { // 检查是否已经被抛弃
				return;
			}
			deleteDoll();
		}
	}

	private static final long serialVersionUID = 1L;
	public static final int DOLL_TIME = 1800000;
	private int _itemId;
	private int _itemObjId;
	private int run;

	private boolean _isDelete = false;

	public L1DollInstance(final L1Npc template, final L1PcInstance master, final int itemId, final int itemObjId) {

		super(template);
		setId(IdFactory.getInstance().nextId());

		setItemId(itemId);
		setItemObjId(itemObjId);
		GeneralThreadPool.getInstance().schedule(new DollTimer(), DOLL_TIME);

		setMaster(master);
		setX(master.getX() + Random.nextInt(5) - 2);
		setY(master.getY() + Random.nextInt(5) - 2);
		setMap(master.getMapId());
		setHeading(5);
		setLightSize(template.getLightSize());
		setMoveSpeed(1);
		setBraveSpeed(1);

		L1World.getInstance().storeObject(this);
		L1World.getInstance().addVisibleObject(this);
		for (final L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			onPerceive(pc);
		}
		master.addDoll(this);
		if (!isAiRunning()) {
			startAI();
		}
		if (L1MagicDoll.isHpRegeneration(_master)) {
			master.startHpRegenerationByDoll();
		}
		if (L1MagicDoll.isMpRegeneration(_master)) {
			master.startMpRegenerationByDoll();
		}
		if (L1MagicDoll.isItemMake(_master)) {
			master.startItemMakeByDoll();
		}
	}

	public void deleteDoll() {
		broadcastPacket(new S_SkillSound(getId(), 5936));
		if ((_master != null) && _isDelete) {
			final L1PcInstance pc = (L1PcInstance) _master;
			pc.sendPackets(new S_SkillIconGFX(56, 0));
			pc.sendPackets(new S_OwnCharStatus(pc));
		}
		if (L1MagicDoll.isHpRegeneration(_master)) {
			((L1PcInstance) _master).stopHpRegenerationByDoll();
		}
		if (L1MagicDoll.isMpRegeneration(_master)) {
			((L1PcInstance) _master).stopMpRegenerationByDoll();
		}
		if (L1MagicDoll.isItemMake(_master)) {
			((L1PcInstance) _master).stopItemMakeByDoll();
		}
		_master.getDollList().remove(getId());
		deleteMe();
	}

	public int getItemId() {
		return _itemId;
	}

	public int getItemObjId() {
		return _itemObjId;
	}

	// 如果没有目标处理
	@Override
	public boolean noTarget() {
		if ((_master != null) && !_master.isDead() && (_master.getMapId() == getMapId())) {
			if (getLocation().getTileLineDistance(_master.getLocation()) > 2) {
				final int dir = moveDirection(_master.getX(), _master.getY());
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
			}
			else {
				// 魔法娃娃 - 特殊动作
				dollAction();
			}
		}
		else {
			_isDelete = true;
			deleteDoll();
			return true;
		}
		return false;
	}

	@Override
	public void onGetItem(final L1ItemInstance item) {
	}

	@Override
	public void onItemUse() {
	}

	@Override
	public void onPerceive(final L1PcInstance perceivedFrom) {
		// 判断旅馆内是否使用相同钥匙
		if ((perceivedFrom.getMapId() >= 16384) && (perceivedFrom.getMapId() <= 25088 // 旅馆内判断
				) && (perceivedFrom.getInnKeyId() != _master.getInnKeyId())) {
			return;
		}
		perceivedFrom.addKnownObject(this);
		perceivedFrom.sendPackets(new S_DollPack(this));
	}

	public void setItemId(final int i) {
		_itemId = i;
	}

	public void setItemObjId(final int i) {
		_itemObjId = i;
	}

	// 表情动作
	private void dollAction() {
		run = Random.nextInt(100) + 1;
		if (run <= 10) {
			int actionCode = ActionCodes.ACTION_Aggress; // 67
			if (run <= 5) {
				actionCode = ActionCodes.ACTION_Think; // 66
			}

			broadcastPacket(new S_DoActionGFX(getId(), actionCode));
			setSleepTime(calcSleepTime(SprTable.getInstance().getSprSpeed(getTempCharGfx(), actionCode), MOVE_SPEED)); //
		}
	}
}
