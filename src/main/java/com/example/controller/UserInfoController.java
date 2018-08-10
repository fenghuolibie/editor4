package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.dto.userinfo.LoginMessageDTO;
import com.example.dto.userinfo.LoginResponseDTO;
import com.example.entity.User;
import com.example.enums.LoginEnum;
import com.example.enums.ResultCode;
import com.example.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(value = "UserInfoController",tags = "用户的注册和登录")
@RestController
@RequestMapping("/api")
public class UserInfoController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录", httpMethod = "POST")
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
        commonResult.setResponseMessage(LoginEnum.SUCCESS.getMessage());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setUserLevel(userService.checkUser(loginMessageDTO, request));
        commonResult.setResponseData(loginResponseDTO);
        return commonResult;
    }

    @ApiOperation(value = "查询当前用户部门所有人的名字", httpMethod = "GET")
    @GetMapping("v1/getDepamentAllName")
    public CommonResult getDepamentAllName(HttpServletRequest request){
        CommonResult commonResult = new CommonResult();
        User user = (User) request.getSession().getAttribute("userbean");
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        commonResult.setResponseData(userService.getDepamentAllname(user.getDepartmentId()));
        return commonResult;
    }

    @ApiOperation(value = "查询所有部门人的名字", httpMethod = "GET")
    @GetMapping("v1/getAllName")
    public CommonResult getAllName(HttpServletRequest request){
        CommonResult commonResult = new CommonResult();
//        User user = (User) request.getSession().getAttribute("userbean");
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        commonResult.setResponseData(userService.getAllname());
        return commonResult;
    }

    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping("v1/exit")
    public CommonResult exit(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session==null){
            return new CommonResult(ResultCode.ERROR.getCode(),"当前不存在会话",null);
        }
        session.removeAttribute("userbean");
        return new CommonResult(ResultCode.SUCCESS.getCode(),"退出成功",null);
    }
}
