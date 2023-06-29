package com.hielectro.welpair.member.model.dao;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    List<MemberDTO> getMemberList(SelectCriteria selectCriteria);


    int totalMemberCount(Map<String, String> searchMap);

    int expiredMemberCount();

}
