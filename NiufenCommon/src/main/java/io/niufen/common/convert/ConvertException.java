package io.niufen.common.convert;

import io.niufen.common.util.ExceptionUtils;
import io.niufen.common.util.StringUtils;

/**
 * 转换异常
 * @author xiaoleilu
 */
public class ConvertException extends RuntimeException{
    private static final long serialVersionUID = 4730597402855274362L;

    public ConvertException(Throwable e) {
        super(ExceptionUtils.getMessage(e), e);
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConvertException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), throwable);
    }
}
