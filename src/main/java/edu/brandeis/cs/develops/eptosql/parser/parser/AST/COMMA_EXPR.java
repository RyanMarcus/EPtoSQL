package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class COMMA_EXPR extends ASTNode {
	public EXPR expr;
	
	public COMMA_EXPR() {
		super(null);
	}
	
	public EXPR getExpr() {
		return expr;
	}

}
