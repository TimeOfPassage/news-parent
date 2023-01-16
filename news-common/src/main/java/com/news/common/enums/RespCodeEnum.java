package com.news.common.enums;

public enum RespCodeEnum {
    FAIL(-1, "操作失败"), SUCCESS(0, "操作成功"),
    /**
     * 10xx段供user服务使用
     */
    PHONE_NOT_BLANK(1001, "手机号不能为空"), PASSWORD_NOT_BLANK(1002, "密码不能为空"), ACCOUNT_PASSWORD_NOT_ILLEGAL(1003, "登陆失败，账号或密码错误"), ACCOUNT_NOT_EXIST(1004, "账号不存在"),
    /**
     * 20xx段供artcile服务使用
     */
    CATEGORY_NAME_NOT_NULL(2001, "分类名不能为空"), CATEGORY_NAME_EXIST(2002, "分类名已存在"), CATEGORY_ID_NOT_NULL(2003, "分类ID不能为空"), CATEGORY_NOT_EXIST(2004, "分类不存在"),
    MATERIAL_NOT_EXIST(2005, "素材不存在"),
    ARTICLE_TITLE_NOT_NULL(2006, "文章标题不能为空"), ARTICLE_CONTENT_NOT_NULL(2007, "文章内容不能为空"), ARTICLE_NOT_EXIST(2008, "文章不存在"),
    ARTICLE_TITLE_REPEAT(2007, "文章标题重复");


    RespCodeEnum(int code, String desc) {
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
