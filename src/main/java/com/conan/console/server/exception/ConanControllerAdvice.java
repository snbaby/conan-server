package com.conan.console.server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.conan.console.server.response.ResponseFaildResult;
import com.conan.console.server.utils.ConanExceptionConstants;

@ControllerAdvice
public class ConanControllerAdvice {
	/**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseFaildResult> errorHandler(Exception ex) {
    	ex.printStackTrace();
    	ResponseFaildResult responseFaildResult = new ResponseFaildResult();
    	responseFaildResult.setCode(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE);
    	responseFaildResult.setDescription(ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE);
    	responseFaildResult.setContent(ex.getMessage());
		return new ResponseEntity<>(responseFaildResult,ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
    }
    
    /**
     * 自定义异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConanException.class)
    public ResponseEntity<ResponseFaildResult> errorHandler(ConanException ex) {
    	ResponseFaildResult responseFaildResult = new ResponseFaildResult();
    	responseFaildResult.setCode(ex.getCode());
    	responseFaildResult.setDescription(ex.getDescription());
		responseFaildResult.setContent(ex.getContent());	
		return new ResponseEntity<>(responseFaildResult,ex.getHttpStatus());
    }
    
}
