package com.example.mapper;

import com.example.dto.review.ReviewedUserDTO;
import com.example.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);

    /**
     * 通过week_id查询审批记录
     * @param weekId
     * @return
     */
    List<Review> getReviewByWeekId(@Param("weekId") String weekId);

    /**
     * 根据审查周id和当前用户id查询审批记录
     * @param weekId
     * @param userId
     * @return
     */
    Review selectReviewByUserWeekId(@Param("weekId") String weekId,@Param("userId") String userId);

    /**
     * 求得被审核人指定时间的审核记录
     * @param weekId
     * @return
     */
    List<ReviewedUserDTO> selectReviewByWeekId(@Param("weekId") String weekId);

    int selectNumByUserWeekId(@Param("weekId") String weekId,@Param("userId") int userId);

    void updateContentByUserWeekId(@Param("weekId") String weekId,@Param("userId") int userId,@Param("content") String content);
}