package io.niufen.springboot.druid.service;

import io.niufen.common.core.constant.SysConstants;
import io.niufen.common.core.enums.StatusEnum;
import io.niufen.common.core.enums.YesOrNoEnum;
import io.niufen.common.core.util.DateUtils;
import io.niufen.common.core.util.FakerUtil;
import io.niufen.springboot.druid.entity.SysUserEntity;
import io.niufen.springboot.druid.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author niufen
 */
@Slf4j
@Service
public class DemoSysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Async
    public void multi(){
        for (int i = 0; i < 1; i++) {
            SysUserEntity sysUserEntity = newSysUserEntity();
            try{
                sysUserRepository.save(sysUserEntity);
            }catch (Exception e){
                continue;
            }
        }
    }

    @Async
    public void batchSave(){
        for (int i = 0; i < 10; i++) {
            SysUserEntity sysUserEntity = newSysUserEntity();
            try{
            sysUserRepository.save(sysUserEntity);
            }catch (Exception e){
                continue;
            }
        }
    }

    @Async
    public void slowSql(){
        for (int i = 0; i < 1; i++) {
            sysUserRepository.count();
        }
    }

    public SysUserEntity getUser(){
        return sysUserRepository.getOne(100L);
    }

    public SysUserEntity newSysUserEntity(){
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(FakerUtil.firstNameEN()+ FakerUtil.cellPhone());
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
