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

import l1j.server.Config;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_HPMeter;
import l1j.server.server.serverpackets.S_Party;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.collections.Lists;

// Referenced classes of package l1j.server.server.model:
// L1Party

/**
 * 队伍
 */
public class L1Party {

	/** 成员列表 */
	private final List<L1PcInstance> _membersList = Lists.newList();

	/** 领导者 */
	private L1PcInstance _leader = null;

	/** 增加成员 */
	public void addMember(L1PcInstance pc) {
		if (pc == null) {
			throw new NullPointerException();
		}
		if (((_membersList.size() == Config.MAX_PT) && !_leader.isGm())
				|| _membersList.contains(pc)) {
			return;
		}

		if (_membersList.isEmpty()) {
			// 最初のPTメンバーであればリーダーにする
			setLeader(pc);
		} else {
			createMiniHp(pc);
		}

		_membersList.add(pc);
		pc.setParty(this);
		showAddPartyInfo(pc);
		pc.startRefreshParty();
	}

	/** 删除成员 */
	private void removeMember(L1PcInstance pc) {
		if (!_membersList.contains(pc)) {
			return;
		}
		pc.stopRefreshParty();
		_membersList.remove(pc);
		pc.setParty(null);
		if (!_membersList.isEmpty()) {
			deleteMiniHp(pc);
		}
	}

	/** 空闲的 */
	public boolean isVacancy() {
		return _membersList.size() < Config.MAX_PT;
	}

	/** 获得空闲 */
	public int getVacancy() {
		return Config.MAX_PT - _membersList.size();
	}

	/** 是成员 */
	public boolean isMember(L1PcInstance pc) {
		return _membersList.contains(pc);
	}

	/** 设置领导者 */
	private void setLeader(L1PcInstance pc) {
		_leader = pc;
	}

	/** 获得领导者 */
	public L1PcInstance getLeader() {
		return _leader;
	}

	/** 是领导者 */
	public boolean isLeader(L1PcInstance pc) {
		return pc.getId() == _leader.getId();
	}

	/** 获得成员名称列表 */
	public String getMembersNameList() {
		String _result = new String("");
		for (L1PcInstance pc : _membersList) {
			_result = _result + pc.getName() + " ";
		}
		return _result;
	}

	/** 创建组队血条 */
	private void createMiniHp(L1PcInstance pc) {
		// 成员加入时、显示对方的HP血条
		L1PcInstance[] members = getMembers();

		for (L1PcInstance member : members) {
			member.sendPackets(new S_HPMeter(pc.getId(), 100
					* pc.getCurrentHp() / pc.getMaxHp()));
			pc.sendPackets(new S_HPMeter(member.getId(), 100
					* member.getCurrentHp() / member.getMaxHp()));
		}
	}

	/** 删除组队血条 */
	private void deleteMiniHp(L1PcInstance pc) {
		// 队员离队时、删除HP血条。
		L1PcInstance[] members = getMembers();

		for (L1PcInstance member : members) {
			pc.sendPackets(new S_HPMeter(member.getId(), 0xff));
			member.sendPackets(new S_HPMeter(pc.getId(), 0xff));
		}
	}

	/** 更新组队血条 */
	public void updateMiniHP(L1PcInstance pc) {
		L1PcInstance[] members = getMembers();

		for (L1PcInstance member : members) { // パーティーメンバー分更新
			member.sendPackets(new S_HPMeter(pc.getId(), 100
					* pc.getCurrentHp() / pc.getMaxHp()));
		}
	}

	/** 解散队伍 */
	private void breakup() {
		L1PcInstance[] members = getMembers();

		for (L1PcInstance member : members) {
			removeMember(member);
			member.sendPackets(new S_ServerMessage(418)); // 您解散您的队伍了!!
		}
	}

	/** 通过领导者 */
	public void passLeader(L1PcInstance pc) {
		pc.getParty().setLeader(pc);
		for (L1PcInstance member : getMembers()) {
			member.sendPackets(new S_Party(0x6A, pc));
		}
	}

	/** 离开队员 */
	public void leaveMember(L1PcInstance pc) {
		if (isLeader(pc) || (getNumOfMembers() == 2)) {
			// 有组队领导者的场合
			breakup();
		} else {
			removeMember(pc);
			for (L1PcInstance member : getMembers()) {
				sendLeftMessage(member, pc);
			}
			sendLeftMessage(pc, pc);
			// 没有组队领导者的场合
			/*
			 * if (getNumOfMembers() == 2) { // パーティーメンバーが自分とリーダーのみ
			 * removeMember(pc); L1PcInstance leader = getLeader();
			 * removeMember(leader); sendLeftMessage(pc, pc);
			 * sendLeftMessage(leader, pc); } else { // 残りのパーティーメンバーが２人以上いる
			 * removeMember(pc); for (L1PcInstance member : members) {
			 * sendLeftMessage(member, pc); } sendLeftMessage(pc, pc); }
			 */
		}
	}

	/** 踢成员 */
	public void kickMember(L1PcInstance pc) {
		if (getNumOfMembers() == 2) {
			// パーティーメンバーが自分とリーダーのみ
			breakup();
		} else {
			removeMember(pc);
			for (L1PcInstance member : getMembers()) {
				sendLeftMessage(member, pc);
			}
			sendKickMessage(pc);
		}
	}

	/** 显示加入队伍的信息 */
	private void showAddPartyInfo(L1PcInstance pc) {
		for (L1PcInstance member : getMembers()) {
			if ((pc.getId() == getLeader().getId()) && (getNumOfMembers() == 1)) {
				continue;
			}
			// 发送给队长的封包
			if (pc.getId() == member.getId()) {
				pc.sendPackets(new S_Party(0x68, pc));
			} else { // 其他成员封包
				member.sendPackets(new S_Party(0x69, pc));
			}
			member.sendPackets(new S_Party(0x6e, member));
			createMiniHp(member);
		}
	}

	/** 获得成员 */
	public L1PcInstance[] getMembers() {
		return _membersList.toArray(new L1PcInstance[_membersList.size()]);
	}

	/** 成员数量 */
	public int getNumOfMembers() {
		return _membersList.size();
	}

	/** 发送踢人信息 */
	private void sendKickMessage(L1PcInstance kickpc) {
		kickpc.sendPackets(new S_ServerMessage(419)); // 您从队伍中被驱逐了。
	}

	/** 发送离队信息 */
	private void sendLeftMessage(L1PcInstance sendTo, L1PcInstance left) {
		// %0%s 离开了队伍。
		sendTo.sendPackets(new S_ServerMessage(420, left.getName()));
	}

}
