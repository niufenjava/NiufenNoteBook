package io.niufen.springboot.druid.multi.mybatis.entity;

import io.niufen.springboot.common.base.entity.BaseEntity;
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
}
