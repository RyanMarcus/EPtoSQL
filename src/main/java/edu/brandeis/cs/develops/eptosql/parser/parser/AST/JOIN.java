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


package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class JOIN extends ASTNode {
	public PAREN_STRING_EXPR_EXPR paren_string_expr_expr;
	public JOIN_TYPE join_type;
	
	public JOIN() {
		super(null);
	}
	
	public EXPR getExpr1() {
		return paren_string_expr_expr.getSubExpr1();
	}
	public EXPR getExpr2() {
		return paren_string_expr_expr.getSubExpr2();
	}
	public String getType() {
		return join_type.type;
	}
	public String getPredicate() {
		return paren_string_expr_expr.string;
	}
}
