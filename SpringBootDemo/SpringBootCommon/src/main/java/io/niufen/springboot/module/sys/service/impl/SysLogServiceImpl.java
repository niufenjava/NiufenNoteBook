package io.niufen.springboot.module.sys.service.impl;

import io.niufen.common.collection.ListUtil;
import io.niufen.common.tool.ObjectTools;
import io.niufen.springboot.common.base.service.impl.BaseServiceImpl;
import io.niufen.springboot.module.sys.bo.SysLogBO;
import io.niufen.springboot.module.sys.entity.SysLogEntity;
import io.niufen.springboot.module.sys.enums.SysLogOperateTypeEnum;
import io.niufen.springboot.module.sys.enums.SysLogTypeEnum;
import io.niufen.springboot.module.sys.mapper.SysLogMapper;
import io.niufen.springboot.module.sys.service.SysLogService;
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
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLogEntity, SysLogBO> implements SysLogService {

    @Override
    public SysLogBO entityToBO(SysLogEntity entity) {
        SysLogBO bo = new SysLogBO();
        BeanUtils.copyProperties(entity,bo);
        bo.setTypeDesc(SysLogTypeEnum.getNameByIndex(entity.getType()));
        bo.setOperateDesc(SysLogOperateTypeEnum.getNameByIndex(entity.getOperateType()));
        return bo;
    }

    @Override
    public Collection<SysLogBO> entityListToBOList(Collection<SysLogEntity> entityList) {
        List<SysLogBO> boList = ListUtil.newLinkedList();
        if(ObjectTools.isNotEmpty(entityList)){
            for (SysLogEntity sysLogEntity : entityList) {
                boList.add(entityToBO(sysLogEntity));
            }
        }
        return boList;
    }
}
