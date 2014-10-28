package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class JOIN extends ASTNode {
	public PAREN_STRING_EXPR_EXPR paren_string_expr_expr;
	public JOIN_TYPE join_type;
	
	public JOIN() {
		super(null);
	}
}
