package com.liu.movices;

import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class RecommendText {
    @Autowired
    RelationDao relationDao;
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    MoviesDao moviesDao;

    @Test
    public void  fun () {
       text("18709612336");
      /*  for (Movies movies1 : movies){
            for (Movies movies2 : movies){
                if (movies1.geId() == movies2.geId()){
                    continue;
                }else {
                    List<Relation> Ni = relationDao.findByMoId(movies1.geId());*//** 喜欢电影i的集合*//*
                    List<Relation> Nj = relationDao.findByMoId(movies2.geId());*//**喜欢电影j的集合*//*
                    int Nij = Ni.size() * Nj.size();
                    if ( Nij != 0) {
                        Wij[movies1.geId()][movies2.geId()] += a[movies1.geId()][movies2.geId()]/Math.sqrt(Nij);*//**g公式计算电影i,j的相似度*//*
                        }
                    }
                }
            }*/
     /*   for (int i = 0 ;i < len ;i++){
            for (int j = 0; j<len ;j++){
                if (a[i][j] != 0){
                    System.out.print(i + " ");
                    System.out.print(j + "/");
                }
            }
            System.out.println();
        }*/

    }
    public  void text(String id){
        List<AdminUser> adminUsers = adminUserDao.findAll();
        List<Movies> movies = moviesDao.findAll();
        int len = (int) moviesDao.count();
        int[][] a = new int[len + 1][len + 1];/** 同时喜欢某两部部电影的人*/
        double[][] Wij = new double[len + 1][len + 1];/** 相似度矩阵*/
        for (AdminUser adminUser : adminUsers) {
            List<Relation> relationList = relationDao.findByAuId(adminUser.getId());
            for (Relation relation : relationList) {
                for (Relation relation1 : relationList) {
                    if (relation.getMoId() == relation1.getMoId()) {
                        continue;
                    } else {
                        a[relation.getMoId()][relation1.getMoId()] += 1; /**统计同时喜欢某两部部电影的人数*/
                    }
                }
            }
        }
        /** 计算物品之间的相似度*/
    /*    for (AdminUser adminUser : adminUsers) {
            List<Relation> relationList = relationDao.findByAuId(adminUser.getId());
            for (Relation relation : relationList) {
                for (Relation relation1 : relationList) {
                    if (relation.getMoId() == relation1.getMoId()) {
                        continue;
                    } else {
                        List<Relation> Ni = relationDao.findByMoId(relation.getMoId());*//** 喜欢电影i的集合*//*
                        List<Relation> Nj = relationDao.findByMoId(relation1.getMoId());*//**喜欢电影j的集合*//*
                        int Nij = Ni.size() * Nj.size();
                        Wij[relation.getMoId()][relation1.getMoId()] += a[relation.getMoId()][relation1.getMoId()] / Math.sqrt(Nij); *//**g公式计算电影i*//*
                    }
                }
            }
        }*/

        List<Relation> current = relationDao.findByAuId(id);/**当前用户喜欢的列表集合*/
        List<Movies> list = new ArrayList<>() ;/** 生成推荐列表*/
        for (int j = 1;j<=len;j++){
            BigDecimal Puj = new BigDecimal(0);
            for (Relation relation: current){
                BigDecimal sum ;
                int mid = relation.getWatch()+relation.getWatch();
                sum = BigDecimal.valueOf(mid).add(relation.getScore());/** 当前用户对电影i 的喜爱度*/
                List<Relation> Ni = relationDao.findByMoId(relation.getMoId());/** 喜欢电影i的集合*/
                List<Relation> Nj = relationDao.findByMoId(j);/**喜欢电影j的集合*/
                int Nij = Ni.size() * Nj.size();
                if (Nij != 0){
                    Wij[relation.getMoId()][j] += a[relation.getMoId()][j] / Math.sqrt(Nij); /**g余弦计算i.j相似度*/
                }
                BigDecimal bd = new BigDecimal(Wij[relation.getMoId()][j] );
                Wij[relation.getMoId()][j]  = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                /**当前用户对电影j的喜爱程度*/
                Puj = Puj.add(new BigDecimal(Wij[relation.getMoId()][j]).multiply(sum).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            }
            if (Puj.compareTo(BigDecimal.ZERO)!=0){
                System.out.println(Puj);
                Movies movies1 = moviesDao.findById(j);
                list.add(movies1);
            }
        }

       for (Movies movies1: list){
           System.out.println(JSONObject.toJSON(movies1));
       }
    }
}
