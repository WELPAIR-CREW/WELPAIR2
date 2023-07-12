package com.hielectro.welpair.post.model.service;

import com.hielectro.welpair.post.controller.BoardException;
import com.hielectro.welpair.post.model.dto.AdminBoardDTO;


public interface AdminBoardService {


    public void PostSave(AdminBoardDTO adminBoardDTO) throws BoardException;
}
