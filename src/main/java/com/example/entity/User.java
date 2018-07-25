package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @JsonIgnore()
    private Integer id;

    /**
     * 这个uid并不对应recode的uid，recode的uid对应主键
     */
    private Integer uid;

    private String userName;

    private Integer department;

    @JsonIgnore()
    private String passWord;

}