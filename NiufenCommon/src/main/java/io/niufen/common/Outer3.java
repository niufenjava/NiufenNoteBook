package io.niufen.common;

/**
 * @author haijun.zhang
 * @date 2020/6/18
 * @time 14:45
 */
public class Outer3 {

    private int out_a = 1;
    private static int STATIC_b = 2;

    public void testFunctionClass() {
        int inner_c = 3;
        class Inner {
            private void fun() {
                System.out.println(out_a);
                System.out.println(STATIC_b);
                System.out.println(inner_c);
            }
        }
        Inner inner = new Inner();
        inner.fun();
    }

    public static void testStaticFunctionClass() {
        int d = 3;
        class Inner {
            private void fun() {
                // System.out.println(out_a); 编译错误，定义在静态方法中的局部类不可以访问外部类的实例变量
                System.out.println(STATIC_b);
                System.out.println(d);
            }
        }
        Inner inner = new Inner();
        inner.fun();
    }

    public static void main(String[] args) {
        Outer3.testStaticFunctionClass();
        new Outer3().testFunctionClass();
    }
}

