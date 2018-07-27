package com.example.dto;

import com.example.entity.Recode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel
@Data
public class UserDayRecodeDTO {
//    @ApiModelProperty(value = "当前登录用户姓名", example = "张三")
    private String userName;
    @ApiModelProperty(value = "一周信息")
    private List<Recode> weeks = new ArrayList<Recode>(7);
}
