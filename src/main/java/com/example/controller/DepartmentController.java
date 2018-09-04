package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.common.util.DateUtil;
import com.example.enums.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @PostMapping("/addDepartment")
    @ApiOperation(value = "增加部门", httpMethod = "POST")
    public CommonResult addDepartment(HttpServletRequest request) {
        return new CommonResult(ResultCode.SUCCESS.getCode(),"服务器时间",DateUtil.getCurrentDate(new Date()));
    }

    @GetMapping("/deleteDepartment")
    @ApiOperation(value = "删除部门", httpMethod = "GET")
    public CommonResult deleteDepartment(HttpServletRequest request) {
        return new CommonResult(ResultCode.SUCCESS.getCode(),"服务器时间",DateUtil.getCurrentDate(new Date()));
    }

    @PostMapping("/updateDepartment")
    @ApiOperation(value = "更新部门", httpMethod = "POST")
    public CommonResult updateDepartment(HttpServletRequest request) {
        return new CommonResult(ResultCode.SUCCESS.getCode(),"服务器时间",DateUtil.getCurrentDate(new Date()));
    }
}
