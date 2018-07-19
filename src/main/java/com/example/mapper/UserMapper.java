package com.example.mapper;

import com.example.entity.User;import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectNumByName(@Param("name") String name);

    String selectPasswordByName(@Param("name") String name);

    User selectUserByName(@Param("name") String name);
}