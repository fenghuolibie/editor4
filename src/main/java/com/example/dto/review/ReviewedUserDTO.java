package com.example.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReviewedUserDTO {

    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String userName;

    @ApiModelProperty(value = "审核内容", example = "String")
    private String content;

    @ApiModelProperty(value = "更新时间", example = "String")
    private String gmtModified;

}
