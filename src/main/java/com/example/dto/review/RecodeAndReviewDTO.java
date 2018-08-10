package com.example.dto.review;

import com.example.dto.recode.DateByWeekDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用于审核列表建设的
 */
@Data
@Accessors(chain = true)
public class RecodeAndReviewDTO {

    @ApiModelProperty(value = "被审核人", example = "3")
    private Integer userId;

    @ApiModelProperty(value = "被审核人姓名", example = "赵二")
    private String userName;

    @ApiModelProperty(value = "被审核人指定日期所在周的日清", example = "好好好")
    private List<DateByWeekDTO> dateByWeekDTOList;

    @ApiModelProperty(value = "审核内容及最后更新时间", example = "赵二")
    private ReviewUserDTO userReviewDTO;


}
