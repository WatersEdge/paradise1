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
package l1j.server.server.clientpackets;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import l1j.server.server.utils.CheckUtil;
import l1j.server.data.ItemClass;
import l1j.server.server.ClientThread;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1EtcItem;

/**
 * 处理由客户端传来使用道具的封包
 * 
 * @author jrwz
 */
public class C_ItemUSe extends ClientBasePacket {

	/** 客户端数据包类型 */
	private static final String C_ITEM_USE = "[C] C_ItemUSe";

	/** 提示信息 */
	private static final Log _log = LogFactory.getLog(C_ItemUSe.class);

	/**
	 * 要求使用道具
	 * 
	 * @param
	 */
	public C_ItemUSe(byte[] abyte0, ClientThread client) throws Exception {

		// 载入资料
		super(abyte0);

		try {

			// 使用物件的OBJID
			int itemObjid = readD();

			// 取得使用者
			L1PcInstance pc = client.getActiveChar();

			// 幽灵状态
			if (pc.isGhost()) {
				return;
			}

			// 死亡状态
			if (pc.isDead()) {
				return;
			}

			// 传送状态
			if (pc.isTeleport()) {
				return;
			}

			// 不能使用道具的地图
			if (!pc.getMap().isUsableItem()) {
				pc.sendPackets(new S_ServerMessage(563)); // \f1你无法在这个地方使用。
				return;
			}

			// 取得使用道具
			L1ItemInstance useItem = pc.getInventory().getItem(itemObjid);

			// 使用道具为空
			if (useItem == null) {
				return;
			}

			// 是否具有Class
			boolean isClass = false;
			final String className = useItem.getItem().getClassName(); // 独立执行项位置

			// Class不为0
			if (!className.equals("0")) {
				isClass = true;
			}

			// PC当前血量大于0
			if (pc.getCurrentHp() > 0) {
				int delay_id = 0; // 延迟ID
				if (useItem.getItem().getType2() == 0) { // 种类：道具
					delay_id = ((L1EtcItem) useItem.getItem()).get_delayid(); // 取得延迟ID
				}
				if (delay_id != 0) { // 延迟设定
					if (pc.hasItemDelay(delay_id) == true) {
						return;
					}
				}

				// 例外:数量异常
				if (useItem.getCount() <= 0) {
					pc.sendPackets(new S_ServerMessage(329, useItem
							.getLogName())); // \f1没有具有 %0%o。
					return;
				}

				int min = useItem.getItem().getMinLevel(); // 取得可以使用道具的最低等级
				int max = useItem.getItem().getMaxLevel(); // 取得可以使用道具的最高等级

				if ((min != 0) && (min > pc.getLevel())) {
					pc.sendPackets(new S_ServerMessage(318, String.valueOf(min))); // 等级
																					// %0以上才可使用此道具。
					return;
				} else if ((max != 0) && (max < pc.getLevel())) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.MSG_LEVEL_OVER,
							max)); // 等级%d以下才能使用此道具。
					return;
				}

				// 是否具有延迟时间
				boolean isDelayEffect = false;
				if (useItem.getItem().getType2() == 0) { // 使用类型为道具 (etcitem)
					int delayEffect = ((L1EtcItem) useItem.getItem())
							.get_delayEffect(); // 延迟时间
					if (delayEffect > 0) {
						isDelayEffect = true;
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed != null) {
							Calendar cal = Calendar.getInstance();
							long UseTime = (cal.getTimeInMillis() - lastUsed
									.getTime()) / 1000;
							if (UseTime <= delayEffect) {
								// 转换为须等待时间
								UseTime = (delayEffect - UseTime) / 60;
								// 取得等待时间 (时间数字 转换为字串)
								String UseTimeSurplus = useItem.getLogName()
										+ " " + String.valueOf(UseTime);
								pc.sendPackets(new S_ServerMessage(1139,
										UseTimeSurplus)); // %0 分钟之内无法使用。
								return;
							}
						}
					}
				}

				// 取得物件触发事件
				final int use_type = useItem.getItem().getUseType();
				switch (use_type) {

				case -3: // 回魔类道具 (蓝色药水)
					if (!CheckUtil.getUseItem(pc)) {
						return;
					}
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -2: // 加血类道具 (治愈药水)
					if (!CheckUtil.getUseItem(pc)) {
						return;
					}
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -1: // 无法使用 (材料等)
					pc.sendPackets(new S_ServerMessage(74, useItem.getLogName())); // \f1%0%o
																					// 无法使用。
					break;

				case 0: // 一般类道具
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case 1: // 武器
						// 武器禁止使用
					if (pc.hasItemDelay(L1ItemDelay.WEAPON) == true) {
						return;
					}
					if (CheckUtil.check(pc, useItem)) {
						CheckUtil.useWeapon(pc, useItem);
					}
					break;

				case 2: // 盔甲
				case 18: // T恤
				case 19: // 斗篷
				case 20: // 手套
				case 21: // 长靴
				case 22: // 头盔
				case 23: // 戒指
				case 24: // 项链
				case 25: // 盾牌
				case 37: // 腰带
				case 40: // 耳环
				case 43: // 辅助装备 (右)
				case 44: // 辅助装备 (左)
				case 45: // 辅助装备 (中)
					// 防具禁止使用
					if (pc.hasItemDelay(L1ItemDelay.ARMOR) == true) {
						return;
					}
					if (CheckUtil.check(pc, useItem)) {
						CheckUtil.useArmor(pc, useItem);
					}
					break;

				default: // 检测
					_log.info("未处理的道具类型: " + use_type);
					break;
				}

				// 道具类 (L1EtcItem)
				if (useItem.getItem().getType2() == 0) {

					// 取得道具ID
					final int itemId = useItem.getItem().getItemId();

					switch (itemId) {
					/*
					 * case 40024: // 古代终极体力恢复剂 Potion.UseHeallingPotion(pc,
					 * UseItem, 55, 197); return;
					 */
					}
				}

				// 物件使用延迟设置
				if (isDelayEffect) {
					final Timestamp ts = new Timestamp(
							System.currentTimeMillis());
					// 设置使用时间
					useItem.setLastUsed(ts);
					pc.getInventory().updateItem(useItem,
							L1PcInventory.COL_DELAY_EFFECT);
					pc.getInventory().saveItem(useItem,
							L1PcInventory.COL_DELAY_EFFECT);
				}
				try {
					L1ItemDelay.onItemUse(client, useItem); // 项目开始延迟

				} catch (Exception e) {
					_log.error("道具使用延迟异常:" + useItem.getItemId(), e);
				}
			}
		} catch (final Exception e) {
			// _log.info(e.getLocalizedMessage());
		}
	}

	@Override
	public String getType() {
		return C_ITEM_USE;
	}
}
