package com.example.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用于审批数据的插入的传输格式
 */
@Data
@Accessors(chain = true)
public class WeekReviewInsertDTO {
    @ApiModelProperty(value = "所要审批的用户id",example = "3")
    private String userId;
    @ApiModelProperty(value = "日期所在周的周一",example = "20180730")
    private String dateTime;
    @ApiModelProperty(value = "审核内容",example = "String")
    private String content;
}
