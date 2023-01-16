package com.news.common.enums;

public enum EnableEnum {
    NO(0, "否"), YES(1, "是");


    EnableEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
