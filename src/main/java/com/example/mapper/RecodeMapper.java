package com.example.mapper;

import com.example.dto.DateByWeekDTO;
import com.example.entity.Recode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recode record);

    int insertSelective(Recode record);

    Recode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recode record);

//    int selectNumByDay(Recode record);

    /**
     * 只更新三个字段,上午，下午，晚上
     * @param recode
     */
    void updateRecodeByIdDay(Recode recode);

    /**
     * 查询当前人员本周的数据
     *
     * @param userId
     * @return
     */
    List<Recode> selectUserRecodeByUid7days(@Param("userId") int userId, @Param("dateTime") String dateTime);

    /**
     * 查询当前人员是否存在当前日期所在星期第一天的数据，检测是否生成数据表
     *
     * @param userId
     * @param dateTime
     * @return 1代表存在
     */
    int selectUserByDate(@Param("userId") int userId, @Param("dateTime") String dateTime);

    /**
     * 查询指定人员，指定日期人员的所在周的日清
     *
     * @param userName
     * @param dateTime
     * @return
     */
    List<Recode> selectUserRecodeByName7days(@Param("userName") String userName,  @Param("dateTime") String dateTime,@Param("departmentName") String departmentName);

    /**
     * 查询指定月份，和指定名称的人的日清
     *
     * @param userName
     * @param dateTime
     * @return
     */
    List<DateByWeekDTO> selectUserRecodeByMonth(@Param("userName") String userName, @Param("dateTime") String dateTime);

    /**
     * 查询指定季度，和指定名称的人的日清
     *
     * @param userName
     * @param quarter
     * @return
     */
    List<DateByWeekDTO> selectUserRecodeByQuarter(@Param("userName") String userName, @Param("quarter") int quarter, @Param("year") String year);

    List<DateByWeekDTO> selectUserRecodeByYear(@Param("userName") String userName, @Param("year") String year);
}