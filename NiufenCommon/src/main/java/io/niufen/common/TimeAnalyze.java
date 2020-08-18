package io.niufen.common;

/**
 * @author haijun.zhang
 * @date 2020/6/22
 * @time 19:06
 */
public class TimeAnalyze {
    public static void logn(int n){
        int count = 1;
        int i =0;
        while (count < n){
            count = count*2;
            System.out.println(i++);
        }
    }

    public static void main(String[] args) {
        TimeAnalyze.logn(1000);
    }
}
