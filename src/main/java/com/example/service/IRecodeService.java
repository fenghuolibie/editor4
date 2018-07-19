package com.example.service;

import com.example.dto.UserDayRecodeDTO;
import com.example.entity.User;

public interface IRecodeService {
    int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user);

    int getUserRecode(int userid);

}
