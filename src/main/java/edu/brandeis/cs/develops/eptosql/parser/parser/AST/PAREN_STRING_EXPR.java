package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
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
