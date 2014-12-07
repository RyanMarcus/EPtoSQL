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


package edu.brandeis.cs.develops.eptosql.ir_generator;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Join;
import edu.brandeis.cs.develops.eptosql.translator.Relation;
import edu.brandeis.cs.develops.eptosql.translator.SampleTrees;
import edu.brandeis.cs.develops.eptosql.translator.Selection;
import edu.brandeis.cs.develops.eptosql.translator.Table;

public class IRGeneratorTest {

	private Parser p;
	private ASTTranslator astt;
	private IRGenerator ir;
	
	@Before
	public void setUp() throws Exception {
		p = new Parser();
		astt = new ASTTranslator();
		ir = new IRGenerator();
	}

	@Test
	public void test1() throws IRGenerationException {
		List<Relation> r = ir.decompose(SampleTrees.BasicJoinExpression());
		assertTrue(r.size() == 1);
	}
	
	@Test
	public void test2() throws IRGenerationException {
		List<Relation> r = ir.decompose(SampleTrees.BasicSelectionExpression());
		assertTrue(r.size() == 1);
	}
	
	@Test
	public void test3() throws IRGenerationException {
		List<Relation> r = ir.decompose(SampleTrees.JoinSelectionExpression());
		
		assertTrue(r.size() == 2);
		assertTrue(r.get(0) instanceof Selection);
		Selection s = (Selection) r.get(0);
		assertTrue(s.getInto() != null);
		
		assertTrue(r.get(1) instanceof Join);
		Join j = (Join) r.get(1);
		assertTrue(j.getRightChild() instanceof Table);
		Table t = (Table) j.getRightChild();
		assertTrue(t.getName().equals(s.getInto()));

	}

	@Test
	public void test4() throws IRGenerationException, ParserException {
		EP ep = (EP) p.parseString("PTABLE(S)");
		Relation rel = astt.parse(ep);
		List<Relation> r = ir.decompose(rel);
	
		
		assertTrue(r.size() == 1);
	}
	
	@Test
	public void test5() throws IRGenerationException, ParserException {
		EP ep = (EP) p.parseString("PSELECT(a > 5, PTABLE(S))");
		Relation rel = astt.parse(ep);
		List<Relation> r = ir.decompose(rel);
	
		
		assertTrue(r.size() == 1);
	}
	
	@Test
	public void test6() throws IRGenerationException, ParserException {
		EP ep = (EP) p.parseString("PSELECT(a < 100, PSELECT(a > 5, PTABLE(S)))");
		Relation rel = astt.parse(ep);
		List<Relation> r = ir.decompose(rel);

		
		assertTrue(r.size() == 2);
		assertTrue(r.get(0) instanceof Selection);
		Selection s1 = (Selection) r.get(0);
		assertTrue(s1.getChild() instanceof Table);
		assertTrue(((Table)s1.getChild()).getName().equals("S"));
		
		assertTrue(r.get(1) instanceof Selection);
		Selection s2 = (Selection) r.get(1);
		assertTrue(((Table) s2.getChild()).getName().equals(s1.getInto()));
		
	}
	
	@Test(expected=IRGenerationException.class)
	public void errorTest1() throws IRGenerationException {
		ir.decompose(new SomeOtherType());
	}
	
	private class SomeOtherType extends Relation {

		@Override
		public Set<Relation> getChildren() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
