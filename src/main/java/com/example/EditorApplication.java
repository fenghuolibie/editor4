package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.example.mapper")
@ServletComponentScan(basePackages = {"com.example.common.filter"})
/*@ServletComponentScan(value = "com.example.configuration.druid")*/
public class EditorApplication {

    public static void main(String[] args) {

        SpringApplication.run(EditorApplication.class, args);

    }
}
