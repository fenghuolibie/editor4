package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.dto.LoginMessageDTO;
import com.example.entity.User;
import com.example.enums.ResultCode;
import com.example.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "UserInfoController",tags = "用户的注册和登录")
@RestController
@RequestMapping("/api")
public class UserInfoController {
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "测试接口", httpMethod = "GET")
    @GetMapping(value = "/v1/user")
    public User getUserInfo(@RequestParam Integer userId) {
        return null;
    }

    @ApiOperation(value = "登录验证", httpMethod = "POST")
    @ApiImplicitParam(name = "loginMessageDTO", value = "该参数用来封装用户登录填入的数据", paramType = "body", dataType = "LoginMessageDTO", required = true)
    @PostMapping("/loginCheck")
    public CommonResult loginCheck(@RequestBody LoginMessageDTO loginMessageDTO, HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        if (loginMessageDTO == null) {
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("传入数据不能为空");
            return commonResult;
        }
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage(userService.checkUser(loginMessageDTO, request));
        return commonResult;
    }

    @ApiOperation(value = "查询当前登陆部门所有人的名字", httpMethod = "POST")
    @GetMapping("/getAllName")
    public CommonResult getAllName(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("userbean");
        userService.getAllname(user.getDepartment());
    }
}
