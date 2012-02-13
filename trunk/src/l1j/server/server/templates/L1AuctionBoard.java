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
package l1j.server.server.templates;

import java.util.Calendar;

public class L1AuctionBoard {
	private int _houseId;

	private String _houseName;

	private int _houseArea;

	private Calendar _deadline;

	private int _price;

	private String _location;

	private String _oldOwner;

	private int _oldOwnerId;

	private String _bidder;

	private int _bidderId;

	public L1AuctionBoard() {
	}

	public String getBidder() {
		return _bidder;
	}

	public int getBidderId() {
		return _bidderId;
	}

	public Calendar getDeadline() {
		return _deadline;
	}

	public int getHouseArea() {
		return _houseArea;
	}

	public int getHouseId() {
		return _houseId;
	}

	public String getHouseName() {
		return _houseName;
	}

	public String getLocation() {
		return _location;
	}

	public String getOldOwner() {
		return _oldOwner;
	}

	public int getOldOwnerId() {
		return _oldOwnerId;
	}

	public int getPrice() {
		return _price;
	}

	public void setBidder(String s) {
		_bidder = s;
	}

	public void setBidderId(int i) {
		_bidderId = i;
	}

	public void setDeadline(Calendar i) {
		_deadline = i;
	}

	public void setHouseArea(int i) {
		_houseArea = i;
	}

	public void setHouseId(int i) {
		_houseId = i;
	}

	public void setHouseName(String s) {
		_houseName = s;
	}

	public void setLocation(String s) {
		_location = s;
	}

	public void setOldOwner(String s) {
		_oldOwner = s;
	}

	public void setOldOwnerId(int i) {
		_oldOwnerId = i;
	}

	public void setPrice(int i) {
		_price = i;
	}

}