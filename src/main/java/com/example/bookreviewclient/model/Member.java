package com.example.bookreviewclient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Builder
@Getter
@AllArgsConstructor
@Table(name="MEMBERINFO")
public class Member implements UserDetails, Serializable{
    private static final long serialVersionUID = 174726374856727L;

    @Id
    @Column(name = "MEMBER_ID")
    @SequenceGenerator(name = "MEMBER_NO_GENERATOR", sequenceName = "MEMBER_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_NO_GENERATOR")
    private Long memberId;

    @Column(name = "MEMBER_EMAIL")
    private String memberEmail;
    @Column(name = "MEMBER_PASSWORD")
    private String memberPassword;
    @Column(name = "MEMBER_NICKNAME")
    private String memberNickname;

    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "member",fetch=FetchType.EAGER)
    @OrderBy("commentId desc ")
    private List<Comments> comments;

    public Member() {

    }

    public Member(Long memberId, String memberEmail, String memberPassword, String memberNickname) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberNickname = memberNickname;
    }

    public Member(Long memberId, String memberEmail, String memberPassword, String memberNickname, String role) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberNickname = memberNickname;
        this.role = role;
    }

    @JsonIgnore
    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));
        return roles;
    }

    @Override
    public String getPassword() {
        return this.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return this.getMemberEmail();
    }
    // ?????? ?????? ?????? ??????
    @Override
    public boolean isAccountNonExpired() {
        // ?????????????????? ???????????? ??????
        return true; // true -> ???????????? ?????????
    }

    // ?????? ?????? ?????? ??????
    @Override
    public boolean isAccountNonLocked() {
        // ?????? ?????????????????? ???????????? ??????
        return true; // true -> ???????????? ?????????
    }

    // ??????????????? ?????? ?????? ??????
    @Override
    public boolean isCredentialsNonExpired() {
        // ??????????????? ?????????????????? ???????????? ??????
        return true; // true -> ???????????? ?????????
    }

    // ?????? ?????? ?????? ?????? ??????
    @Override
    public boolean isEnabled() {
        // ????????? ?????? ???????????? ???????????? ??????
        return true; // true -> ?????? ??????
    }
}