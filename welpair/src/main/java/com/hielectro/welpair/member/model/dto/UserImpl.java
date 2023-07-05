package com.hielectro.welpair.member.model.dto;


import lombok.Getter;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserImpl extends User{

    private String empNo;           // 사원번호
    private String memPwd;          // 비밀번호
    private Date registDate;        // 가입일
    private int pointBalance;       // 포인트
    private String memAuth;         // 권한

    private List<MemberRoleDTO> memberList;

    private EmployeeDTO employee;

    public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }




    // mapper에서 가져와 객체에 담아주는 작업
    public void setDetails(MemberDTO member){

        this.empNo = member.getEmpNo();
        this.memPwd = member.getMemPwd();
        this.registDate = member.getRegistDate();
        this.pointBalance = member.getPointBalance();
        this.memAuth = member.getMemAuth();
        this.memberList = member.getMemberList();


    }

}
