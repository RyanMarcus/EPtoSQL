package edu.brandeis.cs.develops.eptosql.translator;

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
/**
 * 
 * @author rachel
 *
 */
public class ASTTranslator {
	public Relation parse(EP ep) {
		ExpressionParser rp = new ExpressionParser();
		return rp.parseExpr(ep.getExpr());
	}
	
}
