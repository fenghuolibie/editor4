package com.example.mapper;


import com.example.dto.department.DepartmentDTO;
import com.example.entity.Department;

import java.util.List;


public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<DepartmentDTO> selectALLDepartmentName();
}