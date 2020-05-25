package io.niufen.common.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author haijun.zhang
 * @date 2020/5/23
 * @time 22:29
 */
public class FieldComparator<T> implements Comparator<T>,Serializable {
    private static final long serialVersionUID = 3558927520637503917L;

    @Override
    public int compare(T o1, T o2) {
        return 0;
    }
}
