package com.hielectro.welpair.mypage.model.dao;

import com.hielectro.welpair.board.model.dto.BoardDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.mypage.model.dto.WishlistSellProductDTO;
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
    //기본배송지 초기화
    int resetDefaultAddress(String empNo);


    //4. 마이포인트 이력 조회
    List<PointHistoryDTO> mypointList(Map<String, Object> map);
    //페이징처리를 위한 마이포인트 총 항목 수
    int myPointListCount(String empNo);
    //포인트 잔액 조회
    int getPointBalance(String empNo);


    //3. 위시리스트 목록 조회
    String getWishId(String empNo);
    List<WishlistSellProductDTO> getWishlistList(String wishId);


    //5. 내가쓴글
    //문의글목록
    List<BoardDTO> myQnaList(String empNo);
    //페이징처리를 위한 총 항목 수
    int myQnaCount(String empNo);

}
