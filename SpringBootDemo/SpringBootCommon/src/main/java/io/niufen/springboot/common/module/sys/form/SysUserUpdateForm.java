package io.niufen.springboot.common.module.sys.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author haijun.zhang
 * @date 2020/5/16
 * @time 22:40
 */
@Data
public class SysUserUpdateForm {

    private Long id;

    @NotBlank( message = "用户名不能为空")
    private String username;

    private String password;

    private Integer sex;

    private String phone;

    private Integer status;


}
