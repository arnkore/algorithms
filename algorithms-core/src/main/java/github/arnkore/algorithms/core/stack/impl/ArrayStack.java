package github.arnkore.algorithms.core.stack.impl;

import github.arnkore.algorithms.core.stack.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayStack<E> implements Stack<E> {
	private E[] elements;
	
	private int top;
	
	private static final int INIT_SIZE = 1;
	
	@SuppressWarnings("unchecked")
	public ArrayStack() {
		elements = (E[])new Object[INIT_SIZE];
		top = 0;
	}
	
	public boolean isEmpty() {
		return top == 0;
	}
	
	public int size() {
		return top;
	}
	
	public void push(E e) {
		if (top == elements.length) {
			resize(2 * elements.length);
		}
		
		elements[top++] = e;
	}
	
	public E pop() {
		E result = elements[--top];
		elements[top] = null;
		
		if (top > 0 && top <= elements.length / 4) {
			resize(elements.length / 2);
		}
		
		return result;
	}

	private void resize(int len) {
		@SuppressWarnings("unchecked")
		E[] newElements = (E[])new Object[len];
		for (int i = 0; i < top; i++) {
			newElements[i] = elements[i];
		}
		
		elements = newElements;
	}

	public Iterator<E> iterator() {
		return new StackIterator();
	}
	
	private class StackIterator implements Iterator<E> {
		private int ix;
		
		public StackIterator() {
			ix = top;
		}
		
		public boolean hasNext() {
			return ix != 0;
		}

		public E next() {
			if (ix == 0) {
				throw new NoSuchElementException();
			}
			
			return elements[--ix];
		}
	}
	
	public static void main(String[] args) {
		Stack<Integer> stack = new ArrayStack<Integer>();
		stack.push(5);
		System.out.println(stack.pop());
		stack.push(4);
		stack.push(2);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		stack.push(1);
		stack.push(3);
		stack.push(7);
		stack.push(9);
		
		for (Integer e : stack) {
			System.out.println(e);
		}
	}
}
