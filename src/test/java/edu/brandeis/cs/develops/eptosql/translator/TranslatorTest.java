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


package edu.brandeis.cs.develops.eptosql.translator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs.develops.eptosql.parser.*;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;

public class TranslatorTest {
	private Parser p;
	private ASTTranslator astt;
	
	@Before
	public void setup() {
		p = new Parser();
		astt = new ASTTranslator();
	}
	@Test
	public void test() throws ParserException {
		EP ep = (EP) p.parseString("PTABLE(PS)");
		Relation r = astt.parse(ep);
		assertTrue(r instanceof Table);
		Table rt = (Table)r;
		assertTrue(rt.hasAttributes("PS"));
	}
	@Test
	public void test2() throws ParserException {
		EP ep = (EP) p.parseString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		Relation r = astt.parse(ep);
		assertTrue(r instanceof Selection);
		Selection rs = (Selection)r;
		assertTrue(rs.hasAttributes("n_name = 'ASIA'"));
		assertTrue(rs.getChild() instanceof Table);
		assertTrue(((Table)rs.getChild()).hasAttributes("N"));
	}
	@Test
	public void test3() throws ParserException {
		EP ep = (EP) p.parseString("PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))");
		Relation r = astt.parse(ep);
		assertTrue(r instanceof Join);
		Join rj = (Join)r;
		assertTrue(rj.hasAttributes(JoinType.PMJOIN, "ps_suppkey = s_suppkey"));
		assertTrue(rj.getLeftChild() instanceof Table);
		assertTrue(rj.getRightChild() instanceof Table);
		Table leftChild = (Table)rj.getLeftChild();
		Table rightChild = (Table)rj.getRightChild();
		assertTrue(leftChild.hasAttributes("PS"));
		assertTrue(rightChild.hasAttributes("S"));
	}
	@Test
	public void test4() throws ParserException {
		EP ep = (EP) p.parseString("PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))");
		Relation r = astt.parse(ep);
		assertTrue(r instanceof Join);
		Join rj = (Join)r;
		assertTrue(rj.hasAttributes(JoinType.PNLJOIN, "s_nationkey = n_nationkey"));
		assertTrue(rj.getLeftChild() instanceof Join);
		assertTrue(rj.getRightChild() instanceof Selection);
		Join leftChild = (Join)rj.getLeftChild();
		//Check Left Child
		assertTrue(leftChild.hasAttributes(JoinType.PMJOIN, "ps_suppkey = s_suppkey"));
		assertTrue(leftChild.getLeftChild() instanceof Table);
		assertTrue(leftChild.getRightChild() instanceof Table);
		assertTrue(((Table)leftChild.getLeftChild()).hasAttributes("PS"));
		assertTrue(((Table)leftChild.getRightChild()).hasAttributes("S"));
		//Check Right Child
		Selection rightChild = (Selection)rj.getRightChild();
		assertTrue(rightChild.getPredicate().equals("n_name = 'ASIA'"));
		assertTrue(rightChild.getChild() instanceof Table);
		assertTrue(((Table)rightChild.getChild()).hasAttributes("N"));
	}
}
