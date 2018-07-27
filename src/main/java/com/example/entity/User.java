package com.example.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private Integer userCode;

    private String userName;

    private Integer departmentId;

    private String passWord;

    private String userLevel;

}