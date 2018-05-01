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
 * @author Paul token 拦截器，当请求中未带token时，拦截请求，并验证token是否过期
 */
public class TokenInterceptor implements HandlerInterceptor {
	private final Logger _logger = LoggerFactory.getLogger(this.getClass()) ;
	
	@Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        _logger.info("this is MyInterceptor1 postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        _logger.info("this is MyInterceptor1 afterCompletion");
    }
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ip = getIpAddr(request);
		System.out.println("--------------------IP"+ip);
		//获取session里的登录状态值
        String str = (String) request.getSession().getAttribute("isLogin");
        //如果登录状态不为空则返回true，返回true则会执行相应controller的方法
        if(str!=null){
            return true;
        }else {
        	throw new ConanException(ConanExceptionConstants.USER_NOT_LOGIN_EXCEPTION_CODE,
    				ConanExceptionConstants.USER_NOT_LOGIN_EXCEPTION_MESSAGE,
    				ConanExceptionConstants.USER_NOT_LOGIN_EXCEPTION_HTTP_STATUS);
        }
	}
	
	/**
     * 获取访问的ip地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
