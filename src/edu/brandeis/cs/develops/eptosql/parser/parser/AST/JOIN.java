package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

public class JOIN extends ASTNode {
	public PAREN_STRING_EXPR_EXPR paren_string_expr_expr;
	public JOIN_TYPE join_type;
	
	public JOIN() {
		super(null);
	}
	
	public EXPR getExpr1() {
		return paren_string_expr_expr.getSubExpr1();
	}
	public EXPR getExpr2() {
		return paren_string_expr_expr.getSubExpr2();
	}
	public String getType() {
		return join_type.type;
	}
	public String getPredicate() {
		return paren_string_expr_expr.string;
	}
}
