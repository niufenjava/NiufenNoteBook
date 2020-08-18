package io.niufen.common;

import java.util.ArrayList;

/**
 * @author haijun.zhang
 * @date 2020/6/24
 * @time 23:20
 */
public class test18_顺时针打印矩阵 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        int[][] matrix = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        ArrayList<Integer> list = printMatrix(matrix);
        System.out.println(list);

    }

    /*
     *
        简单来说，就是不断地收缩矩阵的边界
        定义四个变量代表范围，up、down、left、right
        向右走存入整行的值，当存入后，该行再也不会被遍历，代表上边界的 up 加一，同时判断是否和代表下边界的 down 交错
        向下走存入整列的值，当存入后，该列再也不会被遍历，代表右边界的 right 减一，同时判断是否和代表左边界的 left 交错
        向左走存入整行的值，当存入后，该行再也不会被遍历，代表下边界的 down 减一，同时判断是否和代表上边界的 up 交错
        向上走存入整列的值，当存入后，该列再也不会被遍历，代表左边界的 left 加一，同时判断是否和代表右边界的 right 交错
     *
     * */
    /*
     * 1  2  3  4
     * 5  6  7  8
     * 9 10  11 12
     * 13 14 15 16
     * */

    //顺时针打印矩阵
    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return list;
        }
        int up = 0;
        //行高
        int down = matrix.length - 1;
        int left = 0;
        // 列高
        int right = matrix[0].length - 1;
        while (true) {
            //向右
            for (int i = left; i <= right; i++) {
                list.add(matrix[up][i]);
            }
            if (++up > down) {
                break;
            }
            //向下
            for (int i = up; i <= down; i++) {
                list.add(matrix[i][right]);
            }
            if (--right < left) {
                break;
            }
            //向左
            for (int i = right; i >= left; i--) {
                list.add(matrix[down][i]);
            }
            if (--down < up) {
                break;
            }
            //向上
            for (int i = down; i >= up; i--) {
                list.add(matrix[i][left]);
            }
            if (++left > right) {
                break;
            }
        }
        return list;
    }

}
