package pres.niufen.common.tools;

//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description List 工具类
 * @Author haijun.zhang@luckincoffee.com
 * @Date 2019-06-02 22:55:33
 **/
public class ListUtils {

    public static final String SEMICOLON = "；";
    public static final String COMMA = ",";

    /**
     * 创建一个 LinkedList() 并返回
     *
     * @return LinkedList
     */
    public static List newLinkedList() {
        return new LinkedList();
    }


//    /**
//     * 将 String 类型的list中的String集合转换成以分号隔开的字符串
//     * @param list
//     * @return
//     */
//    public static String toStrBySemicolon(List<String> list){
//        StringBuilder strBuilder = new StringBuilder();
//        if(!org.springframework.util.CollectionUtils.isEmpty(list)){
//            for (String s : list) {
//                strBuilder.append(s).append(SEMICOLON);
//            }
//        }
//        String retStr = strBuilder.toString();
//        if(retStr.endsWith(SEMICOLON)){
//            retStr = retStr.substring(0,retStr.length()-1);
//        }
//        return retStr;
//    }
//
//
//    /**
//     * 将 String 类型的list中的String集合转换成以分号隔开的字符串
//     * @param list
//     * @return
//     */
//    public static String toStrByComma(List<String> list){
//        StringBuilder strBuilder = new StringBuilder();
//        if(!org.springframework.util.CollectionUtils.isEmpty(list)){
//            for (String s : list) {
//                strBuilder.append(s).append(COMMA);
//            }
//        }
//        String retStr = strBuilder.toString();
//        if(retStr.endsWith(COMMA)){
//            retStr = retStr.substring(0,retStr.length()-1);
//        }
//        return retStr;
//    }
//
//
//    /**
//     * 对 List<String> 进行去重操作
//     * @param targetList 目标List
//     * @return 去重都的list
//     */
//    public static List<String> duplicateRemoval(List<String> targetList)
//    {
//        if(CollectionUtils.isEmpty(targetList) || targetList.size()==0){
//            return ListUtils.newLinkedList();
//        }
//        LinkedHashSet<String> tmpSet = new LinkedHashSet<>(targetList.size());
//        tmpSet.addAll(targetList);
//        targetList.clear();
//        targetList.addAll(tmpSet);
//        return targetList;
//    }
//
//    /**
//     * 将以逗号分隔的字符串转换为List
//     * @param strArray
//     * @return List<String>
//     */
//    public static List<String> strArrayToList(String strArray){
//        List<String> list = ListUtils.newLinkedList();
//        if(StringUtils.isNotBlank(strArray)){
//            String [] array = strArray.split(COMMA);
//            for (String s : array) {
//                list.add(s);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 将以逗号分隔的字符串转换为List
//     * @param strArray
//     * @return List<Integer>
//     */
//    public static List<Integer> strArrayToIntList(String strArray){
//        List<Integer> list = ListUtils.newLinkedList();
//        if(StringUtils.isNotBlank(strArray)){
//            String [] array = strArray.split(COMMA);
//            for (String s : array) {
//                list.add(Integer.valueOf(s));
//            }
//        }
//        return list;
//    }


}
