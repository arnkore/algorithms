package github.arnkore.algorithms.assignment.percolation;

import edu.princeton.cs.algs4.stdlib.StdRandom;
import edu.princeton.cs.algs4.stdlib.StdStats;

public class PercolationStats {
	private double[] t;
	
	/**
	 * perform T independent experiments on an N-by-N grid
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		
		t = new double[T];
		for (int k = 0; k < T; k++) {
			Percolation p = new Percolation(N);
			int openedGrids = 0;
			
			while (!p.percolates()) {
				int i = StdRandom.uniform(N) + 1;
				int j = StdRandom.uniform(N) + 1;
				
				if (!p.isOpen(i, j)) {
					p.open(i, j);
					openedGrids++;
				}
			}
			
			t[k] = openedGrids / (double)(N * N);
		}
	}

	/**
	 * sample mean of percolation threshold
	 * @return
	 */
	public double mean() {
		return StdStats.mean(t);
	}

	/**
	 * sample standard deviation of percolation threshold
	 * @return
	 */
	public double stddev() {
		return StdStats.stddev(t);
	}

	/**
	 * low endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(t.length);
	}

	/**
	 * high endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(t.length);
	}

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(N, T);
		System.out.println("mean = " + ps.mean());
		System.out.println("stddev = " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
	}
}
