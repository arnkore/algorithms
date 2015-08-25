package github.arnkore.algorithms.core.tree;

import github.arnkore.algorithms.core.common.exception.EmptySymbolTableException;
import github.arnkore.algorithms.core.queue.Queue;
import github.arnkore.algorithms.core.queue.impl.ArrayQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.base.Joiner;

/**
 * Binary Search Tree
 * @author arnkore
 *
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<K>, V> implements Iterable<K> {
	private Node root;
	
	/**
	 * search an element
	 * @param key
	 * @return
	 */
	public V get(K key) {
		Node cur = root;
		while (cur != null) {
			int cmp = key.compareTo(cur.key);
			
			if (cmp < 0) cur = cur.left;
			else if (cmp > 0) cur = cur.right;
			else return cur.val; // search hit
		}
		
		return null; // search miss
	}
	
	/**
	 * insert an element
	 * @param key
	 * @param val
	 */
	public void put(K key, V val) {
		root = put(root, key, val);
	}
	
	private Node put(Node x, K key, V val) {
		if (x == null) return new Node(key, val);
		
		int cmp = key.compareTo(x.key);
		if (cmp < 0) x.left = put(x.left, key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else x.val = val; // search hit, update node's val field
		
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	/**
	 * delete an element
	 * @param key
	 */
	public void delete(K key) {
		if (root == null) throw new EmptySymbolTableException();
		root = delete(root, key);
	}
	
	private Node delete(Node x, K key) {
		if (x == null) throw new NoSuchElementException();
		
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			x.left = delete(x.left, key);
		} else if (cmp > 0) {
			x.right = delete(x.right, key);
		} else {
			if (x.right == null) return x.left;
			if (x.left == null) return x.right;
			
			Node rminNode = min(x.right);
			rminNode.right = delMin(x.right);
			rminNode.left = x.left;
			x = rminNode;
		}
		
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	/**
	 * return minimum element in bst
	 * @return
	 */
	public K min() {
		if (root == null) throw new EmptySymbolTableException();
		return min(root).key;
	}
	
	private Node min(Node x) {
		while (x.left != null) {
			x = x.left;
		}
		
		return x;
	}
	
	/**
	 * return maximum element in bst
	 * @return
	 */
	public K max() {
		if (root == null) throw new EmptySymbolTableException();
		return max(root).key;
	}
	
	private Node max(Node x) {
		while (x.right != null) {
			x = x.right;
		}
		
		return x;
	}
	
	/**
	 * delete minimum element
	 */
	public void delMin() {
		if (root == null) throw new EmptySymbolTableException();
		root = delMin(root);
	}
	
	private Node delMin(Node x) {
		if (x.left != null) {
			x.left = delMin(x.left);
			x.size = size(x.left) + size(x.right) + 1;
			return x;
		} else { // search hit, this node is min key node.
			return x.right;
		}
	}
	
	/**
	 * delete maximum element
	 */
	public void delMax() {
		if (root == null) throw new EmptySymbolTableException();
		root = delMax(root);
	}
	
	private Node delMax(Node x) {
		if (x.right != null) {
			x.right = delMax(x.right);
			x.size = size(x.left) + size(x.right) + 1;
			return x;
		} else {
			return x.left;
		}
	}

	/**
	 * return the element which is smaller than or equal to specified value(key)
	 * @param key
	 * @return
	 */
	public K floor(K key) {
		Node x = floor(root, key);
		return x == null ? null : x.key;
	}
	
	private Node floor(Node x, K key) {
		if (x == null) return null;
		
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return floor(x.left, key);
		} else if (cmp > 0) {
			Node res = floor(x.right, key);
			return res == null ? x : res;
		} else {
			return x;
		}
	}

	/**
	 * return the element which is larger than or equal to specified value(key)
	 * @param key
	 * @return
	 */
	public K ceiling(K key) {
		Node x = ceiling(root, key);
		return x == null ? null : x.key;
	}
	
	private Node ceiling(Node x, K key) {
		if (x == null) return null;
		
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			Node res = ceiling(x.left, key);
			return res == null ? x : res;
		} else if (cmp > 0) {
			return ceiling(x.right, key);
		} else {
			return x;
		}
	}
	
	/**
	 * select kth-smallest element
	 * @param k
	 * @return
	 */
	public K select(int k) {
		if (k <= 0 && k > size(root)) {
			throw new IllegalArgumentException();
		}
		
		return select(root, k);
	}
	
	private K select(Node x, int k) {
		if (x == null) throw new NoSuchElementException();
		
		int lisz = size(x.left) + 1;
		if (lisz < k) {
			return select(x.right, k - lisz);
		} else if (lisz > k) {
			return select(x.left, k);
		} else {
			return x.key;
		}
	}
	
	/**
	 * how many elements smaller than the specified value(key).
	 * @param key
	 * @return
	 */
	public int rank(K key) {
		return rank(root, key);
	}
	
	private int rank(Node x, K key) {
		if (x == null) return 0;
		
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return rank(x.left, key);
		} else if (cmp > 0) {
			return size(x.left) + 1 + rank(x.right, key);
		} else {
			return size(x.left);
		}
	}
	
	/**
	 * is empty?
	 * @return
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * how many elements in bst?
	 * @return
	 */
	public int size() {
		return size(root);
	}
	
	/**
	 * how many elements in specified scope([lo, hi])
	 * @param lo
	 * @param hi
	 * @return
	 */
	public int size(K lo, K hi) {
		if (lo.compareTo(hi) > 0) throw new IllegalArgumentException();
		if (root == null) return 0;
		
		Node startNode = ceiling(root, lo);
		Node endNode = floor(root, hi);
		return rank(endNode.key) - rank(startNode.key) + 1;
	}

	private int size(Node x) {
		return x == null ? 0 : x.size;
	}

	/**
	 * return all elements in bst
	 * @return
	 */
	public Iterator<K> keys() {
		return keys(min(), max());
	}
	
	/**
	 * return elements in specified scope([lo, hi])
	 * @param lo
	 * @param hi
	 * @return
	 */
	public Iterator<K> keys(K lo, K hi) {
		if (lo.compareTo(hi) > 0) throw new IllegalArgumentException();
		Queue<K> q = new ArrayQueue<K>();
		keys(root, q, lo, hi);
		return q.iterator();
	}
	
	private void keys(Node x, Queue<K> q, K lo, K hi) {
		if (x == null) return;
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		
		if (cmplo < 0) {
			keys(x.left, q, lo, hi);
		}
		
		if (cmplo <= 0 && cmphi >= 0) q.enqueue(x.key);
		
		if (cmphi > 0) {
			keys(x.right, q, lo, hi);
		}
	}
	
	public Iterator<K> iterator() {
		return inorder();
	}
	
	/**
	 * in-order iteration
	 * @return
	 */
	public Iterator<K> inorder() {
		Queue<K> q = new ArrayQueue<K>();
		inorder(root, q);
		return q.iterator();
	}
	
	private void inorder(Node x, Queue<K> q) {
		if (x == null) return;
		inorder(x.left, q);
		q.enqueue(x.key);
		inorder(x.right, q);
	}
	
	/**
	 * pre-order iteration
	 * @return
	 */
	public Iterator<K> preorder() {
		Queue<K> q = new ArrayQueue<K>();
		preorder(root, q);
		return q.iterator();
	}
	
	private void preorder(Node x, Queue<K> q) {
		if (x == null) return;
		q.enqueue(x.key);
		preorder(x.left, q);
		preorder(x.right, q);
	}
	
	/**
	 * post-order iteration
	 * @return
	 */
	public Iterator<K> postorder() {
		Queue<K> q = new ArrayQueue<K>();
		postorder(root, q);
		return q.iterator();
	}
	
	private void postorder(Node x, Queue<K> q) {
		if (x == null) return;
		postorder(x.left, q);
		postorder(x.right, q);
		q.enqueue(x.key);
	}
	
	/**
	 * level-order iteration
	 * @return
	 */
	public Iterator<K> levelOrder() {
		Queue<K> q = new ArrayQueue<K>();
		levelOrder(root, q);
		return q.iterator();
	}
	
	private void levelOrder(Node x, Queue<K> q) {
		Queue<Node> nodes = new ArrayQueue<Node>();
		if (x != null) nodes.enqueue(x);
		
		while (!nodes.isEmpty()) {
			Node cur = nodes.dequeue();
			q.enqueue(cur.key);
			if (cur.left != null) nodes.enqueue(cur.left);
			if (cur.right != null) nodes.enqueue(cur.right);
		}
	}
	
	private class Node {
		private Node left, right;
		private K key;
		private V val;
		private int size;
		
		public Node(K key, V val, int size) {
			this.key = key;
			this.val = val;
			this.size = size;
		}
		
		public Node(K key, V val) {
			this(key, val, 1);
		}
	}
	
	public static void main(String[] args) {
		BST<String, Integer> bst = new BST<String, Integer>();
		bst.put("E", 1);
		bst.put("A", 2);
		bst.put("S", 3);
		bst.put("C", 4);
		bst.put("I", 5);
		bst.put("H", 6);
		bst.put("R", 7);
		bst.put("N", 8);
		System.out.println("keys(): " + Joiner.on(" ").join(bst.keys()) + " " + bst.size());
		System.out.println("inorder: " + Joiner.on(" ").join(bst) + " " + bst.size());
		System.out.println("preorder: " + Joiner.on(" ").join(bst.preorder()) + " " + bst.size());
		System.out.println("postorder: " + Joiner.on(" ").join(bst.postorder()) + " " + bst.size());
		System.out.println("levelOrder: " + Joiner.on(" ").join(bst.levelOrder()) + " " + bst.size());
		System.out.println("floor(D): " + bst.floor("D"));
		System.out.println("floor(C): " + bst.floor("C"));
		System.out.println("floor(I): " + bst.floor("I"));
		System.out.println("ceiling(D): " + bst.ceiling("D"));
		System.out.println("ceiling(H): " + bst.ceiling("H"));
		System.out.println("ceiling(I): " + bst.ceiling("I"));
		System.out.println("rank(N): " + bst.rank("N"));
		System.out.println("select(5): " + bst.select(5));
		System.out.println("[A, N]" + bst.size("A", "N"));
		System.out.println("[C, I]" + bst.size("C", "I"));
		System.out.println("----------------delete I-----------------------");
		bst.delete("I");
		System.out.println("keys(): " + Joiner.on(" ").join(bst.keys()) + " " + bst.size());
		System.out.println("inorder: " + Joiner.on(" ").join(bst) + " " + bst.size());
		System.out.println("preorder: " + Joiner.on(" ").join(bst.preorder()) + " " + bst.size());
		System.out.println("postorder: " + Joiner.on(" ").join(bst.postorder()) + " " + bst.size());
		System.out.println("levelOrder: " + Joiner.on(" ").join(bst.levelOrder()) + " " + bst.size());
		System.out.println("floor(D): " + bst.floor("D"));
		System.out.println("floor(C): " + bst.floor("C"));
		System.out.println("floor(I): " + bst.floor("I"));
		System.out.println("ceiling(D): " + bst.ceiling("D"));
		System.out.println("ceiling(H): " + bst.ceiling("H"));
		System.out.println("ceiling(I): " + bst.ceiling("I"));
		System.out.println("rank(N): " + bst.rank("N"));
		System.out.println("select(5): " + bst.select(5));
		System.out.println("get(N): " + bst.get("N"));
		System.out.println("min: " + bst.min());
		System.out.println("max: " + bst.max());
		System.out.println("[A, N]" + bst.size("A", "N"));
		System.out.println("[C, I]" + bst.size("C", "I"));
		System.out.println("----------------delete Min-----------------------");
		bst.delMin();
		System.out.println("min: " + bst.min());
		System.out.println("----------------delete Max-----------------------");
		bst.delMax();
		System.out.println("max: " + bst.max());
		System.out.println("keys(): " + Joiner.on(" ").join(bst.keys()) + " " + bst.size());
		System.out.println("inorder: " + Joiner.on(" ").join(bst) + " " + bst.size());
		System.out.println("preorder: " + Joiner.on(" ").join(bst.preorder()) + " " + bst.size());
		System.out.println("postorder: " + Joiner.on(" ").join(bst.postorder()) + " " + bst.size());
		System.out.println("levelOrder: " + Joiner.on(" ").join(bst.levelOrder()) + " " + bst.size());
		System.out.println("floor(D): " + bst.floor("D"));
		System.out.println("floor(C): " + bst.floor("C"));
		System.out.println("floor(I): " + bst.floor("I"));
		System.out.println("ceiling(D): " + bst.ceiling("D"));
		System.out.println("ceiling(H): " + bst.ceiling("H"));
		System.out.println("ceiling(I): " + bst.ceiling("I"));
		System.out.println("rank(N): " + bst.rank("N"));
		System.out.println("select(5): " + bst.select(5));
		System.out.println("[A, N]" + bst.size("A", "N"));
		System.out.println("[C, I]" + bst.size("C", "I"));
		System.out.println("[A, Z]" + bst.size("A", "Z"));
	}
}
