package pres.niufen.common;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
 * 但不够简便；
 *
 * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
 * 4.以输入流的形式获取返回内容 5.关闭输入流
 *
 * @author H__D
 *
 */
public class HttpConnectionUtil {


    /**
     *
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String downloadDir,String filess) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(filess);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }

    public static void main(String[] args) {
    String a ="http://carresources.luckycoffee.com/carresources/resource/ucartp/4577cfbc2f4646d0a08b74b802fd9ec7.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/1a99e4d962414ebc058e98d8d8ca475c.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/6c6923842670bf23aea3f474c28cd6e1.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/ca55ceba63bf356653e983d52fe16bf4.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/2d12fbc83f6d480425d74d6ed939e1bc.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/41168d049ec248fdd9dcbe63ff58c8e7.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/765a97da5008fd7eaf09adfae2410f55.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/bcb3e1ad954e30a38b6162d9f81f0926.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/c99635f0bc659ba4509858d960a7adea.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/dee16d4fb1967ce7e6108c258c6956ac.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/1b7b118fb509b8ee7c0561a5b9895fef.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/ba5bc164c1b5d9f1daf4357e67149c21.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/43afc8b40763929634c3cc735838e876.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/c457ada74526cac492348c4a757bea0c.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/3b266306be79107cc7ba91a2c460267b.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/f5e581acd4e41d03b196a98c9be42e0e.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/c22560a793a37267cf1a61174eab3fb4.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/1a65009974377d82cdea049aa81020c5.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/5bba58dcb3f6dbb109d853eee7b935db.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/cb403a6f20c4752967354c4fe080d50f.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/d7b1372b6d5e21405df2bf5a023c213b.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/5b27b99aae4c4b011a30c3c784256edd.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/fcc3dd01c861c63f984a12b09c4b6f2a.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/d1e898ceec92e3ef8a001d982b65945a.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/aeb4b4ae508ee3931aba881737d00bf5.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/bc5ca6f3bb7ecaab072727777fb91490.csv-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/f8a91ac5326f304befb02ae4ed39264c.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/5174d33ffd7f64999eaeac6a93ed50c2.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/6fe8b74555bb2d624cb6d3b7ec6512be.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/2c435ff35c9cd18103907f7ca139116e.zip-\n" +
            "http://carresources.luckycoffee.com/carresources/resource/ucartp/2d156315d0ad44dc859b8256dd775837.zip";
        // 下载文件测试
        String aa[] = a.split("-\n");
        for (int i = 0; i < aa.length; i++) {
            System.out.println(aa[i]);
            String zhouzui = "zip";
            if(aa[i].contains(".csv")){
                zhouzui = "csv";
            }
            downloadFile(aa[i], "D://","d://WX/"+(i+1)+"."+zhouzui);
        }


    }

}