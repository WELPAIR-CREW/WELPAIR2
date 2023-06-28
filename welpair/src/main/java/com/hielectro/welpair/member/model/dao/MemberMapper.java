package com.hielectro.welpair.member.model.dao;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
//    List<MemberDTO> getMemberList();
//    List<MemberDTO> getMemberList(int startRow, int endRow);
    List<MemberDTO> getMemberList(Map<String, Integer> startAndEndRow);


    int totalMemberCount();

    int expiredMemberCount();
}
