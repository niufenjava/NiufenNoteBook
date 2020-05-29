package io.niufen.common.exception;

/**
 * TODO
 * 工具类异常
 * @author niufen
 */
public class UtilException extends RuntimeException {

	/** 序列化ID */
	private static final long serialVersionUID = 4162103625207009164L;

	/**
	 * 异常构成方法，自定义异常消息
	 * @param message 自定义异常消息
	 */
	public UtilException(String message){
		super(message);
	}


	/**
	 * 异常构成方法，自定义异常
	 * @param cause 自定义异常
	 */
	public UtilException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * 异常构成方法，自定义异常、消息
	 * @param message 自定义异常消息
	 * @param cause 自定义异常
	 */
	public UtilException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
