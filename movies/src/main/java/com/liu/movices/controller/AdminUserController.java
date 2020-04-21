package com.liu.movices.controller;



import com.liu.movices.common.Pages;
import com.liu.movices.entity.AdminUser;
import com.liu.movices.entity.elseEntity.Register;
import com.liu.movices.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/adminUser")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

   /**登录验证*/
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public AdminUser check(String id, String passwd) {
        return adminUserService.check(id,passwd);
    }

    /**注册*/
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Boolean save(@RequestBody Register register){
        return adminUserService.save(register);
    }


    /**获取用户信息所有*/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Pages<AdminUser> list (Integer current, Integer pageSize){
        return adminUserService.list(current,pageSize);
    }

    /**修改*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Boolean update(@RequestBody AdminUser adminUser){
        return adminUserService.update(adminUser);
    }

  /**删除*/
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public boolean del(@RequestBody AdminUser adminUser){
        return adminUserService.del(adminUser);
    }

    /**搜索*/
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Pages<AdminUser> search (String id,String name,Integer current, Integer pageSize){
        return adminUserService.search("%"+id+"%","%"+name+"%",current,pageSize);
    }

}
