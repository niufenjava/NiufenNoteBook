package io.niufen.common.enums;

import io.niufen.common.constant.StringConstants;
import io.niufen.common.tool.ObjectTools;

/**
 * 通用状态枚举
 * @author haijun.zhang@luckincoffee.com
 * @date 2019-06-01 15:35
 **/
public enum SexEnum implements IEnum{

    /**
     * 1-男；2-女
     */
    MALE(1,"男"),
    FEMALE(2,"女");

    /**
     * 索引
     */
    Integer index;

    /**
     * 名字
     */
    String name;

    /**
     *按照枚举数值找到对应枚举
     * @param index 下标
     * @param name 名字
     */
    SexEnum(Integer index, String name){
        this.index = index;
        this.name = name;
    }

    /**
     * 通过ID获取名字
     *
     * @param index 下标
     * @return String 名字
     */
    public static String getNameByIndex(Integer index) {
        if(ObjectTools.isNull(index)){
            return StringConstants.EMPTY;
        }
        for (SexEnum s : SexEnum.values()) {
            if (ObjectTools.equals(s.getIndex(),index)) {
                return s.getName();
            }
        }
        return StringConstants.EMPTY;
    }

    /**
     * 通过ID获取枚举
     *
     * @param index 下标
     * @return OperateFromEnum 枚举
     */
    public static SexEnum getEnumByIndex(int index) {
        for (SexEnum s : SexEnum.values()) {
            if (s.getIndex() == index) {
                return s;
            }
        }
        return null;
    }


    /**
     * 返回随机的下标值
     * @return StatusEnum.index
     */
    public static Integer randomIndex(){
        SexEnum[] values = SexEnum.values();
        int index=(int)(Math.random()*values.length);
        return values[index].getIndex();
    }

    /**
     * 是否为男
     *
     * @param index 下标
     * @return OperateFromEnum 枚举
     */
    public static Boolean isMale(Integer index) {
        if(ObjectTools.isNull(index)){
            return Boolean.FALSE;
        }
        if (ObjectTools.equals(SexEnum.MALE.getIndex(),index) ) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 是否为女
     *
     * @param index 下标
     * @return OperateFromEnum 枚举
     */
    public static Boolean isFemale(Integer index) {
        if(ObjectTools.isNull(index)){
            return Boolean.FALSE;
        }
        return !isMale(index);
    }

    @Override
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
