package com.example.enums;


public enum WeekEnum {
    MONDAY(2,"星期一"),TUESDAY(3,"星期二"),WEDNESDAY(4,"星期三"),THURSDAY(5,"星期四"),FRIDAY(6,"星期五"),STAURDAY(7,"星期六"),SUNDAY(8,"星期日");
    private int code;
    private String message;
    WeekEnum(int code,String message){
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

    public static WeekEnum getByValue(int value) {
        for(WeekEnum typeEnum :WeekEnum.values()) {
            if(typeEnum.code == value) {
                return typeEnum;
            }
        }
        throw new IllegalArgumentException("No element matches " + value);
    }
}
