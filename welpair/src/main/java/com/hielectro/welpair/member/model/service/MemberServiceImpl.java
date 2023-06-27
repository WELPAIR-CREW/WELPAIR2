package com.hielectro.welpair.member.model.service;
import com.hielectro.welpair.member.model.dao.MemberMapper;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

    @Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public List<MemberDTO> getMemberList() {
        return memberMapper.getMemberList();
    }
}
