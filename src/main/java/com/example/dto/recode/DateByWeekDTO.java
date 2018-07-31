package com.example.dto.recode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于存放和接收用户单天所更新获需要的数据
 */
@Data
public class DateByWeekDTO {
//    @JsonIgnore()
//    @ApiModelProperty(value = "姓名",example = "张三")
//    private String userName;

    @ApiModelProperty(value = "日期",example = "20180725")
    private String dateTime;

    @ApiModelProperty(value = "星期",example = " 星期二",hidden = true)
    private String weekDay;

    @ApiModelProperty(value = "上午内容",example = "上午信息")
    private String amContent;

    @ApiModelProperty(value = "下午内容",example = "下午信息")
    private String pmContent;

    @ApiModelProperty(value = "晚上午内容",example = "晚上内容")
    private String nightContent;

    @ApiModelProperty(value = "更新时间",example = "2018-08-01 11：11：11.1",hidden = true)
    private String recodeModified;

    private String write;
}
