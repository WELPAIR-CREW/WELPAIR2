package com.hielectro.welpair.member.model.dto;
import lombok.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    private String empNo;
    private String memPwd;
    private Date registDate;
    private int pointBalance;
    private String memAuth;

    private EmployeeDTO employee;

}
