package io.niufen.springboot.module.sys.controller;

import io.niufen.common.core.enums.StatusEnum;
import io.niufen.common.core.util.FakerUtil;
import io.niufen.springboot.common.Base64ApiTest;
import io.niufen.springboot.module.sys.form.SysUserCreateForm;
import io.niufen.springboot.module.sys.form.SysUserPageQueryForm;
import io.niufen.springboot.module.sys.form.SysUserUpdateForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserControllerTest extends Base64ApiTest {

    @Test
    public void index() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/sysUser/index").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void save() throws Exception {
        String uri = "/sysUser/save";
        SysUserCreateForm sysUserCreateForm = new SysUserCreateForm();
        sysUserCreateForm.setUsername(FakerUtil.nameEN());
        sysUserCreateForm.setPassword(FakerUtil.password());
        sysUserCreateForm.setSex(FakerUtil.sex());
        sysUserCreateForm.setPhone(FakerUtil.cellPhone());

        MockHttpServletResponse response = postTest(uri, sysUserCreateForm);
        assertSuccess(response);
    }

    @Test
    public void page() throws Exception {

        String uri = "/sysUser/save";
        SysUserCreateForm sysUserCreateForm = new SysUserCreateForm();
        sysUserCreateForm.setUsername(FakerUtil.nameEN());
        sysUserCreateForm.setPassword(FakerUtil.password());
        sysUserCreateForm.setSex(FakerUtil.sex());
        sysUserCreateForm.setPhone(FakerUtil.cellPhone());

        post(uri, sysUserCreateForm);

        String list = "/sysUser/page";
        SysUserPageQueryForm pageQueryForm = new SysUserPageQueryForm();
        pageQueryForm.setUsername(sysUserCreateForm.getUsername());
        pageQueryForm.setPassword(sysUserCreateForm.getPassword());
        pageQueryForm.setSex(sysUserCreateForm.getSex());
        pageQueryForm.setPhone(sysUserCreateForm.getPhone());

        MockHttpServletResponse response = postTest(list, sysUserCreateForm);
        assertSuccess(response);


    }


    @Test
    public void update() throws Exception {

//        String uri = "/sysUser/save";
//        SysUserCreateForm sysUserCreateForm = new SysUserCreateForm();
//        sysUserCreateForm.setUsername(FakerUtils.nameEN());
//        sysUserCreateForm.setPassword(FakerUtils.password());
//        sysUserCreateForm.setSex(FakerUtils.sex());
//        sysUserCreateForm.setPhone(FakerUtils.cellPhone());
//
//        post(uri, sysUserCreateForm);

        String update = "/sysUser/update";
        SysUserUpdateForm sysUserUpdateForm = new SysUserUpdateForm();
        sysUserUpdateForm.setId(101L);
        sysUserUpdateForm.setUsername(FakerUtil.nameEN());
        sysUserUpdateForm.setPassword(FakerUtil.password());
        sysUserUpdateForm.setSex(FakerUtil.sex());
        sysUserUpdateForm.setPhone(FakerUtil.cellPhone());
        sysUserUpdateForm.setStatus(StatusEnum.INVALID.getIndex());

        MockHttpServletResponse response = postTest(update, sysUserUpdateForm);
        assertSuccess(response);


    }

    @Test
    public void delete() throws Exception {
        String delete = "/sysUser/delete/"+101;
        post(delete);
    }

    @Test
    public void detail() throws Exception {
        String detail = "/sysUser/detail/"+102;
        post(detail);
    }
}
