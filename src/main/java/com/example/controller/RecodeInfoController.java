package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.common.util.DateUtil;
import com.example.dto.UserDayRecodeDTO;
import com.example.entity.User;
import com.example.enums.ResultCode;
import com.example.service.IRecodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "RecodeInfoController",tags = "日清增删改查接口")
@RestController
@RequestMapping("/api")
public class RecodeInfoController {

    @Autowired
    private IRecodeService recodeService;

    @PostMapping("/updateRecode")
    @ApiOperation(value = "更新数据", httpMethod = "POST")
    @ApiImplicitParam(name = "userDayRecodeDTO", value = "用于封装编辑日清所需要的数据", paramType = "body", dataType = "UserDayRecodeDTO", required = true)
    public CommonResult updateRecode(@RequestBody UserDayRecodeDTO userDayRecodeDTO,HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        User user = (User)request.getSession().getAttribute("userbean");
        System.out.println(user);
        if(user == null){
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("抱歉，请先登录");
            return commonResult;
        }
        if(userDayRecodeDTO == null){
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("所传参数不能为空");
            return commonResult;
        }
        System.out.println(userDayRecodeDTO);
        recodeService.updateUserRecode(userDayRecodeDTO,user);
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("更新成功");
        return commonResult;
    }

    @GetMapping("/getUserRecode/{userId}")
    @ApiOperation(value = "查询本人本周的数据", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int",paramType = "path",required = true)
    public CommonResult selectUserDay(@PathVariable int userId){
        CommonResult commonResult = new CommonResult();
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        commonResult.setResponseData(recodeService.getUserRecode(userId));
        return commonResult;
    }

    @GetMapping("/getUserByCondition")
    @ApiOperation(value = "条件查询的数据", httpMethod = "GET")
    public CommonResult getUserByCondition(String theDay,String UserName,HttpServletRequest request){
        CommonResult commonResult = new CommonResult();

        return commonResult;
    }


    //查询所有人的历史数据
    @GetMapping("/getAllUserRecode")
    @ApiOperation(value = "查询本人本周的数据", httpMethod = "GET")
    public CommonResult getAllUserRecode(){
        CommonResult commonResult = new CommonResult();
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
//        commonResult.setResponseData();
        return commonResult;
    }
    // 查询所有人本周所有的数据
//    @GetMapping("/getAllUserRecode")
    public CommonResult getAllUserRecodeDay(){
        return null;
    }
    @PostMapping("/getData")
    @ApiOperation(value = "得到服务器日期", httpMethod = "POST")
    public CommonResult getData() {
        CommonResult commonResult = new CommonResult();
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("服务器时间");
        commonResult.setResponseData(DateUtil.getCurrentDate());
        return commonResult;
    }
}
