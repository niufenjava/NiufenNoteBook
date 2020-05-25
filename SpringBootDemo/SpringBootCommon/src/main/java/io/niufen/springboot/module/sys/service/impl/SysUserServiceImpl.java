package io.niufen.springboot.module.sys.service.impl;

import io.niufen.springboot.common.base.service.impl.BaseServiceImpl;
import io.niufen.common.enums.SexEnum;
import io.niufen.common.enums.StatusEnum;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.ListUtils;
import io.niufen.springboot.module.sys.bo.SysUserBO;
import io.niufen.springboot.module.sys.entity.SysUserEntity;
import io.niufen.springboot.module.sys.mapper.SysUserMapper;
import io.niufen.springboot.module.sys.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author haijun.zhang
 * @date 2020/5/15
 * @time 23:00
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper,SysUserEntity, SysUserBO> implements SysUserService {

    @Override
    public SysUserBO entityToBO(SysUserEntity entity) {
        SysUserBO bo = new SysUserBO();
        BeanUtils.copyProperties(entity,bo);
        bo.setSexDesc(SexEnum.getNameByIndex(entity.getSex()));
        bo.setStatusDesc(StatusEnum.getNameByIndex(entity.getStatus()));
        return bo;
    }

    @Override
    public Collection<SysUserBO> entityListToBOList(Collection<SysUserEntity> entityList) {
        List<SysUserBO> boList = ListUtils.newLinkedList();
        if(ObjectTools.isNotEmpty(entityList)){
            for (SysUserEntity sysUserEntity : entityList) {
                boList.add(entityToBO(sysUserEntity));
            }
        }
        return boList;
    }
}
