package com.liu.movices.dao;

import com.liu.movices.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingDao extends JpaRepository<UserSetting,Integer> {
}
