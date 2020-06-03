package io.niufen.common.core.exception;

import io.niufen.common.core.util.ExceptionUtil;
import io.niufen.common.core.util.StrUtil;

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
        super(ExceptionUtil.getMessage(e), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UtilException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }
}
