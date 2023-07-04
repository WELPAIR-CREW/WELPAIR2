package com.hielectro.welpair.member.model.dao;

import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String empNo);

    EmployeeDTO selectMemberByUserId(String empNo);



}
