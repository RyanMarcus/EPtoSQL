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
public class PAREN_STRING_EXPR_EXPR extends ASTNode {
	
	public String string;
	public DOUBLE_COMMA_EXPR double_comma_expr;
	
	public PAREN_STRING_EXPR_EXPR() {
		super(null);
	}
	public String getString() {
		return string;
	}
	public EXPR getSubExpr1() {
		return double_comma_expr.getExpr1().getExpr();
	}
	public EXPR getSubExpr2() {
		return double_comma_expr.getExpr2().getExpr();
	}
}
