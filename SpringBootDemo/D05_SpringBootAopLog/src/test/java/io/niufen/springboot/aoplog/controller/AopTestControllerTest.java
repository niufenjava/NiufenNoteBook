package io.niufen.springboot.aoplog.controller;

import io.niufen.springboot.module.sys.form.SysUserCreateForm;
import org.junit.Test;

public class AopTestControllerTest extends Base64ApiTest {

    @Test
    public void index() {
        get("/aop/index");
    }

    @Test
    public void test1() {
        SysUserCreateForm sysUserCreateForm = SysUserCreateForm.testNewEntity();
        post("/aop/test",sysUserCreateForm);
    }
}
