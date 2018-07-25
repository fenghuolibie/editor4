package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class Department implements Serializable {
    @JsonIgnore()
    private Integer id;

    private Integer did;

    private String dname;

}