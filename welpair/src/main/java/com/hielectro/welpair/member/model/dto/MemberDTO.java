package com.hielectro.welpair.member.model.dto;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import lombok.*;
import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    private String empNo;    // 사원번호(아이디)
    private String memPwd;      // 비밀번호
    private Date registDate;       // 가입날짜
    private int pointBalance;       // 포인트
    private String memAuth;         // 권한
    private String isActivated;     //활성화여부 CHAR(1) 기본값Y

    private List<MemberRoleDTO> memberList;  //

    private EmployeeDTO employee;

    private List<AddressDTO> address;

}
