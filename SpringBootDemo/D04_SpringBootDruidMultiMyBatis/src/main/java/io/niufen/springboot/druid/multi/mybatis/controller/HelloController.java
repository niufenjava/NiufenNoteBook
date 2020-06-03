package io.niufen.springboot.druid.multi.mybatis.controller;

import io.niufen.common.core.constant.SysConstants;
import io.niufen.common.core.enums.StatusEnum;
import io.niufen.common.core.enums.YesOrNoEnum;
import io.niufen.common.core.util.DateUtils;
import io.niufen.common.core.util.FakerUtil;
import io.niufen.springboot.druid.multi.mybatis.entity.SysUserEntity;
import io.niufen.springboot.druid.multi.mybatis.mapper.SysUserMapperByXml;
import io.niufen.springboot.druid.multi.mybatis.mapper2.SysUserMapper2ByXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haijun.zhang
 * @date 2020/5/14
 * @time 20:54
 */
@RestController
public class HelloController {

    @Autowired
    private SysUserMapper2ByXml sysUserMapper2ByXml;

    @Autowired
    private SysUserMapperByXml sysUserMapperByXml;

    @RequestMapping("/hallo")
    public String hello(){

        SysUserEntity sysUserEntity = newSysUserEntity();
        sysUserMapperByXml.insert(sysUserEntity);
        sysUserMapper2ByXml.insert(sysUserEntity);
        return "Hello";
    }




    public SysUserEntity newSysUserEntity(){
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(FakerUtil.firstNameEN()+ FakerUtil.idNumberCN());
        sysUserEntity.setPassword(FakerUtil.password());
        sysUserEntity.setSex(FakerUtil.sex());
        sysUserEntity.setPhone(FakerUtil.cellPhone());
        sysUserEntity.setStatus(StatusEnum.VALID.getIndex());
        sysUserEntity.setDelFlag(YesOrNoEnum.NO.getIndex());
        sysUserEntity.setCreateUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysUserEntity.setCreateUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysUserEntity.setCreateTime(DateUtils.curTime());
        return sysUserEntity;
    }
}
