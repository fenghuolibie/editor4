package com.example.serviceimpl;

import com.example.dto.recode.UserDayRecodeDTO;
import com.example.dto.recode.WeekReviewDTO;
import com.example.mapper.ReviewMapper;
import com.example.mapper.UserMapper;
import com.example.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据指定的时间和用于表示审查人的姓名查看数据
     *
     * @param dateTime
     * @return
     */
    @Override
    public List<WeekReviewDTO> getReview(String dateTime, String userName) {
        return null;
    }

    /**
     * 根据指定审查人的等级和指定的时间来获得可以审核的一周日清
     *
     * @param dateTime
     * @return
     */
    @Override
    public List<UserDayRecodeDTO> getRecodeByLevel(String dateTime, String userName) {
        switch (Integer.parseInt(userMapper.selectLevelByName(userName))) {
            case 4:

                break;
            case 3:
                break;
            case 2:
                break;
            case 1:
                break;
            default:
                break;
        }
        return null;
    }
}
