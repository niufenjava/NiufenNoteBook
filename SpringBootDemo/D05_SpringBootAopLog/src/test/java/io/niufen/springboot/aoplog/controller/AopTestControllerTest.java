package io.niufen.springboot.aoplog.controller;

import io.niufen.springboot.common.module.sys.form.SysUserCreateForm;
import org.junit.Test;

import static org.junit.Assert.*;

public class AopTestControllerTest extends BaseApiTest{

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
