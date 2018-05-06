package com.conan.console.server.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.MinioService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class FileController {
	@Autowired
	private MinioService minioService;
	
	@Value("${conan.server.batch-template-file}")
	private String batchTemplateFile;
	
	@PostMapping("upload")
	public ResponseEntity<ResponseSuccessResult> upload(HttpServletRequest request,@RequestParam String type,@RequestParam(required = true) MultipartFile upload_file) throws IOException {
		String userInfoId  = (String) request.getSession().getAttribute("user_info_id");
		if(StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		String objectName = userInfoId+"/"+ymd+"/"+upload_file.getOriginalFilename();
		minioService.uploadFile(upload_file.getInputStream(), objectName, type);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("file_key", objectName);
		jsonObject.put("file_type", type);
		jsonObject.put("file_url", minioService.presignedGetObject(objectName));
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				jsonObject);
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	@GetMapping("get_batch_query_template")
	public ResponseEntity<ResponseSuccessResult> getBatchQueryTemplate(HttpServletRequest request){
		String userInfoId  = (String) request.getSession().getAttribute("user_info_id");
		if(StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("export_link", minioService.presignedGetObject(batchTemplateFile));
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				jsonObject);
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
}
