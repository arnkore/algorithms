package github.arnkore.algorithms.core.queue.impl;

import github.arnkore.algorithms.core.exception.EmptyQueueException;
import github.arnkore.algorithms.core.queue.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue<E> implements Queue<E> {
	private Node first;
	
	private Node last;
	
	private int sz;
	
	private class Node {
		private E element;
		
		private Node next;
	}
	
	public boolean isEmpty() {
		return first == null && last == null;
	}
	
	public int size() {
		return sz;
	}
	
	public void enqueue(E e) {
		Node oldLast = last;
		last = new Node();
		last.element = e;
		
		if (oldLast == null) {
			first = last;
		} else {
			oldLast.next = last;
		}
		
		sz++;
	}
	
	public E dequeue() {
		if (first == null) {
			throw new EmptyQueueException();
		} 
		
		E res = first.element;
		if (first == last) {
			first = last = null;
		} else {
			first = first.next;
		}
		
		sz--;
		return res;
	}

	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<E> {
		private Node ptr;
		
		public QueueIterator() {
			ptr = first;
		}

		public boolean hasNext() {
			return ptr != null;
		}

		public E next() {
			if (ptr == null) {
				throw new NoSuchElementException();
			}
			
			E res = ptr.element;
			ptr = ptr.next;
			return res;
		}
	}
	
	public static void main(String[] args) {
		Queue<String> q = new LinkedQueue<String>();
		q.enqueue("a");
		q.enqueue("e");
		System.out.println(q.dequeue());
		q.enqueue("b");
		q.enqueue("c");
		
		for (String ele : q) {
			System.out.println(ele);
		}
	}
}
