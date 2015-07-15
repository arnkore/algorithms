package github.arnkore.algorithms.core.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<E> implements Stack<E>{
	private Node first;
	
	private int sz;
	
	private class Node {
		private E element;
		
		private Node next;
	}
	
	public void push(E element) {
		Node oldFirst = first;
		first = new Node();
		first.element = element;
		first.next = oldFirst;
		sz++;
	}
	
	public E pop() {
		if (first == null) {
			throw new EmptyStackException();
		}
		
		E result = first.element;
		first = first.next;
		sz--;
		return result;
	}
	
	public int size() {
		return sz;
	}
	
	public boolean isEmpty() {
		return first == null;
	}

	public Iterator<E> iterator() {
		return new StackIterator();
	}
	
	private class StackIterator implements Iterator<E> {
		private Node ptr;
		
		public StackIterator() {
			ptr = first;
		}
		
		public boolean hasNext() {
			return ptr != null;
		}

		public E next() {
			if (ptr == null) {
				throw new NoSuchElementException();
			}
			
			E element = ptr.element;
			ptr = ptr.next;
			return element;
		}
	}
	
	public static void main(String[] args) {
		Stack<Integer> stack = new LinkedStack<Integer>();
		stack.push(5);
		System.out.println(stack.pop());
		stack.push(4);
		stack.push(2);
		System.out.println(stack.pop());
		stack.push(1);
		
		for (Integer e : stack) {
			System.out.println(e);
		}
	}
}
