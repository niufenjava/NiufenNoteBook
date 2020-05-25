package io.niufen.springboot.druid.multi.mybatis.mapper;

import io.niufen.springboot.common.base.mapper.BaseMapperByXml;
import io.niufen.springboot.druid.multi.mybatis.entity.SysUserEntity;
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
