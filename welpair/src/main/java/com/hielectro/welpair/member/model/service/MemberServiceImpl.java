package com.hielectro.welpair.member.model.service;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dao.MemberMapper;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
