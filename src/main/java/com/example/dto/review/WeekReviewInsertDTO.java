package com.example.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于审批数据的插入的传输格式
 */
@Data
public class WeekReviewInsertDTO {
    @ApiModelProperty(value = "所要审批的id",example = "3")
    private int reviewedUserId;
    @ApiModelProperty(value = "所在日期的一周",example = "20180730")
    private String reviewDate;
    @ApiModelProperty(value = "审核内容",example = "String")
    private String reviewContent;
}
