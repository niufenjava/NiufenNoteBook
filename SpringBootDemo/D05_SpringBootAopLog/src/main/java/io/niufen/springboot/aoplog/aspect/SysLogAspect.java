package io.niufen.springboot.aoplog.aspect;

import com.alibaba.fastjson.JSONObject;
import io.niufen.springboot.common.annotation.SysLog;
import io.niufen.common.constant.SysConstants;
import io.niufen.common.tool.ObjectTools;
import io.niufen.common.util.IPUtils;
import io.niufen.common.util.JsonUtils;
import io.niufen.springboot.common.util.SpringContextUtils;
import io.niufen.springboot.module.sys.entity.SysLogEntity;
import io.niufen.springboot.module.sys.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author haijun.zhang
 * @date 2020/5/18
 * @time 11:34
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(io.niufen.springboot.common.annotation.SysLog)")
    private void sysLogPointCut(){}

    @Around("sysLogPointCut()")
    private Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time,result);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint point,long costTime,Object result){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        SysLog sysLogAnnotation = method.getAnnotation(SysLog.class);

        SysLogEntity sysLogEntity = new SysLogEntity();
        //请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");

        if(ObjectTools.isNotNull(sysLogAnnotation)){
            sysLogEntity.setContent(sysLogAnnotation.value());
            sysLogEntity.setOperateType(sysLogAnnotation.operateType());
            sysLogEntity.setType(sysLogAnnotation.type());
        }
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        if(ObjectTools.isNotNull(request)){
            sysLogEntity.setIp(IPUtils.getIpAddr(request));
            sysLogEntity.setRequestType(request.getMethod());
            sysLogEntity.setRequestUrl(request.getRequestURI());
            sysLogEntity.setRequestParam(getRequestParams(request,point));
        }
        HttpServletResponse response = SpringContextUtils.getHttpServletResponse();
        if(ObjectTools.isNotNull(response)){
            sysLogEntity.setResponseStatus(response.getStatus());
            sysLogEntity.setResponseContent(JsonUtils.toJSONString(result));
        }
        sysLogEntity.setCostTime(costTime);
        //todo 获取当前登录用户
        sysLogEntity.setUserId(SysConstants.SYSTEM_DEFAULT_USER_ID);
        sysLogEntity.setUserName(SysConstants.SYSTEM_DEFAULT_USER_NAME);
        sysLogEntity.setCreateTime(new Date());
        sysLogService.save(sysLogEntity);
    }


    /**
     * @Description: 获取请求参数
     * @author: scott
     * @date: 2020/4/16 0:10
     * @param request:  request
     * @param joinPoint:  joinPoint
     * @Return: java.lang.String
     */
    private String getRequestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String params = "";
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            params = JSONObject.toJSONString(paramsArray);
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params += "  " + paramNames[i] + ": " + args[i];
                }
            }
        }
        return params;
    }
}
