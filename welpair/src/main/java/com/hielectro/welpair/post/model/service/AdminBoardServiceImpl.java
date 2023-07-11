package com.hielectro.welpair.post.model.service;

import com.hielectro.welpair.post.model.dao.AdminBoardDAO;
import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBoardServiceImpl implements AdminBoardService{


    public final AdminBoardDAO adminBoardDAO;


    @Autowired
    public AdminBoardServiceImpl(AdminBoardDAO adminBoardDAO) {
        this.adminBoardDAO = adminBoardDAO;
    }

    @Override
    public int PostSave(AdminBoardDTO adminBoardDTO) {

        System.out.println(adminBoardDTO);

        return 0;

    }

//    public List<AdminBoardDTO> BoardSave(AdminBoardDTO adminBoard){
//        System.out.println(adminBoard.getBoardTitle());
//
//        return null;
//    }
}
