package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import com.stylefeng.guns.user.UserAPI;
import com.stylefeng.guns.user.UserInfoModel;
import com.stylefeng.guns.user.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/")
@RestController
public class UserController {

    @Reference(interfaceClass = UserAPI.class,check = true)
    private UserAPI userAPI;

    @RequestMapping(value="register",method = RequestMethod.POST)
    public ResponseVO register(UserModel userModel){
        if(userModel.getUsername() == null && userModel.getUsername().trim().length() == 0){
            return ResponseVO.serviceFail("username is null!");
        }
        if(userModel.getPassword() == null && userModel.getPassword().trim().length() == 0){
            return ResponseVO.serviceFail("passowrd is null!");
        }

        boolean isSuccess = userAPI.register(userModel);
        if(isSuccess){
            return ResponseVO.success("register successful!");
        }else {
            return ResponseVO.serviceFail("register fail!");
        }

    }

    @RequestMapping(value = "check",method = RequestMethod.POST)
    public ResponseVO check(String username){
       if(username != null && username.trim().length() > 0){
           boolean noExists = userAPI.checkUsername(username);
           if(noExists){
               return ResponseVO.success("username exists!");
           }else{
               return ResponseVO.success("username is not exists");
           }
       }else {
           return ResponseVO.serviceFail("username not null!");
       }
    }

    @RequestMapping(value="loginout",method = RequestMethod.GET)
    public ResponseVO logout(){
        /*
            应用：
                1、前端存储JWT 【七天】 ： JWT的刷新
                2、服务器端会存储活动用户信息【30分钟】
                3、JWT里的userId为key，查找活跃用户
            退出：
                1、前端删除掉JWT
                2、后端服务器删除活跃用户缓存
            现状：
                1、前端删除掉JWT
         */


        return ResponseVO.success("logout successful！");
    }


    @RequestMapping(value="getUserInfo",method = RequestMethod.GET)
    public ResponseVO getUserInfo(){
       String userId = CurrentUser.getCurrentUser();
       if(userId != null && userId.trim().length() > 0){
           int uuid = Integer.parseInt(userId);

           UserInfoModel userInfoModel = userAPI.getUserInfo(uuid);
           if(userInfoModel != null){
               return ResponseVO.success(userInfoModel);
           }else{
               return  ResponseVO.appFail("userinfo search fail!");
           }
        }else {
           return ResponseVO.serviceFail("user not login!");
       }

    }

    @RequestMapping(value="updateUserInfo",method = RequestMethod.POST)
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel){
        String userId = CurrentUser.getCurrentUser();
        if(userId != null && userId.trim().length() > 0){
            int uuid = Integer.parseInt(userId);

            //
            if(uuid == userInfoModel.getUuid()){
                UserInfoModel userInfo = userAPI.getUserInfo(uuid);
                if(userInfoModel != null){
                    return ResponseVO.success(userInfoModel);
                }else{
                    return  ResponseVO.appFail("userinfo update fail!");
                }
            }else{
                return ResponseVO.serviceFail("userinfo is errror!");
            }


        }else {
            return ResponseVO.serviceFail("user not login!");
        }

    }
}
