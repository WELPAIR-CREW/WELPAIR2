package com.hielectro.welpair.security.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 후 리다이렉트할 URL 설정
        String targetUrl = redirectTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        response.sendRedirect(targetUrl);
    }

    private String redirectTargetUrl(Authentication authentication) {
        // 원하는 페이지로 리다이렉트할 URL을 반환하는 로직을 구현합니다.
        // 예를 들어, 특정 권한에 따라 다른 URL을 리다이렉트할 수 있습니다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "/admin/index";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
            return "/index";
        } else {
            throw new IllegalStateException("잘못된 권한");
        }
    }
}