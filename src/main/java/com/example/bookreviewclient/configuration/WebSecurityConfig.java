package com.example.bookreviewclient.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/editorUpload/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/member/register",
                            "/member/emailCheck",
                            "/latest",
                            "/popular",
                            "/main/posts").permitAll()
                    .anyRequest().authenticated()
                    .and()// 로그인 설정
                .formLogin()
                    .loginPage("/member/login")
                    .loginProcessingUrl("/login")// /login 주소가 호출되면 시큐리티가 낚아채서 대산 로그인 진행
                    .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                    .permitAll()
                    .and()// 로그아웃 설정
                .logout()
                   .logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
                   .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}