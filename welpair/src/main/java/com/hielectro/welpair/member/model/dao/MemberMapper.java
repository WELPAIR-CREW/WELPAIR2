package com.hielectro.welpair.member.model.dao;
import com.hielectro.welpair.member.controller.SelectCriteria;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    List<MemberDTO> getMemberList(SelectCriteria selectCriteria);

    int totalMemberCount(Map<String, String> searchMap); //셀렉트

    int expiredMemberCount(Map<String, String> searchMap); //셀렉트

    int deleteMember(String empNo); //딜리트

    //회원등록-직원목록
    List<EmployeeDTO> getEmployeeList(SelectCriteria selectCriteria);
    int totalEmployeeCount(Map<String, String> searchMap);
    int registMember(MemberDTO member); //인서트
    //*회원등록할때 회원별권한도 인서트
    int regisMemberRole(String empNo); //인서트

    //가입승인-가입요청 목록
    List<MemberDTO> reqList();
    int reqJoinCount();
    int updateForPermission(String empNo); //업데이트


}
