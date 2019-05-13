package pres.niufen.toolkit;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtil;

import java.io.*;

/**
 * @Description 系统文件处理工具
 * @Author niufenjava
 * @Date 2018-12-02 23:45
 **/
public class ExcelReadTool {
    
    /**
     * 日志
     */
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ExcelReadTool.class);

    public void testExcel2003NoModel() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\niufenjava\\Desktop\\用户扩展信息表.xlsx"));
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();

            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            excelReader.read();
            for (Object object : listener.getDatas()) {
//                if(object!=null){
//                    String [] str = object.toString().split(",");
//                    System.out.println("'"+str[1].trim()+"' "+str[3].trim()+" COMMENT '"+str[2].trim().replaceAll("[\\t\\n\\r]","")+"',");
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String [] args) throws UnsupportedEncodingException, FileNotFoundException {
        new ExcelReadTool().testExcel2003NoModel();
    }


}
