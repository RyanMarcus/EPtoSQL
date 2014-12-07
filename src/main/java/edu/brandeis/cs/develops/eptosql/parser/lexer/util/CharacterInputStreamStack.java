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


package edu.brandeis.cs.develops.eptosql.parser.lexer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class CharacterInputStreamStack implements Deque<Character> {
	
	private BufferedReader br;
	
	private Deque<Character> buffer;
	private static final int TO_READ = 10;
	
	public CharacterInputStreamStack(InputStream s) {
		br = new BufferedReader(new InputStreamReader(s));
		buffer = new LinkedList<Character>();
		
	}
	
	private void ensureBufferHasItems() {
		if (!buffer.isEmpty())
			return;
		
		for (int i = 0; i < CharacterInputStreamStack.TO_READ; i++) {
			try {
				int nextRead = br.read();
				if (nextRead == -1)
					return;
				
				buffer.add((char)nextRead);
			} catch (IOException e) {
				return;
			}
			
		}
		
	}

	public boolean isEmpty() {
		if (!buffer.isEmpty())
			return false;
		
		// attempt a read
		try {
			int i = br.read();
			if (i == -1)
				return true;
			
			buffer.add(Character.valueOf((char)i));
			return false;
		} catch (IOException e) {
			return true;
		}
		
	}

	public Object[] toArray() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public boolean addAll(Collection<? extends Character> c) {
		return buffer.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public void clear() {
		buffer.clear();
		try {
			br.close();
		} catch (IOException e) {
			br = null;
		}
	}

	public void addFirst(Character e) {
		buffer.addFirst(e);
	}

	public void addLast(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public boolean offerFirst(Character e) {
		return buffer.offerFirst(e);
	}

	public boolean offerLast(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public Character removeFirst() {
		ensureBufferHasItems();
		return buffer.removeFirst();
	}

	public Character removeLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public Character pollFirst() {
		ensureBufferHasItems();
		return buffer.pollFirst();
	}

	public Character pollLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public Character getFirst() {
		ensureBufferHasItems();
		return buffer.getFirst();
	}

	public Character getLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	public Character peekFirst() {
		ensureBufferHasItems();
		return buffer.peekFirst();
	}

	public Character peekLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public boolean removeFirstOccurrence(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public boolean removeLastOccurrence(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public boolean add(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public boolean offer(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public Character remove() {
		ensureBufferHasItems();
		return buffer.remove();
	}

	public Character poll() {
		ensureBufferHasItems();
		return buffer.poll();
	}

	public Character element() {
		ensureBufferHasItems();
		return buffer.element();
	}

	public Character peek() {
		ensureBufferHasItems();
		return buffer.peek();
	}

	public void push(Character e) {
		buffer.push(e);
		
	}

	public Character pop() {
		ensureBufferHasItems();
		return buffer.pop();
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public boolean contains(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public int size() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	public Iterator<Character> iterator() {
		return new CharacterInputStreamStackIterator(this);
	}

	public Iterator<Character> descendingIterator() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}
	
	private class CharacterInputStreamStackIterator implements Iterator<Character> {

		private CharacterInputStreamStack ciss;
		private int position = 0;
		
		private CharacterInputStreamStackIterator(CharacterInputStreamStack ciss) {
			this.ciss = ciss;
		}
		
		private Character getItemAtPosition(int p) {
			if (p == 0)
				return (ciss.isEmpty() ? null : ciss.peek());
			
			Deque<Character> buf = new LinkedList<Character>();
			for (int i = 0; i <= p; i++) {
				if (!ciss.isEmpty()) {
					buf.push(ciss.pop());
				} else {
					// return items to the CISS
					while (!buf.isEmpty()) {
						ciss.push(buf.pop());
					}
					return null;
				}
			}
	
			
			Character toR = buf.peek();
			
			// return items to the CISS
			while (!buf.isEmpty()) {
				ciss.push(buf.pop());
			}
			return toR;
		}
		
		public boolean hasNext() {
			return getItemAtPosition(position) != null;
		}

		public Character next() {
			Character toR = getItemAtPosition(position);
			position++;
			return toR;
		}

		public void remove() {
			throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack iterator");
			
		}
		
	}

}
