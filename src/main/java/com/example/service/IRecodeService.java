package com.example.service;

import com.example.dto.UserDayRecodeDTO;
import com.example.entity.Recode;
import com.example.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRecodeService {
    int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user);

    List<Recode> getUserRecode(int userid);

    List<UserDayRecodeDTO> getRecodeCondition(String theDay,String UserName,HttpServletRequest request);

}
