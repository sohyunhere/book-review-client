package com.example.bookreviewclient.service;

import com.example.bookreviewclient.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    @Autowired
    private WebClient webClient;

    private final PasswordEncoder passwordEncoder;

    @Override// 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 MemberInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        Member member = webClient.post()
                .uri("/member/findByMemberEmail")
                .bodyValue(username)
                .retrieve()
                .bodyToMono(Member.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return member;
    }

    public void changeSession(Member member){
        //세션 수정
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member , member.getPassword(), member.getAuthorities());
        Authentication newAuth = token;
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return;
    }

    public boolean comparePassword(String originPW, Member member){
        if(passwordEncoder.matches(originPW, member.getMemberPassword())){
            return true;
        }else{
            return false;
        }
    }
}
