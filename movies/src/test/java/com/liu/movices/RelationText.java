package com.liu.movices;

import com.liu.movices.dao.AdminUserDao;
import com.liu.movices.dao.MoviesDao;
import com.liu.movices.dao.RelationDao;
import com.liu.movices.entity.AdminUser;
import com.liu.movices.entity.Movies;
import com.liu.movices.entity.Relation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class RelationText {
    @Autowired
    RelationDao relationDao;
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    MoviesDao moviesDao;
    @Test
    public void fun(){
        AdminUser adminUser;
        Movies movies;
        Relation relation;
        List<AdminUser> list = adminUserDao.findAll();

        List<Movies> list1 = moviesDao.findAll();

        for (int i = 0; i<2021;i++){
            Random random = new Random();
            int n = random.nextInt(list.size());
            adminUser = list.get(n);

            Random random1 = new Random();
            int n1 = random1.nextInt(list.size());
            movies = list1.get(n1);

            relation = relationDao.findByMoIdAndAndAuId(movies.geId(),adminUser.getId());
            if (relation == null){
                BigDecimal db = new BigDecimal(Math.random()*8 + 2);
                relation = new Relation();
                relation.setAuId(adminUser.getId());
                relation.setMoId(movies.geId());
                relation.setWatch(1);
                relation.setDownload(2);
                relation.setScore(db);
                relationDao.save(relation);
            }
        }
    }
}
