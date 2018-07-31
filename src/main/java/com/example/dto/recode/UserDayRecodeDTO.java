package com.example.dto.recode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于更新日清所需要的json数据
 */
@Data
public class UserDayRecodeDTO {
    @ApiModelProperty(value = "当前登录用户姓名", example = "张三",hidden = true)
    private String userName;

    @ApiModelProperty(value = "一周信息")
    private List<DateByWeekDTO> weeks = new ArrayList<>(7);

}

