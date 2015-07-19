package github.arnkore.algorithms.core.sort;

import edu.princeton.cs.algs4.stdlib.StdRandom;

public class MergeSort {
	/**
	 * 合并过程
	 * @param aux 待合并的数组
	 * @param arr 辅助合并的数组
	 * @param low inclusive 
	 * @param mid inclusive
	 * @param high inclusive
	 */
	@SuppressWarnings("rawtypes")
	private static void merge(Comparable[] arr, Comparable[] aux, int low, int mid, int high) {
		assert SortHelper.isSorted(arr, low, mid + 1); // precondition: arr[low -> mid] is sorted.
		assert SortHelper.isSorted(arr, mid + 1, high + 1); // precondition: arr[mid + 1 -> high] is sorted
		
		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {
			if (i > mid) aux[k] = arr[j++];
			else if (j > high) aux[k] = arr[i++];
			else if (SortHelper.less(arr[j], arr[i])) aux[k] = arr[j++];
			else aux[k] = arr[i++];
		}
		
		assert SortHelper.isSorted(arr, low, high); // postcondition: arr[low -> high] is sorted.
	}
	
	/**
	 * 排序函数
	 * @param arr 待排序的数组
	 * @param aux 辅助排序的数组
	 * @param low inclusive
	 * @param high inclusive
	 */
	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] arr, Comparable[] aux, int low, int high, boolean isAux) {
		// 优化：当待排区域小于CUTOFF时，采用插入排序，减少合并排序的递归深度
		if (high - low + 1 <= SortHelper.CUTOFF) {
			if (isAux) {
				InsertionSort.sort(aux, low, high);
				copyBack(aux, arr, low, high);
			} else {
				InsertionSort.sort(arr, low, high);
				copyBack(arr, aux, low, high);
			}
			return;
		}
		
		int mid = low + (high - low) / 2;
		sort(aux, arr, low, mid, !isAux);
		sort(aux, arr, mid + 1, high, !isAux);
		merge(aux, arr, low, mid, high);
	}
	
	@SuppressWarnings("rawtypes")
	private static void copyBack(Comparable[] srcArr, Comparable[] targetArr, int low, int high) {
		for (int i = low; i <= high; i++) {
			targetArr[i] =  srcArr[i];
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] arr) {
		Comparable[] aux = new Comparable[arr.length];
		sort(arr, aux, 0, arr.length - 1, false);
	}
	
	public static void main(String[] args) {
		int N = 10000;
		Integer[] arr = new Integer[N];
		for (int i = 0; i < N; i++) {
			arr[i] = StdRandom.uniform(N);
		}
		MergeSort.sort(arr);
		for (int i = 0; i < N; i++) {
			if ((i + 1) % 10 == 0) {
				System.out.println(arr[i]);
			} else {
				System.out.print(arr[i] + " ");
			}
		}
	}
}