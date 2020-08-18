package io.niufen.common.sort;

import io.niufen.common.core.util.ArrayUtil;

/**
 * @author haijun.zhang
 * @date 2020/6/14
 * @time 10:53
 */
public class MergeSort {

    /**
     * 归并排序
     *
     * @param a 待排序数组
     */
    public static void sort(int[] a) {
        mergeSort(a, 0, a.length - 1);
    }

    /**
     * 分治递归排序
     *
     * @param a     数组
     * @param start 开始下标
     * @param end   终止终止
     */
    public static void mergeSort(int[] a, int start, int end) {
        // 终止条件：当子序列中只有一个元素时结束递归
        if (start >= end) {
            return;
        }
        // 划分子序列：取 start 到 end 之间的中间位置 mid
        int mid = (start + end) / 2;

        // 分治递归
        //对左侧子序列进行递归排序
        mergeSort(a, start, mid);
        //对右侧子序列进行递归排序
        mergeSort(a, mid + 1, end);

        //合并：将已经有序的 A[start…mid] 和 A[mid+1…end] 合并成一个有序的数组
        //并且放入 A[start…end]
        merge(a, start, mid, end);
        System.out.println("start：" + start + "；" + "mid：" + mid + "；" + "end：" + end + "；");
        System.out.println("merge后：" + mid + ArrayUtil.toString(a));
    }


    /**
     * 两路归并算法，
     * 两个排好序的子序列合并为一个子序列
     *
     * @param a     数组
     * @param left  左边数组第一个下标
     * @param mid   左边数组最后一个下标
     * @param right 右边数组随后一个下标
     */
    public static void merge(int[] a, int left, int mid, int right) {
        //1、辅助数组：申请一个临时数组 tmp，大小与 A[start…end] 相同。
        int[] tmp = new int[a.length];

        // p1 指向左边数组第一个元素
        int p1 = left;
        // p2 指向右边数组第一个元素
        int p2 = mid + 1;
        // k 临时数组起始下标
        int k = left;

        // 比较这两个元素 A[p1] 和 A[p2]，
        // 直到其中一个子数组中的所有数据都放入临时数组中
        while (p1 <= mid && p2 <= right) {
            // 如果 A[p1] <= A[p2]，
            if (a[p1] <= a[p2]) {
                // 我们就把 A[p1] 放入到临时数组 tmp，并且 i 后移一位。
                tmp[k++] = a[p1++];
            } else {
                // 否则将 A[j] 放入到数组 tmp，j 后移一位。
                tmp[k++] = a[p2++];
            }
        }

        // 将剩余的数据拷贝到临时数组tmp
        //如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while (p1 <= mid) {
            tmp[k++] = a[p1++];
        }
        //同上
        while (p2 <= right) {
            tmp[k++] = a[p2++];
        }

        // 这个时候，临时数组中存储的就是两个子数组合并之后的结果了。
        // 复制回原素组 将tmp中的数组拷贝回A[p...r]
        for (int i = left; i <= right; i++) {
            a[i] = tmp[i];
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 5, 6, 2, 3, 4};
        System.out.println("排序前：" + ArrayUtil.toString(array));
        sort(array);
        System.out.println("排序后：" + ArrayUtil.toString(array));
    }
}
