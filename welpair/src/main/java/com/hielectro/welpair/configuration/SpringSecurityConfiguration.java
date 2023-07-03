package com.hielectro.welpair.configuration;

import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity   // 시큐리티 설정을 다루는 클래스임을 선언(권한 및 경로 포함)
public class SpringSecurityConfiguration {

    private MemberService memberService;

    @Autowired
    public SpringSecurityConfiguration(MemberService memberService){
        this.memberService = memberService;
    }

    /* 비밀번호 암호화에 사용할 BCrypt Bean 생성 */
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();  // BC 타입으로된 빈객체를 만들고 그 이름은 PasswordEncoder
    }

    /* 리소스내의 정적 리소스 무시 */
//    @Bean
//    public WebSecurityCustomizer configure(){
//
//        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**");
//    }

    /* HTTP 요청에 대한 권한 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        return http.csrf().disable()
                .authorizeRequests()  // 요청에 대한 권한 체크를 어떻게 할것인지 지정
                .anyRequest().permitAll()     // 등록되지 않은 경로는 누구나 접근 가능  // 나중에 경로 접근권한 추가해야함./////////////
                .and()
                .formLogin()        // 로그인 form을 따로 이용해 로그인 처리할 것이다.
                .loginPage("/member/login")  // login Page로 로그인페이지에서 submit요청하는 경로로 지정하겠다.
                .successForwardUrl("/member/test")      // 성공 시 페이지 설정
               .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/"))  // 로그아웃 시 요청 경로
                .deleteCookies("JSESSIONID")  // 쿠키 제거
                .invalidateHttpSession(true)                    // session 정보 무효화
               .and()
                .exceptionHandling()                     // 인가/인증 exception 핸들링 설정
                .accessDeniedPage("/")     // 인가 되지 않았을 때 - 권한이 없는 기능을 요청했을 떄 랜더랑 할 페이지
                .and().build();

    }

    /* 사요아 인증에서 사용할 Service Bean 등록, 비밀번호 인코딩 방식 설정, 권한을 등록할 때 인증할 비즈니스 로직 등록 */

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(memberService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }


}




