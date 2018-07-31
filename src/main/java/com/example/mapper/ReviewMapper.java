package com.example.mapper;

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
}