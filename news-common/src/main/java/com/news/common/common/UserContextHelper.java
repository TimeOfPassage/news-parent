package com.news.common.common;

public class UserContextHelper {

    private static final ThreadLocal<UserContext> USER_CONTEXT = new ThreadLocal<>();


    public static void set(UserContext context) {
        USER_CONTEXT.set(context);
    }

    public static UserContext get() {
        return USER_CONTEXT.get();
    }


    public static void remove() {
        USER_CONTEXT.remove();
    }
}
