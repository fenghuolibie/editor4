package com.example.controller;

import com.example.entity.User;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/excle")
public class ExcleConontroller {
    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        String templateFileName = "E:\\user.xls.xltx";
        String destFileName = "destFileName.xls";
        //模拟数据
        List<User> staff = new ArrayList<User>();
        User user = new User();
        user.setUserName("asddasdasd");
        user.setPassWord("123456");
        staff.add(user);
        XLSTransformer transformer = new XLSTransformer();
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("employees", staff);
        InputStream in = null;
        OutputStream out = null;
        //设置响应
        response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);
        response.setContentType("application/vnd.ms-excel");
        try {
            in = new BufferedInputStream(new FileInputStream(templateFileName));
            Workbook workbook = transformer.transformXLS(in, beans);
            out = response.getOutputStream();
            //将内容写入输出流并把缓存的内容全部发出去
            workbook.write(out);
            out.flush();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
