package edu.brandeis.cs.develops.eptosql.translator;

/**
 * 
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */
public class Table extends Relation {
	private String name; /**Name of Table*/
	/**
	 * Creates new table object
	 * @param name name of table
	 */
	public Table(String name) {
		this.setName(name);
	}
	/**
	 * Get name of table
	 * @return name of Table
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set name of table
	 * @param name name to become name of Table
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Checks if referenced table has parameter attribute
	 * @param name the table name being compared
	 * @return true if name parameter equals name of table
	 */
	public Boolean hasAttributes(String name) {
		return this.name.equals(name);
	}
	public String toString() {
		return "Table" + this.name;
	}
}
