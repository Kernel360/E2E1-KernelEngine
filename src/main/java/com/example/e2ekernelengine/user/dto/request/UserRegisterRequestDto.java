package com.example.e2ekernelengine.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDto {
	@NotBlank(message = "이름이 입력되지 않았습니다.")
	private String userName;
	@NotBlank(message = "이메일 값이 입력되지 않았습니다.")
	@Email(message = "이메일 형식이 맞지 않습니다.")
	private String userEmail;
	@NotBlank(message = "비밀번호가 입력되지 않았습니다.")
	private String userPassword;

}
