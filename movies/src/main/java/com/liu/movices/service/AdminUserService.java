package com.liu.movices.service;

import com.alibaba.fastjson.JSONObject;
import com.liu.movices.common.Pages;
import com.liu.movices.dao.AdminUserDao;
import com.liu.movices.dao.RelationDao;
import com.liu.movices.entity.AdminUser;
import com.liu.movices.entity.Relation;
import com.liu.movices.entity.elseEntity.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService {
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    RelationDao relationDao;

    public AdminUser check(String id, String password) {
        return adminUserDao.findByIdAndAndPassword(id, password);
    }


    public boolean save(Register register) {
        AdminUser adminUser;
        try {
            adminUser = adminUserDao.findById(register.getAccount()).get();
        } catch (Exception e) {
            adminUser = new AdminUser();
            adminUser.setId(register.getAccount());
            adminUser.setName(register.getUsername());
            adminUser.setPassword(register.getPasswd());
            adminUser.setRole("用户");
            adminUserDao.save(adminUser);
            return true;
        }
        return false;
    }


    public Pages<AdminUser> list(int pageNo, int pageSize) {
        Page<AdminUser> page = adminUserDao.findByRole("用户", PageRequest.of(pageNo, pageSize));
        Pages<AdminUser> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }

    public boolean update(AdminUser adminUser) {
        try {
            adminUserDao.save(adminUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean del(AdminUser adminUser) {
        List<Relation> list = relationDao.findByAuId(adminUser.getId());
        relationDao.deleteAll(list);
        adminUserDao.delete(adminUser);
        return true;
    }

    public Pages<AdminUser> search( String id1, String name1,int pageNo, int pageSize) {
        String id = id1.replaceAll(" ", "");
        String name = name1.replaceAll(" ", "");
        Page<AdminUser> page = adminUserDao.findByIdLikeOrNameLikeAndAndRole(id, name,"用户", PageRequest.of(pageNo,pageSize));
        Pages<AdminUser> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }
}
