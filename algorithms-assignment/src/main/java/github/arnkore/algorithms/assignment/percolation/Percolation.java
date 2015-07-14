package github.arnkore.algorithms.assignment.percolation;

import edu.princeton.cs.algs4.algs.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF percolateUF;
	private WeightedQuickUnionUF isFullUF;
	private boolean[][] opened;
	private final int vtop; // virtual top vertex
	private final int vbottom; // virtual bottom vertex
	private final int len; // rows or columns
	
	/**
	 * create N-by-N grid, with all sites blocked
	 * @param N
	 */
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		
		final int totalGrids = N * N;
		len = N;
		vtop = 0;
		vbottom = totalGrids + 1;
		percolateUF = new WeightedQuickUnionUF(totalGrids + 2);
		isFullUF = new WeightedQuickUnionUF(totalGrids + 1);
		opened = new boolean[N][N];
	}

	/**
	 * open site (row i, column j) if it is not open already
	 * @param i
	 * @param j
	 */
	public void open(int i, int j) {
		if (i < 1 || i > len || j < 1 || j > len) {
			throw new IndexOutOfBoundsException();
		}
		
		opened[i - 1][j - 1] = true;
		int ix = index(i, j);
		if (i == 1) { // connect top virtual vertex
			percolateUF.union(vtop, ix); 
			isFullUF.union(vtop, ix);
		}
		if (i == len) { // connect bottom virtual vertex
			percolateUF.union(vbottom, ix); 
		}
		
		for (int direction = 1; direction <= 4; direction++) {
			connectAround(i, j, direction);
		}
	}
	
	private void connectAround(int r, int c, int direction) {
		int nr = r, nc = c;
		if (direction == 1) nr = r - 1; // upper
		else if (direction == 2) nc = c + 1; // right
		else if (direction == 3) nr = r + 1; // bottom
		else if (direction == 4) nc = c - 1; // left
		
		if (nr >= 1 && nr <= len && nc >= 1 && nc <= len) {
			int ix = index(r, c), nix = index(nr, nc); 
			if (isOpen(nr, nc)) {
				percolateUF.union(ix, nix);
				isFullUF.union(ix, nix);
			}
		}
	}

	private int index(int i, int j) {
		return (i - 1) * len + j;
	}

	/**
	 * is site (row i, column j) open?
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > len || j < 1 || j > len) {
			throw new IndexOutOfBoundsException();
		}
		
		return opened[i - 1][j - 1];
	}

	/**
	 * is site (row i, column j) full?
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {
		if (i < 1 || i > len || j < 1 || j > len) {
			throw new IndexOutOfBoundsException();
		}
		
		return isOpen(i, j) && isFullUF.connected(vtop, index(i, j));
	}

	/**
	 * does the system percolate?
	 * @return
	 */
	public boolean percolates() {
		return percolateUF.connected(vtop, vbottom);
	}
}
