package io.niufen.common.core.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class ListUtilTest {

    @Test
    public void newList() {
        List<Object> objects = ListUtil.list(true);
        assert ArrayList.class.equals(objects.getClass());
        assert objects.size() == 0;
    }

    @Test
    public void testNewList() {
        List<Object> linkedList = ListUtil.list(Boolean.TRUE);
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 0;

        List<Object> arrayList = ListUtil.list(Boolean.FALSE);
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 0;
    }

    @Test
    public void newArrayList() {
        List<Object> arrayList = ListUtil.list(true);
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 0;
    }

    @Test
    public void newLinkedList() {
        List<Object> linkedList = ListUtil.list(true);
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 0;
    }


    @Test
    public void newArrayListByCollection() {
        String [] varargs = {"B","A","C","C"};
        Set<Object> sets = SetUtil.newHashSet(varargs);
//        List<Object> arrayList = ListUtil.newArrayList(sets);
//        assert ArrayList.class.equals(arrayList.getClass());
//        assert arrayList.size() == 3;
    }

    @Test
    public void newArrayListByIterator() {
        String [] varargs = {"B","A","C","C"};
        Set<Object> sets = SetUtil.newHashSet(varargs);
//        List<Object> arrayList = ListUtil.list(sets.iterator());
//        assert ArrayList.class.equals(arrayList.getClass());
//        assert arrayList.size() == 3;
    }

    @Test
    public void newLinkedListByVarargs() {

    }

    @Test
    public void newLinkedListByCollection() {
    }

    @Test
    public void newLinkedListByIterator() {
    }

    @Test
    public void newCopyOnWriteArrayList() {
    }

    @Test
    public void sort(){}



}

@Data
@AllArgsConstructor
class ComparatorUser {

    private String name;

    private Integer age;

}

@Slf4j
@Data
@AllArgsConstructor
class ComparableUser implements Comparable<ComparableUser> {

    private String name;

    private Integer age;

    @Override
    public int compareTo(ComparableUser o) {
        log.error("compareTo start");
        log.error("this:{}",this.toString());
        log.error("next:{}",o.toString());
        log.error("this.age.compareTo(next.age):{}",age.compareTo(o.age));
        log.error("compareTo start");
        return age.compareTo(o.age);
    }
}
