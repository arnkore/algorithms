package github.arnkore.algorithms.core.tree;

/**
 * 符号表-二叉搜索树实现
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
	 * 在符号表中查找键key对应的值
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
	 * 在符号表中更新键key上对应的值
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
	 * 符号表中最小的键
	 * @return
	 */
	public Key min() {
		if (root == null) throw new EmptySymbolTableException();
		return min(root).key;
	}
	
	private Node min(Node x) {
		while (x.left != null) x = x.left;
		return x;
	}
	
	/**
	 * 符号表中最大的键
	 * @return
	 */
	public Key max() {
		if (root == null) throw new EmptySymbolTableException();
		return max(root).key;
	}
	
	private Node max(Node x) {
		while (x.right != null) x = x.right;
		return x;
	}
	
	/**
	 * 在符号表中删除键key对应的键值对
	 * @param key
	 */
	public void delete(Key key) {
		delete(root, key);
	}
	
	private Node delete(Node x, Key key) {
		if (x == null) return null;
		
		int cmp = key.compareTo(x.key);
		if (cmp < 0) x.left = delete(x.left, key);
		else if (cmp > 0) x.right = delete(x.right, key);
		else {
			if (x.right == null) return x.left;
			if (x.left == null) return x.right;
			Node tmp = x;
			// 查找右子树中最小的节点，并用该节点替换待删除节点
			x = min(x.right); 
			x.right = deleteMin(tmp.right);
			x.left = tmp.left;
		}
		
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	/**
	 * 在符号表中删除最小的键所对应的键值对
	 */
	public void deleteMin() {
		if (root == null) throw new EmptySymbolTableException();
		deleteMin(root);
	}
	
	private Node deleteMin(Node x) {
		if (x.left == null) return x.right;
		x.left = deleteMin(x.left);
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}

	/**
	 * 符号表包含的节点数
	 * @return
	 */
	public int size() {
		return size(root);
	}
	
	private int size(Node x) {
		if (x == null) return 0;
		return x.size;
	}
}
