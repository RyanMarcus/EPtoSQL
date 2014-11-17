package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class EXPR extends ASTNode {
	public SELECTION selection = null;
	public JOIN join = null;
	public TABLE table = null;
	
	public EXPR() {
		super(null);
	}
	public Boolean isSelection() {
		return selection!=null;
	}
	public Boolean isJoin() {
		return join!=null;
	}
	public Boolean isTable() {
		return table!=null;
	}
	public SELECTION getSelection() {
		return selection;
	}
	public JOIN getJoin() {
		return join;
	}
	public TABLE getTable() {
		return table;
	}
}
