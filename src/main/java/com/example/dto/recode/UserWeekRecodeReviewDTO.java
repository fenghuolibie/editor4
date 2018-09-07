package com.example.dto.recode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "按周条件查询所有人的数据日清和审核的json数据")
public class UserWeekRecodeReviewDTO {
    @ApiModelProperty(value = "当前登录用户姓名", example = "张三")
    private String userName;

    @ApiModelProperty(value = "当前登录用户等级", example = "4")
    private String userLevel;

    @ApiModelProperty(value = "一周信息")
    private List<DateByWeekDTO> weeks = new ArrayList<>(7);

    @ApiModelProperty(value = "一周信息的审核表")
    private List<WeekReviewDTO> reviews = new ArrayList<>();
}
