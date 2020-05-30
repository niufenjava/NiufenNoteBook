package io.niufen.common.collection;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * {@link java.util.Enumeration} 对象转 {@link Iterator} 对象
 *
 * @author haijun.zhang
 * @date 2020/5/29
 * @time 16:30
 */
public class EnumerationIterator<E> implements Iterator<E>, Iterable<E>, Serializable {

    private static final long serialVersionUID = -2941731140546349751L;

    private final Enumeration<E> e;

    /**
     * 构造
     *
     * @param e enumeration {@link Enumeration} 对象
     */
    public EnumerationIterator(Enumeration<E> e) {
        this.e = e;
    }

    @Override
    public Iterator<E> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return e.hasMoreElements();
    }

    @Override
    public E next() {
        return e.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
