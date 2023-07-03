package com.hielectro.welpair.member.model.dto;
import lombok.*;
import java.util.Date;
import java.util.List;

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
    private String empDept;
    private String jobCode;
    private Date hireDate;
    private Date expireDate;
    private String isExpire; //데이터타입 CHAR = 'N'



}
