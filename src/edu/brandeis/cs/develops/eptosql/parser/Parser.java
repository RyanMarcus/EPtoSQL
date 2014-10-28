package edu.brandeis.cs.develops.eptosql.parser;

import edu.brandeis.cs.develops.eptosql.parser.lexer.Lexer;
import edu.brandeis.cs.develops.eptosql.parser.parser.ShiftReduceParser;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.ASTNode;

public class Parser {
	public ASTNode parseString(String s) {
		ShiftReduceParser srp = new ShiftReduceParser();
		return srp.parseTokens(Lexer.createLexerForString(s));
	}
}
