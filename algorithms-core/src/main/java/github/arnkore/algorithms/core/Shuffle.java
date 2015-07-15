package github.arnkore.algorithms.core;

import edu.princeton.cs.algs4.stdlib.StdRandom;

public class Shuffle {
	/**
	 * 洗牌算法
	 * @param arr
	 */
	public static void shuffle(Object[] arr) {
		int N = arr.length;
		
		for (int i = N - 1; i >= 0; i--) {
			int rix = StdRandom.uniform(i + 1);
			swap(arr, i, rix);
		}
	}
	
	/**
	 * 交换数组索引i和索引j处的值
	 * @param arr
	 * @param i
	 * @param j
	 */
	private static void swap(Object[] arr, int i, int j) {
		Object tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void main(String[] args) {
		int N = 100;
		Integer[] arr = new Integer[N];
		
		for (int i = 0; i < N; i++) {
			arr[i] = i;
		}
		
		Shuffle.shuffle(arr);
		
		for (int i = 0; i < N; i++) {
			if ((i + 1) % 10 != 0) {
				System.out.print(arr[i] + " ");
			} else {
				System.out.println(arr[i]);
			}
		}
	}
}
