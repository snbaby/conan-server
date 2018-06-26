package com.conan.console.server.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.parameter.AddIntoGroupParameters;
import com.conan.console.server.parameter.CreateGroupParameters;
import com.conan.console.server.parameter.DeleteFromGroupParameters;
import com.conan.console.server.parameter.DeleteGroupsParameters;
import com.conan.console.server.parameter.ExportGroupsParameters;
import com.conan.console.server.parameter.ModifyGroupParameters;
import com.conan.console.server.parameter.QueryGroupDetailParameters;
import com.conan.console.server.parameter.QueryGroupListParameters;
import com.conan.console.server.response.ResponseSuccessResult;
import com.conan.console.server.service.GroupService;
import com.conan.console.server.utils.ConanExceptionConstants;

@RestController
@RequestMapping(value = "api/v2.0")
public class GroupController {
	
	@Autowired
	private GroupService groupService; 
	

	@PostMapping("queryGroupList")
	public ResponseEntity<ResponseSuccessResult> queryGroupList(HttpServletRequest request,
			@RequestBody @Valid QueryGroupListParameters queryGroupListParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		return null;
	}

	@PostMapping("createGroup")
	public ResponseEntity<ResponseSuccessResult> createGroup(HttpServletRequest request,
			@RequestBody @Valid CreateGroupParameters createGroupParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				groupService.createGroup(userInfoId,createGroupParameters.getGroup_name()));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("modifyGroup")
	public ResponseEntity<ResponseSuccessResult> modifyGroup(HttpServletRequest request,
			@RequestBody @Valid ModifyGroupParameters modifyGroupParameters, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE, bindingResult.getFieldError(),
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				groupService.modifyGroup(userInfoId,modifyGroupParameters.getGroup_id(),modifyGroupParameters.getGroup_name(),modifyGroupParameters.getGroup_comment()));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("exportGroups")
	public ResponseEntity<ResponseSuccessResult> exportGroups(HttpServletRequest request,
			@RequestBody ExportGroupsParameters exportGroupsParameters, BindingResult bindingResult) {
		return null;
	}
	
	@PostMapping("deleteGroups")
	public ResponseEntity<ResponseSuccessResult> deleteGroups(HttpServletRequest request,
			@RequestBody DeleteGroupsParameters deleteGroupsParameters, BindingResult bindingResult) {
		return null;
	}
	
	@PostMapping("queryGroupDetail")
	public ResponseEntity<ResponseSuccessResult> queryGroupDetail(HttpServletRequest request,
			@RequestBody QueryGroupDetailParameters queryGroupDetailParameters, BindingResult bindingResult) {
		return null;
	}
	
	@PostMapping("addIntoGroup")
	public ResponseEntity<ResponseSuccessResult> addIntoGroup(HttpServletRequest request,
			@RequestBody AddIntoGroupParameters addIntoGroupParameters, BindingResult bindingResult) {
		return null;
	}
	
	@PostMapping("deleteFromGroup")
	public ResponseEntity<ResponseSuccessResult> deleteFromGroup(HttpServletRequest request,
			@RequestBody DeleteFromGroupParameters deleteFromGroupParameters, BindingResult bindingResult) {
		return null;
	}
}
