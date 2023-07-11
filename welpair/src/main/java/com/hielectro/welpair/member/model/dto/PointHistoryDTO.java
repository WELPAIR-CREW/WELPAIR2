package com.hielectro.welpair.member.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PointHistoryDTO {
    private String pointNo; //회원별로 고유한 시퀀스넘버
    private String empNo;
    private Date pointDate;
    private int pointAmount;
    private String pointType;
    private String pointReason;
    private int eventId; //요약조회를 위한 지급이벤트번호

    //요약조회
    private Date pointDate2;
    private int pointCount; //지급회원수
    private int pointTotal; //지급총액

    private EmployeeDTO employeeDTO;
    private DeptDTO deptDTO;
    private JobDTO jobDTO;


    //지급하기(ajax요청할때 보내는 서버로 보내는 데이터들)
    private int selectedCount;
    private List<String> empNos;
    private String selectedReason;
    private int selectedAmount;
}
