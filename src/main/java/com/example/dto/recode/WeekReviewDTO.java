package com.example.dto.recode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于单周单人审批数据传输
 */
@Data
public class WeekReviewDTO {
    @ApiModelProperty(value = "审核人")
    private String reviewUser;
    @ApiModelProperty(value = "审核内容")
    private String content;
    @ApiModelProperty(value = "审核时间")
    private String gmtModified;
}
