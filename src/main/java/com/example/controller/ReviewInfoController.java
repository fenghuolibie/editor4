package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.common.util.DateUtil;
import com.example.dto.review.WeekReviewInsertDTO;
import com.example.entity.User;
import com.example.enums.ResultCode;
import com.example.service.IReviewService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Api(value = "ReviewInfoController", tags = "审核增删改查接口")
@RestController
@RequestMapping("/review")
public class ReviewInfoController {

    @Autowired
    private IReviewService reviewService;

    @ApiIgnore
    @GetMapping("/getCurrentReview")
    @ApiOperation(value = "获得指定被审批的用户指定时间所在周的审批内容", httpMethod = "GET")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "dateTime", value = "用于表示需要查询的时间（yyyyMMdd）", paramType = "query", dataType = "String", required = true),
                    @ApiImplicitParam(name = "userName", value = "用于表示被审查人的姓名", paramType = "query", dataType = "String", required = true)})
    public CommonResult getCurrentReview(String userName, String dateTime) throws ParseException {
        if (dateTime == null || userName == null || dateTime.trim().length() == 0 || userName.trim().length() == 0) {
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), "输入值不能为空");
        }
        if(!DateUtil.isValidDate(dateTime)){
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), "日期格式错误");
        }
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), reviewService.getReview(dateTime, userName));
    }

    @PostMapping("/insertReview")
    @ApiOperation(value = "进行审批记录的插入", httpMethod = "POST")
    @ApiImplicitParam(name = "weekReviewInsertDTO", value = "用于表示插入所需要的时间", paramType = "body", dataType = "WeekReviewInsertDTO", required = true)
    public CommonResult insertReview(@RequestBody WeekReviewInsertDTO weekReviewInsertDTO, HttpServletRequest request) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getStringDate(DateUtil.getFirstDayBy(new Date())));
        calendar.add(Calendar.DATE, 6);
        if (calendar.getTime().getTime() < DateUtil.getStringDate(weekReviewInsertDTO.getDateTime()).getTime()) {
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), "日期越界，不能审批超过本周的日期");
        }
        User user = (User) request.getSession().getAttribute("userbean");
        if (reviewService.judgeWeekReviewInsertDTO(weekReviewInsertDTO, user.getId()) == 0) {
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), "您无资格审批");
        }
        reviewService.insertReview(weekReviewInsertDTO, user.getId());
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), weekReviewInsertDTO);
    }


    @GetMapping("/getReviewRecode")
    @ApiOperation(value = "获得指定用户指定日期所在周的可以审批的所有的日清记录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "用于表示需要查询的时间（yyyyMMdd）", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "userName", value = "用于表示审查人的姓名", paramType = "query", dataType = "String", required = true)})
    public CommonResult getReviewRecode(String dateTime, String userName) throws ParseException {
        if (dateTime == null || userName == null || dateTime.trim().length() == 0 || userName.trim().length() == 0) {
            return new CommonResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), "输入值不能为空");
        }
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), reviewService.getRecodeReciewByLevel(dateTime, userName));
    }

}
