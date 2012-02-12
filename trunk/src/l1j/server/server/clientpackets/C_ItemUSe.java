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

import static l1j.server.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static l1j.server.server.model.skill.L1SkillId.AWAKEN_VALAKAS;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;

import java.sql.Timestamp;
import java.util.Calendar;

import l1j.server.server.ClientThread;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1TreasureBox;
import l1j.server.server.model.item.action.Effect;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1EtcItem;
import lineage.console.ItemClass;
import lineage.console.utils.CheckUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

		// 使用物件的OBJID
		final int itemObjid = readD();

		// 取得使用者
		final L1PcInstance pc = client.getActiveChar();

		// 角色为空
		if (pc == null) {
			return;
		}

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

		// 开个人商店中
		if (pc.isPrivateShop()) {
			return;
		}

		// 不能使用道具的地图
		if (!pc.getMap().isUsableItem()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1你无法在这个地方使用。
			return;
		}

		// 什么都不能做的状态
		if (pc.CanNotDoAnything()) {
			return;
		}

		// 取得使用道具
		final L1ItemInstance useItem = pc.getInventory().getItem(itemObjid);

		// 例外:空道具
		if (useItem == null) {
			return;
		}

		// 例外:数量异常
		if (useItem.getCount() <= 0) {
			pc.sendPackets(new S_ServerMessage(329, useItem.getLogName())); // \f1没有具有 %0%o。
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

			final int min = useItem.getItem().getMinLevel(); // 取得可以使用道具的最低等级
			final int max = useItem.getItem().getMaxLevel(); // 取得可以使用道具的最高等级

			if ((min != 0) && (min > pc.getLevel())) {
				pc.sendPackets(new S_ServerMessage(318, String.valueOf(min))); // 等级 %0以上才可使用此道具。
				return;
			}
			else if ((max != 0) && (max < pc.getLevel())) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.MSG_LEVEL_OVER, max)); // 等级 %d以下才能使用此道具。
				return;
			}

			// 是否具有延迟时间
			boolean isDelayEffect = false;
			if (useItem.getItem().getType2() == 0) { // 使用类型为道具 (etcitem)
				final int delayEffect = ((L1EtcItem) useItem.getItem()).get_delayEffect(); // 延迟时间
				if (delayEffect > 0) {
					isDelayEffect = true;
					final Timestamp lastUsed = useItem.getLastUsed();
					if (lastUsed != null) {
						Calendar cal = Calendar.getInstance();
						long UseTime = (cal.getTimeInMillis() - lastUsed.getTime()) / 1000;
						if (UseTime <= delayEffect) {
							// 转换为须等待时间
							UseTime = (delayEffect - UseTime) / 60;
							// 取得等待时间 (时间数字 转换为字串)
							final String UseTimeSurplus = useItem.getLogName() + " " + String.valueOf(UseTime);
							pc.sendPackets(new S_ServerMessage(1139, UseTimeSurplus)); // %0 分钟之内无法使用。
							return;
						}
					}
				}
			}

			// 取得物件触发事件
			final int use_type = useItem.getItem().getUseType();
			switch (use_type) {

				case -10: // 魔法娃娃类
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -8: // 料理
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -7: // 料理书
					if (isClass) {
						final int[] newData = new int[2];
						newData[0] = this.readC();
						newData[1] = this.readC();
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case -6: // 技能书
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -5: // 其他类道具
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -9: // 状态药水
				case -4: // 加速类道具 (绿色药水)
				case -3: // 回魔类道具 (蓝色药水)
				case -2: // 加血类道具 (治愈药水)
					if (pc.hasSkillEffect(DECAY_POTION)) {
						pc.sendPackets(new S_ServerMessage(698)); // 喉咙灼热，无法喝东西。
						return;
					}
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case -1: // 无法使用 (材料等)
					pc.sendPackets(new S_ServerMessage(74, useItem.getLogName())); // \f1%0%o 无法使用。
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
					if (CheckUtil.checkEquipped(pc, useItem)) {
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
					if (CheckUtil.checkEquipped(pc, useItem)) {
						CheckUtil.useArmor(pc, useItem);
					}
					break;

				case 5: // 魔杖类型 (须选取目标/坐标)地面 / 选择对象(远距离)
					if (isClass) {
						final int[] newData = new int[3];
						newData[0] = this.readD(); // 选取目标的OBJID
						newData[1] = this.readH(); // X坐标
						newData[2] = this.readH(); // Y坐标
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 6: // 瞬间移动卷轴
				case 29: // 瞬间移动卷轴(祝福)
					if (isClass) {
						final int[] newData = new int[2];
						newData[1] = this.readH(); // 所在地图编号
						newData[0] = this.readD(); // 选取目标的OBJID
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 7: // 鉴定卷轴
					if (isClass) {
						final int[] newData = new int[1];
						newData[0] = this.readD(); // 选取物件的OBJID
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 8: // 复活卷轴
					if (isClass) {
						final int[] newData = new int[1];
						newData[0] = this.readD(); // 选取物件的OBJID
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 12: // 信纸
				case 31: // 圣诞卡片
				case 33: // 情人节卡片
				case 35: // 白色情人节卡片
					if (isClass) {
						final int[] newData = new int[1];
						newData[0] = this.readH();
						pc.setText(this.readS());
						pc.setTextByte(this.readByte());
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 13: // 信纸(打开)
				case 32: // 圣诞卡片(打开)
				case 34: // 情人节卡片(打开)
				case 36: // 白色情人节卡片(打开)
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case 14: // 请选择一个物品 (道具栏位) 灯油/磨刀石/胶水/龙之魔眼等
					if (isClass) {
						final int[] newData = new int[1];
						newData[0] = this.readD(); // 选取物件的OBJID
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 15:// 哨子
					if (isClass) {
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case 16: // 变形卷轴
					// 取回觉醒技能ID
					final int awakeSkillId = pc.getAwakeSkillId();
					if ((awakeSkillId == AWAKEN_ANTHARAS) || (awakeSkillId == AWAKEN_FAFURION) || (awakeSkillId == AWAKEN_VALAKAS)) {
						pc.sendPackets(new S_ServerMessage(1384)); // 目前状态中无法变身。
						return;
					}
					if (isClass) {
						final String cmd = this.readS();
						pc.setText(cmd); // 选取的变身命令
						ItemClass.getInstance().item(null, pc, useItem);
					}
					break;

				case 17: // 选取目标 地面 (近距离)
					if (isClass) {
						final int[] newData = new int[3];
						newData[0] = this.readD(); // 选取目标的OBJID
						newData[1] = this.readH(); // X坐标
						newData[2] = this.readH(); // Y坐标
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 26: // 对武器施法的卷轴
				case 27: // 对盔甲施法的卷轴
				case 46: // 饰品强化卷轴
					if (isClass) {
						final int[] newData = new int[1];
						newData[0] = this.readD(); // 选取物件的OBJID
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 28: // 空的魔法卷轴
					if (isClass) {
						final int[] newData = new int[1];
						newData[0] = this.readC();
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 30: // 选取目标 (对NPC需要Ctrl 远距离 无XY坐标传回)
					if (isClass) {
						final int obj = this.readD(); // 选取目标的OBJID
						final int[] newData = new int[] { obj };
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				case 42: // 钓鱼杆 (魔法钓竿)
					if (isClass) {
						final int[] newData = new int[3];
						newData[0] = this.readH(); // X坐标
						newData[1] = this.readH(); // Y坐标
						ItemClass.getInstance().item(newData, pc, useItem);
					}
					break;

				default: // 检测
					_log.info("未处理的道具类型: " + use_type);
					break;
			}

			// 取得道具类型 (0:道具、1:武器、2:防具)
			final int item_type = useItem.getItem().getType2();
			switch (item_type) {

				case 0: // 道具类 (L1EtcItem)

					// 取得道具ID
					final int itemId = useItem.getItem().getItemId();

					// 取得物品类型
					final int etcitem_type = useItem.getItem().getType();

					switch (etcitem_type) {

						case 0: // 箭
							pc.getInventory().setArrow(useItem.getItem().getItemId());
							pc.sendPackets(new S_ServerMessage(452, useItem.getLogName())); // %0%s 被选择了。
							break;

						case 15: // 飞刀
							pc.getInventory().setSting(useItem.getItem().getItemId());
							pc.sendPackets(new S_ServerMessage(452, useItem.getLogName())); // %0%s 被选择了。
							break;

						case 16: // treasure_box (宝箱类)
							final L1TreasureBox box = L1TreasureBox.get(itemId);
							if (box != null) {
								if (box.open(pc)) {
									final L1EtcItem temp = (L1EtcItem) useItem.getItem();
									if (temp.get_delayEffect() > 0) {
										// 有限制再次使用的时间且可堆叠的道具
										if (useItem.isStackable()) {
											if (useItem.getCount() > 1) {
												isDelayEffect = true;
											}
											pc.getInventory().removeItem(useItem.getId(), 1);
										}
										else {
											isDelayEffect = true;
										}
									}
									else {
										pc.getInventory().removeItem(useItem.getId(), 1);
									}
								}
							}
							break;

						default: // 检测
							// _log.info("未处理的道具类型: " + use_type);
							break;
					}

					switch (itemId) {
						/*
						 * case 40024: // 古代终极体力恢复剂 Potion.UseHeallingPotion(pc, UseItem, 55, 197); return;
						 */
						// 1 ~ 4 阶附魔石(近战)(远攻)(恢复)(防御)
						case 47064:
						case 47065:
						case 47066:
						case 47067:
						case 47074:
						case 47075:
						case 47076:
						case 47077:
						case 47084:
						case 47085:
						case 47086:
						case 47087:
						case 47094:
						case 47095:
						case 47096:
						case 47097:
							if (pc.getInventory().consumeItem(41246, 30)) {
								Effect.useEffectItem(pc, useItem);
							}
							else {
								isDelayEffect = false;
								pc.sendPackets(new S_ServerMessage(337, "$5240")); // \f1%0不足%s。
							}
							break;

						// 5 ~ 6阶附魔石(近战)(远攻)(恢复)(防御)
						case 47068:
						case 47069:
						case 47078:
						case 47079:
						case 47088:
						case 47089:
						case 47098:
						case 47099:
							if (pc.getInventory().consumeItem(41246, 60)) {
								Effect.useEffectItem(pc, useItem);
							}
							else {
								isDelayEffect = false;
								pc.sendPackets(new S_ServerMessage(337, "$5240")); // \f1%0不足%s。
							}
							break;

						// 7阶附魔石(近战)(远攻)(恢复)(防御)
						case 47070:
						case 47080:
						case 47090:
						case 47100:
							if (pc.getInventory().consumeItem(41246, 100)) {
								Effect.useEffectItem(pc, useItem);
							}
							else {
								isDelayEffect = false;
								pc.sendPackets(new S_ServerMessage(337, "$5240")); // \f1%0不足%s。
							}
							break;

						// 8阶附魔石(近战)(远攻)(恢复)(防御)
						case 47071:
						case 47081:
						case 47091:
						case 47101:
							if (pc.getInventory().consumeItem(41246, 200)) {
								Effect.useEffectItem(pc, useItem);
							}
							else {
								isDelayEffect = false;
								pc.sendPackets(new S_ServerMessage(337, "$5240")); // \f1%0不足%s。
							}
							break;

						// 9阶附魔石(近战)(远攻)(恢复)(防御)
						case 47072:
						case 47082:
						case 47092:
						case 47102:
							if (pc.getInventory().consumeItem(41246, 300)) {
								Effect.useEffectItem(pc, useItem);
							}
							else {
								isDelayEffect = false;
								pc.sendPackets(new S_ServerMessage(337, "$5240")); // \f1%0不足%s。
							}
							break;

						case 40576: // 灵魂水晶（白）
							if (!pc.isElf()) {
								pc.sendPackets(new S_ServerMessage(264)); // \f1你的职业无法使用此道具。
							}
							break;

						case 40577: // 灵魂水晶（黒）
							if (!pc.isWizard()) {
								pc.sendPackets(new S_ServerMessage(264)); // \f1你的职业无法使用此道具。
							}
							break;

						case 40578: // 灵魂水晶（赤）
							if (!pc.isKnight()) {
								pc.sendPackets(new S_ServerMessage(264)); // \f1你的职业无法使用此道具。
							}
							break;

						default: // 检测

							final int locX = ((L1EtcItem) useItem.getItem()).get_locx();
							final int locY = ((L1EtcItem) useItem.getItem()).get_locy();
							final short mapId = ((L1EtcItem) useItem.getItem()).get_mapid();

							// 各种传送卷轴
							if ((locX != 0) && (locY != 0)) {
								if (!(itemId >= 40289 && itemId <= 40297)) {
									if (pc.getMap().isEscapable() || pc.isGm()) {
										L1Teleport.teleport(pc, locX, locY, mapId, pc.getHeading(), true);
										pc.getInventory().removeItem(useItem, 1);
									}
									else {
										pc.sendPackets(new S_ServerMessage(647)); // 这附近的能量影响到瞬间移动。在此地无法使用瞬间移动。
									}
								}
							}
							// _log.info("未处理的道具类型: " + use_type);
							break;
					}
					break;

				default: // 检测

					// _log.info("未处理的道具类型: " + use_type);
					break;
			}

			// 物件使用延迟设置
			if (isDelayEffect) {
				final Timestamp ts = new Timestamp(System.currentTimeMillis());
				// 设置使用时间
				useItem.setLastUsed(ts);
				pc.getInventory().updateItem(useItem, L1PcInventory.COL_DELAY_EFFECT);
				pc.getInventory().saveItem(useItem, L1PcInventory.COL_DELAY_EFFECT);
			}
			try {
				L1ItemDelay.onItemUse(client, useItem); // 项目开始延迟

			}
			catch (Exception e) {
				_log.error("道具使用延迟异常:" + useItem.getItemId(), e);
			}
		}
	}

	@Override
	public String getType() {
		return C_ITEM_USE;
	}
}
