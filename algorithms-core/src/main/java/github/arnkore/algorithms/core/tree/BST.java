package github.arnkore.algorithms.core.tree;

import github.arnkore.algorithms.core.exception.EmptySymbolTableException;
import github.arnkore.algorithms.core.queue.Queue;
import github.arnkore.algorithms.core.queue.impl.ArrayQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.base.Joiner;

/**
 * 二叉搜索树
 * @author arnkore
 *
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<K>, V> implements Iterable<K> {
	private Node root;
	
	/**
	 * 判断是否为空的二叉搜索树
	 * @return
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * 查找是否存在键key
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
	 * 插入键值对key:val，如果已存在该键，则更新其上的值。
	 * @param key
	 * @param val
	 */
	public void put(K key, V val) {
		root = put(root, key, val);
	}
	
	/**
	 * 在二叉搜索子树subroot中插入键值对key:val，如果二叉搜索子树中已存在该键，则更新其上的值。
	 * @param subroot 二叉搜索树子树
	 * @param key
	 * @param val
	 * @return
	 */
	private Node put(Node subroot, K key, V val) {
		if (subroot == null) return new Node(key, val);
		
		int cmp = key.compareTo(subroot.key);
		if (cmp < 0) subroot.left = put(subroot.left, key, val);
		else if (cmp > 0) subroot.right = put(subroot.right, key, val);
		else subroot.val = val; // search hit, update node's val field
		
		subroot.size = size(subroot.left) + size(subroot.right) + 1;
		return subroot;
	}
	
	/**
	 * 删除存储键key的节点
	 * @param key
	 */
	public void delete(K key) {
		if (root == null) throw new EmptySymbolTableException();
		root = delete(root, key);
	}
	
	/**
	 * 在二叉搜索子树subroot中删除存储键key的节点
	 * @param subroot
	 * @param key
	 * @return
	 */
	private Node delete(Node subroot, K key) {
		if (subroot == null) throw new NoSuchElementException();
		
		int cmp = key.compareTo(subroot.key);
		if (cmp < 0) {
			subroot.left = delete(subroot.left, key);
		} else if (cmp > 0) {
			subroot.right = delete(subroot.right, key);
		} else {
			if (subroot.right == null) return subroot.left;
			if (subroot.left == null) return subroot.right;
			
			Node rminNode = min(subroot.right);
			rminNode.right = delMin(subroot.right);
			rminNode.left = subroot.left;
			subroot = rminNode;
		}
		
		subroot.size = size(subroot.left) + size(subroot.right) + 1;
		return subroot;
	}
	
	/**
	 * 返回最小的键
	 * @return
	 */
	public K min() {
		if (root == null) throw new EmptySymbolTableException();
		return min(root).key;
	}
	
	/**
	 * 返回二叉搜索子树subroot中key最小的节点
	 * @param subroot
	 * @return
	 */
	private Node min(Node subroot) {
		while (subroot.left != null) {
			subroot = subroot.left;
		}
		
		return subroot;
	}
	
	/**
	 * 返回最大的键
	 * @return
	 */
	public K max() {
		if (root == null) throw new EmptySymbolTableException();
		return max(root).key;
	}
	
	/**
	 * 返回二叉搜索子树subroot中存储最大键的那个节点
	 * @param subroot
	 * @return
	 */
	private Node max(Node subroot) {
		while (subroot.right != null) {
			subroot = subroot.right;
		}
		
		return subroot;
	}
	
	/**
	 * 删除存储最小键的那个节点
	 */
	public void delMin() {
		if (root == null) throw new EmptySymbolTableException();
		root = delMin(root);
	}
	
	/**
	 * 删除二叉搜索子树subroot中的存储最小键的那个节点
	 * @param subroot
	 * @return
	 */
	private Node delMin(Node subroot) {
		if (subroot.left != null) {
			subroot.left = delMin(subroot.left);
			subroot.size = size(subroot.left) + size(subroot.right) + 1;
			return subroot;
		} else { // search hit, this node is min key node.
			return subroot.right;
		}
	}
	
	/**
	 * 删除存储最大键的那个节点
	 */
	public void delMax() {
		if (root == null) throw new EmptySymbolTableException();
		root = delMax(root);
	}
	
	/**
	 * 删除二叉搜索子树中键最大的那个节点
	 * @param subroot
	 * @return
	 */
	private Node delMax(Node subroot) {
		if (subroot.right != null) {
			subroot.right = delMax(subroot.right);
			subroot.size = size(subroot.left) + size(subroot.right) + 1;
			return subroot;
		} else {
			return subroot.left;
		}
	}

	/**
	 * 返回总的节点数
	 * @return
	 */
	public int size() {
		return size(root);
	}

	/**
	 * 二叉搜索子树subroot含有的节点数
	 * @param subroot
	 * @return
	 */
	private int size(Node subroot) {
		return subroot == null ? 0 : subroot.size;
	}

	public Iterator<K> iterator() {
		Queue<K> q = new ArrayQueue<K>();
		inorder(root, q);
		return q.iterator();
	}
	
	private void inorder(Node subroot, Queue<K> q) {
		if (subroot == null) return;
		inorder(subroot.left, q);
		q.enqueue(subroot.key);
		inorder(subroot.right, q);
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
		bst.put("a", 1);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("b", 2);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("c", 3);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("d", 4);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("e", 5);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("e");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("g", 6);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("h", 7);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("i", 8);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("j", 9);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("a");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("k", 10);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.put("l", 11);
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		System.out.println(bst.get("a"));
		System.out.println(bst.get("b"));
		System.out.println(bst.get("j"));
		System.out.println(bst.min());
		System.out.println(bst.max());
		bst.delMin();
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delMax();
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		System.out.println(bst.min());
		System.out.println(bst.max());
		
//		while (!bst.isEmpty()) {
//			bst.delMin();
//			System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
//		}
		
//		while (!bst.isEmpty()) {
//			bst.delMax();
//			System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
//		}
		
		bst.delete("g");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("h");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("j");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("d");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("i");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("c");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
		bst.delete("k");
		System.out.println(Joiner.on(" ").join(bst) + " " + bst.size());
	}
}
