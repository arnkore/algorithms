package github.arnkore.algorithms.core.sort;

public class SelectionSort {
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] arr) {
		int N = arr.length;

		for (int i = 0; i < N; i++) {
			int min = i;

			for (int j = i + 1; j < N; j++) {
				if (SortHelper.less(arr[j], arr[min])) {
					min = j;
				}
			}

			SortHelper.swap(arr, i, min);
		}
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[]{5, 3, 9, 2, 7, 8, 4, 0, 6, 1};
		for (int e : arr) {
			System.out.print(e + " ");
		}
		System.out.println();
		SelectionSort.sort(arr);
		for (int e : arr) {
			System.out.print(e + " ");
		}
	}
}