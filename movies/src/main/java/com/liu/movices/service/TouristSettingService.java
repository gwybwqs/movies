package com.liu.movices.service;


import com.liu.movices.dao.TouristSettingDao;
import com.liu.movices.entity.TouristSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TouristSettingService {
    @Autowired
    TouristSettingDao touristSettingDao;


    public TouristSetting list() {
        return  touristSettingDao.findAll().get(0);
    }

    public boolean update(TouristSetting touristSetting){
        try {
            touristSettingDao.save(touristSetting);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
