package com.conan.console.server.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModifyPhotoParameters {
	
	@NotNull(message = "用户头像地址不能为空")
	@NotBlank(message = "用户头像地址不能为空")
	@NotEmpty(message = "用户头像地址不能为空")
	@Size (max=255, message="用户头像地址最大长度为255")
	private String photo_url;

	
	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

}
