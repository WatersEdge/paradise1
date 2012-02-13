package lineage.item.etcitem.other;

import l1j.server.server.ActionCodes;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.utils.Random;
import lineage.item.etcitem.executor.ItemExecutor;

/**
 * 闪电魔杖 - 40007
 * 
 * @author jrwz
 */
public class Wand_Lightning extends ItemExecutor {

	public static ItemExecutor get() {
		return new Wand_Lightning();
	}

	private Wand_Lightning() {
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

		final int spellsc_objid = data[0];
		final int spellsc_x = data[1];
		final int spellsc_y = data[2];

		int dmg = 0;
		int[] data1 = null;
		final L1Object target = L1World.getInstance().findObject(spellsc_objid);
		if (target != null) {
			dmg = doWandAction(pc, target);
		}
		data1 = new int[] { ActionCodes.ACTION_Wand, dmg, 10, 6 }; // data = {actid, dmg, spellgfx, use_type}
		pc.sendPackets(new S_UseAttackSkill(pc, spellsc_objid, spellsc_x, spellsc_y, data1));
		pc.broadcastPacket(new S_UseAttackSkill(pc, spellsc_objid, spellsc_x, spellsc_y, data1));
		item.setChargeCount(item.getChargeCount() - 1);
		pc.getInventory().updateItem(item, L1PcInventory.COL_CHARGE_COUNT);
		if (item.getChargeCount() <= 0) { // 次数为 0时删除
			pc.getInventory().removeItem(item, 1);
		}
	}

	private int doWandAction(final L1PcInstance user, final L1Object target) {
		if (user.getId() == target.getId()) {
			return 0; // 目标为自身
		}
		if (user.glanceCheck(target.getX(), target.getY()) == false) {
			return 0; // 有障碍物
		}

		// 伤害值计算公式
		int dmg = (Random.nextInt(11) - 5) + user.getStr();
		dmg = Math.max(1, dmg);

		if (target instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) target;
			if (pc.getMap().isSafetyZone(pc.getLocation()) || user.checkNonPvP(user, pc)) {
				return 0;
			}
			if ((pc.hasSkillEffect(50) == true) || (pc.hasSkillEffect(78) == true) || (pc.hasSkillEffect(157) == true)) {
				return 0;
			}

			final int newHp = pc.getCurrentHp() - dmg;
			if (newHp > 0) {
				pc.setCurrentHp(newHp);
			}
			else if ((newHp <= 0) && pc.isGm()) {
				pc.setCurrentHp(pc.getMaxHp());
			}
			else if ((newHp <= 0) && !pc.isGm()) {
				pc.death(user);
			}
			return dmg;
		}
		else if (target instanceof L1MonsterInstance) {
			final L1MonsterInstance mob = (L1MonsterInstance) target;
			mob.receiveDamage(user, dmg);
			return dmg;
		}
		return 0;
	}
}
