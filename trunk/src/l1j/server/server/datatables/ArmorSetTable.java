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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.templates.L1ArmorSets;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.collections.Lists;

/**
 * 套装设置资料表
 */
public class ArmorSetTable {

	/** 提示信息 */
	private static Logger _log = Logger.getLogger(ArmorSetTable.class.getName());

	private static ArmorSetTable _instance;

	public static ArmorSetTable getInstance() {
		if (_instance == null) {
			_instance = new ArmorSetTable();
		}
		return _instance;
	}

	/** 套装清单 */
	private final List<L1ArmorSets> _armorSetList = Lists.newList();

	private ArmorSetTable() {
		this.load();
	}

	/**
	 * 传回所有清单
	 * 
	 * @return 所有套装列表
	 */
	public L1ArmorSets[] getAllList() {
		return this._armorSetList.toArray(new L1ArmorSets[this._armorSetList.size()]);
	}

	/**
	 * 写入资料表
	 * 
	 * @param rs
	 */
	private void fillTable(final ResultSet rs) throws SQLException {
		while (rs.next()) {
			final L1ArmorSets as = new L1ArmorSets();
			as.setId(rs.getInt("id")); // 流水编号 (不能重复)
			as.setSets(rs.getString("sets")); // 组件 (几件装备组成一套)
			as.setPolyId(rs.getInt("polyid")); // 变身编号
			as.setAc(rs.getInt("ac")); // 增加物理防御
			as.setHp(rs.getInt("hp")); // 增加血量
			as.setMp(rs.getInt("mp")); // 增加魔量
			as.setHpr(rs.getInt("hpr")); // 增加回血量
			as.setMpr(rs.getInt("mpr")); // 增加回魔量
			as.setMr(rs.getInt("mr")); // 增加魔法防御
			as.setStr(rs.getInt("str")); // 增加力量
			as.setDex(rs.getInt("dex")); // 增加敏捷
			as.setCon(rs.getInt("con")); // 增加体质
			as.setWis(rs.getInt("wis")); // 增加精神
			as.setCha(rs.getInt("cha")); // 增加魅力
			as.setIntl(rs.getInt("intl")); // 增加智力
			as.setHitModifier(rs.getInt("hit_modifier")); // 增加近距离命中率
			as.setDmgModifier(rs.getInt("dmg_modifier")); // 增加近距离伤害值
			as.setBowHitModifier(rs.getInt("bow_hit_modifier")); // 增加远距离命中率
			as.setBowDmgModifier(rs.getInt("bow_dmg_modifier")); // 增加远距离伤害值
			as.setSp(rs.getInt("sp")); // 增加魔法攻击力
			as.setDefenseWater(rs.getInt("defense_water")); // 增加水属性防御
			as.setDefenseWind(rs.getInt("defense_wind")); // 增加风属性防御
			as.setDefenseFire(rs.getInt("defense_fire")); // 增加火属性防御
			as.setDefenseEarth(rs.getInt("defense_earth")); // 增加地属性防御

			this._armorSetList.add(as);
		}
	}

	private void load() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM armor_set");
			rs = pstm.executeQuery();
			this.fillTable(rs);
		}
		catch (final SQLException e) {
			_log.log(Level.SEVERE, "创建armor_set表时出现错误", e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

}
