package github.arnkore.algorithms.core.sort;

import edu.princeton.cs.algs4.stdlib.StdRandom;
import github.arnkore.algorithms.core.Shuffle;

public class QuickSort {
	private static final int CUTOFF = 11;
	/**
	 * 快速排序的分区算法
	 * @param arr 待分区的数组
	 * @param low inclusive
	 * @param high inclusive
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static int partition(Comparable[] arr, int low, int high) {
		int med = medianOf3(arr, low, low + (high - low) / 2, high);
		SortHelper.swap(arr, low, med);
		int i = low, j = high + 1;
		Comparable v = arr[low];
		
		while (true) {
			while (SortHelper.less(arr[++i], v)) if (i == high) break;
			while (SortHelper.less(v, arr[--j])) if (j == low) break;
			if (i >= j) break;
			SortHelper.swap(arr, i, j);
		}
		
		SortHelper.swap(arr, j, low);
		return j;
	}
	
	/**
	 * 返回三个数的中值
	 * @param arr
	 * @param i
	 * @param j
	 * @param k
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static int medianOf3(Comparable[] arr, int i, int j, int k) {
		if (SortHelper.less(arr[i], arr[j])) {
			if (SortHelper.less(arr[j], arr[k])) {
				return j;
			} else {
				return SortHelper.less(arr[i], arr[k]) ? k : i;
			}
		} else {
			if (SortHelper.less(i, k)) {
				return i;
			} else {
				return SortHelper.less(j, k) ? k : j;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] arr, int low, int high) {
		if (high - low + 1 <= CUTOFF) {
			InsertionSort.sort(arr, low, high);
			return;
		}
		int k = partition(arr, low, high);
		sort(arr, low, k - 1);
		sort(arr, k + 1, high);
	}
	
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] arr) {
		StdRandom.shuffle(arr);
		sort(arr, 0, arr.length - 1);
	}
	
	public static void main(String[] args) {
		for (int j = 1; j <= 101; j++) {
			int N = j;
			Integer[] arr = new Integer[N];
			for (int i = 0; i < N; i++) {
				arr[i] = i;
			}
			Shuffle.shuffle(arr);
			QuickSort.sort(arr);
			for (int i = 0; i < N; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}
		
		int N = 1000000;
		Integer[] arr = new Integer[N];
		for (int i = 0; i < N; i++) {
			arr[i] = StdRandom.uniform(N);
		}
		QuickSort.sort(arr);
		
		for (int i = 0; i < 100 && i < N; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
