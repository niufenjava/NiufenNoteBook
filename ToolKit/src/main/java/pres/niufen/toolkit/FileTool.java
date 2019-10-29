package pres.niufen.toolkit;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @Description 系统文件处理工具
 * @Author niufenjava
 * @Date 2018-12-02 23:45
 **/
public class FileTool {

    /**
     * 批量修正某个文件夹下的所有文件
     */
    public static void fileRenameBat(){
        String dirPath = "D:\\PDF资源\\";
        File dir = new File(dirPath);
        String [] fileNames = dir.list();
        for (String fileName : fileNames) {
            File file = new File(dirPath+"\\"+fileName);
            String newFileName = fileName.replaceAll("541118 ","").replaceAll("548214 ","").replaceAll("-5","")
                    .replaceAll("556994 ","").replaceAll("561824 ","")
                    .replaceAll("\\[www\\.java1234\\. com]","")
                    .replaceAll("\\[www\\.java1234\\.com]","")
                    .replaceAll("\\[WWW\\.JAVA1234\\.COM]","")
                    .replaceAll("@www\\.java1234\\.com","")
                    .replaceAll("www\\.java1234\\.com","")
                    .replaceAll("《","").replaceAll("》","");
            boolean b = file.renameTo(new File(dir +"\\"+ newFileName));
        }
    }

    public static void main(String [] args) throws UnsupportedEncodingException {
        FileTool.fileRenameBat();
//// 运行结果：2
//            System.out.println("测试".getBytes("ISO8859-1").length);
//// 运行结果：4
//            System.out.println("测试".getBytes("GB2312").length);
//// 运行结果：4
//            System.out.println("测试a3".getBytes("GBK").length);
//// 运行结果：6
//            System.out.println("测试".getBytes("UTF-8").length);

    }
}
