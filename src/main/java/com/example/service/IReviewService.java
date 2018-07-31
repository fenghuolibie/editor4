package com.example.service;

import com.example.dto.recode.UserDayRecodeDTO;
import com.example.dto.recode.WeekReviewDTO;

import java.util.List;

public interface IReviewService {
    List<WeekReviewDTO> getReview(String dateTime,String userName);

    List<UserDayRecodeDTO> getRecodeByLevel(String dateTime,String userName);
}
