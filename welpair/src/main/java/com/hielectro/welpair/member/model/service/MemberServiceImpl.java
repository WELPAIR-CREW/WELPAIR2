package com.hielectro.welpair.member.model.service;

import com.hielectro.welpair.member.model.dao.MemberDAO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.UserImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberDAO memberDAO;   // username

    public MemberServiceImpl(MemberDAO memberDAO) { // username
        /* 사용자가 입력한 값을 토대로 DB에 값이 있는지 확인하고 있으면 userdetail타입의 user객체로 반환하는 메소드 */
        this.memberDAO = memberDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로직 : DB에 있는지 확인하고 가져오는 것
        MemberDTO member = memberDAO.findMemberById(username); // dto 일단 담자.

        /* 조회가 안될때 npe 방지를 위한 빈 객체 생성 */
        if(member == null){
            member = new MemberDTO();   // dto 빈 객체에 담아두어서 npe 방지함
        }

        /* 실제 조회 후 값이 있으면 실제로 관리하는 객체에 넘겨줘야한다. */
        // 권한이 지금 테이블에 없어서 테이블 생성하고 나서 작업이 진행되어야할것같다?
//        List<GrantedAuthority> authorities = new ArrayList<>();

        /* 어떤 권한을 가지고있는지 RoleDTO를 만들어 list로 가져와야한다. */

        /* for문으로 Role_name을 넘겨주고 다시 반환해줘야한다.  */



        UserImpl user = new UserImpl(member.getEmpNo(), member.getMemPwd());  // 기본생성자가 없어서 내가 만든걸로만 만들수잇음
        user.setDetails(member);  // 모든 정모를 넘겨준다 member객체로 넘겨
        return null;
    }
}
