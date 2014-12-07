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

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.JOIN;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.SELECTION;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.TABLE;

/**
 * Parses Expressions (subtree of AST) into Relation Trees
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */
public class ExpressionParser {
	/**
	 * Parses an expression(subtree of AST) into a Relation
	 * @param ep Expression ep
	 * @return Expression ep parsed into a Relation
	 */
	public Relation parseExpr(EXPR expr) {
		if (expr.isTable()) {
			return parseTable(expr.getTable());
		} else if (expr.isSelection()) {
			return parseSelection(expr.getSelection());
		} else if (expr.isJoin()) {
			return parseJoin(expr.getJoin());
		}
		return null;
	}
	/**
	 * Parses a JOIN into a Join
	 * @param join a JOIN (subtree of AST)
	 * @return join parsed as a Join (subtree of Relation)
	 */
	private Relation parseJoin(JOIN join) {
		return new Join(parseExpr(join.getExpr1()),parseExpr(join.getExpr2()),join.getPredicate(),join.getType());
	}
	/**
	 * Parses a SELECTION into a Selection
	 * @param selection a SELECTION (subtree of AST)
	 * @return selection parsed as a Selection (subtree of Relation)
	 */
	private Relation parseSelection(SELECTION selection) {
		return new Selection(parseExpr(selection.getBaseEXPR()),selection.getPredicate());
	}
	/**
	 * Parses a TABLE into a Table
	 * @param table a TABLE (subtree of AST)
	 * @return table parsed as a Table (subtree of Relation)
	 */
	public Relation parseTable(TABLE table) {
		return new Table(table.getName());
	}
}
