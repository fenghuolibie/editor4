package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.common.util.DateUtil;
import com.example.dto.recode.RecodeConditionDTO;
import com.example.dto.recode.RecodeCondtion2DTO;
import com.example.dto.recode.UserDayRecodeDTO;
import com.example.entity.User;
import com.example.enums.ResultCode;
import com.example.service.IRecodeService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Api(value = "RecodeInfoController", tags = "日清增删改查接口")
@RestController
@RequestMapping("/recode")
public class RecodeInfoController {

    @Autowired
    private IRecodeService recodeService;

    @PostMapping("/updateRecode")
    @ApiOperation(value = "更新当前登录用户的数据", httpMethod = "POST")
    @ApiImplicitParam(name = "userDayRecodeDTO", value = "用于封装编辑日清所需要的数据", paramType = "body", dataType = "UserDayRecodeDTO", required = true)
    public CommonResult updateRecode(@RequestBody UserDayRecodeDTO userDayRecodeDTO, HttpServletRequest request) throws ParseException {
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
        if (recodeService.updateUserRecode(userDayRecodeDTO, user) == 1) {
            commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
            commonResult.setResponseMessage("更新成功");
            return commonResult;
        }
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("格式数据错误");
        return commonResult;

    }

    /**
     * 这个接口可由前端控制，使用另一条接口代替
     *
     * @param request
     * @return
     */
    @ApiIgnore()
    @GetMapping("/getUserRecode")
    @ApiOperation(value = "查询当前用户指定日期所在周的日清和审核", httpMethod = "GET")
    @ApiImplicitParam(name = "dateTime", value = "表示需要查询的日期", dataType = "String", paramType = "query", required = true)
    public CommonResult selectUserDay(HttpServletRequest request, String dateTime) throws ParseException {
        if(!DateUtil.isValidDate(dateTime)){
            return new CommonResult(ResultCode.ERROR.getCode(),"日期格式错误",null);
        }
        Date specifyDate = DateUtil.getStringDate(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getStringDate(DateUtil.getFirstDayBy(new Date())));
        calendar.add(Calendar.DATE, -28);
        long time1 = specifyDate.getTime();
        long time2 = calendar.getTime().getTime();
        if (time1 < time2) {
            return new CommonResult(ResultCode.ERROR.getCode(),"查询日期不能在本周的前四周之前和超过本周",null);
        }
        User user = (User) request.getSession().getAttribute("userbean");
        UserDayRecodeDTO userDayRecodeDTO = new UserDayRecodeDTO();
        userDayRecodeDTO.setWeeks(recodeService.getUserRecode(user.getId(), dateTime));
        userDayRecodeDTO.setUserName(user.getUserName());
        return new CommonResult(ResultCode.SUCCESS.getCode(),"查询成功",userDayRecodeDTO);
    }

    @PostMapping("/getUserByCondition")
    @ApiOperation(value = "历史条件查询所有人的数据日清", httpMethod = "POST")
    @ApiImplicitParam(name = "recodeConditionDTO", value = "该参数用来封装查询条件数据数据", paramType = "body", dataType = "RecodeConditionDTO", required = true)
    public CommonResult getUserByCondition(@RequestBody RecodeConditionDTO recodeConditionDTO) {
        CommonResult commonResult = new CommonResult();
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        commonResult.setResponseData(recodeService.getRecodeCondition(recodeConditionDTO));
        return commonResult;
    }

    @PostMapping("/getUserByCondition2")
    @ApiOperation(value = "按周条件查询所有人的数据日清和审批", httpMethod = "POST")
    @ApiImplicitParam(name = "recodeCondtion2DTO", value = "该参数用来封装查询条件数据数据", paramType = "body", dataType = "RecodeCondtion2DTO", required = true)
    public CommonResult getUserByCondition2(@RequestBody RecodeCondtion2DTO recodeCondtion2DTO, HttpServletRequest request) throws Exception {
        CommonResult commonResult = new CommonResult();
        if (recodeCondtion2DTO.getUserName() == null || recodeCondtion2DTO.getDate() == null || !DateUtil.isValidDate(recodeCondtion2DTO.getDate())) {
            commonResult.setResponseCode(ResultCode.ERROR.getCode());
            commonResult.setResponseMessage("输入参数异常");
            return commonResult;
        }
        commonResult.setResponseCode(ResultCode.SUCCESS.getCode());
        commonResult.setResponseMessage("查询成功");
        commonResult.setResponseData(recodeService.getRecodeCondition2(recodeCondtion2DTO, request));
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
