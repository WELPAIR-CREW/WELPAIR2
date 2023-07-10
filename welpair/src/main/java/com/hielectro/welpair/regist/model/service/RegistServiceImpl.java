package com.hielectro.welpair.regist.model.service;

import com.hielectro.welpair.regist.controller.MemberRegistException;
import com.hielectro.welpair.regist.model.dao.RegistDAO;
import com.hielectro.welpair.regist.model.dto.MemberRoleDTO;
import com.hielectro.welpair.regist.model.dto.RegistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistServiceImpl implements RegistService {


    private final RegistDAO mapper;

    @Autowired
    public RegistServiceImpl(RegistDAO mapper) {
        this.mapper = mapper;
    }






    @Override
    public int idCheck(String empNo) {

        int cnt = mapper.idCheck(empNo);
        System.out.println("cnt = " + cnt);

        return cnt;
    }
//    @Override
//    public int idMemberCheck(String empNo){
//
//        int result = mapper.idMemberCheck(empNo);
//
//        return result;
//
//    }


    @Override
    @Transactional
    public void registSave(RegistDTO regist) throws MemberRegistException {

    int result = mapper.insertMember(regist);
    if(result > 0){
        System.out.println("등록성공");

        String empNo = regist.getEmpNo();
        int result2 = mapper.insertMemberRole(empNo);
        if(result2 > 0){
            System.out.println("권한등록 성공");
        }else {
            System.out.println("권한등록 실패");
            throw new MemberRegistException("해당 회원의 권한 등록에 실패하였습니다.");
        }
    } else {
        System.out.println("등록실패");
        throw new MemberRegistException("회원 가입에 실패하였습니다.");
    }

    }






}












