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

    @Override
    public String toString() {
        return this.generateSQL();
    }

    public String generateSQL() {
        if (whereClause.length() > 0) {
            return String.format("SELECT * FROM %s %s WHERE %s", fromClause, joinClause, whereClause);
        } else {
            return String.format("SELECT * FROM %s %s", fromClause, joinClause);
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
}
