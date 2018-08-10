package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Recode {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer userId;

    @ApiModelProperty(value = "上午内容", example = "上午信息")
    private String amContent;

    @ApiModelProperty(value = "下午内容", example = "下午信息")
    private String pmContent;

    @ApiModelProperty(value = "晚上午内容", example = "晚上内容")
    private String nightContent;

    @ApiModelProperty(value = "星期", example = " 星期二")
    private String weekDay;

    @ApiModelProperty(value = "日期", example = "20180725")
    private String dateTime;

    @JsonIgnore
    private Integer state;

    @JsonIgnore
    private String weekId;

    @JsonIgnore
    private Date recodeCreate;

    private String recodeModified;

}