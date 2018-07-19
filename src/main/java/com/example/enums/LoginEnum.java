package com.example.enums;

public enum LoginEnum {
    SUCCESS(1,"成功登录"),
    PASSWORD_ERROR(2,"密码错误"),
    USER_NULL(3,"用户名不存在");

    private int code;
    private String message;
    LoginEnum(int code,String message){
       this.code =code;
       this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static LoginEnum getByValue(int value) {
        for(LoginEnum typeEnum : LoginEnum.values()) {
            if(typeEnum.code == value) {
                return typeEnum;
            }
        }
        throw new IllegalArgumentException("No element matches " + value);
    }
}
