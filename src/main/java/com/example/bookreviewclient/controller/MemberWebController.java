package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberWebController {

    //회원가입 폼으로 이동
    @GetMapping("/member/register")
    public String goSignup(){
        return "member/signupForm";
    }

    //로그인 폼으로 이동
    @GetMapping("/member/login")
    public String goSignin(){
        return "member/signinForm";
    }

    //마이페이지로 이동
    @GetMapping("/member/mypage")
    public String goMypage(Model model, Authentication auth){
        Member loginuser = (Member) auth.getPrincipal();
        model.addAttribute("loginUser", loginuser);

        return "member/mypage";
    }

    //비밀번호 수정 폼으로 이동
    @GetMapping("/member/mypage/password")
    public String goChangePassword(){
        return "member/passwordForm";
    }

}
