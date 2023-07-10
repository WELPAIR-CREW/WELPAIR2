package com.hielectro.welpair.member.model.dto;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String empNo;
    private String empName;
    private String hireType;
    private String empEmail;
    private String empPhone;
    private String deptCode;
    private String jobCode;
    private Date hireDate;
    private Date expireDate;
    private String isExpire; //데이터타입 CHAR = 'N'

    private MemberDTO memberDTO;
    private DeptDTO deptDTO;
    private JobDTO jobDTO;

    private int yearCount; //포인트지급 페이지에 표시하는 근속연수

}
