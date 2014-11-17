package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class TABLE extends ASTNode {
	public PAREN_STRING paren_string;

	public TABLE() {
		super(null);
	}
	
	public String getName() {
		return paren_string.string;
	}
}
