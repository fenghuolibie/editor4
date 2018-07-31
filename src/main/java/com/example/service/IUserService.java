package com.example.service;

import com.example.dto.userinfo.LoginMessageDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserService {

    String checkUser(LoginMessageDTO loginMessageDTO, HttpServletRequest httpServletRequest);

    List<String> getDepamentAllname(int departmnet);

    List<String> getAllname();
}
