package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class DOUBLE_COMMA_EXPR extends ASTNode {
	public COMMA_EXPR expr1;
	public COMMA_EXPR expr2;
	
	public DOUBLE_COMMA_EXPR() {
		super(null);
	}
	public COMMA_EXPR getExpr1() {
		return expr1;
	}
	public COMMA_EXPR getExpr2() {
		return expr2;
	}
}
