package com.hielectro.welpair.regist.model.dao;

import com.hielectro.welpair.regist.model.dto.EmployeeDTO;
import com.hielectro.welpair.regist.model.dto.RegistDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistDAO {

    EmployeeDTO selectMemberByUserId(String empNo);

    RegistDTO insertMemberBy(RegistDTO registDTO);


}
