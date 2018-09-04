package com.example.service;

import com.example.dto.recode.*;
import com.example.entity.User;

import java.text.ParseException;
import java.util.List;

public interface IRecodeService {
    int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user) throws ParseException;

    List<DateByWeekDTO> getUserRecode(int userid,String dateTime) throws ParseException;

    List<DateByWeek1DTO> getRecodeCondition(RecodeConditionDTO recodeConditionDTO);

    List<UserWeekRecodeReviewDTO> getRecodeCondition2(RecodeCondtion2DTO recodeCondtion2DTO, User user) throws Exception;

}
