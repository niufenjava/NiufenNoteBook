package io.niufen.springboot.config.exception;

import io.niufen.common.core.exception.NiufenException;
import io.niufen.springboot.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 异常处理器
 * @author niufen
 */
@RestControllerAdvice
@Slf4j
public class NiufenExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(NiufenException.class)
	public R handleRRException(NiufenException e){
		log.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}

//	@ExceptionHandler(NoHandlerFoundException.class)
//	public Result<?> handlerNoFoundException(Exception e) {
//		log.error(e.getMessage(), e);
//		return Result.error(404, "路径不存在，请检查路径是否正确");
//	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

//	@ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
//	public Result<?> handleAuthorizationException(AuthorizationException e){
//		log.error(e.getMessage(), e);
//		return Result.noauth("没有权限，请联系管理员授权");
//	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		log.error(e.getMessage(), e);
		return R.error("操作失败，"+e.getMessage());
	}

	/**
	 * @Author 政辉
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		StringBuffer sb = new StringBuffer();
		sb.append("不支持");
		sb.append(e.getMethod());
		sb.append("请求方法，");
		sb.append("支持以下");
		String [] methods = e.getSupportedMethods();
		if(methods!=null){
			for(String str:methods){
				sb.append(str);
				sb.append("、");
			}
		}
		log.error(sb.toString(), e);
		//return Result.error("没有权限，请联系管理员授权");
		return R.error(405,sb.toString());
	}

	 /**
	  * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
	  */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	log.error(e.getMessage(), e);
        return R.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    	log.error(e.getMessage(), e);
        return R.error("字段太长,超出数据库字段的长度");
    }

//    @ExceptionHandler(PoolException.class)
//    public Result<?> handlePoolException(PoolException e) {
//    	log.error(e.getMessage(), e);
//        return Result.error("Redis 连接异常!");
//    }

}
