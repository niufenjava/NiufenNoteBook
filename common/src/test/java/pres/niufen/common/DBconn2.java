package pres.niufen.common;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBconn2 {
    int bufferSize = 20 * 1024 * 1024;//设读取文件的缓存为20MB
    ArrayList<String> column3string = new ArrayList<String>();
    ArrayList<String> column13string = new ArrayList<String>();

    String driver = "com.mysql.jdbc.Driver";
    static String dbName = "balance";
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
        System.out.println("filename:"+filename);
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
            String sql = " INSERT INTO t_bat (create_time, good_code, good_name, good_make_type, is_free, cate_one, cate_two, other_info, temp, tenp_ohter, sugar, milk, cream, good_loss_info, good_price, other_price, send_price, price_shishou, price_good_price, zekou, price_coupon_fentan, coupon_no, coupon_name, coupon_laiyuan, coupon_mianzhi, coupon_plan_name, coupon_plan_no, coupon_faquan_name, coupon_faquan_no, invitation_code, mkt_name, mkt_jianmian, yhq_no, yhq_type, yhq_name, yhq_plan_name, order_no, order_type, city, shop, shop_no, shop_no_four, shop_grade, sum_send_price, good_num, sum_send_jianmian, send_mkt_jianmian, send_shop_jianmian, send_yhq_jianmian, send_yingfu, order_income, zong_good_yunying_jianmian, zong_coupon_jianmian, zong_good_mkt_jianmian, zong_yhq_jianmian, pay_type, jiucan_type, order_status, send_no_status, jiedan_time, yuji_zhizuo_time, shiji_zhizuo_time, pay_time, finash_time, manyidu_pingfen, order_pingjia_neirong, sender, yuji_send_time, shiji_send_time, cancel_reason, bumanyi_reason, coffer_name, coffer_no, member_phone_no, member_id, invitation_member_code, invitation_type, invitation_one, invitation_two, invitation_three, register_time, is_first_time, member_first_time, member_first_shop, member_first_shop_no, member_device_no, member_sex, return_money, make_status, mini_pro_jianmian_money, mini_pro_jianmian_type, order_laiyuan, member_laiyuan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //String sql = "insert INSERT INTO t_bat (create_time, good_code, good_name, good_make_type, is_free) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            try {
                while ((lineTXT = br.readLine()) != null) {
                    System.out.println("count:"+count);
                    if(count==0){
                        count++;
                        continue;

                    }
                    temp = lineTXT.split(",");
                    if(temp.length<15){
                        continue;
                    }
                    for(int i = 0 ; i<93;i++){
                        pstmt.setString(i+1, temp[i]);
                    }

                    pstmt.addBatch();// 用PreparedStatement的批量处理
                    if (count % 5000 == 0) {// 当增加了500个批处理的时候再提交
                        pstmt.executeBatch();// 执行批处理
                        conn.commit();
                        pstmt.clearBatch();
                        //打印插入的条数
                        //System.out.println("count: " + count);
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
        System.out.println("开始........");
        DBconn2 test = new DBconn2();
        //test.show();
        long timeTestStart = System.currentTimeMillis();// 记录开始时间
        for (int i = 1; i <= 1; i++) {
            try {
                test.readFile("C:\\Users\\niufenjava\\Desktop\\report.csv");
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