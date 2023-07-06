package com.hielectro.welpair.mypage.model.dto;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {

    private String addressId;
    private String isDefaultAddress;
    private String addressDetail;
    private String addressName;
    private String addressPhone;
    private String empNo;

    private MemberDTO memberDTO;
}
