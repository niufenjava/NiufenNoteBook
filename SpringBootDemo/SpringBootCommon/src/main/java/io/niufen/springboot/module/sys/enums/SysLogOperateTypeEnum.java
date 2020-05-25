package io.niufen.springboot.module.sys.enums;

import io.niufen.common.constant.StringConstants;
import io.niufen.common.enums.IEnum;
import io.niufen.common.tool.ObjectTools;

/**
 * 系统日志类型枚举
 * @author haijun.zhang@luckincoffee.com
 * @date 2019-06-01 15:35
 **/
public enum SysLogOperateTypeEnum implements IEnum{

    /**
     * 1-新增；2-删除；3-修改；4-查询
     */
    CREATE(1,"新增"),
    DELETE(2,"删除"),
    UPDATE(3,"修改"),
    QUERY(4,"查询")
    ;

    /**
     * 索引
     */
    public Integer index;

    /**
     * 名字
     */
    String name;

    /**
     *按照枚举数值找到对应枚举
     * @param index 下标
     * @param name 名字
     */
    SysLogOperateTypeEnum(Integer index, String name){
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
        for(SysLogOperateTypeEnum s: SysLogOperateTypeEnum.values()){
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
        SysLogOperateTypeEnum[] values = SysLogOperateTypeEnum.values();
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
