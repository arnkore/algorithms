package github.arnkore.algorithms.core.sort;

public class ShellSort {
	public static void sort(Comparable[] arr) {
		int N = arr.length;
		int h = 1;

		while (h < N) {
			h = 3 * h + 1;
		}

		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j - h >= 0 && SortHelper.less(arr[j], arr[j - h]); j -= h) {
					SortHelper.swap(arr, j, j - h);
				}
			}

			h /= 3;
		}
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[]{5, 3, 9, 2, 7, 8, 4, 0, 6, 1};
		for (int e : arr) {
			System.out.print(e + " ");
		}
		System.out.println();
		ShellSort.sort(arr);
		for (int e : arr) {
			System.out.print(e + " ");
		}
	}
}