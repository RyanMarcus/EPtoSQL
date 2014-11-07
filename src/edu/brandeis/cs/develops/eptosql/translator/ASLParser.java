package edu.brandeis.cs.develops.eptosql.translator;

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;

public class ASLParser {
	public Relation parse(EP ep) {
		ExpressionParser rp = new ExpressionParser();
		return rp.parseExpr(ep.getExpr());
	}
	
}
