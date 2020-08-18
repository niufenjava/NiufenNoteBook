package io.niufen.common;

/**
 * @author gress 匿名内部类，我只会使用一次的类
 * <p>
 * 就假如我想吃一个泡面,但我不可能建一个厂,制造一个流水线,生产一包泡面之后就在也不去使用这个泡面厂了
 * 所以这里引申出匿名内部类,而我们建立的泡面厂就像这里构建的一个类Pencil 铅笔类一样
 */

interface Pen {
    public void write();
}

class Pencil implements Pen {
    @Override
    public void write() {
        //铅笔 的工厂
    }
}

class People {
    public void user(Pen pen) {
        pen.write();
    }
}

class AnyInnerTest {
    public static void main(String args[]) {
        People guo = new People();
        guo.user(new Pen() {
            @Override
            public void write() {
                System.out.println("写子");
            }
        });
    }
}
