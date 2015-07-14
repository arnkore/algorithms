package github.arnkore.algorithms.core.sort;

public class SortHelper {
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	public static void swap(Comparable[] arr, int i, int j) {
		Comparable tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static boolean isSorted(Comparable[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if (less(arr[i], arr[i - 1])) {
				return false;
			}
		}

		return true;
	}
}