package io.niufen.springboot.module.sys.form;

import io.niufen.common.util.FakerUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author haijun.zhang
 * @date 2020/5/16
 * @time 22:40
 */
@Data
public class SysUserCreateForm {

    @NotBlank( message = "用户名不能为空")
    private String username;

    @NotBlank( message = "密码不能为空")
    private String password;

    private Integer sex;

    @NotBlank( message = "电话号码不能为空")
    private String phone;


    public static SysUserCreateForm testNewEntity(){
        SysUserCreateForm sysUserCreateForm = new SysUserCreateForm();
        sysUserCreateForm.setUsername(FakerUtils.firstNameEN()+FakerUtils.idNumberCN());
        sysUserCreateForm.setPassword(FakerUtils.password());
        sysUserCreateForm.setSex(FakerUtils.sex());
        sysUserCreateForm.setPhone(FakerUtils.cellPhone());
        return sysUserCreateForm;
    }
}
