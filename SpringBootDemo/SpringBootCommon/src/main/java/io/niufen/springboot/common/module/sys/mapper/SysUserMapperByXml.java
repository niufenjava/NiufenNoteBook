package io.niufen.springboot.common.module.sys.mapper;

import io.niufen.common.base.mapper.BaseMapperByXml;
import io.niufen.springboot.common.module.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 13:22
 */
@Component
@Mapper
public interface SysUserMapperByXml extends BaseMapperByXml<SysUserEntity> {

}
