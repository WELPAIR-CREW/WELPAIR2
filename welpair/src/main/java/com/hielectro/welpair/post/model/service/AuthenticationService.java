package com.hielectro.welpair.post.model.service;

import com.hielectro.welpair.post.model.dao.AdminBoardDAO;
import com.hielectro.welpair.post.model.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationService implements UserDetailsService {

    private final AdminBoardDAO adminBoardDAO;

    public AuthenticationService(AdminBoardDAO adminBoardDAO) {
        this.adminBoardDAO = adminBoardDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {

        MemberDTO member = adminBoardDAO.findByMemberId(empNo);

        if(member == null){
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }

        return (UserDetails) member;
    }
}
