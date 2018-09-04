package com.example.serviceimpl;

import com.example.dto.department.DepartmentDTO;
import com.example.mapper.DepartmentMapper;
import com.example.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<DepartmentDTO> getDepartmentName() {
        return departmentMapper.selectALLDepartmentName();
    }
}
