package com.example.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReviewUserDTO {

    @ApiModelProperty(value = "审核内容", example = "String")
    private String content;

    @ApiModelProperty(value = "审核时间", example = "String")
    private String gmtModified;

//    @ApiModelProperty(value = "审核状态",example = "0")
//    private String reviewState;
}
