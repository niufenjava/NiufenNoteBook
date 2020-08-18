package io.niufen.common.sort;

import io.niufen.common.core.util.ArrayUtil;

/**
 * @author haijun.zhang
 * @date 2020/6/13
 * @time 10:18
 */
public class BubbleSort {

    public static void sort(int[] array) {
        if (null == array || array.length <= 1) {
            return;
        }
        // 外层循环控制比较轮数i
        for (int i = 0; i < array.length -1; i++) {
            // 提前退出冒泡循环标志
            boolean exchangeFlag = false;
            // 内层循环控制每一轮比较次数，每进行一次排序都会找出一个较大值
            // (array.length - 1)防止索引越界，(array.length - 1 - i)减少比较次数
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 前面的数大于后面的数就进行交换
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    // 设置有数据交换
                    exchangeFlag = true;
                }
            }
            System.out.println(ArrayUtil.toString(array));
            // 没有数据交换，提前退出
            if (!exchangeFlag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
//        int[] array = {1, 2, 3, 4, 6, 5};
        // int[] array = {4, 5, 6, 3, 2, 1};
        int[] array = {6, 5, 4, 3, 2, 1};
        System.out.println("排序前："+ArrayUtil.toString(array));
        sort(array);
        System.out.println("排序后："+ArrayUtil.toString(array));
    }
}
