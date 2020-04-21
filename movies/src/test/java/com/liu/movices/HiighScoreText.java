package com.liu.movices;

import com.alibaba.fastjson.JSONObject;
import com.liu.movices.dao.MoviesDao;
import com.liu.movices.dao.RelationDao;
import com.liu.movices.dao.TouristSettingDao;
import com.liu.movices.entity.Movies;
import com.liu.movices.entity.Relation;
import com.liu.movices.entity.TouristSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HiighScoreText {
    @Autowired
    MoviesDao moviesDao;
    @Autowired
    RelationDao relationDao;
    @Autowired
    TouristSettingDao touristSettingDao;

    @Test
    public void fun() {
        List<Movies> list1 = moviesDao.findAll();
        List<Movies> list = avgScore1(list1);

        for (Movies movies : list) {
            System.out.println(JSONObject.toJSON(movies));
        }
    }

    /**
     * 电影平均分
     */
    public List<Movies> avgScore1(List<Movies> page) {
        List<Movies> list1 = new ArrayList<>();
        TouristSetting touristSetting = touristSettingDao.findAll().get(0);
        for (Movies movies : page) {
            BigDecimal sum = new BigDecimal(0);
            List<Relation> list = relationDao.findByMoId(movies.geId());
            for (Relation relation : list) {
                sum = sum.add(relation.getScore());
            }
            try {
                BigDecimal avg = sum.divide(new BigDecimal(list.size()), 1, BigDecimal.ROUND_HALF_DOWN);
                movies.setAvgScore(avg);
            } catch (Exception e) {
                movies.setAvgScore(new BigDecimal(0));
            }
            if ((movies.getAvgScore().compareTo(touristSetting.getMinScore())) >= 0 &&
                    (movies.getAvgScore().compareTo(touristSetting.getMaxScore())) <= 0) {
                list1.add(movies);
            }
        }
        return list1;
    }
}
