package com.example.service;

import com.example.dto.DateByWeekDTO;
import com.example.dto.RecodeConditionDTO;
import com.example.dto.RecodeCondtion2DTO;
import com.example.dto.UserDayRecodeDTO;
import com.example.entity.Recode;
import com.example.entity.User;

import java.util.List;

public interface IRecodeService {
    int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user);

    List<Recode> getUserRecode(int userid);

    List<DateByWeekDTO> getRecodeCondition(RecodeConditionDTO recodeConditionDTO);

    List<UserDayRecodeDTO> getRecodeCondition2(RecodeCondtion2DTO recodeCondtion2DTO) throws Exception;

}
