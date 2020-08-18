package io.niufen.common.sort;

import io.niufen.common.core.util.ArrayUtil;

/**
 * @author haijun.zhang
 * @date 2020/6/14
 * @time 17:29
 */
public class ShellSort {
    /**
     * 希尔排序
     *
     * @param a 待排序数组
     */
    public static void sort(int[] a) {
        int temp;
        //默认步长为数组长度除以2
        int step = a.length;
        do {
            step = step / 2;
            //确定分组数
            for (int i = 0; i < step; i++) {
                //对分组数据进行直接插入排序
                for (int j = i + step; j < a.length; j = j + step) {
                    temp = a[j];
                    int k;
                    for (k = j - step; k >= 0; k = k - step) {
                        if (a[k] > temp) {
                            a[k + step] = a[k];
                        } else {
                            break;
                        }
                    }
                    a[k + step] = temp;
                }
                System.out.println("第" + (i+1) + "轮后：" + ArrayUtil.toString(a));
            }
        } while (step != 1);
    }

    public static void main(String[] args) {
        // int[] array = {1, 2, 3, 4, 6, 5};
        int[] array = {4, 5, 6, 3, 2, 1};
        System.out.println("排序前：" + ArrayUtil.toString(array));
        sort(array);
        System.out.println("排序后：" + ArrayUtil.toString(array));
    }
}
