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

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

/**
 * 移除指定的魔法
 */
public class S_DelSkill extends ServerBasePacket {

	/**
	 * 移除指定的魔法(技能群)
	 * <BR>
	 * @param i 法师技能LV1
	 * @param j 法师技能LV2
	 * @param k 法师技能LV3
	 * @param l 法师技能LV4
	 * @param i1 法师技能LV5
	 * @param j1 法师技能LV6
	 * @param k1 法师技能LV7
	 * @param l1 法师技能LV8
	 * @param i2 法师技能LV9
	 * @param j2 法师技能LV10
	 * <BR>
	 * @param k2 骑士技能LV1
	 * @param l2 骑士技能LV2
	 * <BR>
	 * @param i3 黑暗精灵技能LV1
	 * @param j3 黑暗精灵技能LV2
	 * <BR>
	 * @param k3 王族技能
	 * <BR>
	 * @param l3
	 * <BR>
	 * @param i4 精灵技能LV1
	 * @param j4 精灵技能LV2
	 * @param k4 精灵技能LV3
	 * @param l4 精灵技能LV4
	 * @param i5 精灵技能LV5
	 * @param j5 精灵技能LV6
	 * <BR>
	 * @param k5 龙骑士技能LV1
	 * @param l5 龙骑士技能LV2
	 * @param m5 龙骑士技能LV3
	 * <BR>
	 * @param n5 幻术师LV1
	 * @param o5 幻术师LV2
	 * @param p5 幻术师LV3
	 */
	public S_DelSkill(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3, int l3,
			int i4, int j4, int k4, int l4, int i5, int j5, int k5, int l5, int m5, int n5, int o5, int p5) {
		int i6 = i1 + j1 + k1 + l1;
		int j6 = i2 + j2;
		writeC(Opcodes.S_OPCODE_DELSKILL);
		if ((i6 > 0) && (j6 == 0)) {
			writeC(50);
		}
		else if (j6 > 0) {
			writeC(100);
		}
		else {
			writeC(32);
		}
		writeC(i);
		writeC(j);
		writeC(k);
		writeC(l);
		writeC(i1);
		writeC(j1);
		writeC(k1);
		writeC(l1);
		writeC(i2);
		writeC(j2);
		writeC(k2);
		writeC(l2);
		writeC(i3);
		writeC(j3);
		writeC(k3);
		writeC(l3);
		writeC(i4);
		writeC(j4);
		writeC(k4);
		writeC(l4);
		writeC(i5);
		writeC(j5);
		writeC(k5);
		writeC(l5);
		writeC(m5);
		writeC(n5);
		writeC(o5);
		writeC(p5);
		writeD(0);
		writeD(0);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return "[S] S_DelSkill";
	}

}
