package io.niufen.common.response;

import io.niufen.common.constant.IntConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_CODE = "code";

	public static final String KEY_MSG = "msg";

	public static final String KEY_DATA = "data";

	public static final String MSG_SUCCESS = "success";

	public static final String MSG_BUSINESS_COMMON = "错误";

	public static final Integer CODE_SUCCESS = IntConstants.ZERO;

	public static final Integer CODE_SYSTEM_ERROR = IntConstants.FIVE_HUNDRED;

	public static final String MSG_SYSTEM_ERROR = "未知异常，请联系管理员";

	public static final Integer CODE_BUSINESS_ERROR = IntConstants.ONE_MINUS;

	/**
	 * 状态码 401 Unauthorized 代表客户端错误，指的是由于缺乏目标资源要求的身份验证凭证，发送的请求未得到满足。
	 */
	public static final Integer CODE_UNAUTHORIZED_ERROR = 401;

	public static final String MSG_UNAUTHORIZED_ERROR = "您没有访问权限!";

	public R() {
		put(KEY_CODE, CODE_SUCCESS);
		put(KEY_MSG, MSG_SUCCESS);
	}

	public static R error() {
		return error(CODE_BUSINESS_ERROR, MSG_BUSINESS_COMMON);
	}

	public static R error(String msg) {
		return error(CODE_BUSINESS_ERROR, msg);
	}

	public static R businessError(String msg) {
		return error(CODE_BUSINESS_ERROR, msg);
	}

	public static R systemError() {
		return error(CODE_SYSTEM_ERROR, MSG_SYSTEM_ERROR);
	}

	public static R unauthorizedError() {
		return error(CODE_UNAUTHORIZED_ERROR, MSG_UNAUTHORIZED_ERROR);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put(KEY_CODE, code);
		r.put(KEY_MSG, msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put(KEY_MSG, msg);
		return r;
	}

	public static R ok(Object result) {
		return R.ok().put(result);
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public R put(Object value) {
		super.put(KEY_DATA, value);
		return this;
	}

	public Integer getCode(){
		return (Integer)this.get(KEY_CODE);
	}

}
