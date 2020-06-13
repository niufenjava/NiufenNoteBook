package io.niufen.common.sort;

import io.niufen.common.core.util.ArrayUtil;

/**
 * 选择排序
 * 核心原理：每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
 *
 * @author haijun.zhang
 * @date 2020/6/13
 * @time 16:16
 */
public class SelectionSort {

    /**
     *  核心原理：每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
     * 1. 初始状态：无序区为R[1..n]，有序区为空；
     * 2. 第 i 趟排序 (i=1,2,3…n-1) 开始时
     *   1. 当前有序区为R[1..i-1] ；无序区为 R(i..n）。
     *   2. 该趟排序从当前无序区中选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换。
     *   3. 使 R[1..i] 变为 记录个数增加1个的新有序区 ；R[i+1..n) 变为记录个数减少1个的新无序区。
     * 3. N-1 趟结束，数组有序化了。
     *
     * @param array 待排数组
     */
    public static void sort(int[] array) {
        if (null == array || array.length < 2) {
            return;
        }
        // 无序区为R[1..n]，有序区为空；
        for (int i = 0; i < array.length - 1; i++) {
            // 保存最小索引数
            int minIndex = i;
            // i+1 - array.length 待排序区间
            for (int j = i + 1; j < array.length; j++) {
                // 找到最小的数
                if (array[minIndex] > array[j]) {
                    minIndex = j;
                }
            }
            // 交换元素位置
            if (i != minIndex) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
            System.out.println("第" + i + "轮：" + ArrayUtil.toString(array));
        }
    }

    public static void main(String[] args) {
//        int[] array = {1, 2, 3, 4, 6, 5};
        int[] array = {4, 5, 6, 3, 2, 1};
        System.out.println("排序前：" + ArrayUtil.toString(array));
        sort(array);
        System.out.println("排序后：" + ArrayUtil.toString(array));
    }
}
