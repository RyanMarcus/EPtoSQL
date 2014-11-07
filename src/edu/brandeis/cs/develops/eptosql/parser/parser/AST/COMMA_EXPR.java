package edu.brandeis.cs.develops.eptosql.parser.parser.AST;


public class COMMA_EXPR extends ASTNode {
	public EXPR expr;
	
	public COMMA_EXPR() {
		super(null);
	}
	
	public EXPR getExpr() {
		return expr;
	}

}
