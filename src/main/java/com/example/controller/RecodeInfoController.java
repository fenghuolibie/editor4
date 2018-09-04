package com.example.controller;

import com.example.common.util.CommonResult;
import com.example.common.util.DateUtil;
import com.example.dto.recode.DateByWeek1DTO;
import com.example.dto.recode.RecodeConditionDTO;
import com.example.dto.recode.RecodeCondtion2DTO;
import com.example.dto.recode.UserDayRecodeDTO;
import com.example.entity.User;
import com.example.enums.ResultCode;
import com.example.service.IRecodeService;
import com.github.pagehelper.PageInfo;
import com.mangofactory.swagger.annotations.ApiIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

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
        User user = (User) request.getSession().getAttribute("userbean");
        if (recodeService.updateUserRecode(userDayRecodeDTO, user) == 1) {
            return new CommonResult(ResultCode.SUCCESS.getCode(),"更新成功",null);
        }
        return new CommonResult(ResultCode.ERROR.getCode(),"格式数据错误",null);
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
    public CommonResult selectUserDay(HttpServletRequest request, @RequestParam("dateTime") String dateTime) throws ParseException {
        if(!DateUtil.isValidDate(dateTime)){
            return new CommonResult(ResultCode.ERROR.getCode(),"日期格式错误",null);
        }
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(DateUtil.getStringDate(dateTime));
        int userYear = calendar.get(Calendar.YEAR);
        if(currentYear != userYear){
            return new CommonResult(ResultCode.ERROR.getCode(),"只能查询本年的日清",null);
        }
//        Date specifyDate = DateUtil.getStringDate(dateTime);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(DateUtil.getStringDate(DateUtil.getFirstDayBy(new Date())));
//        calendar.add(Calendar.DATE, -28);
//        long time1 = specifyDate.getTime();
//        long time2 = calendar.getTime().getTime();
//        if (time1 < time2) {
//            return new CommonResult(ResultCode.ERROR.getCode(),"查询日期不能在本周的前四周之前和超过本周",null);
//        }
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
        List<DateByWeek1DTO> dateByWeek1DTOS =  recodeService.getRecodeCondition(recodeConditionDTO);
        PageInfo<DateByWeek1DTO> pageInfo = new PageInfo<>(recodeService.getRecodeCondition(recodeConditionDTO));
        Map<String,Object> recodeMap = new HashMap();
        recodeMap.put("pages",pageInfo.getPages());
        recodeMap.put("total",pageInfo.getTotal());
        recodeMap.put("currentPage",pageInfo.getPageNum());
        recodeMap.put("recodelist",dateByWeek1DTOS);
        return new CommonResult(ResultCode.SUCCESS.getCode(),"查询成功",recodeMap);
    }

    @PostMapping("/getUserByCondition2")
    @ApiOperation(value = "按周条件查询所有人的数据日清和审批", httpMethod = "POST")
    @ApiImplicitParam(name = "recodeCondtion2DTO", value = "该参数用来封装查询条件数据数据", paramType = "body", dataType = "RecodeCondtion2DTO", required = true)
    public CommonResult getUserByCondition2(@RequestBody RecodeCondtion2DTO recodeCondtion2DTO, HttpServletRequest request) throws Exception {
        if (recodeCondtion2DTO.getUserName() == null || recodeCondtion2DTO.getDate() == null || !DateUtil.isValidDate(recodeCondtion2DTO.getDate())) {
            return new CommonResult(ResultCode.ERROR.getCode(),"参数不能为空",null);
        }
        User user = (User) request.getSession().getAttribute("userbean");
        return new CommonResult(ResultCode.SUCCESS.getCode(),"查询成功",recodeService.getRecodeCondition2(recodeCondtion2DTO, user));
    }


    @PostMapping("/getData")
    @ApiOperation(value = "得到服务器日期", httpMethod = "POST")
    public CommonResult getData() {
        return new CommonResult(ResultCode.SUCCESS.getCode(),"服务器时间",DateUtil.getCurrentDate(new Date()));
    }

}
