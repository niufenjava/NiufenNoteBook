package pres.niufen.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author niufenjava
 * @Date 2018-12-01 23:14
 **/
public class TextUtil {

    public static List<String> readTextFile4line(String filename) {
        List<String> list = new ArrayList<>();
        File file = new File(filename);
        BufferedReader reader = null;
        String temp = null;
        int line = 1;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                list.add(temp);
                line++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}
