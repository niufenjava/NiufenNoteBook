package io.niufen.common.exception;

import io.niufen.common.util.ExceptionUtils;
import io.niufen.common.util.StringUtils;

/**
 * TODO
 * 工具类异常
 *
 * @author niufen
 */
public class UtilException extends RuntimeException {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 4162103625207009164L;

    public UtilException(Throwable e) {
        super(ExceptionUtils.getMessage(e), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UtilException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), throwable);
    }
}
