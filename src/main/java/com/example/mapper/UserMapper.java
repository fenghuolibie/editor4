package com.example.mapper;

import com.example.dto.userinfo.LoginMessageDTO;
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

    /**
     *
     * @param department
     * @param departmentName
     * @return
     */
    List<String> selectAllNameByDepament(@Param("department") Integer department,@Param("departmentName") String departmentName);

    List<String> selectAllName();

    String selectNameById(@Param("id") int id);

    String selectLevelByName(@Param("userName") String userName);


}