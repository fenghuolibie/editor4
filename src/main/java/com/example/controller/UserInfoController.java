package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.dto.userinfo.LoginMessageDTO;
import com.example.dto.userinfo.LoginResponseDTO;
import com.example.entity.User;
import com.example.enums.LoginEnum;
import com.example.enums.ResultCode;
import com.example.service.IDepartmentService;
import com.example.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.example.enums.LoginEnum.SUCCESS;

@Api(value = "UserInfoController", tags = "用户的注册和登录")
@RestController
@RequestMapping("/api")
public class UserInfoController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "登录", httpMethod = "POST")
    @ApiImplicitParam(name = "loginMessageDTO", value = "该参数用来封装用户登录填入的数据", paramType = "body", dataType = "LoginMessageDTO", required = true)
    @PostMapping("/loginCheck")
    public CommonResult loginCheck(@RequestBody LoginMessageDTO loginMessageDTO, HttpServletRequest request) {
        if (loginMessageDTO.getPassWord() == null || loginMessageDTO.getUserName() == null) {
            return new CommonResult(ResultCode.ERROR.getCode(), "传入数据不能为空", null);
        }
        int resultCode = userService.checkUser(loginMessageDTO, request);
        if (resultCode == SUCCESS.getCode()) {
            User user = (User) request.getSession().getAttribute("userbean");
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO().setUserLevel(user.getUserLevel());
            return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), loginResponseDTO);
        } else if (resultCode == LoginEnum.PASSWORD_ERROR.getCode()) {
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.SUCCESS.getMessage(), LoginEnum.PASSWORD_ERROR.getMessage());
        } else {
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.SUCCESS.getMessage(), LoginEnum.USER_NULL.getMessage());
        }
    }

    @ApiOperation(value = "查询当前用户部门所有人的名字", httpMethod = "GET")
    @GetMapping("v1/getDepamentAllName")
    public CommonResult getDepamentAllName(HttpServletRequest request) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), "查询成功", userService.getDepamentAllname(0));
    }

    @ApiOperation(value = "查询所有部门人的名字", httpMethod = "GET")
    @GetMapping("v1/getAllName")
    public CommonResult getAllName(HttpServletRequest request) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), "查询成功", userService.getAllname());
    }

    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping("v1/exit")
    public CommonResult exit(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new CommonResult(ResultCode.ERROR.getCode(), "当前不存在会话", null);
        }
        session.removeAttribute("userbean");
        return new CommonResult(ResultCode.SUCCESS.getCode(), "退出成功", null);
    }

    @ApiOperation(value = "添加用户", httpMethod = "POST")
    @PostMapping("v1/addUser")
    public CommonResult addUser(HttpServletRequest request, @RequestBody User user) {
        User userbean = (User) request.getSession().getAttribute("userbean");
        switch (userService.addUser(userbean, user)) {
            case 0:
                return new CommonResult(ResultCode.ERROR.getCode(), "权限不足", null);
            case 1:
                return new CommonResult(ResultCode.SUCCESS.getCode(), "增加成功", null);
            case 2:
                return new CommonResult(ResultCode.ERROR.getCode(), "已存在该用户名", null);
            default:
                return new CommonResult(ResultCode.ERROR.getCode(), "后端报错", null);
        }
    }

    @ApiOperation(value = "删除用户", httpMethod = "GET")
    @ApiImplicitParam(name = "userName", value = "需要删除的用户姓名", paramType = "query", dataType = "String", required = true)
    @GetMapping("v1/deleteUser")
    public CommonResult deleteUser(HttpServletRequest request, String userName) {
        User userbean = (User) request.getSession().getAttribute("userbean");
        switch (userService.deleteUser(userbean, userName)) {
            case 0:
                return new CommonResult(ResultCode.ERROR.getCode(), "权限不足", null);
            case 1:
                return new CommonResult(ResultCode.SUCCESS.getCode(), "删除成功", null);
            case 2:
                return new CommonResult(ResultCode.ERROR.getCode(), "不存在该用户名", null);
            default:
                return new CommonResult(ResultCode.ERROR.getCode(), "后端报错", null);
        }
    }

    @ApiOperation(value = "查询所有部门", httpMethod = "GET")
    @GetMapping("v1/getDepartment")
    public CommonResult getDepartment() {
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), departmentService.getDepartmentName());
    }
}
