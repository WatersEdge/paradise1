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

import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;

import java.util.concurrent.atomic.AtomicInteger;

import l1j.server.server.ActionCodes;
import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1PcInstance;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

/**
 * 物件攻击 (使用技能)
 */
public class S_UseAttackSkill extends ServerBasePacket {

	private static final String S_USE_ATTACK_SKILL = "[S] S_UseAttackSkill";

	private static AtomicInteger _sequentialNumber = new AtomicInteger(0);

	private byte[] _byte = null;

	public S_UseAttackSkill(L1Character cha, int targetobj, int x, int y, int[] data) {
		buildPacket(cha, targetobj, x, y, data, true);
	}

	public S_UseAttackSkill(L1Character cha, int targetobj, int x, int y, int[] data, boolean withCastMotion) {
		buildPacket(cha, targetobj, x, y, data, withCastMotion);
	}

	private void buildPacket(L1Character cha, int targetobj, int x, int y, int[] data, boolean withCastMotion) {
		if (cha instanceof L1PcInstance) {
			// 变身中使用攻击魔法时动作代号异动
			if (cha.hasSkillEffect(SHAPE_CHANGE) && (data[0] == ActionCodes.ACTION_SkillAttack)) {
				int tempchargfx = cha.getTempCharGfx();
				if ((tempchargfx == 5727) || (tempchargfx == 5730)) {
					data[0] = ActionCodes.ACTION_SkillBuff;
				}
				else if ((tempchargfx == 5733) || (tempchargfx == 5736)) {
					// 物件具有变身 改变动作代码
					data[0] = ActionCodes.ACTION_Attack;
				}
			}
		}
		// 火灵之主动作代码强制变更
		if (cha.getTempCharGfx() == 4013) {
			data[0] = ActionCodes.ACTION_Attack;
		}

		// 设置新面向
		int newheading = calcheading(cha.getX(), cha.getY(), x, y);
		cha.setHeading(newheading);
		writeC(Opcodes.S_OPCODE_ATTACKPACKET);
		writeC(data[0]); // actionId 动作代码
		writeD(withCastMotion ? cha.getId() : 0); // 使用者的OBJ
		writeD(targetobj); // 目标的OBJ
		writeH(data[1]); // dmg 伤害值
		writeC(newheading); // 新面向
		writeD(_sequentialNumber.incrementAndGet()); // 以原子方式将当前值加 1。
		writeH(data[2]); // spellgfx 远程动画代码
		writeC(data[3]); // use_type 0:弓箭 6:远距离魔法 8:远距离范围魔法
		writeH(cha.getX()); // 使用者X坐标
		writeH(cha.getY()); // 使用者Y坐标
		writeH(x); // 目标X坐标
		writeH(y); // 目标Y坐标
		writeC(0);
		writeC(0);
		writeC(0); // 0:none 2:爪痕 4:双击 8:镜返射
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}
		else {
			int seq = 0;
			synchronized (this) {
				seq = _sequentialNumber.incrementAndGet();
			}
			_byte[13] = (byte) (seq & 0xff);
			_byte[14] = (byte) (seq >> 8 & 0xff);
			_byte[15] = (byte) (seq >> 16 & 0xff);
			_byte[16] = (byte) (seq >> 24 & 0xff);
		}

		return _byte;
	}

	private static int calcheading(int myx, int myy, int tx, int ty) {
		int newheading = 0;
		if ((tx > myx) && (ty > myy)) {
			newheading = 3;
		}
		if ((tx < myx) && (ty < myy)) {
			newheading = 7;
		}
		if ((tx > myx) && (ty == myy)) {
			newheading = 2;
		}
		if ((tx < myx) && (ty == myy)) {
			newheading = 6;
		}
		if ((tx == myx) && (ty < myy)) {
			newheading = 0;
		}
		if ((tx == myx) && (ty > myy)) {
			newheading = 4;
		}
		if ((tx < myx) && (ty > myy)) {
			newheading = 5;
		}
		if ((tx > myx) && (ty < myy)) {
			newheading = 1;
		}
		return newheading;
	}

	@Override
	public String getType() {
		return S_USE_ATTACK_SKILL;
	}

}