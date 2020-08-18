package io.niufen.common;

/**
 * @author haijun.zhang
 * @date 2020/6/18
 * @time 14:15
 */
public class Outer {
    private int age = 12;

    class Inner {
        private int age = 13;

        public void print() {
            int age = 14;
            System.out.println("局部变量：" + age);
            System.out.println("内部类变量：" + this.age);
            System.out.println("外部类变量：" + Outer.this.age);
        }
    }

    public static void main(String[] args) {
        String str = "Hello";
        str = str + " World";
        System.out.println("str=" + str);
    }

    public static void swap(Student x, Student y) {
        Student temp = x;
        x = y;
        y = temp;
        System.out.println("x:" + x.getName());
        System.out.println("y:" + y.getName());
    }

}

class Student{
    String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


