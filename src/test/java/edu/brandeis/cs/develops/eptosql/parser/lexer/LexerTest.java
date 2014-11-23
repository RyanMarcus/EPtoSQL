package edu.brandeis.cs.develops.eptosql.parser.lexer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.junit.Test;

public class LexerTest {

	@Test
	public void test1() {
		Iterator<Token> it = Lexer.createLexerForString("PTABLE(S)");
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());

	}
	
	@Test
	public void test2() {
		Iterator<Token> it = Lexer.createLexerForString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		assertTrue(it.next().getType() == TokenType.PSELECT);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());

	}
	
	@Test
	public void test3() {
		Iterator<Token> it = Lexer.createLexerForString("PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))");
		assertTrue(it.next().getType() == TokenType.PMJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());

	}
	
	@Test
	public void test3HashJoin() {
		Iterator<Token> it = Lexer.createLexerForString("PHJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))");
		assertTrue(it.next().getType() == TokenType.PHJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());

	}
	
	@Test
	public void test4() {
		Iterator<Token> it = Lexer.createLexerForString("PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))");
		assertTrue(it.next().getType() == TokenType.PNLJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PMJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PSELECT);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());
	}
	
	@Test
	public void test5() {
		Token t = new Token(TokenType.STRING);
		t.setContent("test");
		assertTrue(t.getContent().equals("test"));
	}
	
	@Test
	public void test6() {
		
		String s = "PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))";
		ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
		
		Iterator<Token> it = Lexer.createLexerForInputStream(is);
		assertTrue(it.next().getType() == TokenType.PNLJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PMJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PSELECT);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());
	}

	@Test
	public void test7() {
		String s = "PHJOIN(c_nationkey = n_nationkey, PHJOIN(o_orderkey = l_orderkey, PHJOIN(c_custkey = o_custkey, PTABLE(CUSTOMER), PTABLE(ORDERS)), PSELECT(l_returnflag = 'R',PTABLE(LINEITEM)), PTABLE(NATION))";
		ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
		
		Iterator<Token> it = Lexer.createLexerForInputStream(is);
		
		assertTrue(it.next().getType() == TokenType.PHJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PHJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PHJOIN);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PSELECT);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.COMMA);
		assertTrue(it.next().getType() == TokenType.PTABLE);
		assertTrue(it.next().getType() == TokenType.LP);
		assertTrue(it.next().getType() == TokenType.STRING);
		assertTrue(it.next().getType() == TokenType.RP);
		assertTrue(it.next().getType() == TokenType.RP);
		assertFalse(it.hasNext());



	}
	
}
