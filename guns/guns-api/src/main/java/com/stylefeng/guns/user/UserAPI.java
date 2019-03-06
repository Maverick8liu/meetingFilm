package com.stylefeng.guns.user;

public interface UserAPI {
    int login(String name,String password);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int userid);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
