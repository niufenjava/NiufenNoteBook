package io.niufen.common.core.exception;

/**
 * 自定义异常
 * @author niufen
 */
public class NiufenException extends RuntimeException {

	/** 序列化ID */
	private static final long serialVersionUID = 4162103625207009164L;

	/**
	 * 异常构成方法，自定义异常消息
	 * @param message 自定义异常消息
	 */
	public NiufenException(String message){
		super(message);
	}


	/**
	 * 异常构成方法，自定义异常
	 * @param cause 自定义异常
	 */
	public NiufenException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * 异常构成方法，自定义异常、消息
	 * @param message 自定义异常消息
	 * @param cause 自定义异常
	 */
	public NiufenException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
