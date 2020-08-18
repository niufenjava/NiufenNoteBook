package io.niufen.common.search;

/**
 * @author haijun.zhang
 * @date 2020/6/14
 * @time 21:28
 */
public class BinarySearch {

    /**
     * 最简单二分操作算法
     * 有序数组中不存在重复元素
     *
     * @param a     待搜索数组
     * @param value 待搜索值
     * @return 返回在数组中的位置
     */
    public static int simpleBinarySearch(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            // int mid = (low + high) / 2;
            int mid = (low + (high - low) / 2);
            System.out.println("low:" + low + " mid:" + mid + " high:" + high);
            if (a[mid] == value) {
                return mid;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 递归二分查找
     *
     * @param a     给定的已排序数组
     * @param low   起始下标
     * @param high  最高下标
     * @param value 搜寻的值
     * @return 返回该值对应的数组下标，如果未找到，返回 -1
     */
    public static int recursiveBinarySearch(int[] a, int low, int high, int value) {
        if (low > high) {
            return -1;
        }
        int mid = low + ((high - low) >> 1);
        System.out.println("low:" + low + " mid:" + mid + " high:" + high);
        if (a[mid] == value) {
            return mid;
        } else if (a[mid] < value) {
            return recursiveBinarySearch(a, mid + 1, high, value);
        } else {
            return recursiveBinarySearch(a, low, mid - 1, value);
        }
    }


    /**
     * 变体一：查找第一个值等于给定值的元素
     *
     * @param a     给定已排序数组
     * @param value 搜索的值
     * @return 搜索值的下标
     */
    public static int binarySearchOne(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            System.out.println("low:" + low + " mid:" + mid + " high:" + high);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (a[mid - 1] != value)) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 变体二：查找最后一个值等于给定值的元素
     *
     * @param a     给定已排序数组
     * @param value 搜索的值
     * @return 搜索值的下标
     */
    public static int binarySearchTwo(int[] a, int value) {
        int low = 0;
        int n = a.length;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == n - 1) || (a[mid + 1] != value)) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 变体三：查找第一个大于等于给定值的元素
     *
     * @param a     给定已排序数组
     * @param value 搜索的值
     * @return 搜索值的下标
     */
    public static int binarySearchThree(int[] a, int value) {
        int low = 0;
        int n = a.length;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= value) {
                if ((mid == 0) || (a[mid - 1] < value)) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 变体四：查找最后一个小于等于给定值的元素
     *
     * @param a     给定已排序数组
     * @param value 搜索的值
     * @return 搜索值的下标
     */
    public static int binarySearchFour(int[] a, int value) {
        int low = 0;
        int n = a.length;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else {
                if ((mid == n - 1) || (a[mid + 1] > value)) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int value = 2;
        System.out.println(simpleBinarySearch(a, value));
        System.out.println(recursiveBinarySearch(a, 0, a.length - 1, 2));
        int[] b = {1, 3, 4, 5, 6, 8, 8, 8, 11, 18};
        System.out.println(binarySearchOne(b, 8));
        System.out.println(binarySearchTwo(b, 8));
//        System.out.println(binarySearchThree(b, 8));
//        System.out.println(binarySearchFour(b, 8));
    }
}
