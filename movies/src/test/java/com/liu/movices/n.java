package com.liu.movices;

import com.liu.movices.dao.AdminUserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class n {
    @Autowired
    AdminUserDao adminUserDao;
    @Test
    public void fun(){
        Pageable pageable = PageRequest.of(0,10);
        System.out.println(adminUserDao.findByRole("管理员",pageable));
    }
}
