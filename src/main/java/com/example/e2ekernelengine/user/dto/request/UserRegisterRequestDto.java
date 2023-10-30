package com.example.e2ekernelengine.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {
	@NotBlank
	private String userName;
	@NotBlank
	@Email
	private String userEmail;
	@NotBlank
	private String userPassword;
	
}
