package lineage.item.etcitem.other;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.IdFactory;
import l1j.server.server.clientpackets.C_ItemUSe;
import l1j.server.server.datatables.FurnitureSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.L1HouseLocation;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FurnitureInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Npc;
import lineage.console.executor.ItemExecutor;

/**
 * 家具 (41383 - 41400)
 * 
 * @author jrwz
 */
public class Furniture extends ItemExecutor {

	private static Logger _log = Logger.getLogger(C_ItemUSe.class.getName());

	private Furniture() {
	}

	public static ItemExecutor get() {
		return new Furniture();
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

		final int itemObjid = item.getId();
		final int itemId = item.getItemId();
		useFurnitureItem(pc, itemId, itemObjid);
	}

	private void useFurnitureItem(final L1PcInstance pc, final int itemId, final int itemObjectId) {

		// 不能使用家具的地图
		if (!L1HouseLocation.isInHouse(pc.getX(), pc.getY(), pc.getMapId())) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1你无法在这个地方使用。
			return;
		}

		boolean isAppear = true;
		L1FurnitureInstance furniture = null;
		for (L1Object l1object : L1World.getInstance().getObject()) {
			if (l1object instanceof L1FurnitureInstance) {
				furniture = (L1FurnitureInstance) l1object;
				if (furniture.getItemObjId() == itemObjectId) {
					isAppear = false;
					break;
				}
			}
		}

		if (isAppear) {
			if ((pc.getHeading() != 0) && (pc.getHeading() != 2)) {
				return;
			}
			int npcId = 0;
			if (itemId == 41383) { // 巨大兵蚁标本
				npcId = 80109;
			}
			else if (itemId == 41384) { // 熊标本
				npcId = 80110;
			}
			else if (itemId == 41385) { // 蛇女标本
				npcId = 80113;
			}
			else if (itemId == 41386) { // 黑虎标本
				npcId = 80114;
			}
			else if (itemId == 41387) { // 鹿标本
				npcId = 80115;
			}
			else if (itemId == 41388) { // 哈维标本
				npcId = 80124;
			}
			else if (itemId == 41389) { // 青铜骑士
				npcId = 80118;
			}
			else if (itemId == 41390) { // 青铜马
				npcId = 80119;
			}
			else if (itemId == 41391) { // 烛台
				npcId = 80120;
			}
			else if (itemId == 41392) { // 茶几
				npcId = 80121;
			}
			else if (itemId == 41393) { // 火炉
				npcId = 80126;
			}
			else if (itemId == 41394) { // 火把
				npcId = 80125;
			}
			else if (itemId == 41395) { // 君主用讲台
				npcId = 80111;
			}
			else if (itemId == 41396) { // 旗帜
				npcId = 80112;
			}
			else if (itemId == 41397) { // 茶几椅子(右)
				npcId = 80116;
			}
			else if (itemId == 41398) { // 茶几椅子(左)
				npcId = 80117;
			}
			else if (itemId == 41399) { // 屏风(右)
				npcId = 80122;
			}
			else if (itemId == 41400) { // 屏风(左)
				npcId = 80123;
			}

			try {
				final L1Npc l1npc = NpcTable.getInstance().getTemplate(npcId);
				if (l1npc != null) {
					try {
						String s = l1npc.getImpl();
						Constructor<?> constructor = Class.forName("l1j.server.server.model.Instance." + s + "Instance").getConstructors()[0];
						Object aobj[] = { l1npc };
						furniture = (L1FurnitureInstance) constructor.newInstance(aobj);
						furniture.setId(IdFactory.getInstance().nextId());
						furniture.setMap(pc.getMapId());
						if (pc.getHeading() == 0) {
							furniture.setX(pc.getX());
							furniture.setY(pc.getY() - 1);
						}
						else if (pc.getHeading() == 2) {
							furniture.setX(pc.getX() + 1);
							furniture.setY(pc.getY());
						}
						furniture.setHomeX(furniture.getX());
						furniture.setHomeY(furniture.getY());
						furniture.setHeading(0);
						furniture.setItemObjId(itemObjectId);

						L1World.getInstance().storeObject(furniture);
						L1World.getInstance().addVisibleObject(furniture);
						FurnitureSpawnTable.getInstance().insertFurniture(furniture);
					}
					catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
			}
			catch (Exception exception) {
			}
		}
		else {
			furniture.deleteMe();
			FurnitureSpawnTable.getInstance().deleteFurniture(furniture);
		}
	}
}
