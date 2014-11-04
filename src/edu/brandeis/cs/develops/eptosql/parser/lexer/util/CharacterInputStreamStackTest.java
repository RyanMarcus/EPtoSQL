package edu.brandeis.cs.develops.eptosql.parser.lexer.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.junit.Test;

public class CharacterInputStreamStackTest {

	


	@Test
	public void test1() {
		String s = "this is a test";
		InputStream is = new ByteArrayInputStream(s.getBytes());
	
		
		
		
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		
		assertFalse(ciss.isEmpty());
		assertTrue(ciss.pop() == 't');
		assertTrue(ciss.pop() == 'h');
		assertTrue(ciss.pop() == 'i');
		assertTrue(ciss.pop() == 's');
		
		ciss.push('l');
		assertTrue(ciss.pop() == 'l');
		assertTrue(ciss.pop() == ' ');
		
		ciss.push('l');
		ciss.push('l');
		ciss.push('l');
		ciss.push('l');
		assertTrue(ciss.pop() == 'l');
		assertTrue(ciss.pop() == 'l');
		assertTrue(ciss.pop() == 'l');
		assertTrue(ciss.pop() == 'l');

		assertTrue(ciss.pop() == 'i');


		
	}

	
	
	@Test
	public void test2() {
		String s = "()";
		InputStream is = new ByteArrayInputStream(s.getBytes());
	
		
		
		
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		
		assertFalse(ciss.isEmpty());
		assertTrue(ciss.pop() == '(');
		assertTrue(ciss.pop() == ')');
		assertTrue(ciss.isEmpty());
		ciss.push('8');
		assertTrue(ciss.pop() == '8');
		
	}
	
	@Test
	public void iteratorTest() {
		String s = "abcdef";
		InputStream is = new ByteArrayInputStream(s.getBytes());
		
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		
		Iterator<Character> it = ciss.iterator();
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'a');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'b');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'c');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'd');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'e');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'f');
		
		assertFalse(it.hasNext());
		
	}
	
	@Test
	public void iteratorTest2() {
		String s = "abcdef";
		InputStream is = new ByteArrayInputStream(s.getBytes());
		
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		
		Iterator<Character> it = ciss.iterator();
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'a');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'b');
		
		Iterator<Character> it2 = ciss.iterator();
		assertTrue(it2.hasNext());
		assertTrue(it2.next() == 'a');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'c');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'd');
		
		assertTrue(it2.hasNext());
		assertTrue(it2.next() == 'b');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'e');
		
		assertTrue(it.hasNext());
		assertTrue(it.next() == 'f');
		
		assertFalse(it.hasNext());
		
		assertTrue(it2.hasNext());
		assertTrue(it2.next() == 'c');
		
		assertTrue(it2.hasNext());
		assertTrue(it2.next() == 'd');
		
		assertTrue(it2.hasNext());
		assertTrue(it2.next() == 'e');
		
		assertTrue(it2.hasNext());
		assertTrue(it2.next() == 'f');
		
		assertFalse(it2.hasNext());
		
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void unsupportedOps1() {
		String s = "()";
		InputStream is = new ByteArrayInputStream(s.getBytes());
	
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		ciss.descendingIterator();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void unsupportedOps2() {
		String s = "()";
		InputStream is = new ByteArrayInputStream(s.getBytes());
	
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		ciss.peekLast();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void unsupportedOps3() {
		String s = "()";
		InputStream is = new ByteArrayInputStream(s.getBytes());
	
		CharacterInputStreamStack ciss = new CharacterInputStreamStack(is);
		ciss.add(null);
	}
}
