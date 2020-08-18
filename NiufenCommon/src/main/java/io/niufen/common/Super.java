package io.niufen.common;

/**
 * @author haijun.zhang
 * @date 2020/6/18
 * @time 12:42
 */
public class Super {
    protected int number;

    protected void showNumber() {
        System.out.println("number = " + number);
    }
}

class Sub extends Super {
    void bar() {
        super.number = 10;
        super.showNumber();
    }
}
