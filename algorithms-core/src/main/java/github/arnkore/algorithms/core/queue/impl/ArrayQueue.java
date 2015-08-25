package github.arnkore.algorithms.core.queue.impl;

import github.arnkore.algorithms.core.common.exception.EmptyQueueException;
import github.arnkore.algorithms.core.queue.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 队列的resize数组实现
 * @author arnkore
 *
 * @param <E>
 */
public class ArrayQueue<E> implements Queue<E> {
	private E[] items; // items的大小始终为队列可存储元素数加1
	
	private int head; // 队首索引(inclusive)
	
	private int tail; // 队尾索引(exclusive)
	
	@SuppressWarnings("unchecked")
	public ArrayQueue() {
		items = (E[])new Object[2];
		head = tail = 0;
	}

	public boolean isEmpty() {
		return head == tail;
	}
	
	public int size() {
		return (tail - head + items.length) % items.length;
	}

	public void enqueue(E e) {
		final int realCapacity = items.length - 1;
		if (size() == realCapacity) {
			resize(2 * realCapacity + 1);
		}
		
		items[tail] = e;
		tail = (tail + 1) % items.length;
	}

	public E dequeue() {
		if (isEmpty()) {
			throw new EmptyQueueException();
		}
		
		E res = items[head];
		items[head] = null;
		head = (head + 1) % items.length;
		
		final int sz = size();
		final int realCapacity = items.length - 1;
		if (sz > 0 && sz == realCapacity / 4) {
			resize(realCapacity / 2 + 1);
		}
		
		return res;
	}

	private void resize(int capacity) {
		@SuppressWarnings("unchecked")
		E[] copy = (E[])new Object[capacity];
		int copySize = size();
		
		for (int i = 0; i < copySize; i++) {
			copy[i] = items[(head + i) % items.length];
		}
		
		items = copy;
		head = 0;
		tail = copySize;
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int cur = head;

			public boolean hasNext() {
				return cur != tail;
			}

			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				
				E res = items[cur];
				cur = (cur + 1) % items.length;
				return res;
			}
		};
	}
	
	public static void main(String[] args) {
		Queue<Integer> q = new ArrayQueue<Integer>();
		
		for (int i = 0; i < 10000; i++) {
			q.enqueue(i);
		}
		
		for (int i = 0; i < 9990; i++) {
			if ((i + 1) % 10 == 0) {
				System.out.println(q.dequeue());
			} else {
				System.out.print(q.dequeue() + " ");
			}
		}
		
		for (int i : q) {
			System.out.println(i);
		}
	}
}
