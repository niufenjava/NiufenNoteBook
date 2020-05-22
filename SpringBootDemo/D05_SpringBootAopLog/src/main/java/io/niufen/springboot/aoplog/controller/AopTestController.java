package io.niufen.springboot.aoplog.controller;

import io.niufen.common.annotation.SysLog;
import io.niufen.common.response.R;
import io.niufen.springboot.common.module.sys.form.SysUserCreateForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haijun.zhang
 * @date 2020/5/14
 * @time 21:26
 */
@RestController
@RequestMapping("/aop")
public class AopTestController {

    @RequestMapping("/index")
    @SysLog(value = "获取系统index",operateType = 1)
    public String index(){
        return "HelloWorld";
    }

    @RequestMapping("/test")
    @SysLog(value = "测试接口",operateType = 2)
    public R test(@RequestBody SysUserCreateForm sysUserCreateForm){
        return R.ok();
    }
}
