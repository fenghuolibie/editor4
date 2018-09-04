package com.example.editor;

import com.example.dto.recode.DateByWeek1DTO;
import com.example.dto.recode.RecodeConditionDTO;
import com.example.service.IRecodeService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
// "dateWay": "按月份查看",
//         "message": "08",
//         "userName": "ALL",
//         "year": "2018"
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditorApplicationTests {

    @Autowired
    private IRecodeService recodeService;

    @Test
    public void contextLoads() {
        RecodeConditionDTO recodeConditionDTO  = new RecodeConditionDTO().setDateWay( "按月份查看").setMessage("08").setUserName("ALL").setYear("2018");

        PageInfo<DateByWeek1DTO> pageInfo = new PageInfo<>(recodeService.getRecodeCondition(recodeConditionDTO));
        System.out.println(pageInfo);
        System.out.println("当前页："+pageInfo.getPageNum());
        System.out.println("页面总数："+pageInfo.getPageSize());
        System.out.println("总条数："+pageInfo.getTotal());
        System.out.println("当前页面条数"+pageInfo.getSize());
    }

}
