package github.arnkore.algorithms.core.stack;

import java.util.Iterator;

/**
 * 栈接口
 * @author arnkore
 *
 * @param <E>
 */
public interface Stack<E> extends Iterable<E> {
	/**
	 * 判断栈是否为空
	 * @return
	 */
	boolean isEmpty();
	
	/**
	 * 返回栈的大小
	 * @return
	 */
	int size();
	
	/**
	 * 入栈
	 * @param e
	 */
	void push(E e);
	
	/**
	 * 出栈
	 * @return
	 */
	E pop();
	
	Iterator<E> iterator();
}
