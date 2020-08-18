package io.niufen.common;

/**
 * @author haijun.zhang
 * @date 2020/6/24
 * @time 16:49
 */
public class Test {

    public static void test(int[][] a) {
        if (null == a) {
            return;
        }

        boolean order = true;
        for (int i = 0; i < a.length; i++) {
            int[] c = a[i];
            if (order) {
                for (int i1 = 0; i1 < c.length; i1++) {
                    System.out.println(c[i1]);
                }
            } else {
                for (int j = c.length - 1; j >= 0; j--) {
                    System.out.println(c[j]);
                }
            }
            order = !order;
        }

    }

    public static void main(String[] args) {
        int [][] t = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        test(t);
    }
}
