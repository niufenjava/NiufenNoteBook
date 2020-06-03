package io.niufen.springboot.module.sys.entity;

import io.niufen.springboot.common.base.entity.BaseEntity;
import io.niufen.common.core.constant.SysConstants;
import io.niufen.common.core.enums.StatusEnum;
import io.niufen.common.core.enums.YesOrNoEnum;
import io.niufen.common.core.util.DateUtils;
import io.niufen.common.core.util.FakerUtil;
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
        sysUserEntity.setUsername(FakerUtil.firstNameEN()+ FakerUtil.idNumberCN());
        sysUserEntity.setPassword(FakerUtil.password());
        sysUserEntity.setSex(FakerUtil.sex());
        sysUserEntity.setPhone(FakerUtil.cellPhone());
        sysUserEntity.setStatus(StatusEnum.VALID.getIndex());
        sysUserEntity.setDelFlag(YesOrNoEnum.NO.getIndex());
        sysUserEntity.setCreateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysUserEntity.setCreateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysUserEntity.setCreateTime(DateUtils.curTime());
        return sysUserEntity;
    }
}
