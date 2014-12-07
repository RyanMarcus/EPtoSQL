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

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
/**
 * Translates from Abstract Syntax Tree(AST) to Relation Tree
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */
public class ASTTranslator {
	/**
	 * Parses an expression(subtree of AST) into a Relation
	 * @param ep Expression ep
	 * @return Expression ep parsed into a Relation
	 */
	public Relation parse(EP ep) {
		ExpressionParser rp = new ExpressionParser();
		return rp.parseExpr(ep.getExpr());
	}
	
}
