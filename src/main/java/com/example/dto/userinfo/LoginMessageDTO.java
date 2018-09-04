package com.example.dto.userinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于封装登录信息的传输对象
 */
@Data

public class LoginMessageDTO {

    @ApiModelProperty(value = "用户名",example = "李德龙")
    private String userName;

    @ApiModelProperty(value = "用户密码",example = "123")
    private String passWord;

    @ApiModelProperty(value = "用户所属部门",example = "信息所",hidden = true)
    private String departmentName;

}
