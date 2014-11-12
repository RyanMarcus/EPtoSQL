package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class SELECTION extends ASTNode {
	public PAREN_STRING_EXPR paren_string_expr;
	
	public SELECTION() {
		super(null);
	}
	public String getPredicate() {
		return paren_string_expr.string;
	}
	public EXPR getBaseEXPR() {
		return paren_string_expr.getExpr().getExpr();
	}
}
