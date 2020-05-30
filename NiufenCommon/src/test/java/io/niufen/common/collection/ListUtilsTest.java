package io.niufen.common.collection;

import io.niufen.common.util.ListUtils;
import io.niufen.common.util.SetUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class ListUtilsTest {

    @Test
    public void newList() {
        List<Object> objects = ListUtils.newList();
        assert ArrayList.class.equals(objects.getClass());
        assert objects.size() == 0;
    }

    @Test
    public void testNewList() {
        List<Object> linkedList = ListUtils.newList(Boolean.TRUE);
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 0;

        List<Object> arrayList = ListUtils.newList(Boolean.FALSE);
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 0;
    }

    @Test
    public void newArrayList() {
        List<Object> arrayList = ListUtils.newArrayList();
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 0;
    }

    @Test
    public void newLinkedList() {
        List<Object> linkedList = ListUtils.newLinkedList();
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 0;
    }

    @Test
    public void newArrayListByVarargs() {
        String [] varargs = {"B","A","C","C"};
        List<Object> arrayList = ListUtils.newArrayList(varargs);
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 4;
        for (int i = 0; i < arrayList.size(); i++) {
            assert i != 0 || "B".equals(arrayList.get(i));
            assert i != 1 || "A".equals(arrayList.get(i));
            assert i != 2 || "C".equals(arrayList.get(i));
            assert i != 3 || "C".equals(arrayList.get(i));
        }
    }

    @Test
    public void newArrayListByCollection() {
        String [] varargs = {"B","A","C","C"};
        Set<Object> sets = SetUtils.newHashSet(varargs);
        List<Object> arrayList = ListUtils.newArrayList(sets);
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 3;
    }

    @Test
    public void newArrayListByIterator() {
        String [] varargs = {"B","A","C","C"};
        Set<Object> sets = SetUtils.newHashSet(varargs);
        List<Object> arrayList = ListUtils.newArrayList(sets.iterator());
        assert ArrayList.class.equals(arrayList.getClass());
        assert arrayList.size() == 3;
    }

    @Test
    public void newLinkedListByVarargs() {
        String [] varargs = {"B","A","C","C"};
        List<Object> linkedList = ListUtils.newLinkedList(varargs);
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 4;
        // ArrayList 和 LinkedList 的区别在于，一个底层是数组、一个底层是链表；都是按照插入顺序有序
        // 并不会自动排序 这点与Set不同
        for (int i = 0; i < linkedList.size(); i++) {
            assert i != 0 || "B".equals(linkedList.get(i));
            assert i != 1 || "A".equals(linkedList.get(i));
            assert i != 2 || "C".equals(linkedList.get(i));
            assert i != 3 || "C".equals(linkedList.get(i));
        }
    }

    @Test
    public void newLinkedListByCollection() {
        String [] varargs = {"B","A","C","C"};
        Set<Object> sets = SetUtils.newHashSet(varargs);
        List<Object> linkedList = ListUtils.newLinkedList(sets);
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 3;
    }

    @Test
    public void newLinkedListByIterator() {
        String [] varargs = {"B","A","C","C"};
        Set<Object> sets = SetUtils.newHashSet(varargs);
        List<Object> linkedList = ListUtils.newLinkedList(sets.iterator());
        assert LinkedList.class.equals(linkedList.getClass());
        assert linkedList.size() == 3;
    }

    @Test
    public void newCopyOnWriteArrayList() {
        List<String> copyOnWriteArrayList = ListUtils.newCopyOnWriteArrayList(ListUtils.newArrayList("1", "2", "3"));
        assert copyOnWriteArrayList.contains("1");
        assert copyOnWriteArrayList.contains("2");
        assert copyOnWriteArrayList.contains("3");
        assert !copyOnWriteArrayList.contains("4");
    }

    @Test
    public void sort(){
        List<ComparatorUser> userList = ListUtils.newList();
        userList.add(new ComparatorUser("B",2));
        userList.add(new ComparatorUser("A",1));
        userList.add(new ComparatorUser("AA",1));
        userList.add(new ComparatorUser("C",3));

        // 这里使用 lambda 表达式，实现 Comparator 比较器接口的 compare 方法
        // compare o1 为前一个元素； o2 为后一个元素
        // 返回 -1 或 0 不替换；
        // 返回 1 则进行替换
        ListUtils.sort(userList, (o1, o2) -> {
            if(o1.getAge().equals(o2.getAge())){
                if(o1.getName().equals(o2.getName()))
                    return 0;
                if(o1.getName().length() < o2.getName().length()){
                    return -1;
                }
            }
            return o1.getAge() - o2.getAge();
        });
        for (int i = 0; i < userList.size(); i++) {
            log.debug(userList.get(i).toString());
            assert i != 0 || "A".equals(userList.get(i).getName());
            assert i != 1 || "AA".equals(userList.get(i).getName());
            assert i != 2 || "B".equals(userList.get(i).getName());
            assert i != 3 || "C".equals(userList.get(i).getName());
        }

        // 这里使用 Comparator.comparingInt(ComparatorUser::getAge)); 方法对对象字段get方法直接进行比较
        ListUtils.sort(userList, Comparator.comparingInt(ComparatorUser::getAge));
        for (int i = 0; i < userList.size(); i++) {
            log.debug(userList.get(i).toString());
            assert i != 0 || "A".equals(userList.get(i).getName());
            assert i != 1 || "AA".equals(userList.get(i).getName());
            assert i != 2 || "B".equals(userList.get(i).getName());
            assert i != 3 || "C".equals(userList.get(i).getName());
        }
    }


    @Test
    public void sortByComparable(){
        List<ComparableUser> userList = ListUtils.newList();
        userList.add(new ComparableUser("B",2));
        userList.add(new ComparableUser("A",1));
        userList.add(new ComparableUser("C",3));

        Collections.sort(userList);
        for (int i = 0; i < userList.size(); i++) {
            log.debug(userList.get(i).toString());
            assert i != 0 || "A".equals(userList.get(i).getName());
            assert i != 1 || "B".equals(userList.get(i).getName());
            assert i != 2 || "C".equals(userList.get(i).getName());
        }

    }

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
