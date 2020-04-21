package com.liu.movices.controller;


import com.liu.movices.entity.UserSetting;
import com.liu.movices.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "userSetting")
public class UserSettingController {
    @Autowired
    UserSettingService userSettingService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public UserSetting list(){
        return userSettingService.list();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Boolean update(@RequestBody UserSetting userSetting){
        return userSettingService.update(userSetting);
    }
}
