package com.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecodeCondtion2DTO {
    /**
     * all代表所有人
     */
    @ApiModelProperty(value = "用户名",example = "张三")
    private String userName;
    @ApiModelProperty(value = "yyyyMMdd的日期格式",example = "20180725")
    private String date;
    @ApiModelProperty(value = "部门姓名",example = "信息所")
    private String departmentName;
}
