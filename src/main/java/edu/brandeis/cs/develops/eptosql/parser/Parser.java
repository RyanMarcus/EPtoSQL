package edu.brandeis.cs.develops.eptosql.parser;

import java.io.InputStream;

import edu.brandeis.cs.develops.eptosql.parser.lexer.Lexer;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.ShiftReduceParser;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.ASTNode;
/**
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 * 
 * A frontend to the lexer and parser of the compiler. Can take either
 * a raw string or an input stream. Will give an expanded AST that is then
 * passed into the translator.
 * 
 */
public class Parser {
	public ASTNode parseString(String s) throws ParserException {
		ShiftReduceParser srp = new ShiftReduceParser();
		return srp.parseTokens(Lexer.createLexerForString(s));
	}
	
	public ASTNode parseStream(InputStream s) throws ParserException {
		ShiftReduceParser srp = new ShiftReduceParser();
		return srp.parseTokens(Lexer.createLexerForInputStream(s));
	}
}
