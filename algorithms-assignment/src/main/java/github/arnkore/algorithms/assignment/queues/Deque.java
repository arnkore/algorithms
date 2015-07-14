package github.arnkore.algorithms.assignment.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node head;
	
	private Node tail;
	
	private int sz;
	
	private class Node {
		private Item item;
		private Node prev;
		private Node next;
	}
	
	public Deque() {
		head = tail = null;
		sz = 0;
	}
	
	/**
	 * is the deque empty?
	 * @return
	 */
	public boolean isEmpty() {
		return head == null && tail == null;
	}
	
	/**
	 * return the number of items on the deque
	 * @return
	 */
	public int size() {
		return sz;
	}
	
	/**
	 * add the item to the front
	 * @param item
	 */
	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		
		Node oldHead = head;
		head = new Node();
		head.item = item;
		
		if (oldHead == null) {
			tail = head;
		} else {
			oldHead.prev = head;
			head.next = oldHead;
		}
		sz++;
	}
	
	/**
	 * remove and return the item from front
	 * @return
	 */
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		Item res = head.item;
		if (head == tail) {
			head = tail = null;
		} else {
			Node oldHead = head;
			head = head.next;
			oldHead.next = null;
			head.prev = null;
		}
		
		sz--;
		return res;
	}
	
	/**
	 * add the item to the tail
	 * @param item
	 */
	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		
		Node oldTail = tail;
		tail = new Node();
		tail.item = item;
		
		if (oldTail == null) {
			head = tail;
		} else {
			oldTail.next = tail;
			tail.prev = oldTail;
		}
		
		sz++;
	}
	
	/**
	 * remove and return the item from tail
	 * @return
	 */
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		Item res = tail.item;
		
		if (head == tail) {
			head = tail = null;
		} else {
			Node oldTail = tail;
			tail = tail.prev;
			tail.next = null;
			oldTail.prev = null;
		}
		
		sz--;
		return res;
	}

	/**
	 * return an iterator over items in order from front to end
	 */
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			private Node cur = head;
			
			public boolean hasNext() {
				return cur != null;
			}

			public Item next() {
				if (cur == null) {
					throw new NoSuchElementException();
				}
				
				Item item = cur.item;
				cur = cur.next;
				return item;
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		Deque<Integer> dq = new Deque<Integer>();
		dq.addFirst(1);
		dq.addFirst(2);
		dq.addLast(3);
		dq.addLast(4);
		
		for (int e : dq) {
			System.out.println(e);
		}
		
		System.out.println(dq.removeFirst());
		System.out.println(dq.removeLast());
		
		for (int e : dq) {
			System.out.println(e);
		}
	}
}
