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

import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.utils.collections.Lists;

// Referenced classes of package l1j.server.server.model:
// L1MobGroupInfo

/**
 * MOB群组信息
 */
public class L1MobGroupInfo {

	/** 成员列表 */
	private final List<L1NpcInstance> _membersList = Lists.newList();
	/** 领导者 */
	private L1NpcInstance _leader;

	/** 产生 */
	private L1Spawn _spawn;

	/** 删除群组 */
	private boolean _isRemoveGroup;

	public L1MobGroupInfo() {
	}

	/** 增加成员 */
	public void addMember(L1NpcInstance npc) {
		if (npc == null) {
			throw new NullPointerException();
		}

		// 最初のメンバーであればリーダーにする
		if (_membersList.isEmpty()) {
			setLeader(npc);
			// リーダーの再ポップ情報を保存する
			if (npc.isReSpawn()) {
				setSpawn(npc.getSpawn());
			}
		}

		if (!_membersList.contains(npc)) {
			_membersList.add(npc);
		}
		npc.setMobGroupInfo(this);
		npc.setMobGroupId(_leader.getId());
	}

	/** 取得领导者 */
	public L1NpcInstance getLeader() {
		return _leader;
	}

	/** NUM各成员 */
	public int getNumOfMembers() {
		return _membersList.size();
	}

	/** 取得产生 */
	public L1Spawn getSpawn() {
		return _spawn;
	}

	/** 领导者 */
	public boolean isLeader(L1NpcInstance npc) {
		return npc.getId() == _leader.getId();
	}

	/** 删除群组 */
	public boolean isRemoveGroup() {
		return _isRemoveGroup;
	}

	/** 删除成员 */
	public synchronized int removeMember(L1NpcInstance npc) {
		if (npc == null) {
			throw new NullPointerException();
		}

		if (_membersList.contains(npc)) {
			_membersList.remove(npc);
		}
		npc.setMobGroupInfo(null);

		// リーダーで他のメンバーがいる場合は、新リーダーにする
		if (isLeader(npc)) {
			if (isRemoveGroup() && (_membersList.size() != 0)) { // リーダーが死亡したらグループ解除する場合
				for (L1NpcInstance minion : _membersList) {
					minion.setMobGroupInfo(null);
					minion.setSpawn(null);
					minion.setreSpawn(false);
				}
				return 0;
			}
			if (_membersList.size() != 0) {
				setLeader(_membersList.get(0));
			}
		}

		// 返回剩余的成员数目
		return _membersList.size();
	}

	/** 设定领导者 */
	public void setLeader(L1NpcInstance npc) {
		_leader = npc;
	}

	/** 设定删除群组 */
	public void setRemoveGroup(boolean flag) {
		_isRemoveGroup = flag;
	}

	/** 设定产生 */
	public void setSpawn(L1Spawn spawn) {
		_spawn = spawn;
	}

}
