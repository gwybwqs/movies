package com.liu.movices;


import com.liu.movices.dao.AdminUserDao;
import com.liu.movices.entity.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class ExcelText {
    @Autowired
    AdminUserDao adminUserDao;
    @Test
    public void fun() throws  Exception{
        InputStream inputStream = new FileInputStream("F:/1.xlsx");

        List<List<Object>> dataList = ExcelUtils.readAllExcel(inputStream);
        Object o1;
        Object o2;
        Object o3;
        Object o4;
        AdminUser adminUser = null;
        for (List<Object> row : dataList){
            o1 = row.get(0);
            o2 = row.get(1);
            o3 = row.get(2);
            o4 = row.get(3);
             adminUser = adminUserDao.findByIdAndAndName((String)o1,(String)o2);
             if (adminUser == null){
                 try {
                     adminUser = new AdminUser();
                     adminUser.setId((String)o1);
                     adminUser.setName((String)o2);
                     adminUser.setPassword((String)o3);
                     adminUser.setRole((String)o4);
                     adminUserDao.save(adminUser);
                 }catch (Exception e){
                     continue;
                 }
             }
        }

    }
}
