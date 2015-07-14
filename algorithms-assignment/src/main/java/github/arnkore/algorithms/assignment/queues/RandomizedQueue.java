package github.arnkore.algorithms.assignment.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.stdlib.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] items;
	
	private int top;
	
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		items = (Item[])new Object[1];
		top = 0;
	}
	
	/**
	 * is the randomized queue empty?
	 * @return
	 */
	public boolean isEmpty() {
		return top == 0;
	}
	
	/**
	 * return the number of items in randomized queue
	 * @return
	 */
	public int size() {
		return top;
	}
	
	/**
	 * add an item
	 * @param item
	 */
	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		
		if (top == items.length) {
			resize(2 * items.length);
		}
		
		items[top++] = item;
	}
	
	/**
	 * random remove and return an item from queue
	 * @return
	 */
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int ix = StdRandom.uniform(top);
		swap(items, ix, --top);
		Item res = items[top];
		items[top] = null;
		
		if (top > 0 && top == items.length / 4) {
			resize(items.length / 2);
		}
		
		return res;
	}
	
	private void resize(int capacity) {
		@SuppressWarnings("unchecked")
		Item[] copy = (Item[])new Object[capacity];
		
		for (int i = 0; i < top; i++) {
			copy[i] = items[i];
		}
		
		items = copy;
	}

	private void swap(Item[] items, int i, int j) {
		if (i != j) {
			Item tmp = items[i];
			items[i] = items[j];
			items[j] = tmp;
		}
	}

	/**
	 * random return an item from queue
	 * @return
	 */
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int ix = StdRandom.uniform(top);
		return items[ix];
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}
	
	private class RandomizedQueueIterator implements Iterator<Item> {
		private Item[] citems;
		
		private int ctop;
		
		@SuppressWarnings("unchecked")
		public RandomizedQueueIterator() {
			ctop = top;
			citems = (Item[])new Object[top];
			
			for (int i  = 0; i < top; i++) {
				citems[i] = items[i];
			}
		}
		
		public boolean hasNext() {
			return ctop != 0;
		}

		public Item next() {
			if (ctop == 0) {
				throw new NoSuchElementException();
			}
			
			int ix = StdRandom.uniform(ctop);
			swap(citems, ix, --ctop);
			Item res = citems[ctop];
			citems[ctop] = null;
			return res;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
		for (int i = 0; i < 100; i++) {
			q.enqueue(i);
		}
		
		System.out.println(q.sample());
		System.out.println(q.sample());
		System.out.println(q.sample());
		
		for (Integer e : q) {
			System.out.println(e);
		}
	}
}
