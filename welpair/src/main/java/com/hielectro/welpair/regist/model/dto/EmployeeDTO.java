package com.hielectro.welpair.regist.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {


    private String empNo;                   // 사번
    private String empName;                 // 이름
    private String hireType;                // 고용형태
    private String empEmail;                // 이메일
    private String empPhone;                // 휴대폰번호
    private String empDept;                 // 부서
    private String jobCode;                 // 직급
    private Date hireDate;                  // 입사일
    private Date expireDate;                // 퇴사일
    private String isExpire;                // 퇴사여부

}
