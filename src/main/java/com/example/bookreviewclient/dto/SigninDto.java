package com.example.bookreviewclient.dto;

import com.example.bookreviewclient.model.Member;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SigninDto {

    @Email(message = "이메일은 형식으로 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .memberEmail(email)
                .memberPassword(password)
                .build();
    }
}
