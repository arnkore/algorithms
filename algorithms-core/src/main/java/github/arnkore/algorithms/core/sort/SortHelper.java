package github.arnkore.algorithms.core.sort;

public class SortHelper {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	@SuppressWarnings("rawtypes")
	public static void swap(Comparable[] arr, int i, int j) {
		Comparable tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	/**
	 * 判断整个数组是否已排序
	 * @param arr 待验证的数组
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isSorted(Comparable[] arr) {
		return isSorted(arr, 0, arr.length);
	}

	/**
	 * 判断数组指定范围的数据是否已排序
	 * @param arr 待验证的数组
	 * @param low inclusive
	 * @param high exclusive
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isSorted(Comparable[] arr, int low, int high) {
		for (int i = low; i < high - 1; i++) {
			if (less(arr[i + 1], arr[i])) {
				return false;
			}
		}

		return true;
	}
}