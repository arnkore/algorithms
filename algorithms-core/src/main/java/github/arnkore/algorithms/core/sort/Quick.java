package github.arnkore.algorithms.core.sort;

import edu.princeton.cs.algs4.stdlib.StdRandom;


@SuppressWarnings("rawtypes")
public class Quick {
	/**
	 * 2-way partition
	 * @param arr
	 * @param low
	 * @param high
	 * @return
	 */
	private static int partition(Comparable[] arr, int low, int high) {
		Comparable key = arr[low];
		int i = low, j = high + 1;
		
		while (true) {
			while (SortHelper.less(arr[++i], key)) if (i >= high) break;
			while (SortHelper.less(key, arr[--j])); // arr[low] is sentinel
			if (i >= j) break; // check if pointers cross
			SortHelper.swap(arr, i, j);
		}
		
		SortHelper.swap(arr, low, j);
		return j;
	}
	
	private static void sort(Comparable[] arr, int low, int high) {
		if (low >= high) return;
		int loc = partition(arr, low, high);
		sort(arr, low, loc - 1);
		sort(arr, loc + 1, high);
	}
	
	/**
	 * 基于2-way partition的快速排序算法
	 * @param arr
	 */
	public static void sort(Comparable[] arr) {
		StdRandom.shuffle(arr); // random shuffle based quicksort
		sort(arr, 0, arr.length - 1);
	}
	
	/**
	 * select kth problem
	 * @param arr
	 * @param k
	 * @return
	 */
	public static int select(Comparable[] arr, int k) {
		if (k < 0 || k >= arr.length) {
			throw new IllegalArgumentException("illegal k");
		}
		
		int low = 0, high = arr.length - 1;
		while (low < high) {
			int i = partition(arr, low, high);
			if (i < k) low = i + 1;
			else if (i > k) high = i - 1;
			else return i;
		}
		
		return low;
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{9, 0, 3, 7, 1, 8, 6, 2, 4, 5};
		Quick.sort(arr);
		SortHelper.print(arr);
		
		int N = 100;
		Integer[] arr1 = new Integer[N];
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] = i;
		}
		StdRandom.shuffle(arr1);
		SortHelper.print(arr1);
		Quick.sort(arr1);
		SortHelper.print(arr1);
		
		String[] arr2 = new String[]{"A", "A", "B", "A", "B", "B", "A", "A", "B", "B", "A", "A"};
		Quick.sort(arr2);
		SortHelper.print(arr2);
	}
}
