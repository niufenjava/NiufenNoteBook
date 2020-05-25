package io.niufen.springboot.module.sys.mapper;

import io.niufen.springboot.common.base.mapper.BaseMapper;
import io.niufen.springboot.module.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 13:22
 */
@Component
@Mapper
public interface SysLogMapper extends BaseMapper <SysLogEntity> {
}
