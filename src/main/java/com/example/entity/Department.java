package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Department {
    private Integer id;

    private Integer departmentCode;

    private String departmentName;
}