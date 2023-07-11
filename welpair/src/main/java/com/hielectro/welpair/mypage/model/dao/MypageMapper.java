package com.hielectro.welpair.mypage.model.dao;

import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MypageMapper {

    //2. 배송지 목록
    List<AddressDTO> getAddressList(Map<String,String> map);

    //배송지 삭제 버튼
    int deleteAddress(String addressId);

    //배송지 등록 버튼
    int insertAddress(AddressDTO addressDTO);
    //배송지 아이디
    String nextAddressId();


    //4. 마이포인트 이력 조회
    List<PointHistoryDTO> mypointList(String empNo);
}
