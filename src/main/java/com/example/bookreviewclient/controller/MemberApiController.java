package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.dto.SignupDto;
import com.example.bookreviewclient.model.Member;
import com.example.bookreviewclient.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MemberApiController {
    @Autowired
    private WebClient webClient;

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    //회원가입
    @PostMapping("/member/register")
    public int register(@RequestBody SignupDto dto){
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        dto.setPassword(encodedPassword);
        int i = webClient.post()
                .uri("/member/register")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(1);

        return i;
    }

    //이메일이 중복하는지
    @PostMapping("/member/emailCheck")
    public int emailCheck(@RequestParam("email") String email){
            int i = webClient.post()
                            .uri("/member/emailCheck")
                            .bodyValue(email)
                            .retrieve()
                            .bodyToMono(Integer.class)
                            .flux()
                            .toStream()
                            .findFirst().orElse(1);

            return i;
    }

    //닉네임 수정하기
    @PostMapping("/member/mypage/nickname")
    public String changeNickname(@RequestParam("nickname") String nickname,  Authentication auth) {
        Member user = (Member) auth.getPrincipal();
        Map<String, Object> map = new HashMap<>();

        map.put("nickname", nickname);
        map.put("email", user.getMemberEmail());

        webClient.post()
                .uri("/member/mypage/nickname")
                .bodyValue(map)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Member.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);
        //세션 수정
        Member changedUser = webClient.post()
                .uri("/member/findByMemberEmail")
                .bodyValue(user.getMemberEmail())
                .retrieve()
                .bodyToMono(Member.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        memberService.changeSession(changedUser);
        System.out.println(changedUser.getMemberNickname());
        return nickname;
    }

    //비밀번호 수정하기
    @PostMapping("/member/mypage/password")
    public int changePassword(@RequestParam("originPassword") String originPW, @RequestParam("newPassword") String newPW, Authentication auth) {
        try{
            Member user = (Member) auth.getPrincipal();
            Map<String, Object> map = new HashMap<>();

            map.put("email", user.getMemberEmail());
            map.put("newPW", passwordEncoder.encode(newPW));

            boolean compare = memberService.comparePassword(originPW, user);
            if(compare == false){
                return 0;
            }

            webClient.post()
                    .uri("/member/mypage/password")
                    .bodyValue(map)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .flux()
                    .toStream()
                    .findFirst().orElse(null);

            Member changedUser = webClient.post()
                    .uri("/member/findByMemberEmail")
                    .bodyValue(user.getMemberEmail())
                    .retrieve()
                    .bodyToMono(Member.class)
                    .flux()
                    .toStream()
                    .findFirst().orElse(null);

            memberService.changeSession(changedUser);
        }catch (Exception e){
            //에러발생 현재 비밀번호가 일치하지 않을 떄
            return 0;
        }
        return 1;
    }

}
