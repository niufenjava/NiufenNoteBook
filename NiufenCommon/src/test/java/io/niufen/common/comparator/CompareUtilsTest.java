package io.niufen.common.comparator;

import io.niufen.common.collection.ListUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

@Slf4j
public class CompareUtilsTest {

    @Test
    public void compare() {
        assert CompareUtils.compare(1, 2,true) < 0;
        assert CompareUtils.compare(2, 2,true) == 0;
        assert CompareUtils.compare(3, 2,true) > 0;
        assert CompareUtils.compare(null, "a", true) > 0 ;
        assert CompareUtils.compare("a", null, true) < 0 ;
        assert CompareUtils.compare(null, "a", false) < 0;
        assert CompareUtils.compare("a", null, false) > 0;
    }

    @Test
    public void compareByUser() {

        // this.compareTo(o) 正序 1-2 = -1 进行位置替换
        List<Integer> list = ListUtils.newArrayList(2,1,3);
        Collections.sort(list);
        for (Integer integer : list) {
            log.error("list.sort:{}", integer);
        }

        AesUser user2 = new AesUser("B",2);
        AesUser user1 = new AesUser("A",1);
        assert CompareUtils.compare(user1,user2,Boolean.FALSE) < 0;


        DescUser descUser2 = new DescUser("B",2);
        DescUser descUser1 = new DescUser("A",1);
        assert CompareUtils.compare(descUser1,descUser2,Boolean.FALSE) > 0;
    }

}

@Slf4j
@Data
@AllArgsConstructor
class AesUser implements Comparable<AesUser>{

    private String name;

    private Integer age;

    @Override
    public int compareTo(AesUser o) {
        log.error("compareTo start");
        log.error("this:{}",this.toString());
        log.error("compare:{}",o.toString());
        log.error("this.age.compareTo(compare.age):{}",this.getAge().compareTo(o.getAge()));
        log.error("compareTo start");
        return this.getAge().compareTo(o.getAge());
    }
}

@Slf4j
@Data
@AllArgsConstructor
class DescUser implements Comparable<DescUser>{

    private String name;

    private Integer age;

    @Override
    public int compareTo(DescUser o) {
        log.error("compareTo start");
        log.error("this:{}",this.toString());
        log.error("compare:{}",o.toString());
        log.error("compare.age.compareTo(this.age):{}",o.getAge().compareTo(this.getAge()));
        log.error("compareTo start");
        return o.getAge().compareTo(this.getAge());
    }
}
