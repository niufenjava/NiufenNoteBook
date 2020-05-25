package io.niufen.springboot.module.sys.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author haijun.zhang
 * @date 2020/5/16
 * @time 22:40
 */
@Data
public class SysUserPageQueryForm {

    private String username;

    private String password;

    private Integer sex;

    private String phone;


}
