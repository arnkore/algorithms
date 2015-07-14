package github.arnkore.algorithms.core.queue;

import java.util.Iterator;

/**
 * 队列接口
 * @author arnkore
 *
 * @param <E>
 */
public interface Queue<E> extends Iterable<E> {
	/**
	 * 判断队列是否为空
	 * @return
	 */
	boolean isEmpty();
	
	/**
	 * 返回队列的大小
	 * @return
	 */
	int size();
	
	/**
	 * 往队列中插入一个元素
	 * @param e
	 */
	void enqueue(E e);
	
	/**
	 * 从队列中移除一个元素
	 * @return
	 */
	E dequeue();
	
	Iterator<E> iterator();
}
