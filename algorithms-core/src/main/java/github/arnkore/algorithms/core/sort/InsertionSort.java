package github.arnkore.algorithms.core.sort;

public class InsertionSort {
	public static void sort(Comparable[] arr) {
		int N = arr.length;

		for (int i = 1; i < N; i++) {
			for (int j = i; j > 0; j--) {
				if (SortHelper.less(arr[j], arr[j-1])) {
					SortHelper.swap(arr, j, j-1);
				} else {
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[]{5, 3, 9, 2, 7, 8, 4, 0, 6, 1};
		for (int e : arr) {
			System.out.print(e + " ");
		}
		System.out.println();
		InsertionSort.sort(arr);
		for (int e : arr) {
			System.out.print(e + " ");
		}
	}
}