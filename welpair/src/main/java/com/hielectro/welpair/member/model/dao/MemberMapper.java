package com.hielectro.welpair.member.model.dao;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    //1. 회원조회
//    List<MemberDTO> getMemberList(Map<String, Object> map);

    int totalMemberCount();
    int expiredMemberCount();

    //검색기능 추가
    int searchMemberCount(Map<String, Object> map);
    List<MemberDTO> searchMemberList(Map<String, Object> map);

    int deleteMember(String empNo); //딜리트





    //2. 회원등록
    // 직원목록
    List<EmployeeDTO> getEmployeeList(Map<String, Integer> map);
    int totalEmployeeCount();
    int registMember(MemberDTO member); //인서트
    //*회원등록할때 회원별권한도 인서트
    int regisMemberRole(String empNo); //인서트

    //3. 가입승인-가입요청 목록
//    List<MemberDTO> reqList();
    //새로운 페이징 테스트
    List<MemberDTO> reqList(Map<String, Integer> map);
    int reqJoinCount();
    int updateForPermission(String empNo); //업데이트

    //4. 포인트지급
    //4-1. 포인트지급을 위한 회원목록
    List<MemberDTO> getMemberListforPoint(Map<String, Object> map);
    //4-2. 포인트지급(포인트이력테이블 인서트)
    int insertPointHistory(PointHistoryDTO pointHistoryDTO);
    int getNextEventId();
    int getCurrEventId();


    //4-3. 포인트지급(회원테이블 업데이트)
    int updatePointBalance(Map<String, Object> map);


    //5. 포인트지급이력
    //5-1. 포인트지급이력(요약)
    List<PointHistoryDTO> pointHistorySummary(Map<String, Integer> map);
    //페이징처리를 위한 총 항목수 조회
    int pointHistorySummaryCount();

    //5-2. 포인트지급이력(상세)
    List<PointHistoryDTO> pointHistoryDetail(Map<String, Integer> map);
    int pointHistoryDetailCount(int eventId);

}
