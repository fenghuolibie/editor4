package com.example.dto.recode;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RecodeConditionDTO {
    /**
     * ALL代表所有人
     */
    @ApiModelProperty(value = "用户名",example = "张三")
    private String userName;

    @ApiModelProperty(value = "所查年份",example = "2018")
    private String year;

    @ApiModelProperty(value = "查询方式",example = "按月份查看")
    private String dateWay;

    @ApiModelProperty(value = "查询方式具体",example = "01")
    private String message;
}
