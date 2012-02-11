package lineage.console;

import lineage.console.connector.ConnectorSpellBook;
import lineage.console.connector.cite.CiteConnectorSpellBook;

/**
 * 类工厂
 * 
 * @author jrwz
 */
public class Factory {

	/**
	 * 取得技能书类
	 */
	public final static ConnectorSpellBook getSpellBook() {
		return new CiteConnectorSpellBook();
	}
}
