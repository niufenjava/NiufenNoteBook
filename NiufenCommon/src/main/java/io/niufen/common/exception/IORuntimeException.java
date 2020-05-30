package io.niufen.common.exception;

/**
 * IO 运行时异常，常用于对IOException 的包装
 *
 * @author haijun.zhang
 * @date 2020/5/29
 * @time 16:36
 */
public class IORuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8786255344351997062L;


    public IORuntimeException(Throwable e) {
//        super(ExceptionUtil.getMessage(e), e);
        super(e.getMessage(), e);
    }

    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(String messageTemplate, Object... params) {
//        super(StrUtil.format(messageTemplate, params));
        super(messageTemplate);
    }

    public IORuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IORuntimeException(Throwable throwable, String messageTemplate, Object... params) {
        super(messageTemplate);
//        super(StrUtil.format(messageTemplate, params), throwable);
    }

    /**
     * 导致这个异常的异常是否是指定类型的异常
     *
     * @param clazz 异常类
     * @return 是否为指定类型异常
     */
    public boolean causeInstanceOf(Class<? extends Throwable> clazz) {
        final Throwable cause = this.getCause();
        return null != clazz && clazz.isInstance(cause);
    }
}
