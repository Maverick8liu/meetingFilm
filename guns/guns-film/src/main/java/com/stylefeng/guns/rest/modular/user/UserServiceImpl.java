package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import com.stylefeng.guns.user.UserAPI;
import com.stylefeng.guns.user.UserInfoModel;
import com.stylefeng.guns.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service(interfaceClass = UserAPI.class,loadbalance = "roundrobin")
public class UserServiceImpl implements UserAPI {

   // private UserAPI userAPI;

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String name, String password) {
       // System.out.println("this is user service!"+name+"  "+ password);
        //根据登录账号获取数据信息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(name);
        MoocUserT result = moocUserTMapper.selectOne(moocUserT);
        //获取到的结果，与数据库信息duibi

        if(result != null && result.getUuid() > 0){
            String md5Password = MD5Util.encrypt(password);
            if(result.getUserPwd().equals(md5Password)){
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        //获取注册信息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setUserPwd(userModel.getPassword());
        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setUserPhone(userModel.getPhone());
        //

        //数据加密【MD5混淆加密 + 盐值->shiro加密】
        String md5Password =  MD5Util.encrypt(userModel.getPassword());
        moocUserT.setUserPwd(md5Password);

        //将注册实体转为数据实体
       Integer insert =  moocUserTMapper.insert(moocUserT);
       if(insert >  0){
           return true;
       }else {
           return false;
       }
        //数据实体存入数据库

        //return false;
    }

    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",username);
        Integer result = moocUserTMapper.selectCount(entityWrapper);
        if(result > 0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public UserInfoModel getUserInfo(int userid) {
        MoocUserT moocUserT = moocUserTMapper.selectById(userid);

        moocUserConvertUserInfo(moocUserT);

        return null;
    }

    private void moocUserConvertUserInfo(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        userInfoModel.setSex(moocUserT.getUserSex() != null?moocUserT.getUserSex():0);
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setLifeState(""+moocUserT.getLifeState());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setUsername(moocUserT.getUserName());
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        // 将传入的参数转换为DO 【MoocUserT】
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUuid(userInfoModel.getUuid());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setUpdateTime(new Date());
        moocUserT.setNickName(userInfoModel.getNickname());
        moocUserT.setLifeState(Integer.valueOf(userInfoModel.getLifeState()));
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBeginTime(new DateTime(userInfoModel.getBeginTime()));
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setUserName(userInfoModel.getUsername());

        Integer result = moocUserTMapper.updateById(moocUserT);
        if (result > 0){
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            return userInfo;
        }else{
            return null;
        }


    }
}
