package edu.brandeis.cs.develops.eptosql.parser.lexer;

import java.io.InputStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import edu.brandeis.cs.develops.eptosql.parser.lexer.util.CharacterInputStreamStack;
/**
 * 
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class Lexer implements Iterator<Token> {

	private Deque<Character> charStack;

	private Lexer(String s) {
		charStack = new LinkedList<Character>();
		for (int i = 0; i < s.length(); i++)
			charStack.add(s.charAt(i));
	}
	
	private Lexer(InputStream s) {
		charStack = new CharacterInputStreamStack(s);
	}

	public static Lexer createLexerForString(String s) {
		return new Lexer(s);
	}
	
	public static Lexer createLexerForInputStream(InputStream s) {
		return new Lexer(s);
	}

	@Override
	public boolean hasNext() {
		return !charStack.isEmpty();
	}

	@Override
	public Token next() {
		churnWhitespace();
		Token t = readNextToken();
		churnWhitespace();
		//System.out.println(charStack);
		return t;
	}

	private Token readNextToken() {
		char next = charStack.pop();

		// check for special chars
		if (next == '(') {
			return new Token(TokenType.LP);
		} else if (next == ')') {
			return new Token(TokenType.RP);
		} else if (next == ',') {
			return new Token(TokenType.COMMA);
		}

		if (next == 'P') {
			// might be one of the keywords: PTABLE PSELECT PMJOIN PNLJOIN
			// PHJOIN
			if (checkStackForKeyword("TABLE")) {
				return new Token(TokenType.PTABLE);
			} else if (checkStackForKeyword("SELECT")) {
				return new Token(TokenType.PSELECT);
			} else if (checkStackForKeyword("MJOIN")) {
				return new Token(TokenType.PMJOIN);
			} else if (checkStackForKeyword("NLJOIN")) {
				return new Token(TokenType.PNLJOIN);
			} else if (checkStackForKeyword("PHJOIN")) {
				return new Token(TokenType.PHJOIN);
			}
		}

		// if we're still here, it's a string.
		StringBuilder sb = new StringBuilder();

		while (next != ',' && next != ')' && !charStack.isEmpty()) {
			sb.append(next);
			next = charStack.pop();
		}

		if (next == ',' || next == ')') {
			// push whatever we just popped off back on
			charStack.push(next);
		}

		Token t = new Token(TokenType.STRING);
		t.setContent(sb.toString());
		return t;
	}

	private boolean checkStackForKeyword(String keyword) {
		Iterator<Character> it = charStack.iterator();
		for (int i = 0; i < keyword.length(); i++) {
			if (!it.hasNext())
				return false;
			if (keyword.charAt(i) != it.next()) {
				return false;
			}
		}
		
		// it's a match!
		for (int i = 0; i < keyword.length(); i++)
			charStack.pop();

		return true;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("You cannot remove a token");
	}

	private void churnWhitespace() {
		while (!charStack.isEmpty() && Character.isWhitespace(charStack.peek()))
			charStack.pop();
	}

	public static void main(String[] args) {
		Iterator<Token> it = Lexer.createLexerForString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
