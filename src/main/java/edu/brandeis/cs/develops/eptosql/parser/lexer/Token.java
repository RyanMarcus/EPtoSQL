package edu.brandeis.cs.develops.eptosql.parser.lexer;

public class Token {
	
	public Token(TokenType t) {
		type = t;
	}
	
	private TokenType type;
	private String content;
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String toString() {
		if (this.getContent() == null)
			return type.toString();
		
		return type.toString() + " (" + getContent() + ")";
	}
}
