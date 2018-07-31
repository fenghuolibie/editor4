package com.example.dto.recode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserWeekRecodeReviewDTO {
    @ApiModelProperty(value = "当前登录用户姓名", example = "张三")
    private String userName;

    @ApiModelProperty(value = "一周信息")
    private List<DateByWeekDTO> weeks = new ArrayList<>(7);

    @ApiModelProperty(value = "一周信息的审核表")
    private List<WeekReviewDTO> reviews = new ArrayList<>();
}
