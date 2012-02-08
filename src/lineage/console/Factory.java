package lineage.console;

import lineage.console.connector.ConnectorPoly;
import lineage.console.connector.ConnectorPotion;
import lineage.console.connector.ConnectorSpellBook;
import lineage.console.connector.cite.CiteConnectorPoly;
import lineage.console.connector.cite.CiteConnectorPotion;
import lineage.console.connector.cite.CiteConnectorSpellBook;

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

	/**
	 * 取得变身类
	 */
	public final static ConnectorPoly getPoly() {
		return new CiteConnectorPoly();
	}

	/**
	 * 取得技能书类
	 */
	public final static ConnectorSpellBook getSpellBook() {
		return new CiteConnectorSpellBook();
	}
}
