package pres.niufen.common;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class DBconn3 {
    int bufferSize = 20 * 1024 * 1024;//设读取文件的缓存为20MB
    ArrayList<String> column3string = new ArrayList<String>();
    ArrayList<String> column13string = new ArrayList<String>();

    String driver = "com.mysql.jdbc.Driver";
    static String dbName = "lucky_cube";
    static String password = "2222";
    static String userName = "root";
    static String url = "jdbc:mysql://localhost:3306/" + dbName + "?rewriteBatchedStatements=true";
    static String sql = "select * from user";
    Connection conn = null;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void readFile(String filename) throws SQLException, FileNotFoundException {
       // System.out.println("filename:"+filename);
        File file = new File(filename);
        if (file.isFile() && file.exists()) {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            InputStreamReader isr = new InputStreamReader(bis);
            BufferedReader br = new BufferedReader(isr, bufferSize);
            int count = 0;// 计数器
            String lineTXT = null;
            PreparedStatement pstmt = null;
            String[] temp = null;
            Connection conn = getConnection();
            conn.setAutoCommit(false);// 设置数据手动提交，自己管理事务
            String sql = " INSERT INTO t_bi_mkt_invitation_code " +
                    "(id, invitation_code, invitation_type, source_no, source_name, status, create_emp, create_time, modify_emp, modify_time, delete_time, source_id, use_status, remark, belong_one_level_channel, belong_two_level_channel, belong_three_level_channel, put_in_store, store_in_city, store_in_city_id, store_in_area, store_in_area_id, store_status, store_status_code, put_in_store_id)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            try {
                while ((lineTXT = br.readLine()) != null) {
                  //  System.out.println("count:"+count);
                    if(count==0){
                        count++;
                        continue;

                    }
                    temp = lineTXT.split("\\,");
                    if(temp.length<10){
                        continue;
                    }
                 //   System.out.println(temp.length);
                    for(int i = 0 ; i<temp.length;i++){
                        String abc = temp[i];
                        if(abc != null ){
                            abc = abc.replaceAll("\"","");
                        }
                        if(abc == null){
                            abc = "";
                        }
                        if(i==10){
                            abc = null;
                        }
                        if(i==11){
                            abc = null;
                        }
                        if(i==0){
                            abc = String.valueOf((int)(Math.random() * 1))+ "" + String.valueOf(System.currentTimeMillis())+ (int)(Math.random() * 10000000)+"";
                        }
                        pstmt.setString(i+1, abc);
                       // System.out.println((i+1)+" : " + abc);
                    }
                    for(int i = temp.length; i< 25;i++){
                        pstmt.setString(i+1, "");
                    }
                    //System.out.println(pstmt.toString());
                    pstmt.addBatch();// 用PreparedStatement的批量处理
                    if (count % 10000 == 0) {// 当增加了500个批处理的时候再提交
                        pstmt.executeBatch();// 执行批处理
                        conn.commit();
                        pstmt.clearBatch();
                        //打印插入的条数
                        System.out.println("count: " + count);
                    }
                    count++;
                }
                pstmt.executeBatch();// 执行批处理
                conn.commit();
                pstmt.close();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void show() {
        System.out.println("This is string:");
        for (int i = 0; i < column3string.size(); i++) {
            System.out.println(column3string.get(i));
        }
        System.out.println("This is integer:");
        for (int i = 0; i < column13string.size(); i++) {
            System.out.println(column13string.get(i));
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(System.currentTimeMillis());
        System.out.println("开始........");
        DBconn3 test = new DBconn3();
        //test.show();
        long timeTestStart = System.currentTimeMillis();// 记录开始时间
        for (int i = 1; i <= 1000; i++) {
            try {
                test.readFile("C:\\Users\\niufenjava\\Desktop\\统计结果.csv");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("succeed");
        long timeTestEnd = System.currentTimeMillis();// 记录结束时间
        long time = timeTestEnd - timeTestStart;
        long secondTime = time / 1000;
        System.out.println("Time:" + secondTime + " seconds");
    }


}