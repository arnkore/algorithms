package github.arnkore.algorithms.core.sort;

import github.arnkore.algorithms.core.Shuffle;

public class BottomUpMergeSort {
	/**
	 * 采用自底向上版本的合并排序算法排序整个数组
	 * @param arr
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] arr) {
		int N = arr.length;
		Comparable[] aux = new Comparable[N];
		
		for (int sz = 1; sz < N; sz *= 2) {
			for (int j = 0; j < N - sz; j += 2 * sz) {
				merge(arr, aux, j, j + sz - 1, min(j + 2 * sz - 1, N - 1));
			}
		}
	}
	
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
		
		for (int i = low; i <= high; i++) {
			aux[i] = arr[i];
		}
		
		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {
			if (i > mid) arr[k] = aux[j++];
			else if (j > high) arr[k] = aux[i++];
			else if (SortHelper.less(aux[j], aux[i])) arr[k] = aux[j++];
			else arr[k] = aux[i++];
		}
		
		assert SortHelper.isSorted(arr, low, high); // postcondition: arr[low -> high] is sorted.
	}
	
	private static int min(int i, int j) {
		return i <= j ? i : j;
	}
	
	public static void main(String[] args) {
		for (int j = 1; j <= 101; j++) {
			int N = j;
			Integer[] arr = new Integer[N];
			for (int i = 0; i < N; i++) {
				arr[i] = i;
			}
			Shuffle.shuffle(arr);
			BottomUpMergeSort.sort(arr);
			for (int i = 0; i < N; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}
	}
}
