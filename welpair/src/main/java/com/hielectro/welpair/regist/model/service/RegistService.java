package com.hielectro.welpair.regist.model.service;

import com.hielectro.welpair.regist.controller.MemberRegistException;
import com.hielectro.welpair.regist.model.dto.RegistDTO;

public interface RegistService {

//    int idCheck(String empNo);

    int idCheck(String empNo);


    void registSave(RegistDTO regist) throws MemberRegistException;
}
