package com.example.bookreviewclient.dto;

import com.example.bookreviewclient.model.Member;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupDto {

    @Email
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotBlank(message = "비밀번호를 한번 더 입력해주세요.")
    private String password2;
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;


    public Member toEntity() {
        return Member.builder()
                .memberEmail(email)
                .memberPassword(password)
                .memberNickname(nickname)
                .role("ROLE_USER")
                .build();
    }
}
