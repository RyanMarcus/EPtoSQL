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

}
