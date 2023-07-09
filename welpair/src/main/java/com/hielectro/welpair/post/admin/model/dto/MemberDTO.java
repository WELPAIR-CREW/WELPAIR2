package com.hielectro.welpair.post.admin.model.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    private String empNo;               // 사번
    private String memPwd;              // 패스워드
    private Date registDate;            // 가입일
    private int pointBalance;           // 포인트
    private String isActivated;         // 활성화여부

}
