package com.example.common.util;

import com.example.enums.ResultCode;

/**
 * 通用响应数据
 */
public class CommonResult {
    /**
     * 响应代码
     */
    private int responseCode;

    /**
     * 响应消息
     */
    private String responseMessage;

    /**
     * 响应数据对象
     */
    private Object responseData;

    public CommonResult() {
    }

    public CommonResult(ResultCode resultCode) {
        this.responseCode = resultCode.getCode();
        this.responseMessage = resultCode.getMessage();
    }

    public CommonResult(ResultCode resultCode, Object responseData) {
        this.responseCode = resultCode.getCode();
        this.responseMessage = resultCode.getMessage();
        this.responseData = responseData;
    }

    public CommonResult(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public CommonResult(int responseCode, String responseMessage, Object responseData) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        String temp;
        if(responseMessage == null){
            temp = ",\n    \"responseMessage\"=" + responseMessage;
        }else{
            temp = ",\n    \"responseMessage\"=" + "\""+responseMessage+ "\"";
        }
        return "{\n" + "    \"responseCode\"=" + responseCode + temp  +  ",\n    \"responseData\"=" + responseData + "\n}";
    }

}
