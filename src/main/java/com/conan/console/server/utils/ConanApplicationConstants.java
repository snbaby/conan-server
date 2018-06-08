package com.conan.console.server.utils;

public class ConanApplicationConstants {
	//金额信息 初始化为0
	public static final Double INIT_GOLD_AMOUNT = 0d;
	//金币信息 初始化为0
	public static final Double INIT_GOLD_COUPON = 0d;
	//金币信息 初始化为0
	public static final int INIT_PAGE_SIZE = 20;
	//账号余额不足 提示
	public static final String NO_BALANCE_MESSAGE = "账号余额不足";
	//账号余额不足 Code
	public static final double NO_BALANCE_CODE = -999f;
	//危险
	public static final String DANGER = "危险";
	//可疑
	public static final String SUSPICIOUS = "可疑";
	//未匹配
	public static final String NOT_MATCH_MESSAGE = "暂无危险信息";
	//未匹配
	public static final double NOT_MATCH_CODE = 0d;
	//不存在
	public static final String NOT_EXIST_MESSAGE = "账号不存在";
	//不存在
	public static final double NOT_EXIST_CODE = -2d;
	//RMB=>金币汇率
	public static final Double RMB_TO_COUPON_RATE = ConanUtils.fix2(1f/0.8f);
	
	public static void main(String[] args) {
		double a = 111111.75f;
		double b = 111111111.75f;
		System.out.println(a);
		System.out.println(b);
	}
	
}
