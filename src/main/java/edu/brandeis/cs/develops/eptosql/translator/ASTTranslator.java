package edu.brandeis.cs.develops.eptosql.translator;

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
/**
 * Translates from Abstract Syntax Tree(AST) to Relation Tree
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */
public class ASTTranslator {
	/**
	 * Parses an expression(subtree of AST) into a Relation
	 * @param ep Expression ep
	 * @return Expression ep parsed into a Relation
	 */
	public Relation parse(EP ep) {
		ExpressionParser rp = new ExpressionParser();
		return rp.parseExpr(ep.getExpr());
	}
	
}
