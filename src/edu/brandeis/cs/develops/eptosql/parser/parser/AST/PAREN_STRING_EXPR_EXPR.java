package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

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
