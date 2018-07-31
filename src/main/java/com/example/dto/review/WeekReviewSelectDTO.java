package com.example.dto.review;

import lombok.Data;

/**
 * 用于查询用户指定时间所审批的内容
 */
@Data
public class WeekReviewSelectDTO {
    private String dateTime;
    private String userName;
}
