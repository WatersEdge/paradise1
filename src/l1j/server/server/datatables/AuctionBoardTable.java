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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.templates.L1AuctionBoard;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.collections.Maps;

/**
 * 盟屋拍卖资料表
 */
public class AuctionBoardTable {

	private static Logger _log = Logger.getLogger(AuctionBoardTable.class.getName());

	private final Map<Integer, L1AuctionBoard> _boards = Maps.newConcurrentMap();

	private Calendar timestampToCalendar(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ts.getTime());
		return cal;
	}

	/**
	 * 盟屋拍卖资料表
	 */
	public AuctionBoardTable() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM board_auction ORDER BY house_id");
			rs = pstm.executeQuery();
			while (rs.next()) {
				L1AuctionBoard board = new L1AuctionBoard();
				board.setHouseId(rs.getInt(1)); // 盟屋ID
				board.setHouseName(rs.getString(2)); // 盟屋名称
				board.setHouseArea(rs.getInt(3)); // 盟屋面积
				board.setDeadline(timestampToCalendar((Timestamp) rs.getObject(4))); // 盟屋期限
				board.setPrice(rs.getInt(5)); // 盟屋价格
				board.setLocation(rs.getString(6)); // 盟屋位置
				board.setOldOwner(rs.getString(7)); // 盟屋以前的所有者
				board.setOldOwnerId(rs.getInt(8)); // 盟屋以前的所有者ID
				board.setBidder(rs.getString(9)); // 盟屋购买者
				board.setBidderId(rs.getInt(10)); // 盟屋购买者ID
				_boards.put(board.getHouseId(), board);
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
	 * 取得盟屋拍卖列表清单
	 * 
	 * @return
	 */
	public L1AuctionBoard[] getAuctionBoardTableList() {
		return _boards.values().toArray(new L1AuctionBoard[_boards.size()]);
	}

	/**
	 * 取得盟屋拍卖列表
	 * 
	 * @param houseId
	 *            盟屋ID
	 * @return
	 */
	public L1AuctionBoard getAuctionBoardTable(int houseId) {
		return _boards.get(houseId);
	}

	/**
	 * 插入拍卖盟屋
	 * 
	 * @param board
	 *            拍卖布告栏
	 */
	public void insertAuctionBoard(L1AuctionBoard board) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO board_auction SET house_id=?, house_name=?, house_area=?, deadline=?, price=?, location=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=?");
			pstm.setInt(1, board.getHouseId());
			pstm.setString(2, board.getHouseName());
			pstm.setInt(3, board.getHouseArea());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String fm = sdf.format(board.getDeadline().getTime());
			pstm.setString(4, fm);
			pstm.setInt(5, board.getPrice());
			pstm.setString(6, board.getLocation());
			pstm.setString(7, board.getOldOwner());
			pstm.setInt(8, board.getOldOwnerId());
			pstm.setString(9, board.getBidder());
			pstm.setInt(10, board.getBidderId());
			pstm.execute();

			_boards.put(board.getHouseId(), board);
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 更新拍卖盟屋
	 * 
	 * @param board
	 *            拍卖布告栏
	 */
	public void updateAuctionBoard(L1AuctionBoard board) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE board_auction SET house_name=?, house_area=?, deadline=?, price=?, location=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=? WHERE house_id=?");
			pstm.setString(1, board.getHouseName());
			pstm.setInt(2, board.getHouseArea());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String fm = sdf.format(board.getDeadline().getTime());
			pstm.setString(3, fm);
			pstm.setInt(4, board.getPrice());
			pstm.setString(5, board.getLocation());
			pstm.setString(6, board.getOldOwner());
			pstm.setInt(7, board.getOldOwnerId());
			pstm.setString(8, board.getBidder());
			pstm.setInt(9, board.getBidderId());
			pstm.setInt(10, board.getHouseId());
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
	 * 删除拍卖盟屋
	 * 
	 * @param houseId
	 *            盟屋ID
	 */
	public void deleteAuctionBoard(int houseId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM board_auction WHERE house_id=?");
			pstm.setInt(1, houseId);
			pstm.execute();

			_boards.remove(houseId);
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

}
