package com.hielectro.welpair.member.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    private String empNo;           // 사원번호
    private String memPwd;          // 비밀번호
    private Date registDate;        // 가입일
    private int pointBalance;       // 포인트
    private String memAuth;         // 권한

    private EmployeeDTO employee;

}
