package io.niufen.springboot.common.util;

import io.niufen.springboot.common.constant.SqlConstants;
import io.niufen.common.constant.StringConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.springboot.common.annotation.TableName;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haijun.zhang
 * @date 2020/5/11
 * @time 17:08
 */
@Slf4j
public class MapperUtils {

    /**
     * 通过entity实体对象获取表名，如果该类使用了 TablePrefix 注解，那么通过该注解获取前缀
     * 默认 t_开头
     * @param aClass 实体对象
     * @return 返回表名
     */
    public static String tableNameByClass(Class<?> aClass){
        String tablePrefix = SqlConstants.TABLE_DEFAULT_PREFIX;
        if(ObjectTools.isNull(aClass)){
            log.error("tableNameByClass 传入参数null");
            return StringConstants.BLANK;
        }
        boolean hasAnnotation = aClass.isAnnotationPresent(TableName.class);

        if ( hasAnnotation ) {
            TableName testAnnotation = aClass.getAnnotation(TableName.class);
            String annotationTablePrefix = testAnnotation.value();
            if(ObjectTools.isNotNull(annotationTablePrefix)){
                tablePrefix = annotationTablePrefix;
            }
        }

        return tablePrefix+camelToUnderline(removeEntityStr(toLowerCaseFirstOne(aClass.getSimpleName())));
    }

    /**
     * 通过entity实体对象获取表名，如果该类使用了 TablePrefix 注解，那么通过该注解获取前缀
     * 默认 t_开头
     * @param entity 实体对象
     * @return 返回表名
     */
    public static String tableNameByEntity(Object entity){
        String tablePrefix = SqlConstants.TABLE_DEFAULT_PREFIX;
        if(ObjectTools.isNull(entity)){
            log.error("tableNameByClass 传入参数null");
            return StringConstants.BLANK;
        }
        Class<?> aClass = entity.getClass();
        boolean hasAnnotation = aClass.isAnnotationPresent(TableName.class);

        if ( hasAnnotation ) {
            TableName testAnnotation = aClass.getAnnotation(TableName.class);
            String annotationTablePrefix = testAnnotation.value();
            if(ObjectTools.isNotNull(annotationTablePrefix)){
                tablePrefix = annotationTablePrefix;
            }
        }

        return tablePrefix+camelToUnderline(removeEntityStr(toLowerCaseFirstOne(aClass.getSimpleName())));
    }

    /**
     * 驼峰转下划线
     * @param str 字符串
     * @return 下划线
     */
    public static String camelToUnderline(String str) {
        if (str == null || StringConstants.BLANK.equals(str.trim())) {
            return StringConstants.BLANK;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(StringConstants.Mark.UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * @param str 字符串
     * @return 下划线
     */
    public static String toVar(String str) {

        return "#{"+str+"}";
    }

    public static String removeEntityStr(String str){
        if(ObjectTools.isNotBlank(str) && str.endsWith(SqlConstants.ENTITY)){
            return str.substring(0,str.length()-6);
        }
        return str;
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}
