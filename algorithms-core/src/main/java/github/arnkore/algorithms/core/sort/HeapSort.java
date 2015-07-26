package github.arnkore.algorithms.core.sort;

import edu.princeton.cs.algs4.stdlib.StdRandom;

/**
 * 堆排序
 * @author arnkore
 *
 */
public class HeapSort {
	private static final int ROOT = 0; // 根节点的索引
	
	/**
	 * 排序实现
	 * @param arr
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] arr) {
		int heapSize = arr.length;
		
		for (int i = parent(heapSize - 1); i >= 0; i--) {
			sinkDown(arr, i, heapSize);
		}
		
		while (heapSize > 1) {
			swap(arr, ROOT, --heapSize);
			sinkDown(arr, ROOT, heapSize);
		}
	}

	/**
	 * 对二叉堆中节点k执行下沉操作
	 * @param arr
	 * @param k
	 * @param heapSize 待排序堆的大小
	 */
	@SuppressWarnings("rawtypes")
	private static void sinkDown(Comparable[] arr, int k, int heapSize) {
		while (leftChild(k) <= heapSize - 1) {
			int lc = leftChild(k), rc = lc + 1, gc = lc;
			if (rc <= heapSize - 1 && greater(arr, rc, gc))	gc = rc;
			if (greater(arr, k, gc)) break;
			swap(arr, k, gc);
			k = gc;
		}
	}
	
	/**
	 * 返回二叉堆(0-based, root节点在arr[0]处)中节点k的父节点
	 * @param k
	 * @return
	 */
	private static int parent(int k) {
		return (k - 1) / 2;
	}

	/**
	 * 返回二叉堆(0-based, root节点在arr[0]处)中节点k的左孩子节点
	 * @param k
	 * @return
	 */
	private static int leftChild(int k) {
		return 2 * k + 1;
	}

	/**
	 * 交换数组中索引i和j处的值
	 * @param arr
	 * @param i
	 * @param j
	 */
	@SuppressWarnings("rawtypes")
	private static void swap(Comparable[] arr, int i, int j) {
		Comparable tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	/**
	 * 返回数组中索引i和j处值更大的一个
	 * @param arr
	 * @param i
	 * @param j
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean greater(Comparable[] arr, int i, int j) {
		return arr[i].compareTo(arr[j]) > 0;
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{9, 0, 3, 7, 1, 8, 6, 2, 4, 5};
		HeapSort.sort(arr);
		SortHelper.print(arr);
		
		int N = 100;
		Integer[] arr1 = new Integer[N];
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] = i;
		}
		StdRandom.shuffle(arr1);
		SortHelper.print(arr1);
		HeapSort.sort(arr1);
		SortHelper.print(arr1);
		
		String[] arr2 = new String[]{"A", "A", "B", "A", "B", "B", "A", "A", "B", "B", "A", "A"};
		HeapSort.sort(arr2);
		SortHelper.print(arr2);
	}
}
