package com.hielectro.welpair.mypage.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dao.MypageMapper;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
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


}
