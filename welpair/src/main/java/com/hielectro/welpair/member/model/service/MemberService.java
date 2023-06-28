package com.hielectro.welpair.member.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;

import java.util.List;


public interface MemberService {

    //1-1. 회원조회 - 회원목록
//    List<MemberDTO> getMemberList();
    List<MemberDTO> getMemberList(int currentPage, int itemsPerPage);

    //1-2. 회원조회 - 전체, 퇴사 회원수
    int totalMemberCount();
    int expiredMemberCount();


}
