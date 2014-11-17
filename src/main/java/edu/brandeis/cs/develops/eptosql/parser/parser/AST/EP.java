package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class EP extends ASTNode {
	
	public EXPR expr;
	public EP() {
		super(null);
	}
	public EXPR getExpr() {
		return expr;
	}
}
