package com.example.service;

import com.example.dto.review.RecodeAndReviewDTO;
import com.example.dto.review.ReviewedUserDTO;
import com.example.dto.review.WeekReviewInsertDTO;

import java.text.ParseException;
import java.util.List;

public interface IReviewService {
    List<ReviewedUserDTO> getReview(String dateTime, String userName) throws ParseException;

    List<RecodeAndReviewDTO> getRecodeReciewByLevel(String dateTime, String userName) throws ParseException;

    int insertReview(WeekReviewInsertDTO weekReviewInsertDTO, int userId) throws ParseException;

    int judgeWeekReviewInsertDTO(WeekReviewInsertDTO weekReviewInsertDTO, int userId);
}
