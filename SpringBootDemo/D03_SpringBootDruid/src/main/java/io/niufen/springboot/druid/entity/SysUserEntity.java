package io.niufen.springboot.druid.entity;

import io.niufen.springboot.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 11:13
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity(name = "t_sys_user")
public class SysUserEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private static final long serialVersionUID = -339516038496531943L;

    private String username;

    private String password;

    private Integer sex;

    private String phone;

    private Integer status;

    private Integer delFlag;

    public Long createUserId;

    public String createUserName;

    public Date createTime;

    public Long updateUserId;

    public String updateUserName;

    public Date updateTime;
}
