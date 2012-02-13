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
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * 组队名单
 */
public class S_Party extends ServerBasePacket {

	private static final String _S_Party = "[S] S_Party";

	private byte[] _byte = null;

	public S_Party(final int type, final L1PcInstance pc) { // 3.3C 组队系统
		switch (type) {
			case 104:
				newMember(pc);
				break;
			case 105:
				oldMember(pc);
				break;
			case 106:
				changeLeader(pc);
			case 110:
				refreshParty(pc);
				break;
			default:
				break;
		}
	}

	public S_Party(final String htmlid, final int objid) {
		buildPacket(htmlid, objid, "", "", 0);
	}

	public S_Party(final String htmlid, final int objid, final String partyname, final String partymembers) {
		buildPacket(htmlid, objid, partyname, partymembers, 1);
	}

	/**
	 * 更换队长
	 * 
	 * @param pc
	 */
	public void changeLeader(final L1PcInstance pc) {
		writeC(Opcodes.S_OPCODE_PACKETBOX);
		writeC(S_PacketBox.PATRY_SET_MASTER);
		writeD(pc.getId());
		writeH(0x0000);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}

		return _byte;
	}

	@Override
	public String getType() {
		return _S_Party;
	}

	/**
	 * 新加入队伍的玩家
	 * 
	 * @param pc
	 */
	public void newMember(final L1PcInstance pc) {
		final L1PcInstance leader = pc.getParty().getLeader();
		final L1PcInstance member[] = pc.getParty().getMembers();
		double nowhp = 0.0d;
		double maxhp = 0.0d;
		if (pc.getParty() == null) {
			return;
		}
		else {
			writeC(Opcodes.S_OPCODE_PACKETBOX);
			writeC(S_PacketBox.UPDATE_OLD_PART_MEMBER);
			nowhp = leader.getCurrentHp();
			maxhp = leader.getMaxHp();
			writeC(member.length - 1);
			writeD(leader.getId());
			writeS(leader.getName());
			writeC((int) (nowhp / maxhp) * 100);
			writeD(leader.getMapId());
			writeH(leader.getX());
			writeH(leader.getY());
			for (final L1PcInstance element : member) {
				if ((element.getId() == leader.getId()) || (element == null)) {
					continue;
				}
				nowhp = element.getCurrentHp();
				maxhp = element.getMaxHp();
				writeD(element.getId());
				writeS(element.getName());
				writeC((int) (nowhp / maxhp) * 100);
				writeD(element.getMapId());
				writeH(element.getX());
				writeH(element.getY());
			}
			writeC(0x00);
		}
	}

	/**
	 * 旧的队员
	 * 
	 * @param pc
	 */
	public void oldMember(final L1PcInstance pc) {
		writeC(Opcodes.S_OPCODE_PACKETBOX);
		writeC(S_PacketBox.PATRY_UPDATE_MEMBER);
		writeD(pc.getId());
		writeS(pc.getName());
		writeD(pc.getMapId());
		writeH(pc.getX());
		writeH(pc.getY());
	}

	/**
	 * 更新队伍
	 * 
	 * @param pc
	 */
	public void refreshParty(final L1PcInstance pc) {
		final L1PcInstance member[] = pc.getParty().getMembers();
		if (pc.getParty() == null) {
			return;
		}
		else {
			writeC(Opcodes.S_OPCODE_PACKETBOX);
			writeC(S_PacketBox.PATRY_MEMBERS);
			writeC(member.length);
			for (final L1PcInstance element : member) {
				writeD(element.getId());
				writeD(element.getMapId());
				writeH(element.getX());
				writeH(element.getY());
			}
			writeC(0x00);
		}
	}

	private void buildPacket(final String htmlid, final int objid, final String partyname, final String partymembers, final int type) {
		writeC(Opcodes.S_OPCODE_SHOWHTML);
		writeD(objid);
		writeS(htmlid);
		writeH(type);
		writeH(0x02);
		writeS(partyname);
		writeS(partymembers);
	}

}
