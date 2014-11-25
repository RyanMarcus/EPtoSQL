package edu.brandeis.cs.develops.eptosql.semantic_analysis;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Relation;

public class SemanticAnalyzerTest {

	private SemanticAnalyzer sa;
	private Parser p;
	private ASTTranslator astt;
	
	@Before
	public void setUp() throws Exception {
		sa = new SemanticAnalyzer();
		p = new Parser();
		astt = new ASTTranslator();
	}
	
	@Test
	public void test1() throws ParserException {
		EP ep = (EP) p.parseString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		Relation r = astt.parse(ep);
		assertTrue(sa.analyze(r).size() == 0);
		
	}
	
	@Test
	public void test2() throws ParserException {
		EP ep = (EP) p.parseString("PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))");
		Relation r = astt.parse(ep);
		assertTrue(sa.analyze(r).size() == 0);		
	}
	
	@Test
	public void test3() throws ParserException {
		EP ep = (EP) p.parseString("PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))");
		Relation r = astt.parse(ep);
		assertTrue(sa.analyze(r).size() == 0);	
	}
	
	@Test
	public void test4() throws ParserException {
		EP ep = (EP) p.parseString("PMJOIN(a = b, PSELECT(a = b, PTABLE(A)), PTABLE(B))");
		Relation r = astt.parse(ep);
		assertTrue(sa.analyze(r).size() != 0);
	}
	
	@Test
	public void test5() throws ParserException {
		EP ep = (EP) p.parseString("PHJOIN(a = b, PSELECT(a = b, PTABLE(A)), PTABLE(B))");
		Relation r = astt.parse(ep);
		assertTrue(sa.analyze(r).size() != 0);
	}
	
	@Test
	public void test6() throws ParserException {
		EP ep = (EP) p.parseString("PSELECT(a = b, PHJOIN(a = b, PTABLE(A), PTABLE(B)))");
		Relation r = astt.parse(ep);
		assertTrue(sa.analyze(r).size() != 0);
	}



}
