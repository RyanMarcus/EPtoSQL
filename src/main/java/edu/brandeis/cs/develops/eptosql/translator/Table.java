/*
Copyright 2014 Brionne Godby, Rachel Leeman-Munk, Ryan Marcus

This file is part of EPtoSQL.

EPtoSQL is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

EPtoSQL is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with EPtoSQL.  If not, see <http://www.gnu.org/licenses/>.
*/


package edu.brandeis.cs.develops.eptosql.translator;


import java.util.Set;

import com.google.common.collect.Sets;


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

	@Override
	public Set<Relation> getChildren() {
		return Sets.newHashSet();
	}
}
