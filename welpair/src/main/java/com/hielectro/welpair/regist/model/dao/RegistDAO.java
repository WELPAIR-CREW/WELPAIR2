package com.hielectro.welpair.regist.model.dao;

import com.hielectro.welpair.regist.model.dto.EmployeeDTO;
import com.hielectro.welpair.regist.model.dto.MemberRoleDTO;
import com.hielectro.welpair.regist.model.dto.RegistDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistDAO {

    EmployeeDTO selectMemberByUserId(String empNo);

    int insertMember(RegistDTO regist);


    int idCheck(String empNo);

    int insertMemberRole(String empNo);



}
