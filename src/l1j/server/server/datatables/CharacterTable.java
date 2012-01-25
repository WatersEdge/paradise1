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
package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.storage.CharacterStorage;
import l1j.server.server.storage.mysql.MySqlCharacterStorage;
import l1j.server.server.templates.L1CharName;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.collections.Maps;

/**
 * 角色资料表
 */
public class CharacterTable {

	private static Logger _log = Logger.getLogger(CharacterTable.class.getName());

	/** 存储角色 */
	private final CharacterStorage _charStorage;

	private static CharacterTable _instance;

	/** 角色名称清单 */
	private final Map<String, L1CharName> _charNameList = Maps.newConcurrentMap();

	private CharacterTable() {
		_charStorage = new MySqlCharacterStorage();
	}

	public static CharacterTable getInstance() {
		if (_instance == null) {
			_instance = new CharacterTable();
		}
		return _instance;
	}

	/**
	 * 储存新的角色
	 * 
	 * @param pc
	 */
	public void storeNewCharacter(L1PcInstance pc) throws Exception {
		synchronized (pc) {
			_charStorage.createCharacter(pc);
			String name = pc.getName();
			if (!_charNameList.containsKey(name)) {
				L1CharName cn = new L1CharName();
				cn.setName(name);
				cn.setId(pc.getId());
				_charNameList.put(name, cn);
			}
			_log.finest("储存新的角色");
		}
	}

	/**
	 * 储存角色
	 * 
	 * @param pc
	 */
	public void storeCharacter(L1PcInstance pc) throws Exception {
		synchronized (pc) {
			_charStorage.storeCharacter(pc);
			_log.finest("储存角色: " + pc.getName());
		}
	}

	/**
	 * 删除角色
	 * 
	 * @param accountName
	 *            账号名称
	 * @param charName
	 *            角色名称
	 */
	public void deleteCharacter(String accountName, String charName) throws Exception {
		// 也许，不需要同步
		_charStorage.deleteCharacter(accountName, charName);
		if (_charNameList.containsKey(charName)) {
			_charNameList.remove(charName);
		}
		_log.finest("删除角色");
	}

	/**
	 * 恢复角色
	 * 
	 * @param charName
	 *            角色名称
	 * @return pc
	 */
	public L1PcInstance restoreCharacter(String charName) throws Exception {
		L1PcInstance pc = _charStorage.loadCharacter(charName);
		return pc;
	}

	/**
	 * 加载角色
	 * 
	 * @param charName
	 *            角色名称
	 */
	public L1PcInstance loadCharacter(String charName) throws Exception {
		L1PcInstance pc = null;
		try {
			pc = restoreCharacter(charName);

			// 取回角色所在地图ID
			L1Map map = L1WorldMap.getInstance().getMap(pc.getMapId());

			// 角色在地图不可用位置
			if (!map.isInMap(pc.getX(), pc.getY())) {
				pc.setX(33087);
				pc.setY(33396);
				pc.setMap((short) 4);
			}

			/*
			 * if(l1pcinstance.getClanid() != 0) { L1Clan clan = new L1Clan(); ClanTable clantable = new ClanTable(); clan = clantable.getClan(l1pcinstance.getClanname()); l1pcinstance.setClanname(clan.GetClanName()); }
			 */
			_log.finest("载入角色: " + pc.getName());
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return pc;

	}

	/**
	 * 明确在线状态
	 */
	public static void clearOnlineStatus() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET OnlineStatus=0");
			pstm.execute();
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 更新在线状态
	 * 
	 * @param pc
	 */
	public static void updateOnlineStatus(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET OnlineStatus=? WHERE objid=?");
			pstm.setInt(1, pc.getOnlineStatus());
			pstm.setInt(2, pc.getId());
			pstm.execute();
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 更新好友ID
	 * 
	 * @param targetId
	 */
	public static void updatePartnerId(int targetId) {
		updatePartnerId(targetId, 0);
	}

	/**
	 * 更新好友ID
	 * 
	 * @param targetId
	 * @param partnerId
	 */
	public static void updatePartnerId(int targetId, int partnerId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET PartnerID=? WHERE objid=?");
			pstm.setInt(1, partnerId);
			pstm.setInt(2, targetId);
			pstm.execute();
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 储存角色状态
	 * 
	 * @param pc
	 */
	public static void saveCharStatus(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET OriginalStr= ?" + ", OriginalCon= ?, OriginalDex= ?, OriginalCha= ?" + ", OriginalInt= ?, OriginalWis= ?" + " WHERE objid=?");
			pstm.setInt(1, pc.getBaseStr());
			pstm.setInt(2, pc.getBaseCon());
			pstm.setInt(3, pc.getBaseDex());
			pstm.setInt(4, pc.getBaseCha());
			pstm.setInt(5, pc.getBaseInt());
			pstm.setInt(6, pc.getBaseWis());
			pstm.setInt(7, pc.getId());
			pstm.execute();
		}
		catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 恢复背包道具
	 * 
	 * @param pc
	 */
	public void restoreInventory(L1PcInstance pc) {
		pc.getInventory().loadItems(); // 加载背包道具
		pc.getDwarfInventory().loadItems(); // 加载个人仓库道具
		pc.getDwarfForElfInventory().loadItems(); // 加载精灵仓库道具
	}

	/**
	 * 角色名称是否存在
	 * 
	 * @param name
	 *            名称
	 */
	public static boolean doesCharNameExist(String name) {
		boolean result = true;
		java.sql.Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT account_name FROM characters WHERE char_name=?");
			pstm.setString(1, name);
			rs = pstm.executeQuery();
			result = rs.next();
		}
		catch (SQLException e) {
			_log.warning("could not check existing charname:" + e.getMessage());
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return result;
	}

	/**
	 * 加载所有角色名称
	 */
	public void loadAllCharName() {
		L1CharName cn = null;
		String name = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM characters");
			rs = pstm.executeQuery();
			while (rs.next()) {
				cn = new L1CharName();
				name = rs.getString("char_name");
				cn.setName(name);
				cn.setId(rs.getInt("objid"));
				_charNameList.put(name, cn);
			}
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 取得角色名称列表
	 * 
	 * @return
	 */
	public L1CharName[] getCharNameList() {
		return _charNameList.values().toArray(new L1CharName[_charNameList.size()]);
	}

}
