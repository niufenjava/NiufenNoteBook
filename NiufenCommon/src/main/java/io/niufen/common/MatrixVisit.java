package io.niufen.common;

/**
 * @author haijun.zhang
 * @date 2020/6/24
 * @time 23:06
 */
public class MatrixVisit {

    public static void main(String[] args) {

//		int[][] matrix = new int[][] { { 1, 2, 3, 4, 5 }, { 16, 17, 18, 19, 6 }, { 15, 24, 25, 20, 7 },
//				{ 14, 23, 22, 21, 8 }, { 13, 12, 11, 10, 9 } };
        int[][] matrix = new int[][] {
                { 1,  2,  3,  4,  5,  6,  7,  8 },
                { 22, 23, 24, 25, 26, 27, 28, 9 },
                { 21, 36, 37, 38, 39, 40, 29, 10 },
                { 20, 35, 34, 33, 32, 31, 30, 11 },
                { 19, 18, 17, 16, 15, 14, 13, 12 } };
//				matrix = new int[][]{{1,2,3}};

        // x轴起始坐标
        int xStart = 0;
        // y轴结束坐标
        int xEnd = matrix.length - 1;
        // y轴起始坐标
        int yStart = 0;
        // y轴结束坐标
        int yEnd = matrix[0].length - 1;

        // 结束条件 xStart = xEnd && yStart = yEnd
        while (xStart <= xEnd && yStart <= yEnd) {
            if (xStart == xEnd && yStart == yEnd) {
                System.out.println(matrix[xStart][yStart]);
            }
            for (int j = yStart; j < yEnd; j++) {
                int i = xStart;
                int value = matrix[i][j];
                System.out.println(value);
            }

            for (int i = xStart; i < xEnd; i++) {
                int j = yEnd;
                int value = matrix[i][j];
                System.out.println(value);
            }

            //往回退的时候需要加个处理，就是防止重复打印
            for (int j = yEnd; j > yStart; j--) {
                int i = xEnd;
                int value = matrix[i][j];
                System.out.println(value);
                if(xStart==xEnd){
                    break;
                }
            }

            for (int i = xEnd; i > xStart; i--) {
                int j = yStart;
                int value = matrix[i][j];
                System.out.println(value);
                if(yStart==yEnd){
                    break;
                }
            }
            xStart++;
            yStart++;
            xEnd--;
            yEnd--;
        }

    }

}
