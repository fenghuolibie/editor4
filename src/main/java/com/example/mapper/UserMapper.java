package com.example.mapper;

import com.example.dto.LoginMessageDTO;
import com.example.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectNumByLogin(LoginMessageDTO loginMessageDTO);

    String selectPasswordByName(@Param("name") String name);

    User selectUserByName(@Param("name") String name);

    List<String> selectAllNameByDepament(@Param("department") int department);

    List<String> selectAllName();
}