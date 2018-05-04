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
	// 账单查询
	public static final String BILL_NOT_EXISTS_EXCEPTION_CODE = "BILL_NOT_EXISTS_EXCEPTION";
	public static final String BILL_NOT_EXISTS_EXCEPTION_MESSAGE = "该用户账单不存在";
	public static final HttpStatus BILL_NOT_EXISTS_EXCEPTION_HTTP_STATUS = HttpStatus.NOT_FOUND;
	// 账单查询预扣费错误
	public static final String SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_CODE = "SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION";
	public static final String SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_MESSAGE = "scan_account未传入错误";
	public static final HttpStatus SCAN_ACCOUNT_NOT_EXISTS_EXCEPTION_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	// 账单查询预扣费错误
	public static final String SCAN_FILE_NOT_EXISTS_EXCEPTION_CODE = "SCAN_FILE_NOT_EXISTS_EXCEPTION";
	public static final String SCAN_FILE_EXISTS_EXCEPTION_MESSAGE = "scan_file未传入错误";
	public static final HttpStatus SCAN_FILE_NOT_EXISTS_EXCEPTION_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	// 账单查询预扣费错误
	public static final String SCAN_FILE_EXCEPTION_CODE = "SCAN_FILE_EXCEPTION";
	public static final String SCAN_FILE_EXCEPTION_MESSAGE = "scan_file文件解析异常";
	public static final HttpStatus SCAN_FILE_EXCEPTION_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	//验证码错误错误
	public static final String VALIDATION_CODE_NOT_MATCHED_EXCEPTION_CODE = "VALIDATION_CODE_NOT_MATCHED_EXCEPTION";
	public static final String VALIDATION_CODE_NOT_MATCHED_EXCEPTION_MESSAGE = "验证码不匹配";
	public static final HttpStatus VALIDATION_CODE_NOT_MATCHED_EXCEPTION_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	//手机号码错误错误
	public static final String USER_PHONE_NOT_MATCHED_EXCEPTION_CODE = "USER_PHONE_NOT_MATCHED_EXCEPTION";
	public static final String USER_PHONE_NOT_MATCHED_EXCEPTION_MESSAGE = "手机号码不匹配";
	public static final HttpStatus USER_PHONE_NOT_MATCHED_EXCEPTION_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	//验证码错误错误
	public static final String USER_NOT_LOGIN_EXCEPTION_CODE = "USER_NOT_LOGIN_EXCEPTION";
	public static final String USER_NOT_LOGIN_EXCEPTION_MESSAGE = "用户未登录";
	public static final HttpStatus USER_NOT_LOGIN_EXCEPTION_HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	
}
