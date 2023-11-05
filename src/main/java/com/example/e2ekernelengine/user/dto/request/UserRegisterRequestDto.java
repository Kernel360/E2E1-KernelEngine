package com.example.e2ekernelengine.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.example.e2ekernelengine.user.db.entity.User;

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

	public User toEntity(String encryptedPassword) {
		return User.builder()
				.userName(this.userName)
				.userEmail(this.userEmail)
				.userPassword(encryptedPassword)
				.build();
	}

}
