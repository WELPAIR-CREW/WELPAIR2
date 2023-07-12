package com.hielectro.welpair.mypage.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.mypage.model.dao.MypageMapper;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.mypage.model.dto.WishlistSellProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MypageServiceImpl implements MypageService{

    private final MypageMapper mypageMapper;

    @Autowired
    public MypageServiceImpl(MypageMapper mypageMapper) {
        this.mypageMapper = mypageMapper;
    }


    //2. 배송지 목록
    @Override
    public List<AddressDTO> getAddressList(Map<String, String> map) {

        List<AddressDTO> addressList = mypageMapper.getAddressList(map);
        return addressList;
    }

    //배송지 삭제 버튼
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAddress(String addressId) throws Exception {
        int result = mypageMapper.deleteAddress(addressId);
        if(result>0) {
            System.out.println("배송지 삭제 성공");
        } else {
            System.out.println("배송지 삭제 실패");
            throw new Exception("배송지 삭제 실패");
        }
    }

    //배송지 등록 버튼
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registAddress(AddressDTO addressDTO) throws Exception {
        int result = mypageMapper.insertAddress(addressDTO);
        if(result>0) {
            System.out.println("배송지 등록 성공");
        } else {
            System.out.println("배송지 등록 실패");
            throw new Exception("배송지 등록 실패");
        }
    }

    //배송지아이디
    @Override
    public String nextAddressId() {
        String nextAddressId = mypageMapper.nextAddressId();
        return nextAddressId;
    }


    //4. 마이포인트 이력 조회
    @Override
    public List<PointHistoryDTO> mypointList(Map<String, Object> map) {
        List<PointHistoryDTO> mypointList = mypageMapper.mypointList(map);
        return mypointList;
    }

    //페이징처리를 위한 마이포인트 총항목수
    @Override
    public int myPointListCount(String empNo) {
        return mypageMapper.myPointListCount(empNo);
    }

    //포인트 잔액 조회
    @Override
    public int getPointBalance(String empNo) {
        return mypageMapper.getPointBalance(empNo);
    }


    //위시리스트 목록
    @Override
    public String getWishId(String empNo) {
        return mypageMapper.getWishId(empNo);
    }
    @Override
    public List<WishlistSellProductDTO> getWishlistList(String wishId) {
        return mypageMapper.getWishlistList(wishId);
    }


}
