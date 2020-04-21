package com.liu.movices.controller;

import com.liu.movices.entity.TouristSetting;
import com.liu.movices.service.TouristSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/touristSetting")
public class TouristSettingController {
    @Autowired
    TouristSettingService touristSettingService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TouristSetting list() {
        return touristSettingService.list();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Boolean update(@RequestBody TouristSetting touristSetting){
        return touristSettingService.update(touristSetting);
    }

}
