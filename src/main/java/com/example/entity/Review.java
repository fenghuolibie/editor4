package com.example.entity;

import lombok.Data;

@Data
public class Review {
    private Integer id;

    private Integer userId;

    private String content;

    private Integer state;

    private String weekId;

    private String gmtCreate;

    private String gmtModified;

}