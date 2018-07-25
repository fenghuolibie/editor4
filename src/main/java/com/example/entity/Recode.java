package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Recode {
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer uid;

    private String valueM;

    private String valueA;

    private String valueN;

    private String theWek;

    private String theDay;

    private String write;

    @JsonIgnore
    private String recodeCreate;

    @JsonIgnore
    private String recodeModified;
}