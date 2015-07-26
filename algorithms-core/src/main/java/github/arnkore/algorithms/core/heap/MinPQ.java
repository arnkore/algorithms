package github.arnkore.algorithms.core.heap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.stdlib.StdRandom;
import github.arnkore.algorithms.core.sort.SortHelper;

/**
 * 小顶堆, resizing array实现
 * @author arnkore
 *
 * @param <Key>
 */
public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key> {
	private Comparator<Key> comparator; // 提供comparator时已comparator比较，否则按照Key的compareTo方法比较
	
	private Key[] keys; // 存储堆中的元素
	
	private int capacity; // 堆的最大容量, resizing array实现， 可变。
	
	private int sz = 0; // 堆的大小
	
	private static final int ROOT = 1; // 堆的根
	
	@SuppressWarnings("unchecked")
	public MinPQ(int capacity) {
		this.capacity = capacity;
		keys = (Key[])new Comparable[capacity + 1];
	}
	
	public MinPQ() {
		this(1);
	}
	
	public MinPQ(Key[] keys, Comparator<Key> comparator) {
		this(keys.length);
		sz = keys.length; // heap size
		
		for (int i = 0; i < keys.length; i++) {
			this.keys[i + 1] = keys[i];
		}
		
		for (int i = sz / 2; i >= ROOT; i--) {
			sinkDown(i, sz);
		}
		
		this.comparator = comparator;
	}
	
	/**
	 * 插入一个元素
	 * @param key
	 */
	public void insert(Key key) {
		if (sz == capacity) {
			resize(2 * capacity);
		}
		
		keys[++sz] = key;
		floatUp(sz);
	}

	/**
	 * 从堆中移除最小的元素
	 * @return
	 */
	public Key delMin() {
		if (isEmpty()) {
			throw new EmptyHeapException();
		}
		
		Key min = keys[ROOT];
		swap(keys, ROOT, sz--);
		sinkDown(ROOT, sz);
		keys[sz + 1] = null; // prevent loitering
		
		if (sz > 0 && sz == capacity / 4) {
			resize(capacity / 2);
		}
		return min;
	}

	/**
	 * 返回堆中最小的元素
	 * @return
	 */
	public Key min() {
		if (isEmpty()) {
			throw new EmptyHeapException();
		}
		
		return keys[ROOT];
	}
	
	/**
	 * 堆是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return sz == 0;
	}
	
	/**
	 * 返回堆的大小
	 * @return
	 */
	public int size() {
		return sz;
	}
	

	public Iterator<Key> iterator() {
		return new MinPQIterator();
	}
	
	private class MinPQIterator implements Iterator<Key> {
		private MinPQ<Key> cloneMinPQ;
		
		@SuppressWarnings("unchecked")
		public MinPQIterator() {
			Key[] arr = (Key[])new Comparable[sz];
			for (int i = 0; i < sz; i++) {
				arr[i] = keys[i + 1];
			}
			cloneMinPQ = new MinPQ<Key>(arr, comparator);
		}

		public boolean hasNext() {
			return !cloneMinPQ.isEmpty();
		}

		public Key next() {
			if (cloneMinPQ.isEmpty()) {
				throw new NoSuchElementException();
			}
			
			return cloneMinPQ.delMin();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Key[] copy = (Key[])new Comparable[capacity + 1];
		for (int i = ROOT; i <= sz; i++) {
			copy[i] = keys[i];
		}
		
		keys = copy;
		this.capacity = capacity;
	}
	
	private void floatUp(int k) {
		while (k > ROOT && less(keys[k], keys[k / 2])) {
			swap(keys, k, k / 2);
			k /= 2;
		}
	}
	
	private void sinkDown(int k, int heapsize) {
		while (2 * k <= heapsize) {
			int lc = 2 * k, rc = lc + 1, sc = lc;
			if (rc <= heapsize && less(keys[rc], keys[sc])) sc = rc;
			if (!less(keys[sc], keys[k])) break;
			swap(keys, k, sc);
			k = sc;
		}
	}
	
	private void swap(Key[] keys, int i, int j) {
		Key tmp = keys[i];
		keys[i] = keys[j];
		keys[j] = tmp;
	}

	private boolean less(Key v, Key w) {
		if (comparator == null) {
			return v.compareTo(w) < 0;
		} else {
			return comparator.compare(v, w) < 0;
		}
	}
	
	public static void main(String[] args) {
		MinPQ<Integer> pq = new MinPQ<Integer>();
		Integer[] arr = new Integer[]{9, 0, 3, 7, 1, 8, 6, 2, 4, 5};
		for (int e : arr) {
			pq.insert(e);
		}
		
		for (int e : pq) {
			System.out.print(e + " ");
		}
		System.out.println();

		int k = 0;
		while (!pq.isEmpty()) {
			if ((k++ + 1) % 10 == 0) {
				System.out.println(pq.delMin());
			} else {
				System.out.print(pq.delMin() + " ");
			}
		}
		System.out.println();
		
		int N = 100;
		MinPQ<Integer> pq1 = new MinPQ<Integer>(N);
		Integer[] arr1 = new Integer[N];
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] = i;
		}
		StdRandom.shuffle(arr1);
		SortHelper.print(arr1);
		for (int e : arr1) {
			pq1.insert(e);
		}
		
		k = 0;
		while (!pq1.isEmpty()) {
			if ((k++ + 1) % 10 == 0) {
				System.out.println(pq1.delMin());
			} else {
				System.out.print(pq1.delMin() + " ");
			}
		}
	}
}
