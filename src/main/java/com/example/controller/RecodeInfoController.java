package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.common.util.DateUtil;
import com.example.dto.RecodeConditionDTO;
import com.example.dto.RecodeCondtion2DTO;
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
import java.util.Date;

@Api(value = "RecodeInfoController", tags = "日清增删改查接口")
@RestController
@RequestMapping("/api")
public class RecodeInfoController {

    @Autowired
    private IRecodeService recodeService;

    @PostMapping("/updateRecode")
    @ApiOperation(value = "更新数据", httpMethod = "POST")
    @ApiImplicitParam(name = "userDayRecodeDTO", value = "用于封装编辑日清所需要的数据", paramType = "body", dataType = "UserDayRecodeDTO", required = true)
    public CommonResult updateRecode(@RequestBody UserDayRecodeDTO userDayRecodeDTO, HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        User user = (User) request.getSession().getAttribute("userbean");
        if (user == null) {
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("抱歉，请先登录");
            return commonResult;
        }
        if (userDayRecodeDTO == null) {
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("所传参数不能为空");
            return commonResult;
        }
        if(recodeService.updateUserRecode(userDayRecodeDTO, user) == 1){
            commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
            commonResult.setResponseMessage("更新成功");
            return commonResult;
        }
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("格式错误");
        return commonResult;

    }

    /**
     * 这个接口可由前端控制，使用另一条接口代替
     * @param request
     * @return
     */
    @GetMapping("/getUserRecode")
    @ApiOperation(value = "查询本人本周的数据", httpMethod = "GET")
//    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int",paramType = "path",required = true)
    public CommonResult selectUserDay(HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        User user = (User) request.getSession().getAttribute("userbean");
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        UserDayRecodeDTO userDayRecodeDTO = new UserDayRecodeDTO();
        userDayRecodeDTO.setWeeks(recodeService.getUserRecode(user.getId()));
        userDayRecodeDTO.setUserName(user.getUserName());
        commonResult.setResponseData(userDayRecodeDTO);
        return commonResult;
    }

    @GetMapping("/getUserByCondition")
    @ApiOperation(value = "历史条件查询所有人本周的数据日清", httpMethod = "GET")
    public CommonResult getUserByCondition(@RequestBody RecodeConditionDTO recodeConditionDTO) {
        CommonResult commonResult = new CommonResult();
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
//        commonResult.setResponseData(nu);
        return commonResult;
    }

    @PostMapping("/getUserByCondition2")
    @ApiOperation(value = "按周条件查询所有人本周的数据日清", httpMethod = "POST")
    @ApiImplicitParam(name = "recodeCondtion2DTO", value = "该参数用来封装查询条件数据数据", paramType = "body", dataType = "RecodeCondtion2DTO", required = true)
    public CommonResult getUserByCondition2(@RequestBody RecodeCondtion2DTO recodeCondtion2DTO) throws Exception {
        CommonResult commonResult = new CommonResult();
        if (recodeCondtion2DTO.getUserName() == null || recodeCondtion2DTO.getDate() == null) {
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("输入参数异常");
            return commonResult;
        }
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        commonResult.setResponseData(recodeService.getRecodeCondition2(recodeCondtion2DTO));
        return commonResult;
    }


    @PostMapping("/getData")
    @ApiOperation(value = "得到服务器日期", httpMethod = "POST")
    public CommonResult getData() {
        CommonResult commonResult = new CommonResult();
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("服务器时间");
        commonResult.setResponseData(DateUtil.getCurrentDate(new Date()));
        return commonResult;
    }
}
