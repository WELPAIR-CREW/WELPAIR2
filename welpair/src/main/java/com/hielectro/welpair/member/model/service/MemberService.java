package com.hielectro.welpair.member.model.service;

import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;


public interface MemberService extends UserDetailsService {

    //1-1. 회원조회 - 회원목록
    List<MemberDTO> getMemberList(SelectCriteria selectCriteria);

    //1-2. 회원조회 - 전체, 퇴사 회원수
    int totalMemberCount(Map<String, String> searchMap);
    int expiredMemberCount(Map<String, String> searchMap);








}
