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
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				groupService.queryGroupList(userInfoId,queryGroupListParameters.getPageNo()));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
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
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		if(exportGroupsParameters.getGroup_id() == null) {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",
				groupService.exportGroups(userInfoId,exportGroupsParameters.getGroup_id()));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("deleteGroups")
	public ResponseEntity<ResponseSuccessResult> deleteGroups(HttpServletRequest request,
			@RequestBody DeleteGroupsParameters deleteGroupsParameters, BindingResult bindingResult) {
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		groupService.deleteGroups(userInfoId,deleteGroupsParameters.getGroup_id());
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("queryGroupDetail")
	public ResponseEntity<ResponseSuccessResult> queryGroupDetail(HttpServletRequest request,
			@RequestBody QueryGroupDetailParameters queryGroupDetailParameters, BindingResult bindingResult) {
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
				groupService.queryGroupDetail(userInfoId,queryGroupDetailParameters));
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("addIntoGroup")
	public ResponseEntity<ResponseSuccessResult> addIntoGroup(HttpServletRequest request,
			@RequestBody AddIntoGroupParameters addIntoGroupParameters, BindingResult bindingResult) {
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		Integer addMethod = addIntoGroupParameters.getAdd_method();
		if(addMethod != null) {
			if(addMethod==1) {//1:添加分组内的所有账号到分组,根据group_id
				if(StringUtils.isNotBlank(addIntoGroupParameters.getGroup_id())||StringUtils.isNotBlank(addIntoGroupParameters.getTarget_group_id())) {
					groupService.addIntoGroupByGroup_id(userInfoId, addIntoGroupParameters.getGroup_id(), addIntoGroupParameters.getTarget_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else if(addMethod==2) {//2:添加指定账号列表到分组,根据detection_account_id 
				if(addIntoGroupParameters.getDetection_account_id()!=null&&addIntoGroupParameters.getDetection_account_id().length>0&&StringUtils.isNotBlank(addIntoGroupParameters.getTarget_group_id())) {
					groupService.addIntoGroupByDetection_account_id(userInfoId, addIntoGroupParameters.getDetection_account_id(), addIntoGroupParameters.getTarget_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else if(addMethod==3) {//3:添加查询参数的结果内的所有账号到分组,根据查询参数 
				if(StringUtils.isNotBlank(addIntoGroupParameters.getTarget_group_id())&&StringUtils.isNotBlank(addIntoGroupParameters.getGroup_id())) {
					groupService.addIntoGroupByQuery_params(userInfoId, addIntoGroupParameters.getQuery_params(),addIntoGroupParameters.getGroup_id(), addIntoGroupParameters.getTarget_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else if(addMethod==4) {//4:添加账号查询记录内的所有账号到分组,根据bill_id
				if(StringUtils.isNotBlank(addIntoGroupParameters.getBill_id())&&StringUtils.isNotBlank(addIntoGroupParameters.getTarget_group_id())) {
					groupService.addIntoGroupByBill_id(userInfoId, addIntoGroupParameters.getQuery_params(),addIntoGroupParameters.getBill_id(), addIntoGroupParameters.getTarget_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else {
				throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
						ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
						ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
			}
		}else {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("deleteFromGroup")
	public ResponseEntity<ResponseSuccessResult> deleteFromGroup(HttpServletRequest request,
			@RequestBody DeleteFromGroupParameters deleteFromGroupParameters, BindingResult bindingResult) {
		String userInfoId = (String) request.getSession().getAttribute("user_info_id");
		if (StringUtils.isBlank(userInfoId)) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		Integer deleteMethod = deleteFromGroupParameters.getDelete_method();
		if(deleteMethod != null) {
			if(deleteMethod==1) {//1:删除分组内的所有账号到分组,根据group_id
				if(StringUtils.isNotBlank(deleteFromGroupParameters.getDelete_from_group_id())) {
					groupService.deleteFromGroup(userInfoId, deleteFromGroupParameters.getDelete_from_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else if(deleteMethod==2) {//2:添加指定账号列表到分组,根据detection_account_id 
				if(deleteFromGroupParameters.getDetection_account_id()!=null&&deleteFromGroupParameters.getDetection_account_id().length>0&&StringUtils.isNotBlank(deleteFromGroupParameters.getDelete_from_group_id())) {
					groupService.deleteFromGroup(userInfoId,deleteFromGroupParameters.getDetection_account_id(), deleteFromGroupParameters.getDelete_from_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else if(deleteMethod==3) {//3:添加查询参数的结果内的所有账号到分组,根据查询参数 
				if(StringUtils.isNotBlank(deleteFromGroupParameters.getDelete_from_group_id())) {
					groupService.deleteFromGroup(userInfoId,deleteFromGroupParameters.getQuery_params(), deleteFromGroupParameters.getDelete_from_group_id());
				}else {
					throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
							ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
				}
			}else {
				throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
						ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
						ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
			}
		}else {
			throw new ConanException(ConanExceptionConstants.PARAMETER_EXCEPTION_CODE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_MESSAGE,
					ConanExceptionConstants.PARAMETER_EXCEPTION_HTTP_STATUS);
		}
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
