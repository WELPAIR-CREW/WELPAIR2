package com.hielectro.welpair.member.model.dao;

import com.hielectro.welpair.member.model.dto.MemberDTO;

import java.util.List;

public interface MemberMapper {
    List<MemberDTO> getMemberList();
}
