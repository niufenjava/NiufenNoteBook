package io.niufen.common.collection;

import io.niufen.common.util.SetUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class SetUtilTest {


    /**
     * 验证 SetUtils.newSet() 方法
     * 通过 SetUtils.newSet() 静态方法获取到一个 空的（非null） HashSet
     */
    @Test
    public void newSet() {
        Set<Object> sets = SetUtil.newSet();
        assert null != sets;
        assert HashSet.class.equals(sets.getClass());
        assert !Set.class.equals(sets.getClass());
        assert !LinkedHashSet.class.equals(sets.getClass());
        assert sets.isEmpty();
        assert 0 == sets.size();
    }

    /**
     * 同 newSet()
     */
    @Test
    public void newHashSet() {
        Set<Object> sets = SetUtil.newHashSet();
        assert null != sets;
        assert HashSet.class.equals(sets.getClass());
        assert !Set.class.equals(sets.getClass());
        assert !LinkedHashSet.class.equals(sets.getClass());
        assert sets.isEmpty();
        assert 0 == sets.size();
    }

    /**
     * 验证 SetUtils.newLinkedHashSet() 方法
     * 通过 SetUtils.newLinkedHashSet() 静态方法获取到一个 空的（非null） LinkedHashSet
     */
    @Test
    public void newLinkedHashSet() {
        Set<Object> sets = SetUtil.newLinkedHashSet();
        assert null != sets;
        assert LinkedHashSet.class.equals(sets.getClass());
        assert !Set.class.equals(sets.getClass());
        assert !HashSet.class.equals(sets.getClass());
        assert sets.isEmpty();
        assert 0 == sets.size();
    }

    @Test
    public void newHashSetByVarargs() {
        String[] varargs = {"A"};
        assert SetUtil.newHashSet(varargs).getClass().equals(HashSet.class);

        Set<Object> hashSet = SetUtil.newHashSet("B", "B", "C", "A");

        assert hashSet.getClass().equals(HashSet.class);

        log.debug("strHashSet.size()" + hashSet.size());
        assert hashSet.size() == 3;

        int i = 1;
        // HashSet 会进行默认排序，不会根据加入的顺序进行排序
        for (Object o : hashSet) {
            log.debug("strHashSet item:{}", o);
            assert i != 1 || "A".equals(o);
            assert i != 2 || "B".equals(o);
            assert i != 3 || "C".equals(o);
            i++;
        }
    }

    @Test
    public void newHashSetByCollection() {

        List<String> list = testList();
        Set<String> hashSet = SetUtil.newHashSet(list);

        assert hashSet.getClass().equals(HashSet.class);
        assert hashSet.size() == 3;
    }

    @Test
    public void newHashSetByIterator() {

        List<String> list = testList();
        Set<String> hashSet = SetUtil.newHashSet(list.iterator());

        assert hashSet.getClass().equals(HashSet.class);
        assert hashSet.size() == 3;
    }


    @Test
    public void newLinkedHashSetByVarargs() {

        Object elements = null;
        assert SetUtil.newLinkedHashSet(elements).getClass().equals(LinkedHashSet.class);

        Set<Object> strLinkedHashSet = SetUtil.newLinkedHashSet("B", "B", "C", "A");

        assert strLinkedHashSet.getClass().equals(LinkedHashSet.class);

        log.debug("strHashSet.size()" + strLinkedHashSet.size());
        assert strLinkedHashSet.size() == 3;

        int i = 1;
        // LinkedHashSet 会根据set的顺序进行默认排序
        for (Object o : strLinkedHashSet) {
            log.debug("strHashSet item:{}", o);
            assert i != 1 || "B".equals(o);
            assert i != 2 || "C".equals(o);
            assert i != 3 || "A".equals(o);
            i++;
        }
    }

    @Test
    public void newLinkedHashSetByCollection() {
        List<String> list = testList();
        Set<String> strLinkedHashSet = SetUtil.newLinkedHashSet(list);

        assert strLinkedHashSet.getClass().equals(LinkedHashSet.class);
        assert strLinkedHashSet.size() == 3;
    }

    @Test
    public void newLinkedHashSetByIterator() {
        List<String> list = testList();
        Set<String> strLinkedHashSet = SetUtil.newLinkedHashSet(list.iterator());

        assert strLinkedHashSet.getClass().equals(LinkedHashSet.class);
        assert strLinkedHashSet.size() == 3;
    }

    public ArrayList<String> testList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("B");
        list.add("B");
        list.add("C");
        list.add("A");
        return list;
    }
}
