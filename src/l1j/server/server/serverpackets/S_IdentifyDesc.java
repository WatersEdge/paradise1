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
package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1ItemInstance;

/**
 * 物品资讯讯息 (使用String-h.tbl)
 */
public class S_IdentifyDesc extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 物品资讯讯息 (使用String-h.tbl)
	 */
	public S_IdentifyDesc(L1ItemInstance item) {
		buildPacket(item);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	private void buildPacket(L1ItemInstance item) {
		writeC(Opcodes.S_OPCODE_IDENTIFYDESC);
		writeH(item.getItem().getItemDescId());

		StringBuilder name = new StringBuilder();

		if (item.getItem().getBless() == 0) {
			name.append("$227 "); // 祝福
		}
		else if (item.getItem().getBless() == 2) {
			name.append("$228 "); // 诅咒
		}

		name.append(item.getItem().getIdentifiedNameId());

		// 旅馆钥匙
		if (item.getItem().getItemId() == 40312 && item.getKeyId() != 0) {
			name.append(item.getInnKeyName());
		}

		if (item.getItem().getType2() == 1) { // 武器 (weapon)
			writeH(134); // \f1%0：对小怪物的伤害 %1 对大怪物的伤害 %2
			writeC(3);
			writeS(name.toString());
			writeS(item.getItem().getDmgSmall() + "+" + item.getEnchantLevel());
			writeS(item.getItem().getDmgLarge() + "+" + item.getEnchantLevel());

		}
		else if (item.getItem().getType2() == 2) { // 防具 (armor)
			if (item.getItem().getItemId() == 20383) { // 军马头盔
				writeH(137); // \f1%0：可使用的次数 %1 [重量 %2]
				writeC(3);
				writeS(name.toString());
				writeS(String.valueOf(item.getChargeCount()));
			}
			else {
				writeH(135); // \f1%0：防御力 %1 防御装备
				writeC(2);
				writeS(name.toString());
				writeS(Math.abs(item.getItem().get_ac()) + "+" + item.getEnchantLevel());
			}

		}
		else if (item.getItem().getType2() == 0) { // 道具 (etcitem)
			if (item.getItem().getType() == 1) { // wand
				writeH(137); // \f1%0：可使用的次数 %1 [重量 %2]
				writeC(3);
				writeS(name.toString());
				writeS(String.valueOf(item.getChargeCount()));
			}
			else if (item.getItem().getType() == 2) { // 照明类道具 (light)
				writeH(138); // \f1%0 [重量 %1]
				writeC(2);
				name.append(": $231 "); // 剩余燃料量
				name.append(String.valueOf(item.getRemainingTime()));
				writeS(name.toString());
			}
			else if (item.getItem().getType() == 7) { // 食物 (food)
				writeH(136); // \f1%0：营养度 %1 [重量 %2]
				writeC(3);
				writeS(name.toString());
				writeS(String.valueOf(item.getItem().getFoodVolume()));
			}
			else {
				writeH(138); // \f1%0 [重量 %1]
				writeC(2);
				writeS(name.toString());
			}
			writeS(String.valueOf(item.getWeight()));
		}
	}
}
