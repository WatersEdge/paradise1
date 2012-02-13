package l1j.server.server.model.item.etcitem.quest;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.executor.ItemExecutor;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.L1SpawnUtil;

/**
 * 新50级任务 (王、骑、妖、法)
 * 魔之角笛 - 49167
 * 
 * @author jrwz
 */
public class MagicAngleFlute extends ItemExecutor {

	public static ItemExecutor get() {
		return new MagicAngleFlute();
	}

	private MagicAngleFlute() {
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

		// 再生的圣所 1层-3层 - (王族)
		if (pc.isCrown() && (pc.getMapId() == 2000) && (pc.getX() == 32807) && (pc.getY() == 32773)) {
			boolean found = false;
			for (final L1Object obj : L1World.getInstance().getObject()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob != null) {
						if (mob.getNpcTemplate().get_npcId() == 81323) {
							found = true;
							break;
						}
					}
				}
			}
			if (found) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
			else {
				L1SpawnUtil.spawn(pc, 81323, 0, 0); // 神官奇诺 (新50级试炼怪-王族)
			}
		}

		// 再生的圣所 1层-3层 - (骑士)
		else if (pc.isKnight() && (pc.getMapId() == 2001) && (pc.getX() == 32807) && (pc.getY() == 32773)) {
			boolean found = false;
			for (final L1Object obj : L1World.getInstance().getObject()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob != null) {
						if (mob.getNpcTemplate().get_npcId() == 81324) {
							found = true;
							break;
						}
					}
				}
			}
			if (found) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
			else {
				L1SpawnUtil.spawn(pc, 81324, 0, 0); // 神官奇诺 (新50级试炼怪-骑士)
			}
		}

		// 再生的圣所 1层-3层 - (妖精)
		else if (pc.isElf() && (pc.getMapId() == 2002) && (pc.getX() == 32807) && (pc.getY() == 32773)) {
			boolean found = false;
			for (final L1Object obj : L1World.getInstance().getObject()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob != null) {
						if (mob.getNpcTemplate().get_npcId() == 81325) {
							found = true;
							break;
						}
					}
				}
			}
			if (found) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
			else {
				L1SpawnUtil.spawn(pc, 81325, 0, 0); // 神官奇诺 (新50级试炼怪-妖精)
			}
		}

		// 再生的圣所 1层-3层 - (法师)
		else if (pc.isWizard() && (pc.getMapId() == 2003) && (pc.getX() == 32807) && (pc.getY() == 32773)) {
			boolean found = false;
			for (final L1Object obj : L1World.getInstance().getObject()) {
				if (obj instanceof L1MonsterInstance) {
					final L1MonsterInstance mob = (L1MonsterInstance) obj;
					if (mob != null) {
						if (mob.getNpcTemplate().get_npcId() == 81326) {
							found = true;
							break;
						}
					}
				}
			}
			if (found) {
				pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
			}
			else {
				L1SpawnUtil.spawn(pc, 81326, 0, 0); // 神官奇诺 (新50级试炼怪-法师)
			}
		}
		else {
			pc.sendPackets(new S_ServerMessage(79)); // \f1没有任何事情发生。
		}
		pc.getInventory().consumeItem(49167, 1); // 删除魔之角笛
	}
}
