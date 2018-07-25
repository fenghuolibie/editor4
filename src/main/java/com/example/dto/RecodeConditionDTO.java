package com.example.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecodeConditionDTO {
    /**
     * all代表所有人
     */
    @ApiModelProperty(value = "用户名",example = "张三")
    private String userName;
    @ApiModelProperty(value = "所查年份",example = "2018")
    private String year;
    @ApiModelProperty(value = "查询方式",example = "按月份查询")
    private String dateWay;
    @ApiModelProperty(value = "查询方式具体",example = "1月份")
    private String message;
}
