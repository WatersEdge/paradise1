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
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_Door;
import l1j.server.server.serverpackets.S_DoorPack;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.templates.L1DoorGfx;
import l1j.server.server.templates.L1Npc;

/**
 * 门控制项
 */
public class L1DoorInstance extends L1NpcInstance {

	private static final long serialVersionUID = 1L;

	private static final int DOOR_NPC_ID = 81158;

	private int _doorId = 0;

	private int _direction = 0; // ドアの向き

	private int _leftEdgeLocation = 0; // ドアの左端の座標(ドアの向きからX軸orY軸を決定する)

	private int _rightEdgeLocation = 0; // ドアの右端の座標(ドアの向きからX軸orY軸を決定する)

	private int _openStatus = ActionCodes.ACTION_Close;

	private int _keeperId = 0;

	public L1DoorInstance(final int doorId, final L1DoorGfx gfx, final L1Location loc, final int hp, final int keeper, final boolean isOpening) {
		super(NpcTable.getInstance().getTemplate(DOOR_NPC_ID));
		setDoorId(doorId);
		setMaxHp(hp);
		setCurrentHp(hp);
		setGfxId(gfx.getGfxId());
		setLocation(loc);
		setHomeX(loc.getX());
		setHomeY(loc.getY());
		setDirection(gfx.getDirection());
		final int baseLoc = gfx.getDirection() == 0 ? loc.getX() : loc.getY();
		setLeftEdgeLocation(baseLoc + gfx.getLeftEdgeOffset());
		setRightEdgeLocation(baseLoc + gfx.getRightEdgeOffset());
		setKeeperId(keeper);
		if (isOpening) {
			open();
		}
	}

	public L1DoorInstance(final L1Npc template) {
		super(template);
	}

	public void close() {
		if (isDead() || (getOpenStatus() == ActionCodes.ACTION_Close)) {
			return;
		}

		setOpenStatus(ActionCodes.ACTION_Close);
		broadcastPacket(new S_DoorPack(this));
		broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Close));
		sendDoorPacket(null);
	}

	@Override
	public void deleteMe() {
		setDead(true);
		sendDoorPacket(null);

		_destroyed = true;
		if (getInventory() != null) {
			getInventory().clearItems();
		}
		allTargetClear();
		_master = null;
		L1World.getInstance().removeVisibleObject(this);
		L1World.getInstance().removeObject(this);
		for (final L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.removeKnownObject(this);
			pc.sendPackets(new S_RemoveObject(this));
		}
		removeAllKnownObjects();
	}

	public int getDirection() {
		return _direction;
	}

	public int getDoorId() {
		return _doorId;
	}

	public int getEntranceX() {
		int entranceX = 0;
		if (getDirection() == 0) { // ／向き
			entranceX = getX();
		}
		else { // ＼向き
			entranceX = getX() - 1;
		}
		return entranceX;
	}

	public int getEntranceY() {
		int entranceY = 0;
		if (getDirection() == 0) { // ／向き
			entranceY = getY() + 1;
		}
		else { // ＼向き
			entranceY = getY();
		}
		return entranceY;
	}

	public int getKeeperId() {
		return _keeperId;
	}

	public int getLeftEdgeLocation() {
		return _leftEdgeLocation;
	}

	public int getOpenStatus() {
		return _openStatus;
	}

	public int getRightEdgeLocation() {
		return _rightEdgeLocation;
	}

	@Override
	public void onAction(final L1PcInstance pc) {
		if (getMaxHp() == 0) { // 破壊不可能なドアは対象外
			return;
		}

		if ((getCurrentHp() > 0) && !isDead()) {
			final L1Attack attack = new L1Attack(pc, this);
			if (attack.calcHit()) {
				attack.calcDamage();
				attack.addPcPoisonAttack(pc, this);
				attack.addChaserAttack();
			}
			attack.action();
			attack.commit();
		}
	}

	@Override
	public void onPerceive(final L1PcInstance perceivedFrom) {
		perceivedFrom.addKnownObject(this);
		perceivedFrom.sendPackets(new S_DoorPack(this));
		sendDoorPacket(perceivedFrom);
	}

	public void open() {
		if (isDead() || (getOpenStatus() == ActionCodes.ACTION_Open)) {
			return;
		}

		setOpenStatus(ActionCodes.ACTION_Open);
		broadcastPacket(new S_DoorPack(this));
		broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_Open));
		sendDoorPacket(null);
	}

	@Override
	public void receiveDamage(final L1Character attacker, final int damage) {
		if (getMaxHp() == 0) { // 破壊不可能なドアは対象外
			return;
		}
		if ((getCurrentHp() <= 0) || isDead()) {
			return;
		}

		final int newHp = getCurrentHp() - damage;
		if ((newHp <= 0) && !isDead()) {
			die();
			return;
		}

		setCurrentHpDirect(newHp);
		updateStatus();
	}

	public void repairGate() {
		if (getMaxHp() <= 1) {
			return;
		}

		setDead(false);
		setCurrentHp(getMaxHp());
		setStatus(0);
		setOpenStatus(ActionCodes.ACTION_Open);
		close();
	}

	public void sendDoorPacket(final L1PcInstance pc) {
		final int entranceX = getEntranceX();
		final int entranceY = getEntranceY();
		final int leftEdgeLocation = getLeftEdgeLocation();
		final int rightEdgeLocation = getRightEdgeLocation();

		final int size = rightEdgeLocation - leftEdgeLocation;
		if (size == 0) { // 1マス分の幅のドア
			sendPacket(pc, entranceX, entranceY);
		}
		else { // 2マス分以上の幅があるドア
			if (getDirection() == 0) { // ／向き
				for (int x = leftEdgeLocation; x <= rightEdgeLocation; x++) {
					sendPacket(pc, x, entranceY);
				}
			}
			else { // ＼向き
				for (int y = leftEdgeLocation; y <= rightEdgeLocation; y++) {
					sendPacket(pc, entranceX, y);
				}
			}
		}
	}

	@Override
	public void setCurrentHp(final int i) {
		int currentHp = i;
		if (currentHp >= getMaxHp()) {
			currentHp = getMaxHp();
		}
		setCurrentHpDirect(currentHp);
	}

	public void setDirection(final int i) {
		if ((i != 0) && (i != 1)) {
			throw new IllegalArgumentException();
		}
		_direction = i;
	}

	public void setDoorId(final int i) {
		_doorId = i;
	}

	public void setKeeperId(final int i) {
		_keeperId = i;
	}

	public void setLeftEdgeLocation(final int i) {
		_leftEdgeLocation = i;
	}

	public void setRightEdgeLocation(final int i) {
		_rightEdgeLocation = i;
	}

	private void die() {
		setCurrentHpDirect(0);
		setDead(true);
		setStatus(ActionCodes.ACTION_DoorDie);

		getMap().setPassable(getLocation(), true);

		broadcastPacket(new S_DoActionGFX(getId(), ActionCodes.ACTION_DoorDie));
		sendDoorPacket(null);
	}

	private boolean isPassable() {
		return isDead() || (getOpenStatus() == ActionCodes.ACTION_Open);
	}

	private void sendPacket(final L1PcInstance pc, final int x, final int y) {
		final S_Door packet = new S_Door(x, y, getDirection(), isPassable());
		if (pc != null) { // onPerceive()経由の場合
			// 開いている場合は通行不可パケット送信不要
			if (getOpenStatus() == ActionCodes.ACTION_Close) {
				pc.sendPackets(packet);
			}
		}
		else {
			broadcastPacket(packet);
		}
	}

	private void setOpenStatus(final int newStatus) {
		if ((newStatus != ActionCodes.ACTION_Open) && (newStatus != ActionCodes.ACTION_Close)) {
			throw new IllegalArgumentException();
		}
		_openStatus = newStatus;
	}

	private void updateStatus() {
		int newStatus = 0;
		if ((getMaxHp() * 1 / 6) > getCurrentHp()) {
			newStatus = ActionCodes.ACTION_DoorAction5;
		}
		else if ((getMaxHp() * 2 / 6) > getCurrentHp()) {
			newStatus = ActionCodes.ACTION_DoorAction4;
		}
		else if ((getMaxHp() * 3 / 6) > getCurrentHp()) {
			newStatus = ActionCodes.ACTION_DoorAction3;
		}
		else if ((getMaxHp() * 4 / 6) > getCurrentHp()) {
			newStatus = ActionCodes.ACTION_DoorAction2;
		}
		else if ((getMaxHp() * 5 / 6) > getCurrentHp()) {
			newStatus = ActionCodes.ACTION_DoorAction1;
		}
		if (getStatus() == newStatus) {
			return;
		}
		setStatus(newStatus);
		broadcastPacket(new S_DoActionGFX(getId(), newStatus));
	}
}
