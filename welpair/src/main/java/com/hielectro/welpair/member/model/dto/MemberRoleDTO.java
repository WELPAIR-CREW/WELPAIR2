package com.hielectro.welpair.member.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberRoleDTO {

    private String memberNo;
    private int authCode;        // 권한코드
    private AuthorityDTO authority;    // 회원이 보유하고있는 권한
}
