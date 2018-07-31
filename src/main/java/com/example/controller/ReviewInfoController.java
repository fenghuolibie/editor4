package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.dto.review.WeekReviewInsertDTO;
import com.example.enums.ResultCode;
import com.mangofactory.swagger.annotations.ApiIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "ReviewInfoController", tags = "审核增删改查接口")
@RestController
@RequestMapping("/review")
public class ReviewInfoController {
    //暂时不做考虑，放到recodecontroller中
    @ApiIgnore
    @GetMapping("/getCurrentReview")
    @ApiOperation(value = "获得指定被审批的用户指定时间所在周的审批内容", httpMethod = "GET")
    @ApiImplicitParam(name = "dateTime", value = "用于表示需要查询的时间（yyyyMMdd）", paramType = "query", dataType = "String", required = true)
    public CommonResult getCurrentReview(String userName, String dateTime, HttpServletRequest request) {

        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    //
    @PostMapping("/insertReview")
    @ApiOperation(value = "进行审批记录的插入", httpMethod = "POST")
    @ApiImplicitParam(name = "weekReviewInsertDTO", value = "用于表示插入所需要的时间", paramType = "body", dataType = "WeekReviewInsertDTO", required = true)
    public CommonResult insertReview(@RequestBody WeekReviewInsertDTO weekReviewInsertDTO, HttpServletRequest request) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }


    @GetMapping("/getReviewRecode")
    @ApiOperation(value = "获得指定用户指定日期所在周的可以审批的所有的日清记录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "用于表示需要查询的时间（yyyyMMdd）", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "userName", value = "用于表示审查人的姓名", paramType = "query", dataType = "String", required = true)})
    public CommonResult getReviewRecode(String dateTime, String userName) {

        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
//    @GetMapping("/getReviewRecode")
//    @ApiOperation(value = "查看当前用户的审批记录", httpMethod = "GET")
//    public CommonResult getReviewRecode(HttpServletRequest request) {
//        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
//    }
}
