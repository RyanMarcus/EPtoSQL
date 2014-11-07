package edu.brandeis.cs.develops.eptosql.translator;

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.JOIN;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.SELECTION;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.TABLE;

public class ExpressionParser {
	public Relation parseExpr(EXPR expr) {
		if (expr.isTable()) {
			return parseTable(expr.getTable());
		} else if (expr.isSelection()) {
			return parseSelection(expr.getSelection());
		} else if (expr.isJoin()) {
			return parseJoin(expr.getJoin());
		}
		return null;
	}
	private Relation parseJoin(JOIN join) {
		return new Join(parseExpr(join.getExpr1()),parseExpr(join.getExpr2()),join.getPredicate(),join.getType());
	}
	private Relation parseSelection(SELECTION selection) {
		return new Selection(parseExpr(selection.getBaseEXPR()),selection.getPredicate());
	}
	public Relation parseTable(TABLE table) {
		return new Table(table.getName());
	}
}
