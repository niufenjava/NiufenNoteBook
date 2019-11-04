package pres.niufen.common.tools;

//import org.springframework.util.CollectionUtils;

import java.util.HashSet;

/**
 * @author haijun.zhang
 * @version 1.0
 * @date 2019-08-17 17:22
 **/
public class SetUtils {

    public static final String SEMICOLON = "；";

    /**
     * 将 String 类型的HashSet中的String集合转换成以分号隔开的字符串
     * @param hashSet
     * @return
     */
//    public static String setTransToStr(HashSet<String> hashSet){
//        StringBuilder strBuilder = new StringBuilder();
//        if(!CollectionUtils.isEmpty(hashSet)){
//            for (String s : hashSet) {
//                strBuilder.append(s).append(SEMICOLON);
//            }
//        }
//        String retStr = strBuilder.toString();
//        if(retStr.endsWith(SEMICOLON)){
//            retStr = retStr.substring(0,retStr.length()-1);
//        }
//        return retStr;
//    }


//    public static void main(String [] args){
//        System.out.println(SetUtils.setTransToStr(null));
//        HashSet<String> hashSet= new HashSet<>();
//        System.out.println(SetUtils.setTransToStr(hashSet));
//        hashSet.add("a");
//        hashSet.add("b");
//        System.out.println(SetUtils.setTransToStr(hashSet));
//    }


}
