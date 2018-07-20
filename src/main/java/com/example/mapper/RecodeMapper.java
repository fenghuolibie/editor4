package com.example.mapper;

import com.example.entity.Recode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recode record);

    int insertSelective(Recode record);

    Recode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recode record);

    int updateByPrimaryKey(Recode record);

    int selectNumByDay(Recode record);

    void updateRecodeByIdDay(Recode recode);

    /**
     * 查询当前人员本周的数据
     * @param uid
     * @return
     */
    List<Recode> selectUserRecodeByUid7days (@Param("uid") int uid, @Param("day") String day);


}