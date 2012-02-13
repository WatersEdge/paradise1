package lineage.console;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import lineage.console.executor.ItemExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 道具模组相关
 * 
 * @author jrwz
 */
public class ItemClass {

	/** 提示信息 */
	private static final Log _log = LogFactory.getLog(ItemClass.class);

	/** ItemID执行类位置 */
	private static final Map<Integer, ItemExecutor> _classList = new HashMap<Integer, ItemExecutor>();

	private static ItemClass _instance;

	public static ItemClass getInstance() {
		if (_instance == null) {
			_instance = new ItemClass();
		}
		return _instance;
	}

	/**
	 * 加入Class清单
	 * 
	 * @param itemid
	 *            道具编号
	 * @param className
	 *            执行位置
	 * @param mode
	 *            0 if L1EtcItem, 1 if L1Weapon, 2 if L1Armor
	 */
	public void addList(final int itemid, final String className, final int mode) {

		// Class为0
		if (className.equals("0")) {
			return;
		}

		try {
			final StringBuilder stringBuilder = new StringBuilder();
			switch (mode) {
			case 0: // 道具
				stringBuilder.append("lineage.item.etcitem.");
				break;

			case 1: // 武器
				stringBuilder.append("lineage.item.weapon.");
				break;

			case 2: // 防具
				stringBuilder.append("lineage.item.armor.");
				break;
			}
			stringBuilder.append(className);

			final Class<?> cls = Class.forName(stringBuilder.toString());
			final ItemExecutor exe = (ItemExecutor) cls.getMethod("get").invoke(null);

			_classList.put(new Integer(itemid), exe);

		}
		catch (final ClassNotFoundException e) {
			String error = "道具Class档案 (" + className + ") 错误 (ItemId:" + itemid + ")";
			_log.error(error);
			DataError.isError(_log, error, e);

		}
		catch (final IllegalArgumentException e) {
			_log.error(e.getLocalizedMessage(), e);

		}
		catch (final IllegalAccessException e) {
			_log.error(e.getLocalizedMessage(), e);

		}
		catch (final InvocationTargetException e) {
			_log.error(e.getLocalizedMessage(), e);

		}
		catch (final SecurityException e) {
			_log.error(e.getLocalizedMessage(), e);

		}
		catch (final NoSuchMethodException e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 道具的执行
	 * 
	 * @param data
	 *            资料
	 * @param pc
	 *            对象
	 * @param item
	 *            道具
	 */
	public void item(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {

		// 空角色
		if (pc == null) {
			return;
		}

		// 空道具
		if (item == null) {
			return;
		}

		try {
			// 取得Class执行位置
			final ItemExecutor exe = _classList.get(new Integer(item.getItemId()));
			if (exe != null) {
				exe.execute(data, pc, item);
			}

		}
		catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 防具的执行
	 * 
	 * @param equipped
	 *            装备 (使用)
	 * @param pc
	 *            对象
	 * @param item
	 *            防具
	 */
	public void item_armor(final boolean equipped, final L1PcInstance pc, final L1ItemInstance item) {

		// 空角色
		if (pc == null) {
			return;
		}

		// 空道具
		if (item == null) {
			return;
		}

		try {
			// 取得Class执行位置
			final ItemExecutor exe = _classList.get(new Integer(item.getItemId()));
			if (exe != null) {
				int[] data = new int[1];
				data[0] = equipped ? 1 : 0;
				exe.execute(data, pc, item);
			}

		}
		catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 武器的执行
	 * 
	 * @param equipped
	 *            装备 (使用)
	 * @param pc
	 *            对象
	 * @param item
	 *            武器
	 */
	public void item_weapon(final boolean equipped, final L1PcInstance pc, final L1ItemInstance item) {

		// 空角色
		if (pc == null) {
			return;
		}

		// 空道具
		if (item == null) {
			return;
		}

		try {
			// 取得Class执行位置
			final ItemExecutor exe = _classList.get(new Integer(item.getItemId()));
			if (exe != null) {
				int[] data = new int[1];
				data[0] = equipped ? 1 : 0;
				exe.execute(data, pc, item);
			}

		}
		catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
}
