package io.niufen.springboot.module.sys.enums;

import io.niufen.common.core.constant.StringConstants;
import io.niufen.common.core.enums.IEnum;
import io.niufen.common.core.util.ObjectUtil;

/**
 * 系统日志类型枚举
 * @author haijun.zhang@luckincoffee.com
 * @date 2019-06-01 15:35
 **/
public enum SysLogTypeEnum implements IEnum{

    /**
     * 1-非登录请求；2-操作日志
     */
    NONE_LOGIN_REQUEST(1,"非登录请求"),
    OPERATION(2,"操作日志");

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
    SysLogTypeEnum(Integer index, String name){
        this.index = index;
        this.name = name;
    }

    /**
     * 通过ID获取名字
     * @param index 下标
     * @return String 名字
     */
    public static String getNameByIndex(Integer index){
        if(ObjectUtil.isNull(index)){
            return StringConstants.EMPTY;
        }
        for(SysLogTypeEnum s: SysLogTypeEnum.values()){
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
        SysLogTypeEnum[] values = SysLogTypeEnum.values();
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
