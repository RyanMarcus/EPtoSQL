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


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brandeis.cs.develops.eptosql.code_generator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Brionne Godby
 */
public class SQLDto {

    private final StringBuilder fromClause = new StringBuilder();
    private final StringBuilder whereClause = new StringBuilder();
    private final StringBuilder joinClause = new StringBuilder();
    private final AtomicInteger alias_index = new AtomicInteger(1);
    private String into;
    
    @Override
    public String toString() {
        return this.generateSQL();
    }

    public String generateSQL() {
    	String intoClause = "";
    	
    	if (into != null) {
    		intoClause = "INTO " + into;
    	}
    	
    	
        if (whereClause.length() > 0) {
            return String.format("SELECT * %s FROM %s %s WHERE %s", intoClause, fromClause, joinClause, whereClause);
        } else {
            return String.format("SELECT * %s FROM %s %s", intoClause, fromClause, joinClause);
        }
    }

    public StringBuilder getFromClause() {
        return fromClause;
    }

    public StringBuilder getWhereClause() {
        return whereClause;
    }

    public StringBuilder getJoinClause() {
        return joinClause;
    }

    public void appendFrom(String from) {
        if (fromClause.length() > 0) {
            fromClause.append(", ");
        }
        fromClause.append(from);
    }

    public void appendWhere(String where) {
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }
        whereClause.append(where);
    }

    public void appendJoin(String join) {
        joinClause.append(join);
    }

    public String getNextAlias() {

        return String.format("table_", alias_index.getAndIncrement());
    }

	public void setInto(String into) {
		this.into = into;
	}
}
