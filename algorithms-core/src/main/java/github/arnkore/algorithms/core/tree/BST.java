package github.arnkore.algorithms.core.tree;

/**
 * 二叉搜索树
 * @author arnkore
 *
 * @param <Key>
 * @param <Value>
 */
public class BST<Key extends Comparable<Key>, Value> {
	private Node root; // 根节点
	
	private class Node {
		private Key key;
		private Value value;
		private Node left, right;
		private int size;
		
		public Node(Key key, Value value, int size) {
			super();
			this.key = key;
			this.value = value;
			this.size = size;
		}
	}
	
	/**
	 * 查找key对应的value, 找不到返回null
	 * @param key
	 * @return
	 */
	public Value get(Key key) {
		Node ptr = root;
		
		while (ptr != null) {
			int cmp = key.compareTo(ptr.key);
			if (cmp < 0) ptr = ptr.left;
			else if (cmp > 0) ptr = ptr.right;
			else return ptr.value;
		}
		
		return null;
	}
	
	/**
	 * 二叉搜索树中不存在该key对时，插入之，反之，更新key对应的value
	 * @param key
	 * @param value
	 */
	public void put(Key key, Value value) {
		put(root, key, value);
	}
	
	private Node put(Node ptr, Key key, Value value) {
		if (ptr == null) return new Node(key, value, 1);
		
		int cmp = key.compareTo(ptr.key);
		if (cmp < 0) ptr.left = put(ptr.left, key, value);
		else if (cmp > 0) ptr.right = put(ptr.right, key, value);
		else ptr.value = value;
		
		ptr.size += 1;
		return ptr;
	}
	
	/**
	 * 返回二叉搜索树中最小的key
	 * @return
	 */
	public Key min() {
		if (root == null) return null;
		
		Node ptr = root;
		while (ptr.left != null) {
			ptr = ptr.left;
		}
		
		return ptr.key;
	}
	
	/**
	 * 返回二叉搜索树中最大的key
	 * @return
	 */
	public Key max() {
		if (root == null) return null;
		
		Node ptr = root;
		while (ptr.right != null) {
			ptr = ptr.right;
		}
		
		return ptr.key;
	}
}
