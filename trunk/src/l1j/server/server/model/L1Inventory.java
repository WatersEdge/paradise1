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

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import l1j.server.Config;
import l1j.server.server.IdFactory;
import l1j.server.server.datatables.FurnitureSpawnTable;
import l1j.server.server.datatables.InnKeyTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.LetterTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.RaceTicketTable;
import l1j.server.server.model.Instance.L1FurnitureInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1RaceTicket;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.collections.Lists;

/**
 * 背包道具
 */
public class L1Inventory extends L1Object {

	private static final long serialVersionUID = 1L;

	protected List<L1ItemInstance> _items = Lists.newConcurrentList();

	public static final int MAX_AMOUNT = 2000000000; // 2G

	public static final int MAX_WEIGHT = 1500;

	public L1Inventory() {
		//
	}

	/** 背包内的道具总数 */
	public int getSize() {
		return _items.size();
	}

	/** 背包内的全部道具 */
	public List<L1ItemInstance> getItems() {
		return _items;
	}

	/** 背包内的总重量 */
	public int getWeight() {
		int weight = 0;

		for (L1ItemInstance item : _items) {
			weight += item.getWeight();
		}

		return weight;
	}

	// 确认增加道具的参数、检查承重能力
	public static final int OK = 0;

	public static final int SIZE_OVER = 1;

	public static final int WEIGHT_OVER = 2;

	public static final int AMOUNT_OVER = 3;

	/** 检查增加道具 */
	public int checkAddItem(L1ItemInstance item, int count) {
		if (item == null) {
			return -1;
		}
		if ((item.getCount() <= 0) || (count <= 0)) {
			return -1;
		}
		if ((getSize() > Config.MAX_NPC_ITEM)
				|| ((getSize() == Config.MAX_NPC_ITEM) && (!item.isStackable() || !checkItem(item
						.getItem().getItemId())))) { // 容量确认
			return SIZE_OVER;
		}

		int weight = getWeight() + item.getItem().getWeight() * count / 1000
				+ 1;
		if ((weight < 0) || ((item.getItem().getWeight() * count / 1000) < 0)) {
			return WEIGHT_OVER;
		}
		if (weight > (MAX_WEIGHT * Config.RATE_WEIGHT_LIMIT_PET)) { // 其他重量检查（主要是召唤和宠物）
			return WEIGHT_OVER;
		}

		L1ItemInstance itemExist = findItemId(item.getItemId());
		if ((itemExist != null)
				&& ((itemExist.getCount() + count) > MAX_AMOUNT)) {
			return AMOUNT_OVER;
		}

		return OK;
	}

	// 确认增加道具的参数、检查仓库容量
	/** 个人仓库 */
	public static final int WAREHOUSE_TYPE_PERSONAL = 0;
	/** 血盟仓库 */
	public static final int WAREHOUSE_TYPE_CLAN = 1;

	/** 检查增加道具(仓库) */
	public int checkAddItemToWarehouse(L1ItemInstance item, int count, int type) {
		if (item == null) {
			return -1;
		}
		if ((item.getCount() <= 0) || (count <= 0)) {
			return -1;
		}

		int maxSize = 100;
		if (type == WAREHOUSE_TYPE_PERSONAL) {
			maxSize = Config.MAX_PERSONAL_WAREHOUSE_ITEM;
		} else if (type == WAREHOUSE_TYPE_CLAN) {
			maxSize = Config.MAX_CLAN_WAREHOUSE_ITEM;
		}
		if ((getSize() > maxSize)
				|| ((getSize() == maxSize) && (!item.isStackable() || !checkItem(item
						.getItem().getItemId())))) { // 容量确认
			return SIZE_OVER;
		}

		return OK;
	}

	/** 储存新道具 */
	public synchronized L1ItemInstance storeItem(int id, int count) {
		if (count <= 0) {
			return null;
		}
		L1Item temp = ItemTable.getInstance().getTemplate(id);
		if (temp == null) {
			return null;
		}

		if (id == 40312) { // 旅馆钥匙
			L1ItemInstance item = new L1ItemInstance(temp, count);

			if (findKeyId(id) == null) { // 新しく生成する必要がある場合のみIDの発行とL1Worldへの登録を行う
				item.setId(IdFactory.getInstance().nextId());
				L1World.getInstance().storeObject(item);
			}

			return storeItem(item);
		}
		else if (temp.isStackable()) {
			L1ItemInstance item = new L1ItemInstance(temp, count);

			if (findItemId(id) == null) { // 新しく生成する必要がある場合のみIDの発行とL1Worldへの登録を行う
				item.setId(IdFactory.getInstance().nextId());
				L1World.getInstance().storeObject(item);
			}

			return storeItem(item);
		}

		// 该道具能不能叠加
		L1ItemInstance result = null;
		for (int i = 0; i < count; i++) {
			L1ItemInstance item = new L1ItemInstance(temp, 1);
			item.setId(IdFactory.getInstance().nextId());
			L1World.getInstance().storeObject(item);
			storeItem(item);
			result = item;
		}
		// 返回最后(最近一次)的道具。改变可能会更好的定义方法，返回一个数组。
		return result;
	}

	/** DROP、购买、在GM命令可用存储新项目 */
	public synchronized L1ItemInstance storeItem(L1ItemInstance item) {
		if (item.getCount() <= 0) {
			return null;
		}
		int itemId = item.getItem().getItemId();
		if (item.isStackable()) {
			L1ItemInstance findItem = findItemId(itemId);
			if (itemId == 40309) { // Race Tickets
				findItem = findItemNameId(item.getItem().getIdentifiedNameId());
			} else if (itemId == 40312) { // 旅館鑰匙
				findItem = findKeyId(itemId);
			} else {
				findItem = findItemId(itemId);
			}
			if (findItem != null) {
				findItem.setCount(findItem.getCount() + item.getCount());
				updateItem(findItem);
				return findItem;
			}
		}

		if (itemId == 40309) {// Race Tickets
			String[] temp = item.getItem().getIdentifiedNameId().split(" ");
			temp=temp[temp.length-1].split("-");
			L1RaceTicket ticket = new L1RaceTicket();
			ticket.set_itemobjid(item.getId());
			ticket.set_round(Integer.parseInt(temp[0]));
			ticket.set_allotment_percentage(0.0);
			ticket.set_victory(0);
			ticket.set_runner_num(Integer.parseInt(temp[1]));
			RaceTicketTable.getInstance().storeNewTiket(ticket);
		}
		item.setX(getX());
		item.setY(getY());
		item.setMap(getMapId());
		int chargeCount = item.getItem().getMaxChargeCount();
		if ((itemId == 40006) || (itemId == 40007) || (itemId == 40008)
				|| (itemId == 140006) || (itemId == 140008)
				|| (itemId == 41401)) {
			chargeCount -= Random.nextInt(5);
		}
		if (itemId == 20383) {
			chargeCount = 50;
		}
		item.setChargeCount(chargeCount);
		if ((item.getItem().getType2() == 0) && (item.getItem().getType() == 2)) { // light系列
			item.setRemainingTime(item.getItem().getLightFuel());
		} else {
			item.setRemainingTime(item.getItem().getMaxUseTime());
		}
		item.setBless(item.getItem().getBless());
		// 登入钥匙记录
		if (item.getItem().getItemId() == 40312) {
			if (!InnKeyTable.checkey(item)) {
				InnKeyTable.StoreKey(item);
			}
		}
		_items.add(item);
		insertItem(item);
		return item;
	}

	/** /trade、存储从仓库取得道具 */
	public synchronized L1ItemInstance storeTradeItem(L1ItemInstance item) {
		if (item.getItem().getItemId() == 40312) { // 旅馆钥匙
			L1ItemInstance findItem = findKeyId(item.getKeyId()); // 检查钥匙编号是否相同
			if (findItem != null) {
				findItem.setCount(findItem.getCount() + item.getCount());
				updateItem(findItem);
				return findItem;
			}
		} else if (item.isStackable()) {
			L1ItemInstance findItem = findItemId(item.getItem().getItemId());
			if (findItem != null) {
				findItem.setCount(findItem.getCount() + item.getCount());
				updateItem(findItem);
				return findItem;
			}
		}
		item.setX(getX());
		item.setY(getY());
		item.setMap(getMapId());
		// 登入钥匙记录
		if (item.getItem().getItemId() == 40312) {
			if (!InnKeyTable.checkey(item)) {
				InnKeyTable.StoreKey(item);
			}
		}
		_items.add(item);
		insertItem(item);
		return item;
	}

	/**
	 * 从背包中删除指定ID的项目的一个项目。L1ItemInstanceへの参考
	 * がある場合はremoveItemの方を使用するのがよい。 （こちらは矢とか魔石とか特定のアイテムを消費させるときに使う）
	 * 
	 * @param itemid
	 *            - 删除该道具的itemid(不是objid)
	 * @param count
	 *            - 删除的数量
	 * @return 返回 如果实际删除true。
	 */
	public boolean consumeItem(int itemid, int count) {
		if (count <= 0) {
			return false;
		}
		if (ItemTable.getInstance().getTemplate(itemid).isStackable()) {
			L1ItemInstance item = findItemId(itemid);
			if ((item != null) && (item.getCount() >= count)) {
				removeItem(item, count);
				return true;
			}
		} else {
			L1ItemInstance[] itemList = findItemsId(itemid);
			if (itemList.length == count) {
				for (int i = 0; i < count; i++) {
					removeItem(itemList[i], 1);
				}
				return true;
			} else if (itemList.length > count) { // 所持有的数量超过指定数量
				DataComparator<L1ItemInstance> dc = new DataComparator<L1ItemInstance>();
				Arrays.sort(itemList, dc); // エンチャント順にソートし、エンチャント数の少ないものから消費させる
				for (int i = 0; i < count; i++) {
					removeItem(itemList[i], 1);
				}
				return true;
			}
		}
		return false;
	}

	public class DataComparator<T> implements Comparator<L1ItemInstance> {
		@Override
		public int compare(L1ItemInstance item1, L1ItemInstance item2) {
			return item1.getEnchantLevel() - item2.getEnchantLevel();
		}
	}

	// 删除指定道具的指定数量（使ったりゴミ箱に捨てられたとき）戻り値：实际删除数量
	/** 删除指定道具的指定数量 */
	public int removeItem(int objectId, int count) {
		L1ItemInstance item = getItem(objectId);
		return removeItem(item, count);
	}

	/** 删除指定道具的指定数量 */
	public int removeItem(L1ItemInstance item) {
		return removeItem(item, item.getCount());
	}

	/** 删除指定道具的指定数量 */
	public int removeItem(L1ItemInstance item, int count) {
		if (item == null) {
			return 0;
		}
		if ((item.getCount() <= 0) || (count <= 0)) {
			return 0;
		}
		if (item.getCount() < count) {
			count = item.getCount();
		}
		if (item.getCount() == count) {
			int itemId = item.getItem().getItemId();
			if ((itemId == 40314) || (itemId == 40316)) { // 宠物项圈
				PetTable.getInstance().deletePet(item.getId());
			} else if ((itemId >= 49016) && (itemId <= 49025)) { // 便箋
				LetterTable lettertable = new LetterTable();
				lettertable.deleteLetter(item.getId());
			} else if ((itemId >= 41383) && (itemId <= 41400)) { // 家具
				for (L1Object l1object : L1World.getInstance().getObject()) {
					if (l1object instanceof L1FurnitureInstance) {
						L1FurnitureInstance furniture = (L1FurnitureInstance) l1object;
						if (furniture.getItemObjId() == item.getId()) { // 已经退出了家具
							FurnitureSpawnTable.getInstance().deleteFurniture(
									furniture);
						}
					}
				}
			} else if (item.getItemId() == 40309) {// Race Tickets
				RaceTicketTable.getInstance().deleteTicket(item.getId());
			}
			deleteItem(item);
			L1World.getInstance().removeObject(item);
		} else {
			item.setCount(item.getCount() - count);
			updateItem(item);
		}
		return count;
	}

	// _itemsから指定オブジェクトを削除(L1PcInstance、L1DwarfInstance、L1GroundInstanceでこの部分をオーバライドする)
	public void deleteItem(L1ItemInstance item) {
		// 删除钥匙记录
		if (item.getItem().getItemId() == 40312) {
			InnKeyTable.DeleteKey(item);
		}
		_items.remove(item);
	}

	/** 背包内道具转移的参数 */
	public synchronized L1ItemInstance tradeItem(int objectId, int count,
			L1Inventory inventory) {
		L1ItemInstance item = getItem(objectId);
		return tradeItem(item, count, inventory);
	}

	public synchronized L1ItemInstance tradeItem(L1ItemInstance item,
			int count, L1Inventory inventory) {
		if (item == null) {
			return null;
		}
		if ((item.getCount() <= 0) || (count <= 0)) {
			return null;
		}
		if (item.isEquipped()) {
			return null;
		}
		if (!checkItem(item.getItem().getItemId(), count)) {
			return null;
		}
		L1ItemInstance carryItem;
		if (item.getCount() <= count) {
			deleteItem(item);
			carryItem = item;
		} else {
			item.setCount(item.getCount() - count);
			updateItem(item);
			carryItem = ItemTable.getInstance().createItem(
					item.getItem().getItemId());
			carryItem.setCount(count);
			carryItem.setEnchantLevel(item.getEnchantLevel());
			carryItem.setIdentified(item.isIdentified());
			carryItem.set_durability(item.get_durability());
			carryItem.setChargeCount(item.getChargeCount());
			carryItem.setRemainingTime(item.getRemainingTime());
			carryItem.setLastUsed(item.getLastUsed());
			carryItem.setBless(item.getBless());
			// 旅馆钥匙
			if (carryItem.getItem().getItemId() == 40312) {
				carryItem.setInnNpcId(item.getInnNpcId()); // 旅馆NPC
				carryItem.setKeyId(item.getKeyId()); // 钥匙编号
				carryItem.setHall(item.checkRoomOrHall()); // 房间或会议室
				carryItem.setDueTime(item.getDueTime()); // 租用时间
			}
		}
		return inventory.storeTradeItem(carryItem);
	}

	/**
	 * 如果该道具为损坏的道具・损耗度（包括 武器・防具）、损耗的正负 代表 武器・防具的损伤程度。
	 */
	public L1ItemInstance receiveDamage(int objectId) {
		L1ItemInstance item = getItem(objectId);
		return receiveDamage(item);
	}

	public L1ItemInstance receiveDamage(L1ItemInstance item) {
		return receiveDamage(item, 1);
	}

	public L1ItemInstance receiveDamage(L1ItemInstance item, int count) {
		int itemType = item.getItem().getType2();
		int currentDurability = item.get_durability();

		if (((currentDurability == 0) && (itemType == 0))
				|| (currentDurability < 0)) {
			item.set_durability(0);
			return null;
		}

		// 武器・防具的损耗度
		if (itemType == 0) {
			int minDurability = (item.getEnchantLevel() + 5) * -1;
			int durability = currentDurability - count;
			if (durability < minDurability) {
				durability = minDurability;
			}
			if (currentDurability > durability) {
				item.set_durability(durability);
			}
		} else {
			int maxDurability = item.getEnchantLevel() + 5;
			int durability = currentDurability + count;
			if (durability > maxDurability) {
				durability = maxDurability;
			}
			if (currentDurability < durability) {
				item.set_durability(durability);
			}
		}

		updateItem(item, L1PcInventory.COL_DURABILITY);
		return item;
	}

	public L1ItemInstance recoveryDamage(L1ItemInstance item) {
		if (item == null) {
			return null;
		}

		int itemType = item.getItem().getType2();
		int durability = item.get_durability();

		if (((durability == 0) && (itemType != 0)) || (durability < 0)) {
			item.set_durability(0);
			return null;
		}

		if (itemType == 0) {
			// 增加耐久度。
			item.set_durability(durability + 1);
		} else {
			// 增加损伤度。
			item.set_durability(durability - 1);
		}

		updateItem(item, L1PcInventory.COL_DURABILITY);
		return item;
	}

	/** 搜索物品ID */
	public L1ItemInstance findItemId(int id) {
		for (L1ItemInstance item : _items) {
			if (item.getItem().getItemId() == id) {
				return item;
			}
		}
		return null;
	}

	public L1ItemInstance findKeyId(int id) {
		for (L1ItemInstance item : _items) {
			if (item.getKeyId() == id) {
				return item;
			}
		}
		return null;
	}

	public L1ItemInstance[] findItemsId(int id) {
		List<L1ItemInstance> itemList = Lists.newList();
		for (L1ItemInstance item : _items) {
			if (item.getItemId() == id) {
				itemList.add(item);
			}
		}
		return itemList.toArray(new L1ItemInstance[itemList.size()]);
	}

	public L1ItemInstance[] findItemsIdNotEquipped(int id) {
		List<L1ItemInstance> itemList = Lists.newList();
		for (L1ItemInstance item : _items) {
			if (item.getItemId() == id) {
				if (!item.isEquipped()) {
					itemList.add(item);
				}
			}
		}
		return itemList.toArray(new L1ItemInstance[itemList.size()]);
	}

	/** 搜索对象ID */
	public L1ItemInstance getItem(int objectId) {
		for (Object itemObject : _items) {
			L1ItemInstance item = (L1ItemInstance) itemObject;
			if (item.getId() == objectId) {
				return item;
			}
		}
		return null;
	}

	// 检查所持有的特定道具超过指定的数量（矢とか魔石の確認）
	public boolean checkItem(int id) {
		return checkItem(id, 1);
	}

	public boolean checkItem(int id, int count) {
		if (count == 0) {
			return true;
		}
		if (ItemTable.getInstance().getTemplate(id).isStackable()) {
			L1ItemInstance item = findItemId(id);
			if ((item != null) && (item.getCount() >= count)) {
				return true;
			}
		} else {
			Object[] itemList = findItemsId(id);
			if (itemList.length >= count) {
				return true;
			}
		}
		return false;
	}

	// 強化された特定のアイテムを指定された個数以上所持しているか確認
	// 装備中のアイテムは所持していないと判別する
	public boolean checkEnchantItem(int id, int enchant, int count) {
		int num = 0;
		for (L1ItemInstance item : _items) {
			if (item.isEquipped()) { // 装备不适用
				continue;
			}
			if ((item.getItemId() == id) && (item.getEnchantLevel() == enchant)) {
				num++;
				if (num == count) {
					return true;
				}
			}
		}
		return false;
	}

	// 強化された特定のアイテムを消費する
	// 装備中のアイテムは所持していないと判別する
	public boolean consumeEnchantItem(int id, int enchant, int count) {
		for (L1ItemInstance item : _items) {
			if (item.isEquipped()) { // 装備しているものは該当しない
				continue;
			}
			if ((item.getItemId() == id) && (item.getEnchantLevel() == enchant)) {
				removeItem(item);
				return true;
			}
		}
		return false;
	}

	// 特定のアイテムを指定された個数以上所持しているか確認
	// 装備中のアイテムは所持していないと判別する
	public boolean checkItemNotEquipped(int id, int count) {
		if (count == 0) {
			return true;
		}
		return count <= countItems(id);
	}

	// 特定のアイテムを全て必要な個数所持しているか確認（イベントとかで複数のアイテムを所持しているか確認するため）
	public boolean checkItem(int[] ids) {
		int len = ids.length;
		int[] counts = new int[len];
		for (int i = 0; i < len; i++) {
			counts[i] = 1;
		}
		return checkItem(ids, counts);
	}

	public boolean checkItem(int[] ids, int[] counts) {
		for (int i = 0; i < ids.length; i++) {
			if (!checkItem(ids[i], counts[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * このインベントリ内にある、指定されたIDのアイテムの数を数える。
	 * 
	 * @return
	 */
	public int countItems(int id) {
		if (ItemTable.getInstance().getTemplate(id).isStackable()) {
			L1ItemInstance item = findItemId(id);
			if (item != null) {
				return item.getCount();
			}
		} else {
			Object[] itemList = findItemsIdNotEquipped(id);
			return itemList.length;
		}
		return 0;
	}

	public void shuffle() {
		Collections.shuffle(_items);
	}

	// インベントリ内の全てのアイテムを消す（所有者を消すときなど）
	public void clearItems() {
		for (Object itemObject : _items) {
			L1ItemInstance item = (L1ItemInstance) itemObject;
			L1World.getInstance().removeObject(item);
		}
		_items.clear();
	}

	/**
	 * スタック可能なアイテムリストからnameIdと同じ値を持つitemを返す
	 * 
	 * @param nameId
	 * @return item null 如果沒有找到。
	 */
	public L1ItemInstance findItemNameId(String nameId) {
		for (L1ItemInstance item : _items) {
			if (nameId.equals(item.getItem().getIdentifiedNameId())) {
				return item;
			}
		}
		return null;
	}

	// 用于覆盖
	public void loadItems() {
	}

	public void insertItem(L1ItemInstance item) {
	}

	public void updateItem(L1ItemInstance item) {
	}

	public void updateItem(L1ItemInstance item, int colmn) {
	}

	public void updateEnchantAccessory(L1ItemInstance item, int colmn) {
	}

}
