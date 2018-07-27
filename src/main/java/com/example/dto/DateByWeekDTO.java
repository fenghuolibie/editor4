package com.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DateByWeekDTO {
//    @JsonIgnore()
    @ApiModelProperty(value = "姓名",example = "张三")
    private String userName;

    @ApiModelProperty(value = "日期",example = "20180725")
    private String dateTime;

    @ApiModelProperty(value = "星期",example = " 星期二")
    private String weekDay;

    @ApiModelProperty(value = "上午内容",example = "上午信息")
    private String amContent;

    @ApiModelProperty(value = "下午内容",example = "下午信息")
    private String pmContent;

    @ApiModelProperty(value = "晚上午内容",example = "晚上内容")
    private String nightContent;

    private String write;
}
