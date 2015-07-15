package github.arnkore.algorithms.core;

import edu.princeton.cs.algs4.stdlib.In;

/**
 * UnionFind algorithm
 * 
 * @author arnkore
 * @since 2015-07-04
 *
 */
public class UnionFind {
	private int[] id;
	private int[] sz;
	private int ccNum; // the number of connected components
	
	public UnionFind(int n) {
		id = new int[n];
		sz = new int[n];
		ccNum = n;
		
		for (int i = 0; i < n; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	/**
	 * are p and q connected?
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean connected(int p, int q) {
		if (p < 0 || p >= id.length || q < 0 || q >= id.length) {
			throw new IllegalArgumentException("index out of bounds");
		}
		
		return root(p) == root(q);
	}
	
	/**
	 * add connection between p and q
	 * @param p
	 * @param q
	 */
	public void union(int p, int q) {
		if (p < 0 || p >= id.length || q < 0 || q >= id.length) {
			throw new IllegalArgumentException("index out of bounds");
		}
		
		int pRoot = root(p);
		int qRoot = root(q);
		if (pRoot == qRoot) return;
		
		if (sz[pRoot] <= sz[qRoot]) {
			id[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		} else {
			id[qRoot] = pRoot;
			sz[pRoot] += sz[qRoot];
		}
		
		ccNum--;
	}
	
	/**
	 * component identifier of i
	 * @param i
	 * @return
	 */
	private int root(int i) {
		while (i != id[i]) {
			id[i] = id[id[i]];
			i = id[i];
		}
		
		return i;
	}
	
	/**
	 * number of connected components
	 * @return
	 */
	public int count() {
		return ccNum;
	}
	
	public static void main(String[] args) {
		In in = new In("src/main/resources/uf/mediumUf.txt");
		UnionFind uf = new UnionFind(in.readInt());
		
		while (!in.isEmpty()) {
			int p = in.readInt();
			int q = in.readInt();
			
			if (!uf.connected(p, q)) {
				System.out.println("(" + p + " " + q + ")" + " " + uf.count());
				uf.union(p, q);
			}
		}
	}
}
