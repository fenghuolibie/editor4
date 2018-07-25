package com.example.service;

import com.example.dto.LoginMessageDTO;
import com.example.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserService {
    User getUser(int id);

    String checkUser(LoginMessageDTO loginMessageDTO, HttpServletRequest httpServletRequest);

    List<String> getDepamentAllname(int departmnet);

    List<String> getAllname();
}
