package com.hielectro.welpair.member.model.dao;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDTO> getMemberList();


    int totalMemberCount();

    int expiredMemberCount();
}
