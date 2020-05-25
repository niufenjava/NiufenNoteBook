package io.niufen.common.enums;

import io.niufen.common.constant.StringConstants;
import io.niufen.common.tool.ObjectTools;

/**
 * 通用状态枚举
 * @author haijun.zhang@luckincoffee.com
 * @date 2019-06-01 15:35
 **/
public enum StatusEnum implements IEnum{

    /**
     * 0，无效； 1，有效
     */
    INVALID(0,"无效"),
    VALID(1,"有效");

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
    StatusEnum(Integer index, String name){
        this.index = index;
        this.name = name;
    }

    /**
     * 通过ID获取名字
     * @param index 下标
     * @return String 名字
     */
    public static String getNameByIndex(Integer index){
        if(ObjectTools.isNull(index)){
            return StringConstants.EMPTY;
        }
        for(StatusEnum s: StatusEnum.values()){
            if(s.getIndex().equals(index)){
                return s.getName();
            }
        }
        return StringConstants.EMPTY;
    }

    /**
     * 返回随机的下标值
     * @return StatusEnum.index
     */
    public static Integer randomIndex(){
        StatusEnum[] values = StatusEnum.values();
        int index=(int)(Math.random()*values.length);
        return values[index].getIndex();
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
