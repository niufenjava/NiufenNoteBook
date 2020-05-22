package io.niufen.springboot.common.module.sys.entity;

import io.niufen.common.constant.SysConstants;
import io.niufen.common.response.R;
import io.niufen.common.util.FakerUtils;
import io.niufen.springboot.common.module.sys.enums.SysLogOperateTypeEnum;
import io.niufen.springboot.common.module.sys.enums.SysLogTypeEnum;
import io.niufen.springboot.common.module.sys.form.SysUserCreateForm;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 11:13
 */
@Data
public class SysLogEntity implements Serializable {

    private static final long serialVersionUID = -339516038496531943L;

    private Long id;

    private Integer type;

    private String content;

    private Integer operateType;

    private String ip;

    private String method;

    private String requestType;

    private String requestUrl;

    private String requestParam;

    private Integer responseStatus;

    private String responseContent;

    private Long costTime;

    private Long userId;

    private String userName;

    private Date createTime;

    public static SysLogEntity testNewEntity(){
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setType(SysLogTypeEnum.randomIndex());
        sysLogEntity.setContent(FakerUtils.sysLogContent());
        sysLogEntity.setOperateType(SysLogOperateTypeEnum.randomIndex());
        sysLogEntity.setIp(FakerUtils.ip());
        sysLogEntity.setMethod(FakerUtils.javaMethodName());
        sysLogEntity.setRequestType(FakerUtils.httpRequestType());
        sysLogEntity.setRequestUrl(FakerUtils.uri());
        sysLogEntity.setRequestParam(SysUserCreateForm.testNewEntity().toString());
        sysLogEntity.setResponseStatus(200);
        sysLogEntity.setResponseContent(R.ok().toString());
        sysLogEntity.setCostTime(FakerUtils.costTime());
        sysLogEntity.setUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysLogEntity.setUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysLogEntity.setCreateTime(new Date());
        return sysLogEntity;
    }
}
