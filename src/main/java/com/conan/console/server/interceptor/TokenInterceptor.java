package com.conan.console.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.utils.ConanExceptionConstants;

/**
 * 
 * @author Paul session 拦截器，当请求中未带token时，拦截请求，并验证token是否过期
 */
public class TokenInterceptor implements HandlerInterceptor {
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
		_logger.info("this is TokenInterceptor postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
		_logger.info("this is TokenInterceptor afterCompletion");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getMethod().equals("OPTIONS")) {//解决跨域OPTIONS 问题
			return true;
		}else {//session简单检测
			// 获取session里的登录状态值
			String userStr = (String) request.getSession().getAttribute("isLogin");
			String adminStr = (String) request.getSession().getAttribute("isAdminLogin");
			// 如果登录状态不为空则返回true，返回true则会执行相应controller的方法
			if (userStr != null || adminStr != null) {
				return true;
			} else {
				_logger.info(">>>>>用户未登陆");
				throw new ConanException(ConanExceptionConstants.USER_NOT_LOGIN_EXCEPTION_CODE,
						ConanExceptionConstants.USER_NOT_LOGIN_EXCEPTION_MESSAGE,
						ConanExceptionConstants.USER_NOT_LOGIN_EXCEPTION_HTTP_STATUS);
			}
		}
		
	}
}
