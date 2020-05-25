package io.niufen.springboot.module.sys.bo;

import io.niufen.springboot.module.sys.entity.SysLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author haijun.zhang
 * @date 2020/5/16
 * @time 21:12
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SysLogBO extends SysLogEntity implements Serializable {

    private static final long serialVersionUID = -7145440462977050375L;

    private String typeDesc;

    private String operateDesc;

}
