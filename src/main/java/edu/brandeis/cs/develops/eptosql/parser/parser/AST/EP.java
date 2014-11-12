package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class EP extends ASTNode {
	
	public EXPR expr;
	public EP() {
		super(null);
	}
	public EXPR getExpr() {
		return expr;
	}
}
