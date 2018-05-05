package com.conan.console.server.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.conan.console.server.interceptor.CorsInterceptor;
import com.conan.console.server.interceptor.TokenInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
		List<String> patterns = new ArrayList<>();
		patterns.add("/api/v2.0/user_register");
		patterns.add("/api/v2.0/get_validation_code");
		patterns.add("/api/v2.0/user_login");
		patterns.add("/api/v2.0/check_validation_code");
		patterns.add("/api/v2.0/user_reset_passwd");
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
		super.addInterceptors(registry);
	}
}
