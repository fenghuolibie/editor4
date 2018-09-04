package com.example.service;

import com.example.dto.userinfo.LoginMessageDTO;
import com.example.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserService {

    int checkUser(LoginMessageDTO loginMessageDTO, HttpServletRequest httpServletRequest);

    List<String> getDepamentAllname(int departmnet);

    List<String> getAllname();

    int addUser(User userbean,User user);

}
