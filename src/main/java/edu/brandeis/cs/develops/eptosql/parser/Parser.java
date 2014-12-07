/*
Copyright 2014 Brionne Godby, Rachel Leeman-Munk, Ryan Marcus

This file is part of EPtoSQL.

EPtoSQL is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

EPtoSQL is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with EPtoSQL.  If not, see <http://www.gnu.org/licenses/>.
*/


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
