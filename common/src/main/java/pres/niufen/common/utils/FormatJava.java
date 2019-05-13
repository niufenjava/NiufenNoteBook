package pres.niufen.common.utils;


import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.*;
import java.nio.charset.Charset;

/**
 *
 *https://blog.csdn.net/dontstopme/article/details/80818951
 * @author another
 *
 */
public class FormatJava {

    //修改成需要转码的文件根目录
    private static String filePath = "D:\\SOFT\\jd-gui-windows-1.4.0\\com";

    public static void main(String[] args) throws Exception {
        File file = new File(filePath);
        formatJava(file);
    }

    private static void formatJava(File file) throws Exception {
        // 判断是不是文件
        if (file.isFile()) {
            // 处理.java结尾的文件
            if (file.getPath().indexOf(".java") == -1) {
                return;
            }
            String path = file.getPath();
            FileInputStream fis = new FileInputStream(file);
            //跳过UTF-8的文件
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                isr.close();
                //删除原文件，想保留则将该行注释，想定义文件名可以修改path字符串
                file.delete();
                // 转换成utf-8格式的字节数组
                byte[] utf8Bytes = sb.toString().getBytes("UTF-8");
                String utf8Str = new String(utf8Bytes, "UTF-8");
                File targetFile = new File(path);
                FileOutputStream fos = new FileOutputStream(targetFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "utf8");
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(utf8Str);
                bw.close();
                osw.close();

        } else {
            for (File subFile : file.listFiles()) {
                //递归修改子目录文件
                formatJava(subFile);
            }
        }
    }
}