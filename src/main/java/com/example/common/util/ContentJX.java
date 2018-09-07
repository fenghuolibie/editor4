package com.example.common.util;


/**
 * 用于解析日清内容的工具类
 */
public class ContentJX {
    public static String getContent(String[] strs) {
        if (strs == null || strs.equals("")) {
            return "后台错误";
        }
        switch (strs[0]) {
            case "0":
                return "出差" + strs[1] + "；工作内容：" + strs[2];
            case "1":
                return "在办公室；工作内容：" + strs[2];
            case "2":
                return "南昌市外出" + strs[1] + "；工作内容" + strs[2];
            case "3":
                return "请假；请假原因：" + strs[2];
            default:
                return "后台错误";
        }
    }
}
