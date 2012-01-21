package lineage.console;

import lineage.console.connector.ConnectorPotion;
import lineage.console.connector.cite.CiteConnectorPotion;

/**
 * 类工厂
 * 
 * @author jrwz
 */
public class Factory {

	/**
	 * 取得药水类
	 */
	public final static ConnectorPotion getPotion() {
		return new CiteConnectorPotion();
	}
}
