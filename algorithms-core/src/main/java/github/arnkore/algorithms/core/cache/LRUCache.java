package github.arnkore.algorithms.core.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.base.Joiner;

/**
 * LRUCache
 * 核心思想：如果数据最近被访问过，那么将来被访问的几率也很高。
 * 实现：每当插入数据或者数据被访问到，将其移动到链表头部 ；当缓存满时，淘汰最近最久未访问的数据。
 * 缺点：缓存容易被污染
 * @author arnkore
 *
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> implements Iterable<K>{
	private int capacity;
	private int size;
	private CacheNode head, tail;
	private Map<K, CacheNode> nodesMap = new HashMap<K, CacheNode>();
	
	private class CacheNode {
		private CacheNode prev, next;
		private K key;
		private V val;
		
		public CacheNode(K key, V val) {
			this.key = key;
			this.val = val;
		}
	}
	
	public LRUCache(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
	}
	
	/**
	 * 查找缓存中是否已存在相应的数据。
	 * 如果缓存中存在键key，缓存命中(cache hit)，否则，缓存未命中(cache miss)。
	 * @param key
	 * @return
	 */
	public V get(K key) {
		V resVal = null;
		CacheNode node = nodesMap.get(key);
		
		if (node != null) { // cache hit
			resVal = node.val;
			moveToHead(node);
		}
		
		return resVal;
	}
	
	/**
	 * 如果缓存中不存在键key， 插入键值对key:val到链表头部， 否则更新键key对应的值并移动到链表头部。
	 * @param key
	 * @param val
	 */
	public void put(K key, V val) {
		CacheNode node = nodesMap.get(key);
		
		if (node == null) {
			if (size == capacity) { // 缓存满, 淘汰最久未访问的节点
				CacheNode delNode = deleteTail();
				if (delNode != null) nodesMap.remove(delNode.key);
			} else {
				size++;
			}
			
			node = new CacheNode(key, val);
			nodesMap.put(key, node);
			addToHead(node);
		} else {
			node.val = val;
			moveToHead(node);
		}
	}
	
	public Iterator<K> iterator() {
		return new CacheIterator();
	}
	
	private class CacheIterator implements Iterator<K> {
		private CacheNode cur = head;

		public boolean hasNext() {
			return cur != null;
		}

		public K next() {
			if (cur == null) {
				throw new NoSuchElementException();
			}
			
			K resKey = cur.key;
			cur = cur.next;
			return resKey;
		}
	}
	
	/**
	 * 将链表中已存在的节点移动到链表头部
	 * @param node
	 */
	private void moveToHead(CacheNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		
		if (node != head) {
			if (node == tail) {
				CacheNode origTail = tail;
				tail = tail.prev;
				tail.next = null;
				origTail.prev = null;
				origTail.next = head;
				head.prev = origTail;
				head = origTail;
			} else {
				node.next.prev = node.prev;
				node.prev.next = node.next;
				node.prev = null;
				node.next = head;
				head = node;
			}
		}
	}

	/**
	 * 将节点node添加到链表头部
	 * @param node
	 */
	private void addToHead(CacheNode node) {
		CacheNode origHead = head;
		head = node;
		
		if (origHead == null) {
			tail = head;
		} else {
			head.next = origHead;
			origHead.prev = head;
		}
	}
	
	/**
	 * 删除链表的尾部节点
	 */
	private CacheNode deleteTail() {
		if (tail == null) {
			throw new NoSuchElementException();
		}
		
		CacheNode origTail = tail;
		tail = tail.prev;
		
		if (tail == null) {
			head = null;
		} else {
			tail.next = null;
			origTail.prev = null;
		}
		
		return origTail;
	}
	
	public static void main(String[] args) {
		LRUCache<Integer, Object> cache = new LRUCache<Integer, Object>(3);
		cache.put(1, new Object());
		System.out.println(Joiner.on(" ").join(cache));
		cache.put(4, new Object());
		System.out.println(Joiner.on(" ").join(cache));
		cache.put(3, new Object());
		System.out.println(Joiner.on(" ").join(cache));
		cache.put(9, new Object());
		System.out.println(Joiner.on(" ").join(cache));
		cache.put(4, new Object());
		System.out.println(Joiner.on(" ").join(cache));
		cache.put(1, new Object());
		System.out.println(Joiner.on(" ").join(cache));
		cache.get(1);
		System.out.println(Joiner.on(" ").join(cache));
	}
}
