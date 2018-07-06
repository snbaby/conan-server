package com.conan.console.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;

public class ConanException extends RuntimeException {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	private String code;
	private String description;
	private Object content;
	private HttpStatus httpStatus;

	public ConanException(String code, String description, Object content, HttpStatus httpStatus) {
		_logger.info(">>>>>ConanException");
		_logger.info(">>>>>code:"+code);
		_logger.info(">>>>>description:"+description);
		_logger.info(">>>>>content:"+JSON.toJSONString(content));
		_logger.info(">>>>>httpStatus:"+httpStatus.toString());
		
		
		this.code = code;
		this.description = description;
		this.content = content;
		this.httpStatus = httpStatus;
	}

	public ConanException(String code, String description, HttpStatus httpStatus) {
		_logger.info(">>>>>ConanException");
		_logger.info(">>>>>code:"+code);
		_logger.info(">>>>>description:"+description);
		_logger.info(">>>>>httpStatus:"+httpStatus.toString());
		
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
