package edu.brandeis.cs.develops.eptosql.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;

public class ParserTest {

	
	private Parser p;
	@Before
	public void setUp() throws Exception {
		p = new Parser();
	}

	@Test
	public void test() {
		EP ep = (EP) p.parseString("PTABLE(PS)");
		assertTrue(ep.expr.table.paren_string.string.equals("PS"));
	}
	
	@Test
	public void test2() {
		EP ep = (EP) p.parseString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		assertTrue(ep.expr.selection.paren_string_expr.string.equals("n_name = 'ASIA'"));
		assertTrue(ep.expr.selection.paren_string_expr.expr.expr.table.paren_string.string.equals("N"));
	}
	
	@Test
	public void test3() {
		EP ep = (EP) p.parseString("PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))");
		assertTrue(ep.expr.join.join_type.type.equals("PMJOIN"));
		assertTrue(ep.expr.join.paren_string_expr_expr.string.equals("ps_suppkey = s_suppkey"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr1.expr.table.paren_string.string.equals("PS"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr2.expr.table.paren_string.string.equals("S"));
	}
	
	@Test
	public void test4() {
		EP ep = (EP) p.parseString("PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))");
		assertTrue(ep.expr.join.join_type.type.equals("PNLJOIN"));
		assertTrue(ep.expr.join.paren_string_expr_expr.string.equals("s_nationkey = n_nationkey"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr1.expr.join.join_type.type.equals("PMJOIN"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr1.expr.join.paren_string_expr_expr.double_comma_expr.expr1.expr.table.paren_string.string.equals("PS"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr1.expr.join.paren_string_expr_expr.double_comma_expr.expr2.expr.table.paren_string.string.equals("S"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr2.expr.selection.paren_string_expr.string.equals("n_name = 'ASIA'"));
		assertTrue(ep.expr.join.paren_string_expr_expr.double_comma_expr.expr2.expr.selection.paren_string_expr.expr.expr.table.paren_string.string.equals("N"));

	}

}
