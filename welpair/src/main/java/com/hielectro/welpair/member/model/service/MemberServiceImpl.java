package com.hielectro.welpair.member.model.service;
import com.hielectro.welpair.member.controller.DeleteMemberException;
import com.hielectro.welpair.member.controller.Pagenation;
import com.hielectro.welpair.member.controller.RegistMemberException;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dao.MemberMapper;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.ReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }


    //회원조회
    @Override
    public List<MemberDTO> getMemberList(SelectCriteria selectCriteria) {
        List<MemberDTO> memberList = memberMapper.getMemberList(selectCriteria);
        return memberList;
    }


    //전체,퇴사회원수 조회
    @Override
    public int totalMemberCount(Map<String, String> searchMap) {
        return memberMapper.totalMemberCount(searchMap);
    }

    @Override
    public int expiredMemberCount(Map<String, String> searchMap) {


        return memberMapper.expiredMemberCount(searchMap);
    }


    //계정 삭제
    @Override
    @Transactional(rollbackFor = Exception.class) //어떤 예외가 발생하더라도 롤백하게함
    public void deleteMember(List<String> empNos) throws DeleteMemberException {

        //아이디 목록을 가지고 반복문을 통해 딜리트해야한다
        for(int i=0; i<empNos.size(); i++) {
            String empNo = String.valueOf(empNos.get(i));
            int result = memberMapper.deleteMember(empNo);

            if(result > 0) {
                System.out.println("계정삭제 성공");
            } else {
                System.out.println("계정삭제 실패");
                throw new DeleteMemberException("계정삭제 실패"); //@Transactional에 의해 예외발생시 롤백
            }
        }
    }


    //회원등록-직원목록 조회
    @Override
    public List<EmployeeDTO> getEmployeeList(SelectCriteria selectCriteria) {
        List<EmployeeDTO> employeeList = memberMapper.getEmployeeList(selectCriteria);
        return employeeList;
    }

    @Override
    public int totalEmployeeCount(Map<String, String> searchMap) {
        return memberMapper.totalEmployeeCount(searchMap);
    }

    //회원등록-등록페이지에서 전송버튼 눌렀을때
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registMember(MemberDTO member) throws RegistMemberException {
        int result = memberMapper.registMember(member);
        if(result > 0) {
            System.out.println("회원등록 성공");
        } else {
            System.out.println("회원등록 실패");
            throw new RegistMemberException("계정삭제 실패"); //@Transactional에 의해 예외발생시 롤백
        }
    }


    //가입승인 - 가입요청 목록
    @Override
    public List<ReqDTO> reqList() {
        List<ReqDTO> reqList = memberMapper.reqList();
        return reqList;
    }
    @Override
    public int reqJoinCount() { return memberMapper.reqJoinCount(); }

}
