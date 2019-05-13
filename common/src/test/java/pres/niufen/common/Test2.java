package pres.niufen.common;

import java.io.*;
import java.util.HashSet;

/**
 * @Description
 * @Author niufenjava
 * @Date 2018-07-19 19:20
 **/
public class Test2 {
    public static void main(String [] args){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。

        try {
            HashSet<String> rxSet = new HashSet<>();
            HashSet<String> rxSet2 = new HashSet<>();
            String rxStr = "";
            fis = new FileInputStream("d:\\瑞幸.TXT");// FileInputStream
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while ((rxStr = br.readLine()) != null) {
                if(rxStr.length() >21){
                    if(rxSet.contains(rxStr.trim().substring(0,21))){
                        System.out.println(rxStr.trim().substring(0,21));
                    }
                    rxSet.add(rxStr.trim().substring(0,21));
                }
            }
            System.out.println(rxSet.size());// 打印出str1


            for (String str : rxSet) {
               // System.out.println(str);
            }


        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
