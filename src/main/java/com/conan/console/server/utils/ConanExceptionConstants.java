package com.conan.console.server.utils;

import org.springframework.http.HttpStatus;

public class ConanExceptionConstants {
	// 系统内部错误
	public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "系统内部异常";
	public static final HttpStatus INTERNAL_SERVER_ERROR_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
	// 参数不合法
	public static final String PARAMETER_EXCEPTION_CODE = "PARAMETER_EXCEPTION";
	public static final String PARAMETER_EXCEPTION_MESSAGE = "参数校验失败";
	public static final HttpStatus PARAMETER_EXCEPTION_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	// 用户重复注册使用
	public static final String USER_EXISTS_EXCEPTION_CODE = "USER_EXISTS_EXCEPTION";
	public static final String USER_EXISTS_EXCEPTION_MESSAGE = "用户已存在";
	public static final HttpStatus USER_EXISTS_EXCEPTION_HTTP_STATUS = HttpStatus.FORBIDDEN;
	// 用户权限问题
	public static final String USER_LOGIN_EXCEPTION_CODE = "USER_LOGIN_EXCEPTION";
	public static final String USER_LOGIN_EXCEPTION_MESSAGE = "账号或密码错误";
	public static final HttpStatus USER_LOGIN_EXCEPTION_HTTP_STATUS = HttpStatus.FORBIDDEN;
	// 用户权限问题
	public static final String USER_UNAUTHORIZED_EXCEPTION_CODE = "USER_UNAUTHORIZED_EXCEPTION";
	public static final String USER_UNAUTHORIZED_EXCEPTION_MESSAGE = "用户权限错误";
	public static final HttpStatus USER_UNAUTHORIZED_EXCEPTION_HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	// 獲取用户信息用戶不存在
	public static final String USER_NOT_EXISTS_EXCEPTION_CODE = "USER_NOT_EXISTS_EXCEPTION";
	public static final String USER_NOT_EXISTS_EXCEPTION_MESSAGE = "用户不存在";
	public static final HttpStatus USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS = HttpStatus.NOT_FOUND;
	// 用户或密码错误
	public static final String USER_PASSWD_VALIDATED_EXCEPTION_CODE = "USER_PASSWD_VALIDATED_EXCEPTION";
	public static final String USER_PASSWD_VALIDATED_EXCEPTION_MESSAGE = "用户密码校验失败";
	public static final HttpStatus USER_PASSWD_VALIDATED_EXCEPTION_HTTP_STATUS = HttpStatus.FORBIDDEN;
	// 用户或密码错误
	public static final String BILL_NOT_EXISTS_EXCEPTION_CODE = "BILL_NOT_EXISTS_EXCEPTION";
	public static final String BILL_NOT_EXISTS_EXCEPTION_MESSAGE = "该用户账单不存在";
	public static final HttpStatus BILL_NOT_EXISTS_EXCEPTION_HTTP_STATUS = HttpStatus.NOT_FOUND;
	
}
