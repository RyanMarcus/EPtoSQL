package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class PAREN_STRING_EXPR extends ASTNode {
	public String string;
	public COMMA_EXPR expr;
	
	public PAREN_STRING_EXPR() {
		super(null);
	}
	
	public COMMA_EXPR getExpr() {
		return expr;
	}
}
