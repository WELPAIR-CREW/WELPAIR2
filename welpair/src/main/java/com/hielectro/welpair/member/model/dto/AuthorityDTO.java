package com.hielectro.welpair.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorityDTO {

    private int authCode;       // 권한코드
    private String authName;    // 권한명

//    private List<AuthenticatedMenuDTO> authenticationMenuList;
//    메뉴 권한도 테이블 생성해야한다.  권한별 접근가능 메뉴 리스트로 넣어야함

}
