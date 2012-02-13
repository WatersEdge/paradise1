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

// Referenced classes of package l1j.server.server.templates:
// L1PrivateShopSellList

/**
 * 个人商店贩卖清单
 */
public class L1PrivateShopSellList {

	private int _itemObjectId;

	private int _sellTotalCount; // 预期销售数量

	private int _sellPrice;

	private int _sellCount; // 共售出

	public L1PrivateShopSellList() {
	}

	public int getItemObjectId() {
		return _itemObjectId;
	}

	public int getSellCount() {
		return _sellCount;
	}

	public int getSellPrice() {
		return _sellPrice;
	}

	public int getSellTotalCount() {
		return _sellTotalCount;
	}

	public void setItemObjectId(int i) {
		_itemObjectId = i;
	}

	public void setSellCount(int i) {
		_sellCount = i;
	}

	public void setSellPrice(int i) {
		_sellPrice = i;
	}

	public void setSellTotalCount(int i) {
		_sellTotalCount = i;
	}
}
