package com.hielectro.welpair.member.model.service;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dao.MemberDAO;
import com.hielectro.welpair.member.model.dao.MemberMapper;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;
    private final MemberMapper memberMapper;


    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO, MemberMapper memberMapper) {
        this.memberDAO = memberDAO;
        this.memberMapper = memberMapper;
    }


    @Override
    public List<MemberDTO> getMemberList(SelectCriteria selectCriteria) {
        List<MemberDTO> memberList = memberMapper.getMemberList(selectCriteria);
        return memberList;
    }


    @Override
    public int totalMemberCount(Map<String, String> searchMap) {
        return memberMapper.totalMemberCount(searchMap);
    }

    @Override
    public int expiredMemberCount(Map<String, String> searchMap) {
        return memberMapper.expiredMemberCount(searchMap);
    }




    /* 사용자가 입력한 값을 조회한 후 userdetails 타입의 user객체를 만들어서 반환 */
    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {


        MemberDTO member = memberDAO.findMemberById(empNo);

        /* 조회했을 때 값이 없을 경우 npe발생하는 것을 방지 빈객체에 넣어둡시다. */
        if(member == null){
            throw new UsernameNotFoundException("회원이 존재하지 않습니다. 다시 확인해주세요.");
        }





        return (UserDetails) member;
    }
}
