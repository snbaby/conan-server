package com.conan.console.server.utils;

public class ConanApplicationConstants {
	//金额信息 初始化为0
	public static final Float INIT_GOLD_AMOUNT = 0f;
	//金币信息 初始化为0
	public static final Float INIT_GOLD_COUPON = 0f;
	//金币信息 初始化为0
	public static final int INIT_PAGE_SIZE = 20;
	//账号余额不足 提示
	public static final String NO_BALANCE_MESSAGE = "账号余额不足";
	//账号余额不足 Code
	public static final float NO_BALANCE_CODE = -999f;
	//危险
	public static final String DANGER = "危险";
	//可疑
	public static final String SUSPICIOUS = "可疑";
	//未匹配
	public static final String NOT_MATCH_MESSAGE = "未匹配";
	//未匹配
	public static final float NOT_MATCH_CODE = -1f;
	//不存在
	public static final String NOT_EXIST_MESSAGE = "暂无危险信息";
	//不存在
	public static final float NOT_EXIST_CODE = -2f;
	//RMB=>金币汇率
	public static final Float RMB_TO_COUPON_RATE = ConanUtils.fix2(1f/0.8f);
	
}
