package io.niufen.common.sort;

import io.niufen.common.core.util.ArrayUtil;

/**
 * 插入排序
 * 核心思想：
 * 1. 构建有序序列，就是数组的第一个元素。
 * 2. 对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 3. 并保证已排序序列一直有序。
 *
 * @author haijun.zhang
 * @date 2020/6/13
 * @time 14:20
 */
public class InsertionSort {

    /**
     * 插入排序算法描述
     * 1. 从第一个元素开始，该元素可以认为已经被排序；
     * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 3. 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 5. 将新元素插入到该位置后；
     * 6. 重复步骤2~5。
     * <p>
     * 初始：4，5，6，1，3，2
     * 1->  1，4，5，6，3，2
     * 2->  1，2，4，5，6，3
     * 3->  1，2，3，4，5，6
     *
     * @param array 待排序数组
     */
    public static void sort(int[] array) {
        if (null == array || array.length <= 1) {
            return;
        }
        // 默认第一个元素是已排序空间
        for (int i = 1; i < array.length; i++) {
            // value 待排序元素
            int value = array[i];
            // 已排序元素的最后一个数
            int j = i - 1;
            // 查找插入的位置，从已排序数据从后向前
            for (; j >= 0; j--) {
                // //如果当前数前面的数大于当前数，则把前面的数向后移一个位置
                if (array[j] > value) {
                    // 数据移动
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            // 在已排数据中合适的地方插入数据
            array[j + 1] = value;
            System.out.println("第" + (i) + "轮后：" + ArrayUtil.toString(array)+"; 插入位置:"+(j+1));
        }
    }

    public static void main(String[] args) {
//        int[] array = {1, 2, 3, 4, 6, 5};
         int[] array = {4, 5, 6, 3, 2, 1};
        System.out.println("排序前："+ ArrayUtil.toString(array));
        sort(array);
        System.out.println("排序后："+ArrayUtil.toString(array));
    }
}
