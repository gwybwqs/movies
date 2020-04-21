package com.liu.movices.service;


import com.liu.movices.common.Pages;
import com.liu.movices.dao.*;
import com.liu.movices.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesService {
    @Autowired
    MoviesDao moviesDao;
    @Autowired
    RelationDao relationDao;
    @Autowired
    TouristSettingDao touristSettingDao;
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    UserSettingDao userSettingDao;
    public Pages<Movies> list(int pageNo, int pageSize) {
        Page<Movies> page = moviesDao.findAll(PageRequest.of(pageNo, pageSize));
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }

    public boolean add(Movies movies) {
        try {
            moviesDao.save(movies);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Movies movies) {
        try {
            moviesDao.save(movies);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean del(Movies movies) {
        List<Relation> list = relationDao.findByMoId(movies.geId());
        relationDao.deleteAll(list);
        moviesDao.delete(movies);
        return true;
    }

    public Pages<Movies> search(String name, int pageNo, int pageSize) {
        Page<Movies> page = moviesDao.findByNameLike(name, PageRequest.of(pageNo, pageSize));
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }

    public Pages<Movies> search(String name, String performer, int pageNo, int pageSize) {
        Page<Movies> page = moviesDao.findByNameLikeOrPerformerLike(name, performer, PageRequest.of(pageNo, pageSize));
        avgScore(page);
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }

    public Pages<Movies> show(int pageNo, int pageSize) {
        Page<Movies> page = moviesDao.findAll(PageRequest.of(pageNo, pageSize));
        avgScore(page);
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }


    public Pages<Movies> typeShow(String type, int pageNo, int pageSize) {
        Page<Movies> page = moviesDao.findByTypeLike(type, PageRequest.of(pageNo, pageSize));
        avgScore(page);
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }

    public Pages<Movies> highScoreShow(int pageNo, int pageSize) {
        List<Movies> list1 = moviesDao.findAll();
        List<Movies> list = avgScore1(list1);
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
        Page<Movies> page = new PageImpl<Movies>(list.subList(start, end), pageable, list.size());
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }
/**
 * 可能喜欢
 * */
    public Pages<Movies> like(String id,int pageNo, int pageSize) {
        List<AdminUser> adminUsers = adminUserDao.findAll();
        List<Movies> movies = moviesDao.findAll();
        UserSetting userSetting = userSettingDao.findAll().get(0);
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

        List<Relation> current = relationDao.findByAuId(id);/**当前用户喜欢的列表集合*/
        List<Movies> list = new ArrayList<>() ;/** 生成推荐列表*/
        for (int j = 1;j<=len;j++){
            BigDecimal Puj = new BigDecimal(0);
            for (Relation relation: current){
              if (j == relation.getMoId()){
                 continue;
              } else {
                  BigDecimal sum = new BigDecimal(0) ;/** 当前用户对电影i 的喜爱度*/
                 if (relation.getWatch() == null){
                     int v = 0;
                     relation.setWatch(v) ;
                 }
                  if (relation.getDownload() == null){
                      int v = 0;
                      relation.setDownload(v);
                  }
                  int mid = relation.getWatch()+relation.getDownload();
                  if (mid != 0){
                      sum = sum.add(new BigDecimal(mid));
                  }
                  if (relation.getScore() != null){
                      sum = sum.add(relation.getScore());
                  }
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
            }
            Movies movies1 = moviesDao.findById(j);
            if (Puj.compareTo(BigDecimal.ZERO)!=0){
                if (Puj.compareTo(new BigDecimal(10)) >0){
                    System.out.println("对《"+movies1.getName()+"》喜爱度为："+ Puj +"  在推荐范围");
                    Puj = new BigDecimal(10);
                } else {
                    System.out.println("对《"+movies1.getName()+"》喜爱度为："+ Puj);
                }
                /** 排除范围*/
                if (Puj.compareTo(userSetting.getMinScore())>=0 &&
                        Puj.compareTo(userSetting.getMaxScore())<=0){
                    list.add(movies1);
                }

            }
        }
        /** 排除已看过的电影*/
      /*  List<Movies> list1 = new ArrayList<>();
        for (Relation relation : current) {
            Optional<Movies> movies1 = moviesDao.findById(relation.getMoId());
            list1.add(movies1.get());
        }
        list.removeAll(list1);*/


        Pageable pageable = PageRequest.of(pageNo,pageSize);
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
        Page<Movies> page = new PageImpl<Movies>(list.subList(start, end), pageable, list.size());
        Pages<Movies> pages = new Pages<>(page);
        pages.setData(page.getContent());
        return pages;
    }

    /**
     * 电影平均分
     */
    public void avgScore(Page<Movies> page) {
        for (Movies movies : page) {
            BigDecimal sum = new BigDecimal(0);
            List<Relation> list = relationDao.findByMoId(movies.geId());
            for (Relation relation : list) {
                try {
                    sum = sum.add(relation.getScore());
                } catch (Exception e){
                    continue;
                }
            }
            try {
                BigDecimal avg = sum.divide(new BigDecimal(list.size()), 1, BigDecimal.ROUND_HALF_DOWN);
                movies.setAvgScore(avg);
            } catch (Exception e) {
                movies.setAvgScore(new BigDecimal(0));
            }
        }
    }

    /**
     * 高分电影平均分
     */
    public List<Movies> avgScore1(List<Movies> page) {
        List<Movies> list1 = new ArrayList<>();
        TouristSetting touristSetting = touristSettingDao.findAll().get(0);
        for (Movies movies : page) {
            BigDecimal sum = new BigDecimal(0);
            List<Relation> list = relationDao.findByMoId(movies.geId());
            for (Relation relation : list) {
               try {
                   sum = sum.add(relation.getScore());
               } catch (Exception e){
                   continue;
               }
            }
            try {
                BigDecimal avg = sum.divide(new BigDecimal(list.size()), 1, BigDecimal.ROUND_HALF_DOWN);
                movies.setAvgScore(avg);
            } catch (Exception e) {
               continue;
            }
            if ((movies.getAvgScore().compareTo(touristSetting.getMinScore())) >= 0 &&
                    (movies.getAvgScore().compareTo(touristSetting.getMaxScore())) <= 0) {
                list1.add(movies);
            }
        }
        return list1;
    }

}
