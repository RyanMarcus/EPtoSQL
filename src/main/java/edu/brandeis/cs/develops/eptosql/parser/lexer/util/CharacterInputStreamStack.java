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

	@Override
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

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public boolean addAll(Collection<? extends Character> c) {
		return buffer.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public void clear() {
		buffer.clear();
		try {
			br.close();
		} catch (IOException e) {
			br = null;
		}
	}

	@Override
	public void addFirst(Character e) {
		buffer.addFirst(e);
	}

	@Override
	public void addLast(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public boolean offerFirst(Character e) {
		return buffer.offerFirst(e);
	}

	@Override
	public boolean offerLast(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public Character removeFirst() {
		ensureBufferHasItems();
		return buffer.removeFirst();
	}

	@Override
	public Character removeLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public Character pollFirst() {
		ensureBufferHasItems();
		return buffer.pollFirst();
	}

	@Override
	public Character pollLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public Character getFirst() {
		ensureBufferHasItems();
		return buffer.getFirst();
	}

	@Override
	public Character getLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");
	}

	@Override
	public Character peekFirst() {
		ensureBufferHasItems();
		return buffer.peekFirst();
	}

	@Override
	public Character peekLast() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public boolean add(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public boolean offer(Character e) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public Character remove() {
		ensureBufferHasItems();
		return buffer.remove();
	}

	@Override
	public Character poll() {
		ensureBufferHasItems();
		return buffer.poll();
	}

	@Override
	public Character element() {
		ensureBufferHasItems();
		return buffer.element();
	}

	@Override
	public Character peek() {
		ensureBufferHasItems();
		return buffer.peek();
	}

	@Override
	public void push(Character e) {
		buffer.push(e);
		
	}

	@Override
	public Character pop() {
		ensureBufferHasItems();
		return buffer.pop();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack");

	}

	@Override
	public Iterator<Character> iterator() {
		return new CharacterInputStreamStackIterator(this);
	}

	@Override
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
		
		@Override
		public boolean hasNext() {
			return getItemAtPosition(position) != null;
		}

		@Override
		public Character next() {
			Character toR = getItemAtPosition(position);
			position++;
			return toR;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("This operation is not supported by the CharacterInputStreamStack iterator");
			
		}
		
	}

}
