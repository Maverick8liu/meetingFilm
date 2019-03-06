package com.stylefeng.guns.rest.common;

import com.stylefeng.guns.user.UserInfoModel;

public class CurrentUser {

    //private static final ThreadLocal<UserInfoModel> threadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
  /*  public static void saveUserInfo(UserInfoModel userInfoModel){
        threadLocal.set(userInfoModel);
    }

    public static UserInfoModel getCurrentUser(){
        return threadLocal.get();
    }*/


    public static void saveUserId(String userId){
        threadLocal.set(userId);
    }

    public static String getCurrentUser(){
        return threadLocal.get();
    }
}
