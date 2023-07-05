package com.hielectro.welpair.member.model.service;

import com.hielectro.welpair.member.controller.DeleteMemberException;
import com.hielectro.welpair.member.controller.RegistMemberException;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;


public interface MemberService extends UserDetailsService {

    //1-1. 회원조회 - 회원목록
    List<MemberDTO> getMemberList(SelectCriteria selectCriteria);

    //1-2. 회원조회 - 전체, 퇴사 회원수
    int totalMemberCount(Map<String, String> searchMap);
    int expiredMemberCount(Map<String, String> searchMap);









    void deleteMember(List<String> empNos) throws DeleteMemberException;

    //2-1. 회원등록 - 직원목록
    List<EmployeeDTO> getEmployeeList(SelectCriteria selectCriteria);
    int totalEmployeeCount(Map<String, String> searchMap);
    //2-2. 회원등록 - 등록페이지에서 전송버튼 눌렀을때
    void registMember(MemberDTO member) throws RegistMemberException;



    //3-1. 가입승인 - 가입요청 목록
    List<MemberDTO> reqList();
    int reqJoinCount();
    //승인버튼 눌렀을때
    void updateForPermission(List<String> empNos) throws RegistMemberException;



}
