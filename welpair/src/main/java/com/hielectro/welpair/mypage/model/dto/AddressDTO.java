package com.hielectro.welpair.mypage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

//    private MemberDTO memberDTO;
}
