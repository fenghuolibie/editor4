package com.example.controller;

import com.example.common.util.DateUtil;
import com.example.common.util.JxlsUtils;
import com.example.dto.recode.*;
import com.example.service.IRecodeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/excle")
public class ExcleConontroller {

    @Autowired
    private IRecodeService recodeService;

    @GetMapping("/getUserByCondition2Excle")
    @ApiOperation(value = "导出周查询的数据", httpMethod = "GET")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "date", value = "用于表示需要查询的时间（yyyyMMdd）", paramType = "query", dataType = "String"),
                    @ApiImplicitParam(name = "userName", value = "用于表示被查寻人的姓名", paramType = "query", dataType = "String")})
    public void getUserByCondition2Excle(HttpServletRequest request, HttpServletResponse response, String userName, String date) {
        RecodeCondtion2DTO recodeCondtion2DTO = null;
        if (userName == null && date == null) {
            recodeCondtion2DTO = new RecodeCondtion2DTO().setDate(DateUtil.getCurrentDate(new Date())).setDepartmentName("信息所").setUserName("ALL");
        } else {
            recodeCondtion2DTO = new RecodeCondtion2DTO().setDate(date).setUserName(userName).setDepartmentName("信息所");
        }
        String templatePath = "classpath:templates/template.xls";
        String destFileName = "destFileName.xls";
        String[] strs = null;
        List<UserWeekRecodeReviewDTO> recodeReviewDTOS = null;
        List<DateByWeekDTO> dates = null;

        Map<String, Object> model = new HashMap<>();
        try {
            recodeReviewDTOS = recodeService.getRecodeCondition2(recodeCondtion2DTO, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (UserWeekRecodeReviewDTO rr : recodeReviewDTOS) {
            for (DateByWeekDTO dateByWeekDTO : rr.getWeeks()) {
                if (dateByWeekDTO.getAmContent() != null && !dateByWeekDTO.getAmContent().trim().equals("")) {
                    strs = dateByWeekDTO.getAmContent().split("&");
                    if (strs[0].equals("1")) {
                        dateByWeekDTO.setAmContent("在" + strs[1] + "；工作内容：" + strs[2]);
                    } else {
                        dateByWeekDTO.setAmContent("出差" + strs[1] + "；工作内容：" + strs[2]);
                    }
                }
                if (dateByWeekDTO.getPmContent() != null && !dateByWeekDTO.getAmContent().trim().equals("")) {
                    strs = dateByWeekDTO.getPmContent().split("&");
                    if (strs[0].equals("1")) {
                        dateByWeekDTO.setPmContent("在" + strs[1] + "；工作内容：" + strs[2]);
                    } else {
                        dateByWeekDTO.setPmContent("出差" + strs[1] + "；工作内容：" + strs[2]);
                    }
                }
                if (dateByWeekDTO.getNightContent() != null && !dateByWeekDTO.getNightContent().trim().equals("")) {
                    strs = dateByWeekDTO.getNightContent().split("&");
                    if (strs[0].equals("1")) {
                        dateByWeekDTO.setNightContent("在" + strs[1] + "；工作内容：" + strs[2]);
                    } else {
                        dateByWeekDTO.setNightContent("出差" + strs[1] + "；工作内容：" + strs[2]);
                    }
                }
            }
        }
        dates = recodeReviewDTOS.get(0).getWeeks();
        model.put("rerw", recodeReviewDTOS);
        model.put("weeks", dates);
//
        OutputStream out = null;
        //设置响应
        response.setHeader("Content-disposition", "attachment; filename=" + destFileName);
        response.setContentType("application/force-download;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        try {
            out = response.getOutputStream();
            JxlsUtils.exportExcel(templatePath, out, model);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/getUserByConditionExcle")
    @ApiOperation(value = "历史条件查询所有人的数据日清", httpMethod = "GET")
    public void getUserByConditionExcle(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam String userName, @RequestParam String year, @RequestParam String message, @RequestParam String dateWay) {
        String templatePath = "classpath:templates/template1.xls";
        String destFileName = "destFileName.xls";
        String[] strs = null;
        RecodeConditionDTO recodeConditionDTO = null;
        Map<String, Object> model = new HashMap<>();

        if (userName == null && year == null && message == null) {
            recodeConditionDTO = new RecodeConditionDTO().setYear(new Integer(Calendar.getInstance().get(Calendar.YEAR)).toString()).setDateWay("按年份查看");
        } else {
            recodeConditionDTO = new RecodeConditionDTO().setDateWay(dateWay).setMessage(message).setUserName(userName).setYear(year);
        }
        List<DateByWeek1DTO> list = recodeService.getRecodeCondition(recodeConditionDTO);

        for (DateByWeek1DTO dateByWeek1DTO : list) {
            if (dateByWeek1DTO.getAmContent() != null && !dateByWeek1DTO.getAmContent().trim().equals("")) {
                strs = dateByWeek1DTO.getAmContent().split("&");
                if (strs[0].equals("1")) {
                    dateByWeek1DTO.setAmContent("在" + strs[1] + "；工作内容：" + strs[2]);
                } else {
                    dateByWeek1DTO.setAmContent("出差" + strs[1] + "；工作内容：" + strs[2]);
                }
            }
            if (dateByWeek1DTO.getPmContent() != null && !dateByWeek1DTO.getPmContent().trim().equals("")) {
                strs = dateByWeek1DTO.getPmContent().split("&");
                if (strs[0].equals("1")) {
                    dateByWeek1DTO.setPmContent("在" + strs[1] + "；工作内容：" + strs[2]);
                } else {
                    dateByWeek1DTO.setPmContent("出差" + strs[1] + "；工作内容：" + strs[2]);
                }
            }
            if (dateByWeek1DTO.getNightContent() != null && !dateByWeek1DTO.getNightContent().trim().equals("")) {
                strs = dateByWeek1DTO.getNightContent().split("&");
                if (strs[0].equals("1")) {
                    dateByWeek1DTO.setNightContent("在" + strs[1] + "；工作内容：" + strs[2]);
                } else {
                    dateByWeek1DTO.setNightContent("出差" + strs[1] + "；工作内容：" + strs[2]);
                }
            }
        }

        model.put("hs", list);
        OutputStream out = null;
        //设置响应
        response.setHeader("Content-disposition", "attachment; filename=" + destFileName);
        response.setContentType("application/force-download;charset=UTF-8");
        try {
            out = response.getOutputStream();
            JxlsUtils.exportExcel(templatePath, out, model);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
