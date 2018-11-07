package com.chat.common.core.constants;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * 响应码枚举类
 * @author hzk
 */
public enum ResultCodeEnum {

	/**
	 * 成功
	 */
	SUCCESS(1000,"success"),

	/**
	 * 找不到命令
	 */
	NO_INVOKER(2000,"no_invoker"),

	/**
	 * 参数异常
	 */
	PARAM_ERROR(2001,"param_error"),

	/**
	 * 未知异常
	 */
	UNKNOWN_EXCEPTION(2002,"unknown_exception"),

	/**
	 * 用户名或密码不能为空
	 */
	PLAYER_NAME_NULL(2003,"player_name_null"),

	/**
	 * 用户名已使用
	 */
	PLAYER_EXIST(2004,"player_exist"),

	/**
	 * 用户不存在
	 */
	PLAYER_NO_EXIST(2005,"player_no_exist"),

	/**
	 * 密码错误
	 */
	PASSWORD_ERROR(2006,"password_error"),

	/**
	 * 已经登录
	 */
	ALREADY_LOGIN(2007,"already_login"),

	/**
	 * 登录失败
	 */
	LOGIN_FAIL(2008,"login_fail"),

	/**
	 * 用户不在线
	 */
	PLAYER_NO_ONLINE(2009,"player_not_online"),

	/**
	 * 未登录
	 */
	NOT_LOGIN(2010,"not_login"),

	/**
	 * 不能以自己为私聊对象
	 */
	CANNOT_CHAT_YOURSELF(2011,"can't chat with yourself"),

	/**
	 * 参数检验失败
	 */
	PARAMETER_AUTHORIZATION_FAILED(4001,"parameter_authorization_failed"),
	/**
	 * 运行时异常
	 */
	RuntimeException(4002,"runtime_exception"),
	/**
	 * 空指针异常
	 */
	NullPointerException(4003,"null_pointer_exception"),
	/**
	 * 类型转换异常
	 */
	ClassCastException(4004,"class_cast_exception"),
	/**
	 * IO异常
	 */
	IOException(4005,"io_exception"),
	/**
	 * 未知方法异常
	 */
	NoSuchMethodException(4006,"no_such_method_exception"),
	/**
	 * 数组越界异常
	 */
	IndexOutOfBoundsException(4007,"index_out_of_bounds_exception"),
	/**
	 * 400错误
	 */
	HttpMessageNotReadableException(4008,"http_message_not_readable_exception"),
	/**
	 * 400错误
	 */
	MissingServletRequestParameterException(4009,"miss_servlet_request_parameter_exception"),
	/**
	 * 405错误
	 */
	HttpRequestMethodNotSupportedException(4010,"http_request_method_not_supported_exception"),
	/**
	 * 406错误
	 */
	HttpMediaTypeNotAcceptableException(4011,"http_media_type_not_acceptable_exception"),
	/**
	 * 500错误
	 */
	ConversionNotSupportedException(4012,"conversion_not_supported_exception"),
	/**
	 * 响应码未定义
	 */
	NO_CODE(4013,"no_code"),
    ;

	private static Logger log = Logger.getLogger(ResultCodeEnum.class);
	private int resultCode;
	private String resultInfo;
	private static Map< Integer, ResultCodeEnum> resultCodeMap = new HashMap<Integer, ResultCodeEnum>();
	private static Semaphore semaphore = new Semaphore(1);

	public int getResultCode() {
		return resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	private ResultCodeEnum(int resultCode, String resultInfo){
		this.resultCode = resultCode;
		this.resultInfo = resultInfo;
	}
	
	public static String getResultInfo(int resultCode){
		if(resultCodeMap.isEmpty()){
			try {
				semaphore.acquire();
				initCodeInfoMap();
			} catch (InterruptedException e) {
				log.error("getResultInfo,err:"+e.getMessage());
			}finally{
				semaphore.release();
			}
		}
		ResultCodeEnum resultCodeEnum = resultCodeMap.get(new Integer(resultCode));
		return resultCodeEnum!=null?resultCodeEnum.getResultInfo():NO_CODE.getResultInfo();
	}
	
	private static void initCodeInfoMap(){
		if(resultCodeMap.isEmpty()){
			for(ResultCodeEnum resultCodeEnum:ResultCodeEnum.values()){
				resultCodeMap.put(resultCodeEnum.getResultCode(), resultCodeEnum);
			}
		}
	}
	
	public static void main(String args[]){
		System.out.println(ResultCodeEnum.PLAYER_EXIST.getResultCode());
		System.out.println(ResultCodeEnum.PLAYER_EXIST.getResultInfo());
		for(ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()){
			System.out.println(resultCodeEnum.getResultCode()+" "+resultCodeEnum.getResultInfo());
		}
		
	}
	
}
