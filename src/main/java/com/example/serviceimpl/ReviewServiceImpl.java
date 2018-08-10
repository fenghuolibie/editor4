package com.example.serviceimpl;

import com.example.common.util.DateUtil;
import com.example.dto.recode.DateByWeekDTO;
import com.example.dto.review.RecodeAndReviewDTO;
import com.example.dto.review.ReviewUserDTO;
import com.example.dto.review.ReviewedUserDTO;
import com.example.dto.review.WeekReviewInsertDTO;
import com.example.entity.Review;
import com.example.enums.WeekEnum;
import com.example.mapper.RecodeMapper;
import com.example.mapper.ReviewMapper;
import com.example.mapper.UserMapper;
import com.example.service.IReviewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecodeMapper recodeMapper;

    /**
     * 根据指定的时间和被审查人的姓名查看审查内容
     *
     * @param dateTime
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<ReviewedUserDTO> getReview(String dateTime, String userName) throws ParseException {
        dateTime = DateUtil.getFirstDayBy(DateUtil.getStringDate(dateTime));
        String userId = userMapper.selectIdByName(userName);
        String weekId = dateTime + StringUtils.leftPad(userId, 5, "0");
        return reviewMapper.selectReviewByWeekId(weekId);
    }

    /**
     * 根据指定审查人的等级和指定的时间来获得可以审核的一周日清
     *
     * @param dateTime
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<RecodeAndReviewDTO> getRecodeReciewByLevel(String dateTime, String userName) throws ParseException {
        dateTime = DateUtil.getFirstDayBy(DateUtil.getStringDate(dateTime));
        int dateTime1 = Integer.parseInt(dateTime);
        List<String> nameList = null;
        switch (Integer.parseInt(userMapper.selectLevelByName(userName))) {
            case 4:
                nameList = userMapper.selectNameByLevel(2, 3);
                break;
            case 3:
                nameList = userMapper.selectNameByLevel(2, 2);
                break;
            case 2:
                nameList = userMapper.selectNameByLevel(1, 1);
                break;
            case 1:
                return null;
            default:
                return null;
        }
        String currentUserId = userMapper.selectIdByName(userName);
        String userId;
        String weekId;
        RecodeAndReviewDTO recodeAndReviewDTO = null;
        List<RecodeAndReviewDTO> recodeAndReviewDTOList = new ArrayList<>();
        ReviewUserDTO userReviewDTO = null;
        Review review = null;
        DateByWeekDTO dateByWeekDTO = null;
        for (String name : nameList) {
            //根据weekid获得周数据
            userId = userMapper.selectIdByName(name);
            recodeAndReviewDTO = new RecodeAndReviewDTO();
            weekId = dateTime + StringUtils.leftPad(userId, 5, "0");
            List<DateByWeekDTO> dateByWeekDTOList = recodeMapper.selectUserRecodeByweekId(weekId);
            if (dateByWeekDTOList.size() == 0) {
                dateByWeekDTOList = new ArrayList<>(7);
//                for (int i = 0; i < 7; i++) {
//                    dateByWeekDTO = new DateByWeekDTO();
//                    dateByWeekDTO.setDateTime(new Integer(dateTime1+i).toString());
//                    dateByWeekDTO.setWeekDay(WeekEnum.getByValue(i+2).getMessage());
//                    dateByWeekDTOList.add(dateByWeekDTO);
//                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.getStringDate(dateTime));
                for (int i = 0; i < 7; i++) {
                    dateByWeekDTO = new DateByWeekDTO();
                    dateByWeekDTO.setWeekDay(WeekEnum.getByValue(i + 2).getMessage());
                    dateByWeekDTO.setDateTime(DateUtil.getCurrentDate(calendar.getTime()));
                    dateByWeekDTOList.add(dateByWeekDTO);
                    calendar.add(Calendar.DATE, 1);
                }
            }
            recodeAndReviewDTO.setDateByWeekDTOList(dateByWeekDTOList);
            recodeAndReviewDTO.setUserId(Integer.parseInt(userId));
            recodeAndReviewDTO.setUserName(name);
            userReviewDTO = new ReviewUserDTO();
            //获得相应的审批数据
            review = reviewMapper.selectReviewByUserWeekId(weekId, currentUserId);
            if (review == null) {

            } else {
                BeanUtils.copyProperties(review, userReviewDTO);
                recodeAndReviewDTO.setUserReviewDTO(userReviewDTO);
            }
            recodeAndReviewDTOList.add(recodeAndReviewDTO);
        }
        return recodeAndReviewDTOList;
    }

    /**
     * 更新或插入当前用户的对应的审核内容
     *
     * @param weekReviewInsertDTO
     * @param userId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertReview(WeekReviewInsertDTO weekReviewInsertDTO, int userId) throws ParseException {
        String dateTime = DateUtil.getFirstDayBy(DateUtil.getStringDate(weekReviewInsertDTO.getDateTime()));
        String weekId =  dateTime + StringUtils.leftPad(weekReviewInsertDTO.getUserId(), 5, "0");
        if (reviewMapper.selectNumByUserWeekId(weekId, userId) == 0) {
            Review review = new Review();
            review.setUserId(userId);
            review.setContent(weekReviewInsertDTO.getContent());
            review.setWeekId(weekId);
            review.setState(1);
            reviewMapper.insert(review);
        } else {
            reviewMapper.updateContentByUserWeekId(weekId, userId, weekReviewInsertDTO.getContent());
        }
        return 0;
    }

    /**
     * 用于判断插入审核接口的数据是否合理
     *
     * @param weekReviewInsertDTO
     * @param userId
     * @return 0：无权限 ; 1：正常;
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public int judgeWeekReviewInsertDTO(WeekReviewInsertDTO weekReviewInsertDTO, int userId) {
        int reviewedLevel = userMapper.selectLevelById(Integer.parseInt(weekReviewInsertDTO.getUserId()));
        if (userMapper.selectLevelById(userId) == null) {
            return 500;
        }
        switch (userMapper.selectLevelById(userId)) {
            case 4:
                if (reviewedLevel == 1 || reviewedLevel == 4) {
                    return 0;
                }
                break;
            case 3:
                if (reviewedLevel == 4 || reviewedLevel == 3 || reviewedLevel == 1) {
                    return 0;
                }
                break;
            case 2:
                if (reviewedLevel == 4 || reviewedLevel == 3 || reviewedLevel == 2){
                    return 0;
                }
                    break;
            case 1:
                return 0;
            default:
                return 0;
        }
        return 1;
    }
}
