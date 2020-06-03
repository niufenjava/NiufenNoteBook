package io.niufen.common.core.enums;

import io.niufen.common.core.constant.StringConstants;
import io.niufen.common.core.util.ObjectUtil;

/**
 * 通用状态枚举
 * @author haijun.zhang@luckincoffee.com
 * @date 2019-06-01 15:35
 **/
public enum YesOrNoEnum implements IEnum{

    /**
     * 1，是、通过; 0，不是、不通过；
     */
    YES(1,"是"),
    NO(0,"否");

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
    YesOrNoEnum(Integer index, String name){
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
        if(ObjectUtil.isNull(index)){
            return StringConstants.EMPTY;
        }
        for (YesOrNoEnum s : YesOrNoEnum.values()) {
            if (ObjectUtil.equal(s.getIndex(),index)) {
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
    public static YesOrNoEnum getEnumByIndex(int index) {
        for (YesOrNoEnum s : YesOrNoEnum.values()) {
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
        YesOrNoEnum[] values = YesOrNoEnum.values();
        int index=(int)(Math.random()*values.length);
        return values[index].getIndex();
    }

    /**
     * 通过ID获取枚举
     *
     * @param index 下标
     * @return OperateFromEnum 枚举
     */
    public static Boolean isTrue(Integer index) {
        if(ObjectUtil.isNull(index)){
            return Boolean.FALSE;
        }
        if (ObjectUtil.equal(YesOrNoEnum.YES.getIndex(),index) ) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
