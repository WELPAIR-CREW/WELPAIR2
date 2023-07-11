package com.hielectro.welpair.mypage.model.service;

import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;

import java.util.List;
import java.util.Map;

public interface MypageService {

    //2. 배송지 목록
    List<AddressDTO> getAddressList(Map<String, String> map);

    //배송지 삭제버튼
    void deleteAddress(String addressId) throws Exception;

    //배송지 등록버튼
    void registAddress(AddressDTO addressDTO) throws Exception;
    //배송지 아이디
    String nextAddressId();


    //4. 마이포인트 이력 조회
    List<PointHistoryDTO> mypointList(String empNo);
}
