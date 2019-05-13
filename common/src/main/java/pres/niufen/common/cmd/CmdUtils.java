package pres.niufen.common.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author niufenjava
 * @Date 2019-01-28 10:26
 **/
public class CmdUtils {

    public static void execWindowsCmd(String cmd) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
        String line;
        while ((line = br.readLine()) != null){
            if(!line.contains("DEBUG")){
                System.out.println(line);
            }
        }

        //error
        BufferedReader ir = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName("GBK")));
        while ((line = ir.readLine()) != null){
            System.out.println(line);
        }
        process.waitFor();
    }

}
