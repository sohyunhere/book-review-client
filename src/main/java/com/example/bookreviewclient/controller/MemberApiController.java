package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MemberApiController {
    @Autowired
    private WebClient webClient;

    //회원가입
//    @PostMapping("/member/register")
//    public int register(@RequestBody SignupDto dto) throws Exception {
//
//        memberService.join(dto);
//        return 1;
//    }

    //이메일이 중복하는지
    @PostMapping("/member/emailCheck")
    public int emailCheck(@RequestParam("email") String email){
        try{
            int i = webClient.get()
                    .uri("/member/emailCheck/"+email)
                    .retrieve()
                    .bodyToMono(Integer.class).block();


//            System.out.println("한 번보자 :" + i);

//            memberService.findMemberByEmail(email);
        }catch(IllegalStateException e){
            return 0;//이메일 중복아님
        }
        return 1; //중북
    }

    //닉네임 수정하기
//    @PostMapping("/member/mypage/nickname")
//    public String changeNickname(@RequestParam("nickname") String nickname,  Authentication auth) {
//        Member user = (Member) auth.getPrincipal();
//
//        Member changedUser = memberService.updateNickname(user.getMemberId(), nickname);
//        //세션 수정
//        memberService.changeSession(changedUser);
//        return nickname;
//    }

    //비밀번호 수정하기
//    @PostMapping("/member/mypage/password")
//    public int changePassword(@RequestParam("originPassword") String originPW, @RequestParam("newPassword") String newPW, Authentication auth) {
//        try{
//            Member changedUser = memberService.changePassword((Member) auth.getPrincipal(), originPW, newPW);
//            memberService.changeSession(changedUser);
//        }catch (Exception e){
//            //에러발생 현재 비밀번호가 일치하지 않을 떄
//            return 0;
//        }
//        return 1;
//    }

}
