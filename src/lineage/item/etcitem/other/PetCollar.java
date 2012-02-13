package lineage.item.etcitem.other;

import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import lineage.console.executor.ItemExecutor;

/**
 * 项圈 - 40314 <br>
 * 项圈 - 40316 <br>
 * 
 * @author jrwz
 */
public class PetCollar extends ItemExecutor {

	public static ItemExecutor get() {
		return new PetCollar();
	}

	private PetCollar() {
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

		if (pc.getInventory().checkItem(41160)) { // 宠物召唤笛
			if (withdrawPet(pc, item.getId())) {
				pc.getInventory().consumeItem(41160, 1);
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
	}

	private boolean withdrawPet(final L1PcInstance pc, final int itemObjectId) {

		// 不能携带宠物的地图
		if (!pc.getMap().isTakePets()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1你无法在这个地方使用。
			return false;
		}

		int petCost = 0;
		for (L1NpcInstance petNpc : pc.getPetList().values()) {
			if (petNpc instanceof L1PetInstance) {
				if (((L1PetInstance) petNpc).getItemObjId() == itemObjectId) { // 既に引き出しているペット
					return false;
				}
			}
			petCost += petNpc.getPetcost();
		}

		// 取回角色的魅力值
		int charisma = pc.getCha();
		if (pc.isCrown()) { // 王族
			charisma += 6;
		}
		else if (pc.isElf()) { // 精灵
			charisma += 12;
		}
		else if (pc.isWizard()) { // 法师
			charisma += 6;
		}
		else if (pc.isDarkelf()) { // 黑暗精灵
			charisma += 6;
		}
		else if (pc.isDragonKnight()) { // 龙骑士
			charisma += 6;
		}
		else if (pc.isIllusionist()) { // 幻术师
			charisma += 6;
		}
		charisma -= petCost;
		final int petCount = charisma / 6;

		// 超过可携带数量
		if (petCount <= 0) {
			pc.sendPackets(new S_ServerMessage(489)); // 你无法一次控制那么多宠物。
			return false;
		}

		final L1Pet l1pet = PetTable.getInstance().getTemplate(itemObjectId);
		if (l1pet != null) {
			final L1Npc npcTemp = NpcTable.getInstance().getTemplate(l1pet.get_npcid());
			final L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
			pet.setPetcost(6);
		}
		return true;
	}
}
