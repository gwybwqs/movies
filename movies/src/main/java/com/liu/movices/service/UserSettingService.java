package com.liu.movices.service;

import com.liu.movices.dao.UserSettingDao;
import com.liu.movices.entity.UserSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingService {
    @Autowired
    UserSettingDao userSettingDao;
     public UserSetting list(){
         return userSettingDao.findAll().get(0);
     }

    public boolean update(UserSetting userSetting){
        try {
            userSettingDao.save(userSetting);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
