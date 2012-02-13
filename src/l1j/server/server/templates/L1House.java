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

public class L1House {
	private int _houseId;

	private String _houseName;

	private int _houseArea;

	private String _location;

	private int _keeperId;

	private boolean _isOnSale;

	private boolean _isPurchaseBasement;

	private Calendar _taxDeadline;

	public L1House() {
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

	public int getKeeperId() {
		return _keeperId;
	}

	public String getLocation() {
		return _location;
	}

	public Calendar getTaxDeadline() {
		return _taxDeadline;
	}

	public boolean isOnSale() {
		return _isOnSale;
	}

	public boolean isPurchaseBasement() {
		return _isPurchaseBasement;
	}

	public void setHouseArea(final int i) {
		_houseArea = i;
	}

	public void setHouseId(final int i) {
		_houseId = i;
	}

	public void setHouseName(final String s) {
		_houseName = s;
	}

	public void setKeeperId(final int i) {
		_keeperId = i;
	}

	public void setLocation(final String s) {
		_location = s;
	}

	public void setOnSale(final boolean flag) {
		_isOnSale = flag;
	}

	public void setPurchaseBasement(final boolean flag) {
		_isPurchaseBasement = flag;
	}

	public void setTaxDeadline(final Calendar i) {
		_taxDeadline = i;
	}

}