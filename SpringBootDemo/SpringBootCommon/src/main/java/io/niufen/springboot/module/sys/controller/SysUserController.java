package io.niufen.springboot.module.sys.controller;

import io.niufen.common.constant.SysConstants;
import io.niufen.common.enums.StatusEnum;
import io.niufen.common.enums.YesOrNoEnum;
import io.niufen.springboot.common.response.R;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.module.sys.form.SysUserCreateForm;
import io.niufen.springboot.module.sys.form.SysUserPageQueryForm;
import io.niufen.springboot.module.sys.form.SysUserUpdateForm;
import io.niufen.springboot.module.sys.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author haijun.zhang
 * @date 2020/5/14
 * @time 21:26
 */
@RestController
@RequestMapping(value = "/sysUser", produces = {"application/json;charset=UTF-8"})
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("index")
    public String index(){
        return "系统用户接口";
    }

    @PostMapping("save")
    public R save(@Valid @RequestBody SysUserCreateForm sysUserCreateForm) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(sysUserCreateForm.getUsername());
        sysUserEntity.setPassword(sysUserCreateForm.getPassword());
        sysUserEntity.setSex(sysUserCreateForm.getSex());
        sysUserEntity.setPhone(sysUserCreateForm.getPhone());
        sysUserEntity.setStatus(StatusEnum.VALID.getIndex());
        sysUserEntity.setDelFlag(YesOrNoEnum.YES.getIndex());
        sysUserEntity.setId(0L);
        sysUserEntity.setCreateUserId(0L);
        sysUserEntity.setCreateUserName("");
        sysUserEntity.setCreateTime(new Date());
        sysUserService.save(sysUserEntity);
        return R.ok();
    }

    @PostMapping("page")
    public R page(@RequestBody SysUserPageQueryForm sysUserPageQueryForm) {
        SysUserEntity pageQuerySysUserEntity = new SysUserEntity();
        BeanUtils.copyProperties(sysUserPageQueryForm,pageQuerySysUserEntity);
        return sysUserService.pageByCriteria(pageQuerySysUserEntity).toResult();
    }

    @PostMapping("update")
    public R update(@Valid @RequestBody SysUserUpdateForm sysUserUpdateForm) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(sysUserUpdateForm.getId());
        sysUserEntity.setUsername(sysUserUpdateForm.getUsername());
        sysUserEntity.setPassword(sysUserUpdateForm.getPassword());
        sysUserEntity.setSex(sysUserUpdateForm.getSex());
        sysUserEntity.setPhone(sysUserUpdateForm.getPhone());
        sysUserEntity.setStatus(StatusEnum.VALID.getIndex());
        sysUserEntity.setDelFlag(YesOrNoEnum.YES.getIndex());

        sysUserEntity.setUpdateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysUserEntity.setCreateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysUserEntity.setCreateTime(new Date());
        sysUserService.updateSelectiveById(sysUserEntity);
        return R.ok();
    }

    @PostMapping("delete/{id}")
    public R delete(@PathVariable("id")Long id) {
        sysUserService.removeById(id);
        return R.ok();
    }

    @RequestMapping("detail/{id}")
    public R detail(@PathVariable("id")Long id) {
        return R.ok(sysUserService.getBOById(id));
    }

}
