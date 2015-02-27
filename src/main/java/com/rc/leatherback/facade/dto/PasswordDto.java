package com.rc.leatherback.facade.dto;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordDto {

	// @NotNull(message = "{activation.code.code.required}")
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
