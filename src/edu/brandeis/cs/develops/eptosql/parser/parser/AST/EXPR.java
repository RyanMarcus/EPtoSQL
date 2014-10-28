package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class EXPR extends ASTNode {
	public SELECTION selection = null;
	public JOIN join = null;
	public TABLE table = null;
	
	public EXPR() {
		super(null);
	}
}
