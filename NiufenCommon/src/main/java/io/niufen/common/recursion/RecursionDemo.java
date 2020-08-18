package io.niufen.common.recursion;

/**
 * @author haijun.zhang
 * @date 2020/6/15
 * @time 13:12
 */
public class RecursionDemo {

    /**
     * 假如这里有 n 个台阶，每次你可以跨 1 个台阶或者 2 个台阶，请问走这 n 个台阶有多少种走法？
     * 递推公式：f(n) = f(n-1) + f(n-2)
     * 终止条件：n == 1 return 1； n == 2 return 2
     *
     * @param n 总台阶数
     * @return 梯子的走法
     */
    public static int ladder(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        System.out.println("n:" + n);
        return ladder(n - 1) + ladder(n - 2);
    }

    /**
     * 问题：3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，
     * 假如兔子都不死，问每个月的兔子总数为多少？
     * 分析：首先我们要明白题目的意思指的是每个月的兔子总对数；
     * 假设将兔子分为小中大三种，兔子从出生后三个月后每个月就会生出一对兔子。
     * 那么我们假定第一个月的兔子为小兔子，第二个月为中兔子，第三个月之后就为大兔子，那么
     * 第一个月分别有1、0、0
     * 第二个月分别为0、1、0
     * 第三个月分别为1、0、1
     * 第四个月分别为1、1、1
     * 第五个月分别为2、1、2
     * 第六个月分别为3、2、3
     * 第七个月分别为5、3、5……
     * <p>
     * 兔子总数分别为：1、1、2、3、5、8、13……
     * <p>
     * 递推公式：f(n) = f(n-1) + f(n-2)
     * 终止条件：f(1) = 1; f(2) = 1
     *
     * @param n 第几个月
     * @return 第几个月兔子的数量
     */
    public static int fibonacci(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * 问题：递归阶乘 n! = n * (n-1) * (n-2) * ...* 1  (n>0)
     * 分析：
     * n = 2 > 2*1
     * n = 3 > 3*2*1
     * 递推公式：f(n) = n * f(n-1)
     * 终止条件：n == 1 return ;1
     *
     * @param n 阶乘数
     * @return 阶乘结果
     */
    public static int mulity(int n) {
        if (n == 1) {
            return 1;
        }
        return n * mulity(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(ladder(6));
        System.out.println(fibonacci(7));
        System.out.println(mulity(3));
        System.out.println(mulity(4));
    }
}
