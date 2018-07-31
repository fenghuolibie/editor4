package com.example.service;

import com.example.dto.recode.DateByWeekDTO;
import com.example.dto.recode.UserDayRecodeDTO;
import com.example.dto.recode.UserWeekRecodeReviewDTO;
import com.example.dto.recode.RecodeConditionDTO;
import com.example.dto.recode.RecodeCondtion2DTO;
import com.example.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

public interface IRecodeService {
    int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user) throws ParseException;

    List<DateByWeekDTO> getUserRecode(int userid);

    List<DateByWeekDTO> getRecodeCondition(RecodeConditionDTO recodeConditionDTO);

    List<UserWeekRecodeReviewDTO> getRecodeCondition2(RecodeCondtion2DTO recodeCondtion2DTO, HttpServletRequest request) throws Exception;

}
