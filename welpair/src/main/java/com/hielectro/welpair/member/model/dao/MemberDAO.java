package com.hielectro.welpair.member.model.dao;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String username);

    // DB에 접근을 위한 객체
}
