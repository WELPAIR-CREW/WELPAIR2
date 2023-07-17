package com.hielectro.welpair.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.member.model.dto.MemberDTO;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String empNo);



}
