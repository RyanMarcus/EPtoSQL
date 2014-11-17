package edu.brandeis.cs.develops.eptosql.parser.parser.AST;

import edu.brandeis.cs.develops.eptosql.parser.lexer.Token;
import edu.brandeis.cs.develops.eptosql.parser.lexer.TokenType;

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class ASTNode {
	private Token t;
	
	public ASTNode(Token t) {
		this. t = t;
	}
	
	public boolean isTokenType(TokenType type) {
		return (t != null && t.getType() == type);
	}
	
	public Token getToken() {
		return t;
	}
	
	public String toString() {
		if (this.t == null) {
			return this.getClass().getSimpleName();
		}
		
		return this.t.getType().toString();
	}
}
