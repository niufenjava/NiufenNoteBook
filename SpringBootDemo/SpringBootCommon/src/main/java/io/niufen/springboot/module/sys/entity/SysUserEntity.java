package io.niufen.springboot.module.sys.entity;

import io.niufen.springboot.common.base.entity.BaseEntity;
import io.niufen.common.constant.SysConstants;
import io.niufen.common.enums.StatusEnum;
import io.niufen.common.enums.YesOrNoEnum;
import io.niufen.common.util.DateUtils;
import io.niufen.common.util.FakerUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 11:13
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SysUserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -339516038496531943L;

    private String username;

    private String password;

    private Integer sex;

    private String phone;

    private Integer status;

    private Integer delFlag;

    public static SysUserEntity testNewEntity(){
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(FakerUtils.firstNameEN()+FakerUtils.idNumberCN());
        sysUserEntity.setPassword(FakerUtils.password());
        sysUserEntity.setSex(FakerUtils.sex());
        sysUserEntity.setPhone(FakerUtils.cellPhone());
        sysUserEntity.setStatus(StatusEnum.VALID.getIndex());
        sysUserEntity.setDelFlag(YesOrNoEnum.NO.getIndex());
        sysUserEntity.setCreateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysUserEntity.setCreateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysUserEntity.setCreateTime(DateUtils.curTime());
        return sysUserEntity;
    }
}
