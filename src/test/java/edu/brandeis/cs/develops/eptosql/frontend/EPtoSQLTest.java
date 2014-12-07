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


package edu.brandeis.cs.develops.eptosql.frontend;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class EPtoSQLTest {

	
	private ByteArrayOutputStream stderrBytes;
	
	private PrintStream stderr;
	
	
	
	@Before
	public void setUp() throws Exception {
		
		stderrBytes = new ByteArrayOutputStream();
		
		stderr = new PrintStream(stderrBytes);
		
		System.setErr(stderr);
		
	}

	@Test
	public void test1() throws IOException {
		String s = EPtoSQL.syncCompile(CodeGenerationOption.NESTED, IROption.DISABLE, "PMJOIN(attr1 = attr2, PSELECT(attr1 = \"value\", PTABLE(table1)), PTABLE(table2))");
		
		// should trip the semantic analyzer
		assertTrue(s.length() == 0);
		assertTrue(stderrBytes.size() != 0);
		
	}
	
	@Test
	public void test2() throws IOException {
		String s = EPtoSQL.syncCompile(CodeGenerationOption.NESTED, IROption.ENABLE, "PMJOIN(attr1 = attr2, PSELECT(attr1 = \"value\", PTABLE(table1)), PTABLE(table2))");
		
		// should not trip the semantic analyzer
		assertTrue(stderrBytes.size() == 0);
		assertTrue(s.length() != 0);
	}
	
	@Test
	public void test3() throws IOException {
		String s = EPtoSQL.syncCompile(CodeGenerationOption.UNNESTED, IROption.DISABLE, "PMJOIN(attr1 = attr2, PSELECT(attr1 = \"value\", PTABLE(table1)), PTABLE(table2))");
		
		// should trip the semantic analyzer
		assertTrue(s.length() == 0);
		assertTrue(stderrBytes.size() != 0);
		
	}
	
	@Test
	public void test4() throws IOException {
		String s = EPtoSQL.syncCompile(CodeGenerationOption.UNNESTED, IROption.ENABLE, "PMJOIN(attr1 = attr2, PSELECT(attr1 = \"value\", PTABLE(table1)), PTABLE(table2))");
		
		// should not trip the semantic analyzer
		assertTrue(stderrBytes.size() == 0);
		assertTrue(s.length() != 0);
	}
	
	@Test
	public void test5() throws IOException {
		String s = EPtoSQL.syncCompile(CodeGenerationOption.UNNESTED, IROption.ENABLE, "PMJOINattr1 = attr2, PSELECT(attr1 = \"value\", PTABLE(table1)), PTABLE(table2))");
		
		// should be a syntax/parser error
		assertTrue(stderrBytes.size() != 0);
		assertTrue(s.length() == 0);
	}

}
