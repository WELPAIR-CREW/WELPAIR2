package com.hielectro.welpair.regist.model.service;

import com.hielectro.welpair.regist.model.dao.RegistDAO;
import org.springframework.stereotype.Service;

@Service
public class RegistServiceImpl implements RegistService {

    private final  RegistDAO registDAO;


    public RegistServiceImpl(RegistDAO registDAO) {
        this.registDAO = registDAO;
    }



}
