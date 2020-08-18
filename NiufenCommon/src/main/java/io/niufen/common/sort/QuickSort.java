package io.niufen.common.sort;

import io.niufen.common.core.util.ArrayUtil;

/**
 * @author haijun.zhang
 * @date 2020/6/14
 * @time 15:13
 */
public class QuickSort {

    /**
     * 快速排序
     *
     * @param a 待排序数组
     */
    public static void quickSort(int[] a) {
        quickSortInternally(a, 0, a.length - 1);
    }

    /**
     * 快速排序递归函数
     *
     * @param a 待排序数组
     * @param p 数组起始下标
     * @param r 数组末尾下标
     */
    private static void quickSortInternally(int[] a, int p, int r) {
        // 终止条件
        if (p >= r) {
            return;
        }
        // 获取分区点
        int q = partition(a, p, r);
        // 递归左边部分
        quickSortInternally(a, p, q - 1);
        // 递归右边部分
        quickSortInternally(a, q + 1, r);
    }

    /**
     * 分区函数
     *
     * @param a 待排序数组
     * @param p 起始下标
     * @param r 结束下标
     * @return pivot 基点
     */
    private static int partition(int[] a, int p, int r) {
        // 选择 p 到 r 区间的最后一个元素
        int pivot = a[r];
        // 游标i
        int i = p;

        // 遍历数组
        // 每次都从未处理的区间 A[i…r-1] 中取一个元素 A[j]，与 pivot 对比
        for (int j = p; j < r; ++j) {
            // 小于 pivot，则将其加入到已处理区间的尾部，也就是 A[i]的位置。
            if (a[j] < pivot) {
                // 如果 i == j 那么继续下一个
                if (i == j) {
                    ++i;
                } else {
                    // 否则 A[i] A[j] 交换
                    int tmp = a[i];
                    a[i++] = a[j];
                    a[j] = tmp;
                }
            }
        }
        // 将 pivot 点放在 中间
        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;

        int [] printTmp = new int [r-1];
        for (int j = 0; j < r - p -1; j++) {
            printTmp[j] = a[p++];
        }
        System.out.println("分区点i："+i +"：" + ArrayUtil.toString(printTmp));
        // 返回 pivot 的下标
        return i;
    }

    public static void main(String[] args) {
//        int[] array = {1, 2, 3, 4, 6, 5};
        int[] array = {6, 11, 3, 9, 8};
        System.out.println("排序前：" + ArrayUtil.toString(array));
        quickSort(array);
        System.out.println("排序后：" + ArrayUtil.toString(array));
    }
}
