package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class TABLE extends ASTNode {
	public PAREN_STRING paren_string;

	public TABLE() {
		super(null);
	}
	
	public String getName() {
		return paren_string.string;
	}
}
