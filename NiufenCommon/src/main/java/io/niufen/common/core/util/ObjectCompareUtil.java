package io.niufen.common.core.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haijun.zhang
 * @date 2020/5/10
 * @time 23:22
 */
public class ObjectCompareUtil {

    /**
     * 比较两个BEAN或MAP对象的值是否相等
     * 如果是BEAN与MAP对象比较时MAP中的key值应与BEAN的属性值名称相同且字段数目要一致
     * @param source
     * @param target
     * @return
     */
    public static boolean equals(Object source, Object target) {
        if (source == null || target == null) {
            return false;
        }
        boolean rv = true;
        if (source instanceof Map) {
            rv = mapOfSrc(source, target, rv);
        } else {
            rv = classOfSrc(source, target, rv);
        }
        return rv;
    }

    /**
     * 源目标为MAP类型时
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean mapOfSrc(Object source, Object target, boolean rv) {
        HashMap<String, String> map = new HashMap<String, String>();
        map = (HashMap) source;
        for (String key : map.keySet()) {
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                if(tarMap.get(key)==null){
                    rv = false;
                    break;
                }
                if (!map.get(key).equals(tarMap.get(key))) {
                    rv = false;
                    break;
                }
            } else {
                String tarValue = getClassValue(target, key) == null ? "" : getClassValue(target, key).toString();
                if (!tarValue.equals(map.get(key))) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 源目标为非MAP类型时
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean classOfSrc(Object source, Object target, boolean rv) {
        Class<?> srcClass = source.getClass();
        Field[] fields = srcClass.getFields();
        for (Field field : fields) {
            String nameKey = field.getName();
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                if(tarMap.get(nameKey)==null){
                    rv = false;
                    break;
                }
                if (!tarMap.get(nameKey).equals(srcValue)) {
                    rv = false;
                    break;
                }
            } else {
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                String tarValue = getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey)
                        .toString();
                if (!srcValue.equals(tarValue)) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 根据字段名称取值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[] {});
                } catch (Exception e) {
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID")
                        && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase()
                        .equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject) {
        Map<String, Map<String,Object>> resultMap=compareFields(oldObject,newObject);

        if(resultMap.size()>0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Map<String,Object>> compareFields(Object oldObject, Object newObject) {
        Map<String, Map<String, Object>> map = null;

        try{
           // 只有两个对象都是同一类型的才有可比性
            if (oldObject.getClass() == newObject.getClass()) {
                map = new HashMap<String, Map<String,Object>>();

                Class clazz = oldObject.getClass();
                //获取object的所有属性
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,Object.class).getPropertyDescriptors();

                for (PropertyDescriptor pd : pds) {
                    //遍历获取属性名
                    String name = pd.getName();

                    //获取属性的get方法
                    Method readMethod = pd.getReadMethod();

                    // 在oldObject上调用get方法等同于获得oldObject的属性值
                    Object oldValue = readMethod.invoke(oldObject);
                    // 在newObject上调用get方法等同于获得newObject的属性值
                    Object newValue = readMethod.invoke(newObject);

                    if(oldValue instanceof List){
                        continue;
                    }

                    if(newValue instanceof List){
                        continue;
                    }

                    if(oldValue instanceof Timestamp){
                        oldValue = new Date(((Timestamp) oldValue).getTime());
                    }

                    if(newValue instanceof Timestamp){
                        newValue = new Date(((Timestamp) newValue).getTime());
                    }

                    if(oldValue == null && newValue == null){
                        continue;
                    }else if(oldValue == null && newValue != null){
                        Map<String,Object> valueMap = new HashMap<String,Object>();
                        valueMap.put("oldValue",oldValue);
                        valueMap.put("newValue",newValue);

                        map.put(name, valueMap);

                        continue;
                    }

                    if (!oldValue.equals(newValue)) {// 比较这两个值是否相等,不等就可以放入map了
                        Map<String,Object> valueMap = new HashMap<String,Object>();
                        valueMap.put("oldValue",oldValue);
                        valueMap.put("newValue",newValue);

                        map.put(name, valueMap);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return map;
    }

}

