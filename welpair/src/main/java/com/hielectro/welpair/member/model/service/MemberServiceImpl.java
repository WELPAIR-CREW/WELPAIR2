package com.hielectro.welpair.member.model.service;
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


//    @Override
//    public List<MemberDTO> getMemberList() {
//        return memberMapper.getMemberList();
//    }
    @Override
    public List<MemberDTO> getMemberList(int currentPage, int itemsPerPage) {

        int startRow = (currentPage - 1) * itemsPerPage + 1;
        int endRow = startRow + itemsPerPage - 1;
//        return memberMapper.getMemberList(startRow, endRow);

        Map<String, Integer> startAndEndRow = new HashMap<>();
        startAndEndRow.put("startRow", startRow);
        startAndEndRow.put("endRow", endRow);
        return memberMapper.getMemberList(startAndEndRow);
    }


    @Override
    public int totalMemberCount() {
        return memberMapper.totalMemberCount();
    }

    @Override
    public int expiredMemberCount() {
        return memberMapper.expiredMemberCount();
    }
}
