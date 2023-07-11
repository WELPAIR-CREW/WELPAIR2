package com.hielectro.welpair.member.model.service;
import com.hielectro.welpair.member.controller.DeleteMemberException;
import com.hielectro.welpair.member.controller.PointException;
import com.hielectro.welpair.member.controller.RegistMemberException;
import com.hielectro.welpair.member.model.dao.MemberDAO;
import com.hielectro.welpair.member.model.dao.MemberMapper;
import com.hielectro.welpair.member.model.dto.*;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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


    //1. 회원조회 페이지
//    @Override
//    public List<MemberDTO> getMemberList(SelectCriteria selectCriteria) {
//        List<MemberDTO> memberList = memberMapper.getMemberList(selectCriteria);
//        return memberList;
//    }
//
//
//    //전체,퇴사회원수 조회
//    @Override
//    public int totalMemberCount(Map<String, String> searchMap) {
//        return memberMapper.totalMemberCount(searchMap);
//    }
//
//    @Override
//    public int expiredMemberCount(Map<String, String> searchMap) {
//
//
//        return memberMapper.expiredMemberCount(searchMap);
//    }

    @Override
    public List<MemberDTO> getMemberList(Map<String, Object> map) {
        List<MemberDTO> memberList = memberMapper.getMemberList(map);
        return memberList;
    }

    @Override
    public int totalMemberCount() {
        return memberMapper.totalMemberCount();
    }

    @Override
    public int expiredMemberCount() {
        return memberMapper.expiredMemberCount();
    }

    //검색기능 추가
    @Override
    public int searchMemberCount(Map<String, Object> map) {
        return memberMapper.searchMemberCount(map);
    }

    @Override
    public List<MemberDTO> searchMemberList(Map<String, Object> map) {
        return memberMapper.searchMemberList(map);
    }





    /* 사용자가 입력한 값을 조회한 후 userdetails 타입의 user객체를 만들어서 반환 */
    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {

        System.out.println("empNo = " + empNo);   // 잘들어옴
        MemberDTO member = memberDAO.findMemberById(empNo);
        System.out.println("member ====================== " + member);
        /* 조회했을 때 값이 없을 경우 npe발생하는 것을 방지 빈객체에 넣어둡시다. */
        if(member == null){
            member = new MemberDTO();
        }

        //권한 리스트 뽑아오기
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(member.getMemberList() != null){

            List<MemberRoleDTO> roleList = member.getMemberList();

            for(int i = 0; i < roleList.size(); i++){

                AuthorityDTO authority = roleList.get(i).getAuthority();
                authorities.add(new SimpleGrantedAuthority(authority.getAuthName()));
            }
        }


        UserImpl user = new UserImpl(member.getEmpNo(), member.getMemPwd(), authorities);
        user.setDetails(member);


        return user;
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


    //2. 회원등록-직원목록 조회
    @Override
    public List<EmployeeDTO> getEmployeeList(Map<String, Integer> map) {
        List<EmployeeDTO> employeeList = memberMapper.getEmployeeList(map);
        return employeeList;
    }

    @Override
    public int totalEmployeeCount() {
        return memberMapper.totalEmployeeCount();
    }

    //회원등록-등록페이지에서 전송버튼 눌렀을때
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registMember(MemberDTO member) throws RegistMemberException {
        int result = memberMapper.registMember(member);
        if(result > 0) {
            System.out.println("회원등록 성공");

            //회원별권한도 인서트
            String empNo = member.getEmpNo();
            int result2 = memberMapper.regisMemberRole(empNo);
            if(result2>0) {
                System.out.println("회원권한등록 성공");
            } else {
                System.out.println("회원권한등록 실패");
                throw new RegistMemberException("회원권한등록 실패");
            }

        } else {
            System.out.println("회원등록 실패");
            throw new RegistMemberException("회원등록 실패"); //@Transactional에 의해 예외발생시 롤백
        }
    }


    //3. 가입승인 - 가입요청 목록
//    @Override
//    public List<MemberDTO> reqList() {
//        List<MemberDTO> reqList = memberMapper.reqList();
//        return reqList;
//    }

    //새로운 페이징 테스트
    @Override
    public List<MemberDTO> reqList(Map<String, Integer> map) {
        List<MemberDTO> reqList = memberMapper.reqList(map);
        return reqList;
    }
    @Override
    public int reqJoinCount() { return memberMapper.reqJoinCount(); }


    //승인버튼
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateForPermission(List<String> empNos) throws RegistMemberException {
        //아이디 목록을 가지고 반복문을 통해 업데이트해야한다
        for(int i=0; i<empNos.size(); i++) {
            String empNo = String.valueOf(empNos.get(i));
            int result = memberMapper.updateForPermission(empNo);

            if(result > 0) {
                System.out.println("가입승인 성공");
            } else {
                System.out.println("가입승인 실패");
                throw new RegistMemberException("가입승인 실패"); //@Transactional에 의해 예외발생시 롤백
            }
        }
    }


    //4. 포인트지급 페이지
    //지급을 위한 회원 목록 조회
    @Override
    public List<MemberDTO> getMemberListforPoint(Map<String, Object> map) {
        List<MemberDTO> memberList = memberMapper.getMemberListforPoint(map);
        return memberList;
    }
    //지급(이력테이블 인서트)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertPointHistory(PointHistoryDTO pointHistoryDTO) throws PointException {

        try {
            //처음 인서트되는 회원
            int eventId = memberMapper.getNextEventId();
            System.out.println("getNextEventId()로 설정한 eventId = " + eventId);
            pointHistoryDTO.setEventId(eventId);

            pointHistoryDTO.setEmpNo(pointHistoryDTO.getEmpNos().get(0));
            memberMapper.insertPointHistory(pointHistoryDTO);

            //회원테이블의 포인트잔액 업데이트
            Map<String, Object> map = new HashMap<>();
            map.put("empNo", pointHistoryDTO.getEmpNo());
            map.put("selectedAmount", pointHistoryDTO.getSelectedAmount());
            memberMapper.updatePointBalance(map);


            //이후 회원(모든 회원에게 처음 인서트된 것과 같은 eventId가 할당되어야함)
            eventId = memberMapper.getCurrEventId();
            System.out.println("getCurrEventId()로 설정한 eventId = " + eventId);

            for (int i = 1; i < pointHistoryDTO.getEmpNos().size(); i++) {

                pointHistoryDTO.setEventId(eventId);
                pointHistoryDTO.setEmpNo(pointHistoryDTO.getEmpNos().get(i));
                memberMapper.insertPointHistory(pointHistoryDTO);

                //회원테이블의 포인트잔액 업데이트
                Map<String, Object> secondMap = new HashMap<>();
                secondMap.put("empNo", pointHistoryDTO.getEmpNo());
                secondMap.put("selectedAmount", pointHistoryDTO.getSelectedAmount());
                memberMapper.updatePointBalance(secondMap);

            }
        } catch (Exception e) {
            System.out.println("포인트 이력 insert, 회원 포인트잔액 update 실패");
            throw new PointException("포인트 이력 insert, 회원 포인트잔액 update 실패");
        }
    }


    //5. 포인트지급이력
    //5-1. 요약
    @Override
    public List<PointHistoryDTO> pointHistorySummary(Map<String, Integer> map) {
        List<PointHistoryDTO> pointHistorySummaryList = memberMapper.pointHistorySummary(map);
        return pointHistorySummaryList;
    }
    //요약페이지 페이징처리를 위한 총 항목수 조회
    @Override
    public int pointHistorySummaryCount() { return memberMapper.pointHistorySummaryCount(); }


    // 5-2. 상세
    @Override
    public List<PointHistoryDTO> pointHistoryDetail(Map<String, Integer> map) {
        List<PointHistoryDTO> pointHistoryDetailList = memberMapper.pointHistoryDetail(map);
        return pointHistoryDetailList;
    }
    @Override
    public int pointHistoryDetailCount(int eventId) { return memberMapper.pointHistoryDetailCount(eventId); }


}
