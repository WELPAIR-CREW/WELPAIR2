package com.hielectro.welpair.member.model.service;

import com.hielectro.welpair.member.controller.DeleteMemberException;
import com.hielectro.welpair.member.controller.PointException;
import com.hielectro.welpair.member.controller.RegistMemberException;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;


public interface MemberService extends UserDetailsService {

    //1-1. 회원조회 - 회원목록
//    List<MemberDTO> getMemberList(SelectCriteria selectCriteria);
//
//    //1-2. 회원조회 - 전체, 퇴사 회원수
//    int totalMemberCount(Map<String, String> searchMap);
//    int expiredMemberCount(Map<String, String> searchMap);

    List<MemberDTO> getMemberList(Map<String, Object> map);
    int totalMemberCount();
    int expiredMemberCount();

    //검색기능 추가
    int searchMemberCount(Map<String, Object> map);
    List<MemberDTO> searchMemberList(Map<String, Object> map);







    void deleteMember(List<String> empNos) throws DeleteMemberException;

    //2-1. 회원등록 - 직원목록
    List<EmployeeDTO> getEmployeeList(Map<String, Integer> map);
    int totalEmployeeCount();
    //2-2. 회원등록 - 등록페이지에서 전송버튼 눌렀을때
    void registMember(MemberDTO member) throws RegistMemberException;



    //3-1. 가입승인 - 가입요청 목록
//    List<MemberDTO> reqList();
    //페이징테스트
    List<MemberDTO> reqList(Map<String, Integer> map);
    int reqJoinCount();
    //승인버튼 눌렀을때
    void updateForPermission(List<String> empNos) throws RegistMemberException;



    //4. 포인트지급
    //회원목록 조회
    List<MemberDTO> getMemberListforPoint(Map<String, Integer> map);
    //지급(이력테이블 인서트)
    void insertPointHistory(PointHistoryDTO pointHistoryDTO) throws PointException;
    //지급(회원테이블 업데이트)




    //5. 포인트지급이력
    //5-1. 요약
    List<PointHistoryDTO> pointHistorySummary(Map<String, Integer> map);

    int pointHistorySummaryCount(); //페이징 처리를 위한 총 항목 수 조회

    //5-2. 상세
    List<PointHistoryDTO> pointHistoryDetail(Map<String, Integer> map);
    int pointHistoryDetailCount(int eventId);




}
